package com.zjlp.face.web.server.trade.recharge.dao;

import java.util.List;

import com.zjlp.face.web.server.trade.recharge.domain.Recharge;

/**
 * 充值持久层
 * @ClassName: RechargeDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月16日 上午11:02:18
 */
public interface RechargeDao {

	/**
	 * 新增充值订单
	 * @Title: add 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param recharge
	 * @date 2015年4月16日 上午11:31:01  
	 * @author ah
	 */
	void add(Recharge recharge);
	
	/**
	 * 编辑充值订单
	 * @Title: edit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param recharge
	 * @date 2015年4月16日 上午11:31:34  
	 * @author ah
	 */
	void edit(Recharge recharge);
	
	/**
	 * 删除充值订单
	 * @Title: deleteRecharge 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param rechargeNo
	 * @date 2015年4月16日 上午11:37:36  
	 * @author ah
	 */
	void deleteRecharge(String rechargeNo);
	
	/**
	 * 根据充值编号查询充值订单
	 * @Title: getRechargeByRechargeNo 
	 * @Description: (根据充值编号查询充值订单) 
	 * @param rechargeNo
	 * @return
	 * @date 2015年4月16日 上午11:33:38  
	 * @author ah
	 */
	Recharge getRechargeByRechargeNo(String rechargeNo);
	
	/**
	 * 查询充值订单列表
	 * @Title: findRechargeList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param recharge
	 * @return
	 * @date 2015年4月16日 上午11:35:49  
	 * @author ah
	 */
	List<Recharge> findRechargeList(Recharge recharge);

	/**
	 * 根据交易流水号查询充值订单
	 * @Title: getRechargeByTSN 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param transactionSerialNumber
	 * @return
	 * @date 2015年4月16日 下午2:12:54  
	 * @author ah
	 */
	Recharge getRechargeByTSN(String transactionSerialNumber);

	/**
	 * 通过会员卡ID和时间查询会员卡充值列表
	 * @Title: findRechargeListByUserAccountAndAccountType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @date 2015年4月21日 下午4:30:10  
	 * @author cbc
	 */
	List<Recharge> findRechargeListByUserAccountAndAccountType(
			String memberCardId, String beginTime, String endTime);
}
