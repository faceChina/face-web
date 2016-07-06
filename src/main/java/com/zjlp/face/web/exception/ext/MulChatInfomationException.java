package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

/**
 * @author Baojiang Yang
 *
 */
public class MulChatInfomationException extends BaseException {

	private static final long serialVersionUID = 4750018189385837482L;

	public MulChatInfomationException() {
		super();
	}

	public MulChatInfomationException(String message, Throwable cause) {
		super(message, cause);
	}

	public MulChatInfomationException(String message) {
		super(message);
	}

	public MulChatInfomationException(Throwable cause) {
		super(cause);
	}
}
