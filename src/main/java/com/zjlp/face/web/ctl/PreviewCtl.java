package com.zjlp.face.web.ctl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.material.bussiness.AricleColumnBusiness;
import com.zjlp.face.web.server.product.material.bussiness.ArticleCategoryBusiness;
import com.zjlp.face.web.server.product.material.bussiness.NewsBusiness;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.dto.ArticleCategoryDto;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.product.template.bussiness.impl.TemplateBusinessImpl;
import com.zjlp.face.web.server.product.template.domain.Modular;
import com.zjlp.face.web.server.product.template.domain.TemplateDetail;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.product.template.domain.vo.ModularColorVo;
@Controller
@RequestMapping({"/any/preview/"})
public class PreviewCtl extends BaseCtl {

	@Autowired
	private TemplateBusiness templateBusiness;
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;
	
	@Autowired
	private NewsBusiness newsBusiness;
	
	@Autowired
	private ArticleCategoryBusiness articleCategoryBusiness;
	
	@Autowired
	private AricleColumnBusiness aricleColumnBusiness;
	
	@Autowired
	private GoodBusiness goodBusiness;
	private static final String PRVIEW_TYPE = "previewType";
	private static final String CAROUSEL = "carousel";
	private static final String JSON_IMG_ARR = "imgArrJson";
	/** 自定义模块是否有文字颜色 */
	private static final Integer NAME_COLOR_CUSTOM = 1;
	
	/**
	 * 模版预览
	 * @Title: template
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param jsonstr
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月28日 上午10:14:48
	 */
	
