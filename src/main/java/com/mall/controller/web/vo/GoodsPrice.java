/** *****************  JAVA头文件说明  ****************
 * file name  :  GoodsPrice.java
 * owner      :  dmm
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-10
 * *************************************************/

package com.mall.controller.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** ******************  类说明  *********************
 * class       :  GoodsPrice
 * @author     :  dmm
 * @version    :  1.0  
 * description :  
 * @see        :                        
 * ************************************************/
public class GoodsPrice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1101675444978175823L;
	//PRODUCTID,PAYTYPE 主键
	private String productid;//平台商品id
	private String paytype;//支付方式 points 积分兑换  cash 现金支付 comb 组合支付 cust 自定义积分
	private BigDecimal price;//
	private BigDecimal points;//
	private Integer state;//0 支持，1 不支持
//	private Timestamp intime;//入库时间	timestamp	current_timestamp
//	private Timestamp modtime;//修改时间	timestamp
//	private String createuser;//创建人
//	private String moduser;//修改人
	
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPoints() {
		return points;
	}
	public void setPoints(BigDecimal points) {
		this.points = points;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	/*public Timestamp getIntime() {
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
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getModuser() {
		return moduser;
	}
	public void setModuser(String moduser) {
		this.moduser = moduser;
	}*/
	
}
