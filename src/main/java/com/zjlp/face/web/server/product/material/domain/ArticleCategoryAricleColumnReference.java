package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;

public class ArticleCategoryAricleColumnReference implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3813134557625417182L;

	private Long id;

    private Long articleCategoryId;

    private Long aricleColumnId;

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

    public Long getAricleColumnId() {
        return aricleColumnId;
    }

    public void setAricleColumnId(Long aricleColumnId) {
        this.aricleColumnId = aricleColumnId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}