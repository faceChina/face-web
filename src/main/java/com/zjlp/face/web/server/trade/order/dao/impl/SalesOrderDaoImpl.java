package com.zjlp.face.web.server.trade.order.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.daosupport.ReflectUtils;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.mapper.SalesOrderMapper;
import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.trade.order.dao.SalesOrderDao;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SingleStatuCountDto;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.server.user.customer.domain.dto.CustomerOrder;

@Repository
public class SalesOrderDaoImpl implements SalesOrderDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addSalesOrder(SalesOrder salesOrder){
		sqlSession.getMapper(SalesOrderMapper.class).insertSelective(salesOrder);
	}
	
	@Override
	public void editSalesOrder(SalesOrder salesOrder){
		sqlSession.getMapper(SalesOrderMapper.class).updateByPrimaryKeySelective(salesOrder);
	}
	
	@Override
	public SalesOrder getSalesOrderByOrderNo(String orderNo){
		return sqlSession.getMapper(SalesOrderMapper.class).selectByPrimaryKey(orderNo);
	}
	
	@Override
	public Pagination<SalesOrderDto> findSalesOrderPage(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination){
		Integer totalRow = sqlSession.getMapper(SalesOrderMapper.class).getCount(salesOrderVo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salesOrderVo", salesOrderVo);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		List<SalesOrderDto> list = sqlSession.getMapper(SalesOrderMapper.class).selectSalesOrderPage(map);
		pagination.setTotalRow(totalRow);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public List<OrderAppointmentDetail> queryAppointmentDetail(String orderNo) {
		return sqlSession.getMapper(SalesOrderMapper.class).queryAppointmentDetail(orderNo);
	}
	
	@Override
	public Integer countSalesOrder(SalesOrderVo salesOrderVo){
		return sqlSession.getMapper(SalesOrderMapper.class).countSalesOrder(salesOrderVo);
	}
	
	@Override
	public SalesOrderDto getSalesOrderDtoByOrderNo(String orderNo){
		return sqlSession.getMapper(SalesOrderMapper.class).getSalesOrderDtoByOrderNo(orderNo);
	}
	
	@Override
	public Pagination<SalesOrderDto> findSalesOrderPageForWap(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination){
		Integer totalRow = sqlSession.getMapper(SalesOrderMapper.class).getCountForWap(salesOrderVo);
		pagination.setTotalRow(totalRow);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salesOrderVo", salesOrderVo);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		List<SalesOrderDto> list = sqlSession.getMapper(SalesOrderMapper.class).selectSalesOrderPageForWap(map);
		pagination.setDatas(list);
		return pagination;
	}
	

	@Override
	public List<SalesOrder> findSalesOrderListByTSN(String tsn){
		return sqlSession.getMapper(SalesOrderMapper.class).findSalesOrderListByTSN(tsn);
	}
	
	@Override
	public List<SalesOrder> findSalesOrderListByOrderNoList(List<String> orderNoList){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNoList", orderNoList);
		return sqlSession.getMapper(SalesOrderMapper.class).findSalesOrderListByOrderNoList(map);
	}
	
	@Override
	public Long getSalesOrderIncome(String shopNo, Date startTime, Date endTime){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return sqlSession.getMapper(SalesOrderMapper.class).getSalesOrderIncome(map);
	}
	

	
	@Override
	public Long getFreezeSalesOrderIncome(String shopNo){
		return sqlSession.getMapper(SalesOrderMapper.class).getFreezeSalesOrderIncome(shopNo);
	}
	
	@Override
	public Integer validateOrder(SalesOrderVo salesOrderVo){
		return sqlSession.getMapper(SalesOrderMapper.class).validateOrder(salesOrderVo);
	}

	@Override
	public Long getUserFreezeAmount(Long userId){
		return sqlSession.getMapper(SalesOrderMapper.class).getUserFreezeAmount(userId);
	}

	@Override
	public void editSellerMemo(SalesOrder edit){
		sqlSession.getMapper(SalesOrderMapper.class).editSellerMemo(edit);
	}

	@Override
	public List<SalesOrder> findOrderListByUserIdAndSellerIdForWap(Long userId,
			Long sellerId, String beginTime, String endTime) {
		return sqlSession.getMapper(SalesOrderMapper.class).findOrderListByUserIdAndSellerIdForWap(userId, sellerId, beginTime, endTime);
	}

	@Override
	public void saveBookOrderRefuseReason(String orderNo,String memo) {
		sqlSession.getMapper(SalesOrderMapper.class).saveBookOrderRefuseReason(orderNo,memo);
	}

	@Override
	public Integer getAppointNum(Long appointmentId, String receiverPhone) {
		SalesOrderVo salesOrderVo=new SalesOrderVo();
		salesOrderVo.setAppointmentId(appointmentId);
		salesOrderVo.setReceiverPhone(receiverPhone);
		return sqlSession.getMapper(SalesOrderMapper.class).getAppointNum(salesOrderVo);
	}

	@Override
	public List<CustomerOrder> getCustomerOrderByCurrentUserId(Long userId, Long customerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("customerId", customerId);
		return sqlSession.getMapper(SalesOrderMapper.class).getCustomerOrderByCurrentUserId(map);
	}



	@Override
	public Integer findOrderCountByShopNo(String shopNo) {
		return this.sqlSession.getMapper(SalesOrderMapper.class).findOrderCountByShopNo(shopNo);
	}

	@Override
	public List<SingleStatuCountDto> findOrderStatuCountDtoList(
			SalesOrderVo salesOrderVo) {
		return sqlSession.getMapper(SalesOrderMapper.class).selectOrderCount(salesOrderVo);
	}
	


	@Override
	public Long getPopularizeOrderFreezeAmount(Long userId) {
		return sqlSession.getMapper(SalesOrderMapper.class).getPopularizeOrderFreezeAmount(userId);
	}



//	@Override
//	public Integer getCountsByType(SalesOrderDto dto) {
//		return sqlSession.getMapper(SalesOrderMapper.class).selectCountsByType(dto);
//	}


	@Override
	public Map<String,Object> getShopSalesOrderCountInfo(SalesOrderVo salesOrderVo) {
		return sqlSession.getMapper(SalesOrderMapper.class).getShopSalesOrderCountInfo(salesOrderVo);
		
	}

	@Override
	public List<SubBranchSalesOrderVo> selectOwnSalesOrderPageByShopNo(
			String shopNo, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("status", status);
		map.put("orderBy", StringUtils.isBlank(orderBy)?"CREATE_TIME DESC":orderBy);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		return sqlSession.getMapper(SalesOrderMapper.class).selectOwnSalesOrderPageByShopNo(map);
	}

	@Override
	public List<SubBranchSalesOrderVo> selectOwnSalesOrderPageBySubbranchId(Long subbranchId, Integer status, String orderBy,
			String searchKey, Pagination<SubBranchSalesOrderVo> pagination, Long userId, String isShopRequest) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("subbranchId", subbranchId);
		map.put("status", status);
		map.put("orderBy", StringUtils.isBlank(orderBy)?"CREATE_TIME DESC":orderBy);
		map.put("start", pagination.getStart());
		map.put("searchKey", searchKey);
		map.put("pageSize", pagination.getPageSize());
		map.put("userId", userId);
		map.put("isShopRequest", isShopRequest);
		return sqlSession.getMapper(SalesOrderMapper.class).selectOwnSalesOrderPageBySubbranchId(map);
	}

	@Override
	public List<SubBranchSalesOrderVo> selectSubBranchSalesOrderPageByShopNo(
			String shopNo, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("status", status);
		map.put("orderBy", StringUtils.isBlank(orderBy)?"CREATE_TIME DESC":orderBy);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderPageByShopNo(map);
	}

	@Override
	public List<SubBranchSalesOrderVo> selectSubBranchSalesOrderPageBySubbranchId(
			Long subbranchId, Integer status, String orderBy,String searchKey,
			Pagination<SubBranchSalesOrderVo> pagination) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("subbranchId", subbranchId);
		map.put("status", status);
		map.put("searchKey", searchKey);
		map.put("orderBy", StringUtils.isBlank(orderBy)?"CREATE_TIME DESC":orderBy);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderPageBySubbranchId(map);
	}

	@Override
	public Integer selectOwnSalesOrderCountByShopNo(String shopNo,Integer status) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("status", status);
		return sqlSession.getMapper(SalesOrderMapper.class).selectOwnSalesOrderCountByShopNo(map);
	}

	@Override
	public Integer selectOwnSalesOrderCountBySubbranchId(Long subbranchId, Integer status, String searchKey, Long userId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("subbranchId", subbranchId);
		map.put("status", status);
		map.put("searchKey", searchKey);
		map.put("userId", userId);
		return sqlSession.getMapper(SalesOrderMapper.class).selectOwnSalesOrderCountBySubbranchId(map);
	}

	@Override
	public Integer selectSubBranchSalesOrderCountByShopNo(String shopNo,
			Integer status) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("status", status);
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderCountByShopNo(map);
	}

	@Override
	public Integer selectSubBranchSalesOrderCountBySubbranchId(Long subbranchId,
			Integer status,String searchKey) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("subbranchId", subbranchId);
		map.put("status", status);
		map.put("searchKey", searchKey);
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderCountBySubbranchId(map);
	}


	@Override
	public Integer selectSubBranchSalesOrderCountForProducer(String shopNo,
			Date time) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("time", time);
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderCountForProducer(map);
	}

	@Override
	public Integer selectSubBranchSalesOrderCountForDistributor(String shopNo,
			Date time) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("time", time);
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderCountForDistributor(map);
	}

	@Override
	public Long selectSubBranchSalesOrderTotalPrice(String shopNo, Date time) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("time", time);
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderTotalPrice(map);
	}

	@Override
	public Long selectSubBranchSalesOrderUnFreezeTotalProfitPrice(Long userId, Date time) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", String.valueOf(userId));
		map.put("time", time);
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderUnFreezeTotalProfitPrice(map);
	}

	@Override
	public SubBranchSalesOrderVo selectSubBranchSalesOrderByPrimaryKey(
			String orderNo, String purchaserNo) {
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubBranchSalesOrderByPrimaryKey(orderNo,purchaserNo);
	}
	
	@Override
	public SubBranchSalesOrderVo selectSubOrder(String orderNo, String supplierNo) {
		return sqlSession.getMapper(SalesOrderMapper.class).selectSubOrder(orderNo,supplierNo);
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> selectAllMySalesOrderByShopNo(SalesOrder salesOrder, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination, Long userId, String isShopRequest) {
		Map<String, Object> map=ReflectUtils.getFieldMapForClass(salesOrder);
		Integer totalRow = sqlSession.getMapper(SalesOrderMapper.class).countAllMySalesOrderByShopNo(map);
		pagination.setTotalRow(totalRow);
		map.put("orderBy", StringUtils.isBlank(orderBy)?"CREATE_TIME DESC":orderBy);
		map.put("searchKey", salesOrder.getSearchKey());
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		map.put("userId", userId);
		map.put("isShopRequest", isShopRequest);
		List<SubBranchSalesOrderVo> lists=sqlSession.getMapper(SalesOrderMapper.class).selectAllMySalesOrderByShopNo(map);
		pagination.setDatas(lists);
		return pagination;
	}

	@Override
	public List<SalesOrder> selectAll() {
		return sqlSession.getMapper(SalesOrderMapper.class).seletAll();
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findHistoryOrderForOwn(Long subBranchId, Integer status, Long customerId,
			String orderBy, Pagination<SubBranchSalesOrderVo> pagination, String isShopRequest) throws OrderException {
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("subBranchId", subBranchId);
		map.put("status", status);
		map.put("isShopRequest", isShopRequest);
		map.put("customerId", customerId);
		Integer totalRow = sqlSession.getMapper(SalesOrderMapper.class).countHistoryOrderForOwn(map);
		pagination.setTotalRow(totalRow);
		map.put("orderBy", StringUtils.isBlank(orderBy)?"CREATE_TIME DESC":orderBy);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		List<SubBranchSalesOrderVo> list = sqlSession.getMapper(SalesOrderMapper.class).selectHistoryOrderForOwn(map);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findHistoryOrderForSub(
			Long subBranchId, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) throws OrderException {
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("subBranchId", subBranchId);
		map.put("status", status);
		Integer totalRow = sqlSession.getMapper(SalesOrderMapper.class).countHistoryOrderForSub(map);
		pagination.setTotalRow(totalRow);
		map.put("orderBy", StringUtils.isBlank(orderBy)?"CREATE_TIME DESC":orderBy);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		List<SubBranchSalesOrderVo> list = sqlSession.getMapper(SalesOrderMapper.class).selectHistoryOrderForSub(map);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public Long countTodayShopCommission(Long subbranchId, String userCell) {
		Date today = DateUtil.getZeroPoint(new Date());
		return sqlSession.getMapper(SalesOrderMapper.class).countTodayShopCommission(subbranchId, userCell, today);
	}

	@Override
	public Long countTodaySubbranchCommission(Long subbranchId, String userCell) {
		Date today = DateUtil.getZeroPoint(new Date());
		return sqlSession.getMapper(SalesOrderMapper.class).countTodaySubbranchCommission(subbranchId, userCell, today);
	}

	@Override
	public Integer countTodayPayOrder(Long subbranchId) {
		Date today = DateUtil.getZeroPoint(new Date());
		return sqlSession.getMapper(SalesOrderMapper.class).countTodayPayOrder(subbranchId, today);
	}

	@Override
	public Integer countTodayCustomer(Long subbranchId) {
		Date today = DateUtil.getZeroPoint(new Date());
		return sqlSession.getMapper(SalesOrderMapper.class).countTodayCustomer(subbranchId, today);
	}

	@Override
	public Long countTodayCommission(Long subbranchId) {
		Date today = DateUtil.getZeroPoint(new Date());
		return sqlSession.getMapper(SalesOrderMapper.class).countTodayCommission(subbranchId, today);
	}

	@Override
	public List<OperateData> countSubbranchWeekCommission(Long subbranchId,
			Date startTime, Date endTime) {
		return sqlSession.getMapper(SalesOrderMapper.class).countSubbranchWeekCommission(subbranchId, startTime, endTime);
	}

	
	
	@Override
	public List<OperateData> selectSupplierRecivePrices(SalesOrderVo salesOrderVo) {
		return sqlSession.getMapper(SalesOrderMapper.class).selectSupplierRecivePrices(salesOrderVo);
	}

	@Override
	public Long selectSupplierRecivePrice(SalesOrderVo salesOrderVo) {
		return sqlSession.getMapper(SalesOrderMapper.class).selectSupplierRecivePrice(salesOrderVo);
	}

	@Override
	public Integer supplierPayOrderCount(SalesOrderVo salesOrderVo) {
		return sqlSession.getMapper(SalesOrderMapper.class).selectSupplierPayOrderCount(salesOrderVo);
	}

	@Override
	public Integer supplierConsumerCount(SalesOrderVo salesOrderVo) {
		return sqlSession.getMapper(SalesOrderMapper.class).selectSupplierConsumerCount(salesOrderVo);
	}
	

	@Override
	public Integer countOwnSalesOrderByShopNo(String shopNo, Integer status) {
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("status", status);
		return sqlSession.getMapper(SalesOrderMapper.class).countAllMySalesOrderByShopNo(map);
		 
	}

	@Override
	public Integer countBuyerOrderNumber(Long userId, Integer status) {
		SalesOrderVo salesOrderVo =  new SalesOrderVo();
		salesOrderVo.setUserId(userId);
		salesOrderVo.setStatus(status);
		return sqlSession.getMapper(SalesOrderMapper.class).getCountForWap(salesOrderVo);
	}

	@Override
	public Long getSubbranchFreezeAmount(String cell) {
		return sqlSession.getMapper(SalesOrderMapper.class).getSubbranchFreezeAmount(cell);
	}

	@Override
	public Integer countBookOrder(String shopNo, Integer status) {
		return sqlSession.getMapper(SalesOrderMapper.class).countBookOrder(shopNo, status);
	}

	@Override
	public Integer countCustomerOrderInMyShop(String shopNo, Long customerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("customerId", customerId);
		return this.sqlSession.getMapper(SalesOrderMapper.class).countCustomerOrderInMyShop(map);
	}

	@Override
	public List<SalesOrder> findCustomerOrderInMyShop(String shopNo, Long customerId, Pagination<SalesOrder> pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("customerId", customerId);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		return this.sqlSession.getMapper(SalesOrderMapper.class).findCustomerOrderInMyShop(map);
	}

	@Override
	public Long getAencyFreezeAmount(String shopNo) {
		return this.sqlSession.getMapper(SalesOrderMapper.class).selectAgrencyFreezeAmount(shopNo);
	}
	
}
