package com.mall.controller.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * ******************  类说明  *********************
 * class       :  EcardInf
 * @author     :  zhurouyu
 * @version    :  1.0  
 * description :  卡劵PO
 * @see        :     
 * @date       :  2017.4.12 17:30:47
 * ***********************************************
 */
public class EcardInf  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String orderdate;//主键，格式：yyyyMMdd
	private String orderid;//主键
	private String mobileid;//接受手机号
	private String userid;//用户id
	private String content;//卡券内容
	private String expiredate;//有效期
	private Integer state;//状态
	private Timestamp intime;//入库时间
	private Timestamp modtime;//修改时间

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
		return "EcardInf [orderdate=" + orderdate + ", orderid=" + orderid
				+ ", mobileid=" + mobileid + ", userid=" + userid + ", content="
				+ content + ",expiredate=" + expiredate + ", state=" + state
				+ ",intime=" + intime + ", modtime="
				+ modtime + "]";
	}
	
	
}
