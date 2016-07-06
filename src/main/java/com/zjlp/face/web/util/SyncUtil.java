package com.zjlp.face.web.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @Description: 获取一个锁对象
 * @date: 2015年10月4日 下午4:13:22
 * @author: zyl
 */
public class SyncUtil {
	private static Map<String,String> map=new LinkedHashMap<String,String>();
	public static String getLock(Object obj){
		String key=String.valueOf(obj);
		String result=map.get(key);
		if(null==result){
			map.put(key, key);
			result=key;
			if(map.size()>100000){
				Iterator<String> ite=map.keySet().iterator();
				if(ite.hasNext())map.remove(ite.next());
			}
		}
		return  result;
	}
}
