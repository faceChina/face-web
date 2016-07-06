package com.zjlp.face.web.server.trade.order.producer;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;

public interface PurchaseOrderProducer {

	/**
	 * 订单号查询交易采购单
	 * @Title: findPurchaseOrderList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo 订单号
	 * @param cooperationWay 合作方式 :1分销 2推广
	 * @return
	 * @date 2015年5月8日 下午2:09:24
	 * @author dzq
	 */
	List<PurchaseOrder> findPurchaseOrderList(String orderNo,Integer cooperationWay) throws Exception;
	
	/**
	 * 订单是否拥有推广信息
	 * @Title: hasPopularizeOrder 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo
	 * @return
	 * @date 2015年5月15日 下午5:46:15  
	 * @author dzq
	 */
	boolean hasPopularizeForSalesOrder(String orderNo);
	
	/**
	 * 订单是否拥有分销信息
	 * @Title: hasDistributeForSalesOrder 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo
	 * @return
	 * @date 2015年5月15日 下午5:47:24  
	 * @author dzq
	 */
	boolean hasDistributeForSalesOrder(String orderNo);
	
	/**
	 * 订单是否拥有员工利益
	 * @Title: hasPopularizeOrder 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo
	 * @return
	 * @date 2015年5月15日 下午5:46:15  
	 * @author dzq
	 */
	boolean hasEmployeeForSalesOrder(String orderNo);
	
	PurchaseOrderDto getPurchaseOrder(String orderNo, String purchaserNo) throws Exception ;
}
