package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;


public class AppCircleException extends BaseException {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -3103435258335361666L;

	public AppCircleException() {
		super();
	}
	public AppCircleException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppCircleException(String message) {
		super(message);
	}

	public AppCircleException(Throwable cause) {
		super(cause);
	}
}
