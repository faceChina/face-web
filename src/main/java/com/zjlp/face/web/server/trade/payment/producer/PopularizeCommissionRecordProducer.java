package com.zjlp.face.web.server.trade.payment.producer;

import com.zjlp.face.web.server.user.user.domain.User;

/**
 * 推广佣金待分记录对外处理接口
* @ClassName: PopularizeCommissionRecordProducer 
* @Description: TODO(这里用一句话描述这个类的作用) 
* 			1.注册的时候调用
* 			2.订单完成的时候调用
* @author phb 
* @date 2015年5月12日 下午4:42:52 
*
 */
public interface PopularizeCommissionRecordProducer {

	/**
	 * 注册时处理记录
	 * 			1.已完成订单的记录，金额到账 记录状态改为已到帐
	 * 			2.未完成订单的记录状态改为未到账，补全用户编号
	 * @Title: registeredManageRecord
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param cell
	 * @return void
	 * @author phb
	 * @date 2015年5月12日 下午4:50:05
	 */
	void registeredManageRecord(User user);
	
	/**
	 * 订单完成处理记录
	 * 			1.未激活账户 订单状态改为完成
	 * 			2.已激活账户 订单状态改为完成，金额到账 记录状态改为已到帐
	 * @Title: compileOrderManageRecord
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param order
	 * @return void
	 * @author phb
	 * @date 2015年5月12日 下午4:52:41
	 */
	void compileOrderManageRecord(String order);
}
