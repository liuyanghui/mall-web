package com.mall.controller.api.delive;

import java.util.HashMap;
import java.util.Map;




import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;

public class DeliveryController {
	private static final Logger LOG = LoggerFactory.getLogger(DeliveryController.class);

	/**
	 * ****************** 类说明 ********************* class : TestCtl
	 * 
	 * @author : xuhuafeng
	 * @version : 1.0 description : 微信下单
	 * ************************************************/
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public ModelAndView getPic(ModelMap modelMap) throws Exception {
		Map<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("ShipperCode", "");
		toMap.put("LogisticCode", "");
		String url = PropertiesUtils.getProperty("mall.service.url");
		url = url + "/emall/api/util/delivery";
		String[][] headers = { { "Content-Type", "application/json" } };
		String bodys = mapTojson(toMap);
		String jsonString = HttpUtils.doPost(url, bodys, headers, 3, 10, "utf-8");
		System.out.println(jsonString);
		return new ModelAndView("wxpay");
	}

	/**
	 * 
	 * @data 2017年4月7日下午7:47:53
	 * @return String
	 * @auther liuyanghui
	 * @desc:
	 */
	public static String mapTojson(Map<String, Object> map) {
		JSONObject json = new JSONObject();
		json.put("ShipperCode", map.get("ShipperCode"));
		json.put("LogisticCode", map.get("LogisticCode"));
		return json.toString();
	}

}
