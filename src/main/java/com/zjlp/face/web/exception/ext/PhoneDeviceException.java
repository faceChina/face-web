package com.zjlp.face.web.exception.ext;

public class PhoneDeviceException extends RuntimeException {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -2710402370043835657L;

	public PhoneDeviceException() {
		super();
	}

	public PhoneDeviceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PhoneDeviceException(String message) {
		super(message);
	}

	public PhoneDeviceException(Throwable cause) {
		super(cause);
	}
}
