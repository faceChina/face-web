package com.zjlp.face.web.server.operation.subbranch.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.SubbranchGoodRelationMapper;
import com.zjlp.face.web.server.operation.subbranch.dao.SubbranchGoodRelationDao;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.GoodSkuDifferenceVo;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;

/**
 * 
 * @author 熊斌
 * 2015年8月19日上午11:35:32
 * @version 1.0
 */
@Repository("SubbranchGoodRelationDao")
public class SubbranchGoodRelationDaoImpl implements SubbranchGoodRelationDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long add(SubbranchGoodRelation subbranchGoodRelation) {
		this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).insert(subbranchGoodRelation);
		return subbranchGoodRelation.getId();
	}

	@Override
	public SubbranchGoodRelation findByPrimarykey(Long id) {
		return this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKey(SubbranchGoodRelation subbranchGoodRelation) {
		this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).updateByPrimaryKeySelective(subbranchGoodRelation);
	}

	@Override
	public void deleteByPrimaryKey(Long id) {
		this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public Integer getTotalCount(SubbranchGoodRelationVo subbranchGoodRelationVo) {
		return this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).getTotalCount(subbranchGoodRelationVo);
	}

	@Override
	public Pagination<SubbranchGoodRelationVo> findPageSubbracheGood(SubbranchGoodRelationVo subbranchGoodRelationVo,Pagination<SubbranchGoodRelationVo> pagination) {
		Integer count = this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).getTotalCount(subbranchGoodRelationVo);
		pagination.setTotalRow(count);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subbranchGoodRelationVo", subbranchGoodRelationVo);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		pagination.setDatas(this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).findPageSubbracheGood(map));
		return pagination;
	}

	@Override
	public Integer isExist(SubbranchGoodRelation subbranchGoodRelation) {
		return this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).isExist(subbranchGoodRelation);
	}

	@Override
	public Integer getSubbranchGoodRate(SubbranchGoodRelation subbranchGoodRelation) {
		return this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).findSubbranchGoodRate(subbranchGoodRelation);
	}

	@Override
	public GoodSkuDifferenceVo querySkuDifference(Long goodId) {
		return this.sqlSession.getMapper(SubbranchGoodRelationMapper.class).querySkuDifference(goodId);
	}
}
