/** *****************  JAVA头文件说明  ****************
 * file name  :  ReqUtils.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-17
 * *************************************************/ 

package com.mall.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.mall.contant.Contants;


/** ******************  类说明  *********************
 * class       :  ReqUtils
 * @author     :  xuhuafeng
 * @version    :  1.0  
 * description :  
 * ************************************************/

public class ReqUtils {
	
private static final Logger log = Logger.getLogger(ReqUtils.class);
	
	/**
	 * ********************************************
	 * method name   : getClientIpAddr 
	 * description   : 获取客户端请求真实ip地址
	 * @return       : String
	 * @param        : @param request
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : hejinyun@umpay.com ,  2015-7-27  下午3:45:32
	 * @see          : 
	 * *******************************************
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
	
	/**
	 * ********************************************
	 * method name   : getRpid 
	 * description   : 获取rpid
	 * @return       : String
	 * @param        : @param prefix
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2015-8-7  上午10:32:50
	 * @see          : 
	 * *******************************************
	 */
	public static String getRpid(String prefix){
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		buffer.append(System.currentTimeMillis());
		buffer.append(String.format("%04d", new Random().nextInt(10000)));
		return buffer.toString();
	}
	
	/**
	 * ********************************************
	 * method name   : parser 
	 * description   : 请求参数解析
	 * @return       : Map<String,String>
	 * @param        : @param buffer
	 * @param        : @param params
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2015-8-7  上午10:48:57
	 * @see          : 
	 * *******************************************
	 */
	public static Map<String, String> parser(String buffer, String...params){
		Map<String, String> parametersMap = new HashMap<String, String>();
		if ((null == buffer) || (StringUtil.isBlank(buffer))){
			log.info("#parser() 请求参数为空!");
			return parametersMap;
		}
		String[] args = buffer.split(params[0]);
		for (String arg : args){
			String[] arg_key_value = arg.split(params[1], 2);
			int length = arg_key_value.length;
			if (1 == length){
				parametersMap.put(StringUtil.trim(arg_key_value[0]), "");
			} else {
				parametersMap.put(StringUtil.trim(arg_key_value[0]), StringUtil.trim(arg_key_value[1]));
			}
		}
		return parametersMap;	
	}
	
	
	public static String getSignWithMD5(Map<String, Object> parametersMap, String md5key, String encode){
		//按字母先后顺序排序
		Object[] keys = parametersMap.keySet().toArray();
		Arrays.sort(keys);
		int length = keys.length;
		StringBuffer buffer = new StringBuffer();
		for (int index = 0; index < length; index++){
			String key = (String)keys[index];
			Object value = parametersMap.get(key);
			buffer.append(key).append(Contants.SYMBOL_EQUAL).append(value).append(Contants.SYMBOL_AND);
		}
		buffer.append("key").append(Contants.SYMBOL_EQUAL).append(md5key);
		String source = buffer.toString();
		log.debug("#getSign() 签名原串:"+source);
		String signature = MD5Utils.md5sign(source, encode);
		return signature;
	}
	
	/**
	 * ********************************************
	 * method name   : map2XML 
	 * description   : Map转成成XML字符串
	 * @return       : String
	 * @param        : @param parametersMap
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2015-8-10  下午2:14:11
	 * @see          : 
	 * *******************************************
	 */
	public static String map2XML(Map<String, Object> parametersMap){
		if ((null == parametersMap) || (0 == parametersMap.size())){
			return "";
		}
		Object[] keys = parametersMap.keySet().toArray();
		//Arrays.sort(keys); // 排序会消耗性能,暂时去掉
		int size = keys.length;
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>");
		for (int index = 0; index < size; index ++){
			String key = (String) keys[index];
			Object value = parametersMap.get(key);
			xml.append("<"+key+">").append(value).append("</"+key+">");
		}
		xml.append("</xml>");
		String result = xml.toString();
		log.debug("#map2XML() 转换结果:"+result);
		return result;
	}
	
	/**
	 * ********************************************
	 * method name   : verifySignWithMD5 
	 * description   : MD5签名验证
	 * @return       : boolean
	 * @param        : @param parametersMap
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2015-8-11  上午10:28:22
	 * @see          : 
	 * *******************************************
	 */
	public static boolean verifySignWithMD5(Map<String, String> parametersMap, String md5key){
		if ((null == parametersMap) || (0 == parametersMap.size())){
			return false;
		}
		String signature = parametersMap.get("sign");
		parametersMap.remove("sign");
		Object[] keys = parametersMap.keySet().toArray();
		int size = keys.length;
		StringBuffer buffer = new StringBuffer();
		for (int index = 0; index < size; index++){
			String key = (String)keys[index];
			String value = parametersMap.get(key);
			buffer.append(key).append(Contants.SYMBOL_EQUAL).append(value).append(Contants.SYMBOL_AND);
		}
		buffer.append("key").append(Contants.SYMBOL_EQUAL).append(md5key);
		String source = buffer.toString();
		log.debug("#verifySignWithMD5() 签名原串:"+source);
		return MD5Utils.checkMD5(source, signature);
	}
	
	/**
	 * ********************************************
	 * method name   : checkSign 
	 * description   : 验证域校验
	 * @return       : boolean
	 * @param        : @param source
	 * @param        : @param validfield
	 * @param        : @return
	 * modified      : xuhuafeng ,  2016-2-23  下午03:11:19
	 * *******************************************
	 */
	public static boolean checkValidfiled(String source, String validfield){
		if (StringUtil.isBlank(validfield)){
			return false;
		}
		if (validfield.equals(source)){
			return true;
		}
		return false;
	}

}
