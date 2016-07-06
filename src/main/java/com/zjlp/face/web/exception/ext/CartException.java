package com.zjlp.face.web.exception.ext;

import com.zjlp.face.shop.exception.BaseException;
import com.zjlp.face.shop.exception.enums.BaseExceptionEnum;

public class CartException extends BaseException {

	private static final long serialVersionUID = -2570804892256869465L;

	public CartException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartException(BaseExceptionEnum baseExceptionEnum, String... params) {
		super(baseExceptionEnum, params);
		// TODO Auto-generated constructor stub
	}

	public CartException(BaseExceptionEnum baseExceptionEnum, Throwable cause,
			String... params) {
		super(baseExceptionEnum, cause, params);
		// TODO Auto-generated constructor stub
	}

	public CartException(BaseExceptionEnum baseExceptionEnum, Throwable cause) {
		super(baseExceptionEnum, cause);
		// TODO Auto-generated constructor stub
	}

	public CartException(BaseExceptionEnum baseExceptionEnum) {
		super(baseExceptionEnum);
		// TODO Auto-generated constructor stub
	}

	public CartException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CartException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CartException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
