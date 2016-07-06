package com.jzwgj.trade.coupon;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.coupon.bussiness.CouponBussiness;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
public class CouponBussinessTest {
	
	@Autowired
	private CouponBussiness couponBussiness;
	
	/**
	 * 
	 * @Title: testAddSet 
	 * @Description: 卖家生成优惠券
	 * @date 2015年10月5日 下午3:00:48  
	 * @author cbc
	 */
	@Test
	public void testAddSet() {
		
		CouponSet coupon = new CouponSet();
		coupon.setSellerId(274L); //卖家USERid
		coupon.setShopNo("HZJZ1506291407PMm511"); 
		coupon.setFaceValue(20000L);//面额
		coupon.setCirculation(1000L);//发行量
		coupon.setOrderPrice(20000L);//满减金额
		coupon.setLimitNumber(5);//每人限领张数
		coupon.setCurrencyType(CouponSetDto.CURRENCY_TYPE_ANY);//公开性
		Date date = new Date();
		coupon.setEffectiveTime(date);
		coupon.setEndTime(DateUtil.addDay(date, 10));
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		couponBussiness.addCouponSet(coupon);
		coupon.setId(null);
		
	}
	
	//买家领取优惠券
	@Test
	public void testReceive() {
		Coupon coupon = new Coupon();
		coupon.setUserId(274L);//领取人userId
		coupon.setCouponSetId(52L);//优惠券ID
		coupon.setDrawRemoteType(1);//领取处的类型 
		coupon.setDrawRemoteId("1313");
		couponBussiness.reciveCoupon(coupon);
	}
	
	@Test
	public void testDate() {
		Date date = new Date();
		long time = date.getTime();
		System.out.println(time);
		
		Date addDay = DateUtil.addDay(date, 30);
		long time2 = addDay.getTime();
		System.out.println(time2);
	}
	
	@Test
	public void toJ() {
		CouponSet coupon = new CouponSet();
		coupon.setSellerId(274L);
		coupon.setShopNo("HZJZ1506291407PMm511");
		coupon.setFaceValue(10000L);
		coupon.setCirculation(1000L);
		coupon.setOrderPrice(100L);
		coupon.setLimitNumber(5);
		coupon.setCurrencyType(CouponSetDto.CURRENCY_TYPE_ANY);
		Date date = new Date();
		coupon.setEffectiveTime(date);
		coupon.setEndTime(DateUtil.addDay(date, 10));
		JSONObject fromObject = JSONObject.fromObject(coupon);
		CouponSet bean = this.toBean(fromObject.toString(), CouponSet.class);
		System.out.println(bean);
	}
	
	public <T> T toBean(String jsonString, Class<T> clazz)
			throws RuntimeException {
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonString);
			if (null == jsonObject) {
				throw new RuntimeException("Json参数转换失败!转换后的对象为空");
			}
			return (T) jsonObject.toBean(jsonObject, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Json参数转换时发生异常!", e);
		}
	}
	
	@Test
	public void testFindCouponSet() {
		Pagination<CouponSetDto> pagination = new Pagination<CouponSetDto>();
		pagination = couponBussiness.findCouponSet(pagination, 3, "HZJZ1506251520VQChW2");
		System.out.println(pagination);
	}
	
	@Test
	public void userCoupon() {
		couponBussiness.useCoupon(1L, 273L, "HZJZ1506291407PMm511");
	}
	
	@Test
	public void testUpdate() {
		CouponSet coupon = new CouponSet();
		coupon.setId(2L);
		coupon.setShopNo("HZJZ1506291407PMm511");
		coupon.setFaceValue(10000L);
		coupon.setCirculation(1000L);
		coupon.setOrderPrice(100L);
		coupon.setLimitNumber(5);
		coupon.setCurrencyType(CouponSetDto.CURRENCY_TYPE_POINT);
		couponBussiness.editCouponSet(coupon, 274L);
	}
	
	@Test
	public void endCouponSet() {
		couponBussiness.endCouponSet(9L, 274L);
	}
	
	@Test
	public void testFindCoupon() {
		Pagination<CouponDto> pagination = new Pagination<CouponDto>();
		CouponDto couponDto = new CouponDto();
		couponDto.setUserId(273L);
		pagination = couponBussiness.findCouponPage(pagination, couponDto);
		System.out.println(pagination);
	}
	
	@Test
	public void testMatch() {
		Coupon coupon = couponBussiness.matchBestCoupon(273L, "HZJZ1506291407PMm511", 1000000L);
		System.out.println(coupon);
	}
	
	@Test
	public void findUserCouponSet() {
		Pagination<CouponSetDto> pagination = new Pagination<CouponSetDto>();
		pagination = couponBussiness.findCouponSetForBuyer(pagination, "HZJZ1506291407PMm511", null);
		System.out.println(pagination);
	}

	@Test
	public void testFindAllCouponSet() {
		List<CouponSet> list = couponBussiness.findShopAllValidCoupon("HZJZ1506291407PMm511");
		System.out.println(list);
	}
	
	@Test
	public void testDeleteCouponBatch() {
		String[] ids = {"1", "2", "3"};
		couponBussiness.deleteCouponBatch(ids, 274L);
	}
	
	@Test
	public void testDeleteAll() {
		couponBussiness.deleteAllCoupon(274L);
	}
	
}
