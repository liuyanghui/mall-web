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
 * class       :  GoodsLotteryControl
 * @author     :  dmm
 * @version    :  1.0  
 * description :  商品抽奖概率控制表
 * @see        :                        
 * ************************************************/
public class GoodsLotteryControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6664939856463714824L;
	//`goods_id`, `user_type` pk
	private String goods_id;//商品id
	private Short user_type;//0老用户 1新用户 
	private Integer chance;//中奖概率，商品数量 万分之一
	private Timestamp intime;//创建时间
	private Timestamp modtime;//修改时间
	private String create_user;//创建用户
	private String modify_user;//修改用户
	private Short state;//状态,0不可用，1可用
	//页面显示
	private String goods_name;//商品名称
	
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public Short getUser_type() {
		return user_type;
	}
	public void setUser_type(Short user_type) {
		this.user_type = user_type;
	}
	public Integer getChance() {
		return chance;
	}
	public void setChance(Integer chance) {
		this.chance = chance;
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
