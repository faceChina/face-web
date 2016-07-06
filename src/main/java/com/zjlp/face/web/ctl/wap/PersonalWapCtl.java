package com.zjlp.face.web.ctl.wap;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.member.business.MemberBusiness;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.user.favorites.bussiness.FavoritesBussiness;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.favorites.domain.dto.FavoritesDto;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.util.ShualianUtil;

@Controller
@RequestMapping("/wap/{shopNo}/buy/personal/")
public class PersonalWapCtl extends WapCtl {

	@Autowired
	private UserBusiness userBussiness;
	@Autowired
	private MemberBusiness memberBusiness;
	@Autowired
	private FavoritesBussiness favoritesBussiness;
	@Autowired
	private OperateDataBusiness operateDataBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private AccountBusiness accountBusiness;
	
	/**
	 * 个人中心首页
	 * @Title: index 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午9:21:43  
	 * @author lys
	 */
	@RequestMapping("index")
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) {
		User user = userBussiness.getUserById(getUserId());
		UserVo userVo = new UserVo(user);
		String protocolShop = String.valueOf(PropertiesUtil.getContextProperty(Constants.PROTOCOL_GOOD_SHOPNO));
		model.addAttribute("protocolShopNo", protocolShop);
		model.addAttribute("user", userVo);
		Shop shop = super.getShop();
		//是否签到
		boolean hasRegistratRule = memberBusiness.hasRegistratRule(shop.getNo());
		
		MemberCardDto dto = new MemberCardDto(shop.getUserId(), super.getUserId());
		dto = memberBusiness.getMemberCardForWap(dto);
		if(null != dto) {
			if (hasRegistratRule) {
				boolean isRegist = memberBusiness.isTodayAttendance(dto.getId());
				model.addAttribute("isRegist", isRegist);
			}
			//model.addAttribute("cardId", dto.getId());
			model.addAttribute("memberCardDto", dto);
		}
		
		Integer newOrder = 0;
		Integer payOrder = 0;
		Integer deliverOrder = 0;
		newOrder = operateDataBusiness.countBuyerOrderNumber(getUserId(), 1);
		payOrder = operateDataBusiness.countBuyerOrderNumber(getUserId(), 2);
		deliverOrder = operateDataBusiness.countBuyerOrderNumber(getUserId(), 3);
		//获取收藏的商品和店铺数量
		List<Integer> list = new ArrayList<Integer>();
		list.add(Favorites.TYPE_GOOD);
		//商品收藏数量
		Integer goodCount = favoritesBussiness.countFavorites(getUserId(), list, Constants.VALID);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Favorites.TYPE_SHOP);
		list1.add(Favorites.TYPE_SUBBRANCH);
		//店铺收藏数量
		Integer shopCount = favoritesBussiness.countFavorites(getUserId(), list1, Constants.VALID);
		model.addAttribute("goodCount", goodCount);
		model.addAttribute("shopCount", shopCount);
		model.addAttribute("hasRegistratRule", hasRegistratRule);
		model.addAttribute("newOrderNumber", newOrder);
		model.addAttribute("payOrderNumber", payOrder);
		model.addAttribute("deliverOrderNumber", deliverOrder);
		model.addAttribute("isWechat", super.getIsWechat(user));
		
		/**
		 * 判断是否来源于刷脸APP
		 */
		model.addAttribute("shopApp", ShualianUtil.isShualianBrowser(request));
		/**
		 * 
		 */
		/**
		 * 是否设置支付密码
		 */
		boolean isPayment = accountBusiness.existPaymentCode(getUserId());
		model.addAttribute("isPayment", isPayment);
		/**
		 * 
		 */
		
		/**
		 * 从cookie中获取是否第一登录
		 */
		boolean isFirstCookie = false;
		
		//从cookie中获取用户是否第一次登录
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if(("isFirstCookie_"+user.getId()).equals(cookie.getName())){
				model.addAttribute("isFirstCookie", true);
				isFirstCookie = true;
				break;
			}
		}
		
		//第一次登录,打进cookie中
		if(!isFirstCookie){
			model.addAttribute("isFirstCookie", false);
			Cookie cookie = new Cookie("isFirstCookie_"+user.getId(), "true");
			//cookie失效时间1年
			cookie.setMaxAge(60*60*24*30*12);
			response.addCookie(cookie);
		}
		/**
		 * 
		 */
		
		return "/wap/user/personal/index";
	}

	/**
	 * 编辑页面
	 * @Title: editPage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午9:21:33  
	 * @author lys
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String editPage(Model model) {
		User user = userBussiness.getUserById(getUserId());
		UserVo vo = new UserVo(user);
		model.addAttribute("user", vo);
		model.addAttribute("isWechat", super.getIsWechat(user));
		return "/wap/user/personal/amend";
	}

	/**
	 * 编辑用户
	 * @Title: edit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param headimgurl 头像
	 * @param nickname 昵称
	 * @param email 邮箱
	 * @return
	 * @date 2015年3月23日 下午9:19:22  
	 * @author lys
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(String headimgurl,
			@RequestParam String nickname, @RequestParam(required = false) String email) {
		userBussiness.editProfile(super.getUserId(), headimgurl, nickname, email);
		return super.getRedirectUrl("index");
	}
	
	/**
	 * 
	 * @Title: favorites 
	 * @Description: 列表查询
	 * @param model
	 * @param type
	 * @param status
	 * @param pagination
	 * @return
	 * @date 2015年9月1日 下午5:08:40  
	 * @author cbc
	 */
	@RequestMapping("favorites/{type}/{status}")
	public String favorites(Model model, @PathVariable Integer type, @PathVariable Integer status, Pagination<FavoritesDto> pagination) {
		if ((type == 1 || type == 2) && (status == 0 || status == 1)) {
			model.addAttribute("status", status);
			if (status == 1) {
				status = null;
			}
			//店铺收藏数量
			if (type.intValue() == 1) {
				//商品
				List<Integer> list = new ArrayList<Integer>();
				list.add(Favorites.TYPE_GOOD);
				pagination = favoritesBussiness.findFavorites(pagination, getUserId(), list, status);
				
				List<Integer> goodList = new ArrayList<Integer>();
				goodList.add(Favorites.TYPE_GOOD);
				//商品收藏数量
				Integer goodCount = favoritesBussiness.countFavorites(getUserId(), goodList, status);
				model.addAttribute("goodCount", goodCount);
			}
			if (type.intValue() == 2) {
				//店铺
				List<Integer> list = new ArrayList<Integer>();
				list.add(Favorites.TYPE_SHOP);
				list.add(Favorites.TYPE_SUBBRANCH);
				pagination = favoritesBussiness.findFavorites(pagination, getUserId(), list, status);
				
				List<Integer> shopList = new ArrayList<Integer>();
				shopList.add(Favorites.TYPE_SHOP);
				shopList.add(Favorites.TYPE_SUBBRANCH);
				Integer shopCount = favoritesBussiness.countFavorites(getUserId(), shopList, status);
				model.addAttribute("shopCount", shopCount);
			}
			model.addAttribute("pagination", pagination);
			model.addAttribute("type", type);
			
			return "/wap/user/personal/favorites/collectlist";
		} else {
			return "/wap/common/error404";
		}
	}
	
	/**
	 * 
	 * @Title: favoritesAppend 
	 * @Description: 分页加载
	 * @param model
	 * @param type
	 * @param status
	 * @param pagination
	 * @return
	 * @date 2015年9月1日 下午7:06:22  
	 * @author cbc
	 */
	@RequestMapping(value="favorites/append", method=RequestMethod.POST)
	public String favoritesAppend(Model model, Integer type, Integer status, Pagination<FavoritesDto> pagination) {
		if (type.intValue() == 1) {
			//商品
			List<Integer> list = new ArrayList<Integer>();
			list.add(Favorites.TYPE_GOOD);
			pagination = favoritesBussiness.findFavorites(pagination, getUserId(), list, status);
		}
		if (type.intValue() == 2) {
			//店铺
			List<Integer> list = new ArrayList<Integer>();
			list.add(Favorites.TYPE_SHOP);
			list.add(Favorites.TYPE_SUBBRANCH);
			pagination = favoritesBussiness.findFavorites(pagination, getUserId(), list, status);
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("type", type);
		model.addAttribute("status", status);
		return "/wap/user/personal/favorites/append";
	}
	
	/**
	 * 
	 * @Title: toShop 
	 * @Description: 店铺跳转
	 * @param model
	 * @param id
	 * @return
	 * @date 2015年9月4日 下午2:34:03  
	 * @author cbc
	 */
	@RequestMapping("favorites/toShop/{id}")
	public String toShop(Model model, @PathVariable Long id) {
		Favorites favorites = favoritesBussiness.getFavorites(id);
		if (null != favorites) {
			if (favorites.getRemoteType().intValue() == 2) {
				Shop shop = shopBusiness.getShopByNo(favorites.getRemoteId());
				return super.getRedirectUrl("/wap/"+shop.getNo()+"/any/gwscIndex");
			}
			if (favorites.getRemoteType().intValue() == 3) {
				Subbranch subbranch = subbranchBusiness.findSubbranchById(Long.valueOf(favorites.getRemoteId()));
				return super.getRedirectUrlNoHtm("/wap/"+subbranch.getSupplierShopNo()+"/any/gwscIndex.htm?subbranchId="+subbranch.getId());
			}
		}
		return null;
	}
	
}
