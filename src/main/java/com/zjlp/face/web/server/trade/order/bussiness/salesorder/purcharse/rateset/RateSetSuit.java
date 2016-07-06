package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset;

import com.zjlp.face.web.util.pattern.AbstractElementSuit;
import com.zjlp.face.web.util.pattern.ElementSuit;

public class RateSetSuit extends AbstractElementSuit<PluAgencyRateSet> {

	public RateSetSuit(PluAgencyRateSet data) {
		super(data);
	}

	@Override
	protected ElementSuit<PluAgencyRateSet> cover(PluAgencyRateSet data) {
		return new RateSetSuit(data);
	}

}
