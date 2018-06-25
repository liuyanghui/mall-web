/**
 * 业务平台请求，支付平台应答
 * */
package com.mall.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



@SuppressWarnings("unchecked")
public class ProceMsgProxy {
	private static Log _log = LogFactory.getLog(ProceMsgProxy.class);
	
	public static void main(String[] args) throws Exception {
		String str = "<?xml version='1.0' encoding='UTF-8'?><MESSAGE><FUNCODE>QMYBTZ</FUNCODE><MID>5800000000000000</MID><REQDATE>20170223</REQDATE><REQTIME>102957</REQTIME><RETCODE>9995</RETCODE><RETMSG>验签失败</RETMSG><SIGN>ZUmqSKfWfl4K+4d8O2qVcVs1x22efApgp10mcpQtK1c98g6Uag/MqbLfHCzV5UvbnV3LsNCcOLRrvRL6JY3PRFmj8bUTty7KV/S0SlkoYXENgVtInbYBJ1Icge8PRkVLVMr5bPC4zath++jf459ZEWnr96T9HoyBmf/l9xuDuas=</SIGN></MESSAGE>";
		String str1 = "<?xml version='1.0' encoding='UTF-8'?><MESSAGE>"+
      "<FUNCODE>QMYBTZ</FUNCODE>"+
      "<MID>5800000000000000</MID>"+
     "<REQDATE>20170223</REQDATE>"+
    "<REQTIME>105311</REQTIME>"+
  "<RETCODE>0000</RETCODE>"+
  "<RETMSG>成功</RETMSG>"+
  "<SIGN>XZXlDZ4dC+DJLH7p7KYY/p/5+pTNgpXFwd3kD0QnuGZv+pG51BLuLBkGrbL3b4GQ7jWlzUaP8+OhWrHXQpK0WXbXiPP7fSKghvjSYypoXfsgV2Oox1PnEOu1Us88N45+AUvRZyS8RnpkGy/OyN6eP421sPfHgHmlf/w3MVfHnKE=</SIGN>"+
   "</MESSAGE>";
		ProceMsgProxy.getMessageXml2Map(str1,false);
	}
	/**
	 * 将报文xml转换成map
	 * @param String dataXML 报文
	 * @param 请求、响应标志位；请求:true; 响应:false
	 * @return Map<支付平台KEY>
	 * */
	public static Map<String, Object> getMessageXml2Map(String dataXML, boolean isReq) throws Exception {
		if (dataXML != null && !dataXML.equals("")) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (!dataXML.startsWith("<?xml")) {
				String xmlHeader = "<?xml version='1.0' encoding='GBK'?>";
				dataXML = xmlHeader + dataXML;
			}
			StringBuffer signstr = new StringBuffer();
			try {
				_log.info("dataXML:"+dataXML);
				Document doc = XmlUtil.parseXmlText(dataXML);
				doc.normalize();
				Element root = (Element) doc.getElementsByTagName("MESSAGE").item(0);
				NodeList nodeList = root.getChildNodes();

				for (int i = 0; i < nodeList.getLength(); i++) {
					String key = nodeList.item(i).getNodeName();
					Node child = nodeList.item(i).getFirstChild();
					String value = "";
				/*	 _log.info("key:"+key);
					 _log.info("value:"+value);
					 _log.info("child:"+child);*/
					if (child != null) {
						value = child.getNodeValue();
						dataMap.put(key, value);
					}
					if ( !"SIGN".equals(key)&&!"#text".equals(key)) {
						signstr.append(key).append("=").append(value).append("&");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("支付请求时，解包异常！！！");
			}
			//String signString = SignStr.getSignStr(dataMap, isReq);
			 String signString = signstr.substring(0, signstr.length() - 1);
			 dataMap.put("SIGNSTR", signString);
			_log.debug("end;传入map数据：==========>>>>>>>>>>>\n" + dataMap);
			return dataMap;
		} else {
			return null;
		}
	}
	
	

	
	private static void spellStb2Xml(StringBuffer stb, String key, String value) {
		stb.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
	}	
	

}




