package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AricleColumnMapper;
import com.zjlp.face.web.server.product.material.dao.AricleColumnDao;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.dto.AricleColumnDto;

@Repository("aricleColumnDao")
public class AricleColumnDaoImpl implements AricleColumnDao{

	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public void addAricleColumn(AricleColumn aricleColumn) {
		sqlSession.getMapper(AricleColumnMapper.class).insertSelective(aricleColumn);
		
	}

	@Override
	public void deleteAricleColumnById(Long id) {
		sqlSession.getMapper(AricleColumnMapper.class).deleteByPrimaryKey(id);
		
	}

	@Override
	public void editAricleColumn(AricleColumn aricleColumn) {
		sqlSession.getMapper(AricleColumnMapper.class).updateByPrimaryKeySelective(aricleColumn);
		
	}

	@Override
	public List<AricleColumn> findAricleColumnByShopNo(String shopNo) {
		return sqlSession.getMapper(AricleColumnMapper.class).selectListByShopNo(shopNo);
	}

	@Override
	public Integer getCount(AricleColumnDto aricleColumnDto) {
		return sqlSession.getMapper(AricleColumnMapper.class).getCount(aricleColumnDto);
	}

	@Override
	public List<AricleColumnDto> findAricleColumnList(
			AricleColumnDto aricleColumnDto, int start, int pageSize) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", aricleColumnDto);
		paramMap.put("start", start);
		paramMap.put("pageSize", pageSize);
		return sqlSession.getMapper(AricleColumnMapper.class).selectPageList(paramMap);
	}

	@Override
	public AricleColumn getAricleColumnById(Long id) {
		return sqlSession.getMapper(AricleColumnMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<AricleColumnDto> findCategoryAndSortByColumnId(Long columnId) {
		return sqlSession.getMapper(AricleColumnMapper.class).findCategoryAndSortByColumnId(columnId);
	}

	
	
	
	
}
