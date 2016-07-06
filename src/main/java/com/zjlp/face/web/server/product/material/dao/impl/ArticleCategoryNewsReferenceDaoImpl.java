package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ArticleCategoryNewsReferenceMapper;
import com.zjlp.face.web.server.product.material.dao.ArticleCategoryNewsReferenceDao;
import com.zjlp.face.web.server.product.material.domain.ArticleCategoryNewsReference;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryNewsReferenceDto;

@Repository("articleCategoryNewsReferenceDao")
public class ArticleCategoryNewsReferenceDaoImpl implements
		ArticleCategoryNewsReferenceDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void addArticleCategoryNewsReference(
			ArticleCategoryNewsReference acnr) {
		sqlSession.getMapper(ArticleCategoryNewsReferenceMapper.class).insert(acnr);
	}

	@Override
	public void deleteArticleCategoryNewsReferenceByArticleId(Long articleId) {
		sqlSession.getMapper(ArticleCategoryNewsReferenceMapper.class).deleteByByArticleId(articleId);
	}

	@Override
	public void deleteArticleCategoryNewsReferenceByArticleCategoryId(
			Long articleCategoryId) {
		sqlSession.getMapper(ArticleCategoryNewsReferenceMapper.class).deleteByArticleCategoryId(articleCategoryId);
	}

	@Override
	public Integer getMaxSortByArticleCategoryId(Long articleCategoryId) {
		return sqlSession.getMapper(ArticleCategoryNewsReferenceMapper.class).getMaxSortByArticleCategoryId(articleCategoryId);
	}

	@Override
	public List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByNewsId(
			Long newsId) {
		return sqlSession.getMapper(ArticleCategoryNewsReferenceMapper.class).findArticleCategoryNewsReferenceByNewsId(newsId);
	}

	@Override
	public List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByArticleCategoryId(
			Long id) {
		return sqlSession.getMapper(ArticleCategoryNewsReferenceMapper.class).findArticleCategoryNewsReferenceByArticleCategoryId(id);
	}

	@Override
	public void editArticleCategoryNewsReference(
			ArticleCategoryNewsReference acnr) {
		sqlSession.getMapper(ArticleCategoryNewsReferenceMapper.class).updateByPrimaryKeySelective(acnr);
	}

	@Override
	public ArticleCategoryNewsReference getArticleCategoryNewsReference(Long id) {
		return sqlSession.getMapper(ArticleCategoryNewsReferenceMapper.class).selectByPrimaryKey(id);
	}

}
