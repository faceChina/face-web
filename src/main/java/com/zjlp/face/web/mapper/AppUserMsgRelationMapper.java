package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.social.businesscircle.domain.AppUserMsgRelation;

public interface AppUserMsgRelationMapper {

	int insert(AppUserMsgRelation appUserMsgRelation);
	
	int deleteByPrimaryKey(Long id);
	
	int deleteByUserId(Long userId);
	
	int deleteByCircleMsgId(Long circleMsgId);
	
	List<AppUserMsgRelation> selectAppUserMsgRelation(Map<String,Object> map);
}