	@RequestMapping("{subCode}/template")
	public String template(@PathVariable String subCode,String jsonstr,HttpSession session,Model model){
		OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode,session);
		JSONObject jsonObject = JSONObject.fromObject(jsonstr);
		List<CarouselDiagramDto> carouselDiagramList = new ArrayList<CarouselDiagramDto>();
		List<Modular> standardModularList = null;
		List<Modular> customModularList = null;
		//所有模块
		List<Modular> allModular= new ArrayList<Modular>();
		TemplateDetail templateDetail = null;
		String code = jsonObject.getString("owTemplateCode");
		String previewType = jsonObject.getString(PRVIEW_TYPE);
		if(owTemplateHp.getCode().equals(code)){
			//当前模版预览
			//是否有轮播图
			if(CAROUSEL.equals(previewType)) {
				String imgArrJson = jsonObject.getString(JSON_IMG_ARR);
				JSONArray arr = JSONArray.fromObject(imgArrJson);
				for (int i = 0; i < arr.size(); i++) {
					String imgPath = arr.getString(i);
					if (StringUtils.isBlank(imgPath)) {
						continue;
					}
					CarouselDiagramDto carousel = new CarouselDiagramDto();
					carousel.setImgPath(imgPath);
					carousel.setIsBasePic(1);
					carouselDiagramList.add(carousel);
				}
			} else {
				if(null != owTemplateHp.getIsCarlOrBg() && owTemplateHp.getIsCarlOrBg().intValue() != 0){
					carouselDiagramList = templateBusiness.findCarouselDiagramByOwTemplateId(owTemplateHp.getId());
					for (CarouselDiagramDto carouselDiagramDto : carouselDiagramList) {
						carouselDiagramDto.setImgPath(ImageConstants.getCloudstZoomPath(carouselDiagramDto.getImgPath(), owTemplateHp.getCarouselImageSize()));
					}
				}
			}
			//是否有标准模块
			if(null != owTemplateHp.getIsModular() && owTemplateHp.getIsModular().intValue() == 1){
				standardModularList = templateBusiness.findModularByOwTemplateId(owTemplateHp.getId(), Constants.MODULAR_STANDARD);
				//是否有图片
				if(null != owTemplateHp.getIsImage() && owTemplateHp.getIsImage().intValue() == 1){
					for (Modular modular : standardModularList) {
						modular.setImgPath(ImageConstants.getCloudstZoomPath(modular.getImgPath(), owTemplateHp.getModularImageSize()));
					}
				}
				allModular.addAll(standardModularList);
			}
			//是否有自定义模块
			if(null != owTemplateHp.getIsUserDefined() && owTemplateHp.getIsUserDefined().intValue() == 1){
				customModularList = templateBusiness.findModularByOwTemplateId(owTemplateHp.getId(), Constants.MODULAR_CUSTOM);
				//是否有图片
				if(null != owTemplateHp.getIsImageCustom() && owTemplateHp.getIsImageCustom().intValue() == 1){
					for (Modular modular : customModularList) {
						modular.setImgPath(ImageConstants.getCloudstZoomPath(modular.getImgPath(), owTemplateHp.getCustomModularImageSize()));
					}
				}
				allModular.addAll(customModularList);
			}
			//是否有店招//是否有联系方式
			if((null != owTemplateHp.getIsShopStrokes() && owTemplateHp.getIsShopStrokes().intValue() == 1)
					|| (null != owTemplateHp.getIsContactWay() && owTemplateHp.getIsContactWay().intValue() == 1) ){
				templateDetail = templateBusiness.getTemplateDetailByTemplateId(owTemplateHp.getId());
			}
		}else{
			List<OwTemplateDto> owTemplateDtos = templateBusiness.findOwTemplateBySubCode(subCode, 1);
			for (OwTemplateDto owTemplateDto : owTemplateDtos) {
				if(owTemplateDto.getCode().equals(code)){
					owTemplateHp = owTemplateDto;
				}
			}
			//其他模版预览
			//是否有轮播图
			if(null != owTemplateHp.getIsCarlOrBg() && owTemplateHp.getIsCarlOrBg().intValue() != 0){
				carouselDiagramList = templateBusiness.findCarouselDiagramByCode(code);
			}
			//是否有标准模块
			if(null != owTemplateHp.getIsModular() && owTemplateHp.getIsModular().intValue() == 1){
				standardModularList = templateBusiness.findModularByCode(code, Constants.MODULAR_STANDARD);
				allModular.addAll(standardModularList);
			}
			//是否有自定义模块
			if(null != owTemplateHp.getIsUserDefined() && owTemplateHp.getIsUserDefined().intValue() == 1){
				customModularList = templateBusiness.findModularByCode(code, Constants.MODULAR_CUSTOM);
				allModular.addAll(customModularList);
			}
		}
		Integer nameColorCustom = owTemplateHp.getIsNameZhColorCustom();
		if(null != nameColorCustom && NAME_COLOR_CUSTOM==nameColorCustom) {
			// 标准模块颜色
			List<ModularColorVo> standardModularColor = TemplateBusinessImpl._generateColor(standardModularList, owTemplateHp);
			String json = JSONArray.fromObject(standardModularColor).toString();
			// 自定义模块颜色
			List<ModularColorVo> customModularCustom = TemplateBusinessImpl._generateColor(customModularList, owTemplateHp);
			String jsonCustom = JSONArray.fromObject(customModularCustom).toString();
			model.addAttribute("json", json);
			model.addAttribute("jsonCustom", jsonCustom);
		} else {
			if(null != standardModularList) {
				List<ModularColorVo> standardModularColor = TemplateBusinessImpl._generateColor(standardModularList, owTemplateHp);
				String json = JSONArray.fromObject(standardModularColor).toString();
				model.addAttribute("json", json);
			}
		}
		
