package com.zjlp.face.web.ctl.wap;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.http.filter.SubbranchFilter;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.GoodDetailVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.trade.cart.producer.CartProducer;
import com.zjlp.face.web.server.trade.coupon.bussiness.CouponBussiness;
import com.zjlp.face.web.server.trade.order.domain.vo.RspSelectedSkuVo;
import com.zjlp.face.web.server.user.favorites.bussiness.FavoritesBussiness;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.ShualianUtil;

@Controller
@RequestMapping("/wap/{shopNo}/any/")
public class GoodWapCtl extends WapCtl{
	Logger log=LoggerFactory.getLogger(getClass());
	@Autowired
	private GoodBusiness goodBusiness;
	@Autowired
	private CartProducer cartProducer;
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;
	@Autowired
	private MemberProducer memberProducer;
	@Autowired
	private FavoritesBussiness favoritesBussiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private CouponBussiness couponBussiness;
	@Autowired
	private SubbranchProducer subbranchProducer;
	@Autowired
	private UserBusiness userBusiness;
	
	private static final String MAX_BRANCH_LEVEL = "maxBranchLevel";
	
	
	/**
	 * 查询商品列表
	 * @Title: listGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodVo
	 * @param pagination
	 * @param model
	 * @return
	 * @date 2015年5月8日 下午4:29:23  
	 * @author dzq
	 */
	@RequestMapping(value="list")
	public String listGood(GoodVo goodVo,Pagination<GoodVo> pagination,Model model, String layout){
		Shop shop = super.getShop();
    	
		goodVo.setShopNo(shop.getNo());//限定条件：店铺号
		
		goodVo.setStatus(Constants.GOOD_STATUS_DEFAULT);//限定条件：正常状态
		
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(goodVo.getShopNo());
		
		if(null != goodVo.getShopTypeId()) {
			
			ShopType shopType = shopTypeBusiness.getShopTypeById(goodVo.getShopTypeId());
			
			goodVo.setShopTypeName(shopType.getName());
		}
		goodVo.setServiceId(Constants.SERVICE_ID_COMMON);//条件限定:非预约商品
		pagination =  goodBusiness.searchPageGood(goodVo,pagination);
		
		model.addAttribute("shopTypeList",shopTypeList);
		
		model.addAttribute("goodVo",goodVo);
		
		model.addAttribute("pagination",pagination);
		model.addAttribute("layout", layout);
		model.addAttribute("newGoodMark", goodVo.getNewGoodMark());
		
		return "/wap/product/good/goods-list";
	}
	
	/**
     * 商品列表分页数据
     * 
     * @Title: listAppend
     * @Description: (这里用一句话描述这个方法的作用)
     * @param goodDto
     * @param pagination
     * @return
     * @date 2014年8月12日 上午11:01:35
     * @author dzq
     */
    @RequestMapping(value = "append", method = RequestMethod.POST)
    @ResponseBody
    public String listAppend(GoodVo goodVo,Pagination<GoodVo> pagination){
    	
        try{
//        	Shop shop = super.getShop();
        	
    		goodVo.setShopNo(super.getShopNo());//限定条件：店铺号
    		
    		goodVo.setStatus(Constants.GOOD_STATUS_DEFAULT);//限定条件：正常状态
			goodVo.setServiceId(Constants.SERVICE_ID_COMMON);//条件限定:非预约商品
			pagination =  goodBusiness.searchPageGood(goodVo,pagination);
            return super.getReqJson(true, JSONObject.fromObject(pagination).toString());
        }catch(Exception e){
        	
            return super.getReqJson(false, "加载数据失败");
        }
    }
    
	
	/**
	 * 买家查询商品详情
	 * @throws IOException 
	 * @Title: getDetail 
	 */
	@RequestMapping(value="item/{goodIndex}",method=RequestMethod.GET)
	public String getDetail(@PathVariable String goodIndex,Model model,HttpServletRequest request,HttpServletResponse response, @PathVariable(value="shopNo") String urlShopNo, HttpSession session) throws IOException{
		
		GoodDetailVo goodDetailVo = null;
		
		goodDetailVo = goodBusiness.getGoodDetailById(Long.valueOf(goodIndex));
		/** 仙居专用类目跳转至外链*/
		if(goodDetailVo.getGood().getClassificationId().intValue()==Constants.LEIMU_MENHU){
			response.sendRedirect(goodDetailVo.getGood().getOuterLink());
			return null;
		}
		if(null!=getUserId()){
			Long memberPrice=memberProducer.getDiscountPrice(getUserId(), getShopNo(), goodDetailVo.getGood().getSalesPrice(), goodDetailVo.getGood().getPreferentialPolicy());
			model.addAttribute("memberPrice", memberPrice);
		}
		model.addAttribute("goodDetailVo", goodDetailVo);
		model.addAttribute("goodDetailVoJson", goodDetailVo.getObjectJson());
		Long userId = super.getUserId();
		
		Integer cartCount = 0;
		
		boolean isFavorite = false;
		boolean isLogin = false;
		
		if (null != userId) {
			isLogin = true;
			cartCount = cartProducer.getCount(userId);
			Favorites favorites = favoritesBussiness.getFavorites(getUserId(), Favorites.TYPE_GOOD, goodIndex);
			if (null != favorites && favorites.getStatus().intValue() == Constants.VALID) {
				isFavorite = true;
			}
		}
		
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("cartCount", cartCount);
		model.addAttribute("isFavorite", isFavorite);
		
		/**
		 * 新增选择分类名称
		 */
		if(goodDetailVo.getSalesPropMap()!=null && goodDetailVo.getSalesPropMap().size()>0){
			StringBuffer propName = new StringBuffer();
			Iterator<String> iterator = goodDetailVo.getSalesPropMap().keySet().iterator();
			while(iterator.hasNext()){
				propName.append(iterator.next() + "&nbsp;");
			}
	
			model.addAttribute("propName", propName);
		}
		/**
		 * 
		 */
		Shop shop = super.getShop(); 
		model.addAttribute("recruitButton", shop.getRecruitButton());
		String subbranchIdStr = SubbranchFilter.getSubbranchId(session);
		Long subbranchId = null;
		boolean isMaxSubbranchLevel = false;
		if (StringUtils.isNotBlank(subbranchIdStr)) {
			subbranchId = Long.valueOf(subbranchIdStr);
			model.addAttribute("subbranchId", subbranchId);
			isMaxSubbranchLevel = checkIsMaxBranchLevel(subbranchId);
		}
		
		boolean isMyOwnShop = this.isMyOwnShop(userId, shop, subbranchId);
		model.addAttribute("isMaxSubbranchLevel",isMaxSubbranchLevel);
		model.addAttribute("isMyOwnShop", isMyOwnShop);
		model.addAttribute("urlShopNo", urlShopNo);
		

		/**
		 * 判断是否来源于刷脸APP
		 */
		model.addAttribute("shopApp", ShualianUtil.isShualianBrowser(request));
		
		/**
		 * 判断是微信用户
		 */
		if (null == getUserId()) {
			model.addAttribute("isWechat", false);
		}else {
			User user = userBusiness.getUserById(getUserId());
			model.addAttribute("isWechat", getIsWechat(user));
		}
		
		/**
		 * 新增店铺定位信息
		 */
		ShopLocationDto shopLocationDto = null;
		String shopNo = null;
		if(goodDetailVo!=null && goodDetailVo.getGood()!=null){
			shopNo = goodDetailVo.getGood().getShopNo();
		}
		
		try {
			shopLocationDto = shopBusiness.getShopLocation(shopNo);
		} catch (Exception e) {
			
		}

		session.setAttribute("myshopLocation", shopLocationDto);

		/**
		 * 
		 */
		Integer couponSetCount = couponBussiness.countShopValidCouponSet(shopNo);
		model.addAttribute("couponSetCount", couponSetCount);
		
		return "/wap/product/good/goods-details";
	}
	
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
	
