package com.zjlp.face.web.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingListVo;

public interface PurchaseOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseOrder record);
    
    int insertSelective(PurchaseOrder record);

    PurchaseOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int updateByPrimaryKey(PurchaseOrder record);

	List<PurchaseOrder> selectList(Map<String, Object> map);

	PurchaseOrderDto selectPurchaseOrder(Map<String, Object> map);

	List<Long> selectPopularizeSkuIdList(Map<String, Object> map);

	Integer selectPurchaseOrderCountByOrderNOAndCooperationWay(
			Map<String, Object> map);

	List<PurchaseOrderDto> selectPromoteOrdersByOrderNo(String orderNo);
	
	PurchaseOrder selectSupplierOrder(Map<String, Object> map);

	List<PurchaseOrder> selectAll();

	Integer selectSubbranchOrderTDPCount(@Param("cell") String cell, @Param("today") Date today);

	List<OperateData> selectSupplierpayCommissions(SalesOrderVo salesOrderVo);

	Long selectSupplierpayCommission(SalesOrderVo salesOrderVo);
	
	String selectOrderSourceByPrimaryKey(Long id);
	
	List<SubbranchRankingListVo> selectSubbranchRaningList(@Param("shopNo") String shopNo, @Param("subbranchId") Long subbranchId, @Param("days") Integer days);
	Integer selectSubbranchMyRaning (@Param("shopNo") String shopNo, @Param("subbranchId") Long subbranchId, @Param("days") Integer days);
	Long selectSubbranchMyCommission (@Param("shopNo") String shopNo, @Param("subbranchId") Long subbranchId, @Param("days") Integer days);

	Long getCommissionByTime(@Param("subbranchId")Long subbranchId, @Param("countDay")Date countDay, @Param("today")Date today);

	Long getOrderPayCommission(@Param("orderNo")String orderNo, @Param("supplierNo")String supplierNo,
			@Param("supplierType")Integer supplierType);
}