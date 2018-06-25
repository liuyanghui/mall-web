package com.mall.controller.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * ******************  类说明  *********************
 * class       :  UserMessage
 * @author     :  zhurouyu
 * @version    :  1.0  
 * description :  用户消息中心PO
 * @see        :     
 * @date       :  2017.4.15 17:30:47
 * ***********************************************
 */
public class UserMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String msgid;//消息ID
	private String userid;//会员编号
	private String title;//消息标题
	private String msgtype;//消息类型
	private String digest;//消息摘要
	private Integer isread;//是否已读
	private Timestamp intime;//创建时间
	private Timestamp modtime;//修改时间
	
	
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
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

	@Override
	public String toString() {
		return "WalletPoints [msgid=" + msgid + ", userid=" + userid
				+ ", title=" + title + ", msgtype=" + msgtype + ", digest="
				+ digest + ",isread=" + isread + ",  intime=" + intime + ", modtime="
				+ modtime + "]";
	}
	
	
}
