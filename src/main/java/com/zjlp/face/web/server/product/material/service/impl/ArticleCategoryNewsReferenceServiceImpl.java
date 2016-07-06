package com.zjlp.face.web.server.product.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.product.material.dao.ArticleCategoryNewsReferenceDao;
import com.zjlp.face.web.server.product.material.domain.ArticleCategoryNewsReference;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryNewsReferenceDto;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryNewsReferenceService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ArticleCategoryNewsReferenceServiceImpl implements
		ArticleCategoryNewsReferenceService {
	
	@Autowired
	private ArticleCategoryNewsReferenceDao articleCategoryNewsReferenceDao;

	@Override
	public void addArticleCategoryNewsReference(
			ArticleCategoryNewsReference acnr) {
		articleCategoryNewsReferenceDao.addArticleCategoryNewsReference(acnr);
	}

	@Override
	public void deleteArticleCategoryNewsReferenceByArticleId(Long articleId) {
		articleCategoryNewsReferenceDao.deleteArticleCategoryNewsReferenceByArticleId(articleId);
	}

	@Override
	public void deleteArticleCategoryNewsReferenceByArticleCategoryId(
			Long articleCategoryId) {
		articleCategoryNewsReferenceDao.deleteArticleCategoryNewsReferenceByArticleCategoryId(
				articleCategoryId);
	}

	@Override
	public Integer getMaxSortByArticleCategoryId(Long categoryId) {
		return articleCategoryNewsReferenceDao.getMaxSortByArticleCategoryId(categoryId);
	}

	@Override
	public List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByNewsId(
			Long newsId) {
		return articleCategoryNewsReferenceDao.findArticleCategoryNewsReferenceByNewsId(newsId);
	}

	@Override
	public List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByArticleCategoryId(
			Long id) {
		return articleCategoryNewsReferenceDao.findArticleCategoryNewsReferenceByArticleCategoryId(id);
	}

	@Override
	public void editArticleCategoryNewsReference(
			ArticleCategoryNewsReference acnr) {
		articleCategoryNewsReferenceDao.editArticleCategoryNewsReference(acnr);
	}

	@Override
	public ArticleCategoryNewsReference getArticleCategoryNewsReference(Long id) {
		return articleCategoryNewsReferenceDao.getArticleCategoryNewsReference(id);
	}

}
