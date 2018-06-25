package com.mall.controller.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ******************  类说明  *********************
 * class       :  Address
 * @author     :  zhangyajie1@umpay.com
 * @version    :  1.0  
 * description :  物流配送地址模型
 * @see        :                        
 * ***********************************************
 */
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	private String addressid;
	private String userid;
	private String username;
	private String mobileid;
	private String tel;
	private String address;
	private String postnum;
	private Short  isdefault;
	private Short  state; // 2 有效，4无效
	private Timestamp modtime;
	private Timestamp intime;
	private String hidemobileid ;
	
	public String getAddressid() {
		return addressid;
	}
	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobileid() {
		return mobileid;
	}
	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostnum() {
		return postnum;
	}
	public void setPostnum(String postnum) {
		this.postnum = postnum;
	}
	public Short getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(Short isdefault) {
		this.isdefault = isdefault;
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
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
	public String getHidemobileid() {
		return hidemobileid;
	}
	public void setHidemobileid(String hidemobileid) {
		this.hidemobileid = hidemobileid;
	}
	
}
