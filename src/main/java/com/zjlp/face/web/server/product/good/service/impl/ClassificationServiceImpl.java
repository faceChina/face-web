package com.zjlp.face.web.server.product.good.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.jredis.annotation.enums.CachedName;
import com.zjlp.face.web.server.product.good.dao.ClassificationDao;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.vo.ClassificationVo;
import com.zjlp.face.web.server.product.good.service.ClassificationService;
import com.zjlp.face.web.util.redis.RedisFactory;
import com.zjlp.face.web.util.redis.RedisKey;
@Service
public class ClassificationServiceImpl implements ClassificationService {
	
	@Autowired
	private ClassificationDao classificationDao;

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public Classification getClassificationById(final Long id) {
		return classificationDao.getById(id);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<Classification> findClassificationByPid(final Long pid,final Integer level) {
		return classificationDao.findClassificationByPid(pid, level);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public Boolean hasProtocolClassification(final Long classificationId) {
		Classification classification = classificationDao.getById(classificationId);
		if (2==classification.getCategory().intValue()) {
			return true;
		}
		return false;
	}

	@Override
	public ClassificationVo getLatestClassification(String shopNo) {
		Long classificationId = null;
		if (null != RedisFactory.getWgjStringHelper()) {
			String key = RedisKey.Classification_getLatestClassification_key+shopNo;
			classificationId = RedisFactory.getWgjStringHelper().get(key);
		}
		if (null == classificationId) {
			return null;
		}
		return this.getClassificationVoById(classificationId);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public ClassificationVo getClassificationVoById(Long classificationId) {
		ClassificationVo classificationVo = new ClassificationVo();
		List<String> nameList = new LinkedList<String>();
		
		Classification current = this.getClassificationById(classificationId);
		Classification parent = this.getClassificationById(current.getParentId());
		Classification grandparent = this.getClassificationById(parent.getParentId());
		
		nameList.add(grandparent.getName());
		nameList.add(parent.getName());
		nameList.add(current.getName());
		classificationVo.setNameList(nameList);
		
		classificationVo.setCategory(current.getCategory());
		classificationVo.setId(current.getId());
		classificationVo.setName(current.getName());
		classificationVo.setLeaf(current.getLeaf());
		classificationVo.setLevel(current.getLevel());
		
		//全名
		StringBuilder latestTitleBuilder = new StringBuilder();
		//grandparent.getName() + ">" + parent.getName() + ">" + current.getName();
		latestTitleBuilder.append(grandparent.getName()).append(">")
		.append(parent.getName()).append(">").append(current.getName());
		classificationVo.setAllName(latestTitleBuilder.toString());
		return classificationVo;
	}
}
