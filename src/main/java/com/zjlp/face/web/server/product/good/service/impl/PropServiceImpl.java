package com.zjlp.face.web.server.product.good.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.jredis.annotation.enums.CachedName;
import com.zjlp.face.web.server.product.good.dao.PropDao;
import com.zjlp.face.web.server.product.good.dao.PropValueDao;
import com.zjlp.face.web.server.product.good.domain.Prop;
import com.zjlp.face.web.server.product.good.domain.PropValue;
import com.zjlp.face.web.server.product.good.service.PropService;
@Service
public class PropServiceImpl implements PropService {
	
	@Autowired
	private PropDao propDao;
	
	@Autowired
	private PropValueDao propValueDao;
	

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public Prop getPropById(Long id) {
		return propDao.getById(id);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public PropValue getPropValueById(Long id) {
		return propValueDao.getById(id);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<PropValue> findPropValuesByPropId(final Long propId) {
		return propValueDao.findPropValuesByPropId(propId);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<Prop> findPropsByClassificationId(final Long classificationId) {
		return propDao.findPropsByClassificationId(classificationId);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public Boolean hasSalesProp(final Long classificationId) {
		int count = propDao.hasSalesProp(classificationId);
		return count>0;
	}






}
