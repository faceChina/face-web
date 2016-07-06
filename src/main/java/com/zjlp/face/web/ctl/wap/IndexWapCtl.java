package com.zjlp.face.web.ctl.wap;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.http.filter.SubbranchFilter;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.product.template.domain.OwTemplate;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.product.template.domain.vo.TemplateVo;
import com.zjlp.face.web.server.user.businesscard.business.BusinessCardBusiness;
import com.zjlp.face.web.server.user.favorites.bussiness.FavoritesBussiness;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.shop.bussiness.NoticeBussiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Notice;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;

@Controller
@RequestMapping({ "/wap/{shopNo}/any/" })
public class IndexWapCtl extends WapCtl{
	
	@Autowired
	private ShopBusiness shopBusiness;
	
	@Autowired
    private TemplateBusiness templateBusiness;
	
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;
	
	@Autowired
	private GoodBusiness goodBusiness;
	
	@Autowired
	private FavoritesBussiness favoritesBussiness;
	
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	
	@Autowired
	private SubbranchProducer subbranchProducer;
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private BusinessCardBusiness businessCardBusiness;
	
	@Autowired
	private NoticeBussiness noticeBussiness;
	
	private static final String MAX_BRANCH_LEVEL = "maxBranchLevel";
	
	private static final String MODULARLIST = "modularList";

    private static final String CAROUSELLIST = "carouselList";

    private static final String TEMPLATE = "template";

