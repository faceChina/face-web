package com.zjlp.face.web.server.trade.order.producer;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

/**
 * @ClassName: com.jzwgj.server.trade.order.producer.SalesOrderProducer
 * @Description: 描述
 * @date: 2015年3月16日 上午9:25:47
 * @author: zyl
 */
public interface SalesOrderProducer {
	
	/**
	 * @Description: 查询订单
	 * @param orderNo
	 * @return
	 * @date: 2015年3月16日 上午9:26:05
	 * @author: zyl
	 */
	SalesOrder getSalesOrderByOrderNo(String orderNo);
	
	/**
	 * @Description: 查询订单细项
	 * @param orderNo
	 * @return
	 * @date: 2015年3月16日 上午9:26:09
	 * @author: zyl
	 */
	List<OrderItem> getOrderItemListByOrderNo(String orderNo);
	
	/**
	 * @Description: 修改订单状态
	 * @param orderNo TODO
	 * @param status
	 * @date: 2015年3月16日 上午9:29:13
	 * @author: zyl
	 */
	void editSalesOrderStatus(String orderNo, Integer status);
	
	/**
	 * @Description: 修改订单(不包含订单状态)
	 * @param salesOrder
	 * @date: 2015年3月20日 上午9:23:25
	 * @author: zyl
	 */
	void editSalesOrder(SalesOrder salesOrder);
	
	
	/**
	 * @Description: 交易流水号查询订单
	 * @param tsn
	 * @return
	 * @date: 2015年3月20日 上午11:45:01
	 * @author: zyl
	 */
	List<SalesOrder> findSalesOrderListByTSN(String tsn);
	
	/**
	 * @Description: 累计收益
	 * @param shopNo
	 * @return
	 * @date: 2015年3月26日 下午1:37:53
	 * @author: zyl
	 */
	Long getTotalIncome(String shopNo);
	
	/**
	 * @Description: 昨日收益
	 * @param shopNo
	 * @return
	 * @date: 2015年3月26日 下午1:37:59
	 * @author: zyl
	 */
	Long getYesterdayIncome(String shopNo);
	
	/**
	 * @Description: 订单冻结金额
	 * @param shopNo
	 * @return
	 * @date: 2015年3月26日 下午1:38:03
	 * @author: zyl
	 */
	Long getFreezeIncome(String shopNo);
	/**
	 * @Description: 查询用户冻结金额
	 * @param userId
	 * @return
	 * @date: 2015年4月2日 下午6:57:14
	 * @author: zyl
	 */
	Long getUserFreezeAmount(Long userId);
}
