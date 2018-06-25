package com.mall.util;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



public class XmlUtil {
  
  public static Document parseXmlText(String xml) throws Exception {
    ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes("gbk"));
    return parseXmlInputSource(new InputSource(bais));
  }



  public static Document parseXmlInputStream(InputStream inputStream) throws Exception {
    Document document = null;
    try {
      document  = getDocumentBuilder().parse(inputStream);
    } catch (Exception e) {
      throw new Exception("couldn't parse xml", e);
    }
    return document;
  }

  public static Document parseXmlInputSource(InputSource inputSource) throws Exception {
    Document document = null;
    try {
      document  = getDocumentBuilder().parse(inputSource);
    } catch (Exception e) {
      throw new Exception("couldn't parse xml", e);
    }
    return document;
  }

  public static DocumentBuilder getDocumentBuilder() throws FactoryConfigurationError, ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    return factory.newDocumentBuilder();
  }

  @SuppressWarnings("unchecked")
public static Iterator elementIterator(Element element, String tagName) {
    return elements(element, tagName).iterator();
  }

  @SuppressWarnings("unchecked")
public static List elements(Element element, String tagName) {
    NodeList nodeList = element.getElementsByTagName(tagName);
    List elements = new ArrayList(nodeList.getLength());
    for (int i=0; i<nodeList.getLength(); i++) {
      Node child = nodeList.item(i);
      if(child.getParentNode()==element) {
        elements.add(child);
      }
    }
    return elements;
  }

  public static String getElementValueByTagName(Document doc,String tagName){
	  NodeList nodeList = doc.getElementsByTagName(tagName);
	  if(nodeList != null && nodeList.getLength()>0){
		  return nodeList.item(0).getFirstChild().getNodeValue();
	  }else{
		  return null;
	  }
  }
  
  public static Element element(Element element, String name) {
    Element childElement = null;
    NodeList nodeList = element.getElementsByTagName(name);
    if (nodeList.getLength()>0) {
      childElement = (Element) nodeList.item(0);
    }
    return childElement;
  }

  @SuppressWarnings("unchecked")
public static Iterator elementIterator(Element element) {
    return elements(element).iterator();
  }

  @SuppressWarnings("unchecked")
public static List elements(Element element) {
    List elements = new ArrayList();
    NodeList nodeList = element.getChildNodes();
    for (int i=0; i<nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if ( (node instanceof Element)
           && (element==node.getParentNode())
         ){
        elements.add(node);
      }
    }
    return elements;
  }


  @SuppressWarnings("unchecked")
public static Element element(Element element) {
    Element onlyChild = null;
    List elements = elements(element);
    if (! elements.isEmpty()) {
      onlyChild = (Element) elements.get(0);
    }
    return onlyChild;
  }

  public static String toString(Element element) throws Exception {
    if (element==null) return "null";

    Source source = new DOMSource(element);

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    Result result = new StreamResult(printWriter);
    
    try {
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(source, result);
    } catch (Exception e) {
      throw new Exception("couldn't write element '"+element.getTagName()+"' to string", e);
    }
    
    printWriter.close();

    return stringWriter.toString();
  }

  public static String getContentText(Element element) {
    StringBuffer buffer = new StringBuffer();
    NodeList nodeList = element.getChildNodes();
    for (int i=0; i<nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node instanceof CharacterData) {
        CharacterData characterData = (CharacterData) node;
        buffer.append(characterData.getData());
      }
    }
    return buffer.toString();
  }
  
  
  
  public static void main(String[] args) throws Exception{
	  String xml = "<MESSAGE><MID>201103191754032</MID>" +
	  		"<MCODE>101180</MCODE><DATE>20110319</DATE>" +
	  		"<TIME>175432</TIME><RCODE>000000</RCODE>" +
	  		"<MSG_CD>000000</MSG_CD><PAG_CNT>3</PAG_CNT>" +
	  		"<TOT_REC_NUM>29</TOT_REC_NUM><PAG_NO>1</PAG_NO>" +
	  		"<REC>" +
	  		"<TMERID>888000174200001</TMERID>" +
	  		"<TCUSID>8249475059</TCUSID>" +
	  		"<PE_ITM>001</PE_ITM>" +
	  		"<CCPBUSTYP></CCPBUSTYP>" +
	  		"<MBLNO>13595255878</MBLNO>" +
	  		"<INST_PROV>01</INST_PROV>" +
	  		"<INST_CITY>100</INST_CITY>" +
	  		"<APPDT>20110317</APPDT>" +
	  		"<EFFDT>20110401</EFFDT>" +
	  		"<EFFFLG>1</EFFFLG>" +
	  		"<AGRNO>0011287654893</AGRNO>" +
	  		"<MERCCD>888000174200001</MERCCD>" +
	  		"<JRNNO>$GWA.JRN_NO</JRNNO>" +
	  		"</REC></MESSAGE>";
	 
	  Document doc = parseXmlText(xml); 
	  Element root = (Element) doc.getElementsByTagName("MESSAGE").item(0);
	  NodeList nodeList = root.getChildNodes();
	  
	  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			String key = nodeList.item(i).getNodeName();
			if (key.equals(XmlPAY.REC)) {
				NodeList subNodeList = nodeList.item(i)
						.getChildNodes();
				Map<String, Object> sub_map = new HashMap<String, Object>();
				for (int j = 0; j < subNodeList.getLength(); j++) {
					String sub_key = subNodeList.item(j)
							.getNodeName();
					System.out.println("sub_key--->"+sub_key);

					String sub_value ="";
					Node node =subNodeList.item(j)
					.getFirstChild();
					if(node!=null){
						sub_value = node.getNodeValue();
					}
					
	/*				String sub_value = subNodeList.item(j)
							.getFirstChild().getNodeValue();*/
					System.out.println("sub_value--->"+sub_value);
					sub_map.put(sub_key, sub_value);
				}
				list.add(sub_map);
				
				
			} 
		}
	  
  }
  
  
  
}