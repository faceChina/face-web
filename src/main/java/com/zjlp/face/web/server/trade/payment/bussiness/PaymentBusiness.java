package com.zjlp.face.web.server.trade.payment.bussiness;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zjlp.face.account.dto.LakalaReq;
import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.payment.domain.AlipayTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.WXNotice;
import com.zjlp.face.web.server.trade.payment.domain.vo.WechatPayVo;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;
/**
 * 商品支付接口
* @ClassName: PaymentBusiness 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年4月16日 上午11:06:28 
*
 */
public interface PaymentBusiness {

    /**
     * 钱包支付-实物订单
     * @Title: paymentConsumerByWallet 
     * @Description: (实物订单) 
     * @param userId 支付用户
     * @param vaildateCode 钱包支付密码
     * @param orderNoList  支付订单号
     * @return
     * @date 2014年8月15日 上午10:02:36  
     * @author dzq
     */
	WalletTransactionRecord paymentConsumerByWallet(Long userId,
			String vaildateCode, List<String> orderNoList) throws PayException;
	
	/**
	 * 微信支付统一下单
	 * @Title: paymentWechatOrder
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 * @param openId
	 * @param spbillCreateIp 客户端IP
	 * @param orderNoList
	 * @return
	 * @return WechatPayVo
	 * @author phb
	 * @date 2015年3月20日 下午7:46:00
	 */
	WechatPayVo paymentWechatOrder(Long userId,String openId,String spbillCreateIp,List<String> orderNoList);
	
	/**
	 * 微信支付消费（支付通知）
	 * @Title: wechatConsumer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param notice
	 * @return
	 * @return WechatTransactionRecord
	 * @author phb
	 * @date 2015年3月20日 下午7:46:35
	 */
	WechatTransactionRecord wechatConsumer(WXNotice notice);
	
	/**
	 * 会员卡支付
	 * @Title: paymentConsumerByMemberCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 * @param vaildateCode
	 * @param orderNoList
	 * @return
	 * @throws PayException
	 * @return MemberTransactionRecord
	 * @author phb
	 * @date 2015年4月15日 下午2:56:41
	 */
	MemberTransactionRecord paymentConsumerByMemberCard(Long userId,
			String vaildateCode, List<String> orderNoList)throws PayException;
	
	/**
	 * 捷蓝绑定支付生产
	 * @Title: bindPayProducer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId 用户编号
	 * @param vaildateCode 支付密码
	 * @param orderNoList 订单号集合
	 * @param bankCardId 银行卡编号
	 * @return
	 * @throws PayException
	 * @author phb
	 * @date 2015年5月28日 下午2:02:46
	 */
	Integer bindPayProducer(Long userId,String vaildateCode,List<String> orderNoList,Long bankCardId) throws PayException;
	
	/***
	 * 查询订单如果成功就消费订单
	 * @Title: queryBindPayAndConsumer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param transactionSerialNumber
	 * @param bankCard
	 * @throws PayException
	 * @return void
	 * @author phb
	 * @date 2015年5月29日 上午10:42:13
	 */
	void queryBindPayAndConsumer(String transactionSerialNumber,BankCard bankCard) throws PayException;
	
	/**
	 * 微信支付统一下单
	 * @param userId			用户Id
	 * @param openId			用户微信授权id
	 * @param spbillCreateIp	主机id
	 * @param orderNoList		下单商品
	 * @param tradeType			微信支付类型 	NATIVE:二维码支付;JSAPI:公众号支付
	 * @return
	 * @throws PaymentException
	 */
	WechatPayVo paymentWechatOrder(Long userId,String openId,String spbillCreateIp,List<String> orderNoList,String tradeType) throws PaymentException;
	
	String alipayProducer(Long userId,List<String> orderNoList) throws PayException;
	
	AlipayTransactionRecord alipayConsumer(Map<String,String> notice) throws PayException;

	Map<String, String> lakalaProducer(List<SalesOrder> list, Long userId, Long bankCardId) throws PayException;

	Map<String, String> lakalaConsumer(HttpServletRequest request, String orderNos, Long userId, Long bankCardId);
	
	void dealOrderAfterLakalaPay(Map<String,String> resultMap,Long bankCardId);
	
	BankCardVo getBankCardMsgByCardNo(String bankCard);

	Map<String, String> getLklSignCode(LakalaReq lr);

	Map<String, String> lklSign(LakalaReq lr);

	String signCheck(LakalaReq lakalaReq);
	
	/**
	 * @Description: 拉卡拉签约新增银行卡
	 * @param bankCard
	 * @param userId
	 * @param type:1,第一次签约;2,曾经签约过
	 * @date: 2015年10月6日 下午2:06:33
	 * @author: zyl
	 */
	void addLklSignBankCard(BankCard bankCard, Long userId, Integer type);

	Map<String, String> getLklPayCode(LakalaReq lr);
	
	/**
	 * @Description: 红包钱包支付
	 * @param userId
	 * @param paymentCode
	 * @param id
	 * @return
	 * @date: 2015年10月15日 下午4:49:41
	 * @author: zyl
	 */
	WalletTransactionRecord redenvelopeWalletPayConsumer(Long userId, String paymentCode, Long id);
}
