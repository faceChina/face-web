package com.zjlp.face.web.server.product.good.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.good.business.GoodShopTypeRelationBusiness;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.service.GoodShopTypeRelationService;
@Service
public class GoodShopTypeRelationBusinessImpl implements GoodShopTypeRelationBusiness {

	@Autowired
	private GoodShopTypeRelationService goodShopTypeRelationService;
	
	@Override
	public void add(GoodShopTypeRelation goodShopTypeRelation) {
		goodShopTypeRelationService.add(goodShopTypeRelation);
	}

	@Override
	public void edit(GoodShopTypeRelation goodShopTypeRelation) {
		goodShopTypeRelationService.edit(goodShopTypeRelation);
	}

	@Override
	public GoodShopTypeRelation getById(Long id) {
		return goodShopTypeRelationService.getById(id);
	}

	@Override
	public void delete(Long id) {
		goodShopTypeRelationService.delete(id);
	}

	@Override
	public List<GoodShopTypeRelation> findGoodShopTypeRelationByGoodId(
			Long goodId) {
		return goodShopTypeRelationService.findGoodShopTypeRelationByGoodId(goodId);
	}

	@Override
	public List<GoodShopTypeRelation> findGoodShopTypeRelationByShopTypeId(
			Long shopTypeId) {
		return goodShopTypeRelationService.findGoodShopTypeRelationByShopTypeId(shopTypeId);
	}

	@Override
	public void deleteAllGoodShopTypeByGoodId(Long goodId) {
		goodShopTypeRelationService.deleteAllGoodShopTypeByGoodId(goodId);
	}
	
	@Override
	public void deleteAllGoodShopTypeByShopTypeId(Long shopTypeId){
		goodShopTypeRelationService.deleteAllGoodShopTypeByShopTypeId(shopTypeId);
	}

}
