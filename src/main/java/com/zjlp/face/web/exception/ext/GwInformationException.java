package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class GwInformationException extends BaseException {
	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -4652642965989856254L;

	public GwInformationException() {
		super();
	}

	public GwInformationException(ExceptionObject exObj) {
		super(exObj);
	}

	public GwInformationException(String message, Throwable cause) {
		super(message, cause);
	}

	public GwInformationException(String message) {
		super(message);
	}

	public GwInformationException(Throwable cause) {
		super(cause);
	}
}
