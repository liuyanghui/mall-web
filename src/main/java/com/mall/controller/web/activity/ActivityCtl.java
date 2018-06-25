package com.mall.controller.web.activity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.mall.annotation.LoginValid;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.ActivitySignin;
import com.mall.controller.web.vo.Iresp_common;
import com.mall.controller.web.vo.Signin;
import com.mall.util.HttpUtils;
import com.mall.util.RedisPool;

/**
 * ******************  类说明  *********************
 * class       :  ActivityCtl
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  "积分活动"视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/activity")
public class ActivityCtl extends BaseCtl {
	
	private static final Logger LOG = LoggerFactory.getLogger(ActivityCtl.class);
	@Autowired
	private Gson gson;
	
	/**
	 * ********************************************
	 * method name   : signin 
	 * description   : 进入立即"签到页面"
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : hejinyun@umpay.com ,  2017-4-12  下午5:47:29
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@GetMapping(value="/signin.htm")
	public String signin(ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		String userid = (String)request.getAttribute(Contants.USERID);
		String url = super.getServiceURL("/emall/api/activity/"+userid);
		String respJson = HttpUtils.doGet(url, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#signin() 用户签到情况:{}", respJson);
		ActivitySignin activitySignin = gson.fromJson(respJson, ActivitySignin.class);
		List<Signin> signinList = new ArrayList<Signin>();
		String[] week = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		if (null != activitySignin){
			// 累计签到次数
			String cachePoints = RedisPool.get("ACTIVITY-SIGNIN-POINTS");
			int points = StringUtils.isBlank(cachePoints)?0:Integer.valueOf(cachePoints);
			Short[] signinArray = {	activitySignin.getSunday(),
									activitySignin.getMonday(),
									activitySignin.getTuesday(),
									activitySignin.getWednesday(),
									activitySignin.getThursday(),
									activitySignin.getFriday(),
									activitySignin.getSaturday()};
			int signinDays = 0; // 默认本周签到0天
			int today = activitySignin.getDay();
			int size = signinArray.length;
			Signin signin = null;
			for (int index = 0; index < size; index++){
				signinDays = signinDays + signinArray[index]; // 循环计算每周当前已签到天数
				int cindex = index+1; // 每周从周日开始，下标为1，周六为7
				// 设置每天签到、积分情况
				String day = week[index];
				signin = new Signin();
				signin.setDay(day);
				signin.setPoints(points);
				// 0:未签到，1：已签到;
				signin.setIsSigned((1 == signinArray[index])?true:false);
				// 日期<当天，不能签到
				if (cindex < today){
					signin.setIsOver("Yes");
					signin.setIsSignin(false);
					signinList.add(signin);
					continue;
				}
				// 日期=当天，可以签到
				if (cindex == today){
					signin.setIsOver("Equal");
					signin.setIsSignin(true);
					signinList.add(signin);
					if (signin.getIsSigned()){
						modelMap.addAttribute("isSigned", true);
					}
					continue;
				}
				// 日期>当天，不能签到
				if (cindex > today){
					signin.setIsOver("No");
					signin.setIsSignin(false);
					signinList.add(signin);
					continue;
				}
			}
			modelMap.addAttribute("signinDays", signinDays);
			modelMap.addAttribute("signinList", signinList);
			modelMap.addAttribute("points", points);
		} else {
			for (int index = 0; index < 7; index++){
				Signin signin = new Signin();
				signin.setDay(week[index]);
				signin.setIsOver("Y");
				signin.setIsSigned(false);
				signin.setIsSignin(false);
				signin.setPoints(0);
			}
			modelMap.addAttribute("signinDays", 0);
			modelMap.addAttribute("signinList", signinList);
		}
		String prizePoint = RedisPool.get("ACTIVITY-SIGNIN-PRIZE-POINTS");
		int prizePoints = StringUtils.isBlank(prizePoint)?0:Integer.valueOf(prizePoint);
		modelMap.addAttribute("prizePoints", prizePoints);
		return "activity/signin";
	}
	
	
	/**
	 * ********************************************
	 * method name   : doSignin 
	 * description   : 签到
	 * @return       : Iresp_common
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : hejinyun@umpay.com ,  2017-4-13  下午2:23:04
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@PostMapping(value="/dosignin.htm")
	public void doSignin(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		String userid = (String)request.getAttribute(Contants.USERID);
		String url = super.getServiceURL("/emall/api/activity/signin/"+userid);
		String respJson = HttpUtils.doPost(url, null, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#doSignin() 用户签到响应：{}", respJson);
		if (StringUtils.isBlank(respJson)){
			String content = gson.toJson(new Iresp_common("9999", "签到失败：系统异常！"));
			super.write(response, content);
			return ;
		}
		super.write(response, respJson);
	}
	
	@GetMapping(value="/gopresent.htm")
	public String goPresent(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		return "activity/present";
	}
	
	@GetMapping(value="/minglu.htm")
	public String minglu(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		return "activity/minglu";
	}
	
}
