package com.zjlp.face.web.util;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;


public class SkuPropertiesUtil {

	
	public static String sortProperties(String skuProperties){
		if (StringUtils.isBlank(skuProperties)) {
			return skuProperties;
		}
		String[] strs = skuProperties.split(";");
		Arrays.sort(strs);  
		StringBuffer sorts = new StringBuffer();
		for(String str : strs) {  
			sorts.append(str).append(";");
		} 
		sorts.delete(sorts.length()-1, sorts.length());
		return sorts.toString();
	}
	
	
	public static void main(String[] args) {
		String skuProperties ="58";
		String result  = sortProperties(skuProperties);
		System.out.println(result);
	}
}
