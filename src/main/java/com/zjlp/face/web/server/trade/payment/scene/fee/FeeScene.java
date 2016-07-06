package com.zjlp.face.web.server.trade.payment.scene.fee;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;

/**
 * 计算手续费接口
 * @ClassName: FeeScene 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2014年8月12日 上午11:38:45
 */
public interface FeeScene {

	/**
	 * 
	 * @Title: calculation 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param amount 交易金额
	 * @return
	 * @date 2014年8月12日 上午11:41:07  
	 * @author Administrator
	 */
	FeeDto calculation(Long amount) throws PaymentException;
}
