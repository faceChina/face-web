package com.zjlp.face.web.ctl.wap;



import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.CouponException;
import com.zjlp.face.web.http.filter.SubbranchFilter;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.trade.coupon.bussiness.CouponBussiness;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;

@Controller
@RequestMapping("/wap/{shopNo}/")
public class CouponWapCtl extends WapCtl {
	
	@Autowired
	private CouponBussiness couponBussiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	
	private Logger _logger = Logger.getLogger(getClass());

	/**
	 * 
	 * @Title: receive 
	 * @Description: 优惠券页面
	 * @return
	 * @date 2015年9月24日 下午7:12:01  
	 * @author cbc
	 */
	@RequestMapping(value="any/coupon/{couponSetId}", method=RequestMethod.GET)
	public String show(@PathVariable Long couponSetId, Long subbranchId, Model model) {
		CouponSet couponSet = couponBussiness.getCouponSetById(couponSetId);
		if (couponSet == null) {
			return "/wap/index/empty";
		}
		Shop shop = shopBusiness.getShopByNo(couponSet.getShopNo());
		if (subbranchId != null) {
			SubbranchVo subbranch = subbranchBusiness.getActiveSubbranchByUserId(subbranchId);
			model.addAttribute("subbranch", subbranch);
		}
		model.addAttribute("shop", shop);
		model.addAttribute("couponSet", couponSet);
		Long userId = getUserId();
		model.addAttribute("isLogin", userId != null);
		Integer conponStatus = null;
		conponStatus = this.getConponStatus(couponSet, userId);
		model.addAttribute("conponStatus", conponStatus);
		return "/wap/trade/coupon/shop-coupon-detail";
	}
	
	/**
	 * 
	 * @Title: getConponStatus 
	 * @Description: 优惠券状态 1可领取 2已领完 3已失效 4已领取 5已达到每人限领数量
	 * @param couponSet
	 * @param userId
	 * @return
	 * @date 2015年9月24日 上午11:38:10  
	 * @author cbc
	 */
	private Integer getConponStatus(CouponSet couponSet, Long userId) {
		Integer receiveNumber = couponBussiness.countHasReveivedCoupon(couponSet.getId());
		if (couponSet.getTimeStatus().equals(CouponSetDto.INVALID)) {
			return 3;
		}
		if (receiveNumber.longValue() >= couponSet.getCirculation().longValue()) {
			return 2;
		}
		if (userId != null) {
			boolean boo = couponBussiness.hasCoupon(couponSet.getId(), userId);
			if (boo) {
				return 4;
			}
//			if (couponSet.getSellerId().equals(userId)) {
//				return 5;
//			}
			Integer count = couponBussiness.countUserReceiveNumber(userId, couponSet.getId());
			if (count.compareTo(couponSet.getLimitNumber())>=0) {
				return 5;
			}
		}
		return 1;
	}
	
	@RequestMapping(value="buy/coupon/{couponId}", method=RequestMethod.GET)
	public String detail(@PathVariable Long couponId, Model model) {
		Coupon coupon = couponBussiness.getCouponById(couponId);
		Shop shop = shopBusiness.getShopByNo(coupon.getShopNo());
		if (coupon.getDrawRemoteType().equals(CouponDto.DRAW_TYPE_SUBBRANCH)) {
			SubbranchVo subbranch= subbranchBusiness.getActiveSubbranchByUserId(Long.valueOf(coupon.getDrawRemoteId()));
			model.addAttribute("subbranch", subbranch);
		}
		model.addAttribute("coupon", coupon);
		model.addAttribute("shop", shop);
		return "/wap/trade/coupon/detail";
	}
	
	/**
	 * 
	 * @Title: receive 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月24日 下午7:38:27  
	 * @author cbc
	 */
	@RequestMapping(value="buy/coupon/{couponSetId}", method=RequestMethod.POST)
	@ResponseBody
	public String receive(@PathVariable Long couponSetId, Long subbranchId, Model model) {
		try {
			SubbranchVo subbranch = null;
			if (subbranchId != null) {
				subbranch = subbranchBusiness.getActiveSubbranchByUserId(subbranchId);
			}
			Long userId = getUserId();
			Coupon coupon = new Coupon();
			coupon.setUserId(userId);
			if (null != subbranch) {
				coupon.setDrawRemoteType(CouponDto.DRAW_TYPE_SUBBRANCH);
				coupon.setDrawRemoteId(String.valueOf(subbranchId));
			} else {
				coupon.setDrawRemoteType(CouponDto.DRAW_TYPE_SHOP);
			}
			coupon.setCouponSetId(couponSetId);
			couponBussiness.reciveCoupon(coupon);
			return getReqJson(true, "领取成功");
		} catch (CouponException e) {
			_logger.error("领取优惠券失败", e);
			return getReqJson(false, e.getMessage());
		}	
	}
	
