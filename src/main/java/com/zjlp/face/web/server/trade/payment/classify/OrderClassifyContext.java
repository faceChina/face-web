package com.zjlp.face.web.server.trade.payment.classify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("orderClassifyContext")
public class OrderClassifyContext{

	/**普通店销交易*/
	@Autowired
	private OrderClassify defaultOrderClassify;
	
	/**
	 * 获取订单归类对象，处理不同逻辑
	 * @Title: getOrderClassify
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param type
	 * @return
	 * @return OrderClassify
	 * @author phb
	 * @date 2015年3月14日 上午11:09:08
	 */
	public OrderClassify getOrderClassify(Integer type){
		if(type.intValue() == 1){
			return defaultOrderClassify;
		}
		return null;
	}
}