	/**
	 * 模板首页（手机端）
	 * @Title: init 
	 * @Description: (模板首页（手机端）) 
	 * @param shopNo
	 * @param shopTypeId
	 * @param session
	 * @param model
	 * @return
	 * @date 2015年3月25日 下午3:41:37  
	 * @author ah
	 */
	@RequestMapping("index")
	public String init(@PathVariable String shopNo, String shopTypeId, HttpSession session, Model model){
		// 获取店铺信息
		try{
            AssertUtil.hasLength(shopNo, "店铺编号为空");
			Shop shop = super.getShop();
            AssertUtil.notNull(shop, "店铺信息为空");
            // 免费店铺
            if(Constants.SHOP_FREE_TYPE.equals(shop.getType())) {
            	// 查询免费店铺当前模板
            	OwTemplate owTemplate = templateBusiness.getCurrentHomePageOwTemplateForFree(shop.getNo(), shop.getType());
            	if(null == owTemplate) {
            		return super.getRedirectUrl("/wap/"+shopNo+"/any/list");
            	}
            }
			//查询模板模块轮播图
            TemplateVo dto = templateBusiness.getTemplateDtoByShop(shop.getNo(), shop.getType());
            model.addAttribute(TEMPLATE, dto.getOwTemplateHp());
            model.addAttribute(MODULARLIST, dto.getModularList());
            model.addAttribute(CAROUSELLIST, dto.getCarouselList());
            model.addAttribute("templateDetail", dto.getOwTemplateHp().getTemplateDetail());
			model.addAttribute("json", dto.getJson());
			model.addAttribute("jsonCustom", dto.getJsonCustom());
			model.addAttribute("shopTypeId", shopTypeId);
			OwTemplateDto owTemplateGt = templateBusiness.getCurrentGoodTypePageOwTemplate(shopNo, shop.getType());
			//查询商品分类
			List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(shopNo);
			for(ShopType st : shopTypeList){
				st.setImgPath(ImageConstants.getCloudstZoomPath(st.getImgPath(), owTemplateGt.getGoodTypeImageSize()));
			}
			model.addAttribute("shopTypeList", shopTypeList);
			//模板code
            String code = dto.getOwTemplateHp().getCode();
			/** 首页 */
			if("V31".equals(dto.getOwTemplateHp().getTemplateDtoVersion())){
				//3.1期模板
				GoodVo goodVo = null;
				Pagination<GoodVo> pagination = null;
				//首页商品
				if("HP_WSC_V31_1".equals(code)){
					goodVo = new GoodVo();
					goodVo.setShopNo(shopNo);
					goodVo.setIsSpreadIndex(1);
					goodVo.setStatus(Constants.VALID);//正常状态
					pagination = new Pagination<GoodVo>();
					goodVo.setServiceId(Constants.SERVICE_ID_COMMON);
					//pagination = goodBusiness.searchPageGood(goodVo, pagination);
					pagination = goodBusiness.findGoodVoPageForWap(shopNo, goodVo, pagination);
					model.addAttribute("hpGoodList", pagination.getDatas());
				}
				//查询商品分类和商品
				if("HP_WSC_V31_3".equals(code) || "HP_WSC_V31_4".equals(code) || "HP_WSC_V31_5".equals(code)){
					/*List<ShopTypeVo> shopTypeAndGoodList = shopTypeBusiness.findShopTypeListAndGoodByShopNo(shopNo, Constants.SHOP_TYPE_NUM, 4);*/
					List<ShopTypeVo> shopTypeAndGoodList = shopTypeBusiness.findShopTypeListAndGoodForWap(shopNo, Constants.SHOP_TYPE_NUM, 4);
					model.addAttribute("shopTypeAndGoodList", shopTypeAndGoodList);
					// 查询商品数量
					if("HP_GW_WSC_V31_4".equals(code) || "HP_WSC_V31_4".equals(code)) {
						// 查询商品数量
						goodVo = new GoodVo();
						goodVo.setShopNo(shopNo);
						/*GoodVo goodNumVo = goodBusiness.getGoodNum(goodVo);*/
						Integer totalGoodNum=goodBusiness.countGoodVoNum(goodVo);
						GoodVo goodNumVo = new GoodVo();
						goodNumVo.setTotalGoodNum(totalGoodNum);
						goodVo.setNewGoodMark(Constants.ISDEFAULT);
						Integer newGoodNum=goodBusiness.countGoodVoNum(goodVo);
						goodNumVo.setNewGoodNum(newGoodNum);
						model.addAttribute("goodNumVo", goodNumVo);
					}
				}
				//特定模板的查询条件
				if("HP_WSC_V31_2".equals(code)){
					goodVo = new GoodVo();
					goodVo.setShopNo(shopNo);
					if(null != shopTypeId && !"".equals(shopTypeId)){
						goodVo.setShopTypeId(Long.parseLong(shopTypeId));
					}
					pagination = new Pagination<GoodVo>();
					/*pagination = goodBusiness.searchPageGood(goodVo, pagination);*/
					pagination = goodBusiness.findGoodVoPageForWap(shopNo, goodVo, pagination);
					Long currentTime = System.currentTimeMillis();
					goodVo.setCurrentTime(currentTime);
					model.addAttribute("pagination", pagination);
					model.addAttribute("dto", goodVo);
					model.addAttribute("urlPath", "index");
				}
				if(code.indexOf("WGW") != -1){
					return "/wap/product/template/website" + code.substring(code.lastIndexOf("_") + 1) + "/index";
				}else if(code.indexOf("WSC") != -1){
					Integer num = Integer.valueOf(code.substring(code.lastIndexOf("_") + 1))+1;
					return "/wap/product/template/shop" + num + "/index";
				}
            }
            
        }catch(Exception e){
            model.addAttribute("message", "返回");
            e.printStackTrace();
            return "/wap/common/error404";
        }
		return "/wap/index/empty";
	}

