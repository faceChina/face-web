package com.zjlp.face.web.server.trade.coupon.bussiness;



import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.CouponException;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;

public interface CouponBussiness {

	/**
	 * 1.角色：商家<br>
	 * 2.功能：商家增加优惠券设置<br>
	 * 
	 * @Title: addCouponSet 
	 * @Description: 
	 * @param couponSet
	 * @return
	 * @date 2015年9月22日 下午2:52:59  
	 * @author cbc
	 */
	public Long addCouponSet(CouponSet couponSet) throws CouponException;
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：通过主键查询优惠券规则明细<br>
	 * 
	 * @Title: getCouponSetById 
	 * @Description: 通过主键查询优惠券规则明细
	 * @param id
	 * @return
	 * @throws CouponException
	 * @date 2015年9月22日 下午5:59:35  
	 * @author cbc
	 */
	public CouponSet getCouponSetById(Long id) throws CouponException;
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：商家通过状态查询优惠券<br>
	 * 
	 * @Title: findCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param timeStauts 1为未开始 2为进行中 3为已失效
	 * @return
	 * @throws CouponException
	 * @date 2015年9月23日 上午10:13:56  
	 * @author cbc
	 */
	public Pagination<CouponSetDto> findCouponSet(Pagination<CouponSetDto> pagination, Integer timeStauts, String shopNo) throws CouponException;
	

	/**
	 * 1.角色：商家<br>
	 * 2.功能：商家通过状态查询优惠券数量<br>
	 * 
	 * @Title: countCouponSetByTimeStatus 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @param timeStatus 1为未开始 2为进行中 3为已失效
	 * @return
	 * @throws CouponException
	 * @date 2015年9月23日 下午2:10:27  
	 * @author cbc
	 */
	public Integer countCouponSetByTimeStatus(Integer timeStatus, String shopNo) throws CouponException;
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：商家查询所有优惠券的数量(包括所有状态的优惠券，用来判断是否设置过优惠券)<br>
	 * @Title: countAllCouponSetByUserId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @return
	 * @date 2015年9月23日 下午4:06:29  
	 * @author cbc
	 */
	public Integer countAllCouponSetByUserId(Long userId) throws CouponException;
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：查询该优惠券有多少人领取<br>
	 * @Title: countHasReveivedCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param couponSetId
	 * @return
	 * @throws CouponException
	 * @date 2015年9月23日 下午5:24:01  
	 * @author cbc
	 */
	public Integer countHasReveivedCoupon(Long couponSetId) throws CouponException;
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：修改优惠券<br>
	 * 3.限制：优惠券修改只能修改发行量，每人限量数量，公开性<br>
	 * @Title: editCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param couponSet
	 * @param userId
	 * @date 2015年9月23日 下午4:51:54  
	 * @author cbc
	 */
	public void editCouponSet(CouponSet couponSet, Long userId);
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：结束优惠券<br>
	 * @Title: endCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param couponSetId
	 * @param userId
	 * @date 2015年9月24日 上午9:16:27  
	 * @author cbc
	 */
	public void endCouponSet(Long couponSetId, Long userId);
	
	/**
	 * 1.角色：商家<br>
	 * 2.功能：删除优惠券<br>
	 * @Title: deleteCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param couponSetId
	 * @param userId
	 * @date 2015年9月24日 上午11:16:42  
	 * @author cbc
	 */
	public void deleteCouponSet(Long couponSetId, Long userId);
	
	/**
	 * 1:角色 买家<br>
	 * 2.功能 :买家领取优惠券<br>
	 * 
	 * @Title: reciveCoupon 
	 * @Description: 
	 * @param coupon
	 * @return
	 * @throws CouponException
	 * @date 2015年9月22日 下午5:01:54  
	 * @author cbc
	 */
	public Long reciveCoupon(Coupon coupon) throws CouponException;
	
	/**
	 * 1:角色 买家<br>
	 * 2.功能 :买家使用优惠券<br>
	 * 
	 * @Title: userCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id coupon的IDs
	 * @throws CouponException
	 * @date 2015年9月23日 下午3:30:42  
	 * @author cbc
	 */
	public void useCoupon(Long id, Long userId, String shopNo) throws CouponException;
	
	/**
	 * 1:角色 买家<br>
	 * 2.功能 :根据ID查询优惠券<br>
	 * 
	 * @Title: getCouponById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @throws CouponException
	 * @date 2015年9月23日 下午3:33:47  
	 * @author cbc
	 */
	public Coupon getCouponById(Long id) throws CouponException;

	/**
	 * 1:角色 买家<br>
	 * 2.功能 :是否有相同的未使用的优惠券<br>
	 * 3. true 领取过 false 未领取
	 * @Title: getCouponById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @throws CouponException
	 * @date 2015年9月23日 下午3:33:47  
	 * @author cbc
	 */
	public boolean hasCoupon(Long couponSetId, Long userId);

	/**
	 * 1:角色 买家<br>
	 * 2.功能 :分页查询优惠券<br>
	 * 
	 * @Title: findCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param couponDto
	 * @return
	 * @throws CouponException
	 * @date 2015年9月24日 下午3:04:04  
	 * @author cbc
	 */
	public Pagination<CouponDto> findCouponPage(Pagination<CouponDto> pagination, CouponDto couponDto) throws CouponException;
	
	/**
	 * 1:角色 买家<br>
	 * 2.功能 :订单结算页根据订单金额匹配最佳优惠券<br>
	 * 
	 * 
	 * @Title: matchBestCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param shopNo
	 * @param orderPrice (不包含邮费，会员折扣价之后的订单总额)
	 * @return
	 * @throws CouponException
	 * @date 2015年9月25日 上午9:14:09  
	 * @author cbc
	 */
	public Coupon matchBestCoupon(Long userId, String shopNo, Long orderPrice) throws CouponException;
	
//	/**
//	 * 1:角色 买家<br>
//	 * 2.功能 :订单所有匹配的优惠券<br>
//	 * @Title: matchCoupons 
//	 * @Description: (这里用一句话描述这个方法的作用) 
//	 * @param userId
//	 * @param shopNo
//	 * @param orderPrice
//	 * @return
//	 * @throws CouponException
//	 * @date 2015年10月4日 下午4:28:10  
//	 * @author cbc
//	 */
//	public List<Coupon> matchCoupons(Long userId, String shopNo, Long orderPrice) throws CouponException;
	
	/**
	 * 1.角色：买家
	 * 2.功能：查看店铺所有优惠券（未开始，进行中）
	 * @Title: findAllCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @param buyerUserId 可以传	NULL
	 * @return
	 * @throws CouponException
	 * @date 2015年9月25日 上午11:31:54  
	 * @author cbc
	 */
	public Pagination<CouponSetDto> findCouponSetForBuyer(Pagination<CouponSetDto> pagination, String shopNo, Long buyerUserId) throws CouponException;
	
	/**
	 * 1.角色：买家
	 * 2.功能：删除优惠券
	 * @Title: deleteCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param userId
	 * @throws CouponException
	 * @date 2015年10月4日 上午11:42:27  
	 * @author cbc
	 */
	public void deleteCoupon(Long id, Long userId) throws CouponException;

	/**
	 * 1.角色：买家
	 * 2.功能：查询店铺所有有效的优惠券
	 * @Title: findShopAllValidCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年10月6日 上午9:41:38  
	 * @author cbc
	 */
	public List<CouponSet> findShopAllValidCoupon(String shopNo);

	/**
	 * 1.角色：买家
	 * 2.功能：查询所有未使用的优惠券张数
	 * @Title: countUserAllCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param buyerUserId
	 * @date 2015年10月6日 上午10:20:22  
	 * @author cbc
	 * @return 
	 */
	public Integer countUserAllCoupon(Long buyerUserId);

	/**
	 * 买家批量删除优惠券
	 * @Title: deleteCouponBatch 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param ids
	 * @param userId
	 * @date 2015年10月6日 上午11:35:01  
	 * @author cbc
	 */
	public void deleteCouponBatch(String[] ids, Long userId);

	/**
	 * 查询卖家有效的优惠券设置
	 * @Title: countShopNotDeleteCouponSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年10月6日 下午7:39:06  
	 * @author cbc
	 */
	public Integer countShopValidCouponSet(String shopNo);

	/**
	 * 1.角色：买家
	 * 2.功能：查询店铺所有的优惠券，并查询是否使用过该优惠券，优惠券是否已全部领取
	 * @Title: findCouponSetForBuyer 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @param userId
	 * @return
	 * @date 2015年10月7日 上午11:25:07  
	 * @author cbc
	 */
	public List<CouponSetDto> findAllCouponSetForBuyer(String shopNo, Long userId);

	/**
	 * 查询买家领取该优惠券的次数
	 * @Title: countUserReceiveNumber 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param id
	 * @return
	 * @date 2015年10月7日 下午5:48:29  
	 * @author cbc
	 */
	public Integer countUserReceiveNumber(Long userId, Long couponSetId) throws CouponException;

	/**
	 * 
	 * @Title: deleteAllCoupon 
	 * @Description: 全部删除优惠券
	 * @param userId
	 * @date 2015年10月9日 下午8:57:29  
	 * @author cbc
	 */
	public void deleteAllCoupon(Long userId);

}
