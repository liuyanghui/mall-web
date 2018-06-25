package com.mall.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 日期辅助类
 * @cmopany:    UMPAY
 * @Description:
 * @author:     JL
 * @date:       Aug 15, 2009 4:07:24 PM 
 * @version     V1.0
 */
public class DateUtil {
	public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond
	/**
	 * 要用到的DATE Format的定义
	 */
	public static String DATE_FORMAT_DATEONLY = "yyyy-MM-dd"; // 年/月/日
	public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss"; // 年/月/日
	public static SimpleDateFormat sdfDateTime = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME);
	// Global SimpleDateFormat object
	public static SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATEONLY);
	public static final SimpleDateFormat SHORTDATEFORMAT = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");
	public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	// ADD BY lihaifeng@UMP: START 2010-12-29 下午02:20:22  BUG号:添加yyyy-MM类型的格式
	public static final SimpleDateFormat SHORT_MONTH_FORMAT = new SimpleDateFormat("yyyy-MM");
	// ADD BY lihaifeng@UMP: END 2010-12-29 下午02:20:22 BUG号:
	
	public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat HMS_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat formatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	public static final String DATE_TIME = new String("yyMMddHHmmss");
	public static final String FULL_DATE_TIME = new String("yyyyMMddHHmmss");
	// ---START--- Nov 18, 2010 何金云 Add{新的日期或时间格式}
	public static final String SHORT_TIME = new String("HH:mm");
	public static final String SHORT_TIME_HHMM = new String("HHmm");
	public static final SimpleDateFormat ROLL_DATE = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat SHORT_DATE_TIME = new SimpleDateFormat("yyMMddHHmm");
	public static final SimpleDateFormat FULL_DATE = new SimpleDateFormat("yyyyMMddHHmmss");
	// ---END---
	
	//fanning add
	public static final SimpleDateFormat SIMPLEDATE = new SimpleDateFormat("yyyyMM");
	public static final SimpleDateFormat FULLDATESHORTTIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static final SimpleDateFormat MONTHDATE_FORMAT = new SimpleDateFormat("MM月dd日");//add by dmm 2014-6-19
	public static final SimpleDateFormat cstFormater = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US); //add by dmm 2016-2-24
	
	/**
	 * 获得当前的日期毫秒
	 * @return
	 */
	public static long nowTimeMillis(){
		return System.currentTimeMillis();
	}
	/**
	 * 获得当前的时间戳
	 * @return
	 */
	public static Timestamp nowTimeStamp() {
		return new Timestamp(nowTimeMillis());
	}
	/**
	 * yyyyMMdd 当前日期
	 * 
	 */
	public static String getReqDate() {
		return SHORTDATEFORMAT.format(new Date());
	}
	
	/**
	 * ********************************************
	 * method name   : timestamp2date 
	 * description   : "timestamp"转换为"yyyyMMdd"日期格式
	 * @return       : String
	 * @param        : @param timestamp
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2013-6-17  下午5:21:41
	 * @see          : 
	 * *******************************************
	 */
	public static String timestamp2date(Timestamp timestamp){
		return SHORTDATEFORMAT.format(timestamp);
	}
	public static String timestamp2shortdatetime(Timestamp timestamp){
		return SHORT_DATE_TIME.format(timestamp);
	}
	
	/**
	 * ********************************************
	 * method name   : timestamp2time 
	 * description   : "timestamp"转换为"HHmmss"时间格式
	 * @return       : String
	 * @param        : @param timestamp
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2013-6-17  下午5:22:45
	 * @see          : 
	 * *******************************************
	 */
	public static String timestamp2time(Timestamp timestamp){
		return TIME_FORMAT.format(timestamp);
	}
	
	/**
	 * HHmmss 当前时间
	 * 
	 */
	public static String getCurrrentReqTime(){
		return TIME_FORMAT.format(new Date());
	}
    public static String date8(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }
    
    public static String time6(Date date) {
        return new SimpleDateFormat("HHmmss").format(date);
    }
	/**
	 * yyyy-MM-dd 传入日期
	 * @param date
	 * @return
	 */
	public static String getReqDate(Date date) {
		return SHORT_DATE_FORMAT.format(date);
	}
	/**
	 * yyyy-MM-dd  传入的时间戳
	 * @param date
	 * @return
	 */
	public static String TimestampToDateStr(Timestamp tmp) {
		return SHORT_DATE_FORMAT.format(tmp);
	}
	// ADD BY lihaifeng@UMP: START 2010-12-29 下午02:21:33  BUG号:
	public static String getShortMonthStr(Timestamp tmp){
		return SHORT_MONTH_FORMAT.format(tmp);
	}
	// ADD BY lihaifeng@UMP: END 2010-12-29 下午02:21:33 BUG号:
	/**
	 * HH:mm:ss 当前时间
	 * @return
	 */
	public static String getReqTime() {
		return HMS_FORMAT.format(new Date());
	}
	/**
	 * 得到时间戳格式字串 
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStampStr(Date date) {
		return LONG_DATE_FORMAT.format(date);
	}
	/**
	 * 得到长日期格式字串
	 * 
	 * @return
	 */
	public static String getLongDateStr() {
		return LONG_DATE_FORMAT.format(new Date());
	}
	public static String getLongDateStr(Timestamp time) {
		return LONG_DATE_FORMAT.format(time);
	}
	/**
	 * 得到短日期格式字串
	 * 
	 * @param date
	 * @return
	 */
	public static String getShortDateStr(Date date) {
		return SHORT_DATE_FORMAT.format(date);
	}
	public static String getShortDateStr() {
		return SHORT_DATE_FORMAT.format(new Date());
	}
	/**
	 * 计算 minute 分钟后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	/**
	 * 计算 hour 小时后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}
	/**
	 * 得到day的起始时间点。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 得到day的终止时间点.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
	/**
	 * 计算 day 天后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	/**
	  *description : 计算 month 月后的时间
	  *@param      : @param date
	  *@param      : @param month
	  *@param      : @return
	  *@return     : Date
	  *@see        : 
	  *modified    : 1、2013-7-17 下午02:59:59 由 lch 创建 
	  *			   
	  */ 
	public static Date addMonth(Date date, int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}
	/**
	 * 得到month的起始时间点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 得到month的终止时间点.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
	public static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 365*year);
		return calendar.getTime();
	}
	public static Timestamp strToTimestamp(String dateStr){
		return Timestamp.valueOf(dateStr);
	}
	public static Timestamp strToTimestamp(Date date){
        return Timestamp.valueOf(formatTimestamp.format(date)); 
	}
	public static Timestamp getCurTimestamp(){
        return Timestamp.valueOf(formatTimestamp.format(new Date())); 
	}

	/**
	 * 取得两个日期之间的日数
	 * 
	 * @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
	 */
	public static long daysBetween(java.sql.Timestamp t1, java.sql.Timestamp t2) {
		return (t2.getTime() - t1.getTime()) / DAY_MILLI;
	}
	
	/**
	 * 取得两个日期之间的秒,分,时,日等...,根据传入的参数mms(单位:毫秒)来确定
	 * 
	 * @return t1到t2间的差异，如果t2 在 t1之后，返回正数，否则返回负数
	 * @author sg 2013-08-22
	 */
	public static float anyBetween(java.sql.Timestamp t1, java.sql.Timestamp t2,float mms) {
		return (t2.getTime() - t1.getTime()) / mms;
	}
	/**
	 * 返回java.sql.Timestamp型的SYSDATE
	 * 
	 * @return java.sql.Timestamp型的SYSDATE
	 * @since 1.0
	 * @history
	 */
	public static java.sql.Timestamp getSysDateTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转换String到java.sql.Timestamp
	 * 
	 * @param sDate
	 *            Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static java.sql.Timestamp toSqlTimestamp(String sDate) {
		if (sDate == null) {
			return null;
		}
		if (sDate.length() != DateUtil.DATE_FORMAT_DATEONLY.length()) {
			return null;
		}
		return toSqlTimestamp(sDate, DateUtil.DATE_FORMAT_DATEONLY);
	}
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD hh:mm:ss)转化String到java.sql.Timestamp
	 * 
	 * @param sDate
	 *            Date string
	 * @param sFmt
	 *            Date format DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static java.sql.Timestamp toSqlTimestamp(String sDate, String sFmt) {
		String temp = null;
		System.out.println("sDate = " + sDate);
		System.out.println("sFmt  = " + sFmt);
		if (sDate == null || sFmt == null) {
			return null;
		}
		if (sDate.length() != sFmt.length()) {
			return null;
		}
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME)) {
			temp = sDate.replace('/', '-');
			temp = temp + ".000000000";
		} else if (sFmt.equals(DateUtil.DATE_FORMAT_DATEONLY)) {
			temp = sDate.replace('/', '-');
			temp = temp + " 00:00:00.000000000";
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION )){
			// //Format: 200009301230
			// temp =
			// sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-"+sDate.substring(6,8);
			// temp += " " + sDate.substring(8,10) + ":" +
			// sDate.substring(10,12) + ":00.000000000";
		} else {
			return null;
		}
		System.out.println("Temp = " + temp);
		// java.sql.Timestamp.value() 要求的格式必须为yyyy-mm-dd hh:mm:ss.fffffffff
		return java.sql.Timestamp.valueOf(temp);
	}
	/**
	 * 以YYYY/MM/DD HH24:MI:SS格式返回系统日期时间
	 * 
	 * @return 系统日期时间
	 * @since 1.0
	 * @history
	 */
	public static String getSysDateTimeString() {
		return toString(new java.util.Date(System.currentTimeMillis()),
				DateUtil.sdfDateTime);
	}
	/**
	 * 根据指定的Format转化java.util.Date到String
	 * 
	 * @param dt
	 *            java.util.Date instance
	 * @param sFmt
	 *            Date format , DATE_FORMAT_DATEONLY or DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toString(java.util.Date dt, String sFmt) {
		// add by SJNS/zq 03/16
		if (dt == null) {
			return "";
		}
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME)) { // "YYYY/MM/DD
			// HH24:MI:SS"
			return toString(dt, DateUtil.sdfDateTime);
		} else { // Default , YYYY/MM/DD
			return toString(dt, DateUtil.sdfDateOnly);
		}
	}

	/**
	 * 利用指定SimpleDateFormat instance转换java.util.Date到String
	 * 
	 * @param dt
	 *            java.util.Date instance
	 * @param formatter
	 *            SimpleDateFormat Instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toString(java.util.Date dt, SimpleDateFormat formatter) {
		String sRet = null;

		try {
			sRet = formatter.format(dt).toString();
		} catch (Exception e) {
			e.printStackTrace();
			sRet = null;
		}

		return sRet;
	}
	/**
	 * 转换java.sql.Timestamp到String，格式为YYYY-MM-DD HH24:MI
	 * 
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString2(java.sql.Timestamp dt) {
		if (dt == null) {
			return null;
		}
		String temp = toSqlTimestampString(dt, DateUtil.DATE_FORMAT_DATETIME);
		return temp.substring(0, 16);
	}
	public static String toString(java.sql.Timestamp dt) {
		return dt == null ? "" : toSqlTimestampString2(dt);
	}
	/**
	 * 根据指定的格式转换java.sql.Timestamp到String
	 * 
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @param sFmt
	 *            Date
	 *            格式，DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME/DATE_FORMAT_SESSION
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString(java.sql.Timestamp dt, String sFmt) {
		String temp = null;
		String out = null;
		if (dt == null || sFmt == null) {
			return null;
		}
		temp = dt.toString();
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME) || // "YYYY/MM/DD
				// HH24:MI:SS"
				sFmt.equals(DateUtil.DATE_FORMAT_DATEONLY)) { // YYYY/MM/DD
			temp = temp.substring(0, sFmt.length());
			out = temp.replace('/', '-');
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION ) ){
			// //Session
			// out =
			// temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10);
			// out += temp.substring(12,14) + temp.substring(15,17);
		}
		return out;
	}
	
	//得到当前日期的星期
	public static int getWeek(){
	    Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK);
		return w;
	}
	
	// 得到当前日期时间
	// 当前时间日期格式:yyMMddHHmmss
	public static String getDateAndTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_TIME);
		return sdf.format(new Date());
	}
	
	// 得到当前日期时间
	// 当前日期时间格式:yyyyMMddHHmmss
	public static String getFullDateAndTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FULL_DATE_TIME);
		return sdf.format(new Date());
	}
	
	// 得到今天回滚n天的日期
	// 回滚n天日期时间格式:yyyy-MM-dd
	// if amount > 0 日期>今天日期
	// if amount < 0 日期<今天日期
	public static String getRollCurrentDate(int amount){
		Calendar date = Calendar.getInstance();
		
		date.add(Calendar.DAY_OF_MONTH, amount);
		Date currentDate = date.getTime();
		
		return SHORT_DATE_FORMAT.format(currentDate);	
	}
	
	// ADD BY lihaifeng@UMP: START 2010-10-17 上午09:31:06  BUG号:满足不同格式日期的需求
	/**
	 *  得到今天回滚n天的日期
	 *	回滚n天日期时间格式:yyyy-MM-dd
	 *	if amount > 0 日期>今天日期
	 *	if amount < 0 日期<今天日期
	 * 
	 * @param amount
	 * @param sf
	 * @return
	 */
	public static String getRollCurrentDate(int amount,String sf){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, amount);
		return formatDateForMore(date.getTime(), sf);
	}
	/**
	 *  得到今天回滚n天的日期
	 *	回滚n天日期时间格式:yyyy-MM-dd
	 *	if amount > 0 日期>今天日期
	 *	if amount < 0 日期<今天日期
	 *
	 * @param date
	 * @param amount
	 * @param sf
	 * @return
	 */
	public static String getRollCurrentDate(Date date,int amount,String sf){
		Date currentDate = date;
		Calendar _date = Calendar.getInstance();
		if(currentDate!=null){
			_date.setTime(currentDate);
		}
		_date.add(Calendar.DAY_OF_MONTH, amount);
		return formatDateForMore(_date.getTime(), sf);
	}
	/**
	 * 取得当前时间的前几天或者后几天
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date getRollCurrentDate(Date date,int amount){
		Calendar current = Calendar.getInstance();
		current.setTime(date);
		current.add(Calendar.DAY_OF_MONTH, amount);
		return current.getTime();
	}
	/**
	 * 格式化日期
	 * 
	 * @param currentDate
	 * @param sf
	 * @return
	 */
	public static String formatDateForMore(Date currentDate,String sf){
		String formatDate = "";
		if(SHORTDATEFORMAT.toPattern().equals(sf)){
			formatDate = SHORTDATEFORMAT.format(currentDate.getTime());
		}else if(SHORT_DATE_FORMAT.toPattern().equals(sf)){
			formatDate =  SHORT_DATE_FORMAT.format(currentDate.getTime());
		}else if(LONG_DATE_FORMAT.toPattern().equals(sf)){
			formatDate =  LONG_DATE_FORMAT.format(currentDate.getTime());
		}else if(formatTimestamp.toPattern().equals(sf)){
			formatDate =  formatTimestamp.format(currentDate.getTime());
		}
		return formatDate;
	}
	/**
	 * 取得当前日期
	 * @return
	 */
	public static Date getCurrentDate(){
		Calendar date = Calendar.getInstance();
		return date.getTime();
	}
	/**
	 * 传入指定格式[yyyyMMdd]的日期，转换成Date格式
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateForAppointDate(String date){
		try {
			return SHORT_DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getCurrentDate();
	}
	/**
	 * 传入指定格式[yyyyMMdd]的日期，转换成Date格式
	 * @author dmm
	 * @param date
	 * @return
	 */
	public static Date getDateForShortDate(String date){
		try {
			return SHORTDATEFORMAT.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getCurrentDate();
	}
	/**
	 * 传入指定格式[yyyy-MM-dd HH:mm:ss]的日期，转换成Date格式
	 * @author dmm
	 * @param date
	 * @return
	 */
	public static Date getDateForLongDate(String date){
		try {
			return LONG_DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getCurrentDate();
	}
	/**
	 * 对指定日期{具有指定格式}进行处理
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date getDateForAppointDate(String date,int amount,String sf){
		try {
			Calendar c = Calendar.getInstance();
			if(sf.equals(SHORT_DATE_FORMAT.toPattern())){
				c.setTime(SHORT_DATE_FORMAT.parse(date));
			} else if(sf.equals(SHORTDATEFORMAT.toPattern())){
				c.setTime(SHORTDATEFORMAT.parse(date));
			}
			c.add(Calendar.DAY_OF_MONTH, amount);
			return c.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getCurrentDate();
	}
	// ADD BY lihaifeng@UMP: END 2010-10-17 上午09:31:06 BUG号:
	
	/**
	 * Timestamp 格式转换成yyyyMMdd
	 */
	public static String timestampToShortDate(java.sql.Timestamp timestamp){
		return SHORTDATEFORMAT.format(timestamp);
	}
	
	// ADD BY lihaifeng@UMP: START 2010-12-13 下午02:51:09  BUG号:
	/**
	 * Timestamp 格式转换成HHmmss
	 */
	public static String timestampToShortTime(java.sql.Timestamp timestamp){
		return TIME_FORMAT.format(timestamp);
	}
	/**
	 * Timestamp 格式转换成MM月DD日
	 */
	public static String timestampToMonthDate(Timestamp tmp){
		return MONTHDATE_FORMAT.format(tmp);
	}
	// ADD BY lihaifeng@UMP: END 2010-12-13 下午02:51:09 BUG号:
	
	// ---START--- Nov 18, 2010 何金云 Add{新的日期或时间格式}
	/**
	 * 时间格式:HH:MM
	 */
	public static String getShortTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SHORT_TIME);
		return sdf.format(new Date());
	}
	/**
	 * 时间格式:HHMM
	 */
	public static Date getShortTime(String time){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SHORT_TIME_HHMM);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 时间格式:HH:MM
	 */
	public static String getShortTimeStr(String time){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SHORT_TIME);
		String retTime = sdf.format(getShortTime(time));
		return retTime;
	}
	/**
	 * 时间格式:HHMM
	 */
	public static String getHHMMTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SHORT_TIME_HHMM);
		return sdf.format(date);
	}
	
	/**
	 * 获取当前日期前n日,或者后n日
	 * 日期格式:yyyyMMdd
	 */
	public static String getRollDate(int amount){
		Calendar date = Calendar.getInstance();
		
		date.add(Calendar.DAY_OF_MONTH, amount);
		Date currentDate = date.getTime();
		
		return ROLL_DATE.format(currentDate);
	}
	
	// 日期格式:yyyyMMdd
	public static String getDate(String date){
		Date currentDate = null;
		try {
			currentDate = SHORT_DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getDate(currentDate);
	}
	
	// 日期格式: yyyMMdd
	public static String getDate(Date date){
		return SHORTDATEFORMAT.format(date);
	}
	
	// 日期格式: yyyyMMddHHmmss
	public static String getFullDateAndTime(Timestamp tmp){
		return FULL_DATE.format(tmp);
	}
	// ---END---
	
	/**
	 * 取得当前时间之前或之后某几个月的日期
	 * @param curTime
	 * @param iMonth
	 * @return
	 */
	public static String getDiffMonth(Date curTime,int iMonth){
		Calendar c = Calendar.getInstance();
		c.setTime(curTime);
		c.add(Calendar.MONTH, iMonth);
		return getShortDateStr(c.getTime());
	}
	/**
	 * 取得当前日期的前后月份
	 * iMonth>0  几个月后的某一天
	 * iMonth<0  几个月前的某一天
	 * 
	 * @param iMonth
	 * @return
	 */
	public static String getDiffMonth(int iMonth){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, iMonth);
		return getShortDateStr(c.getTime());
	}
	/**
	 * 比较两个日期的大小
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean compareDate(String startDate,String endDate){
		try {
			Date start = SHORT_DATE_FORMAT.parse(startDate);
			Date end = SHORT_DATE_FORMAT.parse(endDate);
			return compareDate(start, end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		return compareDate(c.getTime(), c.getTime());
	}
	/**
	 * 比较两个日期的大小
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean compareDate(Date startDate,Date endDate){
		if(startDate.compareTo(endDate)>=0){
			return true;
		}
		return false;
	}
	// ADD BY lihaifeng@UMP: END 2010-11-22 下午04:17:11 BUG号:
	
	/**
	 * 字符串格式yyyyMMddHHmmss转为时间戳yyyy-MM-dd HH:mm:ss:fffffffff
	 * add by dmm 2011-9-26
	 * 20110926101917 ->2011-09-26 10:19:17.0
	 */
	public static Timestamp getTimestamp(String timestamp){
		Date date = new Date();   
		try {
		    date = FULL_DATE.parse(timestamp);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return Timestamp.valueOf(LONG_DATE_FORMAT.format(date.getTime()));
	}
	//得到指定日期的星期 add by dmm 2012-11-17
	public static int getDayOfWeek(Date date){
	    Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return w;
	}

	
	//时间戳转Date add by dmm 2012-11-17
	public static Date timestampToDate(Timestamp tt){ 
	    return new Date(tt.getTime()); 
	}
	/**
	 * ********************************************
	 * method name   : isLastNDayOfMonth 
	 * description   : 是否是date月份的最后n天
	 * @return       : boolean
	 * @param        : @param date
	 * @param        : @param n
	 * @param        : @return
	 * modified      : dmm ,  2016-2-28  下午3:18:44
	 * @see          : 
	 * *******************************************
	 */
	public static boolean isLastNDayOfMonth(Date date, int n) { 
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(date); 
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + n)); 
        if (calendar.get(Calendar.DAY_OF_MONTH) <= n) { 
            return true; 
        } 
        return false; 
    } 
	/**
	 * ********************************************
	 * method name   : dateToNYR 
	 * description   : yyyy-MM-dd格式的日期转为yyyy年MM月dd日
	 * @return       : String
	 * @param        : @param date
	 * @param        : @return
	 * modified      : dmm ,  2013-8-19  上午9:58:18
	 * @see          : 
	 * *******************************************
	 */
	public static String dateToNYR(String date){
		return date.substring(0, 4) + "年" + date.substring(5, 7) + "月" + date.substring(8, 10) + "日";
	}
	
	/** ********************************************
	 * method name   : getNMonth 
	 * description   : 获取N个月前的第一天
	 * @return       : Date
	 * @param        : @param month
	 * @param        : @return
	 * modified      : 樊宁 ,  2014-3-13  上午11:11:31
	 * @see          : 
	 * ********************************************/      
	public static Timestamp getNMonth(int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, month);  // -3 3个月前
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return strToTimestamp(getShortDateStr(calendar.getTime())+" 00:00:00");
	}
	
	/** ********************************************
	 * method name   : getNextMonth 
	 * description   : 格式: YYYYmm
	 * @return       : Timestamp
	 * @param        : @param num
	 * @param        : @return
	 * modified      : 樊宁 ,  2014-3-14  下午7:24:40
	 * @see          : 
	 * ********************************************/      
	public static String getNextMonth(String date, int num){
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(4));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1 + num);  
		Date currentDate = calendar.getTime();
		return SIMPLEDATE.format(currentDate);
	}
	
	/**
	 * 传入指定格式[yyyy-MM-dd]的日期，转换成Date格式
	 * @author lch
	 * @param date
	 * @return
	 */
	public static Date toDate(String date){
		try {
			return SHORT_DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getCurrentDate();
	}
	
	/**
	 * Timestamp 格式转换成yyyy-MM-dd
	 * timestampToSql(Timestamp 格式转换成yyyy-MM-dd)
	 * @param   timestamp 时间 
	 * @return createTimeStr  yyyy-MM-dd 时间 
	 * @Exception 异常对象 
	 * @since   V1.0
	 */
	public static String timestampToStringYMD(java.sql.Timestamp  timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATEONLY);
		String createTimeStr = sdf.format(timestamp);
		return createTimeStr;
	}
	public static String timestampToDateTimeStr(Timestamp tmp) {
		return FULLDATESHORTTIME.format(tmp);
	}
	public static Timestamp ctsToTimestamp(String cts){
		Timestamp t = null;
		try {
			t = strToTimestamp((Date)cstFormater.parse(cts));
		}catch (Exception e) {
			// TODO: handle exception
		}
		return t;
		
	}
	/**
	 * ********************************************
	 * method name   : formatTime 
	 * description   : 
	 * @return       : Timestamp
	 * @param        : @param createTime
	 * @param        : @return
	 * modified      : dmm ,  2016-1-4  下午6:56:02
	 * @see          : 
	 * *******************************************
	 */
	public static Timestamp formatTime1000L(Long createTime) {
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long n = createTime*1000L;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(n);
        Timestamp t =java.sql.Timestamp.valueOf(formatter.format(calendar.getTime()));
		return t;
	  }
	
	/**
	 * ********************************************
	 * method name   : getSimpleDate 
	 * description   : 返回yyyyMM格式的日期
	 * @return       : String
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2016-3-3  下午6:27:27
	 * @see          : 
	 * *******************************************
	 */
	public static String getSimpleDate(){
		long ctime = System.currentTimeMillis();
		Date cdate = new Date(ctime);
		return SIMPLEDATE.format(cdate);
	}
	
	/**
	 * ********************************************
	 * method name   : getSimpleDate 
	 * description   : 获取offset值偏移月份
	 * @return       : String
	 * @param        : @param date
	 * @param        : @param offset
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2016-3-4  上午10:41:49
	 * @see          : 
	 * *******************************************
	 */
	public static String getSimpleDate(Date date, int offset){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, offset);
		Date cdate = calendar.getTime();
		
		return SIMPLEDATE.format(cdate);
	}
	
//	public static void main(String[] args) {
//		System.out.println(getDate("2016-02-16"));
//		System.out.println(getRollCurrentDate(-1));
//		System.out.println(ctsToTimestamp("Thu Feb 25 10:22:09 CST 2016"));
//		System.out.println(isLastNDayOfMonth(new Date(), 2));
//		System.out.println(TimestampToDateStr(getCurTimestamp()));
//		System.out.println("20090810".compareTo(TimestampToDateStr(getCurTimestamp())));
//		try {
//			Date date1 = YMD_FORMAT.parse("20080912");
//			Date date2 = new Date();
//			System.out.println(date1.compareTo(date2));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//new Date().compareTo(anotherDate);
//		System.out.println(DateUtil.getDateAndTime());
//		System.out.println(DateUtil.getReqDate());
//		System.out.println(DateUtil.getCurrrentReqTime());
//		System.out.println(DateUtil.getRollCurrentDate(30));
//		Timestamp currentTime = DateUtil.getCurTimestamp();
//		System.out.println(DateUtil.timestampToShortDate(currentTime));
//		System.out.println(DateUtil.getShortTime());
//		System.out.println(DateUtil.getRollDate(-1));
//		System.out.println(DateUtil.getDate("2010-10-21"));
//		String reqDate = DateUtil.formatDateForMore(new Date(), "yyyyMMdd");
//		System.out.println(reqDate);
		
