package com.zjlp.face.web.server.product.material.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.NewsException;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;
import com.zjlp.face.web.server.product.material.domain.vo.ArticleCategoryVo;
import com.zjlp.face.web.server.product.material.domain.vo.NewsVo;

/**
 * 文章业务接口
 * @ClassName: NewsBusiness 
 * @Description: (文章业务接口) 
 * @author ah
 * @date 2014年7月29日 上午11:09:34
 */
public interface NewsBusiness {
	
	/**
	 * 新增文章
	 * @Title: addNews 
	 * @Description: (新增文章) 
	 * @param news
	 * @param categoryItem
	 * @throws NewsException
	 * @date 2014年7月29日 上午11:25:19  
	 * @author ah
	 */
	void addNews(News news, String categoryItem, Long userId) throws NewsException;
	
	/**
	 * 编辑文章
	 * @Title: editNews 
	 * @Description: (编辑文章) 
	 * @param news
	 * @param categoryItem
	 * @throws NewsException
	 * @date 2014年7月29日 上午11:28:06  
	 * @author ah
	 */
	void editNews(News news, String categoryItem,Long userId) throws NewsException;
	
	/**
	 * 删除文章
	 * @Title: deleteNews 
	 * @Description: (删除文章) 
	 * @param id
	 * @throws NewsException
	 * @date 2014年7月29日 上午11:31:47  
	 * @author Administrator
	 */
	String deleteNews(Long id,Long userId) throws NewsException;
	
	/**
	 * 查询文章模块分页列表
	 * @Title: findNewsPageList 
	 * @Description: (查询文章模块分页列表) 
	 * @param dto
	 * @param pagination
	 * @return
	 * @throws NewsException
	 * @date 2014年7月29日 上午11:34:44  
	 * @author Administrator
	 */
	Pagination<NewsDto> findNewsPageList(NewsDto dto, Pagination<NewsDto> pagination) throws NewsException;
	
	/**
	 * 根据文章id查询文章
	 * @Title: getNewsById 
	 * @Description: (根据文章id查询文章) 
	 * @param id
	 * @return
	 * @date 2014年7月30日 上午9:12:44  
	 * @author ah
	 */
	News getNewsById(Long id);
	
	/**
	 * 根据主键查询文章
	 * @Title: getNewsDtoById 
	 * @Description: (根据主键查询文章) 
	 * @param id
	 * @return
	 * @date 2014年9月24日 下午5:38:36  
	 * @author ah
	 */
	NewsDto getNewsDtoById(Long id);
	
	/******************** V.5.0 文章列表排序删除 开始 *****************/
	/**
	 * 调整文章顺序
	 * @Title: sortNews 
	 * @Description: (调整文章顺序) 
	 * @param upId
	 * @param downId
	 * @throws NewsException
	 * @date 2014年7月29日 上午11:49:24  
	 * @author ah
	 *//*
	void sortNews(Long upId, Long downId) throws NewsException;*/
	/******************** V.5.0 文章列表排序删除 结束 *****************/

	/**
	 * 查询文章模块分页列表(预览用)
	 * @Title: findNewsPageListPreview 
	 * @Description: (查询文章模块分页列表(预览用)) 
	 * @param dto
	 * @param pagination
	 * @return
	 * @date 2014年7月30日 下午3:06:37  
	 * @author ah
	 */
	Pagination<NewsDto> findNewsPageListPreview(NewsDto dto,
			Pagination<NewsDto> pagination);

	/**
	 * 查询文章分页列表（前端）
	 * @Title: findNewsPageListForApp 
	 * @Description: (查询文章分页列表（前端）) 
	 * @param dto
	 * @param pagination
	 * @return
	 * @date 2014年7月30日 下午5:10:28  
	 * @author ah
	 */
	Pagination<NewsDto> findNewsPageListForApp(NewsDto dto,
			Pagination<NewsDto> pagination);

	/**
	 * 批量删除文章
	 * @Title: delBatchNews 
	 * @Description: (批量删除文章) 
	 * @param articleItem
	 * @date 2014年9月23日 下午9:36:59  
	 * @author ah
	 */
	void delBatchNews(String articleItem,Long userId);

	/**
	 * 根据店铺编号查询文章列表
	 * @Title: findNewsByShopNo 
	 * @Description: (根据店铺编号查询文章列表) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月25日 下午4:42:54  
	 * @author ah
	 */
	List<News> findNewsByShopNo(String shopNo);

	/**
	 * 根据文章分类主键查询文章列表
	 * @Title: findNewsListByArticleCategoryId 
	 * @Description: (根据文章分类主键查询文章列表) 
	 * @param id
	 * @return
	 * @date 2014年9月25日 下午8:34:13  
	 * @author ah
	 */
	List<News> findNewsListByArticleCategoryId(Long id);
	
	/**
	 * 根据文章分类主键查询文章列表(前端)
	 * @Title: findNewsListByArticleCategoryIdForApp 
	 * @Description: (根据文章分类主键查询文章列表(前端)) 
	 * @param id
	 * @return
	 * @date 2014年9月28日 下午5:28:30  
	 * @author ah
	 */
	List<News> findNewsListByArticleCategoryIdForApp(Long id);

	/**
	 * 根据绑定的文章ID查询文章列表
	 * @Title: findNewsListByBindArticleId 
	 * @Description: (根据绑定的文章ID查询文章列表) 
	 * @param bindArticleId
	 * @return
	 * @date 2014年9月26日 上午11:09:12  
	 * @author ah
	 */
	List<News> findNewsListByBindArticleId(String bindArticleId);

	/**
	 * 根据绑定的文章ID查询文章列表(预览)
	 * @Title: findNewsListByBindArticleIdForPreview 
	 * @Description: (根据绑定的文章ID查询文章列表(预览)) 
	 * @param bindArticleId
	 * @return
	 * @date 2014年9月28日 上午10:42:51  
	 * @author ah
	 */
	List<News> findNewsListByBindArticleIdForPreview(String bindArticleId);

	/**
	 * 查询文章列表
	 * @Title: findNewsList 
	 * @Description: (查询文章列表) 
	 * @param news
	 * @return
	 * @date 2014年9月29日 下午1:48:25  
	 * @author ah
	 */
	List<News> findNewsList(News news);
	
	

	List<ArticleCategoryVo> checkedNewsByNewsId(String shopNo,Long newsId)throws NewsException;
	
	
	
	List<NewsVo> checkNewsByCategoryId(String shopNo,Long categoryId,String title) throws NewsException;
	
	
	String delAllArticle(String delId,Long userId) throws NewsException;
	
	
	List<NewsDto> findNewsAndSortByCategoryId(Long categoryId) throws NewsException;
	
	
	String sortNewByCategoryId(Long categoryId,Integer sort,Integer type) throws NewsException;
	
	
	String soertNew(Long subId,Long tarId) throws NewsException;
	
}
