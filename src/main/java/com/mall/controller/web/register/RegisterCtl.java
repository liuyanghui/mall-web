package com.mall.controller.web.register;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;
import com.mall.util.RSAUtils;
import com.mall.util.RedisPool;
/**
 * ******************  类说明  *********************
 * class       :  RegisterCtl
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  用户注册视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/register")
public class RegisterCtl extends BaseCtl{
	private static final Logger LOG = LoggerFactory.getLogger(RegisterCtl.class);
	
	/** ********************************************
	 * method name   : agreement 
	 * description   : 跳转到注册协议页面
	 * @return       : ModelAndView
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dangjingchao ,  2017年4月12日  下午4:55:11
	 * @see          : 
	 * ********************************************/      
	@RequestMapping(value="/agreement.htm")
	public ModelAndView agreement() throws Exception{
		return new ModelAndView("register/agreement");
	}
	@RequestMapping(value="/index.htm")
	public ModelAndView toLogin() throws Exception{
		return new ModelAndView("login/login");
	}
	
	/** ********************************************
	 * method name   : sendAuthCode 
	 * description   : 发送验证码短信
	 * @return       : String
	 * @param        : @param mobile
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dangjingchao ,  2017年4月12日  上午11:04:33
	 * @see          : 
	 * ********************************************/
	@PostMapping(value = "/sendcode.htm")
	public void sendAuthCode(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String mobileid = request.getParameter("mobileid");
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/reg/checkCodeSend";
		Map<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("mobileid", mobileid);
		String bodys = gson.toJson(toMap);
		LOG.info("请求service：{} 请求参数: {}", url , bodys);
		String result = HttpUtils.doPost(url, bodys, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("响应结果:" + result);
		super.write(response, result);
		return;
	}
	/** ********************************************
	 * method name   : registeredUser 
	 * description   : 注册用户
	 * @return       : String
	 * @param        : @param mobile
	 * @param        : @param passworld
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dangjingchao ,  2017年4月12日  下午8:14:04
	 * @see          : 
	 * ********************************************/      
	@PostMapping(value = "/registereduser.htm")
	public void registeredUser(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String mobileid = request.getParameter("mobileid");
		String password = request.getParameter("password");
		String login_token = getLoginToken(request,response);
		String openid = RedisPool.get(Contants.WX_OPENID+login_token);
		String unionid = RedisPool.get(Contants.WX_UNIONID+login_token);
		
		//还原密码
		password = RSAUtils.decryptStringByJs(password);
		//验证码校验是否正确
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> result = new HashMap<String, String>();
		String code = request.getParameter("code");
		String authcoderedis = RedisPool.get(Contants.SEND_REGISTER_AUTH_CODE + mobileid);
		if ( null == authcoderedis) {
			LOG.info("#registeredUser() redis未取到{}的验证码!",mobileid);
			result.put("ret", "fault");
			result.put("msg", "验证码已过期!");
			String resultJson = gson.toJson(result, map.getClass());
			super.write(response, resultJson);
			return;
		}
		if (!code.equals(authcoderedis)) {
			LOG.info("#registeredUser() 验证码校验时用户输入验证码 {} 下发验证码 {}",code,authcoderedis);
			result.put("ret", "fault");
			result.put("msg", "验证码错误!");
			String resultJson = gson.toJson(result, map.getClass());
			super.write(response, resultJson);
			return;
		}
		
		if ("".equals(mobileid) || "".equals(password) || null == mobileid || null == password) {
			LOG.info("请求参数格式有误!");
			result.put("ret", "fault");
			result.put("msg", "请求参数格式有误!");
			String resultJson = gson.toJson(result, map.getClass());
			super.write(response, resultJson);
			return;
		}
		map.put("mobileid", mobileid);
		map.put("passwd", password);
		map.put("openid", openid);
		map.put("unionid", unionid);
		map.put("login_token", login_token);
		LOG.info("web平台：注册请求，接收入参类型map 值：" + map);
		String bodys = gson.toJson(map);
		LOG.info("web平台：请求参数转换Json" + bodys.toString());
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/reg/adduser";
		String resultJson = HttpUtils.doPost(url, bodys, null, 3, 10, Contants.CHARSET_UTF_8);
		super.write(response, resultJson);
		return;
	}
	/**
	 * ********************************************
	 * method name   : getLoginToken 
	 * description   : 获取token uuid
	 * @return       : String
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * modified      : liuyanghui@umpay.com ,  2017年4月15日  下午10:34:08
	 * @see          : 
	 * *******************************************
	 */
	private String getLoginToken(HttpServletRequest request,HttpServletResponse response) {
		
		Cookie[] cookies = request.getCookies();
		String login_token = "";
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Contants.LOGIN_TOKEN)) {
					login_token = cookie.getValue();
				}
			}
		}
		
		//如果cookie 没有uuid 则生成一个uuid 并存入cookie中
	    if(StringUtils.isEmpty(login_token)){
	    	login_token = UUID.randomUUID().toString();
	    	Cookie cookie = new Cookie(Contants.LOGIN_TOKEN, login_token);
			cookie.setPath("/");
			response.addCookie(cookie);
			cookie.setMaxAge(Contants.COOKIE_TIME);
	    }
		return login_token;
	}
}
