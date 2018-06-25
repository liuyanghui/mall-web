/** *****************  JAVA头文件说明  ****************
 * file name  :  GoodsLotteryControl.java
 * owner      :  dmm
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-6
 * *************************************************/

package com.mall.controller.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;


/** ******************  类说明  *********************
 * class       :  LotteryGoods
 * @author     :  dmm
 * @version    :  1.0  
 * description :  商品抽奖概率控制表
 * @see        :                        
 * ************************************************/
public class LotteryGoods implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9112251214516917999L;
	//`goods_id`pk
	private String goods_id;//商品id
	private String goods_name;//商品名称
	private Short goods_type;//0积分 1实物 
	private Integer goods_num;//奖品数量
	private Integer give_point;//奖品为积分时,积分的数量
	private Integer get_num;//已中奖品数量
	private Timestamp intime;//创建时间
	private Timestamp modtime;//修改时间
	private String create_user;//创建用户
	private String modify_user;//修改用户
	private Short state;//状态,0不可用，1可用
	
	public Integer getGive_point() {
		return give_point;
	}
	public void setGive_point(Integer give_point) {
		this.give_point = give_point;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public Short getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(Short goods_type) {
		this.goods_type = goods_type;
	}
	public Integer getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
	}
	public Integer getGet_num() {
		return get_num;
	}
	public void setGet_num(Integer get_num) {
		this.get_num = get_num;
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
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getModify_user() {
		return modify_user;
	}
	public void setModify_user(String modify_user) {
		this.modify_user = modify_user;
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	
}
