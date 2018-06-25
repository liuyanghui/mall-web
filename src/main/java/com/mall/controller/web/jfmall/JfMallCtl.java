package com.mall.controller.web.jfmall;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mall.contant.Contants;
import com.mall.controller.web.base.BaseCtl;
import com.mall.controller.web.vo.Goods;
import com.mall.controller.web.vo.GoodsPrice;
import com.mall.util.HttpUtils;
import com.mall.util.MoneyUtil;
import com.mall.util.PropertiesUtils;
import com.mall.util.RedisPool;
import com.mall.util.StringUtil;

/**
 * ******************  类说明  *********************
 * class       :  JfMallCtl
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  积分联盟商城视图层处理类
 * @see        :                        
 * ***********************************************
 */
@Controller
@RequestMapping("/jfmall")
public class JfMallCtl extends BaseCtl{
	private static final Logger LOG = LoggerFactory.getLogger(JfMallCtl.class);

	@GetMapping(value="/index.htm")
	public String index(ModelMap modelMap, HttpServletRequest request) throws Exception{
		String showmall = request.getParameter("showmall");
		String login_token = getCookie(request, Contants.LOGIN_TOKEN);
		LOG.info("#index() login_token:{}", login_token);
		if(!StringUtil.isEmptyTrim(login_token)){
			String memberInfo = RedisPool.get(login_token);
			LOG.info("#index() memberInfo:{}", memberInfo);
			if(StringUtils.isNotBlank(memberInfo)){
				//是否登录标识
				modelMap.addAttribute("islogin", "1");
				JSONObject memjson = JSONObject.fromObject(memberInfo);
				String hidemobileid = memjson.optString("hidemobileid");
				modelMap.addAttribute("hidemobileid", hidemobileid);
				LOG.info("#index() hidemobileid:{}", hidemobileid);
			}
		}
		if(!"true".equals(showmall)){
			return "jfmall/index420";
		}
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/goodsonline/indexgoods";
		String resultJson = HttpUtils.doGet(url, null, 3, 10, "utf-8");
		if(StringUtil.isEmptyTrim(resultJson)){
			return "jfmall/index";
		}
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(resultJson, new TypeToken<Map<String, Object>>(){}.getType());
		
		//广告商品图 5个按下标取
		List<String> adpicGoodsIds = (List<String>)map.get("adpicGoodsIds");
		modelMap.addAttribute("adpicGoodsIds", adpicGoodsIds);
		
		//实物商品 十八个 名称,价格,广告图片
		List<Goods> goodsList = (List<Goods>)map.get("swGoodsList");
		
		String staticDomain = PropertiesUtils.getProperty("static.domain");
		modelMap.addAttribute("staticDomain", staticDomain);
		
		modelMap.addAttribute("goodsList", goodsList);
		return "jfmall/index";

	}
	/**
	 * ********************************************
	 * method name   : goods 
	 * description   : 
	 * @return       : ModelAndView
	 * @param        : @param modelMap
	 * @param        : @param productid
	 * @param        : @return
	 * @param        : @throws Exception
	 * modified      : dmm ,  2017-4-12  下午5:22:36
	 * @see          : 
	 * *******************************************
	 */
	@GetMapping(value="/goods/{productid}.htm")
	public String goods(ModelMap modelMap, @PathVariable("productid") String productid) throws Exception{
		if(StringUtil.isEmptyTrim(productid)){
			return "404";
		}
		//查商品详细信息
		String baseurl = PropertiesUtils.getProperty("mall.service.url");
		String url = baseurl + "/emall/api/goodsonline/" + productid;
		String resultJson = HttpUtils.doGet(url, null, 3, 10, "utf-8");

		if(StringUtil.isEmptyTrim(resultJson)){
			return "404";
		}
		
		Goods goods = gson.fromJson(resultJson, new TypeToken<Goods>(){}.getType());
		if(goods == null || goods.getState().intValue() == 0){
			//商品下架
			return "404";
		}
		
		if(goods != null){
			goods.setMarketprice(MoneyUtil.Cent2Dollar(goods.getMarketprice()));
		}
		
		List<GoodsPrice> goodsPriceList = goods.getGoodsPriceList();
		if(goodsPriceList != null){
			GoodsPrice goodsPrice = goodsPriceList.get(0);
			if(goodsPrice.getPrice() != null){
				goodsPrice.setPrice(MoneyUtil.Cent2Dollar(goodsPrice.getPrice()));
			}
			
			modelMap.addAttribute("goodsPrice", goodsPrice);
		}
		
		String staticDomain = PropertiesUtils.getProperty("static.domain");
		modelMap.addAttribute("staticDomain", staticDomain);
		
		modelMap.addAttribute("goods", goods);
		
		return "jfmall/goods";

	}
}
