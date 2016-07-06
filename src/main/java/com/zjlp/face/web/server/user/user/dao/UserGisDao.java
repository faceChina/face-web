package com.zjlp.face.web.server.user.user.dao;

import java.util.List;

import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

public interface UserGisDao {
	
	void add(UserGis userGis);
	
	void delete(Long id);
	
	void deleteByUserId(Long userId);
	
	void update(UserGis userGis);
	
	UserGis getUserGisByUserId(Long userId);
	
	List<UserGisDto> findUserInNear(UserGisVo userGisVo);
	
	void updateStatus(Long id,Integer status);
	
	List<UserGisDto> findLeiDaUser(UserGisVo userGisVo);
	
	void updateLeiDaGisRadarEnable(UserGis userGis);
	
	UserGis getUserGisById(Long id);
}
