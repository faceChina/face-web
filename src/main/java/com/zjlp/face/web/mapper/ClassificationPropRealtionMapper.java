package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.product.good.domain.ClassificationPropRealtion;

public interface ClassificationPropRealtionMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(ClassificationPropRealtion record);

    int insertSelective(ClassificationPropRealtion record);

    ClassificationPropRealtion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassificationPropRealtion record);

    int updateByPrimaryKey(ClassificationPropRealtion record);
}