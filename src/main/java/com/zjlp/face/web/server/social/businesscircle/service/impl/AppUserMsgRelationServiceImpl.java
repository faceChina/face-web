package com.zjlp.face.web.server.social.businesscircle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.social.businesscircle.dao.AppUserMsgRelationDao;
import com.zjlp.face.web.server.social.businesscircle.domain.AppUserMsgRelation;
import com.zjlp.face.web.server.social.businesscircle.service.AppUserMsgRelationService;

@Service("appUserMsgRelationService")
public class AppUserMsgRelationServiceImpl implements AppUserMsgRelationService {
	
	@Autowired
	private AppUserMsgRelationDao appUserMsgRelationDao;

	@Override
	public Long add(AppUserMsgRelation appUserMsgRelation) {
		return appUserMsgRelationDao.add(appUserMsgRelation);
	}

	@Override
	public void delete(Long id) {
		appUserMsgRelationDao.delete(id);

	}

	@Override
	public void deleteByUserId(Long userId) {
		appUserMsgRelationDao.deleteByUserId(userId);

	}

	@Override
	public void deleteByCircleMsgId(Long circleMsgId) {
		appUserMsgRelationDao.deleteByCircleMsgId(circleMsgId);

	}

	@Override
	public List<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,Long startId,int resultNumber) {
		return appUserMsgRelationDao.queryAppUserMsgRelation(userId,startId,resultNumber);
	}

	@Override
	public Pagination<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,
			Pagination<AppUserMsgRelation> pagination) {
		return null;
	}
}
