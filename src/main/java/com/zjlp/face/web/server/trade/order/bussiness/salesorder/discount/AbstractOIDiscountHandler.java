package com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount;

import org.springframework.util.Assert;

import com.zjlp.face.web.util.PrintLog;

/**
 * 基于订单细项的优惠活动处理基类
 * @ClassName: AbstractOIDiscountHandler 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月23日 上午9:53:06
 */
public abstract class AbstractOIDiscountHandler implements DiscountHandler {

	protected PrintLog log = new PrintLog("OrderInfoLog");
	
	@Override
	public Object handle(Object param) {
		
		//验证参数
		boolean cando = this.check(param);
		
		//判断是否可以执行
		if (!cando) {
			return param;
		}
		
		//执行处理
		Object obj = this.excute(param);
		
		return obj;
	}
	
	protected boolean check(Object param) {
		//暂时做非空处理
		Assert.notNull(param, "param can't be null.");
		return true;
	}
	
	protected abstract Object excute(Object param);
	
}
