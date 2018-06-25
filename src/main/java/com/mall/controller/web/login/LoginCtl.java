package com.mall.controller.web.login;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.Member;
import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;
import com.mall.util.PublicKeyMap;
import com.mall.util.RSAUtils;
import com.mall.util.RedisPool;
import com.mall.util.Verify;


/**
 * ******************  类说明  *********************
 * class       :  LoginCtl
 * @author     :  liuyanghui@umpay.com
 * @version    :  1.0  
 * description :  会员登录视图层操作类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/login")
public class LoginCtl extends BaseCtl{
	private static final Logger LOG = LoggerFactory.getLogger(LoginCtl.class);
	private static final Class<Object> Map = null;
	@Autowired
	private Gson gson;

	/**
	 * ********************************************
	 * method name   : toLog 
	 * description   : 跳转至登录页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : liuyanghui@umpay.com ,  2017-4-15  下午7:50:25
	 * @see          : 
	 * *******************************************
	 */
	@RequestMapping(value = "/index.htm")
	public String toLog(ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOG.info("#toLog() 页面跳转：去登录页面");
	    String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		if(StringUtils.isNotBlank(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(memberInfo!=null){
				return "redirect:/jfmall/index.htm";
			}
		}
		// 微信授权回调地址  获取code
		String auto_url = PropertiesUtils.getProperty("auto_url");
		String appid = PropertiesUtils.getProperty("wx.appid");
		String redirect_uri = PropertiesUtils.getProperty("redirect_uri_mb");
		String state = create_timestamp();
		redirect_uri = URLEncoder.encode(redirect_uri);
		auto_url = auto_url.replace("wxappid", appid).replace("redirect_uri_jf", redirect_uri).replace("wx_state", state);
		// 重定向
		return "redirect:" + auto_url;
	}
	
	@GetMapping(value = "/getlogincode.htm")
	public String getLoginCode(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		LOG.info("#getLoginCode() 页面跳转：手机号登陆{}:", code);
		// 获取 access_token 和openid
		String url = PropertiesUtils.getProperty("access_token_url");
		String appid = PropertiesUtils.getProperty("wx.appid");
		String appsecret = PropertiesUtils.getProperty("appsecret");
		url = url.replace("wxappid", appid).replace("appsecret", appsecret).replace("wxcode", code);
		LOG.info("url:{}", url);
		JSONObject json = HttpUtils.httpRequest(url, "POST", "");
		LOG.info("用户code获取openid,access_token返回信息:{}", json.toString());
		String openid = "";
		if (json.containsKey("access_token") && json.containsKey("openid")) {
			openid = json.getString("openid");
			modelMap.put("openid", openid);
		} else {
			return "redirect:login/index.htm" ;
		}
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		modelMap.addAttribute("module", publicKeyMap.getModulus());
		modelMap.addAttribute("empoent", publicKeyMap.getExponent());
		return "login/login";
	}

	@GetMapping(value = "/forgetpwd.htm")
	public String toforget(ModelMap modelMap,HttpServletRequest request) throws Exception {
		LOG.info("#toforget() 页面跳转：去忘记密码");
		String mobileid = request.getParameter("mobileid");
		LOG.debug("#toforget() mobileid={}",mobileid);
		if(StringUtils.isNotBlank(mobileid)){
			if(Verify.isMobileNO(mobileid)){
				modelMap.addAttribute("mobileid",mobileid);
			}
		}
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		modelMap.addAttribute("module", publicKeyMap.getModulus());
		modelMap.addAttribute("empoent", publicKeyMap.getExponent());
		return "login/getpwd";

	}

	/**
	 * ******************************************** method name : sendAuthCode
	 * description :
	 * 
	 * @return : Map<String,String>
	 * @param : @param mobile
	 * @param : @return
	 * @param : @throws Exception modified : lihu , 2017年4月12日 下午1:41:33
	 * @see : *******************************************
	 */
	@RequestMapping(value = "/sendauthcode.do")
	public @ResponseBody Map<String, String> sendAuthCode(@RequestParam("mobileid") String mobileid) throws Exception {

		// 用于响应
		Map<String, String> result = new HashMap<String, String>();
		if (StringUtils.isBlank(mobileid)) {
			result.put("retCode", "fail");
			result.put("retMsg", "请输入手机号");
			return result;
		}
		boolean ismobileid = Verify.isMobileNO(mobileid);
		if (!ismobileid) {
			result.put("retCode", "fail");
			result.put("retMsg", "请输入正确手机号");
			return result;
		}

		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/login/sendauthcode/" + mobileid;
		String[][] headers = { { "Content-Type", "application/json" } };
		String results = HttpUtils.doPost(url, null, headers, 3, 10, "utf-8");
		LOG.info("响应结果:" + result);
		if (StringUtils.isBlank(results)) {
			LOG.info("#sendAuthCode() 响应为空");
			result.put("retCode", "fail");
			result.put("retMsg", "服务器忙，请稍后再试");
			return result;

		}
		Map fromJson = gson.fromJson(results, Map.class);

		if (!"0000".equals((String) fromJson.get("ret"))) {
			LOG.info("#sendAuthCode() 发送失败：ret={} , msg={}", fromJson.get("ret"), fromJson.get("msg"));
			result.put("retCode", "fail");
			result.put("retMsg", (String) fromJson.get("msg"));
			return result;
		}
		result.put("retCode", "success");
		result.put("retMsg", "发送成功");
		return result;

	}

	/**
	 * ******************************************** method name : updatePWD
	 * description : 忘记密码-修改密码
	 * 
	 * @return : Map<String,String>
	 * @param : @param mobileid
	 * @param : @param password
	 * @param : @param pwd2
	 * @param : @param authcode
	 * @param : @return
	 * @param : @throws Exception modified : lihu , 2017年4月12日 下午1:40:58
	 * @see : *******************************************
	 */
	@RequestMapping(value = "/updatepwd.do")
	public @ResponseBody Map<String, String> updatePWD(@RequestParam("mobileid") String mobileid, @RequestParam("password") String password, @RequestParam("pwd2") String pwd2, @RequestParam("authcode") String authcode) throws Exception {
		// 用于发送请求
		Map<String, String> map = new HashMap<String, String>();
		// 用于返回请求
		Map<String, String> result = new HashMap<String, String>();
		LOG.info("#updatePWD() 请求参数(密码加密): mobileid={},password={},pwd2={},authcode={}", mobileid, password, pwd2, authcode);
		LOG.info("#updatePWD() 密码password解密开始,password={}", password);
		password = RSAUtils.decryptStringByJs(password);
		LOG.info("#updatePWD() 密码pwd2解密开始,pwd2={}", pwd2);
		pwd2 = RSAUtils.decryptStringByJs(pwd2);
		// 请求参数校验
		if (StringUtils.isBlank(mobileid) || StringUtils.isBlank(password) || StringUtils.isBlank(pwd2) || StringUtils.isBlank(authcode)) {
			LOG.info("#updatePWD() 请求参数不全 mobileid={},password={},pwd2={},authcode={}", mobileid, password, pwd2, authcode);
			result.put("ret", "fail");
			result.put("msg", "请求参数不全");
			return result;
		}

		// 判断求改密码确认
		if (!password.equals(pwd2)) {
			result.put("ret", "fail");
			result.put("msg", "两次输入密码不一样");
			return result;
		}

		// 组织请求信息
		map.put("mobileid", mobileid);
		map.put("passwd", password);
		map.put("authcode", authcode);

		LOG.info("#updatePWD()：修改密码请求，接收入参类型map值={}", map);
		JSONObject bodys = JSONObject.fromObject(map);
		LOG.info("#updatePWD()：请求参数转换Json={}", bodys.toString());
		// 设置请求头和请求体
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/login/updatepwd";
		String[][] headers = { { "Content-Type", "application/json" } };
		// 请求service 修改密码

		String resultJson = HttpUtils.doPost(url, bodys.toString(), headers, 3, 10, "utf-8");
		LOG.info("#updatePWD() 响应信息 result={}", resultJson);
		// 返回请求
		if (StringUtils.isBlank(resultJson)) {
			LOG.info("响应为空");
			result.put("ret", "fail");
			result.put("msg", "服务器忙，请稍后再试");
			return result;

		}
		Map fromJson = gson.fromJson(resultJson, Map.class);
		LOG.info("#updatePWD() 响应信息 result={}", fromJson.toString());
		return fromJson;

	}
	
	/**
	 * ********************************************
	 * method name   : memberLogin 
	 * description   : 用户登录校验
	 * @return       : String
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : liuyanghui@umpay.com ,  2017年4月14日  下午10:17:12
	 * @see          : 
	 * *******************************************
	 */
	@ResponseBody
	@RequestMapping(value = "/memberlogin.htm")
	public String memberLogin(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		// 获取手机号
        String mobile = request.getParameter("mobile");
        // 获取密码
		String password = request.getParameter("password");
		// 获取openid
		String openid = request.getParameter("openid");
		String login_token = getLoginToken(request, response);
		LOG.debug("mobile:{},password:{}", mobile, password);
		password = RSAUtils.decryptStringByJs(password);
		LOG.debug("还原密码:{}", password);
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> resultMsg = new HashMap<String, String>();
		// 还原后的密码
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
			resultMsg.put("msg", "用户名和密码不能为空");
			return resultMsg.toString();
		}
		map.put("openid", openid);
		map.put("mobile", mobile);
		map.put("password", password);
		map.put("login_token", login_token);
		JSONObject bodys = JSONObject.fromObject(map);
		LOG.info("web平台：请求参数转换Json:{}", bodys);
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String login_url = baseurl + "/emall/api/login/memberlogin";
		String[][] headers = { { "Content-Type", "application/json" } };
		// 调用接口查询用户名密码
		String resultJson = HttpUtils.doPost(login_url, bodys.toString(), headers, 3, 10, "utf-8");
		JSONObject res = JSONObject.fromObject(resultJson);
		JSONObject resMsg = resMsg(request,res);
		return resMsg.toString();
	}
	/**
	 * ********************************************
	 * method name   : resMsg 
	 * description   : 返回页面提示信息 ,建议提示信息写到配置文件中
	 * @return       : String
	 * @param        : @param retcode
	 * @param        : @return
	 * modified      : liuyanghui@umpay.com ,  2017年4月17日  下午4:57:46
	 * @see          : 
	 * *******************************************
	 */
	public static JSONObject resMsg(HttpServletRequest request, JSONObject retJson){
		String retcode = (String) retJson.optString("ret");
		switch (retcode) {
		case "0000":
			retJson.put("msg", "登陆成功");
			//重定向到登录前页面
			String login_token = getCookie(request, Contants.LOGIN_TOKEN);
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				String redirect = RedisPool.get(Contants.REDIRCT+login_token);
				retJson.put("url", redirect);
			}
			break;
		case "00000001":
			retJson.put("msg", "该手机号未注册");
			break;
		case "00000003":
			retJson.put("msg", "用户名或密码输入错误，请重新输入");
			break;
		case "00000004":
			retJson.put("msg", "用户名或密码输入错误，请重新输入");
			break;
		case "00000005":
			retJson.put("msg", "您已经3次错误,还有2次机会");
			break;
		case "00000006":
			retJson.put("msg", "您已经4次错误,还有1次机会");
			break;
		case "00000007":
			retJson.put("msg", "您已经5次错误, 请明天再试");
			break;
		default:
			retJson.put("msg", "系统异常");
			break;
		}
		return retJson;
	}
   /**
    * ********************************************
    * method name   : loginOut 
    * description   :  退出登录 清除cookie 和redis 信息
    * @return       : String
    * @param        : @param request
    * @param        : @param response
    * @param        : @return
    * modified      : liuyanghui@umpay.com ,  2017年4月16日  下午10:15:59
    * @see          : 
    * *******************************************
    */
	@RequestMapping(value = "/loginOut.htm")
	public ModelAndView loginOut(HttpServletRequest request,HttpServletResponse response) {
		String login_token = getToken(request, response);
		LOG.info("#loginOut()用户login_token:{}",login_token);
		if(StringUtils.isNotBlank(login_token)){
			RedisPool.del(login_token);
			removeCookie( request,response);
		}
		//清除cookie login_token信息
		String domain = PropertiesUtils.getProperty("platform.site.domain");
		return new ModelAndView("redirect:/jfmall/index.htm");
	}
	

	/**
	 * ********************************************
	 * method name   : wxlogin 
	 * description   : 微信授权登陆 先获取微信openid，如果该openid存在则直接跳转首页，如果不存在则入库
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : liuyanghui@umpay.com ,  2017年4月16日  下午1:45:40
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value = "/wxlogin.htm")
	public String wxlogin(ModelMap modelMap) throws Exception {
		LOG.info("#wxlogin() 页面跳转：微信登陆");
		// 微信授权回调地址
		String auto_url = PropertiesUtils.getProperty("auto_url");
		String appid = PropertiesUtils.getProperty("wx.appid");
		String redirect_uri = PropertiesUtils.getProperty("redirect_uri_jf");
		String state = create_timestamp();
		redirect_uri = URLEncoder.encode(redirect_uri, Contants.CHARSET_UTF_8);
		auto_url = auto_url.replace("wxappid", appid).replace("redirect_uri_jf", redirect_uri).replace("wx_state", state);
		// 重定向
		return "redirect:" + auto_url;
	}

	/**
	 * ********************************************
	 * method name   : getCode 
	 * description   : 获取微信用户信息
	 * @return       : String
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : liuyanghui@umpay.com ,  2017年4月16日  下午1:45:05
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value = "/getcode.htm")
	public String getCode(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		LOG.info("#wxlogin() 页面跳转：微信登陆{}:", code);
		// 获取 access_token 和openid
		String url = PropertiesUtils.getProperty("access_token_url");
		String appid = PropertiesUtils.getProperty("wx.appid");
		String appsecret = PropertiesUtils.getProperty("appsecret");
		url = url.replace("wxappid", appid).replace("appsecret", appsecret).replace("wxcode", code);
		LOG.info("url:{}", url);
		JSONObject json = HttpUtils.httpRequest(url, "POST", "");
		LOG.info("用户code获取openid,access_token返回信息:{}", json.toString());
		String access = "";
		String openid = "";
		String unionid = "";
		if (json.containsKey("access_token") && json.containsKey("openid")) {
			access = json.getString("access_token");
			openid = json.getString("openid");

		} else {
			return "redirect:login/index.htm" ;
		}
		if (json.containsKey("unionid")) {
			unionid = json.getString("unionid");
		} else {
			json.put("unionid", "");
		}

		// 如果accss_token openid不为空
		if (!StringUtils.isEmpty(access) && !StringUtils.isEmpty(openid)) {
			String wx_user_info_url = PropertiesUtils.getProperty("wx_user_info_url");
			wx_user_info_url = wx_user_info_url.replace("access_toke_jf", access).replace("wx_openid", openid);
			JSONObject wx_user_info = HttpUtils.httpRequest(wx_user_info_url, "GET", "");
			String openids = wx_user_info.getString("openid");
			LOG.info("用户unionid:{}", openids);
			if (!StringUtils.isEmpty(openids)) {
				String[][] headers = { { "Content-Type", "application/json" } };
				String baseurl = PropertiesUtils.getProperty("mall.service.url");
				baseurl = baseurl + "/emall/api/login/wxlogin";
				wx_user_info.put("unionid", unionid);
				wx_user_info.remove("language");
				wx_user_info.remove("city");
				wx_user_info.remove("province");
				wx_user_info.remove("country");
				wx_user_info.remove("privilege");
				String login_token = getLoginToken(request, response);
				wx_user_info.put("login_token", login_token);
				String resultJson = HttpUtils.doPost(baseurl, wx_user_info.toString(), headers, 3, 10, "utf-8");
				JSONObject resJson = JSONObject.fromObject(resultJson);
				// 如果登录成功跳转到首页
				if ("0000".equals(resJson.get("retCode"))) {
					//重定向到登录前页面
					String memberInfo = RedisPool.get(login_token);
					if(StringUtils.isNotBlank(memberInfo)){
						String redirect = RedisPool.get(Contants.REDIRCT+login_token);
						if(StringUtils.isNotBlank(redirect)){
							return "redirect:"+redirect;
						}
					}
					return "redirect:/jfmall/index.htm";
				}
			}
		}
		return "redirect:/login/index.htm" ;
	}

	/**
	 * ******************************************** method name : memberlogin
	 * description : 测试类
	 * 
	 * @return : void
	 * @param : modified : liuyanghui@umpay.com , 2017年4月12日 下午10:56:56
	 * @see : *******************************************
	 */
	public static void memberlogin() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", "18210141187");
		map.put("password", "1234");
		JSONObject bodys = JSONObject.fromObject(map);
		LOG.info("web平台：请求参数转换Json" + bodys.toString());
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/login/memberlogin";
		LOG.info("url:" + url);
		String[][] headers = { { "Content-Type", "application/json" } };
		String resultJson = HttpUtils.doPost(url, bodys.toString(), headers, 3, 10, "utf-8");

	}

	public static void main(String[] args) {

	}

	/**
	 * ******************************************** method name : testInsertUser
	 * description : 测试添加微信用户
	 * 
	 * @return : void
	 * @param : modified : liuyanghui@umpay.com , 2017年4月13日 下午6:02:02
	 * @see : *******************************************
	 */
	public static void testInsertUser() {
		JSONObject json = new JSONObject();
		json.put("openid", "123456789");
		json.put("nickname", "别急");
		json.put("sex", "1");
		json.put("headimgurl", "tupian");
		json.put("unionid", "111222333");
		String[][] headers = { { "Content-Type", "application/json" } };
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		baseurl = baseurl + "/emall/api/login/wxlogin";
		String resultJson = HttpUtils.doPost(baseurl, json.toString(), headers, 3, 10, "utf-8");
		System.out.println(resultJson);
	}

	/**
	 * ******************************************** method name : access
	 * description : 微信返回信息 openid 用户的唯一标识 nickname 用户昵称 sex
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 province 用户个人资料填写的省份 city 普通用户个人资料填写的城市
	 * country 国家，如中国为CN headimgurl
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640
	 * *640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。 privilege 用户特权信息，json
	 * 数组，如微信沃卡用户为（chinaunicom） unionid
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 * 
	 * 
	 * @return : void
	 * @param : modified : liuyanghui@umpay.com , 2017年4月13日 上午9:54:41
	 * @see : *******************************************
	 */
	public static void access() {
		// 获取 access_token 和openid
		String url = PropertiesUtils.getProperty("access_token_url");
		String appid = PropertiesUtils.getProperty("wx.appid");
		String appsecret = PropertiesUtils.getProperty("appsecret");
		url = url.replace("wxappid", appid).replace("appsecret", appsecret).replace("wxcode", "12321323");
		LOG.info("url:" + url);
		JSONObject json = HttpUtils.httpRequest(url, "POST", "");
		LOG.info(json.toString());
		String access = json.getString("access_token");
		String openid = json.getString("openid");
		// 如果accss_token openid不为空
		if (!StringUtils.isEmpty(access) && !StringUtils.isEmpty(openid)) {
			String wx_user_info_url = PropertiesUtils.getProperty("wx_user_info_url");
			wx_user_info_url = wx_user_info_url.replace("access_toke_jf", access).replace("wx_openid", openid);
			JSONObject wx_user_info = HttpUtils.httpRequest(url, "GET", "");
			String unionid = wx_user_info.getString("unionid");
			if (!StringUtils.isEmpty(unionid)) {
				String nickname = wx_user_info.getString("unionid");
				String sex = wx_user_info.getString("sex");
				String headimgurl = wx_user_info.getString("headimgurl");

			}
		}
		/*
		 * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=
		 * SECRET&code=CODE&grant_type=authorization_code
		 */

	}

	/*
	 * 密码错误1次 对话框： 密码错误，请重新输入 密码错误2次 对话框： 密码错误，请重新输入 密码错误3次 toast框：
	 * 您已经3次错误,还有2次机会 (继续 忘记密码 ) 密码错误4次 toast框： 您已经4次错误,还有1次机会 (继续 忘记密码) 密码错误5次
	 * 对话框： 您已经5次错误, 请明天再试
	 * 
	 * 手机号未注册 toast框： 该手机号未注册 （更换手机号 去注册）
	 */
	/**
	 * ******************************************** 
	 * method name : removeCookie
	 * description :  清除 login_token 信息
	 * 
	 * @return : void
	 * @param : @param response
	 * @param : @param request modified : liuyanghui@umpay.com , 2017年4月12日
	 *        上午11:06:44
	 * @see : *******************************************
	 */
	private void removeCookie( HttpServletRequest request,HttpServletResponse response) {
		String projectName = request.getContextPath();
		Cookie nameCookie = new Cookie(Contants.LOGIN_TOKEN, null); // 
		nameCookie.setPath(projectName + "/"); // cookie路径问题
		nameCookie.setMaxAge(0);
		response.addCookie(nameCookie);
	}

	/**
	 * ******************************************** method name : saveToCookie
	 * description : 保存值到cookie中
	 * 
	 * @return : void
	 * @param : @param user
	 * @param : @param response
	 * @param : @param request modified : liuyanghui@umpay.com , 2017年4月12日
	 *        上午10:48:30
	 * @see : *******************************************
	 */
	public static void saveToCookie(Member member, HttpServletResponse response, HttpServletRequest request) {
		String projectName = request.getContextPath();
		LOG.info("projectname:" + projectName);
		Cookie nameCookie = new Cookie(Contants.MEMBER_MOBILEID, member.getMobileid()); // 可以使用md5或着自己的加密算法保存
		Cookie passwordCookie = new Cookie(Contants.MEMBER_PASSWORD, member.getPasswd());
		nameCookie.setPath(projectName + "/"); // cookie路径问题
		nameCookie.setMaxAge(Contants.COOKIE_TIME);
		passwordCookie.setPath(projectName + "/");
		passwordCookie.setMaxAge(Contants.COOKIE_TIME);
		response.addCookie(nameCookie);
		response.addCookie(passwordCookie);
	}

	/**
	 * ******************************************** method name : getCookie
	 * description : 获取cookie中的手机号，密码
	 * 
	 * @return : Member
	 * @param : @param request
	 * @param : @return modified : liuyanghui@umpay.com , 2017年4月13日 下午5:54:02
	 * @see : *******************************************
	 */
	private Member getCookie(HttpServletRequest request) {
		Member member = new Member();
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(Contants.MEMBER_MOBILEID)) {
				member.setMobileid(cookie.getValue());
			}
			if (cookie.getName().equals(Contants.MEMBER_PASSWORD)) {
				member.setPasswd(cookie.getValue());
			}
		}
		return member;
	}
	/**
	 * ********************************************
	 * method name   : getCookie 
	 * description   :  根据key 值获取cookie中value值
	 * @return       : String
	 * @param        : @param request
	 * @param        : @param loginToken
	 * @param        : @return
	 * modified      : liuyanghui@umpay.com ,  2017年4月13日  下午10:50:08
	 * @see          : 
	 * *******************************************
	 */
	public static String getCookie(HttpServletRequest request, String loginToken) {
		String loginTokenValue = "";
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(loginToken)) {
					loginTokenValue = cookie.getValue();
				}
			}
		}
		return loginTokenValue;
	}
	/**
	 * ********************************************
	 * method name   : toReg 
	 * description   : 跳转首页 ，登陆过来的注册页需要带入手机号
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : liuyanghui@umpay.com ,  2017年4月16日  上午10:45:56
	 * @see          : 
	 * *******************************************
	 */
	@RequestMapping(value="/reg.htm")
	public String toReg(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		LOG.info("#toReg()页面跳转：去注册账号");
		String login_token = getLoginToken(request, response);
		String mobileid = RedisPool.get(Contants.MEMBER_MOBILEID+login_token);
		String code = request.getParameter("code");
		LOG.info("#toReg()登陆页传入手机号:{},微信code:{}", mobileid,code);
		JSONObject json = getOpenId(code);
		String openid = "";
		String unionid = "";
		if ( json.containsKey("openid")) {
			openid = json.getString("openid");
			
		} else {
			LOG.info("#toReg():未获取到微信openid");
		}
		if (json.containsKey("unionid")) {
			unionid = json.getString("unionid");
		} else {
			json.put("unionid", "");
		}
		String token_login = getLoginToken(request, response);
		if (StringUtils.isNotBlank(openid)) {
			RedisPool.set(Contants.WX_OPENID+token_login,60*60, openid);
		}
		if (StringUtils.isNotBlank(unionid)) {
			RedisPool.set(Contants.WX_UNIONID+token_login, 60*60, unionid);
		}
		
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		modelMap.addAttribute("module", publicKeyMap.getModulus());
		modelMap.addAttribute("expoent", publicKeyMap.getExponent());
		modelMap.addAttribute("mobileid", mobileid);
		return "register/reg";
	}
	/**
	 * ********************************************
	 * method name   : getOpenId 
	 * description   : 获取微信openid
	 * @return       : JSONObject
	 * @param        : @param code
	 * @param        : @return
	 * modified      : liuyanghui@umpay.com ,  2017年4月16日  下午9:09:51
	 * @see          : 
	 * *******************************************
	 */
	public static JSONObject getOpenId(String code){
		LOG.info("#wxlogin() 页面跳转：微信登陆{}:", code);
		// 获取 access_token 和openid
		String url = PropertiesUtils.getProperty("access_token_url");
		String appid = PropertiesUtils.getProperty("wx.appid");
		String appsecret = PropertiesUtils.getProperty("appsecret");
		url = url.replace("wxappid", appid).replace("appsecret", appsecret).replace("wxcode", code);
		LOG.info("url:{}", url);
		JSONObject json = HttpUtils.httpRequest(url, "POST", "");
		LOG.info("用户code获取openid,access_token返回信息:{}", json.toString());
		return json;
	}
	/**
	 * ********************************************
	 * method name   : wxreg 
	 * description   : 
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : liuyanghui@umpay.com ,  2017年4月16日  下午8:36:48
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value = "/regGetOpenid.htm")
	public String regGetOpenid(ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 获取微信openid
		String mobileid = request.getParameter("mobile");
		String login_token = getLoginToken(request, response);
		if(StringUtils.isNotBlank(mobileid)){
			RedisPool.set(Contants.MEMBER_MOBILEID+login_token, 60*30, mobileid);
		}
		String reg_url = PropertiesUtils.getProperty("reg_url");
		String appid = PropertiesUtils.getProperty("wx.appid");
		String redirect_uri = PropertiesUtils.getProperty("redirect_uri_reg");
		String state = create_timestamp();
		redirect_uri = URLEncoder.encode(redirect_uri);
		reg_url = reg_url.replace("wxappid", appid).replace("redirect_uri_reg", redirect_uri).replace("wx_state", state);
		
		// 重定向到注册页面
		LOG.info("#wxreg()重定向地址:{}",reg_url);
		return "redirect:" + reg_url;
//		return "redirect:" + PropertiesUtils.getProperty("redirect_uri_reg");
	}
	
	@GetMapping(value = "/loginGetOpenid.htm")
	public String loginGetOpenid(ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 获取微信openid
		String mobileid = request.getParameter("mobile");
		String login_token = getLoginToken(request, response);
		if(StringUtils.isNotBlank(mobileid)){
			RedisPool.set(Contants.MEMBER_MOBILEID+login_token, 60*30, mobileid);
		}
		String reg_url = PropertiesUtils.getProperty("reg_url");
		String appid = PropertiesUtils.getProperty("wx.appid");
		String redirect_uri = PropertiesUtils.getProperty("redirect_uri_reg");
		String state = create_timestamp();
		redirect_uri = URLEncoder.encode(redirect_uri);
		reg_url = reg_url.replace("wxappid", appid).replace("redirect_uri_reg", redirect_uri).replace("wx_state", state);
		
		// 重定向到注册页面
		LOG.info("#wxreg()重定向地址:{}",reg_url);
		return "redirect:" + reg_url;
//		return "redirect:" + PropertiesUtils.getProperty("redirect_uri_reg");
	}
	
	
	@RequestMapping(value="/agreement")
	public ModelAndView toAgree() throws Exception{
		LOG.info("页面跳转：去用户协议页");
		return new ModelAndView("login/agreement");
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	/**
	 * ********************************************
	 * method name   : getLoginToken 
	 * description   : 获取token uuid 没有则生成一个uuid 并存入cookie
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

	/**
	 * ******************************************** method name : getToken
	 * description :
	 * 
	 * @return : String
	 * @param : @param request
	 * @param : @param response
	 * @param : @return modified : liuyanghui@umpay.com , 2017年4月16日 下午10:25:11
	 * @see : *******************************************
	 */
	private String getToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String login_token = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Contants.LOGIN_TOKEN)) {
					login_token = cookie.getValue();
				}
			}
		}
		return login_token;
	}
	
}
