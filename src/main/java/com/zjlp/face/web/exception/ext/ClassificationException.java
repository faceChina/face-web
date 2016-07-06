package com.zjlp.face.web.exception.ext;


import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class ClassificationException extends BaseException {

	private static final long serialVersionUID = 1079245930003114452L;

	public ClassificationException() {
		super();
	}

	public ClassificationException(ExceptionObject exObj) {
		super(exObj);
	}

	public ClassificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClassificationException(String message) {
		super(message);
	}

	public ClassificationException(Throwable cause) {
		super(cause);
	}

}
