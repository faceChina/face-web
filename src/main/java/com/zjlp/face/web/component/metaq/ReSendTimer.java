package com.zjlp.face.web.component.metaq;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.zjlp.face.web.component.metaq.log.MetaLog;

/**
 * 发送重试
 * 
 * @ClassName: ReSendTimer
 * @Description: (这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2015年1月7日 下午3:24:33
 */
public class ReSendTimer extends Timer {

	private Logger _logger = Logger.getLogger(this.getClass());

	private Logger _errorLogger = Logger.getLogger("metaqProducerError");

	private static long period = 3 * 1000;

	private Timer timer;

	private String topic;

	private String message;

	private MetaQProviderClinet metaQProviderClinet;

	public ReSendTimer(String topic, String message, MetaQProviderClinet metaQProviderClinet) {
		timer = this;
		this.topic = topic;
		this.message = message;
		this.metaQProviderClinet = metaQProviderClinet;

	}

	public void schedule() {
		_logger.info(MetaLog.getString("ReSendTimer.schedule", topic, message));
		try {
			super.schedule(new ReSendTimerTask(), 0, period);
		} catch (Exception e) {
			_errorLogger.error("{\"info\":\"" + e + "\",\"topic\":" + topic + "\",\"message\":"
					+ message + "}");
		}
	}

	class ReSendTimerTask extends TimerTask {
		int count = 3;

		@Override
		public void run() {
			if (count > 0) {
				_logger.info(MetaLog.getString("ReSendTimerTask.run", topic));
				try {
					boolean isSuccess = metaQProviderClinet.send(topic, message);
					if (isSuccess) {
						timer.cancel();
						_logger.info(MetaLog.getString("ReSendTimerTask.cancel", topic));
						return;
					}
				} catch (Exception e) {
					_logger.error(e.getMessage(), e);
				}
				count--;
			} else {
				timer.cancel();
				_logger.info(MetaLog.getString("ReSendTimerTask.cancel", topic));
			}
		}
	}
}
