package com.zjlp.face.web.server.trade.payment.scene.dis;

import java.util.Date;

import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;

public interface DistributeScene {

	/**
	 * 分配金额场景接口
	 * @Title: distributeCalculation
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param salesOrder
	 * @param orderFee
	 * @param amount
	 * @throws PayException
	 * @return void
	 * @author phb
	 * @date 2015年5月13日 上午11:03:54
	 */
	void distributeCalculation(SalesOrder salesOrder,FeeDto orderFee,Long amount,Date date) throws PayException;
}
