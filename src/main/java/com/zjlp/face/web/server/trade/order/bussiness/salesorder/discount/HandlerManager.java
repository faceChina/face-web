package com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.zjlp.face.web.util.PrintLog;

/**
 * 优惠活动处理管理类
 * @ClassName: HandlerManager 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月23日 上午9:56:34
 */
public class HandlerManager implements DiscountHandler {
	
	private PrintLog log = new PrintLog("OrderInfoLog");

	private final List<DiscountHandler> handlerList = new ArrayList<DiscountHandler>();
	
	@Override
	public Object handle(Object param) {
		log.writeLog("开始处理营销活动！");
		for (DiscountHandler discountHandler : handlerList) {
			log.writeLog("开始处理 ", discountHandler);
			param = discountHandler.handle(param);
		}
		log.writeLog("处理营销活动完毕！");
		return param;
	}
	
	public void append(DiscountHandler handler){
		Assert.notNull(handler, "Can't append an empty handler.");
		handlerList.add(handler);
	}
	
}
