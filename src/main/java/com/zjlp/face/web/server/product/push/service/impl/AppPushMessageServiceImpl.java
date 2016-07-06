package com.zjlp.face.web.server.product.push.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.push.dao.AppPushMessageDao;
import com.zjlp.face.web.server.product.push.domain.AppPushMessage;
import com.zjlp.face.web.server.product.push.service.AppPushMessageService;

@Service
public class AppPushMessageServiceImpl implements AppPushMessageService {

	@Autowired
	private AppPushMessageDao appPushMessageDao;

	@Override
	public int addAppPushMessage(AppPushMessage appPushMessage) {
		return appPushMessageDao.add(appPushMessage);
	}

	@Override
	public List<AppPushMessage> findAppPushMessageByUserId(AppPushMessage appPushMessage) {
		return appPushMessageDao.findByUserId(appPushMessage);
	}

	@Override
	public void updateAppPushMessage(AppPushMessage appPushMessage) {
		appPushMessageDao.updateAppPushMessage(appPushMessage);
	}

	@Override
	public void removeAppPushMessage(Long id) {
		appPushMessageDao.removeAppPushMessage(id);
	}

	@Override
	public AppPushMessage findReadMsgById(AppPushMessage appPushMessage) {
		return appPushMessageDao.findReadMsgById(appPushMessage);

	}

	@Override
	public List<AppPushMessage> findAppPushMessage(AppPushMessage appPushMessage) {
		return appPushMessageDao.findAppPushMessage(appPushMessage);
	}

	@Override
	public void deleteMsgMonthAgo(Long userId, Date createTime) {
		this.appPushMessageDao.deleteMsgMonthAgo(userId, createTime);
	}

}
