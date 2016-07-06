package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

public class AppCustomerException extends BaseException{

	private static final long serialVersionUID = 4750018189385837482L;

	public AppCustomerException() {
		super();
	}

	public AppCustomerException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppCustomerException(String message) {
		super(message);
	}

	public AppCustomerException(Throwable cause) {
		super(cause);
	}

}
