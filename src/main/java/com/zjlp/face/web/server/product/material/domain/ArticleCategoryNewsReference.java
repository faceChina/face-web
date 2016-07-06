package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;

public class ArticleCategoryNewsReference implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4150736645738556144L;

	private Long id;

    private Long articleCategoryId;

    private Long newsId;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}