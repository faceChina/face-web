package com.zjlp.face.web.ctl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.TemplateException;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.operation.appoint.bussiness.AppointmentBusiness;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.material.bussiness.AlbumBusiness;
import com.zjlp.face.web.server.product.material.bussiness.AricleColumnBusiness;
import com.zjlp.face.web.server.product.material.bussiness.ArticleCategoryBusiness;
import com.zjlp.face.web.server.product.material.bussiness.NewsBusiness;
import com.zjlp.face.web.server.product.material.bussiness.PhotoAlbumBusiness;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.ArticleCategory;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.product.template.domain.Modular;
import com.zjlp.face.web.server.product.template.domain.TemplateDetail;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.product.template.domain.dto.ParamDto;
import com.zjlp.face.web.server.user.shop.domain.Shop;

@Controller
@RequestMapping({ "/u/template/"})
public class OwTemplateCtl extends BaseCtl {

	@Autowired
	private TemplateBusiness templateBusiness;
	
	@Autowired
	private GoodBusiness goodBusiness;
	
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;
	//文章
	@Autowired
	private NewsBusiness newsBusiness;
	//专题
	@Autowired
	private ArticleCategoryBusiness articleCategoryBusiness;
	//栏目
	@Autowired
	private AricleColumnBusiness aricleColumnBusiness;
	//相册
	@Autowired
	private AlbumBusiness albumBusiness;
	//相册专辑
	@Autowired
	private PhotoAlbumBusiness photoAlbumBusiness;
	//预约
	@Autowired
	private AppointmentBusiness appointmentBusiness;
	/**
	 * 模版列表
	 * @Title: listOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param subCode
	 * @param pagination
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @return String
	 * @author phb
	 * @date 2015年3月24日 下午6:55:18
	 */
	@RequestMapping(value = "{subCode}/listOwTemplate")
    public String listOwTemplate(@PathVariable String subCode, Pagination<OwTemplateDto> pagination,HttpServletRequest request, Model model) throws Exception{
		List<OwTemplateDto> list = templateBusiness.findOwTemplateBySubCode(subCode,1);
        // 对模板进行分页
        if(null == request.getParameter("pageSize")){
            pagination.setPageSize(12);
        }
        pagination.setTotalRow(list.size());
        List<OwTemplateDto> l = new ArrayList<OwTemplateDto>();
        if(pagination.getEnd() != 0){
            for(int i = 0; i < list.size() && i < pagination.getEnd(); i++){
                if(i >= pagination.getStart()){
                    l.add(list.get(i));
                }
            }
        }
        pagination.setDatas(l);
        model.addAttribute("pagination", pagination);
        
        if(Constants.GW_WSC_CODE.equals(subCode)){
        	return "/m/product/template/tempmanage-gw-wsc";
        } else if(Constants.WGW_CODE.equals(subCode) || Constants.WSC_CODE.equals(subCode)){
        	return "/m/product/template/tempmanage";
        } else {
        	return "/m/common/error404";
        }
    }
	
	/**
	 * 选择、切换 模版
	 * @Title: switchOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param session
	 * @param code
	 * @param model
	 * @return
	 * @throws Exception
	 * @return String
	 * @author phb
	 * @date 2015年3月24日 下午6:55:29
	 */
	@RequestMapping(value = "switchOwTemplate")
    @ResponseBody
	public String switchOwTemplate(HttpSession session, String code, Model model) throws Exception{
        try{
            AssertUtil.notNull(code, "切换的模版编号为空");
            /**测试数据**/
            Shop shop = (Shop) session.getAttribute("shopInfo");
            
            templateBusiness.switchTemplate(shop.getNo(), code);
            
            if(code.indexOf(Constants.GW_WSC_CODE) != -1){
            	// 将当前首页和分类页模板数据放入SESSION
                OwTemplateDto owTemplateHp = templateBusiness.getCurrentHomePageOwTemplate(shop.getNo(), Constants.SHOP_SC_TYPE);
            	session.removeAttribute("owTemplateScHp");
                session.setAttribute("owTemplateScHp", owTemplateHp);
            }else{
            	// 将当前首页和分类页模板数据放入SESSION
                OwTemplateDto owTemplateHp = templateBusiness.getCurrentHomePageOwTemplate(shop.getNo(), shop.getType());
	            session.removeAttribute("owTemplateHp");
	            session.setAttribute("owTemplateHp", owTemplateHp);
            }
            return "1";
        }catch(Exception e){
            e.printStackTrace();
            return "-1";
        }
    }
	/**
	 * 模块列表
	 * @Title: listModular
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param subCode
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 * @return String
	 * @author phb
	 * @date 2015年3月25日 上午9:43:42
	 */
	@RequestMapping(value = "{subCode}/listModular")
	public String listModular(@PathVariable String subCode, HttpSession session, Model model) throws Exception{
		OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode,session);
		//标准模块
        List<Modular> standardModularList = templateBusiness.findModularByOwTemplateId(owTemplateHp.getId(),Constants.MODULAR_STANDARD);
        if(1 == owTemplateHp.getIsUserDefined()){
        	//自定义模块
        	List<Modular> customModularList = templateBusiness.findModularByOwTemplateId(owTemplateHp.getId(),Constants.MODULAR_CUSTOM);
        	model.addAttribute("customModularList", customModularList);
        }
        // 是否有模板细项
        if(owTemplateHp.getIsTemplateDetail() == 1) {
        	// 查询模板细项
        	TemplateDetail templateDetail = templateBusiness.getTemplateDetailByTemplateId(owTemplateHp.getId());
        	model.addAttribute("templateDetail", templateDetail);
        }
        model.addAttribute("standardModularList", standardModularList);
        model.addAttribute("subCode",subCode);
        if(Constants.GW_WSC_CODE.equals(subCode)){
        	return "/m/product/template/template-index-gw-wsc";
        } else if(Constants.WGW_CODE.equals(subCode) || Constants.WSC_CODE.equals(subCode)){
        	return "/m/product/template/template-index";
        } else {
        	return "/m/common/error404";
        }
	}

	@RequestMapping(value = "{subCode}/{type}/addModular",method = RequestMethod.GET)
	public String initAddModular(@PathVariable String subCode,@PathVariable Integer type,HttpSession session,Model model) throws Exception{
		OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode,session);
		//基础模块
		List<Modular> baseModularList = templateBusiness.findBaseModular(owTemplateHp.getCode());
		//商品分类
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(super.getShopNo());
		//商品列表
		List<Good> goodList = goodBusiness.findGoodsByShopNo(super.getShopNo());
		//预约列表
		List<AppointmentDto> appointmentList = appointmentBusiness.findAllAppointmentByShopNo(super.getShopNo());
		
//		if(Constants.WGW_CODE.equals(subCode)){
//			//文章列表
//			List<News> newsList = newsBusiness.findNewsByShopNo(super.getShopNo());
			//文章专题列表
			List<ArticleCategory> articleCategoryList = articleCategoryBusiness.findArticleCategoryListByShopNo(super.getShopNo());
			//文章栏目列表
			List<AricleColumn> aricleColumnList = aricleColumnBusiness.findAricleColumnByShopNo(super.getShopNo());
			//相册列表
			List<Album> albumList = albumBusiness.findAlbumByShopNo(super.getShopNo());
			//相册专辑列表
			List<PhotoAlbum> photoAlbumList = photoAlbumBusiness.findPhotoAlbumByShopNo(super.getShopNo());
//			model.addAttribute("newsList", newsList);
			model.addAttribute("articleCategoryList", articleCategoryList);
			model.addAttribute("aricleColumnList", aricleColumnList);
			model.addAttribute("albumList", albumList);
			model.addAttribute("photoAlbumList", photoAlbumList);
//		}
		
		model.addAttribute("appointmentList", appointmentList);
		model.addAttribute("baseModularList", baseModularList);
		model.addAttribute("shopTypeList", shopTypeList);
		model.addAttribute("goodList", goodList);
		model.addAttribute("modularType", type);
		model.addAttribute("subCode",subCode);
		if(Constants.GW_WSC_CODE.equals(subCode)){
        	return "/m/product/template/template-index-edit-gw-wsc";
        } else if(Constants.WGW_CODE.equals(subCode) || Constants.WSC_CODE.equals(subCode)){
        	return "/m/product/template/template-index-edit";
        } else {
        	return "/m/common/error404";
        }
	}

	@RequestMapping(value = "{subCode}/{type}/addModular",method = RequestMethod.POST)
	public String addModular(@PathVariable String subCode,@PathVariable Integer type,Modular modular,HttpSession session,Model model) throws Exception{
		Assert.notNull(modular,"模块对象为空");
		OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode,session);
		modular.setOwTemplateId(owTemplateHp.getId());
		if(StringUtils.isBlank(modular.getCode())){
			modular.setCode(owTemplateHp.getCode());
		}
		templateBusiness.saveModuarl(modular, owTemplateHp, super.getShopNo(),super.getUserId());
		return super.getRedirectUrl("/u/template/"+subCode+"/listModular");
	}
	
	@RequestMapping(value = "{subCode}/{type}/editModular/{modularId}",method = RequestMethod.GET)
	public String initeEditModular(@PathVariable String subCode,@PathVariable Integer type,@PathVariable Long modularId,HttpSession session,Model model) throws Exception{
		OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode,session);
		//基础模块
		List<Modular> baseModularList = templateBusiness.findBaseModular(owTemplateHp.getCode());
		//商品分类
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(super.getShopNo());
		//商品列表
		List<Good> goodList = goodBusiness.findGoodsByShopNo(super.getShopNo());
		//预约列表
		List<AppointmentDto> appointmentList = appointmentBusiness.findAllAppointmentByShopNo(super.getShopNo());
//		if(Constants.WGW_CODE.equals(subCode)){  //官网与商城保持一致
			//文章列表
			List<News> newsList = newsBusiness.findNewsByShopNo(super.getShopNo());
			//文章专题列表
			List<ArticleCategory> articleCategoryList = articleCategoryBusiness.findArticleCategoryListByShopNo(super.getShopNo());
			//文章栏目列表
			List<AricleColumn> aricleColumnList = aricleColumnBusiness.findAricleColumnByShopNo(super.getShopNo());
			//相册列表
			List<Album> albumList = albumBusiness.findAlbumByShopNo(super.getShopNo());
			//相册专辑列表
			List<PhotoAlbum> photoAlbumList = photoAlbumBusiness.findPhotoAlbumByShopNo(super.getShopNo());
			model.addAttribute("newsList", newsList);
			model.addAttribute("articleCategoryList", articleCategoryList);
			model.addAttribute("aricleColumnList", aricleColumnList);
			model.addAttribute("albumList", albumList);
			model.addAttribute("photoAlbumList", photoAlbumList);
