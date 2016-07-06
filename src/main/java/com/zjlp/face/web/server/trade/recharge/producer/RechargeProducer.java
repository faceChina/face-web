package com.zjlp.face.web.server.trade.recharge.producer;

import java.util.List;

import com.zjlp.face.web.server.trade.recharge.domain.Recharge;

/**
 * 会员充值对外服务接口
 * @ClassName: RechargeService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月16日 上午10:37:46
 */
public interface RechargeProducer {
	
	/**
	 * 编辑充值订单
	 * @Title: editRecharge 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param recharge
	 * @date 2015年4月16日 上午11:19:19  
	 * @author ah
	 */
	void editRecharge(Recharge recharge);
	
	/**
	 * 根据充值编号查询充值订单
	 * @Title: getRechargeByRechargeNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param rechargeNo
	 * @return
	 * @date 2015年4月16日 上午11:22:19  
	 * @author ah
	 */
	Recharge getRechargeByRechargeNo(String rechargeNo);
	
	/**
	 * 编辑充值订单状态
	 * @Title: editRechargeStatus 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param rechargeNo
	 * @param status
	 * @date 2015年4月16日 上午11:24:49  
	 * @author ah
	 */
	void editRechargeStatus(String rechargeNo, Integer status);
	
	/**
	 * 查询充值列表
	 * @Title: findRechargeList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param recharge
	 * @return
	 * @date 2015年4月16日 上午11:29:25  
	 * @author ah
	 */
	List<Recharge> findRechargeList(Recharge recharge);
	
	/**
	 * 根据交易流水号查询充值订单
	 * @Title: getRechargeByTSN 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param transactionSerialNumber
	 * @return
	 * @date 2015年4月16日 下午2:11:00  
	 * @author ah
	 */
	Recharge getRechargeByTSN(String transactionSerialNumber);
	
}
