package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.Classification;

public interface ClassificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Classification record);

    int insertSelective(Classification record);

    Classification selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Classification record);

    int updateByPrimaryKey(Classification record);

	List<Classification> selectByPid(Long pid);
}