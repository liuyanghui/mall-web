/** *****************  JAVA头文件说明  ****************
 * file name  :  TestCtl.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-6
 * *************************************************/

package com.mall.controller.web.card;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mall.controller.web.base.BaseCtl;
import com.mall.util.DateTimeUtil;
import com.mall.util.DateUtil;
import com.mall.util.HttpUtils;
import com.mall.util.ProceMsgProxy;
import com.mall.util.PropertiesUtils;

/**
 * ****************** 类说明 ********************* class : CardController
 * 
 * @author : liuyanghui@umpay.com
 * @version : 1.0 description :
 * @see : ***********************************************
 */
@Controller
@RequestMapping("/web/card")
public class CardController extends BaseCtl{
	private static final Logger LOG = LoggerFactory.getLogger(CardController.class);

	@RequestMapping(value = "/getIssueno", method = RequestMethod.GET)
	public ModelAndView getIssueno(ModelMap modelMap) throws Exception {
		// 5800000000000010
		// 提货券，
		// 1100000000000011
		// 京东不发短信，1100000000000012
		// 京东发短信，
		// 1100000000000013
		// 非京东直接获取不发短信一元
		// ，1100000000000015
		// 非京东发短信一元券码
		String userid = "123";
		String url = PropertiesUtils.getProperty("mall.service.url");
		Map<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("userid", userid);
		toMap.put("funcode", "QM1004");
		toMap.put("merid", "99960001");
		toMap.put("coupid", "5800000000000010");
		toMap.put("count", "1");
		toMap.put("orderid", DateUtil.getDateTime16String());
		toMap.put("orderdate", DateTimeUtil.getDateString());
		toMap.put("channel", "04");
		toMap.put("mediano", "1820141187");
		toMap.put("mediatype", "00");
		url = url + "/emall/api/card/hsfCard";
		String[][] headers = { { "Content-Type", "application/json" } };

		JSONObject body = JSONObject.fromObject(toMap);
		String jsonString = HttpUtils.doPost(url, body.toString(), headers, 3, 10, "utf-8");
		LOG.info("service返回参数{}",jsonString);
		return new ModelAndView("wxpay");
	}

	public static void main(String[] args) {

		// 5800000000000010
		// 提货券，
		// 1100000000000011
		// 京东不发短信，1100000000000012
		// 京东发短信，
		// 1100000000000013
		// 非京东直接获取不发短信一元
		// ，1100000000000015
		// 非京东发短信一元券码
		String userid = "123";
		String url = PropertiesUtils.getProperty("mall.service.url");
		Map<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("userid", userid);
		toMap.put("funcode", "QM1004");
		toMap.put("merid", "99960001");
		toMap.put("coupid", "5800000000000010");
		toMap.put("count", "1");
		toMap.put("orderid", DateUtil.getDateTime16String());
		toMap.put("orderdate", DateTimeUtil.getDateString());
		toMap.put("channel", "04");
		toMap.put("mediano", "1820141187");
		toMap.put("mediatype", "00");
		url = url + "/emall/api/card/hsfCard";
		String[][] headers = { { "Content-Type", "application/json" } };

		JSONObject body = JSONObject.fromObject(toMap);
		String jsonString = HttpUtils.doPost(url, body.toString(), headers, 3, 10, "utf-8");
		LOG.info("service返回参数{}",jsonString);
	
	
	}
	/**
	 * ********************************************
	 * method name   : getAsyIssueno 
	 * description   : 卡券回调通知
	 * @return       : void
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @throws Exception
	 * modified      : liuyanghui@umpay.com ,  2017年4月17日  上午11:16:21
	 * @see          : 
	 * *******************************************
	 */
	@RequestMapping(value = "/getAsyIssueno", method = RequestMethod.POST)
	public void getAsyIssueno(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOG.info("#getAsyIssueno() 卡券回调通知：");
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		} 
		URLDecoder.decode(sb.toString(), "UTF-8");
		String xmlStr = new String(sb.toString().getBytes("utf8"), "utf8");
		LOG.info("#getAsyIssueno() 接受到返回内容:{}",xmlStr);
		String url = PropertiesUtils.getProperty("mall.service.url");
		Map<String, Object> reqMap = ProceMsgProxy.getMessageXml2Map(xmlStr, false);
		Map<String, Object> toMap = new HashMap<String, Object>();
		url = url + "/emall/api/card/hsfCardYBTZ";
		String[][] headers = { { "Content-Type", "application/json" } };
		toMap.put("reqStr", xmlStr);
		JSONObject body = JSONObject.fromObject(toMap);
		String jsonString = HttpUtils.doPost(url, body.toString(), headers, 3, 10, "utf-8");
		LOG.info("#getAsyIssueno() 接受到service返回内容:{}", jsonString);
		super.write(response, jsonString,"application/xml");
	}


	/**
	 * 
	 * @data 2017年4月10日下午4:18:29
	 * @return ModelAndView
	 * @auther liuyanghui
	 * @desc: 异步获取券码
	 */
	public static String JUPost(String url, String message) throws Exception {
		LOG.info("请求地址：" + url);
		LOG.info("请求内容：" + message);
		//
		URL urls = new URL(url);
		HttpURLConnection huc = null;
		byte[] req = message.getBytes("UTF-8");
		// byte[] req = message.getBytes("gb2312");
		huc = openURL(urls);
		huc.setRequestProperty("Content-Length", String.valueOf(req.length));
		OutputStream out = huc.getOutputStream();
		out.write(req);
		out.flush();
		out.close();
		InputStream is = huc.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "gbk"));
		StringBuffer sb = new StringBuffer();
		String line = "";

		while ((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		System.out.println("响应:" + new String(sb.toString().getBytes("GBK"), "UTF-8"));
		return new String(sb.toString().getBytes("GBK"), "UTF-8");
	}

	/**
	 * 请求设置
	 */
	public static HttpURLConnection openURL(URL url) throws Exception {
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("POST");
		huc.setRequestProperty("Content-Type", "application/xml");
		huc.setConnectTimeout(30 * 1000);
		huc.setReadTimeout(60 * 1000);
		huc.setDoOutput(true);
		huc.setDoInput(true);
		huc.setRequestProperty("Accept-Charset", "UTF-8");
		return huc;
	}

	/**
	 * 
	 * @data 2017年4月7日下午1:58:21
	 * @return String
	 * @auther liuyanghui
	 * @desc: map 转 json
	 */
	public static String mapTojson(Map<String, Object> map) {
		JSONObject json = new JSONObject();
		json.put("userid", map.get("userid"));
		json.put("funcode", map.get("funcode"));
		json.put("merid", map.get("merid"));
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
}
