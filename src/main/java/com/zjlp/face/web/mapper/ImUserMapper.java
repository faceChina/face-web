package com.zjlp.face.web.mapper;

import java.util.Map;

import com.zjlp.face.web.server.product.im.domain.ImUser;

public interface ImUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImUser record);

    int insertSelective(ImUser record);

    ImUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImUser record);

    int updateByPrimaryKey(ImUser record);

	ImUser selectByRemoteId(Map<String, Object> map);
	
	ImUser selectByUserName(String userName);
	
	ImUser selectByUserNameAndType(Map<String, Object> map);
}