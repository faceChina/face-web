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
import com.zjlp.face.web.server.product.material.bussiness.ArticleCategoryBusiness;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.ArticleCategoryAricleColumnReference;
import com.zjlp.face.web.server.product.material.domain.ArticleCategoryNewsReference;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;
import com.zjlp.face.web.server.product.material.domain.vo.ArticleCategoryVo;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryAricleColumnReferenceService;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryNewsReferenceService;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryService;
import com.zjlp.face.web.server.product.material.service.NewsService;

@Service
public class ArticleCategoryBusinessImpl implements ArticleCategoryBusiness {
	
	@Autowired
	private ArticleCategoryService articleCategoryService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private ArticleCategoryNewsReferenceService articleCategoryNewsReferenceService;
	@Autowired
	private ArticleCategoryAricleColumnReferenceService articleCategoryAricleColumnReferenceService;
	
	@Autowired(required=false)
	private ImageService imageService;
	//文章分类封面图片大小
	private static final String ARTICLE_CATEGORY_SIZE = "640_380";
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "NewsException" })
	public void addArticleCategory(ArticleCategoryDto articleCategoryDto,Long userId) throws NewsException {
		Date date = new Date();
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		try {
			AssertUtil.notNull(articleCategoryDto, "文章分类内容");
			ArticleCategory articleCategory = this._generateArticleCategory(articleCategoryDto, date);
			//1.新增文章分类
			articleCategoryService.addArticleCategory(articleCategory);
			AssertUtil.notNull(articleCategory.getId(),  "文章分类主键");
			//2.新增文章分类与文章的关联关系
			if(StringUtils.isNotBlank(articleCategoryDto.getArticleItem())) {
				String[] categoryIds = articleCategoryDto.getArticleItem().split(",");
				for (int i = 0; i < categoryIds.length; i++) {
					Long articleId = Long.valueOf(categoryIds[i]);
					//2.1查询文章是否存在
					News news = newsService.getNewsById(articleId);
					AssertUtil.notNull(news,  "文章主键");
					//2.2新增文章分类与文章关联关系
					ArticleCategoryNewsReference acnr = new ArticleCategoryNewsReference();
					acnr.setNewsId(articleId);
					acnr.setArticleCategoryId(articleCategory.getId());
					acnr.setSort(i);
					articleCategoryNewsReferenceService.addArticleCategoryNewsReference(acnr);
				}
			}
			
            //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto();
            dto.setImgData(articleCategory.getPicPath());
            dto.setZoomSizes(ARTICLE_CATEGORY_SIZE);
            dto.setUserId(userId);
            dto.setShopNo(articleCategory.getShopNo());
            dto.setTableName("ARTICLE_CATEGORY");
            dto.setTableId(articleCategory.getId().toString());
            dto.setCode(ImageConstants.ATICLE_CATEGORY_FILE);
            dto.setFileLabel(1);
            list.add(dto);
            String flag = imageService.addOrEdit(list);
			
            
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
          
            ArticleCategory editArticleCategory = new ArticleCategory();
            editArticleCategory.setId(articleCategory.getId());
            editArticleCategory.setPicPath(fbpDto.get(0).getImgData());
            articleCategoryService.editArticleCategory(editArticleCategory);
            
			
		} catch (Exception e) {
		 	//回滚TFS
        	if(null != list){
        		imageService.remove(list);
        	}
			throw new NewsException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "NewsException" })
	public String deleteArticleCategory(Long articleCategoryId,Long userId) throws NewsException{
		try {
			AssertUtil.notNull(articleCategoryId,  "文章分类主键");
			
			ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(articleCategoryId);
			AssertUtil.notNull(articleCategory, "没有找该文章专题");
			
			List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
	         //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto();
            dto.setImgData(articleCategory.getPicPath());
            dto.setUserId(userId);
            dto.setShopNo(articleCategory.getShopNo());
            dto.setTableName("ARTICLE_CATEGORY");
            dto.setTableId(articleCategory.getId().toString());
            dto.setCode(ImageConstants.ATICLE_CATEGORY_FILE);
            list.add(dto);
            
            imageService.remove(list);
			
			//1.删除该文章对应的文章分类与文章的关联关系
			articleCategoryNewsReferenceService.deleteArticleCategoryNewsReferenceByArticleCategoryId(articleCategoryId);
			//2.删除文章分类
			articleCategoryService.deleteArticleCategoryByArticleCategoryId(articleCategoryId);;
			return "SUCCESS";
		} catch (Exception e) {
			throw new NewsException(e);
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "NewsException" })
	public void editArticleCategory(ArticleCategoryDto articleCategoryDto,Long userId) throws NewsException{
		Date date = new Date();
		List<FileBizParamDto> addlist = new ArrayList<FileBizParamDto>();
		try {
			AssertUtil.notNull(articleCategoryDto.getId(),  "文章分类主键");
			//文章分类主键
			
			ArticleCategory queryArticleCategory = articleCategoryService.getArticleCategoryById(articleCategoryDto.getId());
			AssertUtil.notNull(queryArticleCategory,  "没有找到该文章分类");
			ArticleCategory articleCategory = this._generateArticleCategory(articleCategoryDto, date);
			if(null != articleCategoryDto.getPicPath()){
				List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		         //删除图片资源
	            FileBizParamDto dto = new FileBizParamDto();
	            dto.setImgData(queryArticleCategory.getPicPath());
	            dto.setUserId(userId);
	            dto.setShopNo(queryArticleCategory.getShopNo());
	            dto.setTableName("ARTICLE_CATEGORY");
	            dto.setTableId(queryArticleCategory.getId().toString());
	            dto.setCode(ImageConstants.ATICLE_CATEGORY_FILE);
	            list.add(dto);
	            imageService.remove(list);
	            
	            
	            //新增图片
	            dto.setImgData(articleCategoryDto.getPicPath());
	            dto.setZoomSizes(ARTICLE_CATEGORY_SIZE);
	            dto.setFileLabel(1);
	          
	            addlist.add(dto);
	            
	            String flag = imageService.addOrEdit(addlist);
	            
	            JSONObject jsonObject = JSONObject.fromObject(flag);
	            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
	            String dataJson = jsonObject.getString("data");
	            JSONArray jsonArray = JSONArray.fromObject(dataJson);
	            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
				
	            articleCategory.setPicPath(fbpDto.get(0).getImgData());
			}
			
			//1.编辑文章分类
			articleCategoryService.editArticleCategory(articleCategory);
	
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public Pagination<ArticleCategoryDto> findArticleCategoryPageList(
			ArticleCategoryDto articleCategoryDto,
			Pagination<ArticleCategoryDto> pagination) throws NewsException {
		try {
			AssertUtil.notNull(pagination,  "分页器");
			AssertUtil.hasLength(articleCategoryDto.getShopNo(),  "店铺编号");
			//查询文章分类分页列表
			pagination = articleCategoryService.findArticleCategoryPageList(articleCategoryDto, pagination);
			for (ArticleCategoryDto acDto : pagination.getDatas()) {
				//查询文章分类对应的文章
				List<News> newsList = newsService.findNewsListByArticleCategoryId(acDto.getId());
				if(0!=newsList.size()) {
					StringBuffer bindIds = new StringBuffer();
					for (News news : newsList) {
						bindIds.append(news.getId()).append(",");
					}
					acDto.setArticleItem(bindIds.toString());
				}
				//设置文章分类包含的文章数量
				acDto.setArticleCount(newsList.size());
				//设置模板名称
				acDto.setTemplateName(this._generateTemplateName(acDto.getArticleTemplateType()));
				acDto.setNewsList(newsList);
			}
			return pagination;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public List<ArticleCategory> findArticleCategoryListByShopNo(String shopNo) {
		try {
			AssertUtil.hasLength(shopNo,  "店铺编号");
			//根据店铺编号查询文章分类列表
			return articleCategoryService.findArticleCategoryListByShopNo(shopNo);
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public List<ArticleCategory> findArticleCategoryListByArticleId(Long articleId) {
		try {
			AssertUtil.notNull(articleId, "文章主键");
			//根据文章主键查询文章分类列表
			return articleCategoryService.findArticleCategoryListByArticleId(articleId);
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}
	
	@Override
	public ArticleCategory getArticleCategoryById(Long id) throws NewsException{
		try {
			AssertUtil.notNull(id,   "文章分类主键");
			//根据文章分类主键查询文章分类
			return articleCategoryService.getArticleCategoryById(id);
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public ArticleCategory getArticleCategoryByIdForApp(Long id) throws NewsException{
		try {
			AssertUtil.notNull(id,  "文章分类主键");
			//根据文章分类主键查询文章分类
			ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(id);
			if(null == articleCategory) {
				return null;
			}
//			if(StringUtils.isNotBlank(articleCategory.getPicPath())) {
//				String picPath = ImageUtils.getZoomImagePath(articleCategory.getPicPath(), ARTICLE_CATEGORY_SIZE);
//				articleCategory.setPicPath(picPath);
//			}
			return articleCategory;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}
	
	//初始化文章分类
	private ArticleCategory _generateArticleCategory(
			ArticleCategoryDto articleCategoryDto, Date date) {
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setName(articleCategoryDto.getName());
		articleCategory.setPicPath(articleCategoryDto.getPicPath());
		articleCategory.setShopNo(articleCategoryDto.getShopNo());
		articleCategory.setArticleTemplateType(articleCategoryDto.getArticleTemplateType());
		articleCategory.setUpdateTime(date);
		if(null != articleCategoryDto.getId()) {
			articleCategory.setId(articleCategoryDto.getId());
		} else {
			articleCategory.setStatus(Constants.VALID);
			articleCategory.setCreateTime(date);
		}
		
		return articleCategory;
	}
	
	//生成文章模板名称
	private String _generateTemplateName(String articleTemplateType) {
		String templateName = "";
		if(Constants.ACT_ONE.equals(articleTemplateType)) {
			templateName = "文章模板1";
		} else if(Constants.ACT_TWO.equals(articleTemplateType)) {
			templateName = "文章模板2";
		} else if(Constants.ACT_THREE.equals(articleTemplateType)) {
			templateName = "文章模板3";
		} else {
			templateName = "文章模板4";
		}
		return templateName;
	}

	@Override
	public List<ArticleCategoryVo> findArticleCategoryVoListByShopNo(
			String shopNo)throws NewsException{
		try {
			AssertUtil.notNull(shopNo, "shopNo为空");
			List<ArticleCategoryVo> acvList = new ArrayList<ArticleCategoryVo>();
			List<ArticleCategory> acList = articleCategoryService.findArticleCategoryListByShopNo(shopNo);
			 for (ArticleCategory articleCategory : acList) {
				 ArticleCategoryVo acv = new ArticleCategoryVo();
				 acv.setId(articleCategory.getId());
				 acv.setName(articleCategory.getName());
				 acv.setFlag("0");
				 acvList.add(acv);
			}
			 return acvList;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public List<ArticleCategoryVo> checkCategoryByColumnId(String shopNo,
			Long columnId,String title) throws NewsException {
		try {
			AssertUtil.notNull(shopNo, "参数为空");
			AssertUtil.notNull(columnId, "参数为空");
			
			List<ArticleCategoryVo> acList = new ArrayList<ArticleCategoryVo>();
			ArticleCategory queryArticleCategory = new ArticleCategory();
			queryArticleCategory.setShopNo(shopNo);
			queryArticleCategory.setName(title);
			List<ArticleCategory> articleCategoryList = articleCategoryService.findArticleCategoryList(queryArticleCategory);
			
			 List<ArticleCategoryAricleColumnReference> aacrList = articleCategoryAricleColumnReferenceService.findByAricleColumnId(columnId);
			
			Map<Long,ArticleCategoryAricleColumnReference> aacrMap  = new HashMap<Long, ArticleCategoryAricleColumnReference>();
			for (ArticleCategoryAricleColumnReference acnr : aacrList) {
				aacrMap.put(acnr.getArticleCategoryId(), acnr);
			}
			
			for (ArticleCategory articleCategory : articleCategoryList) {
				ArticleCategoryVo ac = new ArticleCategoryVo();
				ac.setId(articleCategory.getId());
				ac.setName(articleCategory.getName());
				if(aacrMap.containsKey(articleCategory.getId())){
					ac.setFlag("1");
				}else{
					ac.setFlag("0");
				}
				acList.add(ac);
			}
			return acList;
			
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public String updateNewsAndCategoryRelation(
			ArticleCategoryDto articleCategoryDto) throws NewsException {
		try {
			AssertUtil.notNull(articleCategoryDto, "参数为空");
			AssertUtil.notNull(articleCategoryDto.getId(), "参数为空");
			//2.编辑文章分类对应的文章
			//2.1删除该文章分类以前对应的文章
			articleCategoryNewsReferenceService.deleteArticleCategoryNewsReferenceByArticleCategoryId(
					articleCategoryDto.getId());
			if(StringUtils.isNotBlank(articleCategoryDto.getArticleItem())) {
				//2.2新增文章分类对应的文章
				if(StringUtils.isNotBlank(articleCategoryDto.getArticleItem())) {
					String[] categoryIds = articleCategoryDto.getArticleItem().split(",");
					Integer j = 0;
					for (int i = categoryIds.length-1; i >=0; i--) {
						Long articleId = Long.valueOf(categoryIds[i]);
						//2.1查询文章是否存在
						News news = newsService.getNewsById(articleId);
						AssertUtil.notNull(news,  "文章主键");
						//2.2新增文章分类与文章关联关系
						ArticleCategoryNewsReference acnr = new ArticleCategoryNewsReference();
						acnr.setNewsId(articleId);
						acnr.setArticleCategoryId(articleCategoryDto.getId());
						acnr.setSort(j++);
						articleCategoryNewsReferenceService.addArticleCategoryNewsReference(acnr);
						AssertUtil.notNull(news,  "文章分类与文章的关联关系主键");
					}
				}
			}
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "保存失败";
		}
	}

	@Override
	public List<ArticleCategoryDto> findCategoryAndSortByColumn(Long columnId)
			throws NewsException {
		try {
			AssertUtil.notNull(columnId, "栏目ID");
			return articleCategoryService.findCategoryAndSortByColumn(columnId);
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

}
