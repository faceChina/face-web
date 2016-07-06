package com.zjlp.face.web.server.social.businesscircle.helper.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.web.component.metaq.producer.CircleMetaOperateProducer;
import com.zjlp.face.web.server.social.businesscircle.helper.CircleProcessor;

@Component("circleCommentProcessor")
public class CircleCommentProcessorImpl implements CircleProcessor<Long> {
	
	private Logger _logger  = Logger.getLogger(CircleCommentProcessorImpl.class);
	
	@Autowired
	private CircleMetaOperateProducer circleMetaOperateProducer;

	@Override
	public void processor(Long t, String username) {
		try {
			circleMetaOperateProducer.senderCommentAnsy(t);
		} catch (Exception e) {
			_logger.error("发布生意圈消息MetaQ推送异常！", e);
		}
	}

}
