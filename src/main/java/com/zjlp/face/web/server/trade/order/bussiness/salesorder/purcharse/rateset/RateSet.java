package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset;

import java.math.BigDecimal;

public interface RateSet<T> {
	
	/**
	 * 获取设置的规则
	 * @Title: getRule 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月26日 上午9:51:54  
	 * @author lys
	 */
	T getRule();

	/**
	 * 获取佣金比率
	 * @Title: getRate 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月26日 上午9:29:00  
	 * @author lys
	 */
	BigDecimal getRate();
	
}
