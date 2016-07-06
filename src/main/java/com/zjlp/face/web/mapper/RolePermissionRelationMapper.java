package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.security.domain.RolePermissionRelation;

public interface RolePermissionRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionRelation record);

    int insertSelective(RolePermissionRelation record);

    RolePermissionRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermissionRelation record);

    int updateByPrimaryKey(RolePermissionRelation record);
    
    
    List<String> findAllPermissionByUserId(Long userId);
}	