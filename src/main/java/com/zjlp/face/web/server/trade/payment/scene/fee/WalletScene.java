package com.zjlp.face.web.server.trade.payment.scene.fee;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
/**
 * 钱包付款场景
* @ClassName: WalletScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月20日 下午2:03:51 
*
 */
public class WalletScene implements FeeScene {

	@Override
	public FeeDto calculation(Long amount) throws PaymentException {
		FeeDto feedto = new FeeDto();
		feedto.setPlatformFee(0l);
		feedto.setPayFee(0l);
		return feedto;
	}

}
