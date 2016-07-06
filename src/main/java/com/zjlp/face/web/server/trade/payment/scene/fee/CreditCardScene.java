package com.zjlp.face.web.server.trade.payment.scene.fee;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
/**
 * 信用卡场景
* @ClassName: CreditCardScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月20日 下午2:07:23 
*
 */
public class CreditCardScene implements FeeScene {

	@Override
	public FeeDto calculation(Long amount) throws PaymentException {
		try {
			String lianlian = PropertiesUtil.getContexrtParam("LIANLIAN_CREDIT_FEE");
			String lianlianMinFee = PropertiesUtil.getContexrtParam("LIANLIAN_MIN_FEE");
			String total = PropertiesUtil.getContexrtParam("TOTAL_FEE");
			AssertUtil.notNull(lianlian, "未配置LIANLIAN_DEBIT_FEE");
			AssertUtil.notNull(lianlianMinFee,"未配置LIANLIAN_MIN_FEE");
			Long llminfee = Long.valueOf(lianlianMinFee);
			AssertUtil.isTrue(llminfee.longValue() <= amount.longValue(), "交易金额不能小于拉卡拉最小手续费");
			//拉卡拉手续费
			Long lianLianFee = CalculateUtils.getFeeHalfUp(amount, lianlian);
			lianLianFee = lianLianFee < llminfee ? llminfee : lianLianFee;
			//总手续费
			Long totalFee = CalculateUtils.getPercentagePriceUp(amount, total);
			//拉卡拉手续费
			Long jzFee = totalFee-lianLianFee;
			jzFee = jzFee < 0l ? 0l : jzFee;
			FeeDto feedto = new FeeDto();
			feedto.setPayFee(lianLianFee);
			feedto.setPlatformFee(jzFee);
			return feedto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException(e);
		}
	}

}
