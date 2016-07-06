package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class MemberException extends BaseException {

	private static final long serialVersionUID = -4463860141310029457L;

	public MemberException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberException(ExceptionObject exObj) {
		super(exObj);
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MemberException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
