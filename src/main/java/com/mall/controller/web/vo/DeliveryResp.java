/** *****************  JAVA头文件说明  ****************
 * file name  :  DeliveryResp.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-12
 * *************************************************/ 

package com.mall.controller.web.vo;

import java.io.Serializable;
import java.util.List;


/** ******************  类说明  *********************
 * class       :  DeliveryResp
 * @author     :  xuhuafeng
 * @version    :  1.0  
 * description :  
 * ************************************************/

public class DeliveryResp implements Serializable {
	
	private static final long serialVersionUID = 32885511635435116L;
	private String shippercode;	//快递公司编码
	private String logisticcode;	//物流运单号
	private String callback;	//用户标识
	private Boolean success;	//成功与否
	private String reason;	//失败原因
	private String state;	//物流状态: 2-在途中，3-签收,4-问题件
	private List<Traces> traces;
	public String getShippercode() {
		return shippercode;
	}
	public void setShippercode(String shippercode) {
		this.shippercode = shippercode;
	}
	public String getLogisticcode() {
		return logisticcode;
	}
	public void setLogisticcode(String logisticcode) {
		this.logisticcode = logisticcode;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Traces> getTraces() {
		return traces;
	}
	public void setTraces(List<Traces> traces) {
		this.traces = traces;
	}
	@Override
	public String toString() {
		return "DeliveryResp [shippercode=" + shippercode + ", logisticcode=" + logisticcode + ", callback=" + callback
				+ ", success=" + success + ", reason=" + reason + ", state=" + state + ", traces=" + traces + "]";
	}
	
	
}
