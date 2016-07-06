package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.Prop;

public interface PropMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Prop record);

    int insertSelective(Prop record);

    Prop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Prop record);

    int updateByPrimaryKey(Prop record);

	List<Prop> selectPropsByClassificationId(Long classificationId);

	Integer hasSalesProp(Long classificationId);
}