package com.zjlp.face.web.server.product.push.producer.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.AppPushMessageException;
import com.zjlp.face.web.server.product.push.domain.AppPushMessage;
import com.zjlp.face.web.server.product.push.producer.AppPushMessageProducer;
import com.zjlp.face.web.server.product.push.service.AppPushMessageService;

@Repository("appPushMessageProducer")
public class AppPushMessageProducerImpl implements AppPushMessageProducer {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private AppPushMessageService appPushMessageService;

	@Override
	public int addAppPushMessage(AppPushMessage appPushMessage) {
		try {
			AssertUtil.notNull(appPushMessage, "appPushMessage为空，无法添加.");
			AssertUtil.notNull(appPushMessage.getUserId(), "userId为空，无法添加.");
			AssertUtil.notNull(appPushMessage.getIsRead(), "isRead为空，无法添加.");
			AssertUtil.notNull(appPushMessage.getMsgType(), "msgType为空，无法添加.");
			AssertUtil.notNull(appPushMessage.getDeviceType(), "deviceType为空，无法添加.");
			AssertUtil.notNull(appPushMessage.getCreateTime(), "createTime为空，无法添加.");
			AssertUtil.notNull(appPushMessage.getUpdateTime(), "updateTime为空，无法添加.");
			this.appPushMessageService.addAppPushMessage(appPushMessage);
			return 0;
		} catch (Exception e) {
			log.info("保存推送信息异常.");
			throw new AppPushMessageException(e);
		}
	}

	@Override
	public List<AppPushMessage> findAppPushMessageByUserId(AppPushMessage appPushMessage) {
		try {
			AssertUtil.notNull(appPushMessage, "appPushMessage为空，无法查询.");
			AssertUtil.notNull(appPushMessage.getUserId(), "userId为空，无法添加.");
			return this.appPushMessageService.findAppPushMessageByUserId(appPushMessage);
		} catch (Exception e) {
			log.info("查找推送信息异常.");
			throw new AppPushMessageException(e);
		}
	}

	@Override
	public void updateAppPushMessage(AppPushMessage appPushMessage) {
		try {
			AssertUtil.notNull(appPushMessage, "appPushMessage为空，无法更新.");
			this.appPushMessageService.updateAppPushMessage(appPushMessage);
		} catch (Exception e) {
			log.info("更细推送信息异常.");
			throw new AppPushMessageException(e);
		}
	}

}
