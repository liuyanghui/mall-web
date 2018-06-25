package com.mall.controller.web.wallet;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.reflect.TypeToken;
import com.mall.annotation.LoginValid;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.EcardInf;
import com.mall.controller.web.vo.WalletPoints;
import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;
import com.mall.util.RedisPool;
import com.mall.util.StringUtil;


/**
 * ******************  类说明  *********************
 * class       :  WalletCtl
 * @author     :  zhurouyu
 * @version    :  1.0  
 * description :  积分钱包视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/wallet")
public class WalletCtl extends BaseCtl{
	private static final Logger LOG = LoggerFactory.getLogger(WalletCtl.class);
	/**
	 * ********************************************
	 * method name   : walletdex 
	 * description   : 进入"积分钱包"页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-12  下午6:12:32
	 * @see          : 
	 * ******************************************* 
	 */
	@LoginValid
	@GetMapping(value="/index.htm")
	public String walletdex(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userid = (String)request.getAttribute(Contants.USERID);
		JSONObject memberInfo =  (JSONObject)request.getAttribute(Contants.LOGIN_TOKEN);
		//获取当前年月
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		String nowTime=df.format(new Date());
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String userurl =getServiceURL("/emall/api/member/detail/"+userid);
		String userres = HttpUtils.doGet(userurl,null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("# walletdex() 响应信息 result={}", userres);
		if(StringUtil.isEmptyTrim(userres)){
			return "404";
		}
		Map userJson = gson.fromJson(userres, new TypeToken<Map<String, Object>>() {}.getType());
		if(userJson != null){
			//获取用户的积分信息
			BigDecimal points=new BigDecimal((Double)userJson.get("points"));
			modelMap.addAttribute("sumPoints", points);
			//从redis中获取现金和积分的兑换值   
			String pointPer=RedisPool.get("points.ratio");
			//等值兑换
			double percent=Double.parseDouble(pointPer);
			 //保留2位小数
			DecimalFormat douf =new DecimalFormat("#0.00"); 
			String amount="0.00";
			amount=douf.format(points.doubleValue()/percent);
			
			modelMap.addAttribute("amount", amount);//等值金额
		}else{
			modelMap.addAttribute("sumPoints", 0);
		}
		
		//用于返回请求
        List<WalletPoints> walletList=new ArrayList<WalletPoints>();
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		Map<String, Object> toMap = new HashMap<String, Object>();
		//传会员编号
		toMap.put("userid", userid);
		String url = baseurl + "/emall/api/wallet/points";
		String body = gson.toJson(toMap);
		String result = HttpUtils.doPost(url, body, null,  3, 10, "utf-8");
		LOG.info("响应结果: {}" ,result);
		
		if(!StringUtil.isEmpty(result)){
			//返回请求
			walletList = gson.fromJson(result, new TypeToken<List<WalletPoints>>(){}.getType());
			//收入income 0  支出 expend 1
			int incomePoints=0,expendPoints=0;
			if(walletList != null){
				//获取积分信息
				for(WalletPoints wallets : walletList){
					int pointsio=wallets.getPointsio();
					BigDecimal points =wallets.getPoints();
					if(pointsio==0){
						incomePoints=incomePoints+points.intValue();
					}else{
						expendPoints=expendPoints+points.intValue();
					}
				}
				
			}
			
		}
		
		modelMap.addAttribute("userid", userid);//用户id
		return "wallet/index";
	}
	/**
	 * ********************************************
	 * method name   : detail 
	 * description   : "积分钱包"--"积分交易明细"
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-12  下午8:12:32
	 * @see          : 
	 * ******************************************* 
	 */
	@LoginValid
	@GetMapping(value="/detail/{date}.htm")
	public String detail(ModelMap modelMap,@PathVariable("date") String date,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userid = (String)request.getAttribute(Contants.USERID);
		//获取当前年月
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowTime=df.format(new Date());
		//请求参数
		Map<String, Object> toMap = new HashMap<String, Object>();
		//传过来的登陆人
		toMap.put("userid", userid);
		//查询时间
		String queryDate="";
		String lastday = "";
		String pointsDate="";
		String time = "";
		//如果没有date，就查询当前月份的积分信息
		if("date".equals(date)||nowTime.equals(date)){
			queryDate=df2.format(new Date());// new Date()为获取当前系统时间
			String year=nowTime.substring(0, 4);
			String month=nowTime.substring(4, 6);
			//获取当前月的第一天
			toMap.put("_start_time", year+"-"+month+"-01 00:00:00");
			toMap.put("_end_time", queryDate);
			pointsDate=year+"年"+month+"月";
			time = year+"-"+month;
		}
		else{
			String year=date.substring(0, 4);
			String month=date.substring(4, 6);
			lastday = this.getLastDayOfMonth(year, month);
			toMap.put("_start_time", year+"-"+month+"-01 00:00:00");
			toMap.put("_end_time", lastday);
			pointsDate=year+"年"+month+"月";
			time = year+"-"+month;
		}
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		//用于返回请求
        List<WalletPoints> walletList=new ArrayList<WalletPoints>();
		
		String url = baseurl + "/emall/api/wallet/points";
		String body = gson.toJson(toMap);
		String result = HttpUtils.doPost(url, body.toString(), null,  3, 10, "utf-8");
		LOG.info("响应结果: {}" , result);
		//收入income 0  支出 expend 1
		int incomePoints=0,expendPoints=0;
		 List<WalletPoints> walletList2=new ArrayList<WalletPoints>();
		if(!StringUtil.isEmpty(result)){
			//返回请求
			walletList = gson.fromJson(result, new TypeToken<List<WalletPoints>>(){}.getType());
			if(walletList != null){
				for(WalletPoints wallets:walletList){
					int pointsio=wallets.getPointsio();
					BigDecimal points =wallets.getPoints();
					String intime=df2.format(wallets.getIntime());
					wallets.setIntimeStr(intime);
					if(pointsio==0){
						incomePoints=incomePoints+points.intValue();
					}else{
						expendPoints=expendPoints+points.intValue();
					}
					walletList2.add(wallets);
				}
			}
		}
		
		
		modelMap.addAttribute("date",time);
		modelMap.addAttribute("incomePoints",incomePoints);//收入积分
		modelMap.addAttribute("expendPoints",expendPoints);//支出积分
		modelMap.addAttribute("pointsDate",pointsDate);//积分支出明细月份
		modelMap.addAttribute("walletList",walletList2);//积分支出明细
		return "wallet/points";
	}
	/**
	 * ********************************************
	 * method name   : card 
	 * description   : "积分钱包"--"我的卡劵"
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-14  下午8:12:32
	 * @see          : 
	 * ******************************************* 
	 */
	@LoginValid
	@GetMapping(value="/ecard.htm")
	public String ecard(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userid = (String)request.getAttribute(Contants.USERID);
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		//用于返回请求
        List<EcardInf> ecardList=new ArrayList<EcardInf>();
		Map<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("userid", userid);
		String url = baseurl + "/emall/api/wallet/ecard";
		String body = gson.toJson(toMap);
		String result = HttpUtils.doPost(url, body, null,  3, 10, "utf-8");
		LOG.info("响应结果:{}" ,result);
	
		if(!StringUtil.isEmpty(result)){
			//返回请求
			ecardList = gson.fromJson(result, new TypeToken<List<EcardInf>>(){}.getType());
		}
		//放置查询数据的集合大小
		modelMap.addAttribute("size", ecardList==null?0:ecardList.size());
		modelMap.addAttribute("ecardList",ecardList);
		return "wallet/ecard";
	}
	/**
	 * ********************************************
	 * method name   : pointsExplain 
	 * description   : 积分说明
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * modified      : zhurouyu ,  2017年4月14日  上午9:38:00
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value="/explain.htm")
	public String pointsExplain(ModelMap modelMap){
		return "wallet/point_note";
	}
	/**
	 * ********************************************
	 * method name   : getDay 
	 * description   : 获取指定月份的第一天和最后一天
	 * @return       : String
	 * @param        : @param year month
	 * @param        : @return
	 * modified      : zhurouyu ,  2017年4月14日  上午9:38:00
	 * @see          : 
	 * *******************************************
	 */
	public String getLastDayOfMonth(String year, String month) {
		 
		  Calendar cal = Calendar.getInstance();
		  cal.set(Calendar.YEAR, Integer.parseInt(year));
		  cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
		  cal.set(Calendar.DAY_OF_MONTH, 1);
		  int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		  cal.set(Calendar.DAY_OF_MONTH, value);
		  return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(cal.getTime());
		 }
}
