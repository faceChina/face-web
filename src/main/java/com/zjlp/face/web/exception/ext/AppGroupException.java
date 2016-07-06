package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

public class AppGroupException extends BaseException {

	private static final long serialVersionUID = -4878766143381669697L;

	public AppGroupException() {
		super();
	}

	public AppGroupException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppGroupException(String message) {
		super(message);
	}

	public AppGroupException(Throwable cause) {
		super(cause);
	}

}
