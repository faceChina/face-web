package com.zjlp.face.web.server.trade.order.dao;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.PromotionDsetail;

public interface PromotionDsetailDao {
	
	void add(PromotionDsetail promotionDsetail);
	
	List<PromotionDsetail> getByOrderNo(String orderNo);

}
