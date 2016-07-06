package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class PopularizeException extends BaseException {

	private static final long serialVersionUID = -6321211841260827158L;


	public PopularizeException() {
		super();
	}

	public PopularizeException(ExceptionObject exObj) {
		super(exObj);
	}

	public PopularizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PopularizeException(String message) {
		super(message);
	}

	public PopularizeException(Throwable cause) {
		super(cause);
	}
}
