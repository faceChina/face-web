package com.zjlp.face.web.server.trade.payment.classify;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;

/**
 * 店销订单支付归类
 * @ClassName: OrderStoreSalesClassify
 * @Description: (这里用一句话描述这个类的作用)
 * @author phb
 * @date 2015年3月14日 上午11:02:36
 *
 */
@Component("defaultOrderClassify")
public class DefaultOrderClassify implements OrderClassify {
	
	@Autowired
	private SalesOrderProducer salesOrderProducer;
	
	@Autowired
	private GoodProducer goodProducer;

	@Override
	public Long cacluOrderPayAmount(List<SalesOrder> list)
			throws PaymentException {
		Assert.notEmpty(list, "订单集合参数为空");
		try {
			Long totalPrice = 0l;
			for (SalesOrder so : list) {
				AssertUtil.isTrue(so.getTotalPrice() >= 0, "订单金额异常");
				totalPrice = CalculateUtils.getSum(totalPrice, so.getTotalPrice());//应付金额
			}
			AssertUtil.isTrue(totalPrice >= 0, "交易金额异常");
			return totalPrice;
		} catch (Exception e) {
			throw new PaymentException("计算订单交易金额发生异常", e);
		}
	}

	@Override
	public String dispTransactionGoodsName(List<SalesOrder> list,Integer length)
			throws PaymentException {
		Assert.notEmpty(list, "订单集合参数为空");
		Assert.notNull(length, "截取长度参数为空");
		try {
        	Integer subLength = 0;
        	
			List<OrderItem> orderItem = salesOrderProducer.getOrderItemListByOrderNo(list.get(0).getOrderNo());
			AssertUtil.notEmpty(orderItem, "订单细项为空");
			String goodName = orderItem.get(0).getGoodName();
			AssertUtil.hasLength(goodName, "商品名称为空");
			subLength = goodName.length() < length ? goodName.length() : length;
			goodName = new StringBuffer(goodName.substring(0, subLength)).append("...").toString();
			return goodName;
		} catch (Exception e) {
			throw new PaymentException("处理支付商品名称异常", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PaymentException"})
	public void deductionStock(List<SalesOrder> salesOrders)
			throws PaymentException {
		for (SalesOrder so : salesOrders) {
			List<OrderItem> orderItems = salesOrderProducer.getOrderItemListByOrderNo(so.getOrderNo());
			for (OrderItem oi : orderItems) {
				goodProducer.editGoodSkuStock(oi.getGoodSkuId(), oi.getQuantity());
			}
		}
	}

	
}
