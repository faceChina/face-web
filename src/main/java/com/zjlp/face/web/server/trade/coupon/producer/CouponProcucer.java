package com.zjlp.face.web.server.trade.coupon.producer;

import java.util.List;

import com.zjlp.face.web.exception.ext.CouponException;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;

public interface CouponProcucer {

	/**
	 * 1:角色 买家<br>
	 * 2.功能 :订单结算页根据订单金额匹配最佳优惠券<br>
	 * 
	 * 
	 * @Title: matchBestCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param shopNo
	 * @param orderPrice
	 * @return
	 * @throws CouponException
	 * @date 2015年9月25日 上午9:14:09  
	 * @author cbc
	 */
	public Coupon matchBestCoupon(Long userId, String shopNo, Long orderPrice) throws CouponException;
	
	/**
	 * 1:角色 买家<br>
	 * 2.功能 :订单所有匹配的优惠券<br>
	 * @Title: matchCoupons 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param shopNo
	 * @param orderPrice
	 * @return
	 * @throws CouponException
	 * @date 2015年10月4日 下午4:28:10  
	 * @author cbc
	 */
	public List<Coupon> findValidCouponsInThisShop(Long userId, String shopNo) throws CouponException;
}