		//查询商品分类
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(super.getShopNo());
		model.addAttribute("shopTypeList", shopTypeList);
		model.addAttribute("carouselDiagramList", carouselDiagramList);
		model.addAttribute("standardModularList", standardModularList);
		model.addAttribute("customModularList", customModularList);
		model.addAttribute("templateDetail", templateDetail);
//		model.addAttribute("previewType", previewType);
		if("V31".equals(owTemplateHp.getTemplateDtoVersion())){
			//3.1期模板
			GoodVo goodVo = null;
			Pagination<GoodVo> pagination = null;
			//首页商品
			if("HP_GW_WSC_V31_1".equals(code) || "HP_WSC_V31_1".equals(code)){
				goodVo = new GoodVo();
				goodVo.setShopNo(super.getShopNo());
				goodVo.setIsSpreadIndex(1);
				goodVo.setStatus(Constants.VALID);//正常状态
				pagination = new Pagination<GoodVo>();
				goodVo.setServiceId(Constants.SERVICE_ID_COMMON);
				pagination = goodBusiness.searchPageGood(goodVo, pagination);
				model.addAttribute("hpGoodList", pagination.getDatas());
			}
			//查询商品分类和商品
			if("HP_GW_WSC_V31_3".equals(code) || "HP_GW_WSC_V31_4".equals(code) || "HP_WSC_V31_3".equals(code) || "HP_WSC_V31_4".equals(code)){
				List<ShopTypeVo> shopTypeAndGoodList = shopTypeBusiness.findShopTypeListAndGoodByShopNo(super.getShopNo(), Constants.SHOP_TYPE_NUM, 4);
				model.addAttribute("shopTypeAndGoodList", shopTypeAndGoodList);
				// 查询商品数量
				if("HP_GW_WSC_V31_4".equals(code) || "HP_WSC_V31_4".equals(code)) {
					// 查询商品数量
					goodVo = new GoodVo();
					goodVo.setShopNo(super.getShopNo());
					GoodVo goodNumVo = goodBusiness.getGoodNum(goodVo);
					model.addAttribute("goodNumVo", goodNumVo);
				}
			}
			//特定模板的查询条件
			if("HP_GW_WSC_V31_2".equals(code) || "HP_WSC_V31_2".equals(code)){
				goodVo = new GoodVo();
				goodVo.setShopNo(super.getShopNo());
				pagination = new Pagination<GoodVo>();
				pagination = goodBusiness.searchPageGood(goodVo, pagination);
				model.addAttribute("pagination", pagination);
				model.addAttribute("dto", goodVo);
				model.addAttribute("urlPath", "list");
			}
        }
		//跳转页面处理
		if(Constants.WGW_CODE.equals(subCode)){
			return new StringBuffer("/wap/product/template/website").append(code.substring(code.lastIndexOf("_")+1)).append("/index320").toString();
		} if(Constants.GW_WSC_CODE.equals(subCode)){
			Integer num = Integer.valueOf(code.substring(code.lastIndexOf("_") + 1))+1;
			return "/wap/product/template/shop" + num + "/index320";
		}else {
			return new StringBuffer("/wap/product/template/shop").append(Integer.valueOf(code.substring(code.lastIndexOf("_")+1))+1).append("/index320").toString();
		}
	}
	
	/**
	 * 获取模版对象
	 * @Title: _getOwTemplateHp
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param code
	 * @param session
	 * @return
	 * @return OwTemplateDto
	 * @author phb
	 * @date 2015年3月26日 上午10:43:21
	 */
	private OwTemplateDto _getOwTemplateHp(String code,HttpSession session){
		if(Constants.WGW_CODE.equals(code) || Constants.WSC_CODE.equals(code)){
			return (OwTemplateDto) session.getAttribute("owTemplateHp");
		} else if(Constants.GW_WSC_CODE.equals(code)){
			return (OwTemplateDto) session.getAttribute("owTemplateScHp");
		} else {
			return null;
		}
	}
	
	/**
	 * 文章分类预览
	 * @Title: articleCategoryPreview 
	 * @Description: (文章分类预览) 
	 * @param request
	 * @param jsonType
	 * @param jsonstr
	 * @param model
	 * @return
	 * @throws Exception
	 * @date 2014年9月26日 下午5:40:06  
	 * @author ah
	 */
	@RequestMapping(value="articleCategory")
	public String articleCategoryPreview(Long id,Model model) throws Exception{
		ArticleCategory articleCategory = articleCategoryBusiness.getArticleCategoryById(id);
		articleCategory.setPicPath(ImageConstants.getCloudstZoomPath(articleCategory.getPicPath(), "640_380"));
		List<NewsDto> newsDtoList = newsBusiness.findNewsAndSortByCategoryId(articleCategory.getId());
		for (NewsDto newsDto : newsDtoList) {
			newsDto.setPicPath(ImageConstants.getCloudstZoomPath(newsDto.getPicPath(), "400_400"));
		}
		model.addAttribute("newsDtoList",newsDtoList);
		model.addAttribute("category",articleCategory);
		if("1".equals(articleCategory.getArticleTemplateType())){
			return "/wap/product/template/article_topic1/index320";
		}
		if("2".equals(articleCategory.getArticleTemplateType())){
			return "/wap/product/template/article_topic2/index320";
		}
		if("3".equals(articleCategory.getArticleTemplateType())){
			return "/wap/product/template/article_topic3/index320";
		}
		return "/wap/product/template/article_topic1/index320";
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
			return "/wap/product/template/article_column1/index320";
		}
		if("2".equals(aricleColumn.getArticleTemplateType())){
			return "/wap/product/template/article_column2/index320";
		}
		if("3".equals(aricleColumn.getArticleTemplateType())){
			return "/wap/product/template/article_column3/index320";
		}
		if("4".equals(aricleColumn.getArticleTemplateType())){
			return "/wap/product/template/article_column4/index320";
		}
		
		return "/wap/product/template/article_column1/index320";
	}
}
