package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.ArticleCategoryNewsReference;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryNewsReferenceDto;

/**
 * 文章分类与文章关联关系持久层
 * @ClassName: ArticleCategoryNewsReferenceDao 
 * @Description: (文章分类与文章关联关系持久层) 
 * @author ah
 * @date 2014年9月22日 下午2:54:04
 */
public interface ArticleCategoryNewsReferenceDao {

	/**
	 * 新增文章分类与文章关联关系
	 * @Title: addArticleCategoryNewsReference 
	 * @Description: (新增文章分类与文章关联关系) 
	 * @param acnr
	 * @date 2014年9月22日 下午2:57:35  
	 * @author ah
	 */
	void addArticleCategoryNewsReference(ArticleCategoryNewsReference acnr);

	/**
	 * 根据文章ID删除文章分类与文章关联关系
	 * @Title: deleteArticleCategoryNewsReferenceByArticleId 
	 * @Description: (根据文章ID删除文章分类与文章关联关系) 
	 * @param articleId
	 * @date 2014年9月22日 下午2:58:08  
	 * @author ah
	 */
	void deleteArticleCategoryNewsReferenceByArticleId(Long articleId);

	/**
	 * 根据文章分类ID删除文章分类与文章关联关系
	 * @Title: deleteArticleCategoryNewsReferenceByArticleCategoryId 
	 * @Description: (根据文章分类ID删除文章分类与文章关联关系) 
	 * @param articleCategoryId
	 * @date 2014年9月22日 下午2:59:11  
	 * @author ah
	 */
	void deleteArticleCategoryNewsReferenceByArticleCategoryId(
			Long articleCategoryId);

	/**
	 * 根据文章分类ID查询最大排序
	 * @Title: getMaxSortByArticleCategoryId 
	 * @Description: (根据文章分类ID查询最大排序) 
	 * @param articleCategoryId
	 * @return
	 * @date 2014年9月22日 下午3:00:18  
	 * @author ah
	 */
	Integer getMaxSortByArticleCategoryId(Long articleCategoryId);
	
	
	/**
	 * 通过文章ID查询所有关联记录
	 * @Title: findArticleCategoryNewsReferenceByNewsId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param newsId
	 * @return
	 * @date 2015年3月26日 下午7:16:02  
	 * @author fjx
	 */
	List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByNewsId(Long newsId);
	
	
	List<ArticleCategoryNewsReference> findArticleCategoryNewsReferenceByArticleCategoryId(Long id);

	
	void editArticleCategoryNewsReference(ArticleCategoryNewsReference acnr);

	ArticleCategoryNewsReference getArticleCategoryNewsReference(Long id);
	
	
}
