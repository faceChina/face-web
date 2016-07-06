package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ArticleCategoryAricleColumnReferenceMapper;
import com.zjlp.face.web.server.product.material.dao.ArticleCategoryAricleColumnReferenceDao;
import com.zjlp.face.web.server.product.material.domain.ArticleCategoryAricleColumnReference;

@Repository("articleCategoryAricleColumnReferenceDao")
public class ArticleCategoryAricleColumnReferenceDaoImpl implements ArticleCategoryAricleColumnReferenceDao{

	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addAricleColumnArticleCategoryReference(
			ArticleCategoryAricleColumnReference acnr) {
		sqlSession.getMapper(ArticleCategoryAricleColumnReferenceMapper.class).insertSelective(acnr);
	}

	@Override
	public void deleteArticleCategoryAricleColumnReferenceByAricleColumnId(
			Long id) {
		sqlSession.getMapper(ArticleCategoryAricleColumnReferenceMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<ArticleCategoryAricleColumnReference> findByAricleColumnId(
			Long aricleColumnId) {
		return 	sqlSession.getMapper(ArticleCategoryAricleColumnReferenceMapper.class).findByAricleColumnId(aricleColumnId);
	}

	@Override
	public void deleteByAricleColumnId(Long aricleColumnId) {
	 	sqlSession.getMapper(ArticleCategoryAricleColumnReferenceMapper.class).deleteByAricleColumnId(aricleColumnId);

	}

	@Override
	public ArticleCategoryAricleColumnReference getArticleCategoryAricleColumnReference(
			Long id) {
		return sqlSession.getMapper(ArticleCategoryAricleColumnReferenceMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void editAricleColumnArticleCategoryReference(
			ArticleCategoryAricleColumnReference acnr) {
		sqlSession.getMapper(ArticleCategoryAricleColumnReferenceMapper.class).updateByPrimaryKeySelective(acnr);
		
	}

}
