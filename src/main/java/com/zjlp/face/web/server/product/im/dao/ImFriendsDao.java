package com.zjlp.face.web.server.product.im.dao;

import java.util.List;

import com.zjlp.face.web.server.product.im.domain.ImFriends;
import com.zjlp.face.web.server.product.im.domain.dto.ImFriendsDto;

public interface ImFriendsDao {
	
	void add(ImFriends imFriends);
	
	void edit(ImFriends imFriends);
	
	void deleteById(Long id);
	
	void deleteByUserId(Long userId);
	
	ImFriends getById(Long id);

	List<ImFriends> findFriendsListByUserId(Long imUserId);
	
	List<ImFriendsDto> findFriendsDtoListByUserId(Long imUserId);
	
	Integer getCountByFriends(ImFriends imFriends);

	Integer countIsFriend(String loginAccount, String toFindLoginAccount);
}
