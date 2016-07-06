package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class WechatException extends BaseException {

	private static final long serialVersionUID = -8313929396621368590L;

	public WechatException() {
		super();
	}

	public WechatException(ExceptionObject exObj) {
		super(exObj);
	}

	public WechatException(String message, Throwable cause) {
		super(message, cause);
	}

	public WechatException(String message) {
		super(message);
	}

	public WechatException(Throwable cause) {
		super(cause);
	}

}
