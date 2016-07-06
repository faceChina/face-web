package com.zjlp.face.web.server.trade.order.producer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.OperateDataException;
import com.zjlp.face.web.server.trade.order.producer.OperateDataProducer;
import com.zjlp.face.web.server.trade.order.service.OperateDataService;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.User;

@Service
public class OperateDataProducerImpl implements OperateDataProducer {
	
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private OperateDataService operateDataService;

	@Override
	public Long getFreezeAmount(User user) {
		AssertUtil.notNull(user, "param[user] can't be null.");
		Long userId = user.getId();
		AssertUtil.notNull(userId, "param[user.id] can't be null.");
		List<Shop> list = shopService.findShopListByUserId(userId);
		//如果没有官网
		Long subbranchFreezeAmount = salesOrderService.getSubbranchFreezeAmount(user.getCell());
		if (CollectionUtils.isEmpty(list)) {
			return subbranchFreezeAmount;
		}
		Shop shop = list.get(0);
		Long shopFreezeAmount = this.caluFreezeAmountByShop(shop.getNo());
		return shopFreezeAmount + subbranchFreezeAmount;
	}

	@Override
	public Long caluFreezeAmountByShop(String shopNo)
			throws OperateDataException {
		AssertUtil.hasLength(shopNo, "param[shopNo] can't be null.");
		//非代理金额
		Long notAencyAmount = salesOrderService.getFreezeSalesOrderIncome(shopNo);
		//代理订单扣除代理费用
		Long aencyAmount = operateDataService.getAencyFreezeAmount(shopNo);
		return notAencyAmount + aencyAmount;
	}
	
}
