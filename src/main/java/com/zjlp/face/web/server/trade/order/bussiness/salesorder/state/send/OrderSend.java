package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.send;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.AbstractOrderState;

public class OrderSend extends AbstractOrderState {
	
	@Override
	protected void check() throws Exception {
		super.check();
		Assert.isTrue(States.WAIT_SEND.getStatus().equals(salesOrder.getStatus()), 
				"It's not a wait send order.");
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
