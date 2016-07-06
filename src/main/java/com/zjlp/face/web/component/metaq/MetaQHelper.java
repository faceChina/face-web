package com.zjlp.face.web.component.metaq;

import org.apache.log4j.Logger;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.zjlp.face.web.component.metaq.log.MetaLog;

@Deprecated
public class MetaQHelper {

	private Logger _logger = Logger.getLogger(this.getClass());

	private MetaQProviderClinet metaQProviderClinet;

	public void send(String topic, String message) {
		try {
			boolean isSuccess = metaQProviderClinet.send(topic, message);
			if (!isSuccess) {
				throw new Exception(MetaLog.getString("Producer.Sender.exception", topic, message));
			}
			_logger.info(MetaLog.getString("Producer.Sender.info", topic, message));
		} catch (Exception e) {
			new ReSendTimer(topic, message, metaQProviderClinet);
		}
	}

	public void send(MetaQProviderClinet provider, String topic, String message) {
		try {
			boolean isSuccess = provider.send(topic, message);
			if (!isSuccess) {
				throw new Exception(MetaLog.getString("Producer.Sender.exception", topic, message));
			}
			_logger.info(MetaLog.getString("Producer.Sender.info", topic, message));
		} catch (Exception e) {
			new ReSendTimer(topic, message, provider);
		}
	}
}
