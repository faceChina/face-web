package com.zjlp.face.web.server.user.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.jredis.annotation.enums.CachedName;
import com.zjlp.face.web.mapper.VAreaMapper;
import com.zjlp.face.web.server.user.user.dao.VaearDao;
import com.zjlp.face.web.server.user.user.domain.VArea;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

@Repository("vaearDao")
public class VaearDaoImpl implements VaearDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public VaearDto getAreaByAreaCode(Integer code) {
		return sqlSession.getMapper(VAreaMapper.class).selectOneAreaByCode(code);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<VArea> findAllByParentId(String parentId) {
		VArea area = new VArea();
		area.setParentId(parentId);
		return sqlSession.getMapper(VAreaMapper.class).selectAllByParentId(area);
	}

	@Override
	public VArea getAreaByAreaName(String areaName) {
		return sqlSession.getMapper(VAreaMapper.class).getAreaByAreaName(areaName);
	}

	@Override
	public VaearDto getCityByAreaCode(Integer areaCode) {
		return sqlSession.getMapper(VAreaMapper.class).selectOneCityByCode(areaCode);
	}

}
