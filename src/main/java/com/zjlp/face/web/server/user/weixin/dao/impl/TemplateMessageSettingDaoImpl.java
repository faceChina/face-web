package com.zjlp.face.web.server.user.weixin.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.TemplateMessageSettingMapper;
import com.zjlp.face.web.server.user.weixin.dao.TemplateMessageSettingDao;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;

@Repository
public class TemplateMessageSettingDaoImpl implements TemplateMessageSettingDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addTemplateMessageSetting(
			TemplateMessageSetting templateMessageSetting) {
		sqlSession.getMapper(TemplateMessageSettingMapper.class).
		insertSelective(templateMessageSetting);

	}

	@Override
	public void editTemplateMessageSetting(
			TemplateMessageSetting templateMessageSetting) {
		sqlSession.getMapper(TemplateMessageSettingMapper.class).
		updateByPrimaryKeySelective(templateMessageSetting);
	}

	@Override
	public TemplateMessageSetting getTemplateMessageSettingByShopNo(
			String shopNo) {
		return sqlSession.getMapper(TemplateMessageSettingMapper.class).
				getTemplateMessageSettingByShopNo(shopNo);
	}

}
