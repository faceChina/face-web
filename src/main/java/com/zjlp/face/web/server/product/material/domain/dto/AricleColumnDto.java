package com.zjlp.face.web.server.product.material.domain.dto;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.AricleColumn;

public class AricleColumnDto extends AricleColumn{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4781303800179918644L;
	
	private Integer sort;
	
	private Long relationId;
	
	private List<ArticleCategoryDto> articleCategoryDtos;
	
	private Integer categoryCount;
	
	
	
	public Integer getCategoryCount() {
		return articleCategoryDtos.size();
	}
	

	public void setCategoryCount(Integer categoryCount) {
		this.categoryCount = categoryCount;
	}


	//文章专题列表
	private String categoryItem;
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public String getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(String categoryItem) {
		this.categoryItem = categoryItem;
	}

	public List<ArticleCategoryDto> getArticleCategoryDtos() {
		return articleCategoryDtos;
	}

	public void setArticleCategoryDtos(List<ArticleCategoryDto> articleCategoryDtos) {
		this.articleCategoryDtos = articleCategoryDtos;
	}
}
