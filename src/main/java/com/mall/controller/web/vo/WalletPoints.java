package com.mall.controller.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * ******************  类说明  *********************
 * class       :  WalletPoints
 * @author     :  zhurouyu
 * @version    :  1.0  
 * description :  积分钱包PO
 * @see        :     
 * @date       :  2017.4.12 17:30:47
 * ***********************************************
 */
public class WalletPoints  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pointsid;//积分ID
	private String userid;//会员编号
	private Integer pointsio;//积分收支 0:收入;1:支出 tinyint
	private String funcode;//功能码
	private String describe;//功能描述
	private BigDecimal points;//积分数量
	private String orderdate;//订单日期
	private String orderid;//子订单号
	private Timestamp intime;//创建时间
	private Timestamp modtime;//修改时间
	private String intimeStr;
	
	public String getPointsid() {
		return pointsid;
	}
	public void setPointsid(String pointsid) {
		this.pointsid = pointsid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Integer getPointsio() {
		return pointsio;
	}
	public void setPointsio(Integer pointsio) {
		this.pointsio = pointsio;
	}
	public String getFuncode() {
		return funcode;
	}
	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public BigDecimal getPoints() {
		return points;
	}
	public void setPoints(BigDecimal points) {
		this.points = points;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
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
	public String getIntimeStr() {
		return intimeStr;
	}
	public void setIntimeStr(String intimeStr) {
		this.intimeStr = intimeStr;
	}
	
	/*@Override
	public String toString() {
		return "WalletPoints [pointsid=" + pointsid + ", userid=" + userid
				+ ", pointsio=" + pointsio + ", funcode=" + funcode + ", describe="
				+ describe + ",points=" + points + ", orderdate=" + orderdate
				+ ", orderid=" + orderid + ", intime=" + intime + ", modtime="
				+ modtime + "]";
	}*/
	
	
}
