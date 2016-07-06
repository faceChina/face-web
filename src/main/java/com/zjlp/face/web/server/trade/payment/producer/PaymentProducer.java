package com.zjlp.face.web.server.trade.payment.producer;

import java.util.Date;

import com.zjlp.face.account.exception.PaymentException;

public interface PaymentProducer {

	/**
	 * 累计用户消费额
	 * @Title: sumCardAmountAndInteger
	 * @Description: (交易完成，累计用户消费额)
	 * @param orderNo
	 * @throws PaymentException
	 * @return void
	 * @author phb
	 * @date 2015年3月30日 上午10:40:43
	 */
	void sumCardAmountAndInteger(String orderNo) throws PaymentException;
	
	/**
	 * 订单金额到账
	 * @Title: divideCommissionsToAccount
	 * @Description: (交易完成，分配订单的金额到商户钱包)
	 * @param orderNo
	 * @throws PaymentException
	 * @return void
	 * @author phb
	 * @date 2015年3月30日 上午10:41:09
	 */
	void divideCommissionsToAccount(String orderNo) throws PaymentException;

	/**
	 * 
	 * @Title: getUserFreezeAmount 
	 * @Description: 获取用户的冻结金额
	 * @param id
	 * @return
	 * @date 2015年5月20日 下午9:37:22  
	 * @author cbc
	 */
	Long getUserFreezeAmount(Long id);

	/**
	 * 
	 * @Title: getUsersShopsFreezeAmount 
	 * @Description: 获取用户所有店铺的冻结金额
	 * @param userId
	 * @return
	 * @date 2015年5月20日 下午9:42:38  
	 * @author cbc
	 */
	Long getUsersShopsFreezeAmount(Long userId);

	/**
	 * 
	 * @Title: getUsersPopularizeAmount 
	 * @Description: 获取用户推广的冻结金额 
	 * @param id
	 * @return
	 * @date 2015年5月20日 下午9:48:51  
	 * @author cbc
	 */
	Long getUsersPopularizeAmount(Long userId);

	/**
	 * 
	 * @Title: getShopFreezeIncome 
	 * @Description: 获取店铺的冻结金额 
	 * @param shopNo
	 * @return
	 * @date 2015年5月21日 上午9:23:28  
	 * @author cbc
	 */
	Long getShopFreezeIncome(String shopNo);

	/**
	 * 
	 * @Title: getShopIncomeByTime 
	 * @Description: 通过开始时间和结束时间查询店铺的收益
	 * @param shopNo
	 * @param startTime
	 * @param endTime
	 * @return
	 * @date 2015年5月21日 上午9:37:39  
	 * @author cbc
	 */
	Long getShopIncomeByTime(String shopNo, Date startTime, Date endTime);
	
	/**
	 * 消费送积分
	 * @Title: comsuerSendInteger
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param salesOrder
	 * @throws PaymentException
	 * @return void
	 * @author phb
	 * @date 2015年6月4日 下午3:35:13
	 */
	void comsuerSendInteger(String shopNo) throws PaymentException;
}
