package com.zjlp.face.web.server.trade.payment.scene.fee;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
/**
 * 平台收取手续费+微信手续费
* @ClassName: PlatfromAndWechatScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月20日 下午2:09:28 
*
 */
public class PlatformAndWechatScene implements FeeScene {

	@Override
	public FeeDto calculation(Long amount) throws PaymentException {
		try {
			String wechat = PropertiesUtil.getContexrtParam("WECHAT_FEE");
			AssertUtil.notNull(wechat, "未配置WECHAT_FEE");
			String total = PropertiesUtil.getContexrtParam("TOTAL_FEE");
			AssertUtil.notNull(total, "未配置TOTAL_FEE");
			//总手续费
			Long totalFee = CalculateUtils.getPercentagePriceUp(amount, total);
			//微信手续费
			Long wechatFee = CalculateUtils.getPercentagePriceUp(amount, wechat);
			Long jzFee = totalFee-wechatFee;
			FeeDto feedto = new FeeDto();
			feedto.setPayFee(wechatFee);
			feedto.setPlatformFee(jzFee);
			return feedto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException(e);
		}
	}

}
