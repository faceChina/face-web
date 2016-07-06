package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;


public class GoodException extends BaseException{

	private static final long serialVersionUID = -5618508203445260935L;

	public GoodException() {
		super();
	}

	public GoodException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoodException(String message) {
		super(message);
	}

	public GoodException(Throwable cause) {
		super(cause);
	}

}
