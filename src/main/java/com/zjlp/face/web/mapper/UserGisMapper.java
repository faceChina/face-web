package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;


public interface UserGisMapper {
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByUserId(Long userId);

    int insert(UserGis userGis);
    
    void updateUserGis(UserGis userGis);
    
    UserGis getUserGisByUserId(Long userId);
    
    List<UserGisDto> selectUserGisPage(UserGisVo UserGisVo);
    
    Integer getCount(UserGisVo UserGisVo);
    
    List<UserGisDto> findLeiDaUser(UserGisVo userGisVo);
    
    void updateLeiDaGisRadarEnable(UserGis userGis);
    
    UserGis getUserGisById(Long id);
    
}