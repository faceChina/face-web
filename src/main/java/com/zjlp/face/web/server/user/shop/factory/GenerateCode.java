package com.zjlp.face.web.server.user.shop.factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class GenerateCode {
	
	/**
	 * 生成产品编号
	 * @Title: generateShopNo 
	 * @Description: (产品编号：HZJZ + 时间戳（yyMMddHHmm）+ 5位随机字符 + 店铺类型（1：官网、2：商城、3：脸谱、4：微小店、5：免费店铺） )
	 * @param businessId
	 * @param cardno
	 * @return
	 * @date 2014年7月18日 下午7:18:45  
	 * @author ah
	 */
	public static String generateShopNo(Integer type){
		if(null == type ) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		String BUSINESS="HZJZ";
		Date nowDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm"); 
		String stringDate = dateFormat.format(nowDate);
		sb.append(BUSINESS).append(stringDate).append(generateWord(5)).append(type);
		return sb.toString();
		
	}
	
	/**
	 * 生成邀请码
	 * @Title: generateInvitationCode 
	 * @Description: (邀请码：用户邀请码 + _ +时间戳（yyyyMMdd）+4位随机字符+ 产品类型（1：官网、2：商城、3：脸谱）) 
	 * @return
	 * @date 2014年7月24日 下午9:13:20  
	 * @author ah
	 */
	public static String generateInvitationCode(String userInvitationCode,Integer type) {
		String code = "&";
		StringBuffer sb = new StringBuffer();
		Date nowDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		String stringDate = dateFormat.format(nowDate);
		sb.append(userInvitationCode).append(code).append(stringDate).append(generateWord(4)).append(type);
		return sb.toString();
	}
	
	/**
	 * 生成站内唯一识别ID
	 * @Title: generateTokenCode 
	 * @Description: (邀请码：T+时间戳（yyyyMMddHHmmss）+5位随机字符) 
	 * @return
	 * @date 2014年7月24日 下午9:13:20  
	 * @author Administrator
	 */
	public static String generateTokenCode() {
		String product ="T";
		StringBuffer sb = new StringBuffer();
		Date nowDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String stringDate = dateFormat.format(nowDate);
		sb.append(product).append(stringDate).append(generateWord(5));
		return sb.toString();
	}
	/**
	 * 生成 随机字符串
	 * @Title: generate 
	 * @Description: (生成随机字符串) 
	 * @param length
	 * @return
	 * @date 2014年7月24日 下午8:50:57  
	 * @author ah
	 */
	public static String generateWord(int length) {
		final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }
	
	public static void main(String[] args){
		String userInvitationCode = "dowrgoasdfoire";
		System.out.println("shopNo: " + generateShopNo(4));
		System.out.println(generateWord(4));
		System.out.println("InvitationCode:"+generateInvitationCode(userInvitationCode,1));
		
	}
}
