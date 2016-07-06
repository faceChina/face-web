package com.zjlp.face.web.ctl;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.bussiness.AricleColumnBusiness;
import com.zjlp.face.web.server.product.material.bussiness.ArticleCategoryBusiness;
import com.zjlp.face.web.server.product.material.bussiness.NewsBusiness;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.AricleColumnDto;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;
import com.zjlp.face.web.server.product.material.domain.vo.ArticleCategoryVo;
import com.zjlp.face.web.server.product.material.domain.vo.NewsVo;

@Controller
@RequestMapping("/u/stuff/article/")
public class ArticleCtl extends BaseCtl{

	@Autowired
	private NewsBusiness newsBusiness;
	
	@Autowired
	private ArticleCategoryBusiness articleCategoryBusiness;
	
	@Autowired
	private AricleColumnBusiness aricleColumnBusiness;
	
	
	
	
	@RequestMapping(value="listArticle")
	public String articleList(NewsDto newsDto,Pagination<NewsDto> pagination,Model model){
		newsDto.setShopNo(super.getShopNo());
		pagination = newsBusiness.findNewsPageList(newsDto, pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("soso", newsDto.getSoso());
		return "/m/article/article-list";
	}
	
	
	@RequestMapping(value="addArticle",method = RequestMethod.GET)
	public String addArticle(Model model){
		List<ArticleCategoryVo> acList = articleCategoryBusiness.findArticleCategoryVoListByShopNo(super.getShopNo());
		News news = new News();
		model.addAttribute("news", news);
		model.addAttribute("articleCategory", acList);
		return "/m/article/article-add";
	}
	
	@RequestMapping(value="addArticle",method = RequestMethod.POST)
	public String saveArticle(News news,String categoryItem,Model model){
		news.setShopNo(super.getShopNo());
		newsBusiness.addNews(news, categoryItem, super.getUserId());
		return super.getRedirectUrl("/u/stuff/article/listArticle");
	}
	
	
	@RequestMapping(value="editArticle",method = RequestMethod.GET)
	public String editArticle(Long editId,Model model){
		News news = newsBusiness.getNewsById(editId);
		List<ArticleCategoryVo> acList = newsBusiness.checkedNewsByNewsId(super.getShopNo(), editId);
		model.addAttribute("news", news);
		model.addAttribute("articleCategory", acList);
		return "/m/article/article-edit";
	}
	
	@RequestMapping(value="editArticle",method = RequestMethod.POST)
	public String updateArticle(News news,String categoryItem,Model model){
		newsBusiness.editNews(news, categoryItem,super.getUserId());
		return super.getRedirectUrl("/u/stuff/article/listArticle");
	}
	
	
	
	@RequestMapping(value="delArticle",method = RequestMethod.POST)
	@ResponseBody
	public String delArticle(Long id){
		return newsBusiness.deleteNews(id,super.getUserId());
	}
	
	@RequestMapping(value="delAllArticle",method = RequestMethod.POST)
	@ResponseBody
	public String delAllArticle(String delId){
		return newsBusiness.delAllArticle(delId,super.getUserId());
	}
	
	@RequestMapping(value="listCategory")
	public String categoryList(ArticleCategoryDto articleCategoryDto,Pagination<ArticleCategoryDto> pagination,Model model){
		articleCategoryDto.setShopNo(super.getShopNo());
		pagination = articleCategoryBusiness.findArticleCategoryPageList(articleCategoryDto, pagination);
		model.addAttribute("pagination", pagination);
		return "/m/article/article-cat";
	}
	
	@RequestMapping(value="addCategory",method = RequestMethod.GET)
	public String addCategory(Model model){
		return "/m/article/category-add";
	}
	
	@RequestMapping(value="addCategory",method = RequestMethod.POST)
	public String saveCategory(ArticleCategoryDto articleCategoryDto,Model model){
		articleCategoryDto.setShopNo(super.getShopNo());
		articleCategoryBusiness.addArticleCategory(articleCategoryDto,super.getUserId());
		return super.getRedirectUrl("/u/stuff/article/listCategory");
	}
	
	
	@RequestMapping(value="editCategory",method = RequestMethod.GET)
	public String editCategory(Long editId,Model model){
		ArticleCategory articleCategory = articleCategoryBusiness.getArticleCategoryById(editId);
		model.addAttribute("articleCategory", articleCategory);
		return "/m/article/category-edit";
	}
	
	
	
	@RequestMapping(value="editCategory",method = RequestMethod.POST)
	public String updateCategory(ArticleCategoryDto articleCategoryDto,Model model){
		articleCategoryBusiness.editArticleCategory(articleCategoryDto,super.getUserId());
		return super.getRedirectUrl("/u/stuff/article/listCategory");
	}
	
	
	@RequestMapping(value="changeNews",method = RequestMethod.POST)
	@ResponseBody
	public String changeNews(ArticleCategoryDto articleCategoryDto){
		return articleCategoryBusiness.updateNewsAndCategoryRelation(articleCategoryDto);
	}
	
	
	@RequestMapping(value="delCategory",method = RequestMethod.POST)
	@ResponseBody
	public String delCategory(Long id){
		return	articleCategoryBusiness.deleteArticleCategory(id,super.getUserId());
	}
	
	
	@RequestMapping(value="listColumn")
	public String columnList(AricleColumnDto aricleColumnDto,Pagination<AricleColumnDto> pagination,Model model){
		aricleColumnDto.setShopNo(super.getShopNo());
		pagination = aricleColumnBusiness.findAricleColumnPageList(aricleColumnDto, pagination);
		model.addAttribute("pagination", pagination);
		return "/m/article/column-list";
	}
	
	
	@RequestMapping(value="addColumn",method = RequestMethod.GET)
	public String addColumn(Model model){
		return "/m/article/column-add";
	}
	
	@RequestMapping(value="addColumn",method = RequestMethod.POST)
	public String saveColumn(AricleColumnDto aricleColumnDto,Model model){
		aricleColumnDto.setShopNo(super.getShopNo());
		aricleColumnBusiness.addAricleColumn(aricleColumnDto,super.getUserId());
		return super.getRedirectUrl("/u/stuff/article/listColumn");
	}
	
	@RequestMapping(value="editColumn",method = RequestMethod.GET)
	public String editColumn(Long editId,Model model){
		AricleColumn aricleColumn = aricleColumnBusiness.getAricleColumn(editId);
		model.addAttribute("data", aricleColumn);
		return "/m/article/column-edit";
	}
	
	@RequestMapping(value="editColumn",method = RequestMethod.POST)
	public String updateColumn(AricleColumnDto aricleColumnDto,Model model){
		aricleColumnBusiness.editAricleColumn(aricleColumnDto,super.getUserId());
		return super.getRedirectUrl("/u/stuff/article/listColumn");
	}
	
	
	@RequestMapping(value="changeCategory",method = RequestMethod.POST)
	@ResponseBody
	public String changeCategory(AricleColumnDto aricleColumnDto){
		return aricleColumnBusiness.updateColumnAndCategory(aricleColumnDto);
	}
	
	
	@RequestMapping(value="delColumn",method = RequestMethod.POST)
	@ResponseBody
	public String delColumn(Long id){
		return	aricleColumnBusiness.delAricleColumn(id,super.getUserId());
	}
	
	
	@RequestMapping(value="listNews",method = RequestMethod.POST)
	@ResponseBody
	public String newsList(){
		List<News> newsList = newsBusiness.findNewsByShopNo(super.getShopNo());
		return JSONArray.fromObject(newsList).toString();
	}
	
	@RequestMapping(value="selectNews",method = RequestMethod.POST)
	@ResponseBody
	public String selectNews(Long categoryId,String title){
		List<NewsVo> newsList = newsBusiness.checkNewsByCategoryId(super.getShopNo(), categoryId,title);
		return JSONArray.fromObject(newsList).toString();
	}
	
	
	@RequestMapping(value="selectAllCategory",method = RequestMethod.POST)
	@ResponseBody
	public String selectAllCategory(){
		List<ArticleCategory> articleCategory = articleCategoryBusiness.findArticleCategoryListByShopNo(super.getShopNo());
		return JSONArray.fromObject(articleCategory).toString();
	}
	
	
	@RequestMapping(value="selectCategory",method = RequestMethod.POST)
	@ResponseBody
	public String selectCategory(Long columnId,String title){
		List<ArticleCategoryVo> articleCategory = articleCategoryBusiness.checkCategoryByColumnId(super.getShopNo(), columnId,title);
		return JSONArray.fromObject(articleCategory).toString();
	}
	
	
	@RequestMapping(value="getSortNews",method = RequestMethod.POST)
	@ResponseBody
	public String getSortNews(Long categoryId){
		List<NewsDto> newsDtoList = newsBusiness.findNewsAndSortByCategoryId(categoryId);
		return JSONArray.fromObject(newsDtoList).toString();
	}
	
	@RequestMapping(value="sortNew",method = RequestMethod.POST)
	@ResponseBody
	public String sortNew(Long subId, Long tarId){
		return newsBusiness.soertNew(subId, tarId);
	}
	
	@RequestMapping(value="getSortCategorys",method = RequestMethod.POST)
	@ResponseBody
	public String getSortCategorys(Long columnId){
		List<AricleColumnDto> columnDtoList = aricleColumnBusiness.findNewsAndSortByCategoryId(columnId);
		return JSONArray.fromObject(columnDtoList).toString();
	}
	
	@RequestMapping(value="sortCategory",method = RequestMethod.POST)
	@ResponseBody
	public String sortCategory(Long subId, Long tarId){
		return aricleColumnBusiness.soertCategory(subId, tarId);
	}
	
	
	
}
