package com.zjlp.face.web.server.social.businesscircle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.social.businesscircle.dao.AppCommentDao;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.service.AppCommentService;

@Service("appCommentService")
public class AppCommentServiceImpl implements AppCommentService {
	
	@Autowired
	private AppCommentDao appCommentDao;

	@Override
	public Long add(AppComment appComment) {
		return appCommentDao.add(appComment);
	}

	@Override
	public int delete(Long id,Long senderId) {
		return appCommentDao.delete(id,senderId);
	}

	@Override
	public void deleteByCircleMsgId(Long circleMsgId) {
		appCommentDao.deleteByCircleMsgId(circleMsgId);
	}

	@Override
	public List<AppComment> queryAppComment(Long circleMsgId) {
		return appCommentDao.queryAppComment(circleMsgId);
	}

	@Override
	public AppComment getAppComment(Long id) {
		return appCommentDao.getAppComment(id);
	}

}
