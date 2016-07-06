package com.zjlp.face.web.server.product.material.bussiness.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.NewsException;
import com.zjlp.face.web.server.product.material.bussiness.AricleColumnBusiness;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.ArticleCategoryAricleColumnReference;
import com.zjlp.face.web.server.product.material.domain.dto.AricleColumnDto;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;
import com.zjlp.face.web.server.product.material.service.AricleColumnService;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryAricleColumnReferenceService;
import com.zjlp.face.web.server.product.material.service.ArticleCategoryService;

@Service
public class AricleColumnBusinessImpl implements AricleColumnBusiness{

	@Autowired
	private AricleColumnService aricleColumnService;
	
	@Autowired
	private ArticleCategoryService articleCategoryService;
	
	@Autowired
	private ArticleCategoryAricleColumnReferenceService articleCategoryAricleColumnReferenceService;
	
	@Autowired(required=false)
	private ImageService imageService;
	//文章分类封面图片大小
	private static final String ARTICLE_COLUMN_SIZE = "640_380";
	
	
	@Override
	public void addAricleColumn(AricleColumnDto aricleColumnDto,Long userId) throws NewsException {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		try {
			AssertUtil.notNull(aricleColumnDto, "文章栏目");
			aricleColumnService.addAricleColumn(aricleColumnDto);
			AssertUtil.notNull(aricleColumnDto.getId(),  "文章栏目主键");
			
			if(StringUtils.isNotBlank(aricleColumnDto.getCategoryItem())) {
				String[] categoryIds = aricleColumnDto.getCategoryItem().split(",");
				for (int i = 0; i < categoryIds.length; i++) {
					Long categoryId = Long.valueOf(categoryIds[i]);
					ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(categoryId);
					
					AssertUtil.notNull(articleCategory,  "专题");
					
					ArticleCategoryAricleColumnReference acacr = new ArticleCategoryAricleColumnReference();
					acacr.setAricleColumnId(aricleColumnDto.getId());
					acacr.setArticleCategoryId(categoryId);
					acacr.setSort(i);
					articleCategoryAricleColumnReferenceService.addAricleColumnArticleCategoryReference(acacr);
				}
			}
			
			
		     //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto();
            dto.setImgData(aricleColumnDto.getPicPath());
            dto.setZoomSizes(ARTICLE_COLUMN_SIZE);
            dto.setUserId(userId);
            dto.setShopNo(aricleColumnDto.getShopNo());
            dto.setTableName("ARTICLE_CATEGORY");
            dto.setTableId(aricleColumnDto.getId().toString());
            dto.setCode(ImageConstants.ATICLE_COLUMN_FILE);
            dto.setFileLabel(1);
            list.add(dto);
            String flag = imageService.addOrEdit(list);
			
            
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
          
            AricleColumn aricleColumn = new AricleColumn();
            aricleColumn.setId(aricleColumnDto.getId());
            aricleColumn.setPicPath(fbpDto.get(0).getImgData());
            aricleColumnService.editAricleColumn(aricleColumn);
			
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
	public void editAricleColumn(AricleColumnDto aricleColumnDto,Long userId)
			throws NewsException {
		List<FileBizParamDto> addlist = new ArrayList<FileBizParamDto>();
		try {
			
			AssertUtil.notNull(aricleColumnDto.getId(),  "文章分类主键");
			AssertUtil.notNull(aricleColumnDto.getShopNo(),  "shopNo主键");
			
			AricleColumn queryAricleColumn = aricleColumnService.getAricleColumnById(aricleColumnDto.getId());
			AssertUtil.notNull(queryAricleColumn, "没有找到该文章分栏");
			
			if(null != aricleColumnDto.getPicPath()){
				List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		         //删除图片资源
	            FileBizParamDto dto = new FileBizParamDto();
	            dto.setImgData(queryAricleColumn.getPicPath());
	            dto.setUserId(userId);
	            dto.setShopNo(queryAricleColumn.getShopNo());
	            dto.setTableName("ARTICLE_COLUMN");
	            dto.setTableId(queryAricleColumn.getId().toString());
	            dto.setCode(ImageConstants.ATICLE_COLUMN_FILE);
	            list.add(dto);
	            imageService.remove(list);
	            
	            
	            //新增图片
	            dto.setImgData(aricleColumnDto.getPicPath());
	            dto.setZoomSizes(ARTICLE_COLUMN_SIZE);
	            dto.setFileLabel(1);
	          
	            addlist.add(dto);
	            
	            String flag = imageService.addOrEdit(addlist);
	            
	            JSONObject jsonObject = JSONObject.fromObject(flag);
	            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
	            String dataJson = jsonObject.getString("data");
	            JSONArray jsonArray = JSONArray.fromObject(dataJson);
	            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
				
	            aricleColumnDto.setPicPath(fbpDto.get(0).getImgData());
			}
			
			aricleColumnService.editAricleColumn(aricleColumnDto);
			
	
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "NewsException" })
	public String delAricleColumn(Long id,Long userId) throws NewsException {
		try {
			AssertUtil.notNull(id,  "文章栏目主键");
			AssertUtil.notNull(userId,"用户Id");
			
			AricleColumn aricleColumn = aricleColumnService.getAricleColumnById(id);
			AssertUtil.notNull(aricleColumn, "没有找该文章栏目");
			
			List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
	         //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto();
            dto.setImgData(aricleColumn.getPicPath());
            dto.setUserId(userId);
            dto.setShopNo(aricleColumn.getShopNo());
            dto.setTableName("ARTICLE_COLUMN");
            dto.setTableId(aricleColumn.getId().toString());
            dto.setCode(ImageConstants.ATICLE_COLUMN_FILE);
            list.add(dto);
            
            imageService.remove(list);
			
            articleCategoryAricleColumnReferenceService.deleteByAricleColumnId(id);
            aricleColumnService.delAricleColumn(id);
			return "SUCCESS";
			
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public List<AricleColumn> findAricleColumnByShopNo(String shopNo)
			throws NewsException {
	    List<AricleColumn> acdList = null;
		try {
			 AssertUtil.notNull(shopNo, "shopNo为空");
			 acdList = aricleColumnService.findAricleColumnByShopNo(shopNo);
		} catch (Exception e) {
			throw new NewsException(e);
		}
		return acdList;
	}

	@Override
	public Pagination<AricleColumnDto> findAricleColumnPageList(
			AricleColumnDto aricleColumnDto,
			Pagination<AricleColumnDto> pagination) throws NewsException {
		try {
			AssertUtil.notNull(pagination,  "分页器");
			AssertUtil.hasLength(aricleColumnDto.getShopNo(),  "店铺编号");
			pagination = aricleColumnService.findAricleColumnPageList(aricleColumnDto, pagination);
			for (AricleColumnDto acd : pagination.getDatas()) {
				List<ArticleCategoryDto> articleCategoryDtos = articleCategoryService.findCategoryAndSortByColumn(acd.getId());
				acd.setArticleCategoryDtos(articleCategoryDtos);
			}
			return pagination;
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public AricleColumn getAricleColumn(Long id) throws NewsException {
		try {
			AssertUtil.notNull(id, "主键为空");
			return aricleColumnService.getAricleColumnById(id);
		} catch (Exception e) {
			throw new NewsException(e);
		}
		
	}

	@Override
	public List<AricleColumnDto> findNewsAndSortByCategoryId(Long columnId)
			throws NewsException {
		try {
			AssertUtil.notNull(columnId, "栏目ID为空");
			return aricleColumnService.findCategoryAndSortByColumnId(columnId);
		} catch (Exception e) {
			throw new NewsException(e);
		}
	}

	@Override
	public String soertCategory(Long subId, Long tarId) throws NewsException {
		try {
			AssertUtil.notNull(subId, "参数为空");
			AssertUtil.notNull(tarId, "参数为空");
			ArticleCategoryAricleColumnReference up = articleCategoryAricleColumnReferenceService.getArticleCategoryAricleColumnReference(subId);
			ArticleCategoryAricleColumnReference down = articleCategoryAricleColumnReferenceService.getArticleCategoryAricleColumnReference(tarId);
			
			ArticleCategoryAricleColumnReference acacr = new ArticleCategoryAricleColumnReference();
			acacr.setId(up.getId());
			acacr.setSort(down.getSort());
			articleCategoryAricleColumnReferenceService.editAricleColumnArticleCategoryReference(acacr);
			acacr.setId(down.getId());
			acacr.setSort(up.getSort());
			articleCategoryAricleColumnReferenceService.editAricleColumnArticleCategoryReference(acacr);
			return "1";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}

	@Override
	public String updateColumnAndCategory(AricleColumnDto aricleColumnDto) throws NewsException {
		try {
			AssertUtil.notNull(aricleColumnDto, "参数为空");
			AssertUtil.notNull(aricleColumnDto.getId(), "参数为空");
			articleCategoryAricleColumnReferenceService.deleteByAricleColumnId(aricleColumnDto.getId());
			
			if(StringUtils.isNotBlank(aricleColumnDto.getCategoryItem())) {
				String[] categoryIds = aricleColumnDto.getCategoryItem().split(",");
				for (int i = 0; i < categoryIds.length; i++) {
					Long categoryId = Long.valueOf(categoryIds[i]);
					ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(categoryId);
					
					AssertUtil.notNull(articleCategory,  "专题");
					
					ArticleCategoryAricleColumnReference acacr = new ArticleCategoryAricleColumnReference();
					acacr.setAricleColumnId(aricleColumnDto.getId());
					acacr.setArticleCategoryId(categoryId);
					acacr.setSort(i);
					articleCategoryAricleColumnReferenceService.addAricleColumnArticleCategoryReference(acacr);
				}
			}
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "保存失败";
		}
	}

}
