package com.zjlp.face.web.ctl.app;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.CouponException;
import com.zjlp.face.web.server.trade.coupon.bussiness.CouponBussiness;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.util.AssConstantsUtil;

@Controller
@RequestMapping({"/assistant/ass/coupon/"})
public class CouponAppCtl extends BaseCtl {

	private Logger _logger = Logger.getLogger(getClass());
	
	private static final String[] COUPON_SET_LIST_FIELD = {"curPage","start","pageSize","totalRow", "datas.faceValue","datas.limitNumber", "datas.id", "datas.orderPrice", "datas.receiveNumber", "datas.usedNumber", "datas.circulation", "datas.effectiveTime"
		,"datas.endTime", "datas.currencyType", "datas.couponUrl", "notBegin", "onGoing", "invalid"};
	
	private static final String[] COUPON_FIELD = {"faceValue", "orderPrice","limitNumber", "circulation", "effectiveTime", "endTime", "currencyType"};
	
	private static final String[] SHOW_FIELD = {"conponStatus", "shopUrl", "shop.shopLogoUrl", "shop.name", "couponSet.faceValue", "couponSet.limitNumber", "couponSet.id", "couponSet.orderPrice", "couponSet.circulation", "couponSet.effectiveTime"
		, "couponSet.endTime", "couponSet.currencyType"};
	
