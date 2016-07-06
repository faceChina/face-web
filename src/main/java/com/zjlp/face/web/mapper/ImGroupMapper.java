package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.im.domain.ImGroup;

public interface ImGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImGroup record);

    int insertSelective(ImGroup record);

    ImGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImGroup record);

    int updateByPrimaryKey(ImGroup record);

	void deleteByUserId(Long userId);

	List<ImGroup> selectList(ImGroup imGroup);
}