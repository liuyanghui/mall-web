/** *****************  JAVA头文件说明  ****************
 * file name  :  GoodsArray.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-12
 * *************************************************/ 

package com.mall.controller.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;


/** ******************  类说明  *********************
 * class       :  GoodsArray
 * @author     :  xuhuafeng
 * @version    :  1.0  
 * description :  订单商品详情
 * ************************************************/

public class GoodsArray implements Serializable {

	private static final long serialVersionUID = 6791124139235508271L;
	
	private String productid;
	private String merid;
	private String mername;
	private String goodsid;
	private String goodsname;
	private Integer goodsnum;
	private BigDecimal price;
	private BigDecimal points;
	private String paytype;
	private Integer goodstype;
	
	private String totalpriceStr;
	private String smallpic;
	
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getSmallpic() {
		return smallpic;
	}
	public void setSmallpic(String smallpic) {
		this.smallpic = smallpic;
	}
	public String getTotalpriceStr() {
		return totalpriceStr;
	}
	public void setTotalpriceStr(String totalpriceStr) {
		this.totalpriceStr = totalpriceStr;
	}
	public Integer getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(Integer goodstype) {
		this.goodstype = goodstype;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getMerid() {
		return merid;
	}
	public void setMerid(String merid) {
		this.merid = merid;
	}
	public String getMername() {
		return mername;
	}
	public void setMername(String mername) {
		this.mername = mername;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public Integer getGoodsnum() {
		return goodsnum;
	}
	public void setGoodsnum(Integer goodsnum) {
		this.goodsnum = goodsnum;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPoints() {
		return points;
	}
	public void setPoints(BigDecimal points) {
		this.points = points;
	}
	
}
