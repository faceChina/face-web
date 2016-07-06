package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class PhoneVerificationCodeException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4586898418133039788L;

	public PhoneVerificationCodeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhoneVerificationCodeException(ExceptionObject exObj) {
		super(exObj);
		// TODO Auto-generated constructor stub
	}

	public PhoneVerificationCodeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PhoneVerificationCodeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PhoneVerificationCodeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
