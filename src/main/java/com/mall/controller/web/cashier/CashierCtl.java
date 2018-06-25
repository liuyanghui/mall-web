package com.mall.controller.web.cashier;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.mall.annotation.LoginValid;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.Address;
import com.mall.controller.web.vo.Goods;
import com.mall.controller.web.vo.GoodsArray;
import com.mall.controller.web.vo.GoodsPrice;
import com.mall.controller.web.vo.Iresp_common;
import com.mall.controller.web.vo.Member;
import com.mall.controller.web.vo.OrderVo;
import com.mall.controller.web.vo.ReqOrder;
import com.mall.controller.web.vo.WXPay;
import com.mall.util.DateUtil;
import com.mall.util.Digests;
import com.mall.util.HttpUtils;
import com.mall.util.MoneyUtil;
import com.mall.util.PropertiesUtils;
import com.mall.util.PublicKeyMap;
import com.mall.util.RSAUtils;
import com.mall.util.RedisPool;
import com.mall.util.ReqUtils;
import com.mall.util.StringUtil;

/**
 * ******************  类说明  *********************
 * class       :  CashierCtl
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  "收银台"视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/cashier")
public class CashierCtl extends BaseCtl {
	
	private static final Logger LOG = LoggerFactory.getLogger(CashierCtl.class);
	
	/**
	 * ********************************************
	 * method name   : topay 
	 * description   : 点击"去支付",展示确认支付页面
	 * @return       : String
	 * @param        : @param request
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dmm ,  2017-4-16  上午10:28:17
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/topay.htm")
	public String topay(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{
		String userid = (String) request.getAttribute(Contants.USERID);
//		String userid="U0000000069";//TODO
		if(StringUtil.isEmptyTrim(userid)){
			return "404";
		}
		String trace = request.getParameter("t");
		LOG.info("#topay() 确认订单,trace={}", trace);

		if(StringUtil.isEmptyTrim(trace)){
			return "404";
		}
		//页面需要展示商品名称,商品中图,订单支付方式,支付的积分和现金
		//订单支付方式,应付金额,应付积分在大订单表
		//商品图,名称在订单商品表
		String baseurl = getServiceDomain();
		String url = baseurl + "/emall/api/order/querydetail/" + userid + "/" + trace;
		String resultJson = HttpUtils.doGet(url, null, 3, 10, "utf-8");

		if(StringUtil.isEmptyTrim(resultJson)){
			return "404";
		}
		OrderVo order = gson.fromJson(resultJson, new TypeToken<OrderVo>() {}.getType());
		modelMap.addAttribute("order", order);
		
		//查用户的总积分(积分+冻结)
		String getUserUrl = baseurl + "/emall/api/member/detail/" + userid;
		String userJson = HttpUtils.doGet(getUserUrl, null, 3, 10, "utf-8");

		if(StringUtil.isEmptyTrim(userJson)){
			return "404";
		}
		Member member = gson.fromJson(userJson, new TypeToken<Member>() {}.getType());
		BigDecimal leftpoints = MoneyUtil.add(member.getFreeze_points(),member.getPoints());
		
		//支付金额,单位:元
		String price = order.getReal_amount_yuan();
		//支付积分,不带小数点
		BigDecimal points = order.getPoints();
		
		if("points".equals(order.getPaytype())){
			//积分支付
			//商品支付积分
			modelMap.addAttribute("points", points);
			//支付积分/100,转为元的
			modelMap.addAttribute("points2yuan", MoneyUtil.getMoneyStr(MoneyUtil.Cent2Dollar(order.getPoints())));
			modelMap.addAttribute("process", getProcess(order.getPoints(), leftpoints));

		}else if("cash".equals(order.getPaytype())){
			//现金支付
			//商品支付金额
			modelMap.addAttribute("price", price);
		}else{
			//现金+积分组合支付
			//商品支付金额
			modelMap.addAttribute("price", price);
			//商品支付积分
			modelMap.addAttribute("points", points);
			//支付积分/100,转为元的
			modelMap.addAttribute("points2yuan", MoneyUtil.getMoneyStr(MoneyUtil.Cent2Dollar(order.getPoints())));
			modelMap.addAttribute("process", getProcess(order.getPoints(), leftpoints));
			
		}
		
		if("points".equals(order.getPaytype())
				|| "comb".equals(order.getPaytype())){
			//输入积分密码时使用
			PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
			modelMap.addAttribute("module", publicKeyMap.getModulus());
			modelMap.addAttribute("empoent", publicKeyMap.getExponent());
		}
		
		//用户的剩余积分数量
		modelMap.addAttribute("leftpoints", leftpoints.intValue());
		modelMap.addAttribute("staticDomain", PropertiesUtils.getProperty("static.domain"));
		//用户是否设置了积分支付密码
		modelMap.addAttribute("hasPin", !StringUtil.isEmptyTrim(member.getPin()));
		modelMap.addAttribute("trace", trace);
		return "cashier/pay_" + order.getPaytype();
	}
	
	private BigDecimal getProcess(BigDecimal points, BigDecimal leftpoints){
		return MoneyUtil.divide(points.multiply(new BigDecimal("100")), leftpoints);
	}
	/**
	 * ********************************************
	 * method name   : confirmpay 
	 * description   : 点击确认支付按钮
	 * @return       : String
	 * @param        : @param request
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dmm ,  2017-4-16  上午10:14:08
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/confirmpay.htm")
	public void confirmpay(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception{
		String userid = (String) request.getAttribute(Contants.USERID);
//		String userid="U0000000069";//TODO
		if(StringUtil.isEmptyTrim(userid)){
			String content = gson.toJson(new Iresp_common("1111", "登录超时"));
			super.write(response, content);
			return;
		}
		String trace = request.getParameter("t");
		LOG.info("#confirmpay() 支付订单,trace={},userid={}", trace, userid);

		//更新实付积分,加积分明细,用户冻结积分扣减;//更新订单的实际支付金额
		String baseurl = getServiceDomain();
		String url = baseurl + "/emall/api/order/confirmpay/" + trace + "/" + userid;
		String resultJson = HttpUtils.doGet(url, null, 3, 10, "utf-8");

		if(StringUtil.isEmptyTrim(resultJson)){
			LOG.info("#confirmpay() 支付订单,返回为空,trace={}", trace);
			String content = gson.toJson(new Iresp_common("9999", "系统异常"));
			super.write(response, content);
			return;
		}
		LOG.info("#confirmpay() 支付订单,返回:{},trace={}", resultJson, trace);

		//弹出积分支付密码框,让用户输入密码
		super.write(response, resultJson);
		return;
	}
	
	/**
	 * ********************************************
	 * method name   : pointspay 
	 * description   : 接收用户输入的密码,校验是否正确,纯积分支付,输入正确,则发货;
	 * 组合支付,向微信下单,返回微信下单需要的参数
	 * @return       : Map<String,String>
	 * @param        : @param request
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dmm ,  2017-4-17  下午5:40:42
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/pointspay.htm")
	public void pointspay(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{
		String userid = (String) request.getAttribute(Contants.USERID);//TODO
//		String userid="U0000000069";
		String content = null;
		if(StringUtil.isEmptyTrim(userid)){
			content = gson.toJson(new Iresp_common("1111", "登录超时"));
			super.write(response, content);
			return;
		}
		String trace = request.getParameter("t");
		LOG.info("#pointspay() 积分支付密码确认请求,trace={}", trace);
		
		 //获取密码
		String passwordPara = request.getParameter("pwd");
		if(StringUtil.isEmptyTrim(passwordPara)){
			LOG.info("#pointspay() 积分支付密码确认,传入的密码为空,trace={}", trace);
			content = gson.toJson(new Iresp_common("2222", "系统忙,请稍后再试"));
			super.write(response, content);
			return;
		}
		
		String baseurl = getServiceDomain();
		//根据订单查订单类型
		String getTraceUrl = baseurl + "/emall/api/order/query/" + trace + "/" + userid;
		String traceJson = HttpUtils.doGet(getTraceUrl, null, 3, 10, "utf-8");
		
		if(StringUtil.isEmptyTrim(traceJson)){
			LOG.info("#pointspay() 积分支付密码确认,用户订单信息返回为空,trace={}", trace);
			content = gson.toJson(new Iresp_common("2222", "系统忙,请稍后再试"));
			super.write(response, content);
			return;
		}
		//还原密码
		passwordPara = RSAUtils.decryptStringByJs(passwordPara);
		
		//查用户的支付密码
		String getUserUrl = baseurl + "/emall/api/member/detail/" + userid;
		String userJson = HttpUtils.doGet(getUserUrl, null, 3, 10, "utf-8");
		
		if(StringUtil.isEmptyTrim(userJson)){
			LOG.info("#pointspay() 积分支付密码确认,用户信息返回为空,trace={}", trace);
			content = gson.toJson(new Iresp_common("2222", "系统忙,请稍后再试"));
			super.write(response, content);
			return;
		}
		Member member = gson.fromJson(userJson, new TypeToken<Member>() {}.getType());
		String password = member.getPin();
		
		String passwordParaSha1 = Digests.sha1(passwordPara, PropertiesUtils.getProperty("password.key"));
		
		if(!password.equals(passwordParaSha1)){
			LOG.info("#pointspay() 积分支付密码确认,用户密码输入错误,trace={}", trace);
			content = gson.toJson(new Iresp_common("3333", "密码输入错误!"));
			super.write(response, content);
			return;
		}
		
		LOG.info("#pointspay() 积分支付密码确认,用户密码输入正确,trace={}", trace);
		
		OrderVo order = gson.fromJson(traceJson, new TypeToken<OrderVo>() {}.getType());
		String paytype = order.getPaytype();
		if(Contants.PAYTYPE_POINTS.equals(paytype)){
			LOG.info("#pointspay() 积分支付密码确认,此订单支付方式为:积分支付,trace={}", trace);

			//如果是纯积分支付,//调用后台通知接口相关逻辑 //TODO
			String notifyUrl = baseurl + "/emall/api/wxpay/payresult/" + trace;
			String notifyJson = HttpUtils.doGet(notifyUrl, null, 3, 10, "utf-8");
			if(StringUtil.isEmptyTrim(notifyJson)){
				LOG.info("#pointspay() 积分支付密码确认,通知支付结果返回为空,trace={}", trace);
				content = gson.toJson(new Iresp_common("2222", "系统忙,请稍后再试"));
				super.write(response, content);
				return;
			}
			super.write(response, notifyJson);
			return;
		}else if(Contants.PAYTYPE_COMB.equals(paytype)){
			LOG.info("#pointspay() 积分支付密码确认,此订单支付方式为:组合支付,trace={}", trace);
			//如果是含现金支付,向微信下单,组装微信需要的参数,js请求微信
			//传ip
			WXPay wxpay = new WXPay();
			wxpay.setOpenid(member.getOpenid());
			wxpay.setOut_trade_no(trace);
			wxpay.setSpbill_create_ip(getClientIpAddr(request));
			wxpay.setTrade_type("JSAPI");
			wxpay.setNotify_url(PropertiesUtils.getProperty("wxpay.back.notify.url"));
			
			String reqBody = gson.toJson(wxpay);
			String wxorderUrl = baseurl + "/emall/api/wxorder/createorder";
			String wxorderJson = HttpUtils.doPost(wxorderUrl, reqBody, null, 3, 10, "utf-8");
			if(StringUtil.isEmptyTrim(wxorderJson)){
				LOG.info("#pointspay() 积分支付密码确认,向微信下单返回为空,trace={}", trace);
				content = gson.toJson(new Iresp_common("2222", "系统忙,请稍后再试"));
				super.write(response, content);
				return;
			}
			LOG.info("#pointspay() 积分支付密码确认,向微信下单返回:{},trace={}", wxorderJson, trace);

			Map<String, Object> map = gson.fromJson(wxorderJson, new TypeToken<Map<String, Object>>() {}.getType());
			map.put("paytype", "wxpay");
			super.write(response, gson.toJson(map));
			return;
			
		}
	}
	
	/**
	 * ********************************************
	 * method name   : confirm 
	 * description   : 订单确认页
	 * @return       : String
	 * @param        : @param productid
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : xuhuafeng ,  2017-4-16  下午3:42:33
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/confirmorder/{productid}.htm")
	public String confirmOrder(HttpServletRequest request, HttpServletResponse response, @PathVariable("productid") String productid, ModelMap modelMap) throws Exception{
//		String userid="U0000000069";//TODO
		//获取登录信息
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String memberInfoJson = RedisPool.get(login_token);
		JSONObject memberInfo = JSONObject.fromObject(memberInfoJson);
		String mobileid = memberInfo.optString("mobileid");
		/*if(StringUtil.isBlank(mobileid)){
			PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
			modelMap.addAttribute("module", publicKeyMap.getModulus());
			modelMap.addAttribute("expoent", publicKeyMap.getExponent());
			return "member/bind";
		}*/
		modelMap.addAttribute("token", login_token);
		modelMap.addAttribute("mobileid", mobileid);
		modelMap.addAttribute("hideMobileid", memberInfo.optString("hidemobileid"));
		
		LOG.info("#confirmOrder() 确认订单,productid={}", productid);
		Integer goodsnum = 1;
		if(StringUtil.isEmptyTrim(productid)){
			return "404";
		}
		//查商品详细信息
		String baseurl = getServiceDomain();
		String url = baseurl + "/emall/api/goodsonline/" + productid;
		String resultJson = HttpUtils.doGet(url, null, 3, 10, "utf-8");

		if(StringUtil.isEmptyTrim(resultJson)){
			return "404";
		}
		
		Goods goods = gson.fromJson(resultJson, Goods.class);
		LOG.info("#confirmOrder() 确认订单,商品信息:{} ", gson.toJson(goods));
		if(goods == null || goods.getState().intValue() == 0){
			//商品下架
			return "404";
		}
		
		//获取商品信息和价格
		GoodsArray goodsArray = changeGoods(goods, goodsnum);
		if(goodsArray == null){
			return "404";
		}
		LOG.info("#confirmOrder() 确认订单,处理后的商品信息:{} ", gson.toJson(goodsArray));
		modelMap.addAttribute("goodsArray", goodsArray);
		
		//判断是实物还是虚拟商品
		modelMap.addAttribute("goodstype", goodsArray.getGoodstype());
		
		return "cashier/confirm";

	}
	/**
	 * ********************************************
	 * method name   : changeGoods 
	 * description   : bean转换
	 * @return       : GoodsArray
	 * @param        : @param goods
	 * @param        : @return
	 * modified      : xuhuafeng ,  2017-4-14  上午10:48:59
	 * *******************************************
	 */
	private GoodsArray changeGoods(Goods goods, Integer goodsnum){
		GoodsArray goodsArray = new GoodsArray();
		goodsArray.setProductid(goods.getProductid());
		goodsArray.setMerid(goods.getMerid());
		goodsArray.setGoodsid(goods.getGoodsid());
		goodsArray.setGoodsname(goods.getGoodsname());
		goodsArray.setGoodsnum(goodsnum);
		goodsArray.setGoodstype(goods.getGoodstype());
		goodsArray.setSmallpic(goods.getSmallpic());
		
		BigDecimal price = new BigDecimal(0);
		//处理价格
		List<GoodsPrice> goodsPriceList = goods.getGoodsPriceList();
		if(goodsPriceList == null){
			return null;
		}
		
		GoodsPrice goodsPrice = goodsPriceList.get(0);
		goodsArray.setPaytype(goodsPrice.getPaytype());
		BigDecimal zero = new BigDecimal(0);
		if(goodsPrice.getPoints() == null){
			goodsArray.setPoints(zero);
		}else{
			goodsArray.setPoints(goodsPrice.getPoints());
		}
		if(goodsPrice.getPrice() == null){
			price = zero;
		}else{
			price = goodsPrice.getPrice();
		}
		
		//订单总金额（积分+钱）
		goodsArray.setTotalpriceStr(MoneyUtil.getGoodsPriceComb(price, goodsArray.getPoints(), goodsArray.getGoodsnum(), goodsArray.getPaytype()));
		goodsArray.setPrice(MoneyUtil.Cent2Dollar(price));
		
		return goodsArray;
	}
	
	/**
	 * ********************************************
	 * method name   : getDefaultAddress 
	 * description   : 获取当前用户的默认收货地址
	 * @return       : Address
	 * @param        : @param userid
	 * @param        : @return
	 * modified      : xuhuafeng ,  2017-4-13  下午8:41:34
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/defaultaddress.htm")
	public @ResponseBody String getDefaultAddress(HttpServletRequest request, HttpServletResponse response){
		String userid = (String) request.getAttribute(Contants.USERID);
		String url = getServiceDomain() + "/emall/api/address/finddefault/" + userid;
		String resultJson = HttpUtils.doGet(url, null, 3, 10, "utf-8");
		if(resultJson == null || "null".equals(resultJson)){
			return null;
		}
		Address address = gson.fromJson(resultJson, Address.class);
		if(address == null){
			return null;
		}
		String mobileid = address.getMobileid();
		String hidemobileid = "";
		if(StringUtil.isNotBlank(mobileid)){
			hidemobileid = StringUtil.hideMobile(mobileid);
		}
		address.setHidemobileid(hidemobileid);
		return gson.toJson(address);
	}
	/**
	 * ********************************************
	 * method name   : apply 
	 * description   : 平台内部下单
	 * @return       : String
	 * @param        : @param request
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : xuhuafeng ,  2017-4-16  下午4:55:59
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/apply.htm")
	public @ResponseBody String apply(HttpServletRequest request, HttpServletResponse response, @RequestBody ReqOrder reqorder, ModelMap modelMap) throws Exception{
		String rpid = ReqUtils.getRpid("P");
		
		LOG.info("#apply() 平台内部下单,请求参数:{}", gson.toJson(reqorder));
		if(reqorder == null){
			return "404";
		}
		
		String userid = (String) request.getAttribute(Contants.USERID);
		
		reqorder.setUserid(userid);
		reqorder.setRpid(rpid);
		reqorder.setReqdate(DateUtil.getReqDate());
		reqorder.setReqtime(DateUtil.getCurrrentReqTime());
		reqorder.setGoodsnum(1);
		reqorder.setSource(Contants.ORDER_SOURCE_PAY);
		//查商品详细信息
		String baseurl = getServiceDomain();
		String url = baseurl + "/emall/api/order/apply/1.0";
		String body = gson.toJson(reqorder);
		LOG.info("#apply() 平台内部下单,请求报文:{} ", body);
		String resultJson = HttpUtils.doPost(url, body, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#apply() 平台内部下单,响应报文:{} ", resultJson);
		if(StringUtil.isEmptyTrim(resultJson)){
			return "404";
		}
		
		Iresp_common resp = gson.fromJson(resultJson, Iresp_common.class);
		if(resp == null || !"0000".equals(resp.getRet())){
			LOG.info("#apply() 平台内部下单,下单失败:{} ", body);
			resp = new Iresp_common("9999", "下单失败！");
			
		}
		
		return resultJson;

	}
	
	
	
}
