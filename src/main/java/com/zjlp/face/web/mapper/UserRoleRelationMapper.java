package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.security.domain.UserRoleRelation;
import com.zjlp.face.web.server.user.security.domain.dto.UserRoleRelationDto;

public interface UserRoleRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRelation record);

    int insertSelective(UserRoleRelation record);

    UserRoleRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleRelation record);

    int updateByPrimaryKey(UserRoleRelation record);
    
    
    int countRoleAndRelationByUserIdAndCode(UserRoleRelationDto userRoleRelationDto);
}