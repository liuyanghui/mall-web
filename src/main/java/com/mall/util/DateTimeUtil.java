//创建人：陈征
//创建时间：Jul 22, 2008 5:05:43 PM
//文件描述：
package com.mall.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateTimeUtil{
	
	public static String getDateString() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	
	public static String getTimeString() {
		SimpleDateFormat fm = new SimpleDateFormat("HHmmss"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	
	public static String getDateTime16String() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmssSS"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	
	
	public static String getDateTime14String() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	
	
	
	
	public static String getYtDateString(){
		Calendar   rightNow   =   Calendar.getInstance(); 
		GregorianCalendar   gc1   =   new   GregorianCalendar(rightNow.get(Calendar.YEAR),   rightNow.get(Calendar.MONTH), 
		rightNow.get(Calendar.DAY_OF_MONTH)); 
		gc1.add(Calendar.DATE,   -1); 
		Date   d1   =   (Date)gc1.getTime();
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd"); // "yyyyMMdd G
		return fm.format(d1);
	}
	public static String getAnyDateString(int calType, int num){
		Calendar   rightNow   =   Calendar.getInstance(); 
		GregorianCalendar   gc1   =   new   GregorianCalendar(rightNow.get(Calendar.YEAR),   rightNow.get(Calendar.MONTH), 
				rightNow.get(Calendar.DAY_OF_MONTH)); 
		gc1.add(calType,   num); 
		Date   d1   =   (Date)gc1.getTime();
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd"); // "yyyyMMdd G
		return fm.format(d1);
	}
	
	public static String date8(){
		return date8(new Timestamp(System.currentTimeMillis()));
	}
	public static String date8(Timestamp t){
		return formatTimestamp2String(t,"yyyyMMdd");
	}
	public static String time6(){
		return time6(new Timestamp(System.currentTimeMillis()));
	}
	public static String time6(Timestamp t){
		return formatTimestamp2String(t,"HHmmss");
	}
	public static String formatDateStr2Str(String date, String format){
		if (StringUtil.isEmpty(date)){
			return "";
		}
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat fm1 = new SimpleDateFormat(format);
		Date d;
		try
		{
			d = fm.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return "";
		}
		
		return fm1.format(d);
	}
	
	/**
	 * 格式化时间戳为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatTimestamp2String(Timestamp date,String format){
		if(null==date)return null;
		if(null==format||("").equals(format))return null;
		return new SimpleDateFormat(format).format(date);
	}
	/**
	 * 格式化Date为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatTimestamp2String(Date date,String format){
		if(null==date)return null;
		if(null==format||("").equals(format))return null;
		return new SimpleDateFormat(format).format(date);
	}
	/**
	 * 格式化字符串为时间戳
	 * @param date
	 * @param format
	 * @return
	 */
	public static Timestamp formatString2Timestamp(String date,String format){
		if(null==date||("").equals(date))return null;
		if(null==format||("").equals(format))return null;
		return new Timestamp(formatStringToDate(date,format).getTime());
	}
	
	public static void main(String[] args) throws ParseException{
//		System.out.println(new Timestamp(System.currentTimeMillis()));
//		getYtDateString();
//		System.out.print(getDate("20150612","20150612"));
//		DateFormat fmt = new SimpleDateFormat("yyyyMMdd"); 
//		String dateBegin=fmt.format("20150612"); 
//		String dateEnd=fmt.format("20150612"); 
//		//如果获得的日期格式不是'2008-05-22',就必须要格式化一下日期 
////		String dateBegin = request.getParameter("dateBegin"); 
////		String dateEnd = request.getParameter("dateEnd"); 
//		if(java.sql.Date.valueOf(dateBegin).after(java.sql.Date.valueOf(dateEnd))){ 
//		//起始日期大于结束日期 
//			System.out.println("#############################");
////		errors.rejectValue("dateEnd", null, "起始日期必须小于结束日期!"); 
//		} 
		System.out.println(formatString2Timestamp("20150612","yyyyMMdd"));
		Timestamp begin =formatString2Timestamp("20150612","yyyyMMdd");
		Timestamp end =formatString2Timestamp(date8(),"yyyyMMdd");
		System.out.println(end);
		
		System.out.println(end.after(begin));
	}
	/**
	 * 将日期格式的字符串转换为日期
	 * @param date 源日期字符串
	 * @param format 源日期字符串格式
	 */
	public static Date formatStringToDate(String date,String format){
		if(null==date||("").equals(date))return null;
		if(null==format||("").equals(format))return null;
		SimpleDateFormat format2 = new SimpleDateFormat(format);
		try{
			Date newDate = format2.parse(date);
			return newDate;
		}catch(Exception ex){
			throw new RuntimeException(ex.getMessage());
		}
		
		
	}
	public static Timestamp addDay(Timestamp date,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return new Timestamp(cal.getTimeInMillis());
	}
	/**
	 * 将日期格式的字符串格式化成指定的日期格式
	 * @param dateStr 源日期字符串
	 * @param formatStr1 源日期字符串日期格式
	 * @param formatStr2 新日期字符串日期格式
	 */
	public static String formatDateStringToString(String dateStr,String formatStr1,String formatStr2){
		SimpleDateFormat format = new SimpleDateFormat(formatStr1);
		SimpleDateFormat format2 = new SimpleDateFormat(formatStr2);
		try{
			if(null==dateStr||("").equals(dateStr))return "";
			Date date = format.parse(dateStr);
			return format2.format(date);
		}catch(Exception ex){
			ex.printStackTrace();
			return "";
		}
	}
	
	/**
	 * yyyyMMdd 与 yyyy-MM-dd日期格式转换
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String dateFormate(String date, String regex){
		SimpleDateFormat sdf_sim = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf_more = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_target = new SimpleDateFormat(regex);
		try{
			if(date==null || "".equals(date)){
				return "";
			}else if(date.contains("-")){
				return sdf_target.format(sdf_more.parse(date));
			}else{
				return sdf_target.format(sdf_sim.parse(date));
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String formatDataToString(Date date,String format){
		//Date类型转换为String
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String nowTime = sdf.format(date);
		return nowTime;
	}
	
	public static Date formateStringToDate(String date){
		//String类型转换为Date
		String[] a = date.split("[-]");
		Date d = new Date(Integer.parseInt(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2]));
		return d;
	}
	
	
	
	/***
	* 计算2个日期之间的天数
	* @param beginTime
	* @param endTime
	* @return 相差的天数
	 * @throws ParseException 
	* @throws BusinessException
	*/
	public static float getDate(String beginTime,String endTime) throws ParseException 
	{
	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	
	Date begin = s.parse(beginTime);
	Date end = s.parse(endTime);
	float time = (end.getTime()-begin.getTime())/1000/60/60/24;
	return time;	
	}
	
/**
	 * 122221转换成12:22:21
	 * 
	 * @param time
	 * @return
	 */
	public static String time6(String time) {
		if (null == time)
			return null;
		time = time.trim();
		if (time.length() != 6)
			return time;
		else
			return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
					+ time.substring(4, 6);
	}	
	
	/**
	 * 格式化时间
	 * @param date
	 * @param regex
	 * @return
	 */
	public static String formatDate(Date date, String regex) {
		if(null == date) return null;
		
		String s = new SimpleDateFormat(regex).format(date);
		return s;
	}
	
	
	/**
	 * 将日期格式的字符串格式化为制定日期格式的字符串
	 * @param date  String 目标字符串
	 * @param patten1  String 原格式
	 * @param patten2  String 目标格式
	 * @return
	 */
	public static String formtDate2String(String date,String patten1,String patten2){
		String dateStr = "";
		try {
			   dateStr = new SimpleDateFormat(patten2).format(new SimpleDateFormat(patten1).parse(date));
			   
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateStr;
	}
	
	
	 /*****************************************
	  * @description：从指定日期起-i天
	  * 2016-3-16
	  * @param date
	  * @param i
	  * @return
	 * @throws ParseException 
	  ******************************************/
	 public static String getDayString(String date, int i) throws ParseException{
		    SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
			Calendar c  = Calendar.getInstance();
			c.setTime(sFormat.parse(date));
			c.add(Calendar.DAY_OF_MONTH, i);
			return sFormat.format(c.getTime());
	 }
	 
		public static Calendar getDateOfLastMonth(Calendar date) {
			Calendar lastDate = (Calendar) date.clone();
			lastDate.add(Calendar.MONTH, -1);
			return lastDate;
		}

	 
		public static Calendar getDateOfLastMonth(String dateStr) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try {
				Date date = sdf.parse(dateStr);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				return getDateOfLastMonth(c);
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid date format(yyyyMMdd): " + dateStr);
			}
		}
}
