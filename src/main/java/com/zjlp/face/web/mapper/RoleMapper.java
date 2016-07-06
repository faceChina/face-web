package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.security.domain.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    
    List<Role> findAllRole();
    
    
    Role getRoleByCode(String code);
}