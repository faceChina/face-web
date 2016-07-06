package com.zjlp.face.web.server.product.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.material.dao.ArticleCategoryAricleColumnReferenceDao;
import com.zjlp.face.web.server.product.material.domain.ArticleCategoryAricleColumnReference;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryAricleColumnReferenceService;

@Service
public class ArticleCategoryAricleColumnReferenceServiceImpl implements ArticleCategoryAricleColumnReferenceService{

	
	
	@Autowired
	private ArticleCategoryAricleColumnReferenceDao acacrDao;
	
	
	
	@Override
	public void addAricleColumnArticleCategoryReference(
			ArticleCategoryAricleColumnReference acnr) {
		acacrDao.addAricleColumnArticleCategoryReference(acnr);
	}

	@Override
	public void deleteArticleCategoryAricleColumnReferenceByAricleColumnId(
			Long id) {
		acacrDao.deleteArticleCategoryAricleColumnReferenceByAricleColumnId(id);
	}

	@Override
	public List<ArticleCategoryAricleColumnReference> findByAricleColumnId(
			Long aricleColumnId) {
		return acacrDao.findByAricleColumnId(aricleColumnId);
	}

	@Override
	public void deleteByAricleColumnId(Long aricleColumnId) {
		// TODO Auto-generated method stub
		acacrDao.deleteByAricleColumnId(aricleColumnId);
	}

	@Override
	public ArticleCategoryAricleColumnReference getArticleCategoryAricleColumnReference(
			Long id) {
		return acacrDao.getArticleCategoryAricleColumnReference(id);
	}

	@Override
	public void editAricleColumnArticleCategoryReference(
			ArticleCategoryAricleColumnReference acnr) {
		acacrDao.editAricleColumnArticleCategoryReference(acnr);
	}

}
