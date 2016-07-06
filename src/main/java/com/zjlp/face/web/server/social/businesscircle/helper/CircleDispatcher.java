package com.zjlp.face.web.server.social.businesscircle.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.helper.log.CircleLog;

@Component
public class CircleDispatcher {
	private Logger _logger  = Logger.getLogger("jredisProducerError");

	@Autowired
	private CircleProcessor circleProcessor;
	@Autowired
	private CircleProcessor circleCommentProcessor;
	@Autowired
	private CircleProcessor circlePraiseProcessor;
	
	
	public void dispatcherCircle(AppCircleMsg appCircle,String username) {

		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + appCircle.getId() + "=========="
					+ Thread.currentThread().getName());
			executor = Executors.newSingleThreadExecutor();
			// 开启线程处理
			executor.execute(new AppCircleAnsyHelper(circleProcessor, appCircle, username));
			_logger.info(CircleLog.getString("CIRCLEMSG.Admin.End", appCircle.getUserId().toString(), appCircle.getId().toString()));
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}
	
	public void dispatcherCircleCommentMQ(Long Id) {

		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + Id + "=========="
					+ Thread.currentThread().getName());
			executor = Executors.newSingleThreadExecutor();
			// 开启线程处理
			executor.execute(new AppCircleAnsyHelper(circleCommentProcessor, Id, ""));
			_logger.info(CircleLog.getString("CIRCLEMSG.Admin.End", Id.toString(), Id.toString()));
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}
	public void dispatcherCirclePraiseMQ(Long Id) {
		
		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + Id + "=========="
					+ Thread.currentThread().getName());
			executor = Executors.newSingleThreadExecutor();
			// 开启线程处理
			executor.execute(new AppCircleAnsyHelper(circlePraiseProcessor, Id, ""));
			_logger.info(CircleLog.getString("CIRCLEMSG.Admin.End", Id.toString(), Id.toString()));
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}
}
