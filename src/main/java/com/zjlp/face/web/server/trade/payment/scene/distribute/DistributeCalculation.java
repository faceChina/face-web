package com.zjlp.face.web.server.trade.payment.scene.distribute;

import org.apache.log4j.Logger;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.web.server.trade.payment.domain.dto.DistributeDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;

public class DistributeCalculation {

	private Logger _logger = Logger.getLogger(this.getClass());
	
	private DistributeScene distributeScene;
	
	@SuppressWarnings("unused")
	private DistributeCalculation(){};
	
	/**
	 * 
	 * @param type
	 * 			1.普通订单
	 * 			2.协议订单
	 * 			4.代理订单
	 */
	public DistributeCalculation(Integer type){
		try {
			//TODO 本阶段暂时只有店销类型
			if(1 == type.intValue()){
				distributeScene = new StoreSalesScene();
				_logger.info("【当前订单为店销】");
			} else {
				throw new PaymentException("参数【type】类型错误");
			}
		} catch (Exception e) {
			throw new PaymentException(e);
		}
	}
	
	public DistributeDto calculation(FeeDto feeDto,Long amount){
		try {
			return distributeScene.calculation(feeDto, amount);
		} catch (Exception e) {
			throw new PaymentException(e);
		}
	}
}
