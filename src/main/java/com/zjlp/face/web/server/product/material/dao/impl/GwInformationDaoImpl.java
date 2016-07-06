package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.GwInformationMapper;
import com.zjlp.face.web.server.product.material.dao.GwInformationDao;
import com.zjlp.face.web.server.product.material.domain.GwInformation;

@Repository
public class GwInformationDaoImpl implements GwInformationDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public GwInformation getByPrimaryKey(Long id) {
		return sqlSession.getMapper(GwInformationMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public int getCount(GwInformation gwInformation) {
		return sqlSession.getMapper(GwInformationMapper.class).getCount(gwInformation);
	}

	@Override
	public List<GwInformation> findGwInformationPageList(Map<String, Object> map) {
		return sqlSession.getMapper(GwInformationMapper.class).findGwInformationPageList(map);
	}
}
