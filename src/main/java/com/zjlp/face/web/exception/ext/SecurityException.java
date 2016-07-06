package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class SecurityException  extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5930485661022604409L;

	public SecurityException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SecurityException(ExceptionObject exObj) {
		super(exObj);
		// TODO Auto-generated constructor stub
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SecurityException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SecurityException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
