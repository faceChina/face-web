package com.zjlp.face.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.zjlp.face.util.exception.BaseException;

public class JzHandlerExceptionResolver implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String ERROR_CODE = "errorCode";

	private static final String ERROR_MESSAGE = "message";

	private static final String WAP = "/wap/";
	
	private static final String MESSAGE = "/message/";

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// 日志打印
		logger.info(ex.getMessage(), ex);
		// 跳转异常页面
		String path = request.getRequestURI();
		ErrorPageEnum errPage = path.contains(WAP) ? ErrorPageEnum.ERRORPAGE_APP_ERROR : path.contains(MESSAGE) ? ErrorPageEnum.ERRORPAGE_MESSAGE_ERROR : ErrorPageEnum.ERRORPAGE_ERROR;
		if(path.contains("free")){
			errPage = ErrorPageEnum.ERRORPAGE_FREE_ERROR;
		}
		// 异常处理
		if (ex instanceof BaseException) {
			return handlerException((BaseException) ex, errPage.getErrPageUrl());
		} else {
			return handlerException(ex, errPage.getErrPageUrl());
		}
	}

	/**
	 * 通用异常处理(自定义异常)
	 * 
	 * @Title: handlerException
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param ex
	 * @param url
	 * @return
	 * @date 2015年3月4日 上午9:25:35
	 * @author lys
	 */
	public ModelAndView handlerException(BaseException ex, String url) {
		ex.printStackTrace();
		ModelAndView mav = new ModelAndView(url);
		if (StringUtils.isBlank(ex.getErrCode())) {
			mav.addObject(ERROR_CODE, "SYSTEM_ERROR");
			mav.addObject(ERROR_MESSAGE, "System error!!!");
			return mav;
		}
		// 业务异常
		mav.addObject(ERROR_CODE, ex.getErrCode());
		mav.addObject(ERROR_MESSAGE, ex.getExternalMsg());
		return mav;
	}

	/**
	 * 通用异常处理（非自定义异常）
	 * 
	 * @Title: handlerException
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param ex
	 * @param url
	 * @return
	 * @date 2015年3月4日 上午9:18:12
	 * @author lys
	 */
	public ModelAndView handlerException(Exception ex, String url) {
		ex.printStackTrace();
		ModelAndView mav = new ModelAndView(url);
		mav.addObject(ERROR_CODE, "SYSTEM_ERROR");
		mav.addObject(ERROR_MESSAGE, "System error!!!");
		return mav;
	}

}
