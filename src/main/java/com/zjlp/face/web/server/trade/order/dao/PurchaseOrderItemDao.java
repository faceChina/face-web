package com.zjlp.face.web.server.trade.order.dao;

import java.util.List;

import com.zjlp.face.web.component.base.BaseDao;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderItemDto;

public interface PurchaseOrderItemDao extends BaseDao<Long,PurchaseOrderItem> {

	List<PurchaseOrderItemDto> findPurchaseOrderItemList(Long purchaseOrderId);

	List<PurchaseOrderItem> selectAll();

}
