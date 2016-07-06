package com.zjlp.face.web.server.social.businesscircle.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.social.businesscircle.domain.AppUserMsgRelation;

public interface AppUserMsgRelationService {

	Long add(AppUserMsgRelation appUserMsgRelation);
	
	void delete(Long id);
	
	void deleteByUserId(Long userId);
	
	void deleteByCircleMsgId(Long circleMsgId);
	
	List<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,Long startId, int resultNumber);
	
	Pagination<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,Pagination<AppUserMsgRelation> pagination);
}
