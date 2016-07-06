package com.zjlp.face.web.util;

import java.util.Random;

public class RandomUtil {
	/**
	 * @Description: 
	 * length:生成字符串的长度;
	 * type:"111",依次表示是否支持数字,大写字母,小写字母;1支持,0不支持.
	 * @date: 2015年9月22日 下午6:06:12
	 * @author: zyl
	 */
	public static String randomString(int length,String type){
		String str="";
		if(type!=null && type.length()>=3){
			if(type.charAt(0)=='1'){
				str+="0123456789";
			}
			if(type.charAt(1)=='1'){
				str+="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			}
			if(type.charAt(2)=='1'){
				str+="abcdefghijklmnopqrstuvwxyz";
			}
		}else{
			str="0123456789";
		}
		Random r=new Random();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<length;i++){
			sb.append(str.charAt(r.nextInt(str.length())));
		}
		return sb.toString();
	}
}
