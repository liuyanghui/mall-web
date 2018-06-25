/** *****************  JAVA头文件说明  ****************
 * file name  :  Order.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2016-11-7
 * *************************************************/ 

package com.mall.controller.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/** ******************  类说明  *********************
 * class       :  Order
 * @author     :  xuhuafeng
 * @version    :  1.0  
 * description :  订单表-YYYYMM
 * ************************************************/

public class OrderVo implements Serializable {

	private static final long serialVersionUID = -1372790221838700442L;
	private String trace;	//大订单号,购物车使用
	private String orderdate;	//订单日期
	private String userid;	//用户ID
	private Short goodstype;	//商品类型,0 实物  1 数字商品
	private String mobileid;	//支付手机号码
	private Timestamp paytime;	//支付时间
	private String paytype;	//支付方式,POINTS积分兑换  CASH现金支付  COMB组合支付  CUST自定义积分  COD货到付款
	private String bankid;	//银行ID,WXPAY微信支付  ALIPAY支付宝
	private String payseq;	//银行交易流水
	private Short paystatus;	//支付状态,[支付状态]0 初始2 成功 4 失败6 退款  [退款状态]20 初始状态22 退费成功24 退费失败
	private Short tracetype;	//交易类型,0 支付 1 退费
	private String accept_name;	//收货人姓名
	private String telphone;	//收货人电话
	private String address;		//收货地址
	private BigDecimal amount;	//现金支付金额
	private BigDecimal points;	//消耗积分
	private BigDecimal real_amount;	//实际支付现金总额
	private BigDecimal real_points;	//实际扣减积分总额
	private BigDecimal affixation;	//附加费，配送费
	private String orgtrace;	//原机构交易流水
	private Short invoice;	//是否索要发票,0 不索要 1索要
	private String invoice_title;	//发票抬头
	private Short delivetype;	//配送方式,0 只工作日配送1 只双休日配送2 工作日、双休日都可以配送
	private String remark;	//用户附言
	private Timestamp expiretime;	//支付过期时间
	private String note;	//管理员备注
	private Timestamp modtime;	//修改时间
	private Timestamp intime;	//订购时间
	private String source;//订单来源 ,PAY支付  LOTTERY抽奖
	private String real_amount_yuan;//实际支付金额元
	private String intimestr;
	
	//关联的订单商品表
	private String id;	//订单商品表ID
	private String orderid;	//子订单号
	private String goods_array;	//商品信息,结构为list<map>
	private String expressid;	//快递公司ID
	private String expressname;	//快递公司名称
	private String expressno;	//快递单号
	private Short shipstatus;	//商品配送情况,0 未发货 1 已发货2 已收货3 备货中
	private Short orderstatus;	//订单状态,0 未确认1 已确认2 已取消3 无效4 退货
	private Short settle;	//是否结算,0 未结算 1 已结算
	private String settledate;	//结算日期,YYYYMMDD
	private Timestamp delivetime;	//配送时间
	private String distributorid;	//配送商ID
	private Short del;	//是否删除，0 不删除
	
	private String goodsname;//	商品名称
	private String smallpic	;//小图路径
	private String centerpic;//	中图路径
	private String pointsstr;//支付积分 用于展示,去掉小数点
	
	
	public String getIntimestr() {
		return intimestr;
	}

	public void setIntimestr(String intimestr) {
		this.intimestr = intimestr;
	}

	public String getPointsstr() {
		return pointsstr;
	}

	public void setPointsstr(String pointsstr) {
		this.pointsstr = pointsstr;
	}

	public String getReal_amount_yuan() {
		return real_amount_yuan;
	}

	public void setReal_amount_yuan(String real_amount_yuan) {
		this.real_amount_yuan = real_amount_yuan;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
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

	private String suffix;	//月表后缀 yyyyMM

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Short getGoodstype() {
		return goodstype;
	}

	public void setGoodstype(Short goodstype) {
		this.goodstype = goodstype;
	}

	public String getMobileid() {
		return mobileid;
	}

	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}

	public Timestamp getPaytime() {
		return paytime;
	}

	public void setPaytime(Timestamp paytime) {
		this.paytime = paytime;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getPayseq() {
		return payseq;
	}

	public void setPayseq(String payseq) {
		this.payseq = payseq;
	}

	public Short getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Short paystatus) {
		this.paystatus = paystatus;
	}

	public Short getTracetype() {
		return tracetype;
	}

	public void setTracetype(Short tracetype) {
		this.tracetype = tracetype;
	}

	public String getAccept_name() {
		return accept_name;
	}

	public void setAccept_name(String accept_name) {
		this.accept_name = accept_name;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public BigDecimal getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(BigDecimal real_amount) {
		this.real_amount = real_amount;
	}

	public BigDecimal getReal_points() {
		return real_points;
	}

	public void setReal_points(BigDecimal real_points) {
		this.real_points = real_points;
	}

	public BigDecimal getAffixation() {
		return affixation;
	}

	public void setAffixation(BigDecimal affixation) {
		this.affixation = affixation;
	}

	public String getOrgtrace() {
		return orgtrace;
	}

	public void setOrgtrace(String orgtrace) {
		this.orgtrace = orgtrace;
	}

	public Short getInvoice() {
		return invoice;
	}

	public void setInvoice(Short invoice) {
		this.invoice = invoice;
	}

	public String getInvoice_title() {
		return invoice_title;
	}

	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}

	public Short getDelivetype() {
		return delivetype;
	}

	public void setDelivetype(Short delivetype) {
		this.delivetype = delivetype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(Timestamp expiretime) {
		this.expiretime = expiretime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getModtime() {
		return modtime;
	}

	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}

	public Timestamp getIntime() {
		return intime;
	}

	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getGoods_array() {
		return goods_array;
	}

	public void setGoods_array(String goods_array) {
		this.goods_array = goods_array;
	}

	public String getExpressid() {
		return expressid;
	}

	public void setExpressid(String expressid) {
		this.expressid = expressid;
	}

	public String getExpressname() {
		return expressname;
	}

	public void setExpressname(String expressname) {
		this.expressname = expressname;
	}

	public String getExpressno() {
		return expressno;
	}

	public void setExpressno(String expressno) {
		this.expressno = expressno;
	}

	public Short getShipstatus() {
		return shipstatus;
	}

	public void setShipstatus(Short shipstatus) {
		this.shipstatus = shipstatus;
	}

	public Short getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Short orderstatus) {
		this.orderstatus = orderstatus;
	}

	public Short getSettle() {
		return settle;
	}

	public void setSettle(Short settle) {
		this.settle = settle;
	}

	public String getSettledate() {
		return settledate;
	}

	public void setSettledate(String settledate) {
		this.settledate = settledate;
	}

	public Timestamp getDelivetime() {
		return delivetime;
	}

	public void setDelivetime(Timestamp delivetime) {
		this.delivetime = delivetime;
	}

	public String getDistributorid() {
		return distributorid;
	}

	public void setDistributorid(String distributorid) {
		this.distributorid = distributorid;
	}

	public Short getDel() {
		return del;
	}

	public void setDel(Short del) {
		this.del = del;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	

}
