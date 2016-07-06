package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年9月7日 下午7:44:08
 *
 */
public class DeviceInfoException extends BaseException {

	private static final long serialVersionUID = 1L;

	public DeviceInfoException() {
		super();
	}

	public DeviceInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceInfoException(String message) {
		super(message);
	}

	public DeviceInfoException(Throwable cause) {
		super(cause);
	}

}
