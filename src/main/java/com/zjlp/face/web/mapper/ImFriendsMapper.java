package com.zjlp.face.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.product.im.domain.ImFriends;
import com.zjlp.face.web.server.product.im.domain.dto.ImFriendsDto;

public interface ImFriendsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImFriends record);

    int insertSelective(ImFriends record);

    ImFriends selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImFriends record);

    int updateByPrimaryKey(ImFriends record);

	void deleteByUserId(Long userId);

	List<ImFriends> selectListByUserId(Long userId);
	
	List<ImFriendsDto> selectListDtoByUserId(Long userId);
	
	Integer selectCountByFriends(ImFriends record);

	Integer countIsFriend(@Param("loginAccount")String loginAccount, @Param("toFindLoginAccount")String toFindLoginAccount);
}