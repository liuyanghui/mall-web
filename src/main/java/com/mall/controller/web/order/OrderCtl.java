package com.mall.controller.web.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.reflect.TypeToken;
import com.mall.annotation.LoginValid;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.DeliveryResp;
import com.mall.controller.web.vo.OrderVo;
import com.mall.controller.web.vo.Traces;
import com.mall.util.HttpUtils;
import com.mall.util.PropertiesUtils;
import com.mall.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * ******************  类说明  *********************
 * class       :  OrderCtl
 * @author     :  zhangyajie1@umpay.com
 * @version    :  1.0  
 * description :  "订单详情"视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/order")
public class OrderCtl extends BaseCtl{
	private static final Logger LOG = LoggerFactory.getLogger(OrderCtl.class);

	/**
	 * ********************************************
	 * method name   : allOrder 
	 * description   : 查询订单
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月12日  上午11:08:00
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@RequestMapping(value="/order.htm")
	public String allOrder(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		String userid = (String) request.getAttribute(Contants.USERID);
		String flag = request.getParameter("flag");
		//用于返回请求
        List<OrderVo> ordList=new ArrayList<OrderVo>();
		LOG.info("#allOrder() 请求参数 userid={}", userid);
		
		//获取url
		String url =getServiceURL("emall/api/order/querylist/"+userid);
		String resultJson = HttpUtils.doGet(url, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#allOrder() 响应信息 result={}", resultJson);
		//返回请求
		ordList = gson.fromJson(resultJson, new TypeToken<List<OrderVo>>() {}.getType());
		
		LOG.info("#address() 响应信息 result={}", ordList.toString());
		modelMap.addAttribute("orderList", ordList);
		modelMap.addAttribute("userid", userid);
		modelMap.addAttribute("flag", flag);
		String staticDomain = PropertiesUtils.getProperty("platform.resource.domain");
		modelMap.addAttribute("staticDomain", staticDomain);
		return "member/order";
	}
	
	/** ********************************************
	 * method name   : orderDelivery 
	 * description   : 物流轨迹查询
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @param expressno 快递简称
	 * @param        : @param orderid 快递单号
	 * @param        : @return
	 * modified      : dangjingchao ,  2017年4月16日  上午11:23:10
	 * @see          : 
	 * ********************************************/      
	@RequestMapping(value="/delivery.htm")
	public String orderDelivery(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){

		String expressid = request.getParameter("expressid");
		String expressno = request.getParameter("expressno");
		String expressname = request.getParameter("expressname");
		String centerpic = request.getParameter("centerpic");
		String trace = request.getParameter("trace");
		if(StringUtil.isEmptyTrim(expressid) || StringUtil.isEmptyTrim(expressno)){
			return "404";
		}
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		expressid = expressid.toUpperCase();
		expressno = expressno.toUpperCase();
		String url = baseurl + "/emall/admin/order/delivery/" + expressid + "/" + expressno;
//		String url = baseurl + "/emall/admin/order/delivery/ZJS/A004091089494";
		//{  "ebusinessid": "1283694",  "shippercode": "zjs",  "success": true,  "logisticcode": "a004091089494",  "state": "3",  "traces": [    {      "accepttime": "2017-03-11 19:56:02",      "acceptstation": "已取件，离开 [上海_华新仓配中心] 发往 [北京基地_航空操作班组]",      "remark": ""    },    {      "accepttime": "2017-03-12 03:05:02",      "acceptstation": "到达 [北京基地]",      "remark": ""    },    {      "accepttime": "2017-03-12 03:11:40",      "acceptstation": "离开 [北京基地] 发往 [北京_中关村营业所_德胜门营业厅]",      "remark": ""    },    {      "accepttime": "2017-03-12 08:38:37",      "acceptstation": "到达 [北京_中关村营业所_德胜门营业厅]",      "remark": ""    },    {      "accepttime": "2017-03-13 08:23:59",      "acceptstation": "离开 [北京_中关村营业所_德胜门营业厅] 派送中，递送员[郭智勇]，电话[13552887623]",      "remark": ""    },    {      "accepttime": "2017-03-13 09:18:21",      "acceptstation": "客户已签收",      "remark": ""    }  ]}
		String respjson = HttpUtils.doGet(url, null, 3, 5, Contants.CHARSET_UTF_8);
		if(respjson == null){
			return "404";
		}
		respjson = respjson.toLowerCase();
		LOG.info("#expressPage() 调用物流查询接口设置订单配送信息，响应报文:" + respjson);
		DeliveryResp deliveryResp = gson.fromJson(respjson, DeliveryResp.class);
		//返回页面展示的内容
		modelMap.put("expressno", expressno);//订单编号
		modelMap.put("expressName", expressname);//快递名称
		modelMap.put("logisticCode",deliveryResp.getLogisticcode());//快递单号
		//物流轨迹排序
		List<Traces> traces = deliveryResp.getTraces();
		Collections.reverse(traces);
		modelMap.put("TracesList",traces);//物流轨迹
		modelMap.put("trace",trace);//订单编号
		String staticDomain = PropertiesUtils.getProperty("platform.resource.domain");
		modelMap.addAttribute("staticDomain", staticDomain);
		modelMap.addAttribute("centerpic", centerpic);
		return "member/order_delivery";
	}
	
