package com.zjlp.face.web.server.product.material.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;

public interface NewsService {
	
	/**
	 * 新增文章
	 * @Title: addNews 
	 * @Description: (新增文章) 
	 * @param news
	 * @date 2014年7月29日 下午2:14:40  
	 * @author ah
	 */
	void addNews(News news);

	/**
	 * 编辑文章
	 * @Title: editNews 
	 * @Description: (编辑文章) 
	 * @param news
	 * @date 2014年7月29日 下午2:34:32  
	 * @author ah
	 */
	void editNews(News news);
	
	/**
	 * 删除文章
	 * @Title: deleteNews 
	 * @Description: (删除文章) 
	 * @param id
	 * @date 2014年7月29日 下午2:38:50  
	 * @author ah
	 */
	void deleteNews(Long id);
	
	/**
	 * 查询文章分页列表
	 * @Title: findNewsPageList 
	 * @Description: (查询文章分页列表) 
	 * @param dto
	 * @param pagination
	 * @return
	 * @date 2014年7月29日 下午2:47:46  
	 * @author ah
	 */
	Pagination<NewsDto> findNewsPageList(NewsDto dto,
			Pagination<NewsDto> pagination);
	
	/**
	 * 查询最大的顺序
	 * @Title: getMaxSortByShopNo 
	 * @Description: (查询最大的顺序) 
	 * @return
	 * @date 2014年7月29日 下午2:27:18  
	 * @author ah
	 * @param shopNo 
	 */
	Integer getMaxSortByShopNo(String shopNo);

	/**
	 * 根据文章id查询文章
	 * @Title: getNewsById 
	 * @Description: (根据文章id查询文章) 
	 * @param id
	 * @return
	 * @date 2014年7月29日 下午2:57:32  
	 * @author ah
	 */
	News getNewsById(Long id);
	
	/**
	 * 根据文章id查询文章
	 * @Title: getNewsDtoById 
	 * @Description: (根据文章id查询文章) 
	 * @param id
	 * @return
	 * @date 2014年9月24日 下午5:40:36  
	 * @author ah
	 */
	NewsDto getNewsDtoById(Long id);
	
	/**
	 * 查询店铺编号下的所有新闻列表
	 * @Title: findNewsByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2014年7月29日 下午3:31:47  
	 * @author ah
	 */
	List<News> findNewsByShopNo(String shopNo);

	/**
	 * 查询模块配置的文章列表
	 * @Title: findNewsPageListForModular 
	 * @Description: (查询模块配置的文章列表) 
	 * @param dto
	 * @param pagination
	 * @return
	 * @date 2014年7月30日 下午5:58:29  
	 * @author ah
	 */
	Pagination<NewsDto> findNewsPageListForModular(NewsDto dto,
			Pagination<NewsDto> pagination);

	/**
	 * 根据文章分类ID查询文章列表
	 * @Title: findNewsListByArticleCategoryId 
	 * @Description: (根据文章分类ID查询文章列表) 
	 * @param articleCategoryId
	 * @return
	 * @date 2014年9月23日 下午3:03:02  
	 * @author ah
	 */
	List<News> findNewsListByArticleCategoryId(Long articleCategoryId);

	/**
	 * 查询文章列表
	 * @Title: findNewsList 
	 * @Description: (查询文章列表) 
	 * @param news
	 * @return
	 * @date 2014年9月29日 下午1:52:16  
	 * @author ah
	 */
	List<News> findNewsList(News news);

	/**
	 * 查询文章分页列表
	 * @Title: findNewsPageListByCondition 
	 * @Description: (查询文章分页列表) 
	 * @param dto
	 * @param pagination
	 * @return
	 * @date 2014年10月6日 上午10:37:56  
	 * @author ah
	 */
	Pagination<NewsDto> findNewsPageListByCondition(NewsDto dto,
			Pagination<NewsDto> pagination);
	
	
	List<NewsDto> findNewsAndSortByCategoryId(Long categoryId);

}
