package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderItemDto;

public interface PurchaseOrderItemMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseOrderItem record);

    int insertSelective(PurchaseOrderItem record);

    PurchaseOrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseOrderItem record);

    int updateByPrimaryKey(PurchaseOrderItem record);

	List<PurchaseOrderItemDto> selectListByPurchaseOrderId(Long purchaseOrderId);
	
	List<PurchaseOrderItem> selectAll();
}