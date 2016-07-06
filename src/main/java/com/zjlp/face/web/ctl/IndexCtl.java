package com.zjlp.face.web.ctl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.shop.exception.ShopException;
import com.zjlp.face.account.domain.Account;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.http.session.UserSessionManager;
import com.zjlp.face.web.server.operation.member.business.MemberBusiness;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.WeixinUtil;

@Controller
@RequestMapping("/u/")
public class IndexCtl extends BaseCtl{
	private Log log=LogFactory.getLog(getClass());
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private TemplateBusiness templateBusiness;
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private MemberBusiness memberBusiness;
	
    @RequestMapping(value = "index")
	public String RoleList(Model model,HttpServletRequest request,HttpServletResponse response){
    	String loginType =(String) request.getSession().getAttribute("loginType");
    	if("wap".equals(loginType)){
    		
    		Shop shop = (Shop) request.getSession().getAttribute("shop");
    		if(WeixinUtil.isWechatBrowser(request) && null != super.getUserId() && null != shop &&
    				Constants.AUTHENTICATE_TYPE_CERTIFIED.equals(shop.getAuthenticate())){
    			
				String openId = (String) request.getSession().getAttribute(shop.getNo()+"shopOpenId");
				// 绑定会员卡
				User user = new User();
				user.setId(super.getUserId());
				memberBusiness.bindMemberCard(openId, user, shop);
			}
    		if(null == shop){
    			return "redirect:/accessDenied" + EXT;
    		}
    		String redirUrl = (String)request.getSession().getAttribute("redirUrl");
    		request.getSession().removeAttribute("redirUrl");
    		if(null != redirUrl){
    			return "redirect:" + redirUrl;
    		}
    		return "redirect:/wap/"+shop.getNo()+"/any/index" + EXT;
    	} 
    	
    	if("free".equals(loginType)) {
    		// 根据用户id查询免费店铺
    		Shop shop = shopBusiness.getShopByUserId(super.getUserId());
    		if(null == shop) {
    			return "redirect:/free/generateShopName" + EXT;
    		}
    		String redirUrl = (String) request.getSession().getAttribute("redirUrl");
			if (StringUtils.isNotBlank(redirUrl)) {
				return "redirect:" + redirUrl;
			}
    		return "redirect:/free/index" + EXT;
    	}
		//所属官网商城
    	Long userId = super.getUserId();
    	Account account = accountBusiness.getAccountByUserId(userId);
		List<ShopDto> shopList = shopBusiness.findShopList(userId, null, null);
		model.addAttribute("shopList", shopList);
		model.addAttribute("amount", account.getWithdrawAmount());
		return "/m/index/index";
	}
    
    
	
	@RequestMapping(value="returnIndex")
	public String returnIndex(Model model,HttpServletRequest request){
		request.getSession().removeAttribute("shopInfo");
		Long userId = super.getUserId();
		if (null != userId) {
			//所属官网商城
	    	Account account = accountBusiness.getAccountByUserId(userId);
			List<ShopDto> shopList = shopBusiness.findShopList(userId, null, null);
			model.addAttribute("shopList", shopList);
			model.addAttribute("amount", account.getWithdrawAmount());
		}
		return "/m/index/index";
	}
	
    
    
    
	/**
	 * 检查产品是否过期
	 * @Title: checkDate 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param no
	 * @param model
	 * @return
	 * @date 2014年8月9日 下午3:19:52  
	 * @author Administrator
	 */
	@RequestMapping(value="index/checkDate")
	@ResponseBody
	public String checkDate(String no,Model model){
		 return shopBusiness.checkDate(no);
	}
	
	
	
