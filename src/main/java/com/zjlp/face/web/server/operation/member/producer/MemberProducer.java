package com.zjlp.face.web.server.operation.member.producer;

import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;


/**
 * 会员卡对外接口支撑
 * @ClassName: MemberProducer 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月11日 下午2:44:00
 */
public interface MemberProducer {
	
	
	/**
	 * 获得会员折扣价格
	 * @Description: 计算会员价
	 * @param userId 买家ID
	 * @param sellerId 卖家ID
	 * @param price 价格
	 * @param isPreferential
	 * @return
	 * @date: 2015年3月18日 上午10:02:18
	 * @author: dzq
	 */
	Long getDiscountPrice(Long userId, String shopNo, Long price,
			Integer isPreferential) throws MemberException;
	
	/**
	 * 初始化会员设置
	 * @Title: initMemberEnactment 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId
	 * @return
	 * @throws MemberException
	 * @date 2015年4月18日 下午2:01:42  
	 * @author lys
	 */
	Long initMemberEnactment(Long sellerId) throws MemberException;

	/**
	 * 会员金额累计
	 * @Title: sumMemberAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 会员
	 * @param amount 充值金额
	 * @param amount 赠送金额
	 * @return
	 * @throws MemberException
	 * @date 2015年4月15日 下午8:16:10  
	 * @author lys
	 * @param long1 
	 */
	boolean sumMemberAmount(Long cardId, Long amount, Long giftAmount) throws MemberException;
	
	/**
	 * 会员金额扣除
	 * @Title: miniMemberAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 会员
	 * @param amount 金额
	 * @return
	 * @throws MemberException
	 * @date 2015年4月15日 下午8:16:13  
	 * @author lys
	 */
	boolean miniMemberAmount(Long cardId, Long amount) throws MemberException;
	/**
	 * 根据用户和卖家查询会员卡
	 * @Title: getMemberCardByUserIdAndSellerId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 * @param sellerId
	 * @return
	 * @return MemberCard
	 * @author phb
	 * @date 2015年4月15日 下午5:55:47
	 * @see MemberProducer.getMemberCardByUserIdAndShopNo
	 */
	@Deprecated
	MemberCard getMemberCardByUserIdAndSellerId(Long userId,Long sellerId);
	
	/**
	 * 根据用户和卖家查询会员卡
	 * @Title: getMemberCardByUserIdAndSellerId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 * @param shopNo
	 * @return
	 * @return MemberCard
	 * @author dzq
	 * @date 2015年4月15日 下午5:55:47
	 */
	MemberCard getMemberCardByUserIdAndShopNo(Long userId, String shopNo);
	
	/***
	 * 根据ID查询会员卡
	 * @Title: getMemberCardById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @return
	 * @return MemberCard
	 * @author phb
	 * @date 2015年4月16日 下午2:29:38
	 */
	MemberCard getMemberCardById(Long id);
	
	/**
	 * 累计消费额
	 * @Title: sumConsumAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户
	 * @param shopNo 店铺
	 * @param amount 金额
	 * @return
	 * @throws MemberException
	 * @date 2015年4月21日 下午5:45:58  
	 * @author lys
	 */
	boolean sumConsumAmount(Long userId, String shopNo, Long amount) throws MemberException;

	/**
	 * 根据管理员userId获取会员设置
	 * @param id
	 * @return
	 */
	MemberEnactment getEnactmentBySettleId(Long id);
}
