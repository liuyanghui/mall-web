/** *****************  JAVA头文件说明  ****************
 * file name  :  TestCtl.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-6
 * *************************************************/

package com.mall.controller.web.cashier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.mall.annotation.LoginValid;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.OrderVo;
import com.mall.util.DateUtil;
import com.mall.util.HttpUtils;
import com.mall.util.MoneyUtil;
import com.mall.util.PropertiesUtils;
import com.mall.util.StringUtil;

/**
 * ****************** 类说明 ********************* class : PayController
 * 
 * @author : liuyanghui@umpay.com
 * @version : 1.0 description :
 * @see : ***********************************************
 */
@Controller
@RequestMapping("/web/pay")
public class PayController extends BaseCtl {
	private static final Logger LOG = LoggerFactory.getLogger(PayController.class);

	/**
	 * ********************************************
	 * method name   : result 
	 * description   : 支付结果展示
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dmm ,  2017-4-21  下午4:19:13
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value = "/result.htm")
	public String result(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//获取用户userid
		String userid = (String) request.getAttribute(Contants.USERID);
		//根据userid和订单号查订单明细
		String trace = request.getParameter("t");
//		String userid = "10086";//TODO
		
		OrderVo order = getOrder(trace, userid);
		
		modelMap.addAttribute("order", order);
		
		return "cashier/payresult";
	}
	
	private OrderVo getOrder(String trace, String userid){
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/order/query/"+trace+"/"+userid;
		String resultJson = HttpUtils.doGet(url, null, 3, 10, "utf-8");
		if(StringUtil.isEmptyTrim(resultJson)){
			return null;
		}
		OrderVo order = gson.fromJson(resultJson, new TypeToken<OrderVo>() {}.getType());
		
		if(order.getPoints() != null){
			order.setPoints(MoneyUtil.fixMoney(order.getPoints(),0));
		}
		if(order.getAmount() != null){
			order.setAmount(MoneyUtil.Cent2Dollar(order.getAmount()));
		}
		order.setIntimestr(DateUtil.getLongDateStr(order.getIntime()));
		return order;
	}
	
	/**
	 * ********************************************
	 * method name   : wxorder 
	 * description   : 微信支付前台通知地址
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @param trace
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dmm ,  2017-4-21  下午5:33:52
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value = "/wx/{trace}.htm")
	public String wxorder(ModelMap modelMap, HttpServletRequest request
			, @PathVariable("trace") String trace) throws Exception {
		//获取用户userid
		String userid = (String) request.getAttribute(Contants.USERID);
		String result = request.getParameter("result");
		
		OrderVo order = getOrder(trace, userid);
		
		modelMap.addAttribute("order", order);
		if("get_brand_wcpay_request:ok".equals(result)){
			//成功
			if(order != null){
				order.setPaystatus((short)2);
			}
		}
		
		modelMap.addAttribute("order", order);
		
		return "cashier/payresult";
	}
	
	@RequestMapping(value = "/wxpay", method = RequestMethod.GET)
	public ModelAndView getPic(ModelMap modelMap) throws Exception {
		String appid = PropertiesUtils.getProperty("appid");
		String mch_id = PropertiesUtils.getProperty("mch_id");
		String nonce_str = create_nonce_str();
		String body = "商品描述";
		// 商品价格
		int total_fee = 1;
		// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
		String spbill_create_ip = "";
		// 商户订单号
		String out_trade_no = "123123123213";
		// 回调通知
		String notify_url = "";
		// openid
		String openid = "oXgHAt8XxHAjaWUFd3n2HB2Wm2H0";// liuyanghui 途说openid
		String url = PropertiesUtils.getProperty("mall.service.url");
		Map<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("appid", appid);
		toMap.put("mch_id", mch_id);
		toMap.put("nonce_str", nonce_str);
		toMap.put("body", body);
		toMap.put("total_fee", total_fee);
		toMap.put("spbill_create_ip", spbill_create_ip);
		toMap.put("out_trade_no", out_trade_no);
		toMap.put("notify_url", notify_url);
		toMap.put("trade_type", "JSAPI");
		toMap.put("openid", openid);
		url = url + "/emall/api/pay/wxpay";
		String[][] headers = { { "Content-Type", "application/json" } };
		String bodys = mapTojson(toMap);
		String jsonString = HttpUtils.doPost(url, bodys, headers, 3, 10, "utf-8");
		System.out.println(jsonString);
		return new ModelAndView("wxpay");
	}

	/**
	 * ******************************************** method name : mapTojson
	 * description :
	 * 
	 * @return : String
	 * @param : @param map
	 * @param : @return modified : liuyanghui@umpay.com , 2017年4月12日 上午10:28:00
	 * @see : *******************************************
	 */
	public static String mapTojson(Map<String, Object> map) {
		JSONObject json = new JSONObject();
		json.put("appid", map.get("appid"));
		json.put("mch_id", map.get("mch_id"));
		json.put("nonce_str", map.get("nonce_str"));
		json.put("body", map.get("body"));
		json.put("total_fee", map.get("total_fee"));
		json.put("spbill_create_ip", map.get("spbill_create_ip"));
		json.put("out_trade_no", map.get("out_trade_no"));
		json.put("notify_url", map.get("notify_url"));
		json.put("trade_type", map.get("trade_type"));
		json.put("openid", map.get("openid"));
		return json.toString();
	}

	public static String getCode() {
		String appid = PropertiesUtils.getProperty("appid");
		// 请求微信地址
		String getCodeUrl = PropertiesUtils.getProperty("get.code.url");
		// 回调地址
		String redirect_uri = PropertiesUtils.getProperty("redirect_uri");
		String requestUrl = redirect_uri + "appid=" + appid + "" + "&redirect_uri=" + redirect_uri + "response_type=code&snsapi_base&state=123#wechat_redirect";
		JSONObject str = HttpUtils.httpRequest(requestUrl, "GET", null);
		System.out.println(str.toString());
		return requestUrl;

	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	public static void main(String[] args) {
	System.out.println(create_timestamp());
	}
}
