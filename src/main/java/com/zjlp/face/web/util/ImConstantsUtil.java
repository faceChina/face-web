package com.zjlp.face.web.util;


public class ImConstantsUtil {
	
	/** IM用户存放在Session中的Key值 */
	public static final String IM_SESSION_USER_KEY = "imUserVo";
	
	//用户类型( 0 匿名,1个人,2.店铺 3.组织)
	/** 用户类型:0 匿名 */
	public static final Integer REMOTE_TYPE_ANONYMOUS = 0;
	/** 用户类型:1个人 */
	public static final Integer  REMOTE_TYPE_PERSONAL = 1;	
	/** 用户类型:2.店铺 */
	public static final Integer  REMOTE_TYPE_SHOP = 2;	
	/** 用户类型:3.组织 */
	public static final Integer  REMOTE_TYPE_ORGANIZATION = 3;	
	
	//用户状态( -1 已删除 0 初始化  1 正常)
	/** 用户状态: -1 已删除 */
	public static final Integer STATES_USER_DEL = -1;
	/** 用户状态: 0 初始化 */
	public static final Integer STATES_USER_INIT = 0;
	/** 用户状态: 1 正常 */
	public static final Integer STATES_USER_NORMAL = 1;
}
