package com.zjlp.face.web.server.product.material.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;

/**
 * 文章分类基础服务层
 * @ClassName: ArticleCategoryService 
 * @Description: (文章分类基础服务层) 
 * @author ah
 * @date 2014年9月20日 下午5:57:17
 */
public interface ArticleCategoryService {
	
	/**
	 * 新增文章分类
	 * @Title: addArticleCategory 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param articleCategory
	 * @date 2014年9月22日 下午4:08:33  
	 * @author ah
	 */
	void addArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 根据文章分类ID删除文章分类
	 * @Title: deleteArticleCategoryByArticleCategoryId 
	 * @Description: (根据文章分类ID删除文章分类) 
	 * @param articleCategoryId
	 * @date 2014年9月22日 下午4:16:44  
	 * @author ah
	 */
	void deleteArticleCategoryByArticleCategoryId(Long articleCategoryId);
	
	/**
	 * 编辑文章分类
	 * @Title: editArticleCategory 
	 * @Description: (编辑文章分类) 
	 * @param articleCategory
	 * @date 2014年9月22日 下午4:11:40  
	 * @author ah
	 */
	void editArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 根据店铺编号查询文章分类列表
	 * @Title: findArticleCategoryListByShopNo 
	 * @Description: (根据店铺编号查询文章分类列表) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月20日 下午5:19:04  
	 * @author ah
	 */
	List<ArticleCategory> findArticleCategoryListByShopNo(String shopNo);

	/**
	 * 根据文章ID查询文章分类列表
	 * @Title: findArticleCategoryListByArticleId 
	 * @Description: (根据文章ID查询文章分类列表) 
	 * @param id
	 * @return
	 * @date 2014年9月20日 下午5:20:41  
	 * @author ah
	 */
	List<ArticleCategory> findArticleCategoryListByArticleId(Long id);

	/**
	 * 查询文章分类分页列表
	 * @Title: findArticleCategoryPageList 
	 * @Description: (查询文章分类分页列表) 
	 * @param articleCategoryDto
	 * @param pagination
	 * @return
	 * @date 2014年9月23日 下午2:51:16  
	 * @author ah
	 */
	Pagination<ArticleCategoryDto> findArticleCategoryPageList(
			ArticleCategoryDto articleCategoryDto,
			Pagination<ArticleCategoryDto> pagination);
	
	/**
	 * 根据主键查询文章分类
	 * @Title: getArticleCategoryById 
	 * @Description: (根据主键查询文章分类) 
	 * @param categoryId
	 * @return
	 * @date 2014年9月20日 下午6:02:02  
	 * @author ah
	 */
	ArticleCategory getArticleCategoryById(Long categoryId);
	
	
	
	List<ArticleCategory> findArticleCategoryList(ArticleCategory ac);
	
	
	List<ArticleCategoryDto> findCategoryAndSortByColumn(Long columnId);

}
