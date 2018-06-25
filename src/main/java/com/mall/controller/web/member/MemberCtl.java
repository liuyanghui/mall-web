package com.mall.controller.web.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mall.annotation.LoginValid;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.Address;
import com.mall.controller.web.vo.Iresp_common;
import com.mall.controller.web.vo.Member;
import com.mall.controller.web.vo.ReqOrder;
import com.mall.util.Digests;
import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;
import com.mall.util.PublicKeyMap;
import com.mall.util.RSAUtils;
import com.mall.util.RedisPool;
import com.mall.util.StringUtil;
import com.mall.util.Verify;

import net.sf.json.JSONObject;

/**
 * ******************  类说明  *********************
 * class       :  MemberCtl
 * @author     :  zhangyajie1@umpay.com
 * @version    :  1.0  
 * description :  "个人中心"视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/member")
public class MemberCtl extends BaseCtl {
	private static final Logger LOG = LoggerFactory.getLogger(MemberCtl.class);

	/**
	 * ********************************************
	 * method name   : gerPersonInfo 
	 * description   : 进入"个人中心"页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017-4-12  下午10:00:00
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value = "/index.htm")
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				String hidemobileid = memjson.optString("hidemobileid");
				modelMap.addAttribute("hidemobileid", hidemobileid);
			}
		}
		String userid = (String) request.getAttribute(Contants.USERID);
//		JSONObject memberInfo = (JSONObject) request.getAttribute(Contants.LOGIN_TOKEN);
//		String hidemobileid = memberInfo.optString("hidemobileid");
//		Member member = gson.fromJson(memberInfo.toString(), Member.class);
//		LOG.info("获取的用户信息为:map值={}", memberInfo.toString());
//		userid = "000000016";
//		Member member = new Member();
//		String hidemobileid = "15890687990";
//		member.setMobileid("15890687990");
//		member.setUserid("U0000000092");
		//用于返回请求
		LOG.info("#index() 请求参数 userid={}", userid);
		
		//获取url
		String url =getServiceURL("emall/api/order/findcount/"+userid);
		String resultJson = HttpUtils.doGet(url, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#index() 响应信息 result={}", resultJson);
		if (StringUtils.isBlank(resultJson)) {
			LOG.info("响应为空");
			return "404";
		}
		String nopay = "";
		String nosend = "";
		if(resultJson != null && !"".equals(resultJson)){
			nopay = resultJson.split("\\|")[0];
			nosend = resultJson.split("\\|")[1];
		}
//		modelMap.addAttribute(member);
		modelMap.addAttribute("nopay",nopay);
		modelMap.addAttribute("nosend",nosend);
//		modelMap.addAttribute("hidemobileid",hidemobileid);
		return "member/index";
	}
	
	/**
	 * ********************************************
	 * method name   : account 
	 * description   : 进入"账户与安全"页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017-4-12  下午10:01:00
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/account.htm")
	public String account(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				String hidemobileid = memjson.optString("hidemobileid");
				modelMap.addAttribute("hidemobileid", hidemobileid);
			}
		}
		return "member/account";
	}
	
	/**
	 * ********************************************
	 * method name   : bindMobile 
	 * description   : 绑定手机功能
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param mobileid
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月15日  下午5:40:40
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/bind_mobile.htm")
	public String bindMobile(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				String hidemobileid = memjson.optString("hidemobileid");
				modelMap.addAttribute("hidemobileid", hidemobileid);
			}
		}
		return "member/bind_mobile";
	}
	
	/**
	 * ********************************************
	 * method name   : updatePWD 
	 * description   : 
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月12日  下午8:03:36
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value="/update_pwd.htm")
	public String updatePWD(ModelMap modelMap){
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		modelMap.addAttribute("module", publicKeyMap.getModulus());
		modelMap.addAttribute("empoent", publicKeyMap.getExponent());
		return "member/update_pwd";
	}
	
	/**
	 * ********************************************
	 * method name   : updatePayPWD 
	 * description   : 新增和修改积分支付密码页面
	 * @return       : String
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月12日  下午9:44:17
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/update_paypwd.htm")
	public String updatePayPWD(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		String t = request.getParameter("t");
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				String hidemobileid = memjson.optString("hidemobileid");
				modelMap.addAttribute("hidemobileid", hidemobileid);
			}
		}
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		modelMap.addAttribute("module", publicKeyMap.getModulus());
		modelMap.addAttribute("empoent", publicKeyMap.getExponent());
		if(t == null || "".equals(t)){
			modelMap.addAttribute("t", "no");
		}else{
			modelMap.addAttribute("t", t);
		}
		return "member/update_paypwd";
	}
	
	/**
	 * ********************************************
	 * method name   : addPayPWD 
	 * description   : 设置积分支付密码
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月18日  上午10:18:12
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/addpwd.htm")
	public String addPayPWD(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		String t = request.getParameter("t");
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				String hidemobileid = memjson.optString("hidemobileid");
				modelMap.addAttribute("hidemobileid", hidemobileid);
			}
		}
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		modelMap.addAttribute("module", publicKeyMap.getModulus());
		modelMap.addAttribute("empoent", publicKeyMap.getExponent());
		modelMap.addAttribute("t", t);
		return "member/paypwd";
	}
	
	/**
	 * ********************************************
	 * method name   : updatePwd
	 * description   : 修改登录密码
	 * @return       : Map<String,String>
	 * @param        : @param mobile
	 * @param        : @param password
	 * @param        : @param pwd2
	 * @param        : @param authcode
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月12日  上午11:33:10
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value = "/updatepwd.htm")
	public void updatePwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String) request.getAttribute(Contants.USERID);
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String mobile = "";
		String passw = "";
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				mobile = memjson.optString("mobileid");
				passw = memjson.optString("passwd");
			}
		}
        //获取密码
		String password = request.getParameter("password");
		//获取原密码
		String oldPwd = request.getParameter("oldPwd");
		
		LOG.info("原文加密后为:password:{}, oldPwd:{}", password, oldPwd);
		LOG.debug("mobile:{}, password:{}, oldPwd:{}", mobile, password, oldPwd);
		
		password = RSAUtils.decryptStringByJs(password);
		LOG.debug("还原密码:{}", password);
		
		oldPwd = RSAUtils.decryptStringByJs(oldPwd);
		LOG.debug("还原密码:{}", oldPwd);
		
		Iresp_common result = new Iresp_common();
		if ("".equals(oldPwd) || "".equals(password) || null==oldPwd || null == password) {
			LOG.info("请求参数不全");
			result.setRet("fail");
			result.setMsg("请求参数不全");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		String pwd = Digests.sha1(oldPwd, PropertiesUtils.getProperty("password.key"));
		// 密码不为空，并且密码正确
		if (StringUtils.isEmpty(pwd) || !pwd.equals(passw)) {
			result.setRet("fail");
			result.setMsg("登录密码不正确！");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		if (oldPwd.equals(password)) {
			result.setRet("fail");
			result.setMsg("修改前和修改后密码不能一致！");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		//获取token
		String loginToken = getLoginToken(request, response);
		//用于发送修改登录密码的请求
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobileid", mobile);
		map.put("passwd", password);
		map.put("userid", userid);
		map.put(Contants.LOGIN_TOKEN,loginToken);
		LOG.debug("#updatePwd()：修改密码请求，接收入参类型map值={}", map);
		String body = gson.toJson(map);
		LOG.debug("#updatePwd()：请求参数转换Json={}", body);
		//获取url
		String checkurl =getServiceURL("emall/api/member/updatepwd");
		//请求service 修改密码
		String results = HttpUtils.doPost(checkurl, body.toString(), null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#updatePwd() 响应信息 result={}", results);
		if (StringUtils.isBlank(results)) {
			LOG.info("响应为空");
			result.setRet("fail");
			result.setMsg("服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		//返回请求
		Map<String, String> fromJson = gson.fromJson(results, new TypeToken<Map<String, String>>() {}.getType());
		LOG.info("#updatePwd() 响应信息 result={}", fromJson.toString());
		if (!"0000".equals((String) fromJson.get("ret"))) {
			LOG.info("发送失败：ret={}, msg={}", fromJson.get("ret"), fromJson.get("msg"));
			result.setRet("fail");
			result.setMsg((String) fromJson.get("msg"));
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.setRet("success");
		result.setMsg(fromJson.get("msg"));
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	
	
	/**
	 * ********************************************
	 * method name   : updatePayPwd
	 * description   : 修改积分支付密码
	 * @return       : Map<String,String>
	 * @param        : @param mobile
	 * @param        : @param password
	 * @param        : @param authcode
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月12日  上午11:33:10
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value = "/updatepaypwd.htm")
	public void updatePayPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String) request.getAttribute(Contants.USERID);
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String mobileid = "";
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				mobileid = memjson.optString("mobileid");
			}
		}
        //获取密码
		String pin = request.getParameter("pin");
		//获取验证码
		String authcode = request.getParameter("authcode");
		LOG.info("原文加密后为:password:{}", pin);
		LOG.debug("mobile:{}, password:{}", mobileid, pin);
		pin = RSAUtils.decryptStringByJs(pin);
		LOG.debug("还原密码:{}", pin);
		
		//用于发送请求
		Map<String, String> map = new HashMap<String, String>();
		//用于返回请求
		Iresp_common result = new Iresp_common();
		LOG.debug("#updatePayPwd() 请求参数 pin={}, authcode={}", pin, authcode);
		//请求参数校验
		if (StringUtils.isBlank(pin)
				|| StringUtils.isBlank(authcode)
				) {
			LOG.debug("#updatePayPwd() 请求参数不全pin={}, authcode={}", pin, authcode);
			result.setRet("fail");
			result.setMsg("请求参数不全");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		//组织请求信息
		map.put("userid", userid);
		map.put("mobileid", mobileid);
		map.put("pin", pin);
		map.put("authcode", authcode);
		
		LOG.debug("#updatePayPwd()：修改密码请求，接收入参类型map值={}", map);
		String bodys = gson.toJson(map);
		LOG.debug("#updatePayPwd()：请求参数转换Json={}", bodys);
		//获取url
		String url = getServiceURL("emall/api/member/updatepaypwd");
		//请求service 修改密码
		String results = HttpUtils.doPost(url, bodys.toString(), null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#updatePayPwd() 响应信息 result={}", results);
		if (StringUtils.isBlank(results)) {
			LOG.info("响应为空");
			result.setRet("fail");
			result.setMsg("服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		//返回请求
		Map<String, String> fromJson = gson.fromJson(results, new TypeToken<Map<String, String>>() {}.getType());
		LOG.info("#updatePayPwd() 响应信息 result={}", fromJson.toString());
		if (!"0000".equals((String) fromJson.get("ret"))) {
			LOG.info("发送失败：ret={}, msg={}", fromJson.get("ret"), fromJson.get("msg"));
			result.setRet("fail");
			result.setMsg((String) fromJson.get("msg"));
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.setRet("success");
		result.setMsg(fromJson.get("msg"));
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	
	/**
	 * ********************************************
	 * method name   : showaddress 
	 * description   : 展示收货地址页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月25日  上午11:43:34
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/showaddress.htm")
	public String showaddress(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String type = request.getParameter("type");
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		LOG.info("#showaddress() 请求参数为 type={}", type);
		modelMap.put("type", type);
		modelMap.put("loginToken", login_token);
		return "member/address";
	}
	
	/**
	 * ********************************************
	 * method name   : address 
	 * description   : 进入"收货地址"页面
	 * @return       : String
	 * @param        : @param userid
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017-4-12  下午10:32:20
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/address.htm")
	public void address(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userid = (String) request.getAttribute(Contants.USERID);
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		//用于返回请求
		List<Address> addressList = new ArrayList<Address>();
		LOG.info("#address() 请求参数 userid={}", userid);
		//用于返回请求
		Map<String, Object> result = new HashMap<String, Object>();
		
		//获取url
		String url =getServiceURL("emall/api/address/list/"+userid);
		String resultJson = HttpUtils.doGet(url, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#address() 响应信息 result={}", resultJson);
		addressList = gson.fromJson(resultJson, new TypeToken<List<Address>>() {}.getType());
		
		//返回请求
		String addresslist = "";
		if(addressList.size()>0){
			for(int i=0; i<addressList.size(); i++){
				if(addressList.get(i).getAddress().indexOf("|") > 0 && addressList.get(i).getAddress().indexOf(",") > 0){
					String addressidr = addressList.get(i).getAddress().replaceAll("\\|", " ").replaceAll(",", "");
					addressList.get(i).setAddress(addressidr);
				}
				addressList.get(i).setHidemobileid(StringUtil.hideMobile(addressList.get(i).getMobileid()));
			}
			addresslist = gson.toJson(addressList);
		}
		//放置查询数据的集合大小
		LOG.info("#address() 响应信息 result={}", addresslist);
		result.put("ret", "success");
		result.put("msg", addresslist);
		result.put("loginToken", login_token);
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	
	/**
	 * ********************************************
	 * method name   : editAddress 
	 * description   : 修改收货地址
	 * @return       : void
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月13日  下午4:52:25
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/editaddress.htm")
	public void editAddress(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		String userid = (String) request.getAttribute(Contants.USERID);
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String mobileid = request.getParameter("mobileid");
		String username = request.getParameter("username");
		String showCityPicker = request.getParameter("showCityPicker");
		String address = request.getParameter("address");
		String isdefault = request.getParameter("isdefault");
		String addressid = request.getParameter("addressid");
		//用于发送请求
		Map<String, String> map = new HashMap<String, String>();
		//用于返回请求
		Map<String, Object> result = new HashMap<String, Object>();
		LOG.info("#editAddress() 请求参数 mobileid={}, username={}, showCityPicker={}, address={}, isdefault={}, addressid={}",
				mobileid, username, showCityPicker, address, isdefault, addressid);
		//请求参数校验
		if (StringUtils.isBlank(mobileid)
				|| StringUtils.isBlank(username)
				|| StringUtils.isBlank(showCityPicker)
				|| StringUtils.isBlank(address)
				|| StringUtils.isBlank(isdefault)
				|| StringUtils.isBlank(addressid)
				) {
			LOG.info("#editAddress() 请求参数不全 mobileid={}, username={}, showCityPicker={}, address={}, isdefault={}, addressid={}",
					mobileid, username, showCityPicker, address, isdefault, addressid);
			result.put("ret", "fail");
			result.put("msg", "请求参数不全");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		//组织请求信息
		map.put("mobileid", mobileid);
		map.put("username", username);
		map.put("address", showCityPicker+"|"+address);
		map.put("isdefault", isdefault);
		map.put("addressid", addressid);
		map.put("userid", userid);
		
		LOG.info("#editAddress()：修改收货地址，接收入参类型map值={}", map);
		String bodys = gson.toJson(map);
		LOG.info("#editAddress()：请求参数转换Json={}", bodys);
		//获取url
		String url = getServiceURL("emall/api/address/modify/"+addressid);
		//请求service 修改收货地址
		String results = HttpUtils.doPost(url, bodys, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#editAddress() 响应信息 result={}", results);
		if (StringUtils.isBlank(results)) {
			LOG.info("响应为空");
			result.put("ret", "fail");
			result.put("msg", "服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		//返回请求
		if (!"0000".equals(results)) {
			LOG.info("发送失败：ret={}", results);
			result.put("ret", "fail");
			result.put("msg", "修改收货地址失败！");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.put("ret", "success");
		result.put("msg", "修改收货地址成功");
		result.put("loginToken", login_token);
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	
	/**
	 * ********************************************
	 * method name   : addAddress 
	 * description   : 添加收货地址
	 * @return       : void
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月13日  下午4:52:34
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/addaddress.htm")
	public void addAddress(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		String mobileid = request.getParameter("mobileid");
		String userid = (String) request.getAttribute(Contants.USERID);
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String username = request.getParameter("username");
		String showCityPicker = request.getParameter("showCityPicker");
		String showaddress = request.getParameter("showaddress");
		String isdefault = request.getParameter("isdefault");
		//用于发送请求
		Map<String, String> map = new HashMap<String, String>();
		//用于返回请求
		Map<String, Object> result = new HashMap<String, Object>();
		LOG.info("#addAddress() 请求参数 mobileid={}, userid={}, username={}, showCityPicker={}, showaddress={}",
				mobileid, userid, username, showCityPicker, showaddress);
		//请求参数校验
		if (StringUtils.isBlank(mobileid)
				|| StringUtils.isBlank(userid)
				|| StringUtils.isBlank(username)
				|| StringUtils.isBlank(showCityPicker)
				|| StringUtils.isBlank(showaddress)
				) {
			LOG.info("#addAddress() 请求参数不全 mobileid={}, userid={}, username={}, showCityPicker={}, address={}",
					mobileid, userid, username, showCityPicker, showaddress);
			result.put("ret", "fail");
			result.put("msg", "请求参数不全");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		//组织请求信息
		map.put("mobileid", mobileid);
		map.put("userid", userid);
		map.put("username", username);
		map.put("address", showCityPicker+"|"+showaddress);
		map.put("isdefault", isdefault);
		map.put("state", "2");
		
		LOG.info("#addAddress()：添加收货地址请求，接收入参类型map值={}", map);
		String bodys = gson.toJson(map);
		LOG.info("#addAddress()：请求参数转换Json={}", bodys.toString());
		//设置请求头和请求体
		String url = getServiceURL("emall/api/address/add");
		//请求service 新增收货地址
		String results = HttpUtils.doPost(url, bodys.toString(), null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#addAddress() 响应信息 result={}", results);
		if (StringUtils.isBlank(results)) {
			LOG.info("响应为空");
			result.put("ret", "fail");
			result.put("msg", "服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		//返回请求
		if ("fail".equals(results)) {
			LOG.info("发送失败：ret={}", results);
			result.put("ret", "fail");
			result.put("msg", "添加收货地址失败！");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		map.put("hidemobileid", StringUtil.hideMobile(mobileid));
		map.put("addressid", results);
		map.put("address", showCityPicker+" "+showaddress);
		map.remove("userid");
		map.remove("state");
		result.put("ret", "success");
		result.put("msg", gson.toJson(map));
		result.put("loginToken", login_token);
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	
	/**
	 * ********************************************
	 * method name   : addressAddView 
	 * description   : 展示添加收货地址页面展示
	 * @return       : String
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月15日  下午9:15:34
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value="/address_add.htm")
	public String addressAddView(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		modelMap.addAttribute("loginToken", login_token);
		return "member/address_add";
	}
	
	/**
	 * ********************************************
	 * method name   : addressEditView 
	 * description   : 展示修改收货地址页面
	 * @return       : ModelAndView
	 * @param        : @param userid
	 * @param        : @param addressid
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月13日  下午8:07:28
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/address_edit/{addressid}.htm")
	public String addressEditView(ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response, @PathVariable("addressid") String addressid){
		String userid = (String) request.getAttribute(Contants.USERID);
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		//用于返回请求
        Address address=new Address();
		LOG.info("#addressEditView() 请求参数 userid={}, addressid={}", userid, addressid);
		
		//设置请求头和请求体
		String url = getServiceURL("emall/api/address/"+userid+"/"+addressid);
		String resultJson = HttpUtils.doGet(url, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#addressEditView() 响应信息 result={}", resultJson);
		//返回请求
		address = gson.fromJson(resultJson, Address.class);
		String addressName = address.getAddress();
		String addressA = "";
		String addressB = "";
		String addressC = "";
		if(addressName != null && !"".equals(addressName)){
			addressA = addressName.split("\\|")[0];
			if(addressA.indexOf(",") > 0){
				addressC = addressA.replaceAll(",", "");
			}
			addressB = addressName.split("\\|")[1];
		}
		modelMap.addAttribute("addressA", addressA);
		modelMap.addAttribute("addressB", addressB);
		modelMap.addAttribute("addressC", addressC);
		modelMap.addAttribute("address", address);
		modelMap.addAttribute("loginToken", login_token);
		return "member/address_edit";
	}
	
	/**
	 * ********************************************
	 * method name   : setDefault 
	 * description   : 设置默认收货地址
	 * @return       : void
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月13日  下午5:51:27
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/setdefault.htm")
	public void setDefault(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Address address) throws Exception{
		String userid = (String) request.getAttribute(Contants.USERID);
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String addressid = address.getAddressid();
		//用于发送请求
		Map<String, String> map = new HashMap<String, String>();
		//用于返回请求
		Map<String, Object> result = new HashMap<String, Object>();
		LOG.info("#setDefault() 请求参数 userid={}, addressid={}", userid, addressid);
		//请求参数校验
		if (StringUtils.isBlank(userid) || StringUtils.isBlank(addressid)) {
			LOG.info("#setDefault() 请求参数不全 userid={}, addressid={}", userid, addressid);
			result.put("ret", "fail");
			result.put("msg", "请求参数不全");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		//组织请求信息
		map.put("userid", userid);
		map.put("addressid", addressid);
		
		LOG.info("#setDefault()：设置默认收货地址，接收入参类型map值={}", map);
		String bodys = gson.toJson(map);
		LOG.info("#setDefault()：请求参数转换Json={}", bodys);
		//设置请求头和请求体
		String url = getServiceURL("emall/api/address/default/"+userid+"/"+addressid);
		//请求service 设置默认收货地址
		String results = HttpUtils.doPost(url, bodys, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#setDefault() 响应信息 result={}", results);
		if (StringUtils.isBlank(results)) {
			LOG.info("响应为空");
			result.put("ret", "fail");
			result.put("msg", "服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		//返回请求
		if (!"0000".equals(results)) {
			LOG.info("发送失败：ret={}", results);
			result.put("ret", "fail");
			result.put("msg", "设置默认收货地址失败！");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.put("ret", "success");
		result.put("msg", "设置默认收货地址成功");
		result.put("loginToken", login_token);
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	
	/**
	 * ********************************************
	 * method name   : deleteAddress 
	 * description   : 删除收货地址
	 * @return       : void
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月13日  下午5:55:05
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/deleteaddress.htm")
	public void deleteAddress(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Address address) throws Exception{
		String userid = (String) request.getAttribute(Contants.USERID);
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String addressid = address.getAddressid();
		//用于发送请求
		Map<String, String> map = new HashMap<String, String>();
		//用于返回请求
		Map<String, Object> result = new HashMap<String, Object>();
		LOG.info("#deleteAddress() 请求参数 userid={}, addressid={}", userid, addressid);
		//请求参数校验
		if (StringUtils.isBlank(userid) || StringUtils.isBlank(addressid)) {
			LOG.info("#setDefault() 请求参数不全 userid={}, addressid={}", userid, addressid);
			result.put("ret", "fail");
			result.put("msg", "请求参数不全");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		//组织请求信息
		map.put("userid", userid);
		map.put("addressid", addressid);
		
		LOG.info("#deleteAddress()：设置默认收货地址，接收入参类型map值={}", map);
		String bodys = gson.toJson(map);
		LOG.info("#deleteAddress()：请求参数转换Json={}" , bodys);
		//设置请求头和请求体
		String url = getServiceURL("emall/api/address/delete/"+userid+"/"+addressid);
		//请求service 设置默认收货地址
		String results = HttpUtils.doPost(url, bodys, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#deleteAddress() 响应信息 result={}", results);
		if (StringUtils.isBlank(results)) {
			LOG.info("响应为空");
			result.put("ret", "fail");
			result.put("msg", "服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		//返回请求
		if (!"0000".equals(results)) {
			LOG.info("删除收货地址失败：ret={}", results);
			result.put("ret", "fail");
			result.put("msg", "删除收货地址失败！");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.put("ret", "success");
		result.put("msg", "删除收货地址成功");
		result.put("loginToken", login_token);
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	
	/**
	 * ********************************************
	 * method name   : sendAuthCode 
	 * description   : 发送验证码
	 * @return       : Map<String,String>
	 * @param        : @param mobileid
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月14日  下午4:27:56
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@RequestMapping(value = "/sendauthcode.do")
	public void sendAuthCode(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String mobileid = "";
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			if(StringUtils.isNotBlank(memberInfo)){
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				mobileid = memjson.optString("mobileid");
			}
		}
		// 用于响应
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isBlank(mobileid)) {
			result.put("ret", "fail");
			result.put("msg", "请输入手机号");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		boolean ismobileid = Verify.isMobileNO(mobileid);
		if (!ismobileid) {
			result.put("ret", "fail");
			result.put("msg", "请输入正确手机号");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}

		String url = getServiceURL("emall/api/member/sendauthcode/" + mobileid);
		String results = HttpUtils.doPost(url, null, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("响应结果:" + result);
		if (StringUtils.isBlank(results)) {
			LOG.info("响应为空");
			result.put("ret", "fail");
			result.put("msg", "服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		//返回请求
		Map<String, String> fromJson = gson.fromJson(results, new TypeToken<Map<String, String>>() {}.getType());

		if (!"0000".equals((String) fromJson.get("ret"))) {
			LOG.info("发送失败：ret={} , msg={}", fromJson.get("ret"), fromJson.get("msg"));
			result.put("ret", "fail");
			result.put("msg", (String) fromJson.get("msg"));
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.put("ret", "success");
		result.put("msg", fromJson.get("msg"));
		String content = gson.toJson(result);
		super.write(response, content);
		return;

	}
	
	/**
	 * ********************************************
	 * method name   : sendcode 
	 * description   : 发送验证码
	 * @return       : Map<String,String>
	 * @param        : @param mobileid
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月14日  下午4:27:56
	 * @see          : 
	 * *******************************************
	 */
	@RequestMapping(value = "/sendcode.do")
	public void sendcode(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//获取手机号
		String mobileid = request.getParameter("mobileid");
	
		// 用于响应
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isBlank(mobileid)) {
			result.put("ret", "fail");
			result.put("msg", "请输入手机号");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		boolean ismobileid = Verify.isMobileNO(mobileid);
		if (!ismobileid) {
			result.put("ret", "fail");
			result.put("msg", "请输入正确手机号");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("mobileid", mobileid);
		String url = getServiceURL("emall/api/member/checkCodeSend");
		String body = gson.toJson(bodys);
		String results = HttpUtils.doPost(url, body.toString(), null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("响应结果:" + result);
		if (StringUtils.isBlank(results)) {
			LOG.info("响应为空");
			result.put("ret", "fail");
			result.put("msg", "服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		//返回请求
		Map<String, String> fromJson = gson.fromJson(results, new TypeToken<Map<String, String>>() {}.getType());

		if (!"0000".equals((String) fromJson.get("ret"))) {
			LOG.info("发送失败：ret={} , msg={}", fromJson.get("ret"), fromJson.get("msg"));
			result.put("ret", "fail");
			result.put("msg", (String) fromJson.get("msg"));
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		//判断手机号之前是否有注册过
		String memberurl = getServiceURL("emall/api/member/memberlist");
		String memberbody = gson.toJson(bodys);
		String memberresults = HttpUtils.doPost(memberurl, memberbody.toString(), null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("响应结果:" + memberresults);
		if(StringUtil.isBlank(memberresults)){
			result.put("ret", "fail");
			result.put("msg", "校验手机号是否注册失败！");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		List<Member> memberList = new ArrayList<Member>();
		memberList = gson.fromJson(memberresults, new TypeToken<List<Member>>() {}.getType());
		if (memberList.size() == 0) {
			LOG.info("响应为空");
			//没有注册信息 就直接返回到页面了
			result.put("ret", "fail");
			result.put("msg", "校验手机号是否注册失败！");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.put("ret", "membersuccess");
		result.put("msg", "该手机号已绑定，可以直接登录");
		String content = gson.toJson(result);
		super.write(response, content);
		return;

	}
	/**
	 * ********************************************
	 * method name   : showDetail 
	 * description   : 绑定手机号时已存在的直接跳到指定页面
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月18日  下午4:47:27
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/showDetail.htm")
	public void showDetail(HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String mobileid = request.getParameter("mobileid");
		String code = request.getParameter("code");
		//获取登录号
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		String memberInfoJson = RedisPool.get(login_token);
		JSONObject memberInfo = JSONObject.fromObject(memberInfoJson);
		String oauth_user_id = memberInfo.optString("openid");
		LOG.info("openid为："+oauth_user_id);
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("mobileid", mobileid);
		//判断手机号之前是否有注册过
		String memberurl = getServiceURL("emall/api/member/memberlist");
		String memberbody = gson.toJson(bodys);
		String memberresults = HttpUtils.doPost(memberurl, memberbody.toString(), null, 3, 10, Contants.CHARSET_UTF_8);
		//有注册信息 就更改第三方授权用户表 的userid
		List<Member> memberList = new ArrayList<Member>();
		memberList = gson.fromJson(memberresults, new TypeToken<List<Member>>() {}.getType());
		String userid = memberList.get(0).getUserid();
		Map<String,String> map =new HashMap<String, String>();
		map.put("oauth_user_id", oauth_user_id);
		map.put("userid", userid);
		map.put("authcode", code);
		map.put("mobileid", mobileid);
		map.put("login_token", login_token);
		String ourl = getServiceURL("emall/api/member/modifyOauthUser");
		String obody = gson.toJson(map);
		String oresults = HttpUtils.doPost(ourl, obody.toString(), null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("响应结果:" + oresults);
		if (StringUtils.isBlank(oresults)) {
			result.put("ret", "fail");
			result.put("msg", "服务器忙，请稍后再试");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		if("failone".equals(oresults)){
			result.put("ret", "fail");
			result.put("msg", "验证码已过期或者未获取");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		if("failtwo".equals(oresults)){
			result.put("ret", "fail");
			result.put("msg", "验证码输入不正确");
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.put("ret", "success");
		String login_token1 = getLoginToken(request,response);
		String redirect = RedisPool.get(Contants.BINDREDIRCT+login_token1);
		LOG.info("存储的redirect的地址：msg={}", redirect);
		result.put("msg", redirect);
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	/**
	 * ********************************************
	 * method name   : bindMobile 
	 * description   : 绑定手机展示页面处理方法
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月18日  下午4:47:27
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value="/bindmobile.htm")
	public String bindMobile(ModelMap modelMap){
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		modelMap.addAttribute("module", publicKeyMap.getModulus());
		modelMap.addAttribute("expoent", publicKeyMap.getExponent());
		return "member/bind";
	}
	
	/**
	 * ********************************************
	 * method name   : addMobile 
	 * description   : 绑定手机号
	 * @return       : void
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @throws Exception
	 * modified      : zhangyajie1@umpay.com ,  2017年4月18日  下午4:47:56
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value = "/addmobile.htm")
	public void addMobile(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String userid = (String) request.getAttribute("userid");
		String mobileid = request.getParameter("mobileid");
		String password = request.getParameter("password");
		String login_token = getLoginToken(request,response);
		//还原密码
		password = RSAUtils.decryptStringByJs(password);
		String code = request.getParameter("code");
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> result = new HashMap<String, String>();
		if ("".equals(mobileid) || "".equals(password) || null == mobileid || null == password) {
			LOG.info("请求参数格式有误!");
			result.put("ret", "fail");
			result.put("msg", "请求参数格式有误!");
			String resultJson = gson.toJson(result, map.getClass());
			super.write(response, resultJson);
			return;
		}
		map.put("userid", userid);
		map.put("mobileid", mobileid);
		map.put("passwd", password);
		map.put("authcode", code);
		map.put("login_token", login_token);
		LOG.info("web平台：绑定手机请求，接收入参类型map 值：" + map);
		String bodys = gson.toJson(map);
		LOG.info("web平台：请求参数转换Json" + bodys.toString());
		String url = getServiceURL("emall/api/member/updateuser");
		String resultJson = HttpUtils.doPost(url, bodys, null, 3, 10, Contants.CHARSET_UTF_8);
		//返回请求
		Map<String, String> fromJson = gson.fromJson(resultJson, new TypeToken<Map<String, String>>() {}.getType());
		
		if (!"0000".equals((String) fromJson.get("ret"))) {
			LOG.info("发送失败：ret={} , msg={}", fromJson.get("ret"), fromJson.get("msg"));
			result.put("ret", "fail");
			result.put("msg", (String) fromJson.get("msg"));
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		result.put("ret", "success");
		String redirect = RedisPool.get(Contants.BINDREDIRCT+login_token);
		LOG.info("存储的redirect的地址：msg={}", redirect);
		result.put("msg", redirect);
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
	
	/**
	 * ********************************************
	 * method name   : getLoginToken 
	 * description   : 获取token
	 * @return       : String
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月18日  下午5:05:18
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
