/** *****************  JAVA头文件说明  ****************
 * file name  :  ExpressTrace.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-12
 * *************************************************/ 

package com.mall.controller.web.vo;

import java.io.Serializable;


/** ******************  类说明  *********************
 * class       :  ExpressTrace
 * @author     :  xuhuafeng
 * @version    :  1.0  
 * description :  物流轨迹
 * ************************************************/

public class Traces implements Serializable {

	private static final long serialVersionUID = -2867536457907271383L;
	
	private String accepttime;	//时间
	private String acceptstation;//描述
	private String remark;//备注
	public String getAccepttime() {
		return accepttime;
	}
	public void setAccepttime(String accepttime) {
		this.accepttime = accepttime;
	}
	public String getAcceptstation() {
		return acceptstation;
	}
	public void setAcceptstation(String acceptstation) {
		this.acceptstation = acceptstation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Traces [accepttime=" + accepttime + ", acceptstation=" + acceptstation + ", remark=" + remark + "]";
	}
}
