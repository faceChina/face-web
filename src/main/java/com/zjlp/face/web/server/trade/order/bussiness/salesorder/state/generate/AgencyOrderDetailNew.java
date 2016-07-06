package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.AgencySetNew;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.PurcharseDetailNew;
import com.zjlp.face.web.util.PrintLog;

@Deprecated
public class AgencyOrderDetailNew extends OrderGenerate {
	private PrintLog log = new PrintLog(getClass());
	private OrdinaryOrderDetail orderDetail;
	private PurcharseDetailNew purcharseDetail;
	private AgencySetNew agencySetNew;
	private String deliveryRemoteId;
	private Integer deliveryRemoteType;
	
	public AgencyOrderDetailNew(OrdinaryOrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	@Override
	public void execute() {
		Assert.notNull(agencySetNew);
//		Assert.notNull(mapRate);
		//普通形式订单生成
		orderDetail.execute();
		salesOrder = orderDetail.getSalesOrder();
		log.writeLog("普通订单生成完毕，详细订单详细：", String.valueOf(salesOrder));
		//采购单生成
		purcharseDetail = new PurcharseDetailNew(agencySetNew, salesOrder, orderDetail.getItemList());
		purcharseDetail.generate();
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
	public AgencyOrderDetailNew withAgencySet(AgencySetNew agencySetNew){
		this.agencySetNew = agencySetNew;
		return this;
	}
	public PurcharseDetailNew getPurcharseDetailNew() {
		return purcharseDetail;
	}
	public AgencyOrderDetailNew withDeliver(String deliveryRemoteId, Integer deliveryRemoteType){
		this.deliveryRemoteId = deliveryRemoteId;
		this.deliveryRemoteType = deliveryRemoteType;
		return this;
	}
	public AgencyOrderDetailNew withDeliver(String deliveryRemoteId){
		this.deliveryRemoteId = deliveryRemoteId;
		this.deliveryRemoteType = TYPE_2;
		return this;
	}
}
