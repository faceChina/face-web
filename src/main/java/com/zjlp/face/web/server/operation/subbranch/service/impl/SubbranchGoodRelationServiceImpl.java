package com.zjlp.face.web.server.operation.subbranch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.subbranch.dao.SubbranchGoodRelationDao;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.GoodSkuDifferenceVo;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchGoodRelationService;

/**
 * 
 * @author 熊斌
 * 2015年8月19日上午11:39:10
 * @version 1.0
 */
@Service
public class SubbranchGoodRelationServiceImpl implements SubbranchGoodRelationService {

	@Autowired
	private SubbranchGoodRelationDao subbranchGoodRelationDao;

	@Override
	public Long add(SubbranchGoodRelation subbranchGoodRelation) {
		return subbranchGoodRelationDao.add(subbranchGoodRelation);
	}

	@Override
	public SubbranchGoodRelation findByPrimarykey(Long id) {
		return subbranchGoodRelationDao.findByPrimarykey(id);
	}

	@Override
	public void updateByPrimaryKey(SubbranchGoodRelation subbranchGoodRelation) {
		subbranchGoodRelationDao.updateByPrimaryKey(subbranchGoodRelation);
	}

	@Override
	public void deleteByPrimaryKey(Long id) {
		subbranchGoodRelationDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer getTotalCount(SubbranchGoodRelationVo subbranchGoodRelationVo) {
		return subbranchGoodRelationDao.getTotalCount(subbranchGoodRelationVo);
	}

	@Override
	public Pagination<SubbranchGoodRelationVo> findPageSubbracheGood(SubbranchGoodRelationVo subbranchGoodRelationVo,Pagination<SubbranchGoodRelationVo> pagination) {
		return subbranchGoodRelationDao.findPageSubbracheGood(subbranchGoodRelationVo, pagination);
	}

	@Override
	public Boolean isExist(SubbranchGoodRelation subbranchGoodRelation) {
		Integer count = subbranchGoodRelationDao.isExist(subbranchGoodRelation);
		
		if(count > 0){
			return true;
		}
		
		return false;
	}

	@Override
	public Integer getSubbranchGoodRate(SubbranchGoodRelation subbranchGoodRelation) {
		return subbranchGoodRelationDao.getSubbranchGoodRate(subbranchGoodRelation);
	}

	@Override
	public GoodSkuDifferenceVo querySkuDifference(Long goodId) {
		return subbranchGoodRelationDao.querySkuDifference(goodId);
	}
}
