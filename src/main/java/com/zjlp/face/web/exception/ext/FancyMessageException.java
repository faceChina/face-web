package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class FancyMessageException extends BaseException {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -4652642965989856254L;

	public FancyMessageException() {
		super();
	}

	public FancyMessageException(ExceptionObject exObj) {
		super(exObj);
	}

	public FancyMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public FancyMessageException(String message) {
		super(message);
	}

	public FancyMessageException(Throwable cause) {
		super(cause);
	}

	
}
