package com.zjlp.face.web.server.user.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.user.weixin.dao.ToolSettingDao;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;
import com.zjlp.face.web.server.user.weixin.service.ToolSettingService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ToolSettingServiceImpl implements ToolSettingService {
	
	@Autowired
	private ToolSettingDao toolSettingDao;

	@Override
	public void addToolSetting(ToolSetting toolSetting) {
		toolSettingDao.addToolSetting(toolSetting);
	}

	@Override
	public ToolSetting getToolSettingByShopNo(String shopNo) {
		return toolSettingDao.getByShopNo(shopNo);
	}

}