//		}
		
		Modular modular = templateBusiness.getModularById(modularId);
		if(modular.getOwTemplateId().longValue() != owTemplateHp.getId().longValue()){
			return "/m/common/error404";
		}
		model.addAttribute("appointmentList", appointmentList);
		model.addAttribute("modular", modular);
		model.addAttribute("baseModularList", baseModularList);
		model.addAttribute("shopTypeList", shopTypeList);
		model.addAttribute("goodList", goodList);
		model.addAttribute("modularType", type);
		model.addAttribute("subCode",subCode);
		if(Constants.GW_WSC_CODE.equals(subCode)){
        	return "/m/product/template/template-index-edit-gw-wsc";
        } else if(Constants.WGW_CODE.equals(subCode) || Constants.WSC_CODE.equals(subCode)){
        	return "/m/product/template/template-index-edit";
        } else {
        	return "/m/common/error404";
        }
	}
	
	@RequestMapping(value = "{subCode}/{type}/editModular/{modularId}",method = RequestMethod.POST)
	public String editModular(@PathVariable String subCode,@PathVariable Integer type,@PathVariable Long modularId,Modular modular,HttpSession session,Model model) throws Exception{
		Assert.notNull(modular,"模块对象为空");
		Assert.notNull(modular.getId(),"模块编号为空");
		OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode,session);
		templateBusiness.saveModuarl(modular, owTemplateHp, super.getShopNo(),super.getUserId());
		return super.getRedirectUrl("/u/template/"+subCode+"/listModular");
	}
	
    @RequestMapping(value = "modular/{subCode}/deleteModular")
    @ResponseBody
    public String deleteModular(@PathVariable String subCode, Long id, HttpSession session, Model model){
        try{
            OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode, session);
            templateBusiness.deleteModular(id, owTemplateHp);
            return "1";
        }catch(Exception e){
            e.printStackTrace();
            return "-1";
        }
    }

    @RequestMapping(value = "modular/{subCode}/resetModular")
    public String resetModular(@PathVariable String subCode, HttpSession session, Model model){
        OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode, session);
        templateBusiness.resetModular(owTemplateHp.getId());
        owTemplateHp = (OwTemplateDto) session.getAttribute("owTemplateHp");
        session.removeAttribute("owTemplateHp");
        session.setAttribute("owTemplateHp", owTemplateHp);
        return super.getRedirectUrl("/u/template/"+subCode+"/listModular");
    }

    @RequestMapping(value = "modular/{subCode}/sortModular")
    @ResponseBody
    public String sortModular(@PathVariable String subCode, Long subId, Long tarId, HttpSession session, Model model){
        try{
        	OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode, session);
        	Modular modular = templateBusiness.getModularById(subId);
        	//标准模块验证
        	if(modular.getType().intValue() == Constants.MODULAR_STANDARD.intValue()){
        		AssertUtil.isTrue(owTemplateHp.getIsChange() == 1, "当前模版模块不可移动");
        	}
            templateBusiness.sortModular(subId, tarId);
            return "1";
        }catch(Exception e){
            e.printStackTrace();
            return "-1";
        }
    }
    
    /**
     * 编辑模板店招
     * @Title: changeShopStrokes 
     * @Description: (编辑模板店招) 
     * @param session
     * @param model
     * @return
     * @date 2014年11月21日 下午4:22:04  
     * @author ah
     */
    @RequestMapping(value="{subCode}/changeShopStrokes")
    @ResponseBody
    public String changeShopStrokes(@PathVariable String subCode,HttpSession session, OwTemplateDto owTemplate, Model model) {
    	Shop shop = super.getShop();
    	 try{
    		 //编辑首页模板店招
             templateBusiness.changeShopStrokes(owTemplate);
             if(subCode.indexOf(Constants.GW_WSC_CODE) != -1){
             	// 将当前首页和分类页模板数据放入SESSION
                 OwTemplateDto owTemplateHp = templateBusiness.getCurrentHomePageOwTemplate(shop.getNo(), Constants.SHOP_SC_TYPE);
             	session.removeAttribute("owTemplateScHp");
                 session.setAttribute("owTemplateScHp", owTemplateHp);
             }else{
             	// 将当前首页和分类页模板数据放入SESSION
                 OwTemplateDto owTemplateHp = templateBusiness.getCurrentHomePageOwTemplate(shop.getNo(), shop.getType());
 	            session.removeAttribute("owTemplateHp");
 	            session.setAttribute("owTemplateHp", owTemplateHp);
             }
             return "1";
         }catch(Exception e){
             e.printStackTrace();
             return "-1";
         }
    }
	
    /**
     * 轮播图列表
     * @Title: carousel
     * @Description: (这里用一句话描述这个方法的作用)
     * @param subCode
     * @param session
     * @param model
     * @return
     * @return String
     * @author phb
     * @date 2015年3月27日 上午9:46:33
     */
    @Token(save=true)
    @RequestMapping(value="{subCode}/carousel",method = RequestMethod.GET)
    public String carousel(@PathVariable String subCode,HttpSession session, Model model) {
    	OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode, session);
        List<CarouselDiagramDto> carouselDiagramList = templateBusiness.findCarouselDiagramByOwTemplateId(owTemplateHp.getId());
        
		//基础模块
		List<Modular> baseModularList = templateBusiness.findBaseModular(owTemplateHp.getCode());
		//商品分类
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(super.getShopNo());
		//商品列表
		List<Good> goodList = goodBusiness.findGoodsByShopNo(super.getShopNo());
		//预约列表
		List<AppointmentDto> appointmentList = appointmentBusiness.findAllAppointmentByShopNo(super.getShopNo());
        
		model.addAttribute("appointmentList", appointmentList);
		model.addAttribute("baseModularList", baseModularList);
		model.addAttribute("shopTypeList", shopTypeList);
		model.addAttribute("goodList", goodList);
        model.addAttribute("carouselDiagramList", carouselDiagramList);
        model.addAttribute("size", carouselDiagramList.size());
        if(Constants.GW_WSC_CODE.equals(subCode)){
        	return "/m/product/template/carousel-gw-wsc";
        } else if(Constants.WGW_CODE.equals(subCode) || Constants.WSC_CODE.equals(subCode)){
        	return "/m/product/template/carousel";
        } else {
        	return "/m/common/error404";
        }
    }
    
    /***
     * 保存轮播图列表
     * @Title: addCarousel
     * @Description: (这里用一句话描述这个方法的作用)
     * @param subCode
     * @param paramDto
     * @param session
     * @param model
     * @return
     * @return String
     * @author phb
     * @date 2015年3月27日 上午9:46:41
     */
    @Token(remove=true)
    @RequestMapping(value="{subCode}/carousel",method = RequestMethod.POST)
    public String addCarousel(@PathVariable String subCode,ParamDto paramDto,HttpSession session, Model model) {
    	OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode, session);
    	templateBusiness.addOrEditCarouselDiagram(paramDto.getList(), super.getShopNo(),super.getUserId(),owTemplateHp);
    	return super.getRedirectUrl("/u/template/"+subCode+"/carousel");
    }
    
    @RequestMapping(value="{subCode}/delCarousel",method = RequestMethod.POST)
    @ResponseBody
    public String delCarousel(@PathVariable String subCode,Long id,HttpSession session, Model model) {
    	try {
			OwTemplateDto owTemplateHp = _getOwTemplateHp(subCode, session);
			templateBusiness.deleteCarouselDiagramHp(id, owTemplateHp,super.getUserId(),super.getShopNo());
			return "1";
		} catch (TemplateException e) {
			e.printStackTrace();
			return "-1";
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
	 * 保存模板细项
	 * @Title: saveTemplateDetail 
	 * @Description: (保存模板细项) 
	 * @param templateDetail
	 * @return
	 * @date 2015年3月26日 下午9:33:50  
	 * @author ah
	 */
	@RequestMapping(value="saveTemplateDetail")
	@ResponseBody
	public String saveTemplateDetail(TemplateDetail templateDetail) {
		
		try {
			//保存模板细项
			templateBusiness.editTemplateDetail(templateDetail,super.getShopNo(), super.getUserId());
			return super.getReqJson(true, null);
		} catch (Exception e) {
			return super.getReqJson(false, "保存联系方式失败");
		}
	}
	
	/**
	 * 查询文章列表
	 * @Title: queryNewsList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param news
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月6日 下午1:49:50
	 */
	@RequestMapping(value="queryNewsList")
	@ResponseBody
	public String queryNewsList(News news,Model model) {
		news.setShopNo(super.getShopNo());
		List<News> newsList = newsBusiness.findNewsList(news);
		return JSONArray.fromObject(newsList).toString();
	}
}
