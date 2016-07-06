package com.zjlp.face.web.http.open.servlet;

/**
 * 友钱自定义返回code
 * 
 * @author Baojiang Yang
 * @date 2015年9月9日 下午3:17:06
 *
 */
public enum RspCode {

	/** 首次上报成功 **/
	CODE_201(201, "SUCCESS"),
	/** 二次入库成功 **/
	CODE_202(202, "SUCCESS"),
	/** 首次重复注册 **/
	CODE_203(203, "REPEAT"),
	/** 请求参数不正确 **/
	CODE_204(204, "FAILURE"),
	/** channelId授权过期 **/
	CODE_205(205, "ACCESS DENIED"),
	/** IP无权限访问 **/
	CODE_206(206, "ACCESS DENIED"),
	/** 未知错误，操作失败 **/
	CODE_207(207, "FAILURE");

	private RspCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
