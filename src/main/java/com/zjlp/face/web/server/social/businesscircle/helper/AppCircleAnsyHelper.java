package com.zjlp.face.web.server.social.businesscircle.helper;

import org.apache.log4j.Logger;

public class AppCircleAnsyHelper <T> implements Runnable{
	
	private CircleProcessor<T> circleProcessor;
	
	private T t ;
	
	private String username;

	private Logger _logger  = Logger.getLogger("jredisProducerError");

	public  AppCircleAnsyHelper(CircleProcessor<T> circleProcessor,
			T t,
			String username){
		this.circleProcessor = circleProcessor;
		this.t = t;
		this.username = username;
	}

	@Override
	public void run() {
		try {
			circleProcessor.processor(t, username);
		} catch (Exception e) {
			_logger.error("生意圈消息调度失败!", e);
		}
	}
}
