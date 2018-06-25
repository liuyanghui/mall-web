package com.mall.controller.web.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.reflect.TypeToken;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;
import com.mall.util.ReqUtils;
import com.mall.util.StringUtil;
import com.mall.util.XmlJSONUtils;

@Controller
@RequestMapping("/wxpay")
public class WeixinPayCtl extends BaseCtl {
	private static final Logger log = LoggerFactory.getLogger(WeixinPayCtl.class);
	
	private static final String WX_ORDER_PREFIX_RPID = "W";

	/**
	 * ********************************************
	 * method name   : result 
	 * description   : 微信后台结果通知
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dmm ,  2017-4-15  下午10:35:42
	 * @see          : 
	 * *******************************************
	 */
	@RequestMapping(value = "/notify.htm")
	public void notify(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		final String rpid = ReqUtils.getRpid(WX_ORDER_PREFIX_RPID);
		String clientip = ReqUtils.getClientIpAddr(request);
		String requestParameters = super.read(request, false);
		log.info("#notify() Rpid:{},IP地址:{},微信支付结果通知请求参数:{}", rpid, clientip, requestParameters);
		if (StringUtil.isBlank(requestParameters)){
			log.info("#notify() Rpid:{},请求参数为空!", rpid);
			String resp = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[The request params is empty!]]></return_msg></xml>";
			super.write(response, resp, CONTENT_TYPE_APPLICATION_HTML);
			return;
		}
		// 解析请求参数
		final String parametersJson = XmlJSONUtils.xml2JSON(requestParameters);
		Map<String, String> wx_pay_notify_req_map = gson.fromJson(parametersJson, 
				new TypeToken<Map<String, String>>(){}.getType());
		log.debug("#notify() Rpid:{},请求参数解析结果:{}", rpid, wx_pay_notify_req_map);
		if ((null == wx_pay_notify_req_map) || (0 == wx_pay_notify_req_map.size())){
			log.info("#nofity() Rpid:{},请求参数为空!", rpid);
			String resp = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[The request params is empty!]]></return_msg></xml>";
			super.write(response, resp, CONTENT_TYPE_APPLICATION_HTML);
			return;
		}
		// 请求参数签名验证
		String return_code = wx_pay_notify_req_map.get(Contants.WX_PAY_NOTIFY_RETURN_CODE);
		String result_code = wx_pay_notify_req_map.get(Contants.WX_PAY_NOTIFY_RESULT_CODE);
		if (!"SUCCESS".equals(return_code) && !"SUCCESS".equals(result_code)){
			log.info("#notify() Rpid:{},微信支付失败:{}", rpid, parametersJson);
			String resp = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
			super.write(response, resp, CONTENT_TYPE_APPLICATION_HTML);
			return;
		}
		String md5key = PropertiesUtils.getProperty("weixin.pay.md5key", "");
		boolean result = ReqUtils.verifySignWithMD5(wx_pay_notify_req_map, md5key);
		if (!result){
			log.info("#notify() Rpid:{},签名验证失败", rpid);
			String resp = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[The sign check failed!]]></return_msg></xml>";
			super.write(response, resp, CONTENT_TYPE_APPLICATION_HTML);
			return;
		}
		
		//通知后台支付成功（发货/退款）
		new Thread(){
			@Override
			public void run(){
				final String url = getServiceURL("emall/api/wxpay/notify");
				log.info("#notify() Rpid:{},微信支付结果通知请求报文:{}", rpid, parametersJson);
				String results = HttpUtils.doPost(url, parametersJson, null, 3, 10, Contants.CHARSET_UTF_8);	
				log.info("#notify() Rpid:{},微信支付结果通知响应报文:{}", rpid, results);
			};
		}.start();
		
		String resp = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		super.write(response, resp, CONTENT_TYPE_APPLICATION_HTML);
	}
}
