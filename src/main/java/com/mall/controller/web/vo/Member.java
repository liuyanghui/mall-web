/** *****************  JAVA头文件说明  ****************
 * file name  :  MerInf.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2016-11-3
 * *************************************************/

package com.mall.controller.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * ****************** 类说明 ********************* class : MerInf
 * 
 * @author : xuhuafeng
 * @version : 1.0 description : 会员信息表
 * ************************************************/

public class Member implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9024164104171604348L;
	
	private String userid;
	private String nickname;
	private String mobileid;
	private String openid;
	private String passwd;
	private String pin;
	private BigDecimal points;
	private BigDecimal freeze_points;
	private String cash;
	private String name;
	private int sex;
	private String email;
	private int cardtype;
	private String cardno;
	private String headimgurl;
	private Timestamp freeze_time;
	private int state;
	private Timestamp modtime;
	private String intime;
	private String sexdes;
	private String statedes;

	private String login_token;

	public String getLogin_token() {
		return login_token;
	}

	public void setLogin_token(String login_token) {
		this.login_token = login_token;
	}

	public String getStatedes() {
		return statedes;
	}

	public void setStatedes(String statedes) {
		this.statedes = statedes;
	}

	public String getMobileid() {
		return mobileid;
	}

	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}

	public String getSexdes() {
		return sexdes;
	}

	public void setSexdes(String sexdes) {
		this.sexdes = sexdes;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public BigDecimal getFreeze_points() {
		return freeze_points;
	}

	public void setFreeze_points(BigDecimal freeze_points) {
		this.freeze_points = freeze_points;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCardtype() {
		return cardtype;
	}

	public void setCardtype(int cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Timestamp getFreeze_time() {
		return freeze_time;
	}

	public void setFreeze_time(Timestamp freeze_time) {
		this.freeze_time = freeze_time;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Timestamp getModtime() {
		return modtime;
	}

	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

}
