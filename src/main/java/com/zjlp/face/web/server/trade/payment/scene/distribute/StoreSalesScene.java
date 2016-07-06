package com.zjlp.face.web.server.trade.payment.scene.distribute;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.trade.payment.domain.dto.DistributeDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
/**
 * 店销金额分配
* @ClassName: StoreSalesScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月20日 下午2:56:21 
*
 */
public class StoreSalesScene implements DistributeScene {

	@Override
	public DistributeDto calculation(FeeDto feeDto, Long amount)
			throws PaymentException {
		Long sellerMoney = amount - feeDto.getPayFee() - feeDto.getPlatformFee();
		AssertUtil.isTrue(sellerMoney >= 0, "交易金额不足以支付手续费");
		DistributeDto dto = new DistributeDto();
		dto.setSellerMoney(sellerMoney);
		dto.setPayFeeMoney(feeDto.getPayFee());
		dto.setPlatformFeeMoney(feeDto.getPlatformFee());
		return dto;
	}

}
