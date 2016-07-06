package com.zjlp.face.web.server.product.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.dao.ArticleCategoryDao;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ArticleCategoryServiceImpl implements ArticleCategoryService {
	
	@Autowired
	private ArticleCategoryDao articleCategoryDao;

	@Override
	public void addArticleCategory(ArticleCategory articleCategory) {
		articleCategoryDao.addArticleCategory(articleCategory);
	}

	@Override
	public void deleteArticleCategoryByArticleCategoryId(Long articleCategoryId) {
		articleCategoryDao.deleteArticleCategoryByArticleCategoryId(articleCategoryId);
	}

	@Override
	public void editArticleCategory(ArticleCategory articleCategory) {
		articleCategoryDao.editArticleCategory(articleCategory);
	}

	@Override
	public List<ArticleCategory> findArticleCategoryListByShopNo(String shopNo) {
		return articleCategoryDao.findArticleCategoryListByShopNo(shopNo);
	}

	@Override
	public List<ArticleCategory> findArticleCategoryListByArticleId(Long articleId) {
		return articleCategoryDao.findArticleCategoryListByArticleId(articleId);
	}

	@Override
	public Pagination<ArticleCategoryDto> findArticleCategoryPageList(
			ArticleCategoryDto articleCategoryDto,
			Pagination<ArticleCategoryDto> pagination) {
		Integer totalRow = articleCategoryDao.getCount(articleCategoryDto);
		List<ArticleCategoryDto> datas = articleCategoryDao.findArticleCategoryPageList(
				articleCategoryDto, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public ArticleCategory getArticleCategoryById(Long articlecategoryId) {
		return articleCategoryDao.getArticleCategoryById(articlecategoryId);
	}

	@Override
	public List<ArticleCategory> findArticleCategoryList(ArticleCategory ac) {
		return articleCategoryDao.findArticleCategoryList(ac);
	}

	@Override
	public List<ArticleCategoryDto> findCategoryAndSortByColumn(Long columnId) {
		return articleCategoryDao.findCategoryAndSortByColumn(columnId);
	}

}
