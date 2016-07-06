package com.zjlp.face.web.server.product.im.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.im.dao.ImUserDao;
import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.service.ImUserService;
@Service
public class ImUserServiceImpl implements ImUserService {
	
	@Autowired
	private ImUserDao imUserDao;

	@Override
	public void addImUser(ImUser imUser) {
		if (null == imUser.getCreateTime()) {
			Date date = new Date();
			imUser.setCreateTime(date);
			imUser.setUpdateTime(date);
		}
		imUserDao.add(imUser);
	}
	
	@Override
	public void editImUser(ImUser imUser) {
		if (null == imUser.getUpdateTime()) {
			imUser.setUpdateTime(new Date());
		}
		imUserDao.edit(imUser);
	}

	@Override
	public ImUser getImUserById(Long id) {
		return imUserDao.getById(id);
	}

	@Override
	public ImUser getImUserByRemoteId(String remoteId, Integer type) {
		return imUserDao.getByRemoteId(remoteId, type);
	}

	@Override
	public ImUser getImUserByUserName(String userName) {
		return imUserDao.getByUserName(userName);
	}

	@Override
	public ImUser getImUserByUserName(String userName, Integer type) {
		return null;
	}

}
