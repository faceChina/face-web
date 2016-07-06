package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ArticleCategoryMapper;
import com.zjlp.face.web.server.product.material.dao.ArticleCategoryDao;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;

@Repository("articleCategoryDao")
public class ArticleCategoryDaoImpl implements ArticleCategoryDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void addArticleCategory(ArticleCategory articleCategory) {
		sqlSession.getMapper(ArticleCategoryMapper.class).insertSelective(articleCategory);
	}

	@Override
	public void deleteArticleCategoryByArticleCategoryId(Long articleCategoryId) {
		sqlSession.getMapper(ArticleCategoryMapper.class).deleteByPrimaryKey(articleCategoryId);
	}

	@Override
	public void editArticleCategory(ArticleCategory articleCategory) {
		sqlSession.getMapper(ArticleCategoryMapper.class).updateByPrimaryKeySelective(articleCategory);
	}

	@Override
	public List<ArticleCategory> findArticleCategoryListByShopNo(String shopNo) {
		return sqlSession.getMapper(ArticleCategoryMapper.class).selectListByShopNo(shopNo);
	}

	@Override
	public List<ArticleCategory> findArticleCategoryListByArticleId(
			Long articleId) {
		return sqlSession.getMapper(ArticleCategoryMapper.class).selectListByArticleId(articleId);
	}

	@Override
	public Integer getCount(ArticleCategoryDto articleCategoryDto) {
		return sqlSession.getMapper(ArticleCategoryMapper.class).getCount(articleCategoryDto);
	}

	@Override
	public List<ArticleCategoryDto> findArticleCategoryPageList(
			ArticleCategoryDto articleCategoryDto, int start, int pageSize) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", articleCategoryDto);
		paramMap.put("start", start);
		paramMap.put("pageSize", pageSize);
		return sqlSession.getMapper(ArticleCategoryMapper.class).selectPageList(paramMap);
	}

	@Override
	public ArticleCategory getArticleCategoryById(Long articlecategoryId) {
		return sqlSession.getMapper(ArticleCategoryMapper.class).selectByPrimaryKey(articlecategoryId);
	}

	@Override
	public List<ArticleCategory> findArticleCategoryList(
			ArticleCategory articleCategory) {
		return sqlSession.getMapper(ArticleCategoryMapper.class).findArticleCategoryList(articleCategory);
	}

	@Override
	public List<ArticleCategoryDto> findCategoryAndSortByColumn(Long columnId) {
		return sqlSession.getMapper(ArticleCategoryMapper.class).findCategoryAndSortByColumn(columnId);
	}

}
