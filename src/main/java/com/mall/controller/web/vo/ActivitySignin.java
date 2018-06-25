package com.mall.controller.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ******************  类说明  *********************
 * class       :  ActivitySignin
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  每日签到信息表
 * @see        :                        
 * ***********************************************
 */
public class ActivitySignin implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userid;
    private String year;
    private Short week;
    private Integer points;
    private Short day;
    private Short monday;
    private Short tuesday;
    private Short wednesday;
    private Short thursday;
    private Short friday;
    private Short saturday;
    private Short sunday;
    private Timestamp modtime;
	private Timestamp intime;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Short getWeek() {
		return week;
	}
	public void setWeek(Short week) {
		this.week = week;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Short getDay() {
		return day;
	}
	public void setDay(Short day) {
		this.day = day;
	}
	public Short getMonday() {
		return monday;
	}
	public void setMonday(Short monday) {
		this.monday = monday;
	}
	public Short getTuesday() {
		return tuesday;
	}
	public void setTuesday(Short tuesday) {
		this.tuesday = tuesday;
	}
	public Short getWednesday() {
		return wednesday;
	}
	public void setWednesday(Short wednesday) {
		this.wednesday = wednesday;
	}
	public Short getThursday() {
		return thursday;
	}
	public void setThursday(Short thursday) {
		this.thursday = thursday;
	}
	public Short getFriday() {
		return friday;
	}
	public void setFriday(Short friday) {
		this.friday = friday;
	}
	public Short getSaturday() {
		return saturday;
	}
	public void setSaturday(Short saturday) {
		this.saturday = saturday;
	}
	public Short getSunday() {
		return sunday;
	}
	public void setSunday(Short sunday) {
		this.sunday = sunday;
	}
	public Timestamp getModtime() {
		return modtime;
	}
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
}
