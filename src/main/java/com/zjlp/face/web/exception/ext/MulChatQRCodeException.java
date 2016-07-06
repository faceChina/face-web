package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

/**
 * @author Baojiang Yang
 *
 */
public class MulChatQRCodeException extends BaseException {

	private static final long serialVersionUID = 4750018189385837482L;

	public MulChatQRCodeException() {
		super();
	}

	public MulChatQRCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MulChatQRCodeException(String message) {
		super(message);
	}

	public MulChatQRCodeException(Throwable cause) {
		super(cause);
	}

}
