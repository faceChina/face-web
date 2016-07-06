package com.zjlp.face.web.server.product.im.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ImFriendsMapper;
import com.zjlp.face.web.server.product.im.dao.ImFriendsDao;
import com.zjlp.face.web.server.product.im.domain.ImFriends;
import com.zjlp.face.web.server.product.im.domain.dto.ImFriendsDto;

@Repository
public class ImFriendsDaoImpl implements ImFriendsDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(ImFriends imFriends) {
		sqlSession.getMapper(ImFriendsMapper.class).insertSelective(imFriends);
	}

	@Override
	public void edit(ImFriends imFriends) {
		sqlSession.getMapper(ImFriendsMapper.class).updateByPrimaryKeySelective(imFriends);
	}

	@Override
	public void deleteById(Long id) {
		sqlSession.getMapper(ImFriendsMapper.class).deleteByPrimaryKey(id);
		
	}

	@Override
	public void deleteByUserId(Long userId) {
		sqlSession.getMapper(ImFriendsMapper.class).deleteByUserId(userId);
	}

	@Override
	public ImFriends getById(Long id) {
		return sqlSession.getMapper(ImFriendsMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<ImFriends> findFriendsListByUserId(Long userId) {
		return sqlSession.getMapper(ImFriendsMapper.class).selectListByUserId(userId);
	}

	@Override
	public List<ImFriendsDto> findFriendsDtoListByUserId(Long imUserId) {
		return sqlSession.getMapper(ImFriendsMapper.class).selectListDtoByUserId(imUserId);
	}

	@Override
	public Integer getCountByFriends(ImFriends imFriends) {
		return sqlSession.getMapper(ImFriendsMapper.class).selectCountByFriends(imFriends);
	}

	@Override
	public Integer countIsFriend(String loginAccount, String toFindLoginAccount) {
		return sqlSession.getMapper(ImFriendsMapper.class).countIsFriend(loginAccount, toFindLoginAccount);
	}
	
	

}
