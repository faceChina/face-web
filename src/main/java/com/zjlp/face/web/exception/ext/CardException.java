package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class CardException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3543457507156270848L;

	public CardException() {
		super();
	}

	public CardException(ExceptionObject exObj) {
		super(exObj);
	}

	public CardException(String message, Throwable cause) {
		super(message, cause);
	}

	public CardException(String message) {
		super(message);
	}

	public CardException(Throwable cause) {
		super(cause);
	}

}
