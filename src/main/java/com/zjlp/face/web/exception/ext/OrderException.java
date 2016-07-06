package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;


public class OrderException extends BaseException{

	private static final long serialVersionUID = -5618508203445260935L;

	public OrderException() {
		super();
	}

	public OrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderException(String message) {
		super(message);
	}

	public OrderException(Throwable cause) {
		super(cause);
	}

}
