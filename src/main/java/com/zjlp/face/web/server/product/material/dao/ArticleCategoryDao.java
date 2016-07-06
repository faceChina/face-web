package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;

/**
 * 文章分类持久层
 * @ClassName: ArticleCategoryDao 
 * @Description: (文章分类持久层) 
 * @author ah
 * @date 2014年9月23日 下午7:45:26
 */
public interface ArticleCategoryDao {

	/**
	 * 新增文章分类
	 * @Title: addArticleCategory 
	 * @Description: (新增文章分类) 
	 * @param articleCategory
	 * @date 2014年9月23日 下午7:46:06  
	 * @author ah
	 */
	void addArticleCategory(ArticleCategory articleCategory);

	/**
	 * 根据主键删除文章分类
	 * @Title: deleteArticleCategoryByArticleCategoryId 
	 * @Description: (根据主键删除文章分类) 
	 * @param articleCategoryId
	 * @date 2014年9月23日 下午7:47:12  
	 * @author ah
	 */
	void deleteArticleCategoryByArticleCategoryId(Long articleCategoryId);
	
	/**
	 * 编辑文章分类
	 * @Title: editArticleCategory 
	 * @Description: (编辑文章分类) 
	 * @param articleCategory
	 * @date 2014年9月23日 下午7:47:18  
	 * @author ah
	 */
	void editArticleCategory(ArticleCategory articleCategory);

	/**
	 * 根据店铺编号查询文章分类
	 * @Title: findArticleCategoryListByShopNo 
	 * @Description: (根据店铺编号查询文章分类) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月23日 下午7:47:24  
	 * @author ah
	 */
	List<ArticleCategory> findArticleCategoryListByShopNo(String shopNo);

	/**
	 * 根据文章主键查询文章分类
	 * @Title: findArticleCategoryListByArticleId 
	 * @Description: (根据文章主键查询文章分类) 
	 * @param articleId
	 * @return
	 * @date 2014年9月23日 下午7:47:30  
	 * @author ah
	 */
	List<ArticleCategory> findArticleCategoryListByArticleId(Long articleId);

	/**
	 * 查询文章分类总数
	 * @Title: getCount 
	 * @Description: (查询文章分类总数) 
	 * @param articleCategoryDto
	 * @return
	 * @date 2014年9月23日 下午7:47:36  
	 * @author ah
	 */
	Integer getCount(ArticleCategoryDto articleCategoryDto);

	/**
	 * 查询文章分类分页列表
	 * @Title: findArticleCategoryPageList 
	 * @Description: (查询文章分类分页列表) 
	 * @param articleCategoryDto
	 * @param start
	 * @param pageSize
	 * @return
	 * @date 2014年9月23日 下午7:47:44  
	 * @author ah
	 */
	List<ArticleCategoryDto> findArticleCategoryPageList(
			ArticleCategoryDto articleCategoryDto, int start, int pageSize);

	/**
	 * 根据主键查询文章分类
	 * @Title: getArticleCategoryById 
	 * @Description: (根据主键查询文章分类) 
	 * @param articlecategoryId
	 * @return
	 * @date 2014年9月23日 下午7:47:50  
	 * @author ahs
	 */
	ArticleCategory getArticleCategoryById(Long articlecategoryId);
	
	
	List<ArticleCategory> findArticleCategoryList(ArticleCategory articleCategory);
	
	
	List<ArticleCategoryDto> findCategoryAndSortByColumn(Long columnId);

}
