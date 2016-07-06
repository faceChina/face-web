package com.zjlp.face.web.server.user.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.UserGisMapper;
import com.zjlp.face.web.server.user.user.dao.UserGisDao;
import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

@Repository("userGisDao")
public class UserGisDaoImpl implements UserGisDao {
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public void add(UserGis userGis) {
		sqlSession.getMapper(UserGisMapper.class).insert(userGis);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(UserGisMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByUserId(Long userId) {
		sqlSession.getMapper(UserGisMapper.class).deleteByUserId(userId);
	}

	@Override
	public void update(UserGis userGis) {
		sqlSession.getMapper(UserGisMapper.class).updateUserGis(userGis);
	}

	@Override
	public UserGis getUserGisByUserId(Long userId) {
		return sqlSession.getMapper(UserGisMapper.class).getUserGisByUserId(userId);
	}

	@Override
	public List<UserGisDto> findUserInNear(UserGisVo userGisVo) {
		List<UserGisDto> list = sqlSession.getMapper(UserGisMapper.class).selectUserGisPage(userGisVo);
		return list;
	}

	@Override
	public void updateStatus(Long id, Integer status) {
		UserGis userGis = new UserGis();
		userGis.setId(id);
		userGis.setStatus(status);
		sqlSession.getMapper(UserGisMapper.class).updateUserGis(userGis);
	}

	@Override
	public List<UserGisDto> findLeiDaUser(UserGisVo userGisVo) {
		return sqlSession.getMapper(UserGisMapper.class).findLeiDaUser(userGisVo);
	}
	
	@Override
	public void updateLeiDaGisRadarEnable(UserGis userGis){
		sqlSession.getMapper(UserGisMapper.class).updateLeiDaGisRadarEnable(userGis);
	}
	
	@Override
	public UserGis getUserGisById(Long id) {
		return sqlSession.getMapper(UserGisMapper.class).getUserGisById(id);
	}
}
