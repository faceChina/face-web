package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年7月15日 下午3:56:32
 *
 */
public class UnregisteredSubUserException extends BaseException {

	private static final long serialVersionUID = 9216331895122624769L;

	public UnregisteredSubUserException() {
		super();
	}

	public UnregisteredSubUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnregisteredSubUserException(String message) {
		super(message);
	}

	public UnregisteredSubUserException(Throwable cause) {
		super(cause);
	}

}