//		System.out.println(addMonth(new Date(), -3));
//		System.out.println(getReqDate(addMonth(new Date(), -3)));
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		calendar.add(Calendar.MONTH, -3);
//		calendar.set(Calendar.DAY_OF_MONTH,1);
//		System.out.println("("+getShortDateStr(calendar.getTime())+")");
//		System.out.println(getTimestamp(getShortDateStr(calendar.getTime())));
//		System.out.println(strToTimestamp("2013-12-01 00:00:00"));
//		System.out.println(getNMonth(-3));
//		System.out.println(getNextMonth("201410",1));
//		float f = anyBetween(strToTimestamp("2013-12-01 00:00:01"),strToTimestamp("2013-12-01 00:00:00"),  60*1000);
//		System.out.println(f);
//		if(f - 10 > 0){
//			
//			System.out.println(0);
//		}else{
//			
//			System.out.println(1);
//		}
//		Date date = DateUtil.toDate("2016-03-01");
//		System.out.println(date);
//		String nextmonth = DateUtil.getSimpleDate(date, -1);
//		System.out.println(nextmonth);
//	}
	/**
	 * ********************************************
	 * method name   : getMonCurrentDate 
	 * description   : 获得当前日期所在周的周1的日期  YYYY:MM:DD 00:00:00
	 * @return       : Date
	 * @param        : @return
	 * modified      : 薛泽荣 ,  2016-2-26  上午10:28:44
	 * @see          : 
	 * *******************************************
	 */
	public static Date getMonCurrentDate(){
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		if(day == 1){
			day = 6;
		}else
		{
			day = day - 2;
		}
		c.add(Calendar.DAY_OF_MONTH, -day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date currentDate = c.getTime();
		return currentDate;
	}
	/**
	 * ********************************************
	 * method name   : getStrMonCurrentDate 
	 * description   : 获得当前日期所在周的周1的日期的字符串 YYYYMMDD
	 * @return       : Date
	 * @param        : @return
	 * modified      : 薛泽荣 ,  2016-2-26  上午11:04:36
	 * @see          : 
	 * *******************************************
	 */
	public static String getStrMonCurrentDate(){
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		if(day == 1){
			day = 6;
		}else
		{
			day = day - 2;
		}
		c.add(Calendar.DAY_OF_MONTH, -day);
		Date currentDate = c.getTime();
		return SHORTDATEFORMAT.format(currentDate);
	}
	/**
	 * ********************************************
	 * method name   : getLastMonStr 
	 * description   : 获取上周周一的8位日期 yyyymmdd
	 * @return       : String
	 * @param        : @return
	 * modified      : 薛泽荣 ,  2016-2-29  下午3:11:45
	 * @see          : 
	 * *******************************************
	 */
	public static String getLastMonStr(){
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		if(day == 1){
			day = 6;
		}else
		{
			day = day - 2;
		}
		c.add(Calendar.DAY_OF_MONTH, -(day + 7));
		Date currentDate = c.getTime();
		return SHORTDATEFORMAT.format(currentDate);
	}
	/**
	 * ********************************************
	 * method name   : getNextSun 
	 * description   : 下周周日的 23:59:59 
	 * @return       : Timestamp
	 * @param        : @return
	 * modified      : 薛泽荣,  2016-3-10  上午12:06:24
	 * @see          : 
	 * *******************************************
	 */
	public static Timestamp getNextSun(){
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		if(day == 1){
			day = 7;
		}else
		{
			day = 15 - day;
		}
		c.add(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date currentDate = c.getTime();
		return DateUtil.strToTimestamp(currentDate);
	}
	public static Date getFriTime(){
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		if(day == 6){
			day = 0;
		}else if(day == 7){
			day = 1;
		}else{
			day = day + 1;
		}
		c.add(Calendar.DAY_OF_MONTH, -day);
		Date currentDate = c.getTime();
		return currentDate;
	}
	
	/**
	 * ********************************************
	 * method name   : getLastDayOfMonth 
	 * description   : 获取当月最后一天
	 * @return       : int
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2016-8-1  上午10:04:30
	 * @see          : 
	 * *******************************************
	 */
	public static int getLastDayOfMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * ********************************************
	 * method name   : getDayOfMonth 
	 * description   : 获取当前日期
	 * @return       : int
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2016-8-1  上午10:13:26
	 * @see          : 
	 * *******************************************
	 */
	public static int getDayOfMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * ********************************************
	 * method name   : isLastTwoDaysOfMonth 
	 * description   : 是否是月末最后两天
	 * @return       : boolean
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2016-6-30  上午10:27:38
	 * @see          : 
	 * *******************************************
	 */
	public static boolean isLastTwoDaysOfMonth(){
		int maximun = DateUtil.getLastDayOfMonth();
		int currday =  DateUtil.getDayOfMonth();
		int distance = maximun - currday;
		if ((distance == 0) || (distance == 1)){
			return true;
		}
		return false;
	}
	
	public static int getSecondToDayEnd() {
		long ctime = System.currentTimeMillis();
		long end = getDayEnd(getCurrentDate()).getTime();
		return (int)(end - ctime) / 1000 + 60;
	}
	public static String getDateTime16String() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmssSS"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	public static void main(String[] args) throws Exception{
		boolean result = DateUtil.isLastTwoDaysOfMonth();
		System.out.println(result);
	}
	
}