package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class UserException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7830979181336269708L;

	public UserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserException(ExceptionObject exObj) {
		super(exObj);
		// TODO Auto-generated constructor stub
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
