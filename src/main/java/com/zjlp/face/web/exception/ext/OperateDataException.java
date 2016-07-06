package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class OperateDataException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7351768245783207325L;

	public OperateDataException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OperateDataException(ExceptionObject exObj) {
		super(exObj);
		// TODO Auto-generated constructor stub
	}

	public OperateDataException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public OperateDataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public OperateDataException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
