package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.ArticleCategoryNewsReference;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryNewsReferenceDto;

public interface ArticleCategoryNewsReferenceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleCategoryNewsReference record);

    int insertSelective(ArticleCategoryNewsReference record);

    ArticleCategoryNewsReference selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleCategoryNewsReference record);

    int updateByPrimaryKey(ArticleCategoryNewsReference record);

	void deleteByByArticleId(Long articleId);

	void deleteByArticleCategoryId(Long articleCategoryId);

	Integer getMaxSortByArticleCategoryId(Long articleCategoryId);
	
	
	List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByNewsId(Long newsId);
	
	
	
	List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByArticleCategoryId(Long articleCategoryId);
}