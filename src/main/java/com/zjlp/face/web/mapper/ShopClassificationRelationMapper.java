package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.shop.domain.ShopClassificationRelation;

public interface ShopClassificationRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopClassificationRelation record);

    int insertSelective(ShopClassificationRelation record);

    ShopClassificationRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopClassificationRelation record);

    int updateByPrimaryKey(ShopClassificationRelation record);
}