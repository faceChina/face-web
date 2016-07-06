package com.zjlp.face.web.server.trade.payment.scene.fee;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
/**
 * 微信支付场景
* @ClassName: WechatScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月20日 下午2:04:41 
*
 */
public class WechatScene implements FeeScene {

	@Override
	public FeeDto calculation(Long amount) throws PaymentException {
		try {
			String wechat = PropertiesUtil.getContexrtParam("WECHAT_FEE");
			AssertUtil.notNull(wechat, "未配置WECHAT_FEE");
			//微信手续费
			Long lianLianFee = CalculateUtils.getPercentagePriceUp(amount, wechat);
			FeeDto feedto = new FeeDto();
			feedto.setPayFee(lianLianFee);
			feedto.setPlatformFee(0l);
			return feedto;
		} catch (Exception e) {
			throw new PaymentException(e);
		}
	}

}
