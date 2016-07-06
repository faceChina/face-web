package com.zjlp.face.web.server.product.good.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.product.good.dao.GoodDao;
import com.zjlp.face.web.server.product.good.dao.GoodSkuDao;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;
import com.zjlp.face.web.server.product.good.service.GoodSkuService;

@Service
public class GoodSkuServiceImpl implements GoodSkuService {
	
	@Autowired
	private GoodSkuDao goodSkuDao;
	@Autowired
	private GoodDao goodDao;
	@Override
	public void add(GoodSku goodSku){
		goodSkuDao.add(goodSku);
	}
	
	@Override
	public void edit(GoodSku goodSku){
		goodSkuDao.edit(goodSku);
	}
	
	@Override
	public GoodSku getById(Long id){
		GoodSku goodSku=goodSkuDao.getById(id);
		if(null!=goodSku&&StringUtils.isBlank(goodSku.getPicturePath())){
			Good good=goodDao.getById(goodSku.getGoodId());
			goodSku.setPicturePath(good.getPicUrl());
		}
		return goodSku;
	}
	
	@Override
	public List<GoodSku> findGoodSkusByGoodId(Long goodId) {
		List<GoodSku> list=goodSkuDao.findGoodSkusByGoodId(goodId);
		for(GoodSku sku:list){
			if(StringUtils.isBlank(sku.getPicturePath())){
				Good good=goodDao.getById(sku.getGoodId());
				sku.setPicturePath(good.getPicUrl());
			}
		}
		return list;
	}
	
	@Override
	public List<GoodSku> findAllGoodSkuByGoodId(Long goodId) {
		List<GoodSku> list=goodSkuDao.findAllGoodSkuByGoodId(goodId);
		for(GoodSku sku:list){
			if(StringUtils.isBlank(sku.getPicturePath())){
				Good good=goodDao.getById(sku.getGoodId());
				sku.setPicturePath(good.getPicUrl());
			}
		}
		return list;
	}

	@Override
	public GoodSku selectGoodskuByGoodIdAndPrprerty(Long goodId, String skuProperties) {
		//字符排序
		skuProperties = GoodSku.sortDbProperties(skuProperties.toString());
		return goodSkuDao.selectGoodskuByGoodIdAndPrprerty(goodId,skuProperties);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<GoodSku> findGoodSkuListByGoodIdAndProerptyIdStr(Long goodId,
			String proerptyIdStr) {
		GoodSku goodSku = new GoodSku();
		goodSku.setGoodId(goodId);
		goodSku.setSkuProperties(proerptyIdStr);
		return goodSkuDao.selectGoodskuByGoodIdAndPrprerty(goodSku);
	}

	@Override
	public List<GoodSkuVo> findGoodSkuByAppointmentIdAndShopTypeId(Long appointmentId, Long shopTypeId) {
		return goodSkuDao.findGoodSkuByAppointmentIdAndShopTypeId(appointmentId,shopTypeId);
	}

	@Override
	public List<GoodSku> findGoodSkuImgListByGoodId(Long goodId) {
		return goodSkuDao.findGoodSkusByGoodId(goodId);
	}




}
