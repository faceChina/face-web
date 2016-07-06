package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class PayException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6365164651667055854L;

	public PayException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayException(ExceptionObject exObj) {
		super(exObj);
		// TODO Auto-generated constructor stub
	}

	public PayException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PayException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PayException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
