package com.mall.util;

import java.io.IOException;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

/**
 * ****************** 类说明 ********************* class : Verify
 * 
 * @author : lihu
 * @version : 1.0 description : 用于校验
 * @see :
 * @date : 下午3:46:39 ***********************************************
 */
public class Verify {

	/**
	 * ******************************************** method name : isMobileNO
	 * description :手机号校验
	 * 
	 * @return : boolean
	 * @param : @param mobiles
	 * @param : @return modified : lihu , 2017年4月11日 下午3:46:57
	 * @see : *******************************************
	 */
	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0-3]|[5-9]))|(18[0-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);


		return m.matches();

	}
	
	public static boolean isPwd(String pwd) {
		
		Pattern p = Pattern
				.compile("[\\da-zA-Z]{8,20}");
		
		Matcher m = p.matcher(pwd);
		
		
		return m.matches();
		
	}
	
	
	public static void main(String[] args) {
		System.out.println(isMobileNO("18717721663"));
		
	}
}