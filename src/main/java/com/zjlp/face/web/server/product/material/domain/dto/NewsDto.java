package com.zjlp.face.web.server.product.material.domain.dto;

import com.zjlp.face.web.server.product.material.domain.News;

/**
 * 文章dto
 * @ClassName: NewsDto 
 * @Description: (文章dto) 
 * @author ah
 * @date 2014年7月29日 上午11:40:43
 */
public class NewsDto extends News {

	private static final long serialVersionUID = 4793356064199897981L;
	
	//文章分组id
	private Long groupId;
	//绑定的文章分类ID
	private String bindCategoryId;
	
	private String soso;
	
	private Integer sort;
	
	private Long relationId;
	
	

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSoso() {
		return soso;
	}

	public void setSoso(String soso) {
		this.soso = soso;
	}
	

	public String getBindArticleId() {
		return bindCategoryId;
	}

	public void setBindCategoryId(String bindCategoryId) {
		this.bindCategoryId = bindCategoryId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
