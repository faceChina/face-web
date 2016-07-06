package com.zjlp.face.web.server.user.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.WechatException;
import com.zjlp.face.web.server.user.weixin.dao.TemplateMessageDao;
import com.zjlp.face.web.server.user.weixin.dao.TemplateMessageSettingDao;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessage;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;
import com.zjlp.face.web.server.user.weixin.service.TemplateMessageService;

@Service
public class TemplateMessageServiceImpl implements TemplateMessageService {

	@Autowired
	private TemplateMessageDao templateMessageDao;
	
	@Autowired
	private TemplateMessageSettingDao templateMessageSettingDao;
	
	@Override
	public TemplateMessage getTemplateMessage(TemplateMessage templateMessage) {
		return templateMessageDao.getTemplateMessage(templateMessage);
	}

	@Override
	public void addTemplateMessage(TemplateMessage templateMessage) {
		templateMessageDao.addTemplateMessage(templateMessage);
	}

	@Override
	public void editTemplateMessage(TemplateMessage templateMessage) {
		templateMessageDao.editTemplateMessage(templateMessage);
	}

	@Override
	public void addTemplateMessageSetting(
			TemplateMessageSetting templateMessageSetting) {
		templateMessageSettingDao.addTemplateMessageSetting(templateMessageSetting);
	}

	@Override
	public void editTemplateMessageSetting(
			TemplateMessageSetting templateMessageSetting) {
		templateMessageSettingDao.editTemplateMessageSetting(templateMessageSetting);
	}

	@Override
	public TemplateMessageSetting getTemplateMessageSettingByShopNo(
			String shopNo) {
		return templateMessageSettingDao.getTemplateMessageSettingByShopNo(shopNo);
	}

	@Override
	public Integer getTemplateMessageCountByShopNo(String shopNo) throws WechatException{
		
		try {
			AssertUtil.notNull(shopNo, "店铺编号为空");
			// 查询店铺模板消息个数
			return templateMessageDao.getTemplateMessageCountByShopNo(shopNo);
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

}
