package com.zjlp.face.web.server.product.im.producer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.server.product.im.domain.ImFriends;
import com.zjlp.face.web.server.product.im.domain.dto.ImFriendsDto;
import com.zjlp.face.web.server.product.im.producer.ImFriendsProducer;
import com.zjlp.face.web.server.product.im.service.ImFriendsService;

@Repository("imFriendsProducer")
public class ImFriendsProducerImpl implements ImFriendsProducer {
	
	@Autowired
	private ImFriendsService imFriendsService;

	@Override
	public void addImFriends(ImFriends imFriends) {
		imFriendsService.addImFriends(imFriends);
	}

	@Override
	public void editImFriends(ImFriends imFriends) {
		imFriendsService.editImFriends(imFriends);

	}

	@Override
	public void deleteFriends(Long id) {
		imFriendsService.deleteFriends(id);
	}

	@Override
	public List<ImFriends> findFriendsListByUserId(Long imUserId) {
		return imFriendsService.findFriendsListByUserId(imUserId);
	}

	@Override
	public List<ImFriendsDto> findFriendsDtoListByUserId(Long imUserId) {
		return imFriendsService.findFriendsDtoListByUserId(imUserId);
	}

	@Override
	public Integer getCountByFriends(ImFriends imFriends) {
		return imFriendsService.getCountByFriends(imFriends);
	}

	@Override
	public Boolean isFriend(String loginAccount, String toFindLoginAccount) {
		Integer count = imFriendsService.countIsFriend(loginAccount, toFindLoginAccount);
		return count > 0;
	}
	
	

}