	/**
	 * 
	 * @Title: receiveNoLogin 
	 * @Description: 未登陆状态下点击领取优惠券效果
	 * @param couponSetId
	 * @param shopNo
	 * @param subbranchId
	 * @param model
	 * @return
	 * @date 2015年10月6日 上午9:05:49  
	 * @author cbc
	 */
	@RequestMapping(value="buy/couponNoLogin/{couponSetId}")
	public String receiveNoLogin(@PathVariable Long couponSetId, @PathVariable String shopNo, Long subbranchId, Model model) {
		if (subbranchId != null) {
			return getRedirectUrlNoHtm(new StringBuilder("/wap/").append(shopNo).append("/any/coupon/").append(couponSetId).append(".htm?subbranchId=").append(subbranchId).toString());
		} else {
			return getRedirectUrlNoHtm(new StringBuilder("/wap/").append(shopNo).append("/any/coupon/").append(couponSetId).append(".htm").toString());
		}
	}
	

	
	
	/**
	 * 买家查看店铺所有优惠券
	 * @Title: showAllCouponSet 
	 * @Description: 
	 * @param model
	 * @param pagination
	 * @return
	 * @date 2015年9月26日 上午9:42:11  
	 * @author cbc
	 */
	@RequestMapping(value="any/couponSet/list", method=RequestMethod.GET)
	public String showAllCouponSet(Model model, Pagination<CouponSetDto> pagination, HttpSession httpSession) {
		String subbranchId = SubbranchFilter.getSubbranchId(httpSession);
		if (StringUtils.isNotBlank(subbranchId)) {
			model.addAttribute("subbranchId", subbranchId);
		}
		String shopNo = super.getShopNo();
		Long buyerUserId = super.getUserId();
		pagination = couponBussiness.findCouponSetForBuyer(pagination, shopNo, buyerUserId);
		return "/wap/trade/coupon/shop-coupon-list";
	}
	
	@RequestMapping(value="any/couponSet/append", method=RequestMethod.POST)
	public String showAllCouponSetAppend(Model model, Pagination<CouponSetDto> pagination) {
		String shopNo = super.getShopNo();
		Long buyerUserId = super.getUserId();
		pagination = couponBussiness.findCouponSetForBuyer(pagination, shopNo, buyerUserId);
		return "/wap/trade/coupon/shop-coupon-list-append";
	}
	
	/**
	 * 
	 * @Title: receiveNoLoginInGoodDetail 
	 * @Description: 商品详情页面未登录领取优惠券
	 * @param couponSetId
	 * @param shopNo
	 * @param model
	 * @return
	 * @date 2015年10月6日 下午5:01:07  
	 * @author cbc
	 */
	@RequestMapping(value="buy/couponNoLoginInGoodDetail")
	public String receiveNoLoginInGoodDetail(@PathVariable String shopNo, Model model) {
		return getRedirectUrlNoHtm(new StringBuilder("/wap/").append(shopNo).append("/any/couponSet/list.htm").toString());
	}
	
	/**
	 * 
	 * @Title: showAllCoupon 
	 * @Description: 买家查询自己的优惠券
	 * @param model
	 * @param pagination
	 * @return
	 * @date 2015年10月6日 上午11:26:32  
	 * @author cbc
	 */
	@RequestMapping(value="buy/coupon/list", method=RequestMethod.GET)
	public String showAllCoupon(Model model, Pagination<CouponDto> pagination, String show) {
		CouponDto couponDto = new CouponDto();
		couponDto.setUserId(getUserId());
		pagination = couponBussiness.findCouponPage(pagination, couponDto);
		Integer count = couponBussiness.countUserAllCoupon(getUserId());
		model.addAttribute("pagination", pagination);
		model.addAttribute("count", count);
		model.addAttribute("show", show);
		return "/wap/trade/coupon/my-coupons-list";
	}
	
	@RequestMapping(value="buy/coupon/append", method=RequestMethod.POST)
	public String appendCoupon(Model model, Pagination<CouponDto> pagination) {
		CouponDto couponDto = new CouponDto();
		couponDto.setUserId(getUserId());
		pagination = couponBussiness.findCouponPage(pagination, couponDto);
		model.addAttribute("pagination", pagination);
		return "/wap/trade/coupon/my-coupons-list-append";
	}
	
	/**
	 * 
	 * @Title: deleteCoupon 
	 * @Description: 批量删除优惠券
	 * @return
	 * @date 2015年10月6日 上午11:29:06  
	 * @author cbc
	 */
	@RequestMapping(value="buy/coupon/delete", method=RequestMethod.POST)
	@ResponseBody
	public String deleteCoupon(String idStr, Model model, Boolean isAllSelected) {
		try {
			if (isAllSelected) {
				couponBussiness.deleteAllCoupon(getUserId());
			} else {
				String[] ids = idStr.split(",");
				couponBussiness.deleteCouponBatch(ids, getUserId());
			}
			return getReqJson(true, "删除成功");
		} catch (CouponException e) {
			return getReqJson(false, e.getMessage());
		}
	}
	
//	/**
//	 * 
//	 * @Title: rematchBestCoupon 
//	 * @Description: 重新获取最优匹配优惠券
//	 * @param shopNo
//	 * @param orderPrice
//	 * @return
//	 * @date 2015年9月26日 下午3:26:48  
//	 * @author cbc
//	 */
//	@RequestMapping(value="buy/coupon/rematch", method=RequestMethod.POST)
//	@ResponseBody
//	public String rematchBestCoupon(String shopNo, Long orderPrice) {
//		Long userId = super.getUserId();
//		Coupon coupon = couponBussiness.matchBestCoupon(userId, shopNo, orderPrice);
//		JSONObject fromObject = JSONObject.fromObject(coupon);
//		return fromObject.toString();
//	}
}
