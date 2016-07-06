package com.zjlp.face.web.server.product.material.service;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.ArticleCategoryNewsReference;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryNewsReferenceDto;

/**
 * 文章分类与文章关联关系基础服务层
 * @ClassName: ArticleCategoryNewsReferenceService 
 * @Description: (文章分类与文章关联关系基础服务层) 
 * @author ah
 * @date 2014年9月20日 下午4:27:05
 */
public interface ArticleCategoryNewsReferenceService {

	/**
	 * 新增文章分类与文章关联关系
	 * @Title: addArticleCategoryNewsReference 
	 * @Description: (新增文章分类与文章关联关系) 
	 * @param acnr
	 * @date 2014年9月20日 下午4:28:45  
	 * @author ah
	 */
	void addArticleCategoryNewsReference(ArticleCategoryNewsReference acnr);
	
	/**
	 * 根据文章ID删除文章分类与文章关联关系
	 * @Title: deleteArticleCategoryNewsReferenceByArticleId 
	 * @Description: (根据文章ID删除文章分类与文章关联关系) 
	 * @param articleId
	 * @date 2014年9月20日 下午4:30:38  
	 * @author ah
	 */
	void deleteArticleCategoryNewsReferenceByArticleId(Long articleId);
	
	/**
	 * 根据文章分类ID删除文章分类与文章关联关系
	 * @Title: deleteArticleCategoryNewsReferenceByArticleCategoryId 
	 * @Description: (根据文章分类ID删除文章分类与文章关联关系) 
	 * @param articleCategoryId
	 * @date 2014年9月20日 下午5:05:57  
	 * @author ah
	 */
	void deleteArticleCategoryNewsReferenceByArticleCategoryId(Long articleCategoryId);

	/**
	 * 根据文章分类ID查询最大排序
	 * @Title: getMaxSortByArticleCategoryId 
	 * @Description: (根据文章分类ID查询最大排序) 
	 * @param categoryId
	 * @return
	 * @date 2014年9月22日 下午2:08:27  
	 * @author ah
	 */
	Integer getMaxSortByArticleCategoryId(Long categoryId);
	
	
	
	List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByNewsId(Long newsId);
	
	
	List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByArticleCategoryId(Long id);
	
	void editArticleCategoryNewsReference(ArticleCategoryNewsReference acnr);
	
	ArticleCategoryNewsReference getArticleCategoryNewsReference(Long id);
	

}
