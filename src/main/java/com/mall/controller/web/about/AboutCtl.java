package com.mall.controller.web.about;

import java.util.HashMap;
import java.util.Map;


import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mall.controller.web.base.BaseCtl;
import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;

/**
 * ******************  类说明  *********************
 * class       :  AboutCtl
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  "关于我们"视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/about")
public class AboutCtl extends BaseCtl{
	
	private static final Logger LOG = LoggerFactory.getLogger(AboutCtl.class);
	
	/**
	 * ********************************************
	 * method name   : aboutus 
	 * description   : 进入"关于我们"页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : hejinyun@umpay.com ,  2017-4-11  下午8:27:57
	 * modified      : zhurouyu ,  2017-4-11  下午9:40:32
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value="/us.htm")
	public String aboutus(ModelMap modelMap) throws Exception{
		
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/aboutus/query";
		LOG.info("请求service：{}",url);
		String result = HttpUtils.doPost(url, "", null,  3, 10, "utf-8");
		LOG.info("响应结果: {}" , result);
		Gson gson = new Gson();
		Map fromJson = gson.fromJson(result, Map.class);
		
		modelMap.addAttribute("description", fromJson.get("description"));
		return "about/us";
	}
	
	/**
	 * ********************************************
	 * method name   : contact 
	 * description   : 进入"联系客服"页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : hejinyun@umpay.com ,  2017-4-11  下午8:32:20
	 * modified 	 : zhurouyu ,  2017-4-12  下午13:32:34
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value="/contact.htm")
	public String contact(ModelMap modelMap) throws Exception{
		//用于请求参数
		Map<String, Object> toMap = new HashMap<String, Object>();
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String key="customertel";
		String url = baseurl + "/emall/api/config/query/"+key;
		String result = HttpUtils.doGet(url, null,  3, 10, "utf-8");
		LOG.info("响应结果: {}" , result);
		Gson gson = new Gson();
		Map fromJson = gson.fromJson(result, Map.class);
		modelMap.addAttribute("tel", fromJson.get("con_value"));
		return "about/contact";
	}
	
}
