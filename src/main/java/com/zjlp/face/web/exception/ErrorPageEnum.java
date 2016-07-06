package com.zjlp.face.web.exception;

/**
 * 错误页面列表
 * @ClassName: ErrorPageEnum 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年3月4日 上午9:29:32
 */
public enum ErrorPageEnum {
	
	ERRORPAGE_LOGIN("000","/login"),
	
	ERRORPAGE_500("500", "/m/error/500"),
	
	ERRORPAGE_404("404", "/m/common/error404"),
	
	ERRORPAGE_ERROR("555", "/m/common/error-back"),
	
	
	//-------------------wap------------------------
	ERRORPAGE_APP_ERROR("WAP_ERROR_BACK", "/wap/common/error-back"),
	
	ERRORPAGE_MESSAGE_ERROR("WAP_ERROR_BACK_NO_BOTTOM", "/wap/common/error-back-no-bottom"),
	
	ERRORPAGE_APP_SORRY("WAP_SORRY_404", "/wap/common/sorry404"),
	
	ERRORPAGE_FREE_ERROR("WAP_ERROR_BACK", "/wap/operation/bestface/freeshop/error-back"),
	
	ERRORPAGE_FREE_SORRY("WAP_SORRY_404", "/free/common/sorry404"),
	;
	//错误页面代码
	private String errPageCode;
	//错误页面url
	private String errPageUrl;
	
	private ErrorPageEnum(String errPageCode, String errPageUrl) {
		this.errPageCode = errPageCode;
		this.errPageUrl = errPageUrl;
	}

	/**
	 * 通过错误页面代码获取错误页面url
	 * 
	 * @param errPageCode
	 *            错误页面代码
	 * @return 错误页面url
	 */
	public static String getErrUrlByCode(String errPageCode) {
		for (ErrorPageEnum url : ErrorPageEnum.values()) {
			if (url.getErrPageCode().equals(errPageCode)) {
				return url.getErrPageUrl();
			}
		}
		return null;
	}

	/**
	 * 获取错误页面代码
	 */
	public String getErrPageCode() {
		return errPageCode;
	}

	/**
	 * 获取错误页面url
	 */
	public String getErrPageUrl() {
		return errPageUrl;
	}

}
