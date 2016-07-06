package com.zjlp.face.web.server.trade.payment.classify;

import java.util.List;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

/**
 * 订单类型业务接口
* @ClassName: OrderClassify 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月14日 上午11:09:47 
*
 */
public interface OrderClassify {

	/**
	 * 计算订单需要支付的金额	
	 * @Title: cacluOrderPayAmount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param list
	 * @return
	 * @throws PaymentException
	 * @return Long
	 * @author phb
	 * @date 2015年3月14日 上午11:16:00
	 */
	Long cacluOrderPayAmount(List<SalesOrder> list) throws PaymentException;
	
	/**
	 * 计算订单佣金
	 * @Title: cacluOrderCommission
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param salesOrder
	 * @return
	 * @throws PaymentException
	 * @return Long
	 * @author phb
	 * @date 2015年3月14日 上午11:16:18
	 */
//	Long cacluOrderCommission(SalesOrder salesOrder) throws PaymentException;
	
	/**
	 * 处理支付商品名称
	 * @Title: dispTransactionGoodsName
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param list
	 * @param length
	 * @return
	 * @throws PaymentException
	 * @return String
	 * @author phb
	 * @date 2015年3月14日 上午11:19:59
	 */
	String dispTransactionGoodsName(List<SalesOrder> list,Integer length) throws PaymentException;
	
	/**
	 * 处理库存
	 * @Title: deductionStock
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param salesOrders
	 * @throws PaymentException
	 * @return void
	 * @author phb
	 * @date 2015年3月14日 上午11:22:01
	 */
	void deductionStock(List<SalesOrder> salesOrders) throws PaymentException;
	
}