	/**
	 * 
	 * @Title: gwscIndex 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @param shopTypeId
	 * @param goodVo
	 * @param session
	 * @param model
	 * @param layout
	 * @param isPreview 是否是预览 1是预览
	 * @param signPic 店招图片（预览）
	 * @param isDefaultSignPic 是否是默认店招图片（预览）
	 * @return
	 * @date 2015年9月15日 上午11:38:23  
	 * @author cbc
	 */
	@RequestMapping(value = "/gwscIndex")
	public String gwscIndex(@PathVariable String shopNo, String shopTypeId, GoodVo goodVo, HttpSession session, Model model, String layout, Integer isPreview, String signPic, Integer isDefaultSignPic){
		// 获取店铺信息
		try{
            AssertUtil.hasLength(shopNo, "店铺编号为空");
			Shop shop = super.getShop();
            AssertUtil.notNull(shop, "店铺信息为空");
			//查询模板模块轮播图
            TemplateVo dto = templateBusiness.getTemplateDtoByShop(shop.getNo(), Constants.SHOP_SC_TYPE);
            Long userId = getUserId();
            String subbranchId = SubbranchFilter.getSubbranchId(session);
            Long subbranchIdLong = null;
            boolean isMaxSubbranchLevel = false;
            if (null != subbranchId) {
            	subbranchIdLong = Long.valueOf(subbranchId);
				model.addAttribute("subbranchId", subbranchIdLong);
				Subbranch subbranch = subbranchBusiness.findSubbranchById(subbranchIdLong);
        		model.addAttribute("mySubbranchName", subbranch.getShopName());
        		isMaxSubbranchLevel = checkIsMaxBranchLevel(subbranchIdLong);
			}
            boolean isFavorites = this.isFavorites(userId, subbranchId, shop); //店铺是否已收藏
            boolean isLogin = (userId != null);//是否是登陆状态
            boolean isMyOwnShop = this.isMyOwnShop(userId, shop, subbranchIdLong);//是否是自己的店铺
            
            model.addAttribute("isMyOwnShop", isMyOwnShop);
            model.addAttribute("isMaxSubbranchLevel",isMaxSubbranchLevel);
            model.addAttribute("shopName", shop.getName());
            model.addAttribute("isLogin", isLogin);
            model.addAttribute("isFavorites", isFavorites);
            model.addAttribute(TEMPLATE, dto.getOwTemplateHp());
            model.addAttribute(MODULARLIST, dto.getModularList());
            model.addAttribute(CAROUSELLIST, dto.getCarouselList());
            model.addAttribute("templateDetail", dto.getOwTemplateHp().getTemplateDetail());
			model.addAttribute("json", dto.getJson());
			model.addAttribute("jsonCustom", dto.getJsonCustom());
			model.addAttribute("isPreview", isPreview);
			model.addAttribute("signPic", signPic);
			model.addAttribute("isDefaultSignPic", isDefaultSignPic);
			OwTemplateDto owTemplateGt = templateBusiness.getCurrentGoodTypePageOwTemplate(shopNo, shop.getType());
			//查询商品分类
			List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(shopNo);
			for(ShopType st : shopTypeList){
				st.setImgPath(ImageConstants.getCloudstZoomPath(st.getImgPath(), owTemplateGt.getGoodTypeImageSize()));
			}
			model.addAttribute("shopTypeList", shopTypeList);
			//模板code
            String code = dto.getOwTemplateHp().getCode();
			/** 首页 */
			if("V31".equals(dto.getOwTemplateHp().getTemplateDtoVersion())){
				//3.1期模板
				Pagination<GoodVo> pagination = null;
				//首页商品
				if("HP_GW_WSC_V31_1".equals(code)){
					goodVo = new GoodVo();
					goodVo.setShopNo(shopNo);
					goodVo.setIsSpreadIndex(1);
					goodVo.setStatus(Constants.VALID);//正常状态
					pagination = new Pagination<GoodVo>();
					goodVo.setServiceId(Constants.SERVICE_ID_COMMON);
					/*pagination = goodBusiness.searchPageGood(goodVo, pagination);*/
					pagination = goodBusiness.findGoodVoPageForWap(shopNo, goodVo, pagination);
					model.addAttribute("hpGoodList", pagination.getDatas());
				}
				//查询商品分类和商品
				if("HP_GW_WSC_V31_3".equals(code) || "HP_GW_WSC_V31_4".equals(code) || "HP_GW_WSC_V31_5".equals(code)){
					/*List<ShopTypeVo> shopTypeAndGoodList = shopTypeBusiness.findShopTypeListAndGoodByShopNo(shopNo, Constants.SHOP_TYPE_NUM, 4);*/
					List<ShopTypeVo> shopTypeAndGoodList = shopTypeBusiness.findShopTypeListAndGoodForWap(shopNo, Constants.SHOP_TYPE_NUM, 4);
					model.addAttribute("shopTypeAndGoodList", shopTypeAndGoodList);
					// 查询商品数量
					if("HP_GW_WSC_V31_4".equals(code)) {
						// 查询商品数量
						goodVo = new GoodVo();
						goodVo.setShopNo(shopNo);
						GoodVo goodNumVo = goodBusiness.getGoodNum(goodVo);
						model.addAttribute("goodNumVo", goodNumVo);
					}
				}
				//特定模板的查询条件
				if("HP_GW_WSC_V31_2".equals(code)){
					goodVo = new GoodVo();
					goodVo.setShopNo(shopNo);
					if(null != shopTypeId && !"".equals(shopTypeId)){
						goodVo.setShopTypeId(Long.parseLong(shopTypeId));
					}
					pagination = new Pagination<GoodVo>();
					/*pagination = goodBusiness.searchPageGood(goodVo, pagination);*/
					pagination = goodBusiness.findGoodVoPageForWap(shopNo, goodVo, pagination);
					Long currentTime = System.currentTimeMillis();
					goodVo.setCurrentTime(currentTime);
					model.addAttribute("pagination", pagination);
					model.addAttribute("dto", goodVo);
					model.addAttribute("shopTypeId", shopTypeId);
					model.addAttribute("urlPath", "gwscIndex");
				}
				if("HP_GW_WSC_V31_6".equals(code)){
					goodVo.setShopNo(shopNo);
					pagination = new Pagination<GoodVo>();
					pagination = goodBusiness.findGoodVoPageForWap(shopNo, goodVo, pagination);
					Long currentTime = System.currentTimeMillis();
					goodVo.setCurrentTime(currentTime);
					model.addAttribute("pagination", pagination);
					model.addAttribute("goodVo", goodVo);
					model.addAttribute("layout", layout);
					model.addAttribute("shopNo", shopNo);
					Notice notice = noticeBussiness.getNoticeByShopNo(shopNo);
					model.addAttribute("notice", notice);
				}
				if(code.indexOf("GW_WSC") != -1){
					Integer num = Integer.valueOf(code.substring(code.lastIndexOf("_") + 1))+1;
					return "/wap/product/template/shop" + num + "/index";
				}
            }
            
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
            e.printStackTrace();
            return "/wap/common/error404";
        }
		return "/wap/index/empty";
	}
	
