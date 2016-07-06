package com.zjlp.face.web.server.user.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.user.dao.UserGisDao;
import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;
import com.zjlp.face.web.server.user.user.service.UserGisService;

@Service("userGisService")
public class UserGisServiceImpl implements UserGisService {
	
	@Autowired
	private UserGisDao userGisDao;

	@Override
	public Long add(UserGisVo userGisVo) {
		UserGis userGis = new UserGis();
		Date  date = new Date();
		userGis.setUserId(userGisVo.getUserId());
		userGis.setLatitude(userGisVo.getLatitude());
		userGis.setLongitude(userGisVo.getLongitude());
		userGis.setStatus(1);
		userGis.setCreateTime(date);
		userGis.setUpdateTime(date);
		userGisDao.add(userGis);
		return userGis.getId();
	}

	@Override
	public void edit(UserGis userGis) {
		userGisDao.update(userGis);
	}

	@Override
	public void deleteById(Long id) {
		userGisDao.delete(id);
	}

	@Override
	public UserGis getUserGisByUserId(Long userId) {
		return userGisDao.getUserGisByUserId(userId);
	}
	
	@Override
	public List<UserGisDto> findUserInNear(UserGisVo userGisVo) {
		return userGisDao.findUserInNear(userGisVo);
	}
	
	@Override
	public boolean updateStatus(Long id, Integer status) {
		 userGisDao.updateStatus(id, status);
		 return true;
	}

	@Override
	public List<UserGisDto> findLeiDaUser(UserGisVo userGisVo) {
		return userGisDao.findLeiDaUser(userGisVo);
	}
	
	@Override
	public boolean updateLeiDaStatusRadarEnable(UserGis userGis) {
		 userGisDao.updateLeiDaGisRadarEnable(userGis);
		 return true;
	}

	@Override
	public UserGis getUserGisById(Long id) {
		return userGisDao.getUserGisById(id);
	}
}
