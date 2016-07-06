package com.zjlp.face.web.server.trade.order.domain.dto;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;

public class PurchaseOrderDto extends PurchaseOrder {

	private static final long serialVersionUID = 8855812036761867220L;

	public static Long getTotalPromoteSpend(List<? extends PurchaseOrder> list) {
		Long promoteSpend = 0L;
	    if (null == list || list.isEmpty()) {
	    	return promoteSpend;
	    }
		for (PurchaseOrder purchaseOrder : list) {
			if (null == purchaseOrder
					|| null == purchaseOrder.getCooperationWay()
					|| 2 != purchaseOrder.getCooperationWay()
					|| null == purchaseOrder.getTotalProfitPrice()) {
				continue;
			}
			promoteSpend += purchaseOrder.getTotalProfitPrice();
		}
		return promoteSpend;
	}
}
