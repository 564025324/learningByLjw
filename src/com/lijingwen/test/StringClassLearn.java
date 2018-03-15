package com.lijingwen.test;

import java.util.HashMap;
import java.util.Map;

public class StringClassLearn {

	public static void spiltTest() {
		String baobensyMonth = null;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("baobensydate", "2017-12-18");

		baobensyMonth = map1.get("baobensydate").replace("-", "").substring(0, 6);
		System.out.println(baobensyMonth);
	}

	public static void main(String[] args) {
		spiltTest();
	}
}
