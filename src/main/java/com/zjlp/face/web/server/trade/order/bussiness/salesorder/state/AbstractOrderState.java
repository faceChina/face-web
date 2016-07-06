package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

public abstract class AbstractOrderState implements OrderState {

	protected SalesOrder salesOrder;

	protected void check() throws Exception {
		Assert.notNull(salesOrder, "salesOrder can't be null.");
	}
	
	public AbstractOrderState withSalesOrder(SalesOrder salesOrder){
		this.salesOrder = salesOrder;
		return this;
	}

}
