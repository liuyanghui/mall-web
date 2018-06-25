package com.mall.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ******************  类说明  *********************
 * class       :  IPUtil
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  ip地址获取公共处理类
 * @see        :                        
 * ***********************************************
 */
public class IPUtil {

	/**
	 * ********************************************
	 * method name   : getRemoteAddress 
	 * description   : 获取远程请求客户端ip地址
	 * @return       : String
	 * @param        : @param request
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2016-11-21  下午6:40:40
	 * @see          : 
	 * *******************************************
	 */
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
