package com.zjlp.face.web.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SingleStatuCountDto;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.server.user.customer.domain.dto.CustomerOrder;

public interface SalesOrderMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(SalesOrder record);

    int insertSelective(SalesOrder record);

    SalesOrder selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(SalesOrder record);

    int updateByPrimaryKey(SalesOrder record);
	
	Integer countSalesOrder(SalesOrderVo salesOrderVo);
	
	Integer getCount(SalesOrderVo salesOrderVo);
	
	List<SalesOrderDto> selectSalesOrderPage(Map<String, Object> map);
	
	SalesOrderDto getSalesOrderDtoByOrderNo(String orderNo);
	
	List<SalesOrderDto> selectSalesOrderPageForWap(Map<String, Object> map);
	
	Integer getCountForWap(SalesOrderVo salesOrderVo);
	
	List<SalesOrder> findSalesOrderListByTSN(String tsn);
	
	List<SalesOrder> findSalesOrderListByOrderNoList(Map<String, Object> map);
	
	Long getSalesOrderIncome(Map<String, Object> map);
	
	Long getFreezeSalesOrderIncome(String shopNo);
	
	Integer validateOrder(SalesOrderVo salesOrderVo);

	Long getUserFreezeAmount(Long userId);

	void editSellerMemo(SalesOrder edit);

	List<SalesOrder> findOrderListByUserIdAndSellerIdForWap(@Param("userId")Long userId,
			@Param("sellerId")Long sellerId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);

	void saveBookOrderRefuseReason(@Param("orderNo") String orderNo,@Param("memo") String memo);

	List<OrderAppointmentDetail> queryAppointmentDetail(String orderNo);

	Integer getAppointNum(SalesOrderVo salesOrderVo);

	List<CustomerOrder> getCustomerOrderByCurrentUserId(Map<String, Object> map);


	Integer findOrderCountByShopNo(String shopNo);

	
	List<SingleStatuCountDto> selectOrderCount(
			SalesOrderVo salesOrderVo);

	Long getPopularizeOrderFreezeAmount(Long userId);

	Integer selectCountsByType(SalesOrderDto dto);
	
	Map<String,Object> getShopSalesOrderCountInfo(SalesOrderVo salesOrderVo);
	
	
	List<SubBranchSalesOrderVo> selectOwnSalesOrderPageByShopNo(Map<String,Object> map);
	List<SubBranchSalesOrderVo> selectOwnSalesOrderPageBySubbranchId(Map<String,Object> map);
	List<SubBranchSalesOrderVo> selectSubBranchSalesOrderPageByShopNo(Map<String,Object> map);
	List<SubBranchSalesOrderVo> selectSubBranchSalesOrderPageBySubbranchId(Map<String,Object> map);
	
	Integer selectOwnSalesOrderCountByShopNo(Map<String,Object> map);
	Integer selectOwnSalesOrderCountBySubbranchId(Map<String,Object> map);
	
	Integer selectSubBranchSalesOrderCountByShopNo(Map<String,Object> map);
	Integer selectSubBranchSalesOrderCountBySubbranchId(Map<String,Object> map);
	
	Integer selectSubBranchSalesOrderCountForProducer(Map<String,Object> map);
	Integer selectSubBranchSalesOrderCountForDistributor(Map<String,Object> map);
	Long selectSubBranchSalesOrderTotalPrice(Map<String,Object> map);
	Long selectSubBranchSalesOrderUnFreezeTotalProfitPrice(Map<String,Object> map);
	SubBranchSalesOrderVo selectSubBranchSalesOrderByPrimaryKey(@Param("orderNo") String orderNo,@Param("purchaserNo") String purchaserNo);
	SubBranchSalesOrderVo selectSubOrder(@Param("orderNo") String orderNo,@Param("supplierNo") String supplierNo);
	List<SubBranchSalesOrderVo> selectAllMySalesOrderByShopNo(Map<String,Object> map);
	Integer countAllMySalesOrderByShopNo(Map<String,Object> map);
	
	List<SalesOrder> seletAll();
	
	Integer countHistoryOrderForOwn(Map<String,Object> map);
	Integer countHistoryOrderForSub(Map<String,Object> map);
	
	List<SubBranchSalesOrderVo> selectHistoryOrderForOwn(Map<String,Object> map);
	
	List<SubBranchSalesOrderVo> selectHistoryOrderForSub(Map<String,Object> map);

	Long countTodayShopCommission(@Param("subbranchId")Long subbranchId, @Param("userCell")String userCell, @Param("today")Date today);

	Long countTodaySubbranchCommission(@Param("subbranchId")Long subbranchId, @Param("userCell")String userCell,
			@Param("today")Date today);

	Integer countTodayPayOrder(@Param("subbranchId")Long subbranchId, @Param("today")Date today);

	Integer countTodayCustomer(@Param("subbranchId")Long subbranchId, @Param("today")Date today);

	Long countTodayCommission(@Param("subbranchId")Long subbranchId, @Param("today")Date today);

	List<OperateData> countSubbranchWeekCommission(@Param("subbranchId")Long subbranchId,
			@Param("startTime")Date startTime, @Param("endTime")Date endTime);
	
	List<OperateData> selectSupplierRecivePrices(SalesOrderVo salesOrderVo);

	Long selectSupplierRecivePrice(SalesOrderVo salesOrderVo);

	Integer selectSupplierPayOrderCount(SalesOrderVo salesOrderVo);

	Integer selectSupplierConsumerCount(SalesOrderVo salesOrderVo);

	Long getSubbranchFreezeAmount(String cell);

	Integer countBookOrder(@Param("shopNo")String shopNo, @Param("status")Integer status);

	Integer countCustomerOrderInMyShop(Map<String, Object> map);

	List<SalesOrder> findCustomerOrderInMyShop(Map<String, Object> map);
	
	Long selectAgrencyFreezeAmount(String shopNo);
}