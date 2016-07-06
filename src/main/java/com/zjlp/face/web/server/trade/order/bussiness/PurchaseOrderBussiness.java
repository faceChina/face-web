package com.zjlp.face.web.server.trade.order.bussiness;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

public interface PurchaseOrderBussiness {
	
	/**
	 * 订单生成时生成推广采购单
	 * @Title: addPopularizePurchaseOrder 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param buyShopNo
	 * @param salesOrder
	 * @param orderItemList
	 * @date 2015年5月13日 下午3:07:23  
	 * @author dzq
	 */
	public void addPopularizePurchaseOrder(String buyShopNo,Integer type,Integer commissionRate,SalesOrder salesOrder,List<OrderItem> orderItemList);

	/**
	 * 
	 * @Title: getOrderPayCommission 
	 * @Description: 获取单笔订单的支付佣金 
	 * @param orderNo 
	 * @param supplierNo 供货商(如果以总店角色查询佣金支出时，传总店店铺号，以分店角色查询佣金支出时，传分店手机号)
	 * @param supplierType 供货商类型（1.供货商，2.分销商）
	 * @return
	 * @date 2015年8月18日 上午10:35:42  
	 * @author cbc
	 */
	public Long getOrderPayCommission(String orderNo, String supplierNo, Integer supplierType);

}
