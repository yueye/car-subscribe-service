package com.sxsd.car.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	private static String defalut_format = "yyyy-MM-dd HH:mm:ss";
	
	private static long nd = 1000 * 24 * 60 * 60;//天
	private static long nh = 1000 * 60 * 60;//时
	private static long nm = 1000 * 60;//分
	private static long ns = 1000;//秒

	/**
	 * 取得时间戳
	 * @return
	 */
	public static String sequeceId() {
		return sequeceId(false);
	}

	/**
	 * 取得时间戳+四位随机数
	 * @param isRandom
	 * @return
	 */
	public static String sequeceId(boolean isRandom) {
		return sequeceId(isRandom,null);
	}

	/**
	 * 取得时间戳+四位随机数（根据指定时间）
	 * @param isRandom
	 * @param date
	 * @return
	 */
	public static String sequeceId(boolean isRandom,Date date) {
		return sequeceId(isRandom,date,"yyyyMMddHHmmss");
	}

	/**
	 * 取得时间戳+四位随机数+时间格式
	 * @param isRandom
	 * @param date
	 * @param format
	 * @return
	 */
	public static String sequeceId(boolean isRandom,Date date,String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		if (null == date) {
			date = new Date();
		}
		long round = Math.round(Math.floor(Math.random() * (1 + 9999 - 1000)+ 1000));
		if (isRandom) {
			return sf.format(date)+round;
		}
		return sf.format(date);
	}
	/**
	 * 获取指定日期的年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.YEAR);
	}
	/**
	 * end-begin取得天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getDaySub(Date beginDate,Date endDate){ 
		long days = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
		long surplus = (endDate.getTime()-beginDate.getTime())%(24*60*60*1000);
		if (surplus > 0) {
			days++;
		}
		return days;
	} 

	/**
	 * 取得当前时间戳
	 * @return
	 */
	public static Timestamp currentTimeStamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	/**
	 * 取得指定时间和格式时间戳
	 * @param time
	 * @param format
	 * @return
	 */
	public static Timestamp currentTimeStamp(String time,String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			Date date = sf.parse(time);
			return date2timestamp(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 时间戳转Date
	 * @param timeStamp
	 * @return
	 */
	public static Date timestamp2Date(Timestamp timeStamp) {
		long time = timeStamp.getTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.getTime();
	}
	/**
	 * 时间戳转date
	 * @param timeStamp
	 * @return
	 */
	public static Timestamp date2timestamp(Date date) {
		long time = date.getTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return new Timestamp(calendar.getTimeInMillis());
	}
	/**
	 * 格式化当前时间
	 * @param format
	 * @return
	 */
	public static String fomatDate(String format) {
		return fomatDate(new Date(),format);
	}
	/**
	 * 格式化时间
	 * @param format
	 * @return
	 */
	public static String fomatDate(Date date,String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(date);
	}
	/**
	 * 格式化时间
	 * @param format
	 * @return
	 */
	public static String fomatDate(Timestamp timeStamp,String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(timestamp2Date(timeStamp));
	}


	public static String formateDate(Long time,String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(null == time ? 0L :new Date(time));
	}
	
	/**
	 * 
	 * @param date
	 * @return 获取传入时间的最后一天 返回格式:2012-06-30 23:59:59
	 */
	public static Date lastDayCurrentMonth(Date date){
	    GregorianCalendar cal2 = new GregorianCalendar();
	    cal2.setTime(date);
        int month = cal2.get(Calendar.MONTH)+1;
        int year = cal2.get(Calendar.YEAR);
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            cal2.set(Calendar.DAY_OF_MONTH,31);
        }else if(month==2){
            if((year%4==0&&year%100!=0)||year%400==0){
                cal2.set(Calendar.DAY_OF_MONTH,29);
            }else{
                cal2.set(Calendar.DAY_OF_MONTH,28);
            }
        }else{
            cal2.set(Calendar.DAY_OF_MONTH,30);
        }
        
        cal2.set(Calendar.HOUR_OF_DAY,23);
        cal2.set(Calendar.MINUTE,59);
        cal2.set(Calendar.SECOND,59);
        return cal2.getTime();
	}
	
	/**
	 * 
	 * @param date
	 * @return 获取传入时间的下个月第一天 返回格式:2012-07-01 00:00:00
	 */
	public static Date firstDayNextMonth(Date date){
	    GregorianCalendar cal2 = new GregorianCalendar();
	    cal2.setTime(date);
        int month = cal2.get(Calendar.MONTH)+2;
        int year = cal2.get(Calendar.YEAR);
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            cal2.set(Calendar.DAY_OF_MONTH,31);
        }else if(month==2){
            if((year%4==0&&year%100!=0)||year%400==0){
                cal2.set(Calendar.DAY_OF_MONTH,29);
            }else{
                cal2.set(Calendar.DAY_OF_MONTH,28);
            }
        }else{
            cal2.set(Calendar.DAY_OF_MONTH,30);
        }
        
        cal2.set(Calendar.HOUR_OF_DAY,0);
        cal2.set(Calendar.MINUTE,0);
        cal2.set(Calendar.SECOND,0);
        return cal2.getTime();
	}
	
	
	/**
	 * 
	 * @param date
	 * @return 获取传入时间的当月第一天 返回格式:2012-07-01 00:00:00
	 */
	public static Date firstDayCurrentMonth(Date date){
	    GregorianCalendar cal2 = new GregorianCalendar();
	    cal2.setTime(date);
//        int month = cal2.get(Calendar.MONTH)+1;
//        int year = cal2.get(Calendar.YEAR);
        cal2.set(Calendar.DAY_OF_MONTH,1);
        cal2.set(Calendar.HOUR_OF_DAY,0);
        cal2.set(Calendar.MINUTE,0);
        cal2.set(Calendar.SECOND,0);
        return cal2.getTime();
	}
	
	/**
	 * 
	 * @param date
	 * @return 获取传入时间的当月第一天 返回格式:2012-01-01 00:00:00
	 */
	public static Date firstMonthByYear(Date date){
	    GregorianCalendar cal2 = new GregorianCalendar();
	    cal2.setTime(date);
        cal2.set(Calendar.MONTH,0);
        cal2.set(Calendar.DAY_OF_MONTH,1);
        cal2.set(Calendar.HOUR_OF_DAY,0);
        cal2.set(Calendar.MINUTE,0);
        cal2.set(Calendar.SECOND,0);
        return cal2.getTime();
	}
	
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static Date add(Date date,Long diff){
	    date = addDay(date,Long.valueOf(diff / nd).intValue());
	    date = addHour(date,Long.valueOf(diff % nd / nh).intValue());
	    date = addMinute(date,Long.valueOf(diff % nd % nh / nm).intValue());
	    date = addSecond(date,Long.valueOf(diff % nd % nh % nm /ns).intValue());
	    return date;
	}
	
	public static Date add(Date date,Long effTime,Long expTime){
		Long diff = expTime - effTime;
	    date = addDay(date,Long.valueOf(diff / nd).intValue());
	    date = addHour(date,Long.valueOf(diff % nd / nh).intValue());
	    date = addMinute(date,Long.valueOf(diff % nd % nh / nm).intValue());
	    date = addSecond(date,Long.valueOf(diff % nd % nh % nm /ns).intValue());
	    return date;
	}
	
	public static Date addMonth(Date date,int count){
		Calendar calendar = Calendar.getInstance();
		if(date!=null){
			calendar.setTime(date);
		}
		calendar.add(Calendar.MONTH, count);
	    return calendar.getTime();
	}

	public static Date addDay(Date date,int count){
		Calendar calendar = Calendar.getInstance();
		if(date!=null){
			calendar.setTime(date);
		}
        calendar.add(Calendar.DATE, count);
	    return calendar.getTime();
	}
	public static Date addYear(Date date,int count){
		Calendar calendar = Calendar.getInstance();
		if(date!=null){
			calendar.setTime(date);
		}
        calendar.add(Calendar.YEAR, count);
	    return calendar.getTime();
	}
	
	public static Date addSecond(Date date,int count){
		GregorianCalendar cal2 = new GregorianCalendar();
	    cal2.setTime(date);
	    cal2.add(GregorianCalendar.SECOND, count);
	    return cal2.getTime();
	}
	
	public static Date addMillisecond(Date date,int count){
		GregorianCalendar cal2 = new GregorianCalendar();
	    cal2.setTime(date);
	    cal2.add(GregorianCalendar.MILLISECOND, count);
	    return cal2.getTime();
	}
	
	public static Date addHour(Date date,int count){
		GregorianCalendar cal2 = new GregorianCalendar();
	    cal2.setTime(date);
	    cal2.add(GregorianCalendar.HOUR_OF_DAY, count);
	    return cal2.getTime();
	}
	
	public static Date addMinute(Date date,int count) {
		GregorianCalendar cal2 = new GregorianCalendar();
		cal2.setTime(date);
		cal2.add(GregorianCalendar.MINUTE, count);
		return cal2.getTime();
	}
	
	public static Date String2Date(String time) {
		return String2Date(time, defalut_format);
	}
	public static java.sql.Date String2SqlDate(String time) {
		return new java.sql.Date(String2Date(time, defalut_format).getTime());
	}
	public static Date String2Date(String time,String format) {
		try {
			if (StringUtils.isBlank(format)) {
				format = defalut_format;
			}
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	/* 
     * java 比较时间大小 
     */  
       
    public static boolean getDateCompareTo(Timestamp startTime, Timestamp EndTime) {  
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        c1.setTime(timestamp2Date(startTime));  
        c2.setTime(timestamp2Date(EndTime));
        int result=c1.compareTo(c2);
        if(result>0){
        	return true;
        }
        return false;  
         
    }

	/**
	 * 获取精确到秒的时间戳
	 * @param date
	 * @return
	 */
	public static int getSecondTimestampTwo(Date date){
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime()/1000);
		return Integer.valueOf(timestamp);
	}
	
	public static void main(String[] args) throws ParseException{

//		String sevenDay = DateUtil.fomatDate(DateUtil.addDay(new Date(),-7), "yyyy-MM-dd")+" 00:00:00";
//		Timestamp timestamp = new Timestamp(DateUtil.addDay(new Date(),-7).getTime());
//		Timestamp timestamp2 = new Timestamp(new Date().getTime());
//		boolean dateCompareTo = getDateCompareTo(timestamp, timestamp2);
//		System.out.println(dateCompareTo);
//		System.out.println(sevenDay);
//		String d = "2019-01-30 23:59:59";
//		Date dd = addDay(parsString(d,"yyyy-MM-dd HH:mm:ss"),1);
//		Date dd1 = addMonth(dd,12);
//		System.out.println(fomatDate(dd1,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(getSecondTimestampTwo(new Date()));

		
	}
	
	public static String durationToStr(String duration){
		if(duration!=null && duration !=""){
		 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
	     String hms = formatter.format(Long.valueOf(duration)*1000); 
	     String t = hms.substring(0,hms.indexOf(":"));
	     int h = (Integer.valueOf(t)-8);
	     hms = (h<10?"0"+h:h+"0")+hms.substring(2, hms.length());
	     return hms;
		}else{
			return duration;
		}
	}
	
	public static Long strToDuration(String time){
		String[] my = time.split(":");
		int length = my.length;
		Long totalSec = 0L;
		if(length == 2) {
			int min = Integer.parseInt(my[0]);
			int sec = Integer.parseInt(my[1]);
			totalSec = (long) (min*60+sec);
		}
		if(length == 3) {
			int hour = Integer.parseInt(my[0]);
			int min = Integer.parseInt(my[1]);
			int sec = Integer.parseInt(my[2]);
			totalSec = (long)(hour*3600+min*60+sec);
		}
		return totalSec;
	}
	
	/**
	 * 解析字符串日期格式
	 * @param time
	 * @param format
	 * @return
	 * @exception 如果time 和format格式不同 不会出现异常，会格式化成错误日期
	 * 			  example:2018-01-01,yyyyMMdd
	 */
	public static Date parsString(String time,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date parse = null;
		try {
			parse = sdf.parse(time);
		} catch (ParseException e) {
			return null;
		}
		return parse;
	}
	
	public static Long timeStampExpByMinut(Integer num){
		return timeStampExpByMinut(null,num);
	}
	public static Long timeStampExpByMinut(Long currentTime,Integer num){
		if(currentTime == null){
			currentTime = currentTimeStamp().getTime();
		}
		return currentTime+num*60*1000;
	}
	
	/**
	 * 获取今天剩余的秒值
	 * @return
	 */
	public static Long getSyTime() {
		long now = new Date().getTime();//现在的毫秒值
		Date today = parsString(fomatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
		long tomorrow = addDay(today, 1).getTime();
		return (tomorrow - now)/1000;
	}

	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = sequeceId();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = RandomUtil.generateNumber(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}

	public static boolean overTime(long timestamp,int second){
		long newTimestamp = addSecond(new Date(timestamp * 1000L),second).getTime();
		if(newTimestamp < System.currentTimeMillis()){
			return true;
		}
		return false;
	}
}
