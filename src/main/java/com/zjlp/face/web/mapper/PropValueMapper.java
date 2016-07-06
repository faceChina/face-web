package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.PropValue;

public interface PropValueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PropValue record);

    int insertSelective(PropValue record);

    PropValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PropValue record);

    int updateByPrimaryKey(PropValue record);

	List<PropValue> selectPropValuesByPropId(Long propId);
}