package com.mall.controller.web.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mall.annotation.LoginValid;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.Iresp_common;
import com.mall.controller.web.vo.UserMessage;
import com.mall.controller.web.vo.WalletPoints;
import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;
import com.mall.util.StringUtil;

/**
 * ******************  类说明  *********************
 * class       :  NewsCtl
 * @author     :  zhurouyu
 * @version    :  1.0  
 * description :  "消息中心"视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/news")
public class NewsCtl extends BaseCtl{
	
	private static final Logger LOG = LoggerFactory.getLogger(NewsCtl.class);
	
	/**
	 * ********************************************
	 * method name   : message 
	 * description   : 进入"消息中心"页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-15  下午7:20:32
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/message.htm")
	public String message(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Gson gson = new Gson();
		String userid = (String)request.getAttribute(Contants.USERID);
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		//请求参数
		Map<String, Object> toMap = new HashMap<String, Object>();
		//传过来的登陆人
		toMap.put("userid", userid);
		String body = gson.toJson(toMap);
		String url = baseurl + "/emall/api/news/message";
		LOG.info("请求service：{}",url);
		String result = HttpUtils.doPost(url, body, null,  3, 10, "utf-8");
		LOG.info("响应结果: {}" , result);
		List<UserMessage> messageList = new ArrayList<UserMessage>();
		List<UserMessage> wreadList = new ArrayList<UserMessage>();
		List<UserMessage> yreadList = new ArrayList<UserMessage>();
		int count=0;
		if(!StringUtil.isEmpty(result)){
			//返回请求
			messageList = gson.fromJson(result, new TypeToken<List<UserMessage>>(){}.getType());
			for(UserMessage um:messageList){
				int isread = um.getIsread();
				if(isread == 0){
					wreadList.add(um);
					count++;
				}else{
					yreadList.add(um);
				}
			}
		}
		modelMap.addAttribute("userid", userid);
		modelMap.addAttribute("count", count);//未读消息记录
		modelMap.addAttribute("size", messageList.size());
		modelMap.addAttribute("messageList", messageList);//消息记录
		modelMap.addAttribute("wreadList", wreadList);//未读消息记录
		modelMap.addAttribute("yreadList", yreadList);//已读消息记录
		return "news/message";
	}
	/**
	 * ********************************************
	 * method name   : update 
	 * description   : 将未读消息修改为已读状态
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhurouyu ,  2017-4-15  下午7:20:32
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/update.htm")
	public void update(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Gson gson = new Gson();
		String userid = (String)request.getAttribute(Contants.USERID);
		String msgid = request.getParameter("msgid");
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		//请求参数
		Map<String, Object> toMap = new HashMap<String, Object>();
		//传过来的登陆人
		toMap.put("userid", userid);
		toMap.put("isread", 1);
		toMap.put("msgid", msgid);
		String body = gson.toJson(toMap);
		String url = baseurl + "/emall/api/news/update";
		LOG.info("请求service：{}",url);
		String result = HttpUtils.doPost(url, body, null,  3, 10, "utf-8");
		LOG.info("响应结果: {}" , result);
	}
}
