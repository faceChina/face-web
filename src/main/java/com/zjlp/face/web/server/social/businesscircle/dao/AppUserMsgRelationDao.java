package com.zjlp.face.web.server.social.businesscircle.dao;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.AppUserMsgRelation;

public interface AppUserMsgRelationDao {
	
	Long add(AppUserMsgRelation appUserMsgRelation);
	
	void delete(Long id);
	
	void deleteByUserId(Long userId);
	
	void deleteByCircleMsgId(Long circleMsgId);
	
	List<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,Long startId,int resultNumber);
	

}
