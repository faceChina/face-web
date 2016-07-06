package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;


/**
 * 文章异常类
 * @ClassName: NewsException 
 * @Description: (文章异常类) 
 * @author ah
 * @date 2014年7月29日 上午11:23:03
 */
public class NewsException extends BaseException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -474497052061862258L;

	public NewsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewsException(ExceptionObject exObj) {
		super(exObj);
		// TODO Auto-generated constructor stub
	}

	public NewsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NewsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NewsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


}
