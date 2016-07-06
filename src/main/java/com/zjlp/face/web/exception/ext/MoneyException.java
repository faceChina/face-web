package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class MoneyException extends BaseException {

	private static final long serialVersionUID = 870314720650604331L;

	public MoneyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MoneyException(ExceptionObject exObj) {
		super(exObj);
		// TODO Auto-generated constructor stub
	}

	public MoneyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MoneyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MoneyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
