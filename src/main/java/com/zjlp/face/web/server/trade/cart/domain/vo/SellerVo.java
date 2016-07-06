package com.zjlp.face.web.server.trade.cart.domain.vo;
import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;


public class SellerVo {
	
	/** 卖家ID */
	private Long sellerId;
	/** 用户在当前卖家的总积分 */
	private Long userIntegral = 0L;
	/**消费送积分条件 (单位：分)*/
	private Long giftCondition= 0L;
	/**消费送积分结果 (单位：个)*/
	private Long giftResult= 0L;
	/**积分抵价条件 (单位：个)*/
	private Long deductionCondition= 0L;
	/**积分抵价结果(单位：分)*/
	private Long deductionResult= 0L;
	/**允许抵价百分比(单位：百分数)*/
	private Long deductionMaxrete= 0L;
	
	/** 店铺及商品列表 */
	private Map<ShopVo,List<CartDto>> shopMap;
	
	/**该用户拥有的当前店铺所有可用优惠券*/
	private List<Coupon> matchCoupons;
	
	
	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Map<ShopVo, List<CartDto>> getShopMap() {
		return shopMap;
	}

	public void setShopMap(Map<ShopVo, List<CartDto>> shopMap) {
		this.shopMap = shopMap;
	}

	public Long getUserIntegral() {
		return userIntegral;
	}

	public void setUserIntegral(Long userIntegral) {
		this.userIntegral = userIntegral;
	}

	public Long getGiftCondition() {
		return giftCondition;
	}

	public void setGiftCondition(Long giftCondition) {
		this.giftCondition = giftCondition;
	}

	public Long getGiftResult() {
		return giftResult;
	}

	public void setGiftResult(Long giftResult) {
		this.giftResult = giftResult;
	}

	public Long getDeductionCondition() {
		return deductionCondition;
	}

	public void setDeductionCondition(Long deductionCondition) {
		this.deductionCondition = deductionCondition;
	}

	public Long getDeductionResult() {
		return deductionResult;
	}

	public void setDeductionResult(Long deductionResult) {
		this.deductionResult = deductionResult;
	}

	public Long getDeductionMaxrete() {
		return deductionMaxrete;
	}

	public void setDeductionMaxrete(Long deductionMaxrete) {
		this.deductionMaxrete = deductionMaxrete;
	}


	public List<Coupon> getMatchCoupons() {
		return matchCoupons;
	}

	public void setMatchCoupons(List<Coupon> matchCoupons) {
		this.matchCoupons = matchCoupons;
	}
	
}
