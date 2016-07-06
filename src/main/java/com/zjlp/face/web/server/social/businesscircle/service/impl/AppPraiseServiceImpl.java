package com.zjlp.face.web.server.social.businesscircle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.social.businesscircle.dao.AppPraiseDao;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;
import com.zjlp.face.web.server.social.businesscircle.service.AppPraiseService;

@Service("appPraiseService")
public class AppPraiseServiceImpl implements AppPraiseService {
	
	@Autowired
	private AppPraiseDao appPraiseDao;

	@Override
	public Long add(AppPraise appPraise) {
		return appPraiseDao.add(appPraise);
	}

	@Override
	public void deleteByCircleMsgId(Long circleMsgId) {
		appPraiseDao.deleteByCircleMsgId(circleMsgId);
	}

	@Override
	public List<AppPraise> queryAppPraise(Long circleMsgId) {
		return appPraiseDao.queryAppPraise(circleMsgId);
	}

	@Override
	public AppPraise getAppPraise(Long id) {
		return appPraiseDao.getAppPraise(id);
	}

	@Override
	public int cancelPraise(Long id, Long userId) {
		AppPraise praise = appPraiseDao.getAppPraise(id);
		if (null == praise || !userId.equals(praise.getUserId())) {
			return 0;
		}
		return appPraiseDao.delete(id);
	}
}
