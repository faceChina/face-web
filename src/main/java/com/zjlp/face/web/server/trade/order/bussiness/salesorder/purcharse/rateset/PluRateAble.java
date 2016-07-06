package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset;

import java.math.BigDecimal;

public interface PluRateAble<K> {
	
	/**
	 * 获取特定的佣金比率
	 * @Title: getRateBy 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param key 键值
	 * @return
	 * @date 2015年9月26日 上午9:29:20  
	 * @author lys
	 */
	BigDecimal getRateBy(K key);
}
