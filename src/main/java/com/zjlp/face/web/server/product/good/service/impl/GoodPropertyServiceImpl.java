package com.zjlp.face.web.server.product.good.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.product.good.dao.GoodPropertyDao;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.service.GoodPropertyService;
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GoodPropertyServiceImpl implements GoodPropertyService {
	
	@Autowired
	private GoodPropertyDao goodPropertyDao;

	@Override
	public List<GoodProperty> findGoodProperties(GoodProperty goodProperty) {
		return goodPropertyDao.findGoodProperties(goodProperty);
	}

	@Override
	public List<GoodProperty> findGoodSalesProperties(
			GoodProperty salesGoodProperty) {
		return goodPropertyDao.findGoodSalesProperties(salesGoodProperty);
	}

	@Override
	public List<GoodProperty> findInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty) {
		return goodPropertyDao.findInputNotKeyGoodProperties(gnotKeyoodProperty);
	}
	
	@Override
	public List<GoodProperty> findSalesInputKeyGoodProperties(
			GoodProperty salesInputProperty) {
		return goodPropertyDao.findSalesInputKeyGoodProperties(salesInputProperty);
	}

	@Override
	public GoodProperty getGoodPropertiesByGoodIdAndPropValueId(Long goodId,
			Long propValueId) {
		return goodPropertyDao.getByGoodIdAndPropValueId(goodId,propValueId);
	}

	@Override
	public GoodProperty getGoodPropertyById(Long id) {
		return goodPropertyDao.getById(id);
	}

	@Override
	public List<GoodProperty> findGoodPropertyListByGoodId(Long goodId) {
		return goodPropertyDao.findGoodPropertyListByGoodId(goodId);
	}


	
	

}
