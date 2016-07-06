package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class GroupListException extends BaseException {

	private static final long serialVersionUID = -2104720671780453691L;

	public GroupListException() {
		super();
	}

	public GroupListException(ExceptionObject exObj) {
		super(exObj);
	}

	public GroupListException(String message, Throwable cause) {
		super(message, cause);
	}

	public GroupListException(String message) {
		super(message);
	}

	public GroupListException(Throwable cause) {
		super(cause);
	}

}
