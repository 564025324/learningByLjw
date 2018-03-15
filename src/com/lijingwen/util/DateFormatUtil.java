package com.lijingwen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lijingwen.po.Student;

public class DateFormatUtil {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm");
		Date nowtime = new Date();
		Date nowtime2 = sdf.parse("20171205 04:15:00");
		
		
		System.out.println(nowtime2);
//		System.out.println(sdf.format(nowtime2));
		
//		Student student = new Student();
//		student.setBirthday(nowtime);
//		
//		System.out.println(student.getBirthday());
	}

}
