package com.zjlp.face.web.mapper;

import java.util.HashMap;
import java.util.List;

import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;

public interface ArticleCategoryMapper {
	   int deleteByPrimaryKey(Long id);

	    int insert(ArticleCategory record);

	    int insertSelective(ArticleCategory record);

	    ArticleCategory selectByPrimaryKey(Long id);

	    int updateByPrimaryKeySelective(ArticleCategory record);

	    int updateByPrimaryKey(ArticleCategory record);

		List<ArticleCategory> selectListByShopNo(String shopNo);

		List<ArticleCategory> selectListByArticleId(Long articleId);

		Integer getCount(ArticleCategoryDto articleCategoryDto);

		List<ArticleCategoryDto> selectPageList(HashMap<String, Object> paramMap);
		
		List<ArticleCategory> findArticleCategoryList(ArticleCategory articleCategory);
		
		
		List<ArticleCategoryDto> findCategoryAndSortByColumn(Long columnId);
}