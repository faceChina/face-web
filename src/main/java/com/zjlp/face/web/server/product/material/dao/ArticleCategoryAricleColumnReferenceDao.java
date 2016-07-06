package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.ArticleCategoryAricleColumnReference;

public interface ArticleCategoryAricleColumnReferenceDao {

	
	void addAricleColumnArticleCategoryReference(ArticleCategoryAricleColumnReference acnr);

	void deleteArticleCategoryAricleColumnReferenceByAricleColumnId(
			Long id);

	
	List<ArticleCategoryAricleColumnReference> findByAricleColumnId(Long aricleColumnId);

	void deleteByAricleColumnId(Long aricleColumnId);
	
	ArticleCategoryAricleColumnReference getArticleCategoryAricleColumnReference(Long id);
	
	void editAricleColumnArticleCategoryReference(ArticleCategoryAricleColumnReference acnr);
	
	
}
