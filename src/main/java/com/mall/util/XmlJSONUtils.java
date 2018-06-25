/** *****************  JAVA头文件说明  ****************
 * file name  :  XmlJSONUtils.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-17
 * *************************************************/ 

package com.mall.util;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;


/**
 * ******************  类说明  *********************
 * class       :  XmlJSONUtils
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  xml与json互转工具类
 * @see        :                        
 * ***********************************************
 */
public class XmlJSONUtils {
	
	/**
	 * ********************************************
	 * method name   : xml2JSON 
	 * description   : xml转换成json
	 * @return       : String
	 * @param        : @param xml
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2015-8-5  下午6:44:33
	 * @see          : 
	 * *******************************************
	 */
	public static String xml2JSON(String xml){
		return new XMLSerializer().read(xml).toString();
	}
	
	/**
	 * ********************************************
	 * method name   : json2XML 
	 * description   : json转换成xml
	 * @return       : String
	 * @param        : @param json
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2015-8-5  下午6:44:52
	 * @see          : 
	 * *******************************************
	 */
	public static String json2XML(String json){
		JSONObject jsonObject = JSONObject.fromObject(json);
		return new XMLSerializer().write(jsonObject);
	}
}
