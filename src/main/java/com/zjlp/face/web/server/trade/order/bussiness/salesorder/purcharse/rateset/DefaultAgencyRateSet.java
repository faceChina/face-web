package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset;

import java.math.BigDecimal;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.util.PrintLog;

public class DefaultAgencyRateSet implements RateSet<Subbranch> {
	
	protected PrintLog log = new PrintLog("OrderInfoLog");

	//绑定规则
	protected Subbranch subbranch;
	
	public DefaultAgencyRateSet(Subbranch subbranch) {
		Assert.notNull(subbranch, "subbranch can't be null.");
		Assert.notNull(subbranch.getProfit(), "subbranch.profit can't be null.");
		this.subbranch = subbranch;
	}

	@Override
	public BigDecimal getRate() {
		return BigDecimal.valueOf(subbranch.getProfit());
	}

	@Override
	public Subbranch getRule() {
		return subbranch;
	}

}
