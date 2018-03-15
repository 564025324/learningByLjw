package com.guosen.bj.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringTools {

	public static String getMD5Str(String arg) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(arg.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	
	public static String getStringFromObject(Object obj){
		if (obj != null)
			return String.valueOf(obj).trim();
		else
			return "";
	}
	
	public static String getStringFromArray(Object[] obj){
		if(obj != null && obj[0] != null)
			return (String)obj[0];
		else
			return "";
	}
	
	public static boolean strToBoolean(String value){
		return (value!=null)&&value.equals("1")?true:false;
	}
	
	public static String booleanToStr(boolean value){
		return value?"1":"0";
	}	
	
	public static String formatBoolean(boolean value){
		return value?"是":"";
	}
	
	public static Object getObjectFromArray(Object[] org){
		if (org!=null && org.length>0)
			return org[0];
		else
			return "";
	}
	
	/**
	 * 产生随机字符串
	 * @param contents  可选字符串数组
	 * @param length    生成的随机字符串的长度
	 * @return
	 */
	public static String getRandomStr(char[] contents, int length){
		int max=contents.length;
		int randomIndex=0;
		StringBuffer buffer=new StringBuffer("");
		Random r=new Random();
		int count=0;
		while(count<length){
			randomIndex=Math.abs(r.nextInt(max));
			if(randomIndex>0&&randomIndex<contents.length){
				buffer.append(contents[randomIndex]);
				count++;
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 以分隔符合并一个数组为一个字符串
	 * @param <T>
	 * @param arrayContent 数组内容
	 * @param splitter  分隔符
	 * @param endPrefix 是否结尾加上分隔符
	 * @return
	 */
	public static <T> String StringConnect(T[] arrayContent, String splitter, boolean endPrefix){
		StringBuffer buffer=new StringBuffer();
		T t=null;
		if(arrayContent==null)
			return "";
		for(int i=0;i<arrayContent.length;i++){
			t=arrayContent[i];
			if(i==0)
				buffer.append(t.toString());
			else
				buffer.append(splitter).append(t.toString());
		}
		if(endPrefix){
			buffer.append(splitter);
		}
		return buffer.toString();
	}
	
	/**
	 * 限制字符串输出长度，大于指定长度则截断并带"..."符号
	 * @param length
	 * @param src
	 * @return
	 */
	public static String makeLimitString(Integer length, String src){
		String result=src;
		if(result==null)
			return "";
		if(result.length()>length){
			result=result.substring(0, length);
			result += "...";
		}
		return result;
	}
	
	public static float parseFloat(String value,float defaultValue){
		if(value==null||value.equals(""))
			return defaultValue;
		else
		    return Float.parseFloat(value);
	}
	
	/**
	 * 给字符串后部的整数部分，增加1，如：BJ0001->BJ0002
	 * @param src
	 * @return
	 */
	public static String incStrinNum(String src){
		String result=src;
		//src.format(format, args)
		return result;
	}
	
	public static  String getCharacterFromNum(String num){
		String result="";
		if(num==null){
			result="未知";
		}
		else if(num.equals("1")){
			result="一";
		}
		else if(num.equals("2")){
			result="二";
		}
		else if(num.equals("3")){
			result="三";
		}
		else if(num.equals("4")){
			result="四";
		}
		else if(num.equals("5")){		
			result="五";
		}
		return result;		
	}
	
	/**
	 * 由GBK文件名生成XLS导出文件名
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getExpXlsISOFileName(String fileName) throws UnsupportedEncodingException{
		return new String(fileName.getBytes("GBK"), "ISO8859-1");
	}
	
	/**
	 * 生成32位GUID字符串
	 * @return
	 */
	public static String getGUID(){
		UUID uuid=UUID.randomUUID();
		String result=uuid.toString().replace("-", "");	
		return result;
	}
	
	/**
	 * 获取文件扩展名(1.txt -> txt)
	 * @param filename
	 * @return
	 */
	public static String getFileExtName(String filename){
		int index=filename.lastIndexOf(".");
		return filename.substring(index+1, filename.length());
	}
	
	public static Boolean isIpAddress(String s) {
		String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}

	public static String getClientAddress(HttpServletRequest request) {
		String address = request.getHeader("X-Forwarded-For");
		if (address != null && isIpAddress(address)) {
			return address;
		}
		return request.getRemoteAddr();
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}	
	
	/**
	 * 是否是有效的Email地址
	 * @param value
	 * @return
	 */
	public static boolean isEmail(String value){
		String regex="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value);
		return m.matches();		
		
	}
	
	public static void main(String[] args){
//		char[] aaa={'a', 'b','c' ,'1', 'd','3','e', 'f', '9'};
//		for(int i=0;i<100;i++)
//		  System.out.println(getRandomStr(aaa, 6));
		
		System.out.println(StringTools.getMD5Str("123"));
//		String[] aar={"1","dd","c","dddd"};		
//		System.out.println(StringConnect(new String[]{"1","dd","c","dddd"}, "->", false));
//		System.out.println(StringConnect(new String[]{"1","dd","c","dddd"}, "->", true));
//		System.out.println(StringUtils.join(new String[]{"1","dd","c","dddd"}, ";"));
		System.out.println(Integer.MAX_VALUE);
		String tempStr="销售部xxx";
		System.out.println(tempStr.matches("销售.+"));
		
		System.out.println((int)Math.floor(102/(365*1.0)*5));
		
		System.out.println(StringTools.isEmail("ayoya@163.com"));
		System.out.println(StringTools.isEmail("ayoya.yang@163.com"));
		System.out.println(StringTools.isEmail("tuoguan_sh@bank-of-china.com"));
		String regex="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher("0xLJCDD0B9DCD2B5CEF1zBJFHJRJGTG@mail.notes.bank-of-china.com");
		if(m.matches()){
			System.out.println("true");
		}
		
		
		
	}
	

}
