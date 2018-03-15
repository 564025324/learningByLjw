package com.guosen.bj.util;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


import com.guosen.bj.hrm.attendance.model.workday.WorkDayUtil;
/**
 * 
 * @author YangHui
 *  日期函数处理帮助类
 */
public class DateToolUtil {
	
	public static final String[] DAYOFWEEKS={"日", "一", "二", "三", "四", "五", "六"};
	
	/**
	 * 返回与date对应的Calendar类对象
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(java.util.Date date){
		if(date==null)
			return null;
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		return cal;
	}
	
	public static Calendar getCalendar(java.sql.Date date){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTimeInMillis(date.getTime());
		return cal;
	}
	
	public static Calendar getCalendar(java.sql.Timestamp timestamp){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTimeInMillis(timestamp.getTime());
		return cal;
	}
	
	@SuppressWarnings("deprecation")
	public static Calendar getCalendar(java.util.Date date, java.sql.Time time){
		Calendar cal=getCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, time.getHours());
		cal.set(Calendar.MINUTE, time.getMinutes());
		cal.set(Calendar.SECOND, time.getSeconds());
		return cal;
	}
	
	public static Timestamp getTimestamp(java.util.Date date){
		return date==null?null:new Timestamp(date.getTime()); 
	}
	
	public static java.util.Date getDate(Timestamp time){
		if(time==null)
			return null;
		return new java.util.Date(time.getTime());
	}

	
	
	/**
	 * 
	 *  计算相隔日期的自然月数
	 * @param d1  
	 * @param d2
	 * @return
	 */
	public static int getMonthsBetween(java.util.Date d1, java.util.Date d2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		
		if(cal1.after(cal2)){
			Calendar temp = cal1;
			cal1 = cal2;
			cal2 = temp;
		}
		int year = cal2.get(Calendar.YEAR)-cal1.get(Calendar.YEAR);		
        int result = year * 12;
        
        result += cal2.get(Calendar.MONTH)-cal1.get(Calendar.MONTH);
		return result;
	}

	/**
	 * 
	 *  获取两个相隔日期的天数,有符号数
	 * @param d1
	 * @param d2
	 * @return 
	 */
	public static int getDaysBetween(Calendar d1, Calendar d2){
		if(d1.after(d2)){
			Calendar temp = d1;
			d1 = d2;
			d2 = temp;			
		}
		int days = d2.get(Calendar.DAY_OF_YEAR)-d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if(d1.get(Calendar.YEAR)!= y2){
			Calendar temp=(Calendar)d1.clone();
			do{
				days += temp.getActualMaximum(Calendar.DAY_OF_YEAR);
				temp.add(Calendar.YEAR, 1);
			}while(temp.get(Calendar.YEAR)!= y2);			
		}		
		
		return (d1.after(d2))?-days:days;
	}
	
