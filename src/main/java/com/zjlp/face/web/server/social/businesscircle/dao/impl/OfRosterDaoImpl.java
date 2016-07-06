package com.zjlp.face.web.server.social.businesscircle.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.OfRosterMapper;
import com.zjlp.face.web.server.social.businesscircle.dao.OfRosterDao;
import com.zjlp.face.web.server.social.businesscircle.domain.OfRoster;

@Repository("ofRosterDao")
public class OfRosterDaoImpl implements OfRosterDao{
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<OfRoster> selectRosterByUserName(String userName,String excludeName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("excludeName", excludeName);
		return sqlSession.getMapper(OfRosterMapper.class).selectByUserName(map);
	}

}
