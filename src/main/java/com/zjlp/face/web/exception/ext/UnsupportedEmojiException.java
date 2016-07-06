package com.zjlp.face.web.exception.ext;

import com.zjlp.face.util.exception.BaseException;

/**
 * 不支持的Emoji插入表情异常
 * 
 * @author Baojiang Yang
 * @date 2015年8月29日 上午11:01:41
 *
 */
public class UnsupportedEmojiException extends BaseException {

	private static final long serialVersionUID = 1L;

	public UnsupportedEmojiException() {
		super();
	}

	public UnsupportedEmojiException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedEmojiException(String message) {
		super(message);
	}

	public UnsupportedEmojiException(Throwable cause) {
		super(cause);
	}
}
