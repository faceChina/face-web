package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.user.domain.UserSession;

public interface UserSessionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSession record);

    int insertSelective(UserSession record);

    UserSession selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserSession record);

    int updateByPrimaryKey(UserSession record);
}