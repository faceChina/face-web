package com.zjlp.face.web.server.social.businesscircle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.social.businesscircle.dao.AppCircleMsgDao;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;
import com.zjlp.face.web.server.social.businesscircle.service.AppCircleMsgService;

@Service("appCircleMsgService")
public class AppCircleMsgServiceImpl implements AppCircleMsgService {
	
	@Autowired
	private AppCircleMsgDao appCircleMsgDao;

	@Override
	public Long add(AppCircleMsg appCircle) {
		return appCircleMsgDao.add(appCircle);
	}

	@Override
	public void delete(Long id) {
		appCircleMsgDao.delete(id);
	}

	@Override
	public AppCircleMsgVo getAppCircleById(Long id) {
		return appCircleMsgDao.getAppCircleById(id);
	}

	@Override
	public Pagination<AppCircleMsgVo> findMyAppCircleMsgVo(Long userId,Pagination<AppCircleMsgVo> pagination) {
		return appCircleMsgDao.findAppCircleMsgVoByUserId(userId,pagination);
	}

	@Override
	public List<AppCircleMsgVo> queryAppCircleMsgVo(Long userId) {
		return null;
	}

	@Override
	public Pagination<AppCircleMsgVo> queryAppCircleMsgVo(String username,Long upMsgId,Long downMsgId,
			Pagination<AppCircleMsgVo> pagination) {
		return appCircleMsgDao.queryAppCircleMsgVo(username, upMsgId,downMsgId, pagination);
	}

	@Override
	public void edit(AppCircleMsg appCircle) {
		appCircleMsgDao.edit(appCircle);
	}

}