	/**
	 * ********************************************
	 * method name   : orderDetail 
	 * description   : 订单详情处理方法
	 * @return       : String
	 * @param        : @param modelMap
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @return
	 * modified      : zhangyajie1@umpay.com ,  2017年4月16日  下午7:17:11
	 * @see          : 
	 * *******************************************
	 */
	@LoginValid
	@RequestMapping(value="/detail.htm")
	public String orderDetail(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		String userid = (String) request.getAttribute(Contants.USERID);
		JSONObject  jsonMem = (JSONObject) request.getAttribute(Contants.LOGIN_TOKEN);
		String mobileid = jsonMem.optString("mobileid");
		String trace = request.getParameter("trace");
		String goodstype = request.getParameter("goodstype");
		//用于返回请求
        OrderVo ordVo=new OrderVo();
		LOG.info("#orderDetail() 请求参数 userid={},trace={}", userid, trace);
		
		//获取url
		String url =getServiceURL("emall/api/order/querydetail/"+userid+"/"+trace);
		String resultJson = HttpUtils.doGet(url, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#orderDetail() 响应信息 result={}", resultJson);
		//返回请求
		ordVo = gson.fromJson(resultJson, OrderVo.class);
		String address = ordVo.getAddress().replaceAll("\\|", " ").replaceAll(",", "");
		ordVo.setAddress(address);
		String hidemobileid = StringUtil.hideMobile(ordVo.getTelphone());
		LOG.info("#orderDetail() 响应信息 result={}", ordVo);
		modelMap.addAttribute("orderVo", ordVo);
		modelMap.addAttribute("trace", trace);
		modelMap.addAttribute("mobileid", mobileid);
		modelMap.addAttribute("hidemobileid", hidemobileid);
		String staticDomain = PropertiesUtils.getProperty("platform.resource.domain");
		modelMap.addAttribute("staticDomain", staticDomain);
		if("0".equals(goodstype)){
			return "member/orderdetail";
		}
		return "member/order_detail";
	}
	
	/** ********************************************
	 * method name   : confirmreceipt 
	 * description   : 确认收货
	 * @return       : void
	 * @param        : @param orderid
	 * @param        : @param request
	 * @param        : @param response
	 * modified      : dangjingchao ,  2017年4月16日  下午8:19:28
	 * modified		 : zhagnyajie@umpay.com 2017年4月17日  下午2:19:28
	 * @see          : 
	 * ********************************************/
	@LoginValid
	@PostMapping(value = "/confirmreceipt.htm")
	public void confirmreceipt(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userid = (String) request.getAttribute("userid");
		String orderid =(String) request.getParameter("orderid");
		String trace =(String) request.getParameter("trace");
		Map<String, String> map = new HashMap<String,String>();
		//用于返回请求
		Map<String, Object> result = new HashMap<String, Object>();
		map.put("orderid", orderid);
		map.put("userid", userid);
		String json = gson.toJson(map);
		String url =getServiceURL("emall/api/order/confirmreceipt/" + userid + "/" + orderid);
		String results = HttpUtils.doPost(url, json, null, 3, 5, Contants.CHARSET_UTF_8);	
		//请求service 设置默认收货地址
		LOG.info("#confirmreceipt() 响应信息 result={}", results);
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
		if (!"0000".equals(fromJson.get("ret"))) {
			LOG.info("发送失败：ret={}, msg={}", fromJson.get("ret"), fromJson.get("msg"));
			result.put("ret", "fail");
			result.put("msg", fromJson.get("msg"));
			String content = gson.toJson(result);
			super.write(response, content);
			return;
		}
		
		//查询一条订单详情的数据
		String orurl =getServiceURL("emall/api/order/querydetail/"+userid+"/"+trace);
		String resultJson = HttpUtils.doGet(orurl, null, 3, 10, Contants.CHARSET_UTF_8);
		LOG.info("#confirmreceipt() 响应信息 result={}", resultJson);
		//返回请求
		String staticDomain = PropertiesUtils.getProperty("platform.resource.domain");
		String ordVo = gson.toJson(resultJson);
		LOG.info("#confirmreceipt() 响应信息 result={}", ordVo);
		result.put("ret", "success");
		result.put("msg", ordVo);
		result.put("staticDomain", staticDomain);
		String content = gson.toJson(result);
		super.write(response, content);
		return;
	}
}
