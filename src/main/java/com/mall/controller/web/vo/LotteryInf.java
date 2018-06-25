package com.mall.controller.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * ******************  类说明  *********************
 * class       :  LotteryInf
 * @author     :  zhurouyu
 * @version    :  1.0  
 * description :  用户中奖信息表
 * @see        :     
 * @date       :  2017.4.16 20:50:47
 * ***********************************************
 */
public class LotteryInf implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String lotteryid;//中奖纪录ID
	private String userid;//用户ID
	private String mobileid;//手机号码
	private String productid;//商品ID
	private String goodsname;//商品名称
	private Timestamp intime;//创建时间
	private Timestamp modtime;//修改时间
	private String trace;//电子劵的订单编号
	private Timestamp receivetime;//失效时间
	private Timestamp losetime;//领取时间
	private Integer receivestate;//领取状态
	private Long losetimeStr;
	private String intimeStr;
	
	public String getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(String lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMobileid() {
		return mobileid;
	}

	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public Timestamp getIntime() {
		return intime;
	}

	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}

	public Timestamp getModtime() {
		return modtime;
	}

	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}


	public Timestamp getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Timestamp receivetime) {
		this.receivetime = receivetime;
	}

	public Timestamp getLosetime() {
		return losetime;
	}

	public void setLosetime(Timestamp losetime) {
		this.losetime = losetime;
	}
	
	public Integer getReceivestate() {
		return receivestate;
	}

	public void setReceivestate(Integer receivestate) {
		this.receivestate = receivestate;
	}


	public Long getLosetimeStr() {
		return losetimeStr;
	}

	public void setLosetimeStr(Long losetimeStr) {
		this.losetimeStr = losetimeStr;
	}

	public String getIntimeStr() {
		return intimeStr;
	}

	public void setIntimeStr(String intimeStr) {
		this.intimeStr = intimeStr;
	}

	@Override
	public String toString() {
		return "LotteryInf [lotteryid=" + lotteryid + ", userid=" + userid
				+ ", mobileid=" + mobileid + ", productid=" + productid + ", goodsname="
				+ goodsname + ",intime=" + intime + ", modtime"
				+ modtime + ",trace=" + trace + ",receivetime" + receivetime + 
				",losetime=" + losetime + ",receivestate=" + receivestate + "]";
	}
	
	
}
