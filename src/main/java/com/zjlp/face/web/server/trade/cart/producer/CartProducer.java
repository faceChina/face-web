package com.zjlp.face.web.server.trade.cart.producer;

import com.zjlp.face.web.exception.ext.CartException;


public interface CartProducer {
	
	/**
	 * @Description: 查询用户购物车中商品数
	 * @param userId
	 * @return
	 * @date: 2015年3月24日 下午6:41:11
	 * @author: zyl
	 */
	Integer getCount(Long userId);

	/**
	 * 檫除代理信息
	 * 
	 * <p>
	 * 
	 * 将代理者的购物车记录
	 * 
	 * @Title: clearSubbranchInfo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param subbranchId
	 * @date 2015年7月13日 下午2:28:41  
	 * @author lys
	 */
	void clearSubbranchInfo(Long subbranchId) throws CartException;

}
