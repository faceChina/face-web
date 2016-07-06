package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;

/**
 * 文章持久层
 * @ClassName: NewsDao 
 * @Description: (文章持久层) 
 * @author ah
 * @date 2014年7月29日 下午3:06:21
 */
public interface NewsDao {

	/**
	 * 新增文章
	 * @Title: addNews 
	 * @Description: (新增文章) 
	 * @param news
	 * @date 2014年7月29日 下午3:10:10 	 
	 * @author ah
	 */
	void addNews(News news);

	/**
	 * 编辑文章
	 * @Title: editNews 
	 * @Description: (编辑文章) 
	 * @param news
	 * @date 2014年7月29日 下午3:10:29  
	 * @author ah
	 */
	void editNews(News news);

	/**
	 * 删除文章
	 * @Title: deleteNews 
	 * @Description: (删除文章) 
	 * @param id
	 * @date 2014年7月29日 下午3:10:46  
	 * @author ah
	 */
	void deleteNews(Long id);

	/**
	 * 查询文章总数
	 * @Title: getCount 
	 * @Description: (查询文章总数) 
	 * @param dto
	 * @return
	 * @date 2014年7月29日 下午3:11:06  
	 * @author ah
	 */
	Integer getCount(NewsDto dto);

	/**
	 * 查询文章分页列表
	 * @Title: findNewsPageList 
	 * @Description: (查询文章分页列表) 
	 * @param dto
	 * @param start
	 * @param pageSize
	 * @return
	 * @date 2014年7月29日 下午3:11:43  
	 * @author ah
	 */
	List<NewsDto> findNewsPageList(NewsDto dto, int start, int pageSize);

	/**
	 * 查询文章最大顺序
	 * @Title: getMaxSortByShopNo 
	 * @Description: (查询文章最大顺序) 
	 * @param shopNo
	 * @return
	 * @date 2014年7月29日 下午3:12:19  
	 * @author ah
	 */
	Integer getMaxSortByShopNo(String shopNo);

	/**
	 * 根据文章id查询文章
	 * @Title: getNewsById 
	 * @Description: (根据文章id查询文章) 
	 * @param id
	 * @return
	 * @date 2014年7月29日 下午3:12:52  
	 * @author ah
	 */
	News getNewsById(Long id);
	
	/**
	 * 根据文章id查询文章
	 * @Title: getNewsDtoById 
	 * @Description: (根据文章id查询文章) 
	 * @param id
	 * @return
	 * @date 2014年9月24日 下午5:42:07  
	 * @author ah
	 */
	NewsDto getNewsDtoById(Long id);
	
	/**
	 * 根据店铺编号查询文章列表
	 * @Title: findNewsByShopNo 
	 * @Description: (根据店铺编号查询文章列表) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月24日 下午5:42:19  
	 * @author ah
	 */
	List<News> findNewsByShopNo(String shopNo);

	/**
	 * 查询模块配置文章列表总数
	 * @Title: getCountForModular 
	 * @Description: (查询模块配置文章列表总数) 
	 * @param dto
	 * @return
	 * @date 2014年7月30日 下午6:07:03  
	 * @author ah
	 */
	Integer getCountForModular(NewsDto dto);

	/**
	 * 查询模块配置文章列表
	 * @Title: findNewsPageListForModular 
	 * @Description: (查询模块配置文章列表) 
	 * @param dto
	 * @param start
	 * @param pageSize
	 * @return
	 * @date 2014年7月30日 下午6:07:59  
	 * @author ah
	 */
	List<NewsDto> findNewsPageListForModular(NewsDto dto, int start,
			int pageSize);

	/**
	 * 根据文章分类ID查询文章列表
	 * @Title: findNewsListByArticleCategoryId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param articleCategoryId
	 * @return
	 * @date 2014年9月23日 下午3:08:22  
	 * @author ah
	 */
	List<News> findNewsListByArticleCategoryId(Long articleCategoryId);

	/**
	 * 查询文章列表
	 * @Title: findNewsList 
	 * @Description: (查询文章列表) 
	 * @param news
	 * @return
	 * @date 2014年9月29日 下午1:53:19  
	 * @author ah
	 */
	List<News> findNewsList(News news);

	/**
	 * 根据主键编辑文章
	 * @Title: editNewsById 
	 * @Description: (根据主键编辑文章) 
	 * @param news
	 * @date 2014年10月11日 上午11:51:32  
	 * @author ah
	 */
	void editNewsById(News news);
	
	
	
	List<NewsDto>  findNewsAndSortByCategoryId(Long categoryId);

}
