/** *****************  JAVA头文件说明  ****************
 * file name  :  Goods.java
 * owner      :  dmm
 * copyright  :  UMPAY
 * description:  
 * modified   :  2016-11-7
 * *************************************************/

package com.mall.controller.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/** ******************  类说明  *********************
 * class       :  Goods
 * @author     :  dmm
 * @version    :  1.0  
 * description :  
 * @see        :                        
 * ************************************************/
public class Goods implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4588797357088422406L;
	
	private String productid;//平台商品ID 主键 GOODSID-MERID
	private String goodsid;//商品id
	private String merid;//	商户id(供货商id)
	private String categoryid;//	商品所属分类id
	private String brandid;//	品牌id
	private String goodsname;//	商品名称
	private String subtitle;//商品副标题
	private Integer goodstype;//	商品类型	tinyint	0	0 实物 1 数字商品
	private BigDecimal marketprice;//	市场价
	private BigDecimal purchaserprice;//进货价,结算价
	private Integer position;//排序	tinyint	0	
	private Integer state;//上下架状态	tinyint	0	0 下架 1 上架
	private Integer storedcount;//	库存	int	0	
	private Integer outcount;//	出库数	int	0	
	private String model;//	型号
	private String spec;//	尺寸
	private String smallpic	;//小图路径
	private String centerpic;//	中图路径
	private String bigpic;//	大图路径
	private String manufactory;//	生产商
	private String reseller;//	销售商
	private String producingarea;//	产地
	private String notes;//	备注
	private Integer popnum;//	多维度图数量
	private String poppic;//	多维度推荐图
	private String description;//	商品描述
	private String shipdesc;//	配送说明
	private String packagelist;//	包装清单
	private String serviceinfo;//	售后说明
	private String distributorid;//配送商id
	private Integer auditstate;//	审核状态	0"0 未审核 	1 审核未通过 	2 审核通过 	3 撤回"
	private String remark;//	备注
	private String audituser;//	审核人
	private Timestamp audittime;//	审核时间	timestamp	1900-1-1 0:00	
	private String moduser;//	更新人
	private Timestamp modtime;//	修改时间	timestamp	1900-1-1 0:00	
	private String createuser;//	创建人
	private Timestamp intime;//	入库时间	timestamp	current_timestamp
	
	private Integer ishot; // 是否热门
	private String adpic;//广告图片
	
	//页面展示数据
	private String categoryname;
	private String brandname;
	private String shipmername;//配送供应商名称
	private String supplymername;//商品供应商名称
	private String[] items; // 分类列表
	//商品价格列表
	private List<GoodsPrice> goodsPriceList;
	private String paytype;//支付方式 points 积分兑换  cash 现金支付 comb 组合支付 cust 自定义积分
	private BigDecimal price;//
	private BigDecimal points;//
	private String orderby;//1代表按照修改时间倒叙
	
	private String pointsstr;//
	
	public String getPointsstr() {
		return pointsstr;
	}
	public void setPointsstr(String pointsstr) {
		this.pointsstr = pointsstr;
	}
	
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
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
	public String getAdpic() {
		return adpic;
	}
	public void setAdpic(String adpic) {
		this.adpic = adpic;
	}
	public List<GoodsPrice> getGoodsPriceList() {
		return goodsPriceList;
	}
	public void setGoodsPriceList(List<GoodsPrice> goodsPriceList) {
		this.goodsPriceList = goodsPriceList;
	}
	
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public BigDecimal getPurchaserprice() {
		return purchaserprice;
	}
	public void setPurchaserprice(BigDecimal purchaserprice) {
		this.purchaserprice = purchaserprice;
	}
	public String getDistributorid() {
		return distributorid;
	}
	public void setDistributorid(String distributorid) {
		this.distributorid = distributorid;
	}
	public String getShipmername() {
		return shipmername;
	}
	public void setShipmername(String shipmername) {
		this.shipmername = shipmername;
	}
	public String getSupplymername() {
		return supplymername;
	}
	public void setSupplymername(String supplymername) {
		this.supplymername = supplymername;
	}
	public void setIshot(Integer ishot) {
		this.ishot = ishot;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getMerid() {
		return merid;
	}
	public void setMerid(String merid) {
		this.merid = merid;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String[] getItems() {
		return items;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public String getBrandid() {
		return brandid;
	}
	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public Integer getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(Integer goodstype) {
		this.goodstype = goodstype;
	}
	public BigDecimal getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(BigDecimal marketprice) {
		this.marketprice = marketprice;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getStoredcount() {
		return storedcount;
	}
	public void setStoredcount(Integer storedcount) {
		this.storedcount = storedcount;
	}
	public Integer getOutcount() {
		return outcount;
	}
	public void setOutcount(Integer outcount) {
		this.outcount = outcount;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getSmallpic() {
		return smallpic;
	}
	public void setSmallpic(String smallpic) {
		this.smallpic = smallpic;
	}
	public String getCenterpic() {
		return centerpic;
	}
	public void setCenterpic(String centerpic) {
		this.centerpic = centerpic;
	}
	public String getBigpic() {
		return bigpic;
	}
	public void setBigpic(String bigpic) {
		this.bigpic = bigpic;
	}
	public String getManufactory() {
		return manufactory;
	}
	public void setManufactory(String manufactory) {
		this.manufactory = manufactory;
	}
	public String getReseller() {
		return reseller;
	}
	public void setReseller(String reseller) {
		this.reseller = reseller;
	}
	public String getProducingarea() {
		return producingarea;
	}
	public void setProducingarea(String producingarea) {
		this.producingarea = producingarea;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getPopnum() {
		return popnum;
	}
	public void setPopnum(Integer popnum) {
		this.popnum = popnum;
	}
	public String getPoppic() {
		return poppic;
	}
	public void setPoppic(String poppic) {
		this.poppic = poppic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getShipdesc() {
		return shipdesc;
	}
	public void setShipdesc(String shipdesc) {
		this.shipdesc = shipdesc;
	}
	public String getPackagelist() {
		return packagelist;
	}
	public void setPackagelist(String packagelist) {
		this.packagelist = packagelist;
	}
	public String getServiceinfo() {
		return serviceinfo;
	}
	public void setServiceinfo(String serviceinfo) {
		this.serviceinfo = serviceinfo;
	}
	public Integer getAuditstate() {
		return auditstate;
	}
	public void setAuditstate(Integer auditstate) {
		this.auditstate = auditstate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAudituser() {
		return audituser;
	}
	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}
	public Timestamp getAudittime() {
		return audittime;
	}
	public void setAudittime(Timestamp audittime) {
		this.audittime = audittime;
	}
	public String getModuser() {
		return moduser;
	}
	public void setModuser(String moduser) {
		this.moduser = moduser;
	}
	public Timestamp getModtime() {
		return modtime;
	}
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
	public Integer getIshot() {
		return ishot;
	}

}
