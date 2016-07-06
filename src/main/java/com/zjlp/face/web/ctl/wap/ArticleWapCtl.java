package com.zjlp.face.web.ctl.wap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.product.material.bussiness.AricleColumnBusiness;
import com.zjlp.face.web.server.product.material.bussiness.ArticleCategoryBusiness;
import com.zjlp.face.web.server.product.material.bussiness.NewsBusiness;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;

@Controller
@RequestMapping("/wap/{shopNo}/any/article/")
public class ArticleWapCtl extends WapCtl{
	
	@Autowired
	private NewsBusiness newsBusiness;
	
	@Autowired
	private ArticleCategoryBusiness articleCategoryBusiness;
	
	@Autowired
	private AricleColumnBusiness aricleColumnBusiness;
	
	

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model){
		
	
		return "/wap/product/";
	}
	
	@RequestMapping(value = "news", method = RequestMethod.GET)
	public String news(Long id,Model model){
		News news = newsBusiness.getNewsById(id);
		model.addAttribute("news",news);
		return "/wap/product/article/details";
	}
	
	@RequestMapping(value = "category", method = RequestMethod.GET)
	public String category(Long id,Model model){
		
		ArticleCategory articleCategory = articleCategoryBusiness.getArticleCategoryById(id);
		articleCategory.setPicPath(ImageConstants.getCloudstZoomPath(articleCategory.getPicPath(), "640_380"));
		List<NewsDto> newsDtoList = newsBusiness.findNewsAndSortByCategoryId(articleCategory.getId());
		for (NewsDto newsDto : newsDtoList) {
			newsDto.setPicPath(ImageConstants.getCloudstZoomPath(newsDto.getPicPath(), "400_400"));
		}
		model.addAttribute("newsDtoList",newsDtoList);
		model.addAttribute("category",articleCategory);
		if("1".equals(articleCategory.getArticleTemplateType())){
			return "/wap/product/template/article_topic1/index";
		}
		if("2".equals(articleCategory.getArticleTemplateType())){
			return "/wap/product/template/article_topic2/index";
		}
		if("3".equals(articleCategory.getArticleTemplateType())){
			return "/wap/product/template/article_topic3/index";
		}
		return "/wap/product/template/article_topic1/index";
	}
	
	@RequestMapping(value = "column", method = RequestMethod.GET)
	public String column(Long id,Model model){
		
		AricleColumn aricleColumn = aricleColumnBusiness.getAricleColumn(id);
		List<ArticleCategoryDto> acdList = articleCategoryBusiness.findCategoryAndSortByColumn(aricleColumn.getId());
		aricleColumn.setPicPath(ImageConstants.getCloudstZoomPath(aricleColumn.getPicPath(), "640_380"));
		for (ArticleCategoryDto articleCategoryDto : acdList) {
			articleCategoryDto.setPicPath(ImageConstants.getCloudstZoomPath(articleCategoryDto.getPicPath(), "640_380"));
		}
		model.addAttribute("column",aricleColumn);
		model.addAttribute("acdList",acdList);
		if("1".equals(aricleColumn.getArticleTemplateType())){
			return "/wap/product/template/article_column1/index";
		}
		if("2".equals(aricleColumn.getArticleTemplateType())){
			return "/wap/product/template/article_column2/index";
		}
		if("3".equals(aricleColumn.getArticleTemplateType())){
			return "/wap/product/template/article_column3/index";
		}
		if("4".equals(aricleColumn.getArticleTemplateType())){
			return "/wap/product/template/article_column4/index";
		}
		
		return "/wap/product/template/article_column1/index";
	}
	
}
