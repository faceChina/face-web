package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;

public interface GoodShopTypeRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodShopTypeRelation record);

    int insertSelective(GoodShopTypeRelation record);

    GoodShopTypeRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodShopTypeRelation record);

    int updateByPrimaryKey(GoodShopTypeRelation record);

	List<GoodShopTypeRelation> selectByGoodId(Long goodId);

	List<GoodShopTypeRelation> selectByShopTypeId(Long shopTypeId);

	void deleteAllByGoodId(Long goodId);
	
	void deleteAllByShopTypeId(Long shopTypeId);

}