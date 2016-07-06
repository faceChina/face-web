package com.zjlp.face.web.exception.errorcode;

/**
 * 通用式异常列表
 * @ClassName: CErrMsg 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年7月9日 上午10:28:56
 */
public enum CErrMsg implements ErrMsg {

	/** 参数验证： 参数{}为空 */
	NULL_PARAM("NULL_PARAM", "参数[{}]为空"),
	
	/** 角色权限：角色{}没有{}的权限 */
	OPERATION_RIGHT("OPERATION_RIGHT", "角色[{}]没有[{}]的权限"),
	
	/** 查询结果为空：[{}]查询的结果不存在 */
	NULL_RESULT("NULL_RESULT", "[{}]查询的结果不存在"),
	
	/** 新增[{}]已存在 */
	ALREADY_EXISTS("ALREADY_EXISTS", "新增[{}]已存在"),
	
	;
	CErrMsg(String errorCode, String errorMesage){
		this.errorCode = errorCode;
		this.errorMesage = errorMesage;
	}
	//错误代码
	private String errorCode;
	//错误信息
	private String errorMesage;
	
	@Override
	public String getErrCd() {
		return errorCode;
	}
	@Override
	public String getErrorMesage() {
		return errorMesage;
	}
	@Override
	public String cdByMsg() {
		return this.toString();
	}
	@Override
	public String toString() {
		return new StringBuilder(errorCode).append(" : ")
				.append(errorMesage).toString();
	}
	
}
