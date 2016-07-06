package com.zjlp.face.web.server.product.material.domain.dto;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.News;

public class ArticleCategoryDto extends ArticleCategory {

	private static final long serialVersionUID = -1022854544839195448L;
	
	//文章数量
	private Integer articleCount;
	//文章分类包含的所有文章
	private String articleItem;
	//文章列表
	private List<News> newsList;
	//文章模板名称
	private String templateName;
	
	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getArticleItem() {
		return articleItem;
	}

	public void setArticleItem(String articleItem) {
		this.articleItem = articleItem;
	}

	public Integer getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
