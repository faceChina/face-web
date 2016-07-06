package com.zjlp.face.web.server.trade.recharge.service;

import java.util.List;

import com.zjlp.face.web.server.trade.recharge.domain.Recharge;

/**
 * 会员充值基础服务接口
 * @ClassName: RechargeService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月16日 上午10:37:46
 */
public interface RechargeService {
	
	/**
	 * 新增充值订单
	 * @Title: addRecharge 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param recharge
	 * @date 2015年4月16日 上午11:18:57  
	 * @author ah
	 */
	void addRecharge(Recharge recharge);
	
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
	 * 
	 * @Title: findRechargeListByUserAccountAndAccountType 
	 * @Description: 通过会员卡ID查询会员卡的充值记录
	 * @param valueOf
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @date 2015年4月21日 下午4:28:51  
	 * @author cbc
	 */
	List<Recharge> findRechargeListByUserAccountAndAccountType(String memberCardId,
			String beginTime, String endTime);
	
}
