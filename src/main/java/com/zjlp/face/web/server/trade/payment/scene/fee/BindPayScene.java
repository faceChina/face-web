package com.zjlp.face.web.server.trade.payment.scene.fee;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;

public class BindPayScene implements FeeScene {

	@Override
	public FeeDto calculation(Long amount) throws PaymentException {
		try {
			String lianlian = PropertiesUtil.getContexrtParam("BIND_PAY_FEE");
			AssertUtil.notNull(lianlian, "未配置BIND_PAY_FEE");
			String lianlianMinFee = PropertiesUtil.getContexrtParam("LIANLIAN_MIN_FEE");//暂时借用
			AssertUtil.notNull(lianlianMinFee, "未配置LIANLIAN_MIN_FEE");
			Long llminfee = Long.valueOf(lianlianMinFee);
			AssertUtil.isTrue(llminfee.longValue() <= amount.longValue(), "交易金额不能小于最小手续费");
			//捷蓝手续费
			Long lianLianFee = CalculateUtils.getFeeHalfUp(amount, lianlian);
			lianLianFee = lianLianFee < llminfee ? llminfee : lianLianFee;
			FeeDto feedto = new FeeDto();
			feedto.setPayFee(lianLianFee);
			feedto.setPlatformFee(0l);
			return feedto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException(e);
		}
	}

}
