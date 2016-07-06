package com.zjlp.face.web.server.trade.payment.bussiness;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.WXNotice;
import com.zjlp.face.web.server.trade.payment.domain.vo.WechatPayVo;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
/**
 * 充值支付接口
* @ClassName: RechargeBusiness 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年4月15日 下午4:35:25 
*
 */
public interface RechargePayBusiness {

	/**
	 * 钱包支付-充值会员卡订单
	 * @Title: paymentConsumerByWallet
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param vaildateCode
	 * @param rechargeNo
	 * @return
	 * @throws PaymentException
	 * @return WalletTransactionRecord
	 * @author phb
	 * @date 2015年4月15日 下午4:56:04
	 */
	WalletTransactionRecord paymentConsumerByWallet(String vaildateCode, String rechargeNo) throws PaymentException;
		
	/**
	 * 微信支付统一下单
	 * @Title: paymentWechatOrder
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 * @param openId
	 * @param spbillCreateIp
	 * @param rechargeNo
	 * @return
	 * @return WechatPayVo
	 * @author phb
	 * @date 2015年4月15日 下午4:56:26
	 */
	WechatPayVo paymentWechatOrder(Long userId,String openId,String spbillCreateIp,String rechargeNo);
	
	/**
	 * 微信支付消费（支付通知）
	 * @Title: wechatConsumer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param notice
	 * @return
	 * @return WechatTransactionRecord
	 * @author phb
	 * @date 2015年4月15日 下午4:56:37
	 */
	WechatTransactionRecord wechatConsumer(WXNotice notice);
	
	/**
	 * 绑定支付 生产
	 * @Title: bindPayProducer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param vaildateCode
	 * @param rechargeNo
	 * @param bankCardId
	 * @return
	 * @return Integer
	 * @author phb
	 * @date 2015年6月1日 下午2:15:54
	 */
	Integer bindPayProducer(String vaildateCode,String rechargeNo,Long bankCardId);
	
	/**
	 * 查询订单如果成功就消费订单
	 * @Title: queryBindPayAndConsumer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param transactionSerialNumber
	 * @param bankCard
	 * @param memberCard
	 * @throws PayException
	 * @return void
	 * @author phb
	 * @date 2015年6月1日 下午2:16:44
	 */
	void queryBindPayAndConsumer(String transactionSerialNumber,BankCard bankCard, MemberCard memberCard)
			throws PayException;
	
	/**
	 * 微信支付统一下单
	 * @Title: paymentWechatOrder
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 * @param openId
	 * @param spbillCreateIp
	 * @param rechargeNo
	 * @param tradeType			微信支付类型
	 * @return
	 * @return WechatPayVo
	 * @author phb
	 * @date 2015年4月15日 下午4:56:26
	 */
	WechatPayVo paymentWechatOrder(Long userId,String openId,String spbillCreateIp,String rechargeNo,String tradeType);
}