	public static int getDaysBetween(java.util.Date d1, java.util.Date d2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		return getDaysBetween(cal1, cal2);		
	}
	/**
	 * 
	 *  获取指定年度第一天
	 * @param year
	 * @return
	 */
	public static java.util.Date getTheFirstDayOfYear(String year){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date result = null;
		try {
			result =  sdf.parse(year + "-1-1");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 *  获取指定年度的最后一天
	 * @param year
	 * @return
	 */
	public static java.util.Date getTheLastDayOfYear(String year){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date result = null;
		try {
			result =  sdf.parse(year + "-12-31");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;		
	}
	
	public static java.util.Date getTheFirstDayOfMonth(int year, int month){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	public static java.util.Date getTheFirstDayOfMonth(java.util.Date date){
		Calendar calDay=DateToolUtil.getCalendar(date);
		calDay.set(Calendar.DAY_OF_MONTH, 1);
		return calDay.getTime();		
	}
	
	public static java.util.Date getTheLastDayOfMonth(int year, int month){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();		
	}
	
	public static java.util.Date getTheLastDayOfMonth(java.util.Date date){
		Calendar calDay=DateToolUtil.getCalendar(date);
		calDay.set(Calendar.MONTH, calDay.get(Calendar.MONTH) + 1);
		calDay.set(Calendar.DAY_OF_MONTH, 1);		
		calDay.add(Calendar.DATE, -1);
		
		return calDay.getTime();
						
	}
		
	/**
	 * 
	 * 根据生日日期计算年龄，公历(阳历)算法
	 * @param birthday
	 * @return
	 */
	public static int getAge(java.util.Date birthday){
		Calendar now = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(birthday);			
		if(now.before(cal)){
			throw new IllegalArgumentException("非法参数，生日不能在当前时间后");
		}	
		int age= now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		if(now.get(Calendar.MONTH)<cal.get(Calendar.MONTH)){
			age--;
		}
		else if(now.get(Calendar.MONTH)==cal.get(Calendar.MONTH)){
			if(now.get(Calendar.DAY_OF_MONTH)<cal.get(Calendar.DAY_OF_MONTH)){
				age--;
			}
		}			
		return age;
	}
	
	/**
	 * 获取相差年数
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int getYearsBetween(java.util.Date fromDate, java.util.Date  toDate){
		return (DateToolUtil.getCalendar(toDate).get(Calendar.YEAR))-(DateToolUtil.getCalendar(fromDate).get(Calendar.YEAR));
	}
	
    public static Time getAfterTime(Time time, int minitues){
    	Calendar afterTime = Calendar.getInstance();
    	afterTime.setTime(time);
    	afterTime.add(Calendar.MINUTE, minitues);
    	return new Time(afterTime.getTime().getTime());
    }
	
	
	/** 
	 *  比较time和date的时间，time>date则返回1，time=date则返回0，time<date则返回-1 
	 * @param time
	 * @param date
	 * @return 
	 */
	@SuppressWarnings("deprecation")
	public static int compareTime(Time time, java.util.Date date){
		if(time!=null&&date==null){
			return 1;
		}
		Time temp=getTime(date);
		temp.setSeconds(date.getSeconds());				
		return time.compareTo(temp);		
	}
	
	
	@SuppressWarnings("deprecation")
	public static Time getTime(java.util.Date date){
		if(date==null)
			return null;
		Time temp=new Time(0);
		temp.setHours(date.getHours());
		temp.setMinutes(date.getMinutes());
		temp.setSeconds(date.getSeconds());
		return temp;
	}
	
	/** 
	 *  判断两个日期是否同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isTheSameDay(java.util.Date date1, java.util.Date date2){
		return (date1.getYear()==date2.getYear()&&date1.getMonth()==date2.getMonth()&&date1.getDate()==date2.getDate());
	}
		
	/**
	 * 
	 * 比较两个Date的日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(java.util.Date date1, java.util.Date date2){
		return splitDate(date1).compareTo(splitDate(date2));
	}
		
	
	
	
	@SuppressWarnings("deprecation")
	public static java.util.Date splitDate(java.util.Date Src){
		return new  java.util.Date(Src.getYear(), Src.getMonth(), Src.getDate());
	}
	
	@SuppressWarnings("deprecation")
	public static Time splitTime(java.util.Date Src){
		return new Time(Src.getHours(), Src.getMinutes(), Src.getSeconds());
	}
	
	public static java.util.Date getTheFirstDayOfYear(java.util.Date date){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}
	
	public static java.util.Date getTheLastDayOfYear(java.util.Date date){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.roll(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}	
	
	public static int getMaxDaysByYearMonth(int year, int month){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		return cal.getActualMaximum(Calendar.DATE);
	}
	
	
	
	
	
	public static String formatTime(java.util.Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		return sdf.format(date);
		
	}
	
	
	/**
	 * 根据参数转日期类型为字符串格式 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param withDate
	 * @param withTime
	 * @return
	 */
	public static String formatDate(java.util.Date date, boolean withDate, boolean withTime){
		String f="";
		if(date==null)
			return f;
		if(withDate)
			f="yyyy-MM-dd";
		if(withTime){
			if(!f.equals(""))
				f += " ";
			f += "HH:mm:ss";
		}
		if(f.equals(""))
			return f;
		SimpleDateFormat sdf=new SimpleDateFormat(f);
		return sdf.format(date);
	}
	
	public static String formatDate(java.sql.Timestamp timestamp, boolean withDate, boolean withTime){
		java.util.Date date=getDate(timestamp);
		return formatDate(date, withDate, withTime);
	}
	
	
	/**
	 * 根据指定转换日期或者时间,格式yyyy-MM-dd HH:mm:ss
	 * @param DateStr
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	public static java.util.Date parseDate(String DateStr, boolean withTime) throws ParseException{		
		String f="";
		f="yyyy-MM-dd";
		if(withTime)
			f += "HH:mm:ss";
		if(f.equals(""))
			return null;
		SimpleDateFormat sdf=new SimpleDateFormat(f);
		if(DateStr==null||DateStr.equals(""))
			return null;
		else
		    return sdf.parse(DateStr);
	}
	
	public static java.sql.Time parseTime(String timeStr){
		if(timeStr==null||timeStr.equals(""))
			return null;
		return java.sql.Time.valueOf(timeStr);
	}
	
	/**
	 * 格式化日期，生成 YYYY-MM-DD 上午或者下午
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String formatDateTimeWithAPM(java.util.Date date){
		String result=formatDate(date, true, true);
//		if(date.getHours()<=12)
//			result += " 上午";
//		else
//			result += " 下午";
		return result;
	}
		
	
	/**
	 * 增加日期，
	 * @param date
	 * @param field Calendar.
	 * @param amount
	 * @return
	 */
	public static java.util.Date add(java.util.Date date,int field, int amount){
		Calendar cal=DateToolUtil.getCalendar(date);
		cal.add(field, amount);
		return cal.getTime();		
	}
	
	
	/**
	 * 根据日期字符串、am或pm，生成其请假申请时间
	 * @param dateStr
	 * @param APM
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	public static java.util.Date parseDate(String dateStr, String TimeStr) throws ParseException{
		java.util.Date result=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String tempDateStr=dateStr + " " + TimeStr;
	    result=sdf.parse(tempDateStr.trim());			

	    	   
		return result;
	}
		
	
	private static String withZeroPrefix(int value){
		String result=Integer.toString(value);
		if(value<10)
			return "0"+result;
		return result;
	    
	}
	
	public static String formatCalendar(Calendar cal, boolean witDate, boolean withTime){
		String result="";
		if(cal!=null){
			if(witDate)
				result += cal.get(Calendar.YEAR) + "-" + withZeroPrefix(cal.get(Calendar.MONTH) + 1) + "-" + withZeroPrefix(cal.get(Calendar.DAY_OF_MONTH));	
			if(withTime)
				result += (!result.equals("")?" ":"") + withZeroPrefix(cal.get(Calendar.HOUR_OF_DAY)) + ":" + withZeroPrefix(cal.get(Calendar.MINUTE)) + ":" + withZeroPrefix(cal.get(Calendar.SECOND));
		}
		return result;
	}
	
	
	public static Calendar combineYearAndDate(int year, java.util.Date date){
		Calendar result=Calendar.getInstance();
		result.clear();
		result.setTime(date);
		result.set(Calendar.YEAR, year);				
		return result;
	}
	
	
				
	/**
	 * 获取对应汉字数字，目前只支持1到7
	 * @param i
	 * @return
	 */
	public static String getWeekDayCharater(int i){
		return DAYOFWEEKS[i-1];       
	}
	

	
	public static Calendar getTheMondayOfWeek(Calendar calendar){
		Calendar cal=(Calendar)calendar.clone();
        int min = cal.getActualMinimum(Calendar.DAY_OF_WEEK); //获取周开始基准
        int current = cal.get(Calendar.DAY_OF_WEEK); //获取当天周内天数
        
        cal.add(Calendar.DAY_OF_WEEK, min - current + 1); //当天-基准，获取周开始日期
        
        return cal;    					
	}
	
	public static Calendar getTheFridayOfWeek(Calendar calendar){
		Calendar cal=getTheMondayOfWeek(calendar);
		cal.add(Calendar.DAY_OF_WEEK, 4);
		return cal;	
	}
	
	public static Calendar getTheMondayOfWeek(int year, int weekOfYear){
		java.util.Date date=getTheFirstDayOfMonth(year, 1);
		
		Calendar cal=getCalendar(date);
		cal.add(Calendar.WEEK_OF_YEAR, weekOfYear-1);
		return getTheMondayOfWeek(cal);
	}
	
	
	public static Calendar getTheFridayOfWeek(int year, int weekOfYear){
		java.util.Date date=getTheFirstDayOfMonth(year, 1);
		
		Calendar cal=getCalendar(date);
		cal.add(Calendar.WEEK_OF_YEAR, weekOfYear-1);
		return getTheFridayOfWeek(cal);
	}	
		
	
	public static double getTimeDiffMinutes(Time beginTime, Time endTime){
		long diff=endTime.getTime()-beginTime.getTime();
		return diff/(1000*60);
		
		
	}
	
	
    public static int getThisSeasonTime(Calendar cal){
    	int month=cal.get(Calendar.MONTH) + 1;
        int quarter=-1;    
        if(month>=1&&month<=3){     
            quarter=1;     
        }     
        if(month>=4&&month<=6){     
            quarter=2;       
        }     
        if(month>=7&&month<=9){     
            quarter = 3;     
        }     
        if(month>=10&&month<=12){     
            quarter = 4;     
        }
        return quarter;
    }	
	
	
	
	
	
	static int a=0;
	
	public static void test01() throws Exception{
	a++;
	System.out.println("test01"+a);
	if(a<9){//递归头
		if(a==8)
			throw new Exception("异常");
	test01();
	}else{//递归体
	System.out.println("结束");
	}
	}	
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {	
		try {			
			//test01();
//			Calendar cal=Calendar.getInstance();
//			cal.add(Calendar.MONTH, -1);
//			int quarter=getThisSeasonTime(cal);			
//			while(getThisSeasonTime(cal)==quarter){				
//				System.out.println(cal);		
//				cal.add(Calendar.MONTH, -1);
//			}
			System.out.println(getTheFirstDayOfMonth(new Date()));
			
																	
              
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}				
	}
}