	/**
	 * 
	 * @Title: isFavorites 
	 * @Description: 判断店铺是否已收藏
	 * @param userId
	 * @param subbranchId
	 * @param shop
	 * @return
	 * @date 2015年9月18日 上午9:52:56  
	 * @author cbc
	 */
	private boolean isFavorites(Long userId, String subbranchId, Shop shop) {
		boolean isFavorites = false;
		if (null != userId) {
        	Favorites favorites = null;
        	if (null != subbranchId) {
        		favorites = favoritesBussiness.getFavorites(getUserId(), Favorites.TYPE_SUBBRANCH, String.valueOf(subbranchId));
			} else {
				favorites = favoritesBussiness.getFavorites(getUserId(), Favorites.TYPE_SHOP, shop.getNo());
			}
        	if (null != favorites && favorites.getStatus().intValue() == Constants.VALID) {
        		isFavorites = true;
        	}
        	
		}
		return isFavorites;
	}

	/**\
	 * 
	 * @Title: isMyOwnShop 
	 * @Description: 判断店铺是否是自己的店铺
	 * @param userId
	 * @param shop
	 * @param subbranchId
	 * @return
	 * @date 2015年9月18日 上午9:53:15  
	 * @author cbc
	 */
	private boolean isMyOwnShop(Long userId, Shop shop, Long subbranchId) {
		boolean boo = false;
		if (null != userId) {
			if (subbranchId != null) {
				Subbranch subbranch = subbranchBusiness.findSubbranchById(subbranchId);
				boo = (subbranch.getUserId().longValue() == userId);
			}
			if (shop.getUserId().longValue() == userId) {
				boo = true;
			}
		}
		return boo;
	}

	/**
	 * 
	 * @Title: checkIsMaxBranchLevel 
	 * @Description: 验证该分店已经是最大代理等级 
	 * @param subId
	 * @return 如果是最大代理等级，返回true
	 * 			如果不是最大代理等级，则可以继续申请，返回false
	 * @date 2015年7月6日 下午1:57:55  
	 * @author cbc
	 */
	private boolean checkIsMaxBranchLevel(Long subId) {
		String maxBranchLevel = PropertiesUtil.getContexrtParam(MAX_BRANCH_LEVEL);
		AssertUtil.hasLength(maxBranchLevel, "subbranchConfig.properties未配置maxBranchLevel字段");
		List<Subbranch> subbranchList = subbranchProducer.findSubbranchList(subId);
		if (null != subbranchList) {
			if (subbranchList.size() >= Integer.valueOf(maxBranchLevel)) {
				//说明已经是最大代理等级，返回true
				return true;
			}
		}
		return false;
	}
}
