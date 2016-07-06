package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.recive;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.AbstractOrderState;

public class OrderRecive extends AbstractOrderState {
	
	@Override
	protected void check() throws Exception {
		super.check();
		Assert.isTrue(States.WAIT_RECIVE.getStatus().equals(salesOrder.getStatus()), 
				"It's not a wait recive order.");
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
