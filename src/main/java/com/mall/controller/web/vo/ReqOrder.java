/** *****************  JAVA头文件说明  ****************
 * file name  :  ReqOrder.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2016-11-16
 * *************************************************/ 

package com.mall.controller.web.vo;

import java.io.Serializable;


/** ******************  类说明  *********************
 * class       :  ReqOrder
 * @author     :  xuhuafeng
 * @version    :  1.0  
 * description :  下单请求参数
 * ************************************************/

public class ReqOrder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String rpid;	//请求流水
	private String reqdate;	//请求日期
	private String reqtime;	//请求时间
	private String productid;	//商品ID
	private Integer goodstype;	//商品类型,0 实物 1 数字商品
	private String goodsname;	//商品名称
	private Integer goodsnum;	//商品数量
	private String source;	//订单来源，pay 支付  lottery抽奖
	private String mobileid;	//支付手机号码
	private String telphone;	//接收手机号
	private String userid;	//用户ID
	private String addressid;	//配送地址ID
	private Long delivetype;	//配送方式,0 只工作日配送1 只双休日配送2 工作日、双休日都可以配送
	private String paytype;	//支付方式,POINTS 积分兑换 CASH 现金支付 COMB 组合支付 CUST 自定义积分 COD 货到付款
	private String bankid;	//银行ID,wxPay微信支付 aliPay 支付宝
	private Long amount;	//应付商品单价
	private Long points;	//应付积分
	private Long affixation;	//附加费，配送费	，单位，分
	private String remark;	//备注说明（可为空）
	private String sign;	//验签
	public String getRpid() {
		return rpid;
	}
	public void setRpid(String rpid) {
		this.rpid = rpid;
	}
	public String getReqdate() {
		return reqdate;
	}
	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}
	public String getReqtime() {
		return reqtime;
	}
	public void setReqtime(String reqtime) {
		this.reqtime = reqtime;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public Integer getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(Integer goodstype) {
		this.goodstype = goodstype;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMobileid() {
		return mobileid;
	}
	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAddressid() {
		return addressid;
	}
	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}
	public Long getDelivetype() {
		return delivetype;
	}
	public void setDelivetype(Long delivetype) {
		this.delivetype = delivetype;
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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public Long getAffixation() {
		return affixation;
	}
	public void setAffixation(Long affixation) {
		this.affixation = affixation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

}
