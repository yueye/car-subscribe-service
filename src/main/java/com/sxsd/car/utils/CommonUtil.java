package com.sxsd.car.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CommonUtil {

	public static int getRandomNum() {
		int max=3;
        int min=0;
        Random random = new Random();
        return   random.nextInt(max)%(max-min+1) + min;
	}
	
	public static String durationToStr(String duration,String cpId){
		try{
			boolean flag = false;
			if(StringUtils.isBlank(cpId)) {
				flag = true;
			}else {
				if(cpId.equals("117")) {//tv 应用app，时长存的是 应用大小
					if(StringUtils.isNotBlank(duration)) {
						DecimalFormat df = new DecimalFormat("0.00");
						double aaa = Double.valueOf(duration);
						return df.format((aaa/1024)/1024)+"M";
					}
				}else {
					flag = true;
				}
			}
			if(flag) {
				if(duration!=null && duration !="" && duration !="null"&& !duration.equals("null")){
					 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
				     String hms = formatter.format(Long.valueOf(duration)*1000); 
				     String t = hms.substring(0,hms.indexOf(":")); 
				     int h = (Integer.valueOf(t)-8);
				     hms = (h<10?"0"+h:h+"0")+hms.substring(2, hms.length());
				     return hms;
				}else{
					return duration;
				}
			}else {
				return "";
			}
		}catch(Exception e){
			return "";
		}
	}
	
	public static String durationToStr2(String duration){
		try{
			if(duration!=null && duration !="" && duration !="null"&& !duration.equals("null")){
				 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				 Long second = Long.valueOf(duration)*1000;
			     String hms = formatter.format(second);
			     if(second >=3600000) {
			    	 String t = hms.substring(0,hms.indexOf(":")); 
				     int h = (Integer.valueOf(t)-8);
				     hms = h+hms.substring(2, hms.length());
			     }else {
			    	 hms = hms.substring(3, hms.length());
			     }
			     return hms;
			}else{
				return duration;
			}
		}catch(Exception e){
			return "";
		}
	}
	
	
	public static String getTimeSpace() {
		Calendar calendar = Calendar.getInstance();  
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		if(hours>=7 && hours <=10) {
			return "morning";
		}else if(hours>=11 && hours <=14) {
			return "noon";
		}else if(hours>=16 && hours <=20) {
			return "night";
		}else {
			return "no";
		}
		
		
	}
	
	public static String getFormatDate(Date date,String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		if (null == date) {
			date = new Date();
		}
		return sf.format(date);
	}
	
	/**
	 * 获取指定日期所在的 星期；周一，week=1，周二 week=2。。。
	 * @param time
	 * @param week
	 * @return
	 */
	public static String getWeekByDate(Date time,int week,String formate) {
		SimpleDateFormat dateFa=new SimpleDateFormat(formate);
        Calendar cal = Calendar.getInstance();  
        cal.setTime(time);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
//        System.out.println("要计算日期为:" + date1.format(cal.getTime())); // 输出要计算日期  
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        if(week == 1) {
	        String imptimeBegin = dateFa.format(cal.getTime());  
//	        System.out.println("所在周星期一的日期：" + imptimeBegin); 
	        return imptimeBegin;
        }else {
	        cal.add(Calendar.DATE, week-1);  
	        String imptimeMi = dateFa.format(cal.getTime());  
//	        System.out.println("所在周星期"+week+"的日期：" + imptimeMi); 
	        return imptimeMi;
        }
  
    }
	
	/**
	 * 获取指定日期的前day天的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static String getDatePreDay(Date date,int day,String format) {
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -day);  
        date = calendar.getTime();  
        return getFormatDate(date,format); 
	}
	
	/**
	 * 获取指定时间 ，前n个小时的整点
	 * @param date
	 * @param n
	 * @return
	 */
	public static String getLastHourTime(Date date,int n){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY)-n);
        date = ca.getTime();
        return sdf.format(date);
    }
	
	public static Date getLastHourTimeDate(Date date,int n){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY)-n);
        date = ca.getTime();
        return date;
    }
	
	/**
	 * 获取当前时间的 时分秒格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatTime(Date date,String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		if (null == date) {
			date = new Date();
		}
		return sf.format(date);
	}
	
	/**
	 * 获取指定时间的 小时或者分钟
	 * @param date  指定日期
	 * @param t  1返回小时 2返回分钟
	 * @return
	 */
	public static int getDateHourMin(Date date,int t) {
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date);
		if(t==1) {
			return calendar.get(Calendar.HOUR_OF_DAY);
		}else {
			return calendar.get(Calendar.MINUTE);
		}
	}
	
	/**
	 * 字符串 转换为 日期格式
	 * @param date
	 * @return
	 */
	public static Date coverStringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取 指定时间，h个小时后的时间，的整点时间
	 * @param date
	 * @param h
	 * @return
	 */
	public static Date getIntTime(Date date,int h) {
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date);
		int min = calendar.get(Calendar.MINUTE);
		calendar.add(Calendar.HOUR, h);//h个小时后的时间
		calendar.add(Calendar.MINUTE, -min);//减去 分钟，只剩下整点
		calendar.set(Calendar.SECOND,0);//秒钟 置为0
		return calendar.getTime();
	}
	
	/**
	 * 指定 时间，多久之后的时间
	 * @param date
	 * @param t 分钟
	 */
	public static Date getDateHourMinx(Date date,int t) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, t);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 两个时间  相差多少分钟； date2-date
	 * @param date
	 * @param date2
	 */
	public static long getTimeDifferenceStr(String date,String date2) {
		long end = coverStringToDate(date2).getTime();
		long begin = coverStringToDate(date).getTime();
		return (end-begin)/(1000*60);
	}
	
	/**
	 * 两个时间  相差多少分钟； date2-date
	 * @param date
	 * @param date2
	 */
	public static long getTimeDifferenceDate(Date date,Date date2) {
		long end =date2.getTime();
		long begin = date.getTime();
		return (end-begin)/(1000*60);
	}
	
	/**
	 * 获取当天的日期
	 * @return  yyyy-MM-dd
	 */
	public static String getToday() {
		return getFormatDate(new Date(),"yyyy-MM-dd");
	}

	
	
	public static void main(String[]arg) { 
		System.out.println(getFormatTime(new Date(),"HH:mm:ss"));
		
		getDateHourMinx(new Date(),9);
		
		System.out.println(getIntTime(new Date(), 1));
	}
}
