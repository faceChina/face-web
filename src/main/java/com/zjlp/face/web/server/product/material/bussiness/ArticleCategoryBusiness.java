package com.zjlp.face.web.server.product.material.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.NewsException;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;
import com.zjlp.face.web.server.product.material.domain.vo.ArticleCategoryVo;

/** 文章分类业务层
 * @ClassName: ArticleCategoryBusiness 
 * @Description: (文章分类业务层) 
 * @author ah
 * @date 2014年9月20日 下午5:16:02  
 */
public interface ArticleCategoryBusiness {
	
	/**
	 * 新增文章分类
	 * @Title: addArticleCategory 
	 * @Description: (新增文章分类) 
	 * @param articleCategoryDto
	 * @date 2014年9月22日 下午1:46:29  
	 * @author ah
	 */
	void addArticleCategory(ArticleCategoryDto articleCategoryDto,Long userId);
	
	/**
	 * 删除文章分类
	 * @Title: deleteArticleCategory 
	 * @Description: (删除文章分类) 
	 * @param articleCategoryId
	 * @date 2014年9月22日 下午2:25:59  
	 * @author ah
	 */
	String deleteArticleCategory(Long articleCategoryId,Long userId);
	
	/**
	 * 编辑文章分类
	 * @Title: editArticleCategory 
	 * @Description: (编辑文章分类) 
	 * @param articleCategoryDto
	 * @date 2014年9月22日 下午1:59:59  
	 * @author ah
	 */
	void editArticleCategory(ArticleCategoryDto articleCategoryDto,Long userId);
	
	/**
	 * 查询文章分类分页列表
	 * @Title: findArticleCategoryPageList 
	 * @Description: (查询文章分类分页列表) 
	 * @param articleCategoryDto
	 * @param pagination
	 * @return
	 * @date 2014年9月22日 上午11:11:27  
	 * @author ah
	 */
	Pagination<ArticleCategoryDto> findArticleCategoryPageList(ArticleCategoryDto articleCategoryDto, 
			Pagination<ArticleCategoryDto> pagination) ;

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
	 * 根据主键查询文章分类
	 * @Title: getArticleCategoryById 
	 * @Description: (根据主键查询文章分类) 
	 * @param id
	 * @return
	 * @date 2014年9月28日 下午4:37:05  
	 * @author ah
	 */
	ArticleCategory getArticleCategoryById(Long id);

	/**
	 * 根据主键查询文章分类(前端)
	 * @Title: getArticleCategoryByIdForApp 
	 * @Description: (根据主键查询文章分类(前端)) 
	 * @param id
	 * @return
	 * @date 2014年9月28日 下午5:34:06  
	 * @author ah
	 */
	ArticleCategory getArticleCategoryByIdForApp(Long id);
	
	
	
	List<ArticleCategoryVo> findArticleCategoryVoListByShopNo(String shopNo) throws NewsException;
	
	
	List<ArticleCategoryVo> checkCategoryByColumnId(String shopNo, Long columnId,String title) throws NewsException;
	
	
	String updateNewsAndCategoryRelation(ArticleCategoryDto articleCategoryDto) throws NewsException;
	
	
	List<ArticleCategoryDto> findCategoryAndSortByColumn(Long columnId) throws NewsException;
	
	
	
	
	
}
