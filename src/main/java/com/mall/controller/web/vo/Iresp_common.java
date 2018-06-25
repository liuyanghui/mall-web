package com.mall.controller.web.vo;

import java.io.Serializable;

/**
 * ******************  类说明  *********************
 * class       :  Iresp_common
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  接口响应处理公共类
 * @see        :                        
 * ***********************************************
 */
public class Iresp_common implements Serializable{

	private static final long serialVersionUID = 1L;
	private String ret;
	private String msg;
	
	public Iresp_common(){
		
	}
	
	public Iresp_common(String ret, String msg){
		this.ret = ret;
		this.msg = msg;
	}
	
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
