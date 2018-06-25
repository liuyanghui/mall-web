package com.mall.controller.web.vo;

import java.io.Serializable;

/**
 * ******************  类说明  *********************
 * class       :  Signin
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  每日签到信息
 * @see        :                        
 * ***********************************************
 */
public class Signin implements Serializable {

	private static final long serialVersionUID = 1L;
	private String day; // 星期几
	private Boolean isSigned; //是否已签到
	private String isOver; // 是否已过
	private Boolean isSignin; // 是否可以签到
	private Integer points; //
	
	public Signin(){
		
	}
	
	public Signin(String day, Boolean isSigned, 
			String isOver, Boolean isSignin, Integer points){
		this.day = day;
		this.isSigned = isSigned;
		this.isOver = isOver;
		this.isSignin = isSignin;
		this.points = points;
	}
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Boolean getIsSigned() {
		return isSigned;
	}
	public void setIsSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}
	public String getIsOver() {
		return isOver;
	}
	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	public Boolean getIsSignin() {
		return isSignin;
	}
	public void setIsSignin(Boolean isSignin) {
		this.isSignin = isSignin;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
}
