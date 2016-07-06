package com.zjlp.face.web.server.trade.payment.scene.fee;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
/**
 * 平台收取手续费+借记卡手续费
* @ClassName: PlatformAndDebitCardScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月20日 下午2:12:55 
*
 */
public class PlatformAndDebitCardScene implements FeeScene {

	@Override
	public FeeDto calculation(Long amount) throws PaymentException {
		try {
			String lianlian = PropertiesUtil.getContexrtParam("LIANLIAN_DEBIT_FEE");
			AssertUtil.notNull(lianlian, "未配置LIANLIAN_DEBIT_FEE");
			String total = PropertiesUtil.getContexrtParam("TOTAL_FEE");
			AssertUtil.notNull(total, "未配置TOTAL_FEE");
			String lianlianMinFee = PropertiesUtil.getContexrtParam("LIANLIAN_MIN_FEE");
			AssertUtil.notNull(lianlianMinFee,"未配置LIANLIAN_MIN_FEE");
			Long llminfee = Long.valueOf(lianlianMinFee);
			AssertUtil.isTrue(llminfee.longValue() <= amount.longValue(), "交易金额不能小于连连最小手续费");
			//总手续费
			Long totalFee = CalculateUtils.getPercentagePriceUp(amount, total);
			//连连手续费
			Long lianLianFee = CalculateUtils.getFeeHalfUp(amount, lianlian);
			Long jzFee = totalFee-lianLianFee;
			jzFee = jzFee < llminfee ? 0l : jzFee;
			lianLianFee = lianLianFee < llminfee ? llminfee : lianLianFee;
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