	@Autowired
	private CouponBussiness couponBussiness;
	@Autowired
	private ShopBusiness shopBusiness;
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：商家增加优惠券设置<br>
	 * 
	 * @Title: setCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @date 2015年9月23日 上午9:15:11  
	 * @author cbc
	 */
	@RequestMapping(value="couponSet/set", method=RequestMethod.POST)
	@ResponseBody
	public String setCoupon(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			JSONObject couponSetJson = jsonObj.optJSONObject("couponSet");
			if (null == couponSetJson || couponSetJson.isEmpty()) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			CouponSet couponSet = JsonUtil.toBean(couponSetJson.toString(), CouponSet.class);
			Long userId = getUserId();
			Shop shop = shopBusiness.getShopByUserId(userId);
			couponSet.setSellerId(userId);
			couponSet.setShopNo(shop.getNo());
			couponBussiness.addCouponSet(couponSet);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("添加优惠券失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：商家查询所有优惠券的数量(包括所有状态的优惠券，用来判断是否设置过优惠券)<br>
	 * 
	 * @Title: countAllCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月23日 下午4:02:25  
	 * @author cbc
	 */
	@RequestMapping(value="couponSet/countAll",method=RequestMethod.POST)
	@ResponseBody
	public String countAllCouponSet() {
		try {
			Long userId = getUserId();
			Integer count = couponBussiness.countAllCouponSetByUserId(userId);
			return outSucceed(count);
		} catch (Exception e) {
			_logger.error("查询商家所有优惠券数量失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：商家查询优惠券设置<br>
	 * 
	 * @Title: findCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj timeStatus 1为未开始 2为进行中 3为失效（被商家结束，过了失效时间）
	 * @param pagination
	 * @return
	 * @date 2015年9月23日 下午2:05:58  
	 * @author cbc
	 */
	@RequestMapping(value="couponSet/list", method=RequestMethod.POST)
	@ResponseBody
	public String findCouponSet(@RequestBody JSONObject jsonObj, Pagination<CouponSetDto> pagination) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long userId = getUserId();
			Shop shop = shopBusiness.getShopByUserId(userId);
			if (null == shop) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Object timeStatusObject = jsonObj.get("timeStatus");
			if (null == timeStatusObject) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Integer timeStauts = Integer.valueOf(timeStatusObject.toString());
			pagination = super.initPagination(jsonObj);
			pagination = couponBussiness.findCouponSet(pagination, timeStauts, shop.getNo());
			
			//未开始优惠券数量
			Integer notBegin =  couponBussiness.countCouponSetByTimeStatus(1, shop.getNo());
			//进行中优惠券数量
			Integer onGoing =  couponBussiness.countCouponSetByTimeStatus(2, shop.getNo());
			//已失效优惠券数量
			Integer invalid =  couponBussiness.countCouponSetByTimeStatus(3, shop.getNo());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", pagination.getEnd());
			map.put("curPage", pagination.getCurPage());
			map.put("pageSize", pagination.getPageSize());
			map.put("totalRow", pagination.getTotalRow());
			map.put("datas", pagination.getDatas());
			map.put("notBegin", notBegin);
			map.put("onGoing", onGoing);
			map.put("invalid", invalid);
			
			return outSucceed(map, true, COUPON_SET_LIST_FIELD);
		} catch (Exception e) {
			_logger.error("查询优惠券失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：初始化修改优惠券页面<br>
	 * 3.参数：couponSetId 优惠券ID<br>
	 * @Title: initCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj
	 * @return
	 * @date 2015年9月23日 下午4:22:53  
	 * @author cbc
	 */
	@RequestMapping(value="couponSet/get", method=RequestMethod.POST)
	@ResponseBody
	public String initCouponSet(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object object = jsonObj.get("couponSetId");
			if (null == object) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long couponSetId = Long.valueOf(object.toString());
			CouponSet couponSet = couponBussiness.getCouponSetById(couponSetId);
			return outSucceed(couponSet, true, COUPON_FIELD);
		} catch (Exception e) {
			_logger.error("初始化修改优惠券页面失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：修改优惠券<br>
	 * 3.参数：couponSetId 优惠券ID<br>
	 * 		  circulation 发行量
	 * 		  limitNumber 没人领取数量
	 * 		  currencyType 1非公开 2公开
	 * 
	 * @Title: editCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月23日 下午3:20:43  
	 * @author cbc
	 */
	@RequestMapping(value="couponSet/edit", method=RequestMethod.POST)
	@ResponseBody
	public String editCouponSet(@RequestBody JSONObject jsonObj) {
		try {
			JSONObject couponSetJson = jsonObj.optJSONObject("couponSet");
			if (null == couponSetJson || couponSetJson.isEmpty()) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			CouponSet couponSet = JsonUtil.toBean(couponSetJson.toString(), CouponSet.class);
			Long userId = getUserId();
			Shop shop = shopBusiness.getShopByUserId(userId);
			couponSet.setShopNo(shop.getNo());
			couponBussiness.editCouponSet(couponSet, userId);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("修改优惠券失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：结束优惠券<br>
	 * 3.参数：couponSetId 优惠券ID<br>
	 * 
	 * 
	 * @Title: endCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月23日 下午3:26:14  
	 * @author cbc
	 */
	@RequestMapping(value="couponSet/end", method=RequestMethod.POST)
	@ResponseBody
	public String endCouponSet(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object object = jsonObj.get("couponSetId");
			if (null == object) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long couponSetId = Long.valueOf(object.toString());
			couponBussiness.endCouponSet(couponSetId, getUserId());
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("结束优惠券失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：结束优惠券<br>
	 * 3.参数：couponSetId 优惠券ID<br>
	 * 
	 * @Title: deleteCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月24日 上午11:15:57  
	 * @author cbc
	 */
	@RequestMapping(value="couponSet/delete", method=RequestMethod.POST)
	@ResponseBody
	public String deleteCouponSet(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object object = jsonObj.get("couponSetId");
			if (null == object) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long couponSetId = Long.valueOf(object.toString());
			couponBussiness.deleteCouponSet(couponSetId, getUserId());
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("删除优惠券失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：展示、预览优惠券<br>
	 * 3.参数：couponSetId 优惠券ID<br>
	 * 		  
	 * @Title: showCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月23日 下午3:16:32  
	 * @author cbc
	 */
//	@RequestMapping(value="show", method=RequestMethod.POST)
	@ResponseBody
	public String showCoupon(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object object = jsonObj.get("couponSetId");
			if (null == object) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long couponSetId = Long.valueOf(object.toString());
			CouponSet couponSet = couponBussiness.getCouponSetById(couponSetId);
			if (null == couponSet) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Shop shop = shopBusiness.getShopByNo(couponSet.getShopNo());
			if (null == shop) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			//查询该优惠券对于用户的状态
			Integer conponStatus = getConponStatus(couponSet, getUserId());
			String shopUrl = new StringBuilder("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm").toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("couponSet", couponSet);
			map.put("shop", shop);
			map.put("conponStatus", conponStatus);
			map.put("shopUrl", shopUrl);
			return outSucceed(map, true, SHOW_FIELD);
		} catch (Exception e) {
			_logger.error("展示优惠券失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: getConponStatus 
	 * @Description: 优惠券状态 1可领取 2已领完 3已失效 4已领取 5是自己店铺优惠券不能领取 
	 * @param couponSet
	 * @param userId
	 * @return
	 * @date 2015年9月24日 上午11:38:10  
	 * @author cbc
	 */
	private Integer getConponStatus(CouponSet couponSet, Long userId) {
		Integer receiveNumber = couponBussiness.countHasReveivedCoupon(couponSet.getId());
		if (receiveNumber.longValue() >= couponSet.getCirculation().longValue()) {
			return 2;
		}
		if (couponSet.getTimeStatus().equals(CouponSetDto.INVALID)) {
			return 3;
		}
		if (userId != null) {
			boolean boo = couponBussiness.hasCoupon(couponSet.getId(), userId);
			if (boo) {
				return 4;
			}
			if (couponSet.getSellerId().equals(userId)) {
				return 5;
			}
		}
		return 1;
	}

	/**
	 * 1.角色：买家<br>
	 * 2.功能：领取优惠券<br>
	 * 3.参数 ：couponSetId 优惠券ID<br>
	 * 		   DrawRemoteType ：领取处类型 1总店 2分店<br>
	 *         DrawRemoteId: 总店为shopNo，分店为subbranchId<br>
	 * @Title: reveiveCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj
	 * @return
	 * @date 2015年9月23日 下午3:10:06  
	 * @author cbc
	 */
	@RequestMapping(value="receive", method=RequestMethod.POST)
	@ResponseBody
	public String receiveCoupon(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object object = jsonObj.get("couponSetId");
			if (null == object) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long couponSetId = Long.valueOf(object.toString());
			Coupon coupon = new Coupon();
			coupon.setCouponSetId(couponSetId);
			coupon.setDrawRemoteType(CouponDto.DRAW_TYPE_SHOP);
			coupon.setUserId(getUserId());
			
			couponBussiness.reciveCoupon(coupon);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("领取优惠券失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	
	
}
