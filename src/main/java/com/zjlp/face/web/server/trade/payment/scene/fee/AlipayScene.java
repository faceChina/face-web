package com.zjlp.face.web.server.trade.payment.scene.fee;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;

public class AlipayScene implements FeeScene {

	@Override
	public FeeDto calculation(Long amount) throws PaymentException {
		try {
			String alipay = PropertiesUtil.getContexrtParam("ALIPAY_UNIFIED_FEE");
			AssertUtil.notNull(alipay, "未配置ALIPAY_UNIFIED_FEE");
			String lianlianMinFee = PropertiesUtil.getContexrtParam("LIANLIAN_MIN_FEE");//暂时借用
			AssertUtil.notNull(lianlianMinFee, "未配置LIANLIAN_MIN_FEE");
			Long llminfee = Long.valueOf(lianlianMinFee);
			AssertUtil.isTrue(llminfee.longValue() <= amount.longValue(), "交易金额不能小于最小手续费");
			//捷蓝手续费
			Long alipayFee = CalculateUtils.getFeeHalfUp(amount, alipay);
			alipayFee = alipayFee < llminfee ? llminfee : alipayFee;
			FeeDto feedto = new FeeDto();
			feedto.setPayFee(alipayFee);
			feedto.setPlatformFee(0l);
			return feedto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException(e);
		}
	}

}
