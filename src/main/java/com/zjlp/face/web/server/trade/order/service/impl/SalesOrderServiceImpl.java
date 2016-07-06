package com.zjlp.face.web.server.trade.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.trade.order.dao.OrderAppointmentDetailDao;
import com.zjlp.face.web.server.trade.order.dao.OrderItemDao;
import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderDao;
import com.zjlp.face.web.server.trade.order.dao.SalesOrderDao;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SingleStatuCountDto;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;
import com.zjlp.face.web.server.user.customer.domain.dto.CustomerOrder;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {
	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private SalesOrderDao salesOrderDao;
	
	@Autowired
	private OrderItemDao orderItemDao;
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Autowired
	private OrderAppointmentDetailDao orderAppointmentDetailDao;
	
	@Override
	public void addSalesOrder(SalesOrder salesOrder){
		salesOrderDao.addSalesOrder(salesOrder);
	}
	
	@Override
	public void editSalesOrder(SalesOrder salesOrder){
		salesOrderDao.editSalesOrder(salesOrder);
	}
	
	@Override
	public SalesOrder getSalesOrderByOrderNo(String orderNo){
		return salesOrderDao.getSalesOrderByOrderNo(orderNo);
	}
	
	@Override
	public SalesOrderDto getSalesOrderDtoByOrderNo(String orderNo){
		return salesOrderDao.getSalesOrderDtoByOrderNo(orderNo);
	}
	
	@Override
	public Pagination<SalesOrderDto> findSalesOrderDetailPage(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination){
		pagination = salesOrderDao.findSalesOrderPage(salesOrderVo, pagination);
		List<SalesOrderDto> list = pagination.getDatas();
		for(SalesOrderDto dto : list){
			dto.setOrderItemList(orderItemDao.findOrderItemListByOrderNo(dto.getOrderNo()));
			dto.setOrderAppointmentDetails(salesOrderDao.queryAppointmentDetail(dto.getOrderNo()));
		}
		return pagination;
	}
	
	@Override
	public Integer countSalesOrder(SalesOrderVo salesOrderVo){
		return salesOrderDao.countSalesOrder(salesOrderVo);
	}
	
	@Override
	public List<OrderItem> getOrderItemListByOrderNo(String orderNo){
		return orderItemDao.findOrderItemListByOrderNo(orderNo);
	}

	@Override
	public void editSalesOrderStatus(String orderNo, Integer status){
		SalesOrder order = salesOrderDao.getSalesOrderByOrderNo(orderNo);
		SalesOrderStatusValidate statusValidate = new SalesOrderStatusValidate();
		if(!statusValidate.validate(status, order.getStatus())){
			log.info("订单状态异常不能修改:" + orderNo + "," + status + "," + order.getStatus());
			throw new OrderException("订单状态异常,不能修改");
		}
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setOrderNo(orderNo);
		salesOrder.setStatus(status);
		if(Constants.STATUS_PAY.intValue() == status){
			salesOrder.setPaymentTime(new Date());
		}else if(Constants.STATUS_SEND.intValue() == status){
			salesOrder.setDeliveryTime(new Date());
		}else if (Constants.STATUS_RECEIVE.intValue() == status){
			salesOrder.setReceiveTime(new Date());
		}
		else if(Constants.STATUS_COMPILE.intValue() == status || Constants.BOOKORDER_STATUS_CONFIRM.intValue() == status){
			salesOrder.setConfirmTime(new Date());
		}else if(Constants.STATUS_CANCEL.intValue() == status || Constants.BOOKORDER_STATUS_CANCEL.intValue() == status){
			salesOrder.setCancelTime(new Date());
		}else if(Constants.STATUS_DELETE.intValue() == status){
			salesOrder.setDeleteTime(new Date());
		}else if(Constants.STATUS_CLOSE.intValue() == status){
			salesOrder.setClosingTime(new Date());
		}else if (Constants.BOOKORDER_STATUS_REFUSE.intValue() == status) {
			salesOrder.setRefuseTime(new Date());
		}
		salesOrder.setUpdateTime(new Date());
		salesOrderDao.editSalesOrder(salesOrder);
	}
	
	@Override
	public Pagination<SalesOrderDto> findSalesOrderPageForWap(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination){
		pagination = salesOrderDao.findSalesOrderPageForWap(salesOrderVo, pagination);
		List<SalesOrderDto> list = pagination.getDatas();
		for(SalesOrderDto dto : list){
			dto.setOrderItemList(orderItemDao.findOrderItemListByOrderNo(dto.getOrderNo()));
			if (dto.getOrderCategory().intValue() == 3) {
				List<OrderAppointmentDetail> appointmentDetailList = orderAppointmentDetailDao.findOrderAppointmentDetailListByOrderNo(dto.getOrderNo());
				dto.setOrderAppointmentDetails(appointmentDetailList);
			}
		}
		return pagination;
	}
	
	@Override
	public List<SalesOrder> findSalesOrderListByTSN(String tsn){
		return salesOrderDao.findSalesOrderListByTSN(tsn);
	}
	
	
	@Override
	public List<SalesOrder> findSalesOrderListByOrderNoList(List<String> orderNoList){
		return salesOrderDao.findSalesOrderListByOrderNoList(orderNoList);
	}
	
	@Override
	public Long getSalesOrderIncome(String shopNo, Date startTime, Date endTime){
		Long salesOrderIncome = salesOrderDao.getSalesOrderIncome(shopNo, startTime, endTime);
		return salesOrderIncome == null ? 0l : salesOrderIncome;
	}
	
	@Override
	public Long getFreezeSalesOrderIncome(String shopNo){
		Long freezeSalesOrderIncome = salesOrderDao.getFreezeSalesOrderIncome(shopNo);
		return freezeSalesOrderIncome == null ? 0l : freezeSalesOrderIncome;
	}
	
	@Override
	public Integer validateOrder(SalesOrderVo salesOrderVo){
		return salesOrderDao.validateOrder(salesOrderVo);
	}

	@Override
	public Long getUserFreezeAmount(Long userId){
		Long amount = salesOrderDao.getUserFreezeAmount(userId);
		return null == amount ? 0l : amount;
	}

	@Override
	public void editSellerMemo(SalesOrder edit){
		salesOrderDao.editSellerMemo(edit);
	}

	@Override
	public List<SalesOrder> findOrderListByUserIdAndSellerIdForWap(Long userId,
			Long sellerId, String beginTime, String endTime) {
		return this.salesOrderDao.findOrderListByUserIdAndSellerIdForWap(userId, sellerId, beginTime, endTime);
	}

	@Override
	public void saveBookOrderRefuseReason(String orderNo,String memo) {
		salesOrderDao.saveBookOrderRefuseReason(orderNo,memo);
	}

	@Override
	public Integer getAppointNum(Long appointmentId, String receiverPhone) {
		return salesOrderDao.getAppointNum(appointmentId,receiverPhone);
	}

	
	@Override
	public Integer findOrderCountByShopNo(String shopNo) {
		return this.salesOrderDao.findOrderCountByShopNo(shopNo);
	}

	@Override
	public List<SingleStatuCountDto> findOrderStatuCountDtoList(
			SalesOrderVo salesOrderVo) {
		return salesOrderDao.findOrderStatuCountDtoList(salesOrderVo);
	}


	@Override
	public Long getPopularizeOrderFreezeAmount(Long userId) {
		Long popularizeOrderFreezeAmount =  salesOrderDao.getPopularizeOrderFreezeAmount(userId);
		return popularizeOrderFreezeAmount==null ? 0l : popularizeOrderFreezeAmount;
	}

	@Override
	public List<CustomerOrder> findCustomerOrderByCurrentUserId(Long userId, Long customerId) {
		return salesOrderDao.getCustomerOrderByCurrentUserId(userId, customerId);
	}

	@Override
	public Map<String,Integer> getShopSalesOrderCountInfo(SalesOrderVo salesOrderVo) {
		Map<String,Object> map = salesOrderDao.getShopSalesOrderCountInfo(salesOrderVo);
		Map<String,Integer> re = new HashMap<String,Integer>();
		for(String key : map.keySet()){
			Object obj = map.get(key);
			Integer value = Integer.valueOf(obj.toString());
			re.put(key, value);
		}
		return re;
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findOwnSalesOrderPageByShopNo(String shopNo,
			Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) {
		Integer count=this.countOwnSalesOrderCountByShopNo(shopNo,status);
		pagination.setTotalRow(count);
		List<SubBranchSalesOrderVo> list=salesOrderDao.selectOwnSalesOrderPageByShopNo(shopNo, status, orderBy, pagination);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findOwnSalesOrderPageBySubbranchId(Long subbranchId, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination, Long userId, String isShopRequest) {
		Integer count = this.countOwnSalesOrderCountBySubbranchId(subbranchId, status, userId);
		pagination.setTotalRow(count);
		List<SubBranchSalesOrderVo> list = salesOrderDao.selectOwnSalesOrderPageBySubbranchId(subbranchId, status, orderBy, null,
				pagination, userId, isShopRequest);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findOwnSalesOrderPageBySubbranchId(Long subbranchId, Integer status, String orderBy,
			String searchKey, Pagination<SubBranchSalesOrderVo> pagination, Long userId, String isShopRequest) {
		Integer count = salesOrderDao.selectOwnSalesOrderCountBySubbranchId(subbranchId, status, searchKey, userId);
		pagination.setTotalRow(count);
		List<SubBranchSalesOrderVo> list = salesOrderDao.selectOwnSalesOrderPageBySubbranchId(subbranchId, status, orderBy,
				searchKey, pagination, userId, isShopRequest);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchSalesOrderPageByShopNo(String shopNo,
			Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) {
		Integer count=this.countSubBranchSalesOrderCountByShopNo(shopNo,status);
		pagination.setTotalRow(count);
		List<SubBranchSalesOrderVo> list=salesOrderDao.selectSubBranchSalesOrderPageByShopNo(shopNo, status, orderBy, pagination);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchSalesOrderPageBySubbranchId(Long subbranchId,
			Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) {
		Integer count=this.countSubBranchSalesOrderCountBySubbranchId(subbranchId,status);
		pagination.setTotalRow(count);
		List<SubBranchSalesOrderVo> list=salesOrderDao.selectSubBranchSalesOrderPageBySubbranchId(subbranchId, status, orderBy, null,pagination);
		pagination.setDatas(list);
		return pagination;
	}
	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchSalesOrderPageBySubbranchId(Long subbranchId,
			Integer status, String orderBy,String searchKey,
			Pagination<SubBranchSalesOrderVo> pagination) {
		Integer count=salesOrderDao.selectSubBranchSalesOrderCountBySubbranchId(subbranchId,status,searchKey);
		pagination.setTotalRow(count);
		List<SubBranchSalesOrderVo> list=salesOrderDao.selectSubBranchSalesOrderPageBySubbranchId(subbranchId, status, orderBy, searchKey,pagination);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public Integer countOwnSalesOrderCountByShopNo(String shopNo,Integer status) {
		return salesOrderDao.selectOwnSalesOrderCountByShopNo(shopNo,status);
	}

	@Override
	public Integer countOwnSalesOrderCountBySubbranchId(Long subbranchId, Integer status, Long userId) {
		return salesOrderDao.selectOwnSalesOrderCountBySubbranchId(subbranchId, status, null, userId);
	}

	@Override
	public Integer countSubBranchSalesOrderCountByShopNo(String shopNo,
			Integer status) {
		return salesOrderDao.selectSubBranchSalesOrderCountByShopNo(shopNo,status);
	}

	@Override
	public Integer countSubBranchSalesOrderCountBySubbranchId(Long subbranchId,
			Integer status) {
		return salesOrderDao.selectSubBranchSalesOrderCountBySubbranchId(subbranchId,status,null);
	}


	@Override
	public Integer countSubBranchSalesOrderCountForProducer(String shopNo) {
		Date today=DateUtil.getZeroPoint(new Date());
		return salesOrderDao.selectSubBranchSalesOrderCountForProducer(shopNo, today);
	}

	@Override
	public Integer countSubBranchSalesOrderCountForDistributor(String shopNo) {
		Date today=DateUtil.getZeroPoint(new Date());
		return salesOrderDao.selectSubBranchSalesOrderCountForDistributor(shopNo, today);
	}

	@Override
	public Long getSubBranchSalesOrderTotalPrice(String shopNo) {
		Date today=DateUtil.getZeroPoint(new Date());
		return salesOrderDao.selectSubBranchSalesOrderTotalPrice(shopNo, today);
	}

	@Override
	public Long getSubBranchSalesOrderUnFreezeTotalProfitPrice(Long userId) {
		Date today=DateUtil.getZeroPoint(new Date());
		return salesOrderDao.selectSubBranchSalesOrderUnFreezeTotalProfitPrice(userId, today);
	}

	@Override
	public SubBranchSalesOrderVo getSubBranchSalesOrderByPrimaryKey(
			String orderNo, String purchaserNo) {
		return salesOrderDao.selectSubBranchSalesOrderByPrimaryKey(orderNo, purchaserNo);
	}
	
	@Override
	public SubBranchSalesOrderVo getSubOrder(String orderNo,
			String supplierNo) {
		return salesOrderDao.selectSubOrder(orderNo, supplierNo);
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findAllMySalesOrderByShopNo(SalesOrder salesOrder, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination, Long userId, String isShopRequest) {
		return salesOrderDao.selectAllMySalesOrderByShopNo(salesOrder, orderBy, pagination, userId, isShopRequest);
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findHistoryOrderForOwn(Long subBranchId, Integer status, Long customerId,
			String orderBy, Pagination<SubBranchSalesOrderVo> pagination, String isShopRequest) throws OrderException {
		return salesOrderDao.findHistoryOrderForOwn(subBranchId, status, customerId, orderBy, pagination, isShopRequest);

	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findHistoryOrderForSub(
			Long subBranchId, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) throws OrderException {
		return salesOrderDao.findHistoryOrderForSub(subBranchId, status, orderBy, pagination);
	}

	@Override
	public Long countTodayShopCommission(Long subbranchId, String userCell) {
		Long todayShopCommission = salesOrderDao.countTodayShopCommission(subbranchId, userCell);
		return todayShopCommission == null ? 0L : todayShopCommission;
	}

	@Override
	public Long countTodaySubbranchCommission(Long subbranchId, String userCell) {
		Long todaySubbranchCommission = salesOrderDao.countTodaySubbranchCommission(subbranchId, userCell);
		return todaySubbranchCommission == null ? 0L : todaySubbranchCommission;
	}

	@Override
	public Integer countTodayPayOrder(Long subbranchId) {
		Integer todayPayOrder = salesOrderDao.countTodayPayOrder(subbranchId);
		return todayPayOrder == null ? 0 : todayPayOrder;
	}

	@Override
	public Integer countTodayCustomer(Long subbranchId) {
		Integer todayCustomer = salesOrderDao.countTodayCustomer(subbranchId);
		return todayCustomer == null ? 0 : todayCustomer;
	}

	@Override
	public Long countTodayCommission(Long subbranchId) {
		Long todayCommission = salesOrderDao.countTodayCommission(subbranchId);
		return todayCommission == null ? 0 : todayCommission;
	}

	@Override
	public List<OperateData> countSubbranchWeekCommission(Long subbranchId,
			Date startTime, Date endTime) {
		return salesOrderDao.countSubbranchWeekCommission(subbranchId, startTime, endTime);
	}
	
	

	//----------------------------------经营数据统计    start--------------------------------------------
	@Override
	public List<OperateData> supplierRecivePrices(SalesOrderVo salesOrderVo) {
		return salesOrderDao.selectSupplierRecivePrices(salesOrderVo);
	}

	@Override
	public List<OperateData> supplierpayCommissions(SalesOrderVo salesOrderVo) {
		return purchaseOrderDao.selectSupplierpayCommissions(salesOrderVo);
	}

	@Override
	public Long supplierRecivePrice(SalesOrderVo salesOrderVo) {
		return salesOrderDao.selectSupplierRecivePrice(salesOrderVo);
	}
	
	@Override
	public Long supplierpayCommission(SalesOrderVo salesOrderVo) {
		return purchaseOrderDao.selectSupplierpayCommission(salesOrderVo);
	}

	@Override
	public Integer supplierPayOrderCount(SalesOrderVo salesOrderVo) {
		return salesOrderDao.supplierPayOrderCount(salesOrderVo);
	}

	@Override
	public Integer supplierConsumerCount(SalesOrderVo salesOrderVo) {
		return salesOrderDao.supplierConsumerCount(salesOrderVo);
	}
	
	//----------------------------------经营数据统计    end--------------------------------------------


	@Override
	public Integer countOwnSalesOrderByShopNo(String shopNo, Integer status) {
		return salesOrderDao.countOwnSalesOrderByShopNo(shopNo, status);
	}

	@Override
	public Integer countBuyerOrderNumber(Long userId, Integer status) {
		return salesOrderDao.countBuyerOrderNumber(userId, status);
	}

	@Override
	public Long getSubbranchFreezeAmount(String cell) {
		Long freezeAmount = salesOrderDao.getSubbranchFreezeAmount(cell);
		return freezeAmount == null ? 0L : freezeAmount;
	}

	@Override
	public Integer countBookOrder(String shopNo, Integer status) {
		return salesOrderDao.countBookOrder(shopNo, status);
	}

	@Override
	public Pagination<SalesOrder> findCustomerOrderInMyShop(String shopNo, Long customerId, Pagination<SalesOrder> pagination) {
		Integer count = this.salesOrderDao.countCustomerOrderInMyShop(shopNo, customerId);
		pagination.setTotalRow(count);
		List<SalesOrder> list = this.salesOrderDao.findCustomerOrderInMyShop(shopNo, customerId, pagination);
		pagination.setDatas(list);
		return pagination;
	}

}
