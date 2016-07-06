package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 下午4:14:49
 *
 */
public class SubbranchException extends BaseException {

	private static final long serialVersionUID = 7164164051765673861L;

	public SubbranchException() {
		super();
	}

	public SubbranchException(String message, Throwable cause) {
		super(message, cause);
	}

	public SubbranchException(String message) {
		super(message);
	}

	public SubbranchException(Throwable cause) {
		super(cause);
	}
}
