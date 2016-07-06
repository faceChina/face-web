package com.zjlp.face.web.server.social.businesscircle.dao;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.OfRoster;


public interface OfRosterDao {
	
	List<OfRoster> selectRosterByUserName(String userName,String excludeName);

}
