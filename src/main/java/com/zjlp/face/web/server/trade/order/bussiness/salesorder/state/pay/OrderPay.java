package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.pay;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.AbstractOrderState;

public class OrderPay extends AbstractOrderState {
	
	@Override
	protected void check() throws Exception {
		super.check();
		Assert.isTrue(States.WAIT_PAY.getStatus().equals(salesOrder.getStatus()), 
				"It's not a wait pay order.");
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
