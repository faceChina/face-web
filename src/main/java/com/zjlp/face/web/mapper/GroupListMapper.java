package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.GroupList;

public interface GroupListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupList record);

    int insertSelective(GroupList record);

    GroupList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupList record);

    int updateByPrimaryKey(GroupList record);
    
    Long selectMaxGroupId();
    
    List<GroupList> selectByGroupId(Long groupId);

	List<GroupList> selectList(GroupList groupList);

	void deleteByGroupId(GroupList groupList);
}