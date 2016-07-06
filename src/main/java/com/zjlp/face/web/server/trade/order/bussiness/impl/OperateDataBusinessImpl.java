package com.zjlp.face.web.server.trade.order.bussiness.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.date.DateStyle;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.exception.ext.OperateDataException;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;
import com.zjlp.face.web.server.trade.order.service.PurchaseOrderService;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;

@Service("operateDataBusiness")
public class OperateDataBusinessImpl implements OperateDataBusiness {
	
	private Logger _log = Logger.getLogger(getClass());
	
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private SubbranchProducer subbranchProducer;
	
	private static final Integer WEEK_DAYS = 7;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Override
	public Long supplierRecivePrice(Long userId) throws OperateDataException {
		try {
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "userId");
			Shop shop = shopProducer.getShopByUserId(userId);
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setShopNo(shop.getNo());
			salesOrderVo.setPaymentTime(DateUtil.getZeroPoint(new Date()));
			Long price = salesOrderService.supplierRecivePrice(salesOrderVo);
			return null == price ? OperateData.L_ZERO : price;
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Long countTodayCommission(Long subbranchId) throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "参数subbranchId不能为空");
			return salesOrderService.countTodayCommission(subbranchId);
		} catch (Exception e) {
			_log.error("查询今日佣金总收入失败");
			throw new OperateDataException(e);
		}
	}

	@Override
	public Long countTodayShopCommission(Long subbranchId, String userCell)
			throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "参数subbranchId不能为空");
			AssertUtil.hasLength(userCell, "参数userCell不能为空");
			return salesOrderService.countTodayShopCommission(subbranchId, userCell);
		} catch (Exception e) {
			_log.error("查询店铺今日佣金收入失败");
			throw new OperateDataException(e);
		}
	}


	@Override
	public Long countTodaySubbranchCommission(Long subbranchId, String userCell)
			throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "参数subbranchId不能为空");
			AssertUtil.hasLength(userCell, "参数userId不能为空");
			return salesOrderService.countTodaySubbranchCommission(subbranchId, userCell);
		} catch (Exception e) {
			_log.error("查询分店今日佣金提成收入失败");
			throw new OperateDataException(e);
		}
	}

	@Override
	public Integer countTodayPayOrder(Long subbranchId) throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "参数subbranchId不能为空");
			return salesOrderService.countTodayPayOrder(subbranchId);
		} catch (Exception e) {
			_log.error("查询店铺今日付款订单失败");
			throw new OperateDataException(e);
		}
	}

	@Override
	public Integer countTodayCustomer(Long subbranchId) throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "参数subbranchId不能为空");
			return salesOrderService.countTodayCustomer(subbranchId);
		} catch (Exception e) {
			_log.error("查询店铺今日付款客户数失败");
			throw new OperateDataException(e);
		}
	}

	@Override
	public Map<String, Long> countSubbranchWeekCommission(Long subbranchId)
			throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "参数subbranchId为空");
			Date endTime = DateUtil.getZeroPoint(new Date());
			Date startTime = DateUtil.addDay(endTime, -7);
			List<OperateData> list = salesOrderService.countSubbranchWeekCommission(subbranchId, startTime, endTime);
			Map<String, Long> cover = cover(list, startTime);
			return cover;
		} catch (Exception e) {
			_log.error("查询7日佣金总金额失败失败");
			throw new OperateDataException(e);
		}
	}

	
	@Override
	public Long supplierPayCommission(Long userId) throws OperateDataException {
		try {
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "userId");
			Shop shop = shopProducer.getShopByUserId(userId);
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setShopNo(shop.getNo());
			salesOrderVo.setPaymentTime(DateUtil.getZeroPoint(new Date()));
			Long price = salesOrderService.supplierpayCommission(salesOrderVo);
			return null == price ? OperateData.L_ZERO : price;
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Integer supplierPayOrderCount(Long userId)
			throws OperateDataException {
		try {
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "userId");
			Shop shop = shopProducer.getShopByUserId(userId);
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setShopNo(shop.getNo());
			salesOrderVo.setPaymentTime(DateUtil.getZeroPoint(new Date()));
			Integer count = salesOrderService.supplierPayOrderCount(salesOrderVo);
			return null == count ? OperateData.I_ZERO : count;
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Integer supplierConsumerCount(Long userId)
			throws OperateDataException {
		try {
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "userId");
			Shop shop = shopProducer.getShopByUserId(userId);
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setShopNo(shop.getNo());
			salesOrderVo.setPaymentTime(DateUtil.getZeroPoint(new Date()));
			Integer count = salesOrderService.supplierConsumerCount(salesOrderVo);
			return null == count ? OperateData.I_ZERO : count;
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Map<String, Long> supplierRecivePrices(Long userId)
			throws OperateDataException {
		try {
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "userId");
			Shop shop = shopProducer.getShopByUserId(userId);
			Date preWeekDay = DateUtil.addDay(new Date(), WEEK_DAYS * -1);
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setShopNo(shop.getNo());
			salesOrderVo.setPaymentTime(DateUtil.getZeroPoint(preWeekDay));
			List<OperateData> list = salesOrderService.supplierRecivePrices(salesOrderVo);
			return cover(list, preWeekDay);
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Map<String, Long> supplierPayCommissions(Long userId)
			throws OperateDataException {
		try {
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "userId");
			Shop shop = shopProducer.getShopByUserId(userId);
			Date preWeekDay = DateUtil.addDay(new Date(), WEEK_DAYS * -1);
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setShopNo(shop.getNo());
			salesOrderVo.setPaymentTime(DateUtil.getZeroPoint(preWeekDay));
			List<OperateData> list = salesOrderService.supplierpayCommissions(salesOrderVo);
			return cover(list, preWeekDay);
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}
	
	//数据格式的转换
	private static Map<String, Long> cover(List<OperateData> list, Date begin) {
		if (null == list) list = new ArrayList<OperateData>();
		Map<String, Long> map = new LinkedHashMap<String, Long>();
		Map<String, Long> temp = new LinkedHashMap<String, Long>();
		String key = null;
		Long currentPrice = null;
		//查询数据
		String filterKey = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
		for (OperateData operateData : list) {
			//当天数据过滤
			if (filterKey.equals(operateData.getTimeStamp())) {
				continue;
			}
			temp.put(operateData.getTimeStamp(), operateData.getPrice());
		}
		//木有数据时的过滤
		for (int i = 0; i < WEEK_DAYS; i++) {
			key = DateUtil.DateToString(begin, DateStyle.YYYY_MM_DD);
			currentPrice = temp.get(key);
			if (null == currentPrice) {
				currentPrice = 0L;
			}
			map.put(key, currentPrice);
			begin = DateUtil.addDay(begin, 1);
		}
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getZeroPoint(new Date()));
	}

	@Override
	public Integer countMyShopOrderNumber(String shopNo, Integer status) throws OperateDataException {
		
		try {
			AssertUtil.notNull(shopNo, "param[shopNo] can't be null.");
			
			return salesOrderService.countOwnSalesOrderByShopNo(shopNo, status);
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Integer countOwnSaleSubOrderNumber(Long subbranchId, Integer status, Long userId) throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "param[subbranchId] can't be null.");
			return salesOrderService.countOwnSalesOrderCountBySubbranchId(subbranchId, status, userId);
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Integer countSubSaleSubOrderNumber(Long subbranchId, Integer status)throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "param[subbranchId] can't be null.");
			return salesOrderService.countSubBranchSalesOrderCountBySubbranchId(subbranchId, status);
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Integer countBuyerOrderNumber(Long userId, Integer status)
			throws OperateDataException {
		try {
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			return salesOrderService.countBuyerOrderNumber(userId, status);
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public Pagination<SupplierSalesRankingVo> getSupplierSalesRaning(
			Long userId, Integer orderBy, Integer time, Pagination<SupplierSalesRankingVo> pagination)
			throws OperateDataException {
		try {
			AssertUtil.notNull(userId, "参数userId为空");
			AssertUtil.isTrue(orderBy == 1 || orderBy == 2, "参数orderBy只能为1或2");
			AssertUtil.isTrue(time == 1 || time == 2 || time == 3, "参数time只能为1或2或3");
			AssertUtil.notNull(pagination, "参数pagination不能为空");
			return subbranchProducer.getSupplierSalesRaning(userId, orderBy, time, pagination);
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}
	
	public SubbranchRankingVo getSubbranchRaning(Long subbranchId, Integer days) 
			throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "参数subbranchId为空");
			AssertUtil.notNull(days, "参数days为空");
			return subbranchProducer.getSubbranchRaning(subbranchId, days);
		} catch (Exception e) {
			throw new OperateDataException(e);
		}
	}

	@Override
	public SupplierSalesRankingVo getSupplierSalesRankingDetail(
			Long subbranchId, Integer time) throws OperateDataException {
		try {
			AssertUtil.notNull(subbranchId, "参数subbranchId不能为空");
			AssertUtil.isTrue(time == 1 || time == 2 || time == 3, "参数time只能为1或2或3");
			SupplierSalesRankingVo supplierSalesRankingDetail = subbranchProducer.getSupplierSalesRankingDetail(subbranchId, time);
			Long commision = purchaseOrderService.getCommissionByTime(subbranchId, time);
			supplierSalesRankingDetail.setCommission(commision);
			return supplierSalesRankingDetail;
		} catch (RuntimeException e) {
			throw new OperateDataException(e);
		}
	}

}
