package com.zjlp.face.web.util;

import net.sf.json.JSONObject;

/**
 * 错误代码
 * @author xb
 */
public class ResultCode {
	/**
	 * 正确
	 */
	public static final String SUCCESS = "success";
	/**
	 * 未知错误
	 */
	public static final String NOT_ERROR = "1000";
	/**
	 * 用户对象为空
	 */
	public static final String USERVO_NULL = "1001";
	/**
	 * 手机号码不能为空
	 */
	public static final String PHONE_NO_ISNULL = "1002";
	/**
	 * 密码不能为空
	 */
	public static final String PASSWORD_NO_ISNULL = "1003";
	/**
	 * 密码不能和用户名相同
	 */
	public static final String PAS_NAME_CANNOT_SAME = "1004";
	/**
	 * 手机验证码不能为空
	 */
	public static final String PHONECODE_NO_ISNULL = "1005";
	/**
	 * 手机验证码错误
	 */
	public static final String PHONECODE_ERROR = "1006";
	/**
	 * 手机验证码超时
	 */
	public static final String PHONECODE_TIMEOUT = "1007";
	/**
	 * 用户已存在
	 */
	public static final String USER_EXIST = "1008";
	/**
	 * 赋予权限出错
	 */
	public static final String ENDOW_USER_ROLE_ERROR  = "1009";
	/**
	 * 初始化钱包出错
	 */
	public static final String INIT_WALLET_ERROR  = "1010";
	/**
	 * 用户名不存在
	 */
	public static final String USERNAME_NO_EXIST  = "1011";
	/**
	 * 密码错误
	 */
	public static final String PASSWORD_ERROR  = "1012";
	/**
	 * 登录账户不能为空
	 */
	public static final String LOGINACCOUNT_NO_NULL  = "1013";
	/**
	 * 生成官网失败
	 */
	public static final String ACTIVATE_GW_SHOP_FAIL  = "1014";
	/**
	 * 注册账号失败
	 */
	public static final String REGISTER_ACCOUNT_FAIL = "1015";
	/** 
	 * 请输入正确的邀请码
	 */
	public static final String INVITATION_CODE_ERROR = "1016";
	
	public static JSONObject getResultMsg(String code){
		String msg = "";
		if(SUCCESS.equals(code)){
			msg = "ok";
		}else if(USERVO_NULL.equals(code)){
			msg = "用户对象为空";
		}else if(NOT_ERROR.equals(code)){
			msg = "未知错误";
		}else if(PHONE_NO_ISNULL.equals(code)){
			msg = "手机号码不能为空";
		}else if(PASSWORD_NO_ISNULL.equals(code)){
			msg = "密码不能为空";
		}else if(PAS_NAME_CANNOT_SAME.equals(code)){
			msg = "密码不能和用户名相同";
		}else if(PHONECODE_NO_ISNULL.equals(code)){
			msg = "手机验证码不能为空";
		}else if(PHONECODE_ERROR.equals(code)){
			msg = "手机验证码错误";
		}else if(PHONECODE_TIMEOUT.equals(code)){
			msg = "手机验证码超时";
		}else if(USER_EXIST.equals(code)){
			msg = "用户已存在";
		}else if(ENDOW_USER_ROLE_ERROR.equals(code)){
			msg = "赋予权限出错";
		}else if(INIT_WALLET_ERROR.equals(code)){
			msg = "初始化钱包出错";
		}else if(USERNAME_NO_EXIST.equals(code)){
			msg = "用户名不存在";
		}else if(PASSWORD_ERROR.equals(code)){
			msg = "密码错误";
		}else if(LOGINACCOUNT_NO_NULL.equals(code)){
			msg = "登录账户不能为空";
		}else if(ACTIVATE_GW_SHOP_FAIL.equals(code)){
			msg = "生成官网失败";
		}else if(REGISTER_ACCOUNT_FAIL.equals(code)){
			msg = "注册账号失败";
		}else if(INVITATION_CODE_ERROR.equals(code)){
			msg = "请输入正确的邀请码";
		}else{
			msg = "未知错误";
		}
		
		return JSONUtils.resultErrCode(code, msg);
	}
}
