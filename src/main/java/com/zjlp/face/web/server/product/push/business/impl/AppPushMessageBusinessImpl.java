package com.zjlp.face.web.server.product.push.business.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.AppPushMessageException;
import com.zjlp.face.web.server.product.push.business.AppPushMessageBusiness;
import com.zjlp.face.web.server.product.push.domain.AppPushMessage;
import com.zjlp.face.web.server.product.push.service.AppPushMessageService;

/**
 * @author ybj
 *
 */
@Component("appPushMessageBusiness")
public class AppPushMessageBusinessImpl implements AppPushMessageBusiness {

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
			AssertUtil.notNull(appPushMessage.getUserId(), "userId为空，无法查询.");
			Date current = new Date();
			Date zeroPoint = DateUtil.getZeroPoint(current);
			/** 查询最近一周消息 */
			appPushMessage.setCreateTime(DateUtil.addDay(zeroPoint, -7));
			List<AppPushMessage> results = this.appPushMessageService.findAppPushMessageByUserId(appPushMessage);
			/** 删除一个月之前的已读消息 **/
			this.appPushMessageService.deleteMsgMonthAgo(appPushMessage.getUserId(), DateUtil.addMonth(zeroPoint, -1));
			return results;
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

	@Override
	public void removeAppPushMessage(Long id) {
		try {
			AssertUtil.notNull(id, "Long id为空，无法删除.");
			this.appPushMessageService.removeAppPushMessage(id);
		} catch (Exception e) {
			log.info("删除推送信息异常.");
			throw new AppPushMessageException(e);
		}
	}

	@Override
	public AppPushMessage findReadMsgById(AppPushMessage appPushMessage) {
		try {
			AssertUtil.notNull(appPushMessage, "appPushMessage为空，无法查找.");
			AssertUtil.notNull(appPushMessage.getId(), "appPushMessage.getId()为空，无法查找.");
			AssertUtil.notNull(appPushMessage.getIsRead(), "appPushMessage.getIsRead()为空，无法查找.");
			return this.appPushMessageService.findReadMsgById(appPushMessage);
		} catch (Exception e) {
			log.info("查找推送信息异常.");
			throw new AppPushMessageException(e);
		}
	}

	@Override
	public List<AppPushMessage> findAppPushMessage(AppPushMessage appPushMessage) {
		return appPushMessageService.findAppPushMessage(appPushMessage);
	}

}