	/**
	 * 进入店铺
	 * @Title: check 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param no
	 * @param model
	 * @param session
	 * @return
	 * @date 2015年1月16日 上午9:37:24  
	 * @author fjx
	 */
	@RequestMapping(value="index/check")
	public String check(String no,Model model,HttpServletRequest request){
		if(shopBusiness.checkIntoShop(no,super.getUserId())){
			Shop shop = shopBusiness.getShopByNo(no);
			if( Constants.SHOP_GW_TYPE.intValue() == shop.getType().intValue() ){
				OwTemplateDto owTemplateScHp = templateBusiness.getCurrentHomePageOwTemplate(shop.getNo(), Constants.SHOP_SC_TYPE);
				request.getSession().setAttribute("owTemplateScHp", owTemplateScHp);
			}
			OwTemplateDto owTemplateHp = templateBusiness.getCurrentHomePageOwTemplate(shop.getNo(), shop.getType());
			OwTemplateDto owTemplateGt = templateBusiness.getCurrentGoodTypePageOwTemplate(shop.getNo(), shop.getType());
			
			request.getSession().setAttribute("owTemplateHp", owTemplateHp);
			request.getSession().setAttribute("owTemplateGt", owTemplateGt);
			request.getSession().setAttribute("shopInfo", shop);
			return super.getRedirectUrl("shopIndex");
		}
		return "/m/common/error404";
	}
	
	@Autowired
	private GoodBusiness goodBusiness;
	@RequestMapping(value="index/shopIndex")
	public String sohpIndex(Model model,HttpSession session){
		Shop shop = (Shop)session.getAttribute("shopInfo");
		if(null == shop){
			return super.getRedirectUrl("/u/index");
		}
		Integer countTodayAll=salesOrderBusiness.countTodayAll(shop.getNo());
		model.addAttribute("countTodayAll", countTodayAll);
		Integer countPay=salesOrderBusiness.countPay(shop.getNo());
		model.addAttribute("countPay", countPay);
		Long yesterdayIncome=salesOrderBusiness.getYesterdayIncomeByShopNo(shop.getNo());
		model.addAttribute("yesterdayIncome", yesterdayIncome);
		//获取在售商品数量
		Integer onSellCount = goodBusiness.getOnSellCount(shop.getNo());
		model.addAttribute("onsell", onSellCount);
		//获取下架商品数量
		Integer unShelveCount = goodBusiness.getUnShelveCount(shop.getNo());
		model.addAttribute("unshelve", unShelveCount);
		//获取代付款代理和推广订单数量
		Integer waitForPayAgencyOrderCount = null;
		//获取代发货代理和推广订单数量
		Integer wairForSendAgencyOrderCount = null;
		Integer salesAgencyGoodCount = null;
		Integer warehouseAgencyGoodCount = null;
//		if (1 == shop.getIsFree().intValue()) {
//			//说明是供货商
//			//获取代理在售商品数量
//			salesAgencyGoodCount = this.goodBusiness.findGHSSalesAgencyGoodCount(shop.getNo());
//			//获取代理仓库商品
//			warehouseAgencyGoodCount = this.goodBusiness.findGHSWarehouseAgencyGoodCount(shop.getNo());
//			waitForPayAgencyOrderCount = this.salesOrderBusiness.findSupplierWaitForPayAgencyOrderCount(shop.getNo());
//			wairForSendAgencyOrderCount = this.salesOrderBusiness.findSupplierWaitForSendAgencyOrderCount(shop.getNo());
//		} else {
//			//说明是分销商
//			salesAgencyGoodCount = this.goodBusiness.findFXSSalesAgencyGoodCount(shop.getNo());
//			warehouseAgencyGoodCount = this.goodBusiness.findFSXWarehouseAgencyGoodCount(shop.getNo());
//			waitForPayAgencyOrderCount = this.salesOrderBusiness.findWaitForPayAgencyOrder(shop.getNo());
//			wairForSendAgencyOrderCount = this.salesOrderBusiness.findWaitForSendAgencyOrder(shop.getNo());
//		}
		model.addAttribute("waitForPayAgencyOrderCount", waitForPayAgencyOrderCount);
		model.addAttribute("wairForSendAgencyOrderCount", wairForSendAgencyOrderCount);
		model.addAttribute("salesAgencyGoodCount", salesAgencyGoodCount);
		model.addAttribute("warehouseAgencyGoodCount", warehouseAgencyGoodCount);
		if(Constants.SHOP_GW_TYPE.equals(shop.getType())){
			return "/m/index/gw-index";
		}
		if(Constants.SHOP_SC_TYPE.equals(shop.getType())){
//			if (1 == shop.getIsFree().intValue()) {
//				//说明是供货商,分为内部和外部
//				if (null != shop.getProxyType()) {
//					if (2== shop.getProxyType().intValue()) {
//						//说明是外部
//						//获取待审核代理商数量
//						Integer waitForCheckShopCount = this.agencyBusiness.findWaitForCheckAgencyShopCount(shop.getNo());
//						//获取已通过的代理商数量
//						Integer passedAgencyShopCount = this.agencyBusiness.findPassedAgencyShopCount(shop.getNo());
//						model.addAttribute("waitForCheckShopCount", waitForCheckShopCount);
//						model.addAttribute("passedAgencyShopCount", passedAgencyShopCount);
//					} 
//				}
//			} else {
//				//说明是分销商
//				//查询是否有下一级代理商
//				Integer subordinateCount = this.agencyBusiness.findSubordinateShopCount(shop.getNo());
//				if (subordinateCount.intValue() > 0) {
//					//说明有下一级
//					//获取下级代理商待付款代理订单数量
//					Integer subordinateWaitForPayOrderCount = this.salesOrderBusiness.findSubordinateAgencyShopWaitForPayOrderCount(shop.getNo());
//					//获取下级代理商代发货代理订单数量
//					Integer subordinateWaitForSendOrderCount = this.salesOrderBusiness.findSubordinateAgencyShopWaitForSendOrderCount(shop.getNo());
//					model.addAttribute("subordinateWaitForPayOrderCount", subordinateWaitForPayOrderCount);
//					model.addAttribute("subordinateWaitForSendOrderCount", subordinateWaitForSendOrderCount);
//				}
//				model.addAttribute("subordinateCount", subordinateCount);
//			}
			return "/m/index/sc-index";
		}
		return "/m/common/error404";
	}
	@RequestMapping(value="activate",method=RequestMethod.POST)
	@ResponseBody
	public String activate(String code){
		try{
			String loginAccount = UserSessionManager.getLoginUser().getUsername();
			System.out.println(loginAccount);
			Integer states = shopBusiness.activateShop(code, loginAccount);
			return super.getReqJson(true, states.toString());
		}catch(Exception e){
			log.error(e.getMessage(), e);
			return super.getReqJson(false, null);
		}
	}
	
	@RequestMapping(value = "bindshop", method = RequestMethod.POST)
	@ResponseBody
	public String bindshop(Shop shop, Model model){
		try{
			shopBusiness.bindshop(shop);
			return super.getReqJson(true, null);
		}catch(ShopException e){
			log.info(e);
			return super.getReqJson(false, e.getMessage());
		}catch(Exception e){
			log.info(e);
			return super.getReqJson(false, "绑定公众号失败");
		}
	}
	
	/**
	 * 公众号解绑
	 * @param shop
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "unbindshop", method = RequestMethod.POST)
	@ResponseBody
	public String unbindshop(Shop shop, Model model){
		try{
			shopBusiness.unbindshop(shop);
			return super.getReqJson(true, null);
		}catch(com.zjlp.face.web.exception.ext.ShopException e){
			log.info(e);
			return super.getReqJson(false, e.getMessage());
		}catch(Exception e){
			log.info(e);
			return super.getReqJson(false, "解绑公众号失败");
		}
	}
	
	
	@RequestMapping(value="crumbs/init")
	@ResponseBody
	public String menuInit(HttpServletRequest request, Model model){
		Shop shop = (Shop)request.getSession().getAttribute("shopInfo");
		if(null == shop){
			return "user"; 
		}
		if(Constants.SHOP_GW_TYPE.equals(shop.getType())){
			return "gw"; 
		}
		if(Constants.SHOP_SC_TYPE.equals(shop.getType())){
			return "sc"; 
		}
		return "";
	}
	
	
	
}
 
