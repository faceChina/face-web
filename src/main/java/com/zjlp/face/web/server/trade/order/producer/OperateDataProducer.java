package com.zjlp.face.web.server.trade.order.producer;

import com.zjlp.face.web.exception.ext.OperateDataException;
import com.zjlp.face.web.server.user.user.domain.User;

public interface OperateDataProducer {

	/**
	 * 获取冻结金额
	 * <p>
	 * 1.如果用户有官网<下载过app>
	 *   （1）店铺收入--无代理订单和代理订单扣除代理费用
	 * 2.代理收入
	 * @Title: getFreezeAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param user 用户
	 * @return
	 * @date 2015年9月4日 下午4:47:47  
	 * @author lys
	 */
	Long getFreezeAmount(User user) throws OperateDataException;
	
	/**
	 * 店铺销售所得金额
	 * 
	 * <p>
	 * 1.非代理订单的全部金额
	 * 
	 * 2.代理订单扣除代理费用的金额
	 * 
	 * @Title: caluFreezeAmountByShop 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo 店铺编号
	 * @return
	 * @throws OperateDataException
	 * @date 2015年9月4日 下午4:53:37  
	 * @author lys
	 */
	Long caluFreezeAmountByShop(String shopNo) throws OperateDataException;
	
}
