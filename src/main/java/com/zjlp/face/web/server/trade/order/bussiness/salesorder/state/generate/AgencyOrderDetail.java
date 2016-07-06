package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.PluPurcharseDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset.PluAgencyRateSet;
import com.zjlp.face.web.util.PrintLog;
import com.zjlp.face.web.util.pattern.ElementSuit;

public class AgencyOrderDetail extends OrderGenerate {
	
	private PrintLog log = new PrintLog(getClass());
	private OrdinaryOrderDetail orderDetail;
	private PluPurcharseDetail pluPurcharseDetail;
	private ElementSuit<PluAgencyRateSet> setSuit;
	private String deliveryRemoteId;
	private Integer deliveryRemoteType;
	
	public AgencyOrderDetail(OrdinaryOrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	@Override
	public void execute() {
		Assert.notNull(setSuit);
		//普通形式订单生成
		orderDetail.execute();
		salesOrder = orderDetail.getSalesOrder();
		log.writeLog("普通订单生成完毕，详细订单详细：", String.valueOf(salesOrder));
		//采购单生成
		pluPurcharseDetail = new PluPurcharseDetail(setSuit, salesOrder, orderDetail.getItemDetailList());
		pluPurcharseDetail.generate();
		log.writeLog("采购单生成完毕");
		//订单类型
		salesOrder.setOrderCategory(ORDER_TYPE_AGENCY);
		//收货地址重新定义
		if (null != deliveryRemoteId) {
			salesOrder.setDeliveryRemoteId(deliveryRemoteId);
			salesOrder.setDeliveryRemoteType(deliveryRemoteType);
			log.writeLog("重置收货地址完成");
		}
	}
	public AgencyOrderDetail withAgencySet(ElementSuit<PluAgencyRateSet> setSuit){
		this.setSuit = setSuit;
		return this;
	}
	public PluPurcharseDetail getPurcharseDetail() {
		return pluPurcharseDetail;
	}
	public AgencyOrderDetail withDeliver(String deliveryRemoteId, Integer deliveryRemoteType){
		this.deliveryRemoteId = deliveryRemoteId;
		this.deliveryRemoteType = deliveryRemoteType;
		return this;
	}
	public AgencyOrderDetail withDeliver(String deliveryRemoteId){
		this.deliveryRemoteId = deliveryRemoteId;
		this.deliveryRemoteType = TYPE_2;
		return this;
	}

}
