package com.mall.controller.web.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.mall.contant.Contants;
import com.mall.util.PropertiesUtils;

/**
 * ******************  类说明  *********************
 * class       :  BaseCtl
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  Controller基类
 * @see        :                        
 * ***********************************************
 */
@Controller
public class BaseCtl {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseCtl.class);
	@Autowired
	protected Gson gson;
	
	protected static final String CONTENT_TYPE_APPLICATION_JSON = "application/json;charset=utf-8";
	protected static final String CONTENT_TYPE_APPLICATION_HTML = "application/html";
	
	/**
	 * ********************************************
	 * method name   : getServiceDomain 
	 * description   : 
	 * @return       : String
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2017-4-13  下午2:57:58
	 * @see          : 
	 * *******************************************
	 */
	public String getServiceDomain(){
		String domain = "http://localhost:9000";
		return domain;
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
	 * method name   : getSeviceURL 
	 * description   : 获取服务请求url
	 * @return       : String
	 * @param        : @param uri
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2017-4-13  下午3:10:58
	 * @see          : 
	 * *******************************************
	 */
	public String getServiceURL(String uri){
		String domain = this.getServiceDomain();
		return getServiceURL(domain, uri);
	}
	
	/**
	 * ********************************************
	 * method name   : getServiceURL 
	 * description   : 获取服务请求url
	 * @return       : String
	 * @param        : @param domain
	 * @param        : @param uri
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2017-4-13  下午2:59:26
	 * @see          : 
	 * *******************************************
	 */
	public String getServiceURL(String domain, String uri){
		StringBuffer buffer = new StringBuffer();
		buffer.append(domain);
		buffer.append("/");
		buffer.append(uri);
		return buffer.toString();
	}
	
	/**
	 * ********************************************
	 * method name   : write 
	 * description   : 响应
	 * @return       : void
	 * @param        : @param response
	 * @param        : @param content
	 * @param        : @throws Exception
	 * modified      : hejinyun@umpay.com ,  2017-4-13  下午3:42:43
	 * @see          : 
	 * *******************************************
	 */
	public void write(HttpServletResponse response, 
			String content) throws Exception{
		write(response, content, CONTENT_TYPE_APPLICATION_JSON);
	}
	
	/**
	 * ********************************************
	 * method name   : write 
	 * description   : 响应
	 * @return       : void
	 * @param        : @param response
	 * @param        : @param content
	 * @param        : @param contentType
	 * @param        : @throws Exception
	 * modified      : hejinyun@umpay.com ,  2017-4-13  下午3:43:15
	 * @see          : 
	 * *******************************************
	 */
	public void write(HttpServletResponse response, 
			String content, String contentType) throws Exception{
		response.setContentType(contentType);
		PrintWriter writer = response.getWriter();
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	public String read(HttpServletRequest request, boolean decoder)
			throws Exception {
		
		String method = request.getMethod();
		String encoding = request.getCharacterEncoding();
		encoding = (null == encoding) ? Contants.CHARSET_UTF_8 : encoding;
		if (Contants.POST.equals(method)) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), encoding));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while (null != (line = br.readLine())) {
				buffer.append(line);
			}
			return decoder ? URLDecoder.decode(buffer.toString(), Contants.CHARSET_UTF_8):buffer.toString();
		}
		return decoder ? URLDecoder.decode(request.getQueryString(), Contants.CHARSET_UTF_8):request.getQueryString();
	}
	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getClientIpAddr(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
