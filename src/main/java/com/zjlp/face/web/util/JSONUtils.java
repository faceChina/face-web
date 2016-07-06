package com.zjlp.face.web.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class JSONUtils {

	/**
	 * 返回错误码
	 * @param code		错误码
	 * @param msg		错误信息
	 * @return
	 */
	public static JSONObject resultErrCode(String code,Object msg){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(code, msg);
		return JSONObject.fromObject(resultMap);
	}
	
	/**
	 * 返回正确码
	 * @return
	 */
	public static JSONObject resultSuccess(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(ResultCode.SUCCESS, "ok");
		return JSONObject.fromObject(resultMap);
	}
	
	/**
	 * 正确返回对象信息
	 * @param resultMap		{"xxx":"xxxx"}
	 * @return
	 */
	public static JSONObject resultSuccessObject(Map<String,Object> map){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(ResultCode.SUCCESS, "ok");
		resultMap.putAll(map);
		return JSONObject.fromObject(resultMap);
	}
}
