package com.mall.controller.web.lottery;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.reflect.TypeToken;
import com.mall.annotation.LoginValid;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.Iresp_common;
import com.mall.controller.web.vo.LotteryGoods;
import com.mall.controller.web.vo.LotteryInf;
import com.mall.controller.web.vo.LotteryNum;
import com.mall.controller.web.vo.ReqOrder;
import com.mall.controller.web.vo.WalletPoints;
import com.mall.util.DateUtil;
import com.mall.util.HttpUtils;
import com.mall.util.PublicKeyMap;
import com.mall.util.RSAUtils;
import com.mall.util.RedisPool;
import com.mall.util.ReqUtils;
import com.mall.util.StringUtil;

/**
 * ******************  类说明  *********************
 * class       :  LotteryCtl
 * @author     :  zhurouyu
 * @version    :  1.0  
 * description :  "积分抽奖"视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/lottery")
public class LotteryCtl extends BaseCtl{
	
	private static final Logger LOG = LoggerFactory.getLogger(LotteryCtl.class);
	//未中奖的返回码
	private static final String WEI_ZHONG_JIANG = "5";
	
	/**
	 * ********************************************
	 * method name   : game 
	 * description   : 进入"积分抽奖"页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-11  下午9:40:32
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/game.htm")
	public String game(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//用户id
		String userid = (String)request.getAttribute(Contants.USERID);

		//根据userid查用户积分
		String userurl =getServiceURL("/emall/api/member/detail/"+userid);
		String userres = HttpUtils.doGet(userurl,null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("# clicklottery() 响应信息 result={}", userres);
		Map userJson = gson.fromJson(userres, Map.class);
		//获取用户的积分信息
		BigDecimal points=new BigDecimal((Double)userJson.get("points"));
		modelMap.addAttribute("points", points);
		//获取每次抽奖积分，走配置项管理LOTTERY-POINTS
		//从redis中获取抽奖积分
		String lotteryPoints=RedisPool.get("LOTTERY-POINTS");
		if(lotteryPoints==null){
			lotteryPoints="50";
		}
		//最新中奖记录
		String baseurl =getServiceURL("emall/api/lottery/lotteryinf");
		String results = HttpUtils.doPost(baseurl,null, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#game() 响应信息 result={}", results);
		//判断当天抽奖次数
		String numurl =getServiceURL("/emall/api/lottery/"+userid);
		String numres = HttpUtils.doPost(numurl,null,null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("# clicklottery() 响应信息 result={}", numres);
		Integer num=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String date=df.format(new Date());//当前日期
		if(!"".equals(numres)){
			LotteryNum lott = gson.fromJson(numres, new TypeToken<LotteryNum>() {}.getType());
			String ldate=lott.getDate();
			if(ldate.equals(date)){
				num=lott.getNum();
			}else{
				num=0;
			}
			
		}
		//用于返回请求
        List<LotteryInf> lotteryList=new ArrayList<LotteryInf>();
        List<LotteryInf> lotteryList2=new ArrayList<LotteryInf>();
		if(!StringUtil.isEmpty(results)){
			//返回请求
			lotteryList = gson.fromJson(results, new TypeToken<List<LotteryInf>>(){}.getType());
			for(int i=0;i<lotteryList.size();i++){
				LotteryInf lf=lotteryList.get(i);
				String mob=lf.getMobileid();
				String str=mob.substring(3, 7);
				String mid=mob.replace(str, "****");
				lf.setMobileid(mid);
				lotteryList2.add(lf);
			}
		}
		//从redis中获取超时时间
		String timeout=RedisPool.get("lottery.timeout");
		if(timeout==null||"".equals(timeout)){
			modelMap.addAttribute("timeout", "5000");//超时时间
		}else{
			modelMap.addAttribute("timeout", timeout);//超时时间
		}
		modelMap.addAttribute("lotterycount", num);
		modelMap.addAttribute("lotteryList", lotteryList2);//中奖记录
		modelMap.addAttribute("lotterypoints", lotteryPoints);//抽奖消耗积分
		return "lottery/game";
	}
	/**
	 * ********************************************
	 * method name   : clicklottery 
	 * description   : 点击"抽奖"
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-11  下午9:40:32
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/clicklottery.htm")
	public void clicklottery(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//用户id
		String userid = (String)request.getAttribute(Contants.USERID);
		//获取当天抽奖次数
		String numurl =getServiceURL("/emall/api/lottery/"+userid);
		String numres = HttpUtils.doPost(numurl,null,null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("# clicklottery() 响应信息 result={}", numres);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String date=df.format(new Date());//当前日期
		LotteryNum lotterynum=null;
		int lotterycount=0;
		if("".equals(numres)){
			//如果不存在，就添加
			lotterynum=new LotteryNum();
			lotterynum.setNum(1);
			lotterynum.setUserid(userid);
			lotterynum.setDate(df.format(new Date()));
			String numbody = gson.toJson(lotterynum);
			numurl =getServiceURL("/emall/api/lottery/addnum");
			numres = HttpUtils.doPost(numurl,numbody,null, 3, 10, Contants.CHARSET_UTF_8);
			LOG.info("# clicklottery() 添加用户抽奖次数成功，响应信息 result={}", numres);
		}else{
			//存在就修改时间和次数
			LotteryNum lott = gson.fromJson(numres, new TypeToken<LotteryNum>() {}.getType());
			Integer lnum=lott.getNum();
			String ldate=lott.getDate();
			//先判断是否是同一天
			if(!ldate.equals(date)){
				//不是同一天 更新时间 并修改次数
				Map<String,String> map =new HashMap<String, String>();
				map.put("userid", userid);
				map.put("date", date);
				map.put("beforenum", String.valueOf(lnum));
				map.put("beforedate", ldate);
				String numbody = gson.toJson(map);
				numurl =getServiceURL("/emall/api/lottery/modifynumdiff");
				numres = HttpUtils.doPost(numurl,numbody,null, 3, 10, Contants.CHARSET_UTF_8);
				LOG.info("# clicklottery() 更新用户抽奖次数成功 响应信息 result={}", numres);
			}else{
				//是同一天就 累加抽奖次数
				lotterynum=new LotteryNum();
				lotterynum.setNum(1);
				lotterynum.setUserid(userid);
				lotterynum.setDate(df.format(new Date()));
				String numbody = gson.toJson(lotterynum);
				numurl =getServiceURL("/emall/api/lottery/modifynum");
				numres = HttpUtils.doPost(numurl,numbody,null, 3, 10, Contants.CHARSET_UTF_8);
				LOG.info("# clicklottery() 更新用户抽奖次数成功 响应信息 result={}", numres);
				lotterycount=lnum+1;
			}
			
		}
		//根据userid查用户积分
		String userurl =getServiceURL("/emall/api/member/detail/"+userid);
		String userres = HttpUtils.doGet(userurl,null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("# clicklottery() 响应信息 result={}", userres);
		Map userJson = gson.fromJson(userres, Map.class);
		//获取用户的积分信息
		BigDecimal points=new BigDecimal((Double)userJson.get("points"));
		String mobileid=(String)userJson.get("mobileid");
		Iresp_common result = new Iresp_common();
		//获取每次抽奖积分，走配置项管理LOTTERY-POINTS
		//从redis中获取抽奖积分
		String lotteryPoints=RedisPool.get("LOTTERY-POINTS");
		if(lotteryPoints==null){
			lotteryPoints="50";
		}
		Integer lpoints=Integer.parseInt(lotteryPoints);
		//获取用户的积分信息
		//判断用户积分是否够一次抽奖
		if(points.intValue() < lpoints){
			result.setRet("fail");
			result.setMsg("您的积分不足，不能进行本次抽奖");
			LOG.info("# clicklottery() 扣减积分失败,userid:{}",userid);
			String content = gson.toJson(result);
			super.write(response, content);
			return ;
		}

		//插入积分表的扣减信息
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		WalletPoints wp=new WalletPoints();
		wp.setUserid(userid);
		wp.setPointsio(1);//支出
		wp.setPoints(new BigDecimal(lotteryPoints));
		wp.setOrderdate(df2.format(new Date()));
		wp.setFuncode("JFCJ");//funcode 后续会修改
		wp.setDescribe("积分抽奖消耗"+lotteryPoints+"积分");
		
		String pointbody = gson.toJson(wp);
		String pointsurl =getServiceURL("emall/api/lottery/updatepoints");
		LOG.info("# clicklottery() 插入积分表明细,请求报文:{} ", pointbody);
		String pointres = HttpUtils.doPost(pointsurl,pointbody, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("# clicklottery() 响应信息 result={}", pointres); 
		//开始抽奖
		String result_id=this.getTicket(userid,mobileid);
		String trace="";
		if(result_id.length()>2){
			trace=result_id.substring(1, result_id.length());
			result_id=result_id.substring(0, 1);
		}
		LOG.info("# clicklottery() 抽奖结果:{},userid:{}", result_id, userid);
		//根据userid查用户积分
		userurl =getServiceURL("/emall/api/member/detail/"+userid);
		userres = HttpUtils.doGet(userurl,null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("# clicklottery() 响应信息 result={}", userres);
		userJson = gson.fromJson(userres, Map.class);
		Map<String,Object> mapRes=new HashMap<String,Object>();
		Map<String,Object> map=new HashMap<String,Object> ();
		map.put("points", userJson.get("points"));
		map.put("goodsid", result_id);
		if(!"".equals(trace)){
			map.put("trace", trace);
		}
		map.put("lotterycount", lotterycount);
		mapRes.put("ret","success");
		mapRes.put("msg",map);
		String content = gson.toJson(mapRes);
		super.write(response, content);
		return;
	}
	/**
	 * ********************************************
	 * method name   : getTicket 
	 * description   : 开始抽奖
	 * @return       : String
	 * @param        : @param userid
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-11  下午9:40:32
	 * @see          : 
	 * *******************************************
	 */
	public String getTicket(String userid,String mobileid) throws Exception{
		try{
			//获取抽奖商品信息
			String goodsurl =getServiceURL("emall/api/lottery/lotterygoods");
			String goodsresult = HttpUtils.doPost(goodsurl,null, null, 3, 10, Contants.CHARSET_UTF_8);
			LOG.info("# getTicket() 响应信息 result={}", goodsresult);
			//用于抽奖商品返回请求
	        List<LotteryGoods> lotteryList=new ArrayList<LotteryGoods>();
			if(!StringUtil.isEmpty(goodsresult)){
				//返回请求
				lotteryList = gson.fromJson(goodsresult, new TypeToken<List<LotteryGoods>>(){}.getType());
			}
			//获取用户抽奖概率，从redis中获取LOTTERY-USER-RATIO
			String user_lottery=RedisPool.get("LOTTERY-USER-RATIO");//用户中奖概率
			if(user_lottery==null||"".equals(user_lottery)){
				user_lottery="1";
			}
			//获取商品中奖概率，从redis中获取LOTTERY-GOODS-RATIO 
			String goods_lottery=RedisPool.get("LOTTERY-GOODS-RATIO");//商品中奖概率
			//获取每次抽奖积分，从redis中获取LOTTERY-POINTS
			String lotteryPoints=RedisPool.get("LOTTERY-POINTS");//用户抽奖积分
			if(lotteryPoints==null||"".equals(goods_lottery)){
				lotteryPoints="50";
			}
			//抽奖结果
			Random r = new Random();
			//[0,10000)
			int r_result = r.nextInt(10000);
			if(r_result >= Integer.parseInt(user_lottery)){
				//没有在中奖区间内，则未中奖
				LOG.info("# getTicket() 没有中奖,返回未中奖,userid:{}", userid);
				return WEI_ZHONG_JIANG;
			}
			String goodsid =WEI_ZHONG_JIANG;//中奖商品号
			String trace = "";//中奖订单
			Random r_goodsid = new Random();
			//中奖随机数[0,10000)
			int win_num = r_goodsid.nextInt(lotteryList.size());//商品中奖率相等，随机出一个
			//落在哪个奖品上
			LotteryGoods lgoods = lotteryList.get(win_num);
			goodsid=lgoods.getGoods_id();
			/*for(LotteryGoods goods : lotteryList){
				if(win_num <= win_num){
					//中奖
					lgoods = new LotteryGoods(); 
					lgoods.setGoods_id(goods.getGoods_id());
					lgoods.setGoods_num(goods.getGoods_num());
					lgoods.setGoods_name(goods.getGoods_name());
					lgoods.setGet_num(goods.getGet_num());
					lgoods.setGoods_type(goods.getGoods_type());
					lgoods.setGive_point(goods.getGive_point());
					lgoods.setState(goods.getState());
					break;
				}
			}*/
			LOG.info("# getTicket() 抽取到的商品号:{},userid:{}", goodsid,userid);
			/*if(lgoods==null){
				return WEI_ZHONG_JIANG;//5是谢谢参与
			}*/
			int goods_num=lgoods.getGoods_num();//获取奖品数量
			int get_num=lgoods.getGet_num();//已中奖品数量
			if(goods_num == get_num){
				LOG.info("# getTicket() 商品号:{}已无存货,返回未中奖,userid:{}", goodsid,userid);
				return WEI_ZHONG_JIANG;
			}
			//如果不为0 则已中奖品数量加1 //更新奖品表中的领取数量
			LotteryGoods lotterygoods =new LotteryGoods();
			lotterygoods.setGet_num(1);
			lotterygoods.setGoods_id(lgoods.getGoods_id());
			String body = gson.toJson(lotterygoods);
			LOG.info("# getTicket() 更新奖品表,请求报文:{} ", body);
			String url =getServiceURL("emall/api/lottery/updategoods");
			String result = HttpUtils.doPost(url, body,null, 3, 10, Contants.CHARSET_UTF_8);
			LOG.info("# getTicket() 响应信息 result={}", result);
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String nowday=df2.format(new Date());
			//判断中奖商品时积分0    还是电子劵1  
			if(lgoods.getGoods_type()==0){
				if(lgoods.getGive_point()==100){
					goodsid="2";
				}else if(lgoods.getGive_point()==300){
					goodsid="3";
				}else if(lgoods.getGive_point()==500){
					goodsid="0";
				}
				//存积分表
				WalletPoints wp=new WalletPoints();
				wp.setUserid(userid);
				wp.setPointsio(0);//收入
				wp.setPoints(new BigDecimal(lgoods.getGive_point()));
				wp.setOrderdate(nowday);
				wp.setFuncode("JFCJ");//funcode 后续会修改
				wp.setDescribe("积分抽奖中"+lgoods.getGive_point()+"积分");
				String bodys = gson.toJson(wp);
				LOG.info("# getTicket() 插入积分明细,请求报文:{} ", bodys);
				String pointsurl =getServiceURL("emall/api/lottery/updatepoints");
				String pointres = HttpUtils.doPost(pointsurl,bodys, null, 3, 10, Contants.CHARSET_UTF_8);
				LOG.info("# getTicket() 响应信息 result={}", pointres);
				
			}else if(lgoods.getGoods_type()==1){
				goodsid="1";
				//如果是电子劵，就调用订单下单接口
				ReqOrder reqorder = new ReqOrder();
				String rpid = ReqUtils.getRpid("P");
				reqorder.setUserid(userid);
				reqorder.setRpid(rpid);
				reqorder.setReqdate(DateUtil.getReqDate());
				reqorder.setReqtime(DateUtil.getCurrrentReqTime());
				reqorder.setGoodsnum(1);
				reqorder.setProductid(lgoods.getGoods_id());
				reqorder.setSource(Contants.ORDER_SOURCE_LOTTERY);
				reqorder.setPoints(0L);//0
				reqorder.setPaytype(Contants.PAYTYPE_POINTS);
				reqorder.setTelphone(mobileid);
				//查商品详细信息
				String baseurl = getServiceDomain();
				String orderurl = baseurl + "/emall/api/order/lotteryorder";
				String orderbody = gson.toJson(reqorder);
				LOG.info("#getTicket() 平台内部下单,请求报文:{} ", orderbody);
				String resultJson = HttpUtils.doPost(orderurl, orderbody, null, 3, 10, Contants.CHARSET_UTF_8);
				LOG.info("#getTicket() 平台内部下单,响应报文:{} ", resultJson);
				Iresp_common resp = gson.fromJson(resultJson, Iresp_common.class);
				if(resp == null || !"0000".equals(resp.getRet())){
					LOG.info("#getTicket() 平台内部下单,下单失败:{} ", body);
					return WEI_ZHONG_JIANG;
				}
				trace=resp.getMsg();
			}
			//存入用户中奖表
			LotteryInf lf = new LotteryInf();
			lf.setUserid(userid);
			lf.setMobileid(mobileid);
			lf.setProductid(lgoods.getGoods_id());
			lf.setGoodsname(lgoods.getGoods_name());
			lf.setTrace(trace);
			String lfbody = gson.toJson(lf);
			LOG.info("# getTicket() 插入用户中奖表,请求报文:{} ", lfbody);
			String lfurl =getServiceURL("emall/api/lottery/insertlotteryinf");
			String lfres = HttpUtils.doPost(lfurl,lfbody, null, 3, 10, Contants.CHARSET_UTF_8);
			LOG.info("# getTicket() 响应信息 result={}", lfres);
			
			return goodsid+trace;
	
		}catch (Exception e) {
			LOG.error("# getTicket() 处理抽奖时,发生异常,返回未中奖,userid:{}", userid, e);
			return WEI_ZHONG_JIANG;
		}
	}
	/**
	 * ********************************************
	 * method name   : receive 
	 * description   : 领取电子劵
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-14  下午8:12:32
	 * @see          : 
	 * ******************************************* 
	 */
	@LoginValid
	@GetMapping(value="/receive.htm")
	public void receive(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userid = (String)request.getAttribute(Contants.USERID);
		String trace = request.getParameter("trace");
		String baseurl = getServiceDomain();
		String orderurl = baseurl + "/emall/api/card/hsfcard/"+trace;
		String resultJson = HttpUtils.doGet(orderurl, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#getTicket() 调用发货接口,响应报文:{} ", resultJson);
		Iresp_common resp = gson.fromJson(resultJson, Iresp_common.class);
		
		Iresp_common resp2 = new  Iresp_common();
		if(resp == null || !"0000".equals(resp.getRet())){
			resp2.setRet("fail");
			resp2.setMsg("领取失败");
			String content = gson.toJson(resp2);
			super.write(response, content);
			LOG.info("#getTicket() 发货失败 ");
		}else {
			//领取成功后更新领取时间
			Map<String, String>   map= new HashMap<String, String>();
			map.put("userid", userid);
			map.put("trace", trace);
			map.put("receivestate", "1");
			String bodys = gson.toJson(map);
			String baseurls = getServiceDomain();
			String lourl = baseurls + "/emall/api/lottery/modify";
			String resultJsons = HttpUtils.doPost(lourl, bodys,null, 3, 10, Contants.CHARSET_UTF_8);
			LOG.info("#getTicket() 更新奖品领取时间,响应报文:{} ", resultJson);
			resp2.setRet("success");
			resp2.setMsg("领取成功");
			String content = gson.toJson(resp2);
			super.write(response, content);
			LOG.info("#getTicket() 发货成功 ");
		}
	}
	/**
	 * ********************************************
	 * method name   : lotteryinfo 
	 * description   : 中奖记录展示
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-14  下午8:12:32
	 * @see          : 
	 * ******************************************* 
	 */
	@LoginValid
	@GetMapping(value="/lotteryinfo.htm")
	public String lotteryinfo(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userid = (String)request.getAttribute(Contants.USERID);
	//	String userid="U0000000115";
		//传会员编号
		Map<String, String>   map= new HashMap<String, String>();
		map.put("userid", userid);
		String body = gson.toJson(map);
		String baseurl = getServiceDomain();
		String orderurl = baseurl + "/emall/api/lottery/lotteryuserinf/";
		String resultJson = HttpUtils.doPost(orderurl, body,null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#lotteryinfo() 查询奖品信息,响应报文:{} ", resultJson);
		//用于返回请求
        List<LotteryInf> lotteryList=new ArrayList<LotteryInf>();
        List<LotteryInf> lotteryList2=new ArrayList<LotteryInf>();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		if(!StringUtil.isEmpty(resultJson)){
			//返回请求
			lotteryList = gson.fromJson(resultJson, new TypeToken<List<LotteryInf>>(){}.getType());
			for(LotteryInf li:lotteryList){
				Long losetime=Long.parseLong(df.format(li.getLosetime()));
				String intime=df2.format(li.getIntime());
				li.setIntime(Timestamp.valueOf(intime));
				li.setIntimeStr(intime);
				li.setLosetimeStr(losetime);
				lotteryList2.add(li);
			}
		}
		Long date = Long.parseLong(df.format(new Date()));
		modelMap.addAttribute("date", date);//获取当前时间
		modelMap.addAttribute("size", lotteryList2.size());//list长度
		modelMap.addAttribute("lotteryList", lotteryList2);//等值金额
		return "lottery/prize_list";
	}
	
}
