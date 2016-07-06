package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.social.businesscircle.domain.OfRoster;


public interface OfRosterMapper {
	
	List<OfRoster> selectByUserName(Map<String,Object> map);

}
