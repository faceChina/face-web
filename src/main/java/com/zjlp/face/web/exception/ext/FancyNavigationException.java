package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

/**
* 
* @author Baojiang Yang
* @date 2015年8月24日 下午5:40:17
*
*/ 
public class FancyNavigationException extends BaseException{

	private static final long serialVersionUID = 3734158434680516857L;

	public FancyNavigationException() {
		super();
	}

	public FancyNavigationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FancyNavigationException(String message) {
		super(message);
	}

	public FancyNavigationException(Throwable cause) {
		super(cause);
	}
}
