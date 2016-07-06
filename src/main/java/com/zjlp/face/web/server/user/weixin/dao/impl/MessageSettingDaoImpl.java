package com.zjlp.face.web.server.user.weixin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MessageSettingMapper;
import com.zjlp.face.web.server.user.weixin.dao.MessageSettingDao;
import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.dto.MessageSettingDto;

@Repository("MessageSettingDao")
public class MessageSettingDaoImpl implements MessageSettingDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(MessageSetting messageSetting) {
		sqlSession.getMapper(MessageSettingMapper.class).insertSelective(messageSetting);
		
	}

	@Override
	public void edit(MessageSetting messageSetting) {
		sqlSession.getMapper(MessageSettingMapper.class).updateMessageSetting(
				messageSetting);

	}

	@Override
	public List<MessageSettingDto> findMessageSettingPageList(
			MessageSettingDto dto, int start, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(MessageSettingMapper.class).selectList(map);
	}

	@Override
	public Integer getCount(MessageSettingDto dto) {
		return sqlSession.getMapper(MessageSettingMapper.class).getCount(dto);
	}

	@Override
	public MessageSetting getById(Long id) {
		return sqlSession.getMapper(MessageSettingMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void removeMessageSetting(MessageSetting messageSetting) {
		sqlSession.getMapper(MessageSettingMapper.class).updateByPrimaryKeySelective(messageSetting);
	}

	@Override
	public MessageSetting getMessageSetting(MessageSetting messageSetting) {
		return sqlSession.getMapper(MessageSettingMapper.class).getMessageSetting(messageSetting);
	}

	@Override
	public void editMessageSettingStatus(MessageSetting messageSetting) {
		sqlSession.getMapper(MessageSettingMapper.class).editMessageSettingStatus(messageSetting);
	}

}
