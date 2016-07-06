package com.zjlp.face.web.server.product.push.service;

import java.util.Date;
import java.util.List;

import com.zjlp.face.web.server.product.push.domain.AppPushMessage;

public interface AppPushMessageService {

	int addAppPushMessage(AppPushMessage appPushMessage);

	List<AppPushMessage> findAppPushMessageByUserId(AppPushMessage appPushMessage);

	void updateAppPushMessage(AppPushMessage appPushMessage);

	void removeAppPushMessage(Long id);

	AppPushMessage findReadMsgById(AppPushMessage appPushMessage);
	
	List<AppPushMessage> findAppPushMessage(AppPushMessage appPushMessage);
	
	void deleteMsgMonthAgo(Long userId, Date createTime);
}