	/**
	 * 卖家查看商品详情
	 * @throws IOException 
	 * @Title: getDetail 
	 */
	@RequestMapping(value="item/seller/{goodIndex}",method=RequestMethod.GET)
	public String getSellerDetail(@PathVariable String goodIndex,Model model,HttpServletResponse response) throws IOException{
		
		GoodDetailVo goodDetailVo = null;
		
		goodDetailVo = goodBusiness.getSellerGoodDetailById(Long.valueOf(goodIndex));
		/** 仙居专用类目跳转至外链*/
		if(goodDetailVo.getGood().getClassificationId().intValue()==Constants.LEIMU_MENHU){
			response.sendRedirect(goodDetailVo.getGood().getOuterLink());
			return null;
		}
		if(null!=getUserId()){
			Long memberPrice=memberProducer.getDiscountPrice(getUserId(), getShopNo(), goodDetailVo.getGood().getSalesPrice(), goodDetailVo.getGood().getPreferentialPolicy());
			model.addAttribute("memberPrice", memberPrice);
		}
		model.addAttribute("goodDetailVo", goodDetailVo);
		model.addAttribute("goodDetailVoJson", goodDetailVo.getObjectJson());
		
		return "/wap/product/good/seller-goods-details";
	}
	
	/**
	 * 查询普通商品SKU
	 * @Title: selectSku 
	 */
	@RequestMapping(value="selectSku",method=RequestMethod.POST)
	@ResponseBody
	public String selectSku(@RequestParam Long goodId,@RequestParam String skuProperties){
		try {
			RspSelectedSkuVo rssv = null;
			//普通商品SKU查询
			rssv = goodBusiness.selectGoodskuByGoodIdAndPrprerty(goodId,skuProperties);
			return super.getReqJson(true, JSONObject.fromObject(rssv).toString());
		} catch (GoodException e) {
			return super.getReqJson(false, "操作失败");
		}
	}
	@RequestMapping(value="test")
	@ResponseBody
	public String test(HttpServletRequest request) throws Exception{
		String result=null;
		if(request.getRequestURL().toString().indexOf("192.168.")!=-1 || request.getRequestURL().toString().indexOf("218.244")!=-1){
			/*JSONObject obj=new JSONObject();
			obj.put("phone", "18112345678");
			obj.put("mobilecode", "123456");
			obj.put("invitationCode", "926908");
			obj.put("pwd", "123456");
			result=ContextLoaderListener.getCurrentWebApplicationContext().getBean(AnyAppCtl.class).registeWithFreeShop(obj);
			System.out.println(result);*/
		}
		return result;
	}
	/**
	 * @Description: 校验商品状态
	 * @param goodId
	 * @return
	 * @date: 2015年4月7日 上午9:45:54
	 * @author: zyl
	 */
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	@ResponseBody
	public String validate(@RequestParam Long goodId){
		Good good = goodBusiness.getGoodById(goodId);
		if(null != good && good.getStatus() == 1){
			return super.getReqJson(true, "");
		}else{
			return super.getReqJson(false, "");
		}
	}
	
}
