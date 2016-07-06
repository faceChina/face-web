package com.zjlp.face.web.util;

import java.util.regex.Pattern;

public class VerificationRegexUtil {
	
	/**
	 * 手机号验证 （182开头的无法验证）
	 * 
	 * @Title: phoneVer
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param phone
	 * @return boolean 返回类型
	 * @author wxn
	 * @date 2014年12月24日 下午3:59:39
	 */
	@Deprecated
	public static boolean phoneVer(String phone){
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * @Title: newPhoneVer
	 * @Description: (最新手机号正则)
	 * @param phone
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月31日 下午5:59:31
	 */
	public static boolean newPhoneVer(String phone){
		String regex = "^[1][3,4,5,8][0-9]{9}$";
		return Pattern.matches(regex, phone);
	}
	/**
	 * 快递单号验证
	* @Title: logisticsNumVer
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param logisticsNum
	* @return boolean    返回类型
	* @author wxn  
	* @date 2014年12月24日 下午4:00:01
	 */
	public static boolean logisticsNumVer(String logisticsNum){
		
		String logisticsNumRegex = "^[0-9a-zA-Z]{6,}$";
		return Pattern.matches(logisticsNumRegex, logisticsNum);
	}
	
/*	public static void main(String[] args){
		String phone = "18868823930";
		String ln = "x";
		boolean b = phoneVer(phone);
		
		System.out.println(phone);
		System.out.println(b);
		System.out.println("===========");
		boolean c = logisticsNumVer(ln);
		System.out.println(c);
	}*/
}
