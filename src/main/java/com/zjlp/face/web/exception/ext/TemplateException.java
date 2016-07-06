package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class TemplateException extends BaseException {

	private static final long serialVersionUID = 7357475941144981171L;

	public TemplateException() {
		super();
	}

	public TemplateException(ExceptionObject exObj) {
		super(exObj);
	}

	public TemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateException(String message) {
		super(message);
	}

	public TemplateException(Throwable cause) {
		super(cause);
	}

}
