package com.mall.controller.web.vo;

import java.io.Serializable;


public class WXPay implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4635106283504783527L;
	/**
	 * 终端IP
	 */
	private String spbill_create_ip;
	/**
	 * 商户订单号
	 */
	private String out_trade_no;
	/**
	 * 通知地址
	 */
	private String notify_url;
	/**
	 * 交易类型
	 */
	private String trade_type;
	/**
	 * 用户标识
	 */
	private String openid;
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
