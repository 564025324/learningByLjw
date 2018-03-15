package com.lijingwen.test;

import java.sql.Connection;
import java.util.Calendar;

import sun.security.jca.GetInstance;

import com.lijingwen.util.ConnectDB;

public class TestUtil {

	public static void calendarMethodTest() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 5);
		System.out.println(cal.getTime());
	}

	private Connection conn;

	// main方法 -- 测试
	public static void main(String[] args) {

		calendarMethodTest();		
		
		// ConnectDB conn = new ConnectDB();
		// conn.getConnection();
		// // conn.insert();
		// conn.select();
	}

}
