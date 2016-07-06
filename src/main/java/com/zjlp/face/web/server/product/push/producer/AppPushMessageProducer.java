package com.zjlp.face.web.server.product.push.producer;

import java.util.List;

import com.zjlp.face.web.server.product.push.domain.AppPushMessage;

public interface AppPushMessageProducer {
	/**
	 * 新增一条push消息
	 * 
	 * @param appPushMessage
	 * @return
	 */
	int addAppPushMessage(AppPushMessage appPushMessage);

	/**
	 * 通过用户ID查找推送消息,isRead=1是未读取，0是已读取
	 * 
	 * @param appPushMessage
	 * @return
	 */
	List<AppPushMessage> findAppPushMessageByUserId(AppPushMessage appPushMessage);

	/**
	 * 更新推送消息 ，主要更新是读取状态，即1置0
	 * 
	 * @param appPushMessage
	 */
	void updateAppPushMessage(AppPushMessage appPushMessage);
}
