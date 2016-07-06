package com.zjlp.face.web.server.operation.member.producer;


public interface IntegralProducer {
	
	/**
	 * 修改用户积分
	 * @Title: editIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 管理员ID
	 * @param userId	用户ID
	 * @param integral	操作积分数
	 * @param way 操作方式
	 * 			 1.获得积分
	 * 			 2.抵扣/减积分 
	 * 			 3.积分冻结
	 * 			 4.积分解冻
	 * @param TYPE 操作类型 、
	 *  		 0 系统操作（如超时关闭订单时返回用户积分）
	 * 			 1 积分抵扣商品价格  
	 * 			 2 消费送积分 
	 * 			 3 签到送积分
	 * @date 2015年4月15日 下午5:53:41  
	 * @author dzq
	 */
	void editIntegral(Long sellerId,Long userId,Long integral,Integer way,Integer type);
	
	/**
	 * 修改用户积分
	 * @Title: editIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardId 会员卡
	 * @param integral	操作积分数
	 * @param way 操作方式
	 * 			 1.获得积分
	 * 			 2.抵扣/减积分 
	 * 			 3.积分冻结
	 * 			 4.积分解冻
	 * @param TYPE 操作类型 
	 * 			 0 系统操作 （如超时关闭订单时返回用户积分）
	 * 			 1,积分抵扣商品价格  
	 * 			 2 消费送积分 
	 * 			 3 签到送积分
	 * @date 2015年4月15日 下午5:53:41  
	 * @author dzq
	 */
	void editIntegral(Long memberCardId,Long integral,Integer way,Integer type);
	
	/**
	 * 积分抵扣时积分冻结接口
	 * @Title: frozenIntegral 
	 * @Description: (订单生成时调用) 
	 * @param sellerId 卖家ID
	 * @param userId 买家ID
	 * @param frozenIntegral 冻结积分
	 * @return
	 * @date 2015年4月17日 下午3:08:48  
	 * @author dzq
	 */
	void frozenIntegral(Long sellerId,Long userId,Long frozenIntegral);
	
	/**
	 * 积分抵扣时积分冻结接口
	 * @Title: frozenIntegral 
	 * @Description: (订单生成时调用) 
	 * @param memberCardId  会员卡ID
	 * @param frozenIntegral 冻结积分
	 * @return
	 * @date 2015年4月17日 下午3:08:48  
	 * @author dzq
	 */
	void frozenIntegral(Long memberCardId,Long frozenIntegral);
	
	/**
	 * 积分低价扣除积分接口
	 * @Title: deductIntegral 
	 * @Description: (交易完成时调用) 
	 * @param sellerId 卖家ID
	 * @param userId 卖家ID
	 * @param integral 扣除积分
	 * @date 2015年4月27日 下午5:30:06  
	 * @author cbc
	 */
	void deductIntegral(Long sellerId, Long userId, Long integral);
	
	
	/**
	 * 订单超时关闭时积分解冻接口
	 * @Title: unfrozenIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家ID
	 * @param userId 买家ID
	 * @param integral 解冻积分
	 * @date 2015年4月17日 下午3:30:11  
	 * @author dzq
	 */
	void unfrozenIntegral(Long sellerId,Long userId,Long integral);
	
	
	/**
	 * 订单超时关闭时积分解冻接口
	 * @Title: unfrozenIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo 店铺号
	 * @param userId 买家ID
	 * @param integral 解冻积分
	 * @date 2015年4月17日 下午3:30:11  
	 * @author dzq
	 */
	void unfrozenIntegral(String shopNo,Long userId,Long integral);
	
	
	/**
	 * 订单超时关闭时积分解冻接口
	 * @Title: unfrozenIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardId  会员卡ID
	 * @param integral 解冻积分
	 * @date 2015年4月17日 下午3:30:11  
	 * @author dzq
	 */
	void unfrozenIntegral(Long memberCardId,Long integral);
	
	/**
	 * 签到送积分接口
	 * @Title: unfrozenIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家ID
	 * @param userId 买家ID
	 * @param giftIntegral 赠送的积分
	 * @date 2015年4月17日 下午3:30:11  
	 * @author dzq
	 */
	void signinIntegral(Long sellerId,Long userId,Long giftIntegral);
	
	/**
	 * 签到送积分接口
	 * @Title: unfrozenIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家ID
	 * @param userId 买家ID
	 * @param giftIntegral 赠送的积分
	 * @date 2015年4月17日 下午3:30:11  
	 * @author dzq
	 */
	void signinIntegral(Long memberCardId,Long giftIntegral);
	
	
	/**
	 * 消费送积分部分操作接口
	 * @Title: unfrozenIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardId  会员卡ID
	 * @param giftIntegral 赠送的积分
	 * @date 2015年4月17日 下午3:30:11  
	 * @author dzq
	 */
	void consumerGiftIntegral(Long memberCardId,Long giftIntegral);
	
	/**
	 *  消费送积分部分操作接口
	 * @Title: unfrozenIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家ID
	 * @param userId 买家ID
	 * @param giftIntegral 赠送的积分
	 * @date 2015年4月17日 下午3:30:11  
	 * @author dzq
	 */
	void consumerGiftIntegral(Long sellerId,Long userId,Long giftIntegral);	
	
}
