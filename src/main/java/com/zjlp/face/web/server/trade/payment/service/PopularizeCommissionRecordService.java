package com.zjlp.face.web.server.trade.payment.service;

import java.util.List;

import com.zjlp.face.web.server.trade.payment.domain.PopularizeCommissionRecord;

public interface PopularizeCommissionRecordService {

	/**
	 * 添加推广待分佣金记录
	 * @Title: addPopularizeCommissionRecord
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param popularizeCommissionRecord
	 * @return void
	 * @author phb
	 * @date 2015年5月12日 下午4:20:27
	 */
	void addRecord(PopularizeCommissionRecord popularizeCommissionRecord);
	
	/**
	 * 修改记录订单状态为完成
	 * @Title: editRecordOrderComplete
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @return void
	 * @author phb
	 * @date 2015年5月12日 下午4:23:47
	 */
	void editRecordOrderComplete(Long id,Long userId);
	
	/**
	 * 修改记录状态为未到账
	 * @Title: editRecordStatusNoArrival
	 * @Description: (当用户注册账户 但是订单还未完成的时候)
	 * @param id
	 * @return void
	 * @author phb
	 * @date 2015年5月12日 下午4:25:31
	 */
	void editRecordStatusNoArrival(Long id,Long userId);
	
	/**
	 * 修改记录状态为已到帐
	 * @Title: editRecordStatusArrival
	 * @Description: (当订单完成且有账户 或者 注册账户且订单完成时)
	 * @param id
	 * @return void
	 * @author phb
	 * @date 2015年5月12日 下午4:26:44
	 */
	void editRecordStatusArrival(Long id,Long userId);
	
	/**
	 * 根据订单号查询记录
	 * @Title: getRecordByOrderNo
	 * @Description: (每个订单只可能呗一个人推广，所以推广待分佣金记录只会有一份)
	 * @param orderNo
	 * @return
	 * @return PopularizeCommissionRecord
	 * @author phb
	 * @date 2015年5月12日 下午4:28:02
	 */
	List<PopularizeCommissionRecord> getRecordByOrderNo(String orderNo);
	
	/**
	 * 根据手机号查询未激活账户的记录
	 * @Title: findRecordByPopuCell
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param popuCell
	 * @return
	 * @return List<PopularizeCommissionRecord>
	 * @author phb
	 * @date 2015年5月12日 下午5:25:17
	 */
	List<PopularizeCommissionRecord> findRecordByPopuCell(String popuCell);
	
	/**
	 * 锁定当前记录
	 * @Title: getByIdForLock
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @return
	 * @return PopularizeCommissionRecord
	 * @author phb
	 * @date 2015年5月12日 下午5:38:14
	 */
	PopularizeCommissionRecord getByIdForLock(Long id);

	/**
	 * 
	 * @Title: getUsersPopularizeAmount 
	 * @Description: 获取用户的推广冻结金额 
	 * @param accountId
	 * @return
	 * @date 2015年5月20日 下午9:51:25  
	 * @author cbc
	 */
	Long getUsersPopularizeAmount(Long userId);
}
