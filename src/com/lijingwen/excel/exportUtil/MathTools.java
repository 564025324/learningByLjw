package com.guosen.bj.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class MathTools {
	/**
	 * 设置小数位和位舍
	 * @param value
	 * @param scale   小数位精度
	 * @param roundingMode   舍位模式 (4 四舍五入)
	 * @return
	 */
	public static float roundOff(float value, int scale, int roundingMode){
		BigDecimal bd=new BigDecimal((double)value); 
		bd=bd.setScale(scale, roundingMode);
		return bd.floatValue();
	}
	
	/**
	 * 浮点运算，除以 10000
	 */
	public static Double divide10000(Double d) throws Exception {
		if (d == null) {
			d = 0.0;
		}
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = b1.divide(new BigDecimal(10000));
		return b2.doubleValue();
	}
	/**
	 * 浮点运算，乘以10000
	 */
	public static Double multiply10000(Double d) throws Exception {
		if (d == null) {
			d = 0.0;
		}
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = b1.multiply(new BigDecimal(10000));
		return b2.doubleValue();
	}
	
	  public static boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	  }	
	  
	  public static void main(String[] args) {
		  System.out.println(isInteger("4989898"));
		  System.out.println(isInteger("498ee9898"));
		  System.out.println(isInteger("(ddd)"));
		  
		
		  
	  };	  
	
	
}
