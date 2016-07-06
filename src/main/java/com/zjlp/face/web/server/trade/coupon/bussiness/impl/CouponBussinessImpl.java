	package com.zjlp.face.web.server.trade.coupon.bussiness.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.GenerateCode;
import com.zjlp.face.web.exception.ext.CouponException;
import com.zjlp.face.web.server.trade.coupon.bussiness.CouponBussiness;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;
import com.zjlp.face.web.server.trade.coupon.service.CouponService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;

@Service
public class CouponBussinessImpl implements CouponBussiness {
	

	
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private CouponService couponService;

	@Override
	public Long addCouponSet(CouponSet couponSet) throws CouponException {
		try {
			AssertUtil.notNull(couponSet, "参数couponSet不能为空");
			AssertUtil.notNull(couponSet.getFaceValue(), "couponSet.faceValue不能为空");
			AssertUtil.notNull(couponSet.getCirculation(), "couponSet.circulation不能为空");
			AssertUtil.notNull(couponSet.getEffectiveTime(), "couponSet.effectiveTime不能为空");
			AssertUtil.notNull(couponSet.getEndTime(), "couponSet.endTime不能为空");
			AssertUtil.notNull(couponSet.getOrderPrice(), "couponSet.orderPrice不能为空");
			AssertUtil.notNull(couponSet.getLimitNumber(), "couponSet.limitNumber不能为空");
			AssertUtil.notNull(couponSet.getCurrencyType(), "couponSet.currencyType不能为空");
			AssertUtil.notNull(couponSet.getSellerId(), "couponSet.sellerId不能为空");
			AssertUtil.notNull(couponSet.getShopNo(), "couponSet.shopNo不能为空");
			this.checkCouponSet(couponSet);
			return couponService.addCouponSet(couponSet);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	/**
	 * 
	 * @Title: checkCouponSet 
	 * @Description: 验证优惠券参数合法性 
	 * @param couponSet
	 * @date 2015年9月22日 下午3:22:22  
	 * @author cbc
	 */
	private void checkCouponSet(CouponSet couponSet) {
		
		//发行量数量校验
		AssertUtil.isTrue(couponSet.checkCirculation(), 
					"发行量数量必须要大于等于每人限领数量。发行数量最多不超过10000张");
		//满减金额校验
		AssertUtil.isTrue(couponSet.checkOrderPrice(), "满减金额一定要大于等于优惠券的面值");
		//优惠券面额校验
		AssertUtil.isTrue(couponSet.checkFaceValue(), "优惠券面额必须为1-1000");
		//每人限领校验
		AssertUtil.isTrue(couponSet.checkLimitNumber(), "每人限领1-10张");
		//shopNo sellerId校验
		Shop shop = shopProducer.getShopByNo(couponSet.getShopNo());
		AssertUtil.notNull(shop, "查无此shop");
		AssertUtil.isTrue(shop.getUserId().equals(couponSet.getSellerId()), "该用户无为此店铺设置优惠券的权限");
	}
	
	
	@Override
	public Long reciveCoupon(Coupon coupon) throws CouponException {
		try {
			AssertUtil.notNull(coupon, "coupon不能为空");
			AssertUtil.notNull(coupon.getCouponSetId(), "coupon.couponSetId不能为空");
			AssertUtil.notNull(coupon.getDrawRemoteType(), "coupon.drawRemoteType不能为空");
			if (coupon.getDrawRemoteType().equals(CouponDto.DRAW_TYPE_SUBBRANCH)) {
				AssertUtil.notNull(coupon.getDrawRemoteId(), "如果是分店领取,coupon.drawRemoteId不能为空");
			}
			AssertUtil.notNull(coupon.getUserId(), "coupon.userId不能为空");
			
			CouponSet couponSet = this.getCouponSetById(coupon.getCouponSetId());
			AssertUtil.notNull(couponSet, "无该优惠券");
			
			coupon = coupon.withSet(couponSet);
			AssertUtil.notTrue((coupon.getUserId().equals(coupon.getSellerId())), "不能领取自己店铺的优惠券");
			AssertUtil.notTrue(this.hasCoupon(coupon.getCouponSetId(), coupon.getUserId()), "还有未使用的优惠券，请使用后再领取");
			AssertUtil.notTrue(this.hasReceiveLimitCoupon(coupon.getCouponSetId(), coupon.getUserId(), couponSet), "已到每人限领总数，不能再领取");
			AssertUtil.notTrue(this.hasReachCirculation(couponSet), "优惠券已经被领完，不能再领取");
			
			coupon.setCode(GenerateCode.createCouponCode(coupon.getShopNo()));
			return couponService.reciveCoupon(coupon);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	/**
	 * 
	 * @Title: hasReachCirculation 
	 * @Description: 优惠券是否已经被领完
	 * @param couponSet
	 * @return
	 * @date 2015年9月24日 下午4:27:07  
	 * @author cbc
	 */
	private boolean hasReachCirculation(CouponSet couponSet) {
		Integer count = this.countHasReveivedCoupon(couponSet.getId());
		return count.longValue() >= couponSet.getCirculation().longValue();
	}

	/**
	 * 
	 * @Title: hasReceiveLimitCoupon 
	 * @Description: 是否已经达到每人领取限额
	 * @param couponSetId
	 * @param userId
	 * @return
	 * @date 2015年9月24日 下午4:23:40  
	 * @author cbc
	 * @param couponSet2 
	 */
	private boolean hasReceiveLimitCoupon(Long couponSetId, Long userId, CouponSet couponSet) {
		Integer count = couponService.countCouponByUserIdAndCouponSetId(couponSetId, userId, null);
		return count.compareTo(couponSet.getLimitNumber()) >= 0;
	}

	@Override
	public CouponSet getCouponSetById(Long id) throws CouponException {
		try {
			AssertUtil.notNull(id, "id不能为空");
			return couponService.getCouponSetById(id);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	@Override
	public Pagination<CouponSetDto> findCouponSet(
			Pagination<CouponSetDto> pagination, Integer timeStauts, String shopNo)
			throws CouponException {
		try {
			AssertUtil.notNull(pagination, "pagination不能为空");
			AssertUtil.hasLength(shopNo, "shopNo不能为空");
			AssertUtil.isTrue(timeStauts != null && (CouponSetDto.INVALID.equals(timeStauts) || CouponSetDto.ON_GOING.equals(timeStauts) || CouponSetDto.NOT_BEGIN.equals(timeStauts)), 
					"timeStatus校验未通过");
			return couponService.findCouponSet(pagination, timeStauts, shopNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponException(e);
		}
	}

	@Override
	public Integer countCouponSetByTimeStatus(Integer timeStatus, String shopNo)
			throws CouponException {
		try {
			AssertUtil.hasLength(shopNo, "shopNo不能为空");
			AssertUtil.isTrue(timeStatus != null && (CouponSetDto.INVALID.equals(timeStatus) || CouponSetDto.ON_GOING.equals(timeStatus) || CouponSetDto.NOT_BEGIN.equals(timeStatus)), 
					"timeStatus校验未通过");
			return couponService.countCouponSetByTimeStatus(timeStatus, shopNo);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	@Override
	public void useCoupon(Long id, Long userId, String shopNo) throws CouponException {
		try {
			AssertUtil.notNull(id, "id不能为空");
			AssertUtil.notNull(userId, "userId不能为空");
			AssertUtil.notNull(shopNo, "shopNo不能为空");
			
			Coupon coupon = this.getCouponById(id);
			AssertUtil.notNull(coupon, "查询该优惠券为空");
			//校验优惠券状态
			AssertUtil.notTrue(CouponDto.STATUS_USERD.equals(coupon.getStatus()), "该优惠券已使用不能重复使用");
			AssertUtil.notTrue(CouponDto.STATUS_DELETE.equals(coupon.getStatus()), "该优惠券已删除不能使用");
			//校验优惠券是否是使用者本人使用
			AssertUtil.isTrue(coupon.getUserId().equals(userId), "不是本人使用该优惠券，无法使用");
			//校验是否在优惠券指定店铺使用
			AssertUtil.isTrue(coupon.getShopNo().equals(shopNo), "当前店铺非优惠券的指定店铺，无法使用");
			//校验优惠券是否在规定时间内使用
			AssertUtil.notTrue(coupon.getValid() == 2, "优惠券还未开始");
			AssertUtil.notTrue(coupon.getValid() == 3, "优惠券已结束");
			
			Coupon newCoupon = new Coupon();
			newCoupon.setId(coupon.getId());
			newCoupon.setStatus(CouponDto.STATUS_USERD);
			couponService.updateCoupon(newCoupon);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	@Override
	public Coupon getCouponById(Long id) throws CouponException {
		AssertUtil.notNull(id, "id不能为空");
		return couponService.getCouponById(id);
	}

	@Override
	public Integer countAllCouponSetByUserId(Long userId) {
		AssertUtil.notNull(userId, "userId不能为空");
		return couponService.countAllCouponSetByUserId(userId);
	}

	@Override
	public void editCouponSet(CouponSet couponSet, Long userId) throws CouponException {
		try {
			AssertUtil.notNull(couponSet, "couponSet不能为空");
			AssertUtil.notNull(userId, "userId不能为空");
			
			//校验(发行量可修改的数量要大于已经领取的优惠券数量, 修改权限, )
			this.checkUpdate(couponSet, userId);
			
			CouponSet newCouponSet = new CouponSet();
			newCouponSet.setId(couponSet.getId());
			newCouponSet.setCirculation(couponSet.getCirculation());
			newCouponSet.setLimitNumber(couponSet.getLimitNumber());
			newCouponSet.setCurrencyType(couponSet.getCurrencyType());
			
			couponService.editCouponSet(couponSet);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	/**
	 * 
	 * @Title: checkUpdate 
	 * @Description: 校验修改
	 * @param couponSet
	 * @param userId
	 * @date 2015年9月23日 下午5:43:19  
	 * @author cbc
	 */
	private void checkUpdate(CouponSet couponSet, Long userId) {
		//发行量数量校验
		AssertUtil.isTrue(couponSet.checkCirculation(), 
				"发行量数量必须要大于等于每人限领数量。发行数量最多不超过10000张");
		//每人限领校验
		AssertUtil.isTrue(couponSet.checkLimitNumber(), "每人限领1-10张");
		
		CouponSet oldCouponSet = this.getCouponSetById(couponSet.getId());
		AssertUtil.notNull(oldCouponSet, "无此优惠券");
		//修改权限
		AssertUtil.isTrue(oldCouponSet.getShopNo().equals(couponSet.getShopNo()), "没有修改该店铺优惠券权限");
		AssertUtil.isTrue(oldCouponSet.getSellerId().equals(userId), "该用户没有修改该优惠券的权限");
		//发行量可修改的数量要大于已经领取的优惠券数量
		AssertUtil.isTrue(couponSet.getCirculation().intValue() >= this.countHasReveivedCoupon(couponSet.getId()).intValue(), "发行量可修改的数量要大于已经领取的优惠券数量");
	}

	@Override
	public Integer countHasReveivedCoupon(Long couponSetId)
			throws CouponException {
		AssertUtil.notNull(couponSetId, "couponSetId不能为空");
		return couponService.countHasReveivedCoupon(couponSetId);
	}

	@Override
	public void endCouponSet(Long couponSetId, Long userId) throws CouponException {
		try {
			AssertUtil.notNull(couponSetId, "couponSetId不能为空");
			AssertUtil.notNull(userId, "userId不能为空");
			
			CouponSet oldCouponSet = this.getCouponSetById(couponSetId);
			AssertUtil.notNull("oldCouponSet", "查询结果为空");
			//校验用户修改权限
			AssertUtil.isTrue(oldCouponSet.getSellerId().equals(userId), "该用户无权限结束该优惠券");
			//校验优惠券状态
			AssertUtil.isTrue(CouponSetDto.NOT_BEGIN.equals(oldCouponSet.getTimeStatus()) || CouponSetDto.ON_GOING.equals(oldCouponSet.getTimeStatus()), "状态校验未通过");
			
			CouponSet newCouponSet = new CouponSet();
			newCouponSet.setId(couponSetId);
			newCouponSet.setStatus(CouponSetDto.STATUS_END);
			couponService.endCouponSet(newCouponSet);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	@Override
	public void deleteCouponSet(Long couponSetId, Long userId) {
		try {
			AssertUtil.notNull(couponSetId, "couponSetId不能为空");
			AssertUtil.notNull(userId, "userId不能为空");
			
			CouponSet oldCouponSet = this.getCouponSetById(couponSetId);
			AssertUtil.notNull("oldCouponSet", "查询结果为空");
			//校验用户修改权限
			AssertUtil.isTrue(oldCouponSet.getSellerId().equals(userId), "该用户无权限删除该优惠券");
			//校验优惠券状态
			AssertUtil.isTrue(CouponSetDto.INVALID.equals(oldCouponSet.getTimeStatus()), "状态校验未通过");
			AssertUtil.notTrue(CouponSetDto.STATUS_DELETE.equals(oldCouponSet.getStatus()), "优惠券已删除，不能重复删除");
			CouponSet newCouponSet = new CouponSet();
			newCouponSet.setId(couponSetId);
			newCouponSet.setStatus(CouponSetDto.STATUS_DELETE);
			couponService.deleteConponSet(newCouponSet);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	@Override
	public boolean hasCoupon(Long couponSetId, Long userId) {
		AssertUtil.notNull(couponSetId, "couponSetId不能为空");
		AssertUtil.notNull(userId, "userId不能为空");
		Integer count = couponService.countCouponByUserIdAndCouponSetId(couponSetId, userId, CouponDto.STATUS_UNUSED);
		return count.intValue() >= 1;
	}

	@Override
	public Pagination<CouponDto> findCouponPage(Pagination<CouponDto> pagination,
			CouponDto couponDto) throws CouponException {
		try {
			AssertUtil.notNull(pagination, "pagination不能为空");
			AssertUtil.notNull(couponDto, "couponDto不能为空");
			AssertUtil.notNull(couponDto.getUserId(), "couponDto.userId不能为空");
			return couponService.findCouponPage(pagination, couponDto);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	@Override
	public Coupon matchBestCoupon(Long userId, String shopNo, Long orderPrice)
			throws CouponException {
		List<Coupon> list = couponService.findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(userId, shopNo, orderPrice);
		return (list != null && !list.isEmpty()) ? list.get(0) : null;
	}
	

//	@Override
//	public List<Coupon> matchCoupons(Long userId, String shopNo, Long orderPrice)
//			throws CouponException {
//		AssertUtil.notNull(userId, "userId不能为空");
//		AssertUtil.hasLength(shopNo, "shopNo不能为空");
//		AssertUtil.notNull(orderPrice, "orderPrice不能为空");
//		return couponService.findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(userId, shopNo, orderPrice);
//	}

	@Override
	public Pagination<CouponSetDto> findCouponSetForBuyer(
			Pagination<CouponSetDto> pagination, String shopNo, Long buyerUserId)
			throws CouponException {
		AssertUtil.notNull(pagination, "pagination不能为空");
		AssertUtil.hasLength(shopNo, "shopNo不能为空");
		return couponService.findCouponSetForBuyer(pagination, shopNo, buyerUserId);
	}

	@Override
	public void deleteCoupon(Long id, Long userId) throws CouponException {
		try {
			AssertUtil.notNull(id, "id不能为空");
			AssertUtil.notNull(userId, "userId不能为空");
			Coupon coupon = couponService.getCouponById(id);
			AssertUtil.notNull(coupon, "所要删除的优惠券为空");
			Coupon newCoupon = new Coupon();
			newCoupon.setId(coupon.getId());
			newCoupon.setStatus(CouponDto.STATUS_DELETE);
			couponService.deleteCoupon(newCoupon);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	@Override
	public List<CouponSet> findShopAllValidCoupon(String shopNo) {
		AssertUtil.hasLength(shopNo, "shopNo不能为空");
		return couponService.findShopAllValidCoupon(shopNo);
	}

	@Override
	public Integer countUserAllCoupon(Long userId) {
		return couponService.countUserAllCoupon(userId);
	}

	@Override
	public void deleteCouponBatch(String[] ids, Long userId) {
		couponService.deleteCouponBatch(ids, userId);
	}

	@Override
	public Integer countShopValidCouponSet(String shopNo) {
		return couponService.countShopValidCouponSet(shopNo);
	}

	@Override
	public List<CouponSetDto> findAllCouponSetForBuyer(String shopNo,
			Long userId) {
		List<CouponSetDto> list = couponService.findAllCouponSetForBuyer(shopNo, userId);
		if (list != null && !list.isEmpty()) {
			Iterator<CouponSetDto> iterator = list.iterator();
			while (iterator.hasNext()) {
				CouponSetDto couponSetDto = (CouponSetDto) iterator.next();
				if (couponSetDto.getIsAllReceive()) {
					iterator.remove();
					continue;
				}
				if (couponSetDto.getIsReachLimitNumber() && !couponSetDto.getHasReceive()) {
					iterator.remove();
					continue;
				}
			}
		}
		return list;
	}

	@Override
	public Integer countUserReceiveNumber(Long userId, Long couponSetId) throws CouponException {
		try {
			AssertUtil.notNull(userId, "userId不能为空");
			AssertUtil.notNull(couponSetId, "couponSetId不能为空");
			return couponService.countUserReceiveNumber(couponSetId, userId);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}

	@Override
	public void deleteAllCoupon(Long userId) {
		try {
			AssertUtil.notNull(userId, "userId不能为空");
			couponService.deleteAllCoupon(userId);
		} catch (Exception e) {
			throw new CouponException(e);
		}
	}
	
	

}
