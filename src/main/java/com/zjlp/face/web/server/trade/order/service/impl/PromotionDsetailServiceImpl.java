package com.zjlp.face.web.server.trade.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.order.dao.PromotionDsetailDao;
import com.zjlp.face.web.server.trade.order.domain.PromotionDsetail;
import com.zjlp.face.web.server.trade.order.service.PromotionDsetailService;
@Service
public class PromotionDsetailServiceImpl implements PromotionDsetailService {
	
	@Autowired
	private PromotionDsetailDao promotionDsetailDao;

	@Override
	public void addPromotionDsetail(PromotionDsetail promotionDsetail) {
		promotionDsetailDao.add(promotionDsetail);
	}

}
