package com.mall.controller.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * ******************  类说明  *********************
 * class       :  LotteryNum
 * @author     :  zhurouyu
 * @version    :  1.0  
 * description :  关于我们PO
 * @see        :     
 * @date       :  上午9:30:47
 * ***********************************************
 */
@SuppressWarnings("serial")
public class LotteryNum implements Serializable {
	
	private String userid;
	private String date;
	private Integer num;
	private Timestamp modtime;
	private Timestamp intime;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	
	@Override
	public String toString() {
		return "AboutUS [userid=" + userid + ", date=" + date
				+ ", num=" + num + ", modtime=" + modtime + ", intime="
				+ intime + "]";
	}
	
	
}
