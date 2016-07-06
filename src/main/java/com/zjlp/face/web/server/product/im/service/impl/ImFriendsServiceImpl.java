package com.zjlp.face.web.server.product.im.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.im.dao.ImFriendsDao;
import com.zjlp.face.web.server.product.im.domain.ImFriends;
import com.zjlp.face.web.server.product.im.domain.dto.ImFriendsDto;
import com.zjlp.face.web.server.product.im.service.ImFriendsService;
@Service
public class ImFriendsServiceImpl implements ImFriendsService {
	
	@Autowired
	private ImFriendsDao imFriendsDao;

	@Override
	public void addImFriends(ImFriends imFriends) {
		if (null == imFriends.getCreateTime()) {
			Date date = new Date();
			imFriends.setCreateTime(date);
			imFriends.setUpdateTime(date);
		}
		imFriendsDao.add(imFriends);

	}

	@Override
	public void editImFriends(ImFriends imFriends) {
		if (null == imFriends.getUpdateTime()) {
			imFriends.setUpdateTime(new Date());
		}
		imFriendsDao.edit(imFriends);
	}

	@Override
	public void deleteFriends(Long id) {
		imFriendsDao.deleteById(id);
	}

	@Override
	public List<ImFriends> findFriendsListByUserId(Long imUserId) {
		return imFriendsDao.findFriendsListByUserId(imUserId);
	}

	@Override
	public List<ImFriendsDto> findFriendsDtoListByUserId(Long imUserId) {
		return imFriendsDao.findFriendsDtoListByUserId(imUserId);
	}

	@Override
	public Integer getCountByFriends(ImFriends imFriends) {
		return imFriendsDao.getCountByFriends(imFriends);
	}

	@Override
	public Integer countIsFriend(String loginAccount, String toFindLoginAccount) {
		return imFriendsDao.countIsFriend(loginAccount, toFindLoginAccount);
	}
	
	

}
