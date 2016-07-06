package com.zjlp.face.web.server.product.good.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.good.dao.GoodShopTypeRelationDao;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.service.GoodShopTypeRelationService;
@Service
public class GoodShopTypeRelationServiceImpl implements GoodShopTypeRelationService {

	@Autowired
	private GoodShopTypeRelationDao goodShopTypeRelationDao;
	
	@Override
	public void add(GoodShopTypeRelation goodShopTypeRelation) {
		goodShopTypeRelationDao.add(goodShopTypeRelation);
	}

	@Override
	public void edit(GoodShopTypeRelation goodShopTypeRelation) {
		goodShopTypeRelationDao.edit(goodShopTypeRelation);
	}

	@Override
	public GoodShopTypeRelation getById(Long id) {
		return goodShopTypeRelationDao.getById(id);
	}

	@Override
	public void delete(Long id) {
		goodShopTypeRelationDao.delete(id);
	}

	@Override
	public List<GoodShopTypeRelation> findGoodShopTypeRelationByGoodId(
			Long goodId) {
		return goodShopTypeRelationDao.findGoodShopTypeRelationByGoodId(goodId);
	}

	@Override
	public List<GoodShopTypeRelation> findGoodShopTypeRelationByShopTypeId(
			Long shopTypeId) {
		return goodShopTypeRelationDao.findGoodShopTypeRelationByShopTypeId(shopTypeId);
	}

	@Override
	public void deleteAllGoodShopTypeByGoodId(Long goodId) {
		goodShopTypeRelationDao.deleteAllGoodShopTypeByGoodId(goodId);
	}
	
	@Override
	public void deleteAllGoodShopTypeByShopTypeId(Long shopTypeId){
		goodShopTypeRelationDao.deleteAllGoodShopTypeByShopTypeId(shopTypeId);
	}

}
