package com.zjlp.face.web.server.product.material.bussiness.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.NewsException;
import com.zjlp.face.web.server.product.material.bussiness.NewsBusiness;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.ArticleCategoryNewsReference;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;
import com.zjlp.face.web.server.product.material.domain.vo.ArticleCategoryVo;
import com.zjlp.face.web.server.product.material.domain.vo.NewsVo;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryNewsReferenceService;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryService;
import com.zjlp.face.web.server.product.material.service.NewsService;

@Service
public class NewsBusinessImpl implements NewsBusiness {
	
	@Autowired
	private NewsService newsService;
	@Autowired
	private ArticleCategoryNewsReferenceService articleCategoryNewsReferenceService;
	@Autowired
	private ArticleCategoryService articleCategoryService;
	@Autowired(required=false)
	private ImageService imageService;
	//增加的位数
	private static final Integer INDEX = 1;
	//文章封面图片大小
	private static final String ARTICLE_SIZE = "400_400";
	
	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addNews(News news, String categoryItem, Long userId) throws NewsException {
		Date date = new Date();
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		String picPath =  news.getPicPath();
		try {
			AssertUtil.notNull(news, "文章信息");
			news.setUpdateTime(date);
			news.setCreateTime(date);
			//根据发布类型设定相应内容
			this._editAticle(news);
			//1.新增文章
			newsService.addNews(news);
			AssertUtil.notNull(news.getId(), "newsId");
			if (StringUtils.isNotBlank(picPath)) {
				 //上传图片到TFS
	            FileBizParamDto dto = new FileBizParamDto(ARTICLE_SIZE, userId, news.getShopNo(), "NEWS", news.getId().toString(), ImageConstants.ATICLE_FILE, 1);
	            dto.setImgData(picPath);
	            list.add(dto);
			}
			if(StringUtils.isNotBlank(news.getArticleContent())){
	            //封装文章内容UBB图片
	            FileBizParamDto ubbDto = new FileBizParamDto(null, userId, news.getShopNo(), "NEWS", news.getId().toString(), ImageConstants.UBB_FILE, 1);
	            ubbDto.setImgData(news.getArticleContent());
	            list.add(ubbDto);
			}
			if(null != list && list.size() > 0){
	            String flag = imageService.addOrEdit(list);
	            
	            JSONObject jsonObject = JSONObject.fromObject(flag);
	            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
	            String dataJson = jsonObject.getString("data");
	            JSONArray jsonArray = JSONArray.fromObject(dataJson);
	            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
	            if (fbpDto != null && !fbpDto.isEmpty()) {
					for (FileBizParamDto fileBizParamDto : fbpDto) {
						if(ImageConstants.ATICLE_FILE.equals(fileBizParamDto.getCode())){
							News editNew = new News();
							editNew.setId(news.getId());
							editNew.setPicPath(fbpDto.get(0).getImgData());
							newsService.editNews(editNew);
						}
						if (ImageConstants.UBB_FILE.equals(fileBizParamDto.getCode()) 
	    						&& Long.valueOf(fileBizParamDto.getTableId()).longValue() == news.getId()) {
							News articleContent = new News();
							articleContent.setId(news.getId());
							articleContent.setArticleContent(fileBizParamDto.getImgData());
							newsService.editNews(articleContent);
						}
					}
				}
			}
			//2.新增该文章所对应的文章分类
			if (StringUtils.isNotBlank(categoryItem)) {
				String[] categoryIds = categoryItem.split(",");
				for (String id : categoryIds) {
					Long categoryId = Long.valueOf(id);
					//2.1查询文章分类是否存在
					ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(categoryId);
					AssertUtil.notNull(articleCategory,  "文章分类主键");
					//2.2查询该文章分类中最大的排序
					Integer sort = articleCategoryNewsReferenceService.getMaxSortByArticleCategoryId(categoryId);
					ArticleCategoryNewsReference acnr = new ArticleCategoryNewsReference();
					acnr.setNewsId(news.getId());
					acnr.setArticleCategoryId(categoryId);
					acnr.setSort(sort+INDEX);
					//2.3新增文章分类与文章的关联关系
					articleCategoryNewsReferenceService.addArticleCategoryNewsReference(acnr);
					AssertUtil.notNull(acnr.getId(), "文章分类与文章的关联关系主键");
				}
			}
		} catch (Exception e) {
			//回滚TFS
        	if(null != list){
        		imageService.remove(list);
        	}
			throw new NewsException(e);
		}

	}

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editNews(News news, String categoryItem,Long userId) throws NewsException {
		  List<FileBizParamDto> addlist = new ArrayList<FileBizParamDto>();
		  String picPath =  news.getPicPath();
		try {
			AssertUtil.notNull(news.getId());
			news.setUpdateTime(new Date());
			//根据发布类型设定相应内容
			this._editAticle(news);
			newsService.editNews(news);
			//1.编辑文章
			newsService.editNews(news);
            //新增图片
            if(StringUtils.isNotBlank(picPath)){
            	FileBizParamDto dto = new FileBizParamDto(ARTICLE_SIZE, userId, news.getShopNo(), "NEWS", news.getId().toString(), ImageConstants.ATICLE_FILE, 1);
	            dto.setImgData(news.getPicPath());
	            addlist.add(dto);
            }
            //封装文章内容UBB图片
            if(StringUtils.isNotBlank(news.getArticleContent())){
		        FileBizParamDto ubbDto = new FileBizParamDto(null, userId, news.getShopNo(), "NEWS", news.getId().toString(), ImageConstants.UBB_FILE, 1);
		        ubbDto.setImgData(news.getArticleContent());
		        addlist.add(ubbDto);
            }
            //上传并更新图片
            if(null != addlist && addlist.size() > 0){
	            String flag = imageService.addOrEdit(addlist);
	            
	            JSONObject jsonObject = JSONObject.fromObject(flag);
	            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
	            String dataJson = jsonObject.getString("data");
	            JSONArray jsonArray = JSONArray.fromObject(dataJson);
	            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
				
	            if (null != fbpDto && !fbpDto.isEmpty()) {
					for (FileBizParamDto fileBizParamDto : fbpDto) {
						if(ImageConstants.ATICLE_FILE.equals(fileBizParamDto.getCode())){
							News newsImg = new News();
							newsImg.setId(news.getId());
							newsImg.setPicPath(fbpDto.get(0).getImgData());
							newsService.editNews(newsImg);
						}

						if (ImageConstants.UBB_FILE.equals(fileBizParamDto.getCode()) 
	    						&& Long.valueOf(fileBizParamDto.getTableId()).longValue() == news.getId()) {
							News articleContent = new News();
							articleContent.setId(news.getId());
							articleContent.setArticleContent(fileBizParamDto.getImgData());
							newsService.editNews(articleContent);
						}
					}
				}
            }
            
			//2.编辑文章对应的文章分类
			//2.1删除该文章以前对应的文章分类
			articleCategoryNewsReferenceService.deleteArticleCategoryNewsReferenceByArticleId(news.getId());
			if(StringUtils.isNotBlank(categoryItem)) {
				//2.2新增该文章所对应的分类
				if (StringUtils.isNotBlank(categoryItem)) {
					String[] categoryIds = categoryItem.split(",");
					for (String id : categoryIds) {
						Long categoryId = Long.valueOf(id);
						//2.2.1查询文章分类是否存在
						ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(categoryId);
						AssertUtil.notNull(articleCategory);
						//2.2.2查询该文章分类中最大的排序
						Integer sort = articleCategoryNewsReferenceService.getMaxSortByArticleCategoryId(categoryId);
						ArticleCategoryNewsReference acnr = new ArticleCategoryNewsReference();
						acnr.setNewsId(news.getId());
						acnr.setArticleCategoryId(categoryId);
						acnr.setSort(sort+INDEX);
						//2.2.3新增文章分类与文章关联关系
						articleCategoryNewsReferenceService.addArticleCategoryNewsReference(acnr);
						AssertUtil.notNull(acnr.getId(), "ArticleCategoryNewsReferenceId");
					}
				}
			}
		} catch (Exception e) {
		 	//回滚TFS
        	if(null != addlist){
        		imageService.remove(addlist);
        	}
			throw new NewsException(e);
		}

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public String deleteNews(Long id,Long userId) throws NewsException {
		try {
			AssertUtil.notNull(id,  "文章主键");
			News news =newsService.getNewsById(id);
			AssertUtil.notNull(news, "没有找该文章");
			
			List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
	         //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto(null, userId, news.getShopNo(), "NEWS", news.getId().toString(), ImageConstants.ATICLE_FILE, 1);
            list.add(dto);
            
            FileBizParamDto ubbdto = new FileBizParamDto(null, userId, news.getShopNo(), "NEWS", news.getId().toString(), ImageConstants.UBB_FILE, 1);
            list.add(ubbdto);
            
            imageService.remove(list);
			
			//1.删除该文章对应的文章分类与文章的关联关系
			articleCategoryNewsReferenceService.deleteArticleCategoryNewsReferenceByArticleId(id);
			//2.删除文章
			newsService.deleteNews(id);
			
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}

	}
	
	@Override
	public void delBatchNews(String articleItem,Long userId) throws NewsException {
		try {
			AssertUtil.notNull(articleItem,  "文章主键列表");
			JSONArray articleIdArray = JSONArray.fromObject(articleItem);
			for (int i = 0; i < articleIdArray.size(); i++) {
				Long articleId = articleIdArray.getLong(i);
				
				//删除文章
				this.deleteNews(articleId,userId);
			}
		} catch (Exception e) {
			throw new NewsException(e);
		}
		
	}

	@Override
	public Pagination<NewsDto> findNewsPageList(NewsDto dto,
			Pagination<NewsDto> pagination) throws NewsException {
		try {
			AssertUtil.notNull(pagination, "分页器");
			//查询文章分页列表
			return newsService.findNewsPageListByCondition(dto, pagination);
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public News getNewsById(Long id) throws NewsException{
		try {
			AssertUtil.notNull(id,  "文章主键");
			//根据主键查询文章
			News news = newsService.getNewsById(id);
			//编辑超链接
			if(StringUtils.isNotBlank(news.getHyperlink())) {
				if(news.getHyperlink().indexOf("http://") == -1){
					if (news.getHyperlink().indexOf("https://") == -1) {
						String newLisk = "http://" + news.getHyperlink();
						news.setHyperlink(newLisk);
					}
				}
			}
			return news;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}
	
	@Override
	public NewsDto getNewsDtoById(Long id) {
		NewsDto newsDto = newsService.getNewsDtoById(id);
		List<ArticleCategory> articleCategorys = articleCategoryService.findArticleCategoryListByArticleId(id);
		StringBuffer bindCategoryId = new StringBuffer();
		for (ArticleCategory articleCategory : articleCategorys) {
			bindCategoryId.append(articleCategory.getId()).append(",");
		}
		newsDto.setBindCategoryId(bindCategoryId.toString());
		return newsDto;
	}
	
	/******************** V.5.0 文章列表排序删除 开始 *****************/
	/*@Override
	public void sortNews(Long upId, Long downId) throws NewsException {
		Date date = new Date();
		try {
			AssertUtil.notNull(upId, NewsException.class, NewsExceptionEnum.NEWS_10001, "upId");
			AssertUtil.notNull(downId, NewsException.class, NewsExceptionEnum.NEWS_10001, "downId");
			//查询交换sort的文章
			News upNews = newsService.getNewsById(upId);
			News downNews = newsService.getNewsById(downId);
			Integer upSort = upNews.getSort();
			Integer downSort = downNews.getSort();
			//交换文章sort
			upNews.setSort(downSort);
			upNews.setUpdateTime(date);
			downNews.setSort(upSort);
			downNews.setUpdateTime(date);
			newsService.editNews(upNews);
			newsService.editNews(downNews);
		} catch (Exception e) {
			throw new NewsException(e);
		}

	}*/
	/******************** V.5.0 文章列表排序删除 结束 *****************/
	
	@Override
	public Pagination<NewsDto> findNewsPageListPreview(NewsDto dto,
			Pagination<NewsDto> pagination) {
		try {
			AssertUtil.notNull(pagination, "分页器");
			//查询文章分页列表
			pagination = newsService.findNewsPageList(dto, pagination);
			//编辑链接
			if(null != pagination.getDatas() && 0 < pagination.getDatas().size()){
				this._editHyperlink(pagination);
			}
			return pagination;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}
	
	@Override
	public Pagination<NewsDto> findNewsPageListForApp(NewsDto dto,
			Pagination<NewsDto> pagination) {
		if(null != dto.getGroupId()){
			//模块配置的文章列表
			pagination = newsService.findNewsPageListForModular(dto,pagination);
		}else {
			//查询文章列表
			pagination = newsService.findNewsPageList(dto, pagination);
		}
		//编辑链接
		this._editHyperlink(pagination);
		return pagination;
	}

	@Override
	public List<News> findNewsByShopNo(String shopNo) {
		try {
			AssertUtil.notNull(shopNo,  "店铺编号");
			//根据店铺编号查询文章列表
			List<News> newsList = newsService.findNewsByShopNo(shopNo);
			return newsList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}
	
	@Override
	public List<News> findNewsListByArticleCategoryId(Long id) {
		try {
			AssertUtil.notNull(id, "文章分类主键");
			//根据店铺编号查询文章列表
			List<News> newsList = newsService.findNewsListByArticleCategoryId(id);
			return newsList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}
	
	@Override
	public List<News> findNewsListByArticleCategoryIdForApp(Long id) {
		try {
			AssertUtil.notNull(id,  "文章分类主键");
			//根据店铺编号查询文章列表
			List<News> newsList = newsService.findNewsListByArticleCategoryId(id);
//			for (News news : newsList) {
//				if(StringUtils.isNotBlank(news.getPicPath())) {
//					String picPath = ImageUtils.getZoomImagePath(news.getPicPath(), ARTICLE_SIZE);
//					news.setPicPath(picPath);
//				}
//			}
			return newsList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}
	
	
	@Override
	public List<News> findNewsListByBindArticleId(String bindArticleIds) throws NewsException {
		
		try {
			AssertUtil.hasLength(bindArticleIds, "绑定的文章主键");
			List<News> newsList = new ArrayList<News>();
			//查询文章列表
			if (StringUtils.isNotBlank(bindArticleIds)) {
				String[] articleIds = bindArticleIds.split(",");
				for (String id : articleIds) {
					Long articleId = Long.valueOf(id);
					News news = this.getNewsById(articleId);
					newsList.add(news);
				}
			}
			return newsList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public List<News> findNewsList(News news) {
		try {
			AssertUtil.notNull(news, "文章信息");
			//查询文章列表
			return newsService.findNewsList(news);
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public List<News> findNewsListByBindArticleIdForPreview(String bindArticleIds) throws NewsException {
		try {
			List<News> newsList = new ArrayList<News>();
			//查询文章列表
			if (StringUtils.isNotBlank(bindArticleIds)) {
				String[] articleIds = bindArticleIds.split(",");
				for (String id : articleIds) {
					Long articleId = Long.valueOf(id);
					News news = this.getNewsById(articleId);
//					if(StringUtils.isNotBlank(news.getPicPath())) {
//						String picPath = ImageUtils.getZoomImagePath(news.getPicPath(), ARTICLE_SIZE);
//						news.setPicPath(picPath);
//					}
					newsList.add(news);
				}
			}
			return newsList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}
	
	/**
	 * 编辑文章内容
	 * @Title: _editAticle 
	 * @Description: (编辑文章内容) 
	 * @param news
	 * @date 2014年7月30日 下午1:53:33  
	 * @author Administrator
	 */
	private void _editAticle(News news) {
		switch(news.getType()){
		case 1:
			news.setHyperlink(" ");
			break;
		case 2:
			news.setArticleContent(" ");
			break;
		default :
			break;
		}
	}
	
	/**
	 * 编辑链接
	 * @Title: _editHyperlink 
	 * @Description: (编辑链接) 
	 * @param datas
	 * @date 2014年7月30日 下午5:24:53  
	 * @author Administrator
	 */
	private void _editHyperlink(Pagination<NewsDto> pagination) {
		for(News news : pagination.getDatas()){
			if(null != news.getHyperlink() && Constants.ARTICLE_TYPE_LINK.equals(news.getType().intValue())){
				if(news.getHyperlink().indexOf("http://") == -1){
					String newLisk = "http://" + news.getHyperlink();
					news.setHyperlink(newLisk);
				}
			}
		}	
	}

	@Override
	public List<ArticleCategoryVo> checkedNewsByNewsId(String shopNo, Long newsId) throws NewsException{
		try {
			AssertUtil.notNull(shopNo, "shopNo为空");
			AssertUtil.notNull(newsId, "newsId为空");
			List<ArticleCategoryVo> acdList = new ArrayList<ArticleCategoryVo>();
			List<ArticleCategory>  acList = articleCategoryService.findArticleCategoryListByShopNo(shopNo);
			List<ArticleCategoryNewsReference> acnrList = articleCategoryNewsReferenceService.findArticleCategoryNewsReferenceByNewsId(newsId);
			Map<Long,ArticleCategoryNewsReference> acnrMap  = new HashMap<Long, ArticleCategoryNewsReference>();
			for (ArticleCategoryNewsReference acnr : acnrList) {
				acnrMap.put(acnr.getArticleCategoryId(), acnr);
			}
			
			for (ArticleCategory ac : acList) {
				ArticleCategoryVo acv = new ArticleCategoryVo();
				acv.setId(ac.getId());
				acv.setName(ac.getName());
				if(acnrMap.containsKey(ac.getId())){
					acv.setFlag("1");
				}else{
					acv.setFlag("0");
				}
				acdList.add(acv);
			}
			return acdList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public List<NewsVo> checkNewsByCategoryId(String shopNo, Long categoryId,String title)
			throws NewsException {
		try {
			AssertUtil.notNull(shopNo, "shopNo为空");
			//AssertUtil.notNull(categoryId, "categoryId为空");
			
			List<NewsVo> nvList = new ArrayList<NewsVo>();
			
			News queryNews = new News();
			queryNews.setShopNo(shopNo);
			queryNews.setTitle(title);
			List<News> newsList = newsService.findNewsList(queryNews);
			
			List<ArticleCategoryNewsReference> acnrList = articleCategoryNewsReferenceService.findArticleCategoryNewsReferenceByArticleCategoryId(categoryId);
			
			Map<Long,ArticleCategoryNewsReference> acnrMap  = new HashMap<Long, ArticleCategoryNewsReference>();
			for (ArticleCategoryNewsReference acnr : acnrList) {
				acnrMap.put(acnr.getNewsId(), acnr);
			}
			
			for (News news : newsList) {
				NewsVo nv = new NewsVo();
				nv.setId(news.getId());
				nv.setTitle(news.getTitle());
				if(acnrMap.containsKey(news.getId())){
					nv.setFlag("1");
				}else{
					nv.setFlag("0");
				}
				nvList.add(nv);
			}
			return nvList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "NewsException" })
	public String delAllArticle(String delId,Long userId) throws NewsException {
		try {
			AssertUtil.notNull(userId,  "参数为空");
			
			if(StringUtils.isNotBlank(delId)) {
				String[] articleIds = delId.split(",");
				for (int i = articleIds.length-1; i >=0; i--) {
					Long articleId = Long.valueOf(articleIds[i]);
					
					News news =newsService.getNewsById(articleId);
					AssertUtil.notNull(news, "没有找该文章");
					
					List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
			         //上传图片到TFS
			        FileBizParamDto dto = new FileBizParamDto();
			        dto.setImgData(news.getPicPath());
			        dto.setUserId(userId);
			        dto.setShopNo(news.getShopNo());
			        dto.setTableName("NEWS");
			        dto.setTableId(news.getId().toString());
			        dto.setCode(ImageConstants.ATICLE_FILE);
			        list.add(dto);
			        
			        imageService.remove(list);
					
					//1.删除该文章对应的文章分类与文章的关联关系
					articleCategoryNewsReferenceService.deleteArticleCategoryNewsReferenceByArticleId(articleId);
					//2.删除文章
					newsService.deleteNews(articleId);
					
				}
			}
			return "SUCCESS";
		} catch (Exception e) {
			throw new NewsException(e);
		}

	}

	@Override
	public List<NewsDto> findNewsAndSortByCategoryId(Long categoryId)
			throws NewsException {
		try {
			AssertUtil.notNull(categoryId, "参数为空");
			List<NewsDto> newsList= newsService.findNewsAndSortByCategoryId(categoryId);
			for (NewsDto newsDto : newsList) {
				//编辑超链接
				if(StringUtils.isNotBlank(newsDto.getHyperlink())) {
					if(newsDto.getHyperlink().indexOf("http://") == -1){
						String newLisk = "http://" + newsDto.getHyperlink();
						newsDto.setHyperlink(newLisk);
					}
				}
			}
			return newsList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "NewsException" })
	public String sortNewByCategoryId(Long categoryId, Integer sort,
			Integer type) throws NewsException {
		try {
			AssertUtil.notNull(categoryId, "专题ID为空");
			AssertUtil.notNull(sort, "sort为空");
			AssertUtil.notNull(type, "type为空");
			
			Map<String,NewsDto> newMap = new HashMap<String, NewsDto>();
			List<NewsDto> newsDtoList = newsService.findNewsAndSortByCategoryId(categoryId);
			if(null == newsDtoList || 1 >= newsDtoList.size()){
				return "SUCCESS";
			}
			
			for (int i=0;i<=newsDtoList.size()-1;i++) {
				if(sort.equals(newsDtoList.get(i).getSort())){
					if(0 == i && 1 == type.intValue()){
						return "SUCCESS";
					}
					if(newsDtoList.size()-1 == i && 2 == type.intValue()){
						return "SUCCESS";
					}
					
					if(1 == type.intValue()){
						newMap.put("up", newsDtoList.get(i-1));
						newMap.put("down", newsDtoList.get(i));
						continue;
					}
					if(2 == type.intValue()){
						newMap.put("up", newsDtoList.get(i));
						newMap.put("down", newsDtoList.get(i+1));
						continue;
					}
					if(3 == type.intValue()){
						newMap.put("up", newsDtoList.get(0));
						newMap.put("down", newsDtoList.get(i));
						continue;
					}
					if(4 == type.intValue()){
						newMap.put("up", newsDtoList.get(i));
						newMap.put("down", newsDtoList.get(newsDtoList.size()-1));
						continue;
					}
					
				}
			}
			
			ArticleCategoryNewsReference acnr = new ArticleCategoryNewsReference();
			acnr.setId(newMap.get("up").getRelationId());
			acnr.setSort(newMap.get("down").getSort());
			articleCategoryNewsReferenceService.editArticleCategoryNewsReference(acnr);
			acnr.setId(newMap.get("down").getRelationId());
			acnr.setSort(newMap.get("up").getSort());
			articleCategoryNewsReferenceService.editArticleCategoryNewsReference(acnr);
			
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "排序失败";
		}
	}

	@Override
	public String soertNew(Long subId, Long tarId) throws NewsException {
		try {
			AssertUtil.notNull(subId, "参数为空");
			AssertUtil.notNull(tarId, "参数为空");
			ArticleCategoryNewsReference up = articleCategoryNewsReferenceService.getArticleCategoryNewsReference(subId);
			ArticleCategoryNewsReference down = articleCategoryNewsReferenceService.getArticleCategoryNewsReference(tarId);
			
			ArticleCategoryNewsReference acnr = new ArticleCategoryNewsReference();
			acnr.setId(up.getId());
			acnr.setSort(down.getSort());
			articleCategoryNewsReferenceService.editArticleCategoryNewsReference(acnr);
			acnr.setId(down.getId());
			acnr.setSort(up.getSort());
			articleCategoryNewsReferenceService.editArticleCategoryNewsReference(acnr);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}
}
