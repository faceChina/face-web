package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

public class AppPushMessageException extends BaseException {

	private static final long serialVersionUID = 1L;

	public AppPushMessageException() {
		super();
	}

	public AppPushMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppPushMessageException(String message) {
		super(message);
	}

	public AppPushMessageException(Throwable cause) {
		super(cause);
	}
}
