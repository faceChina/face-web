package com.zjlp.face.web.server.user.weixin.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ToolSettingMapper;
import com.zjlp.face.web.server.user.weixin.dao.ToolSettingDao;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;

@Repository
public class ToolSettingDaoImpl implements ToolSettingDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public ToolSetting getByShopNo(String shopNo) {
		return sqlSession.getMapper(ToolSettingMapper.class).getByShopNo(shopNo);
	}

	@Override
	public void edit(ToolSetting toolSetting) {
		sqlSession.getMapper(ToolSettingMapper.class).updateByPrimaryKeySelective(toolSetting);
	}

	@Override
	public void addToolSetting(ToolSetting toolSetting){
		sqlSession.getMapper(ToolSettingMapper.class).insertSelective(toolSetting);
	}
}
