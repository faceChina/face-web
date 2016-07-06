package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset.PluAgencyRateSet;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderItemDetail;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.util.PrintLog;
import com.zjlp.face.web.util.pattern.ElementSuit;

public class PluPurcharseDetail {
	
	private PrintLog log = new PrintLog("OrderInfoLog");
	//采购明细
	private List<PurcharseItemDetail> details;
	//设置
	private ElementSuit<PluAgencyRateSet> setSuit;
	//订单
	private SalesOrder salesOrder;
	//订单细项
	private List<OrderItemDetail> orderItemDetails;
	
	public PluPurcharseDetail(ElementSuit<PluAgencyRateSet> setSuit, SalesOrder salesOrder, 
			List<OrderItemDetail> orderItemDetails){
		this.setSuit = setSuit;
		this.salesOrder = salesOrder;
		this.orderItemDetails = orderItemDetails;
		details = new ArrayList<PurcharseItemDetail>();
	}
	public void generate(){
		log.writeLog("采购单生成业务开始...");
		Assert.notNull(setSuit);
		Assert.notEmpty(orderItemDetails);
		PurcharseItemDetail itemDetail;
		PluAgencyRateSet upSetData = null;
		ElementSuit<PluAgencyRateSet> currentSet = setSuit;
		ElementSuit<PluAgencyRateSet> nextSet;
		do {
			nextSet = currentSet.hasChild() ? currentSet.getChilds().get(0) : null;
			itemDetail = new PurcharseItemDetail(salesOrder, orderItemDetails, upSetData, currentSet.getData(),  
					currentSet.hasChild() ? nextSet.getData() : null, !currentSet.isRoot());
			itemDetail.generate();
			details.add(itemDetail);
			
			upSetData = currentSet.getData();
			currentSet = nextSet;
			
		} while (null != currentSet);
		log.writeLog("采购单生成完成");
	}
	
	public List<PurcharseItemDetail> getDetails() {
		return details;
	}
	
}
