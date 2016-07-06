package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class RechargeException extends BaseException {

	private static final long serialVersionUID = -4884008544373192526L;

	public RechargeException() {
		super();
	}

	public RechargeException(ExceptionObject exObj) {
		super(exObj);
	}

	public RechargeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RechargeException(String message) {
		super(message);
	}

	public RechargeException(Throwable cause) {
		super(cause);
	}

}
