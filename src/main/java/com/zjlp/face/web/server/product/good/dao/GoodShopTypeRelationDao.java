package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;

public interface GoodShopTypeRelationDao {

	void add(GoodShopTypeRelation  GoodShopTypeRelation);
	
	void edit(GoodShopTypeRelation  GoodShopTypeRelation);
	
	GoodShopTypeRelation getById(Long id);
	
	void delete(Long id);
	
	List<GoodShopTypeRelation> findGoodShopTypeRelationByGoodId(Long goodId);
	
	List<GoodShopTypeRelation> findGoodShopTypeRelationByShopTypeId(Long shopTypeId);

	void deleteAllGoodShopTypeByGoodId(Long goodId);
	
	void deleteAllGoodShopTypeByShopTypeId(Long shopTypeId);

}
