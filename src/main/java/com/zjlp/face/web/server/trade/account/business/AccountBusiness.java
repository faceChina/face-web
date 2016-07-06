package com.zjlp.face.web.server.trade.account.business;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.util.page.Aide;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.account.domain.dto.AccountOperationRecordDto;

public interface AccountBusiness {

	/**
	 * 补全资料
	 * 
	 * @Title: completInformation
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId 用户id
	 * @param paymentCode 支付密码
	 * @param confirmCode 确认支付密码
	 * @param userName 用户名
	 * @param identity 身份证
	 * @return
	 * @throws AccountException
	 * @date 2015年3月17日 上午9:51:20
	 * @author lys
	 */
	boolean completInformation(Long userId, String paymentCode,
			String confirmCode, String userName, String identity)
			throws AccountException;

	/**
	 * 根据主键查询钱包记录
	 * 
	 * @Title: getAccountById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id 主键
	 * @return
	 * @throws AccountException
	 * @date 2015年3月17日 下午2:10:21
	 * @author lys
	 */
	Account getAccountById(Long id) throws AccountException;

	/**
	 * 根据用户id获取账户信息
	 * 
	 * @Title: getAccountByUserId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId 用户id
	 * @return
	 * @throws AccountException
	 * @date 2015年3月17日 上午11:30:01
	 * @author lys
	 */
	Account getAccountByUserId(Long userId) throws AccountException;

	/**
	 * 根据店铺编号获取账户信息
	 * 
	 * @Title: getAccountByShopNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo 店铺编号
	 * @return
	 * @throws AccountException
	 * @date 2015年3月18日 上午9:29:38
	 * @author lys
	 */
	Account getAccountByShopNo(String shopNo) throws AccountException;

	/**
	 * 查询用户钱包余额
	 * 
	 * @Title: getWithdrawAmount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId 用户id
	 * @return
	 * @throws AccountException
	 * @date 2015年3月18日 上午9:30:45
	 * @author lys
	 */
	Long getWithdrawAmount(Long userId) throws AccountException;
	
	/**
	 * 查询店铺钱包余额
	 * @Title: getWithdrawAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @throws AccountException
	 * @date 2015年3月25日 下午10:04:00  
	 * @author lys
	 */
	Long getWithdrawAmount(String shopNo) throws AccountException;

	/**
	 * 查询用户收支明细记录列表
	 * 
	 * @Title: findOperationPage
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param operationRecordDto 查询条件
	 * @param pagination 分页信息
	 * @return
	 * @throws AccountException
	 * @date 2015年3月18日 上午9:38:28
	 * @author lys
	 */
	Pagination<AccountOperationRecordDto> findUserOperationPage(
			AccountOperationRecordDto operationRecordDto,
			Pagination<AccountOperationRecordDto> pagination, Aide aide)
			throws AccountException;
	
	/**
	 * 查询超管收支明细<br>
	 * 角色：用户<br>
	 * aide 接受7日 1月 1年 查询
	 * 
	 * 
	 * @Title: findUserOperationPageByType 
	 * @Description: 查询超管收支明细
	 * @param userId 用户id String类型
	 * @param pagination
	 * @param aide
	 * @param type 0为全部 1为收入， 2为支出， 3为提现
	 * @return
	 * @throws AccountException
	 * @date 2015年8月3日 下午2:01:00  
	 * @author cbc
	 */
	Pagination<AccountOperationRecordDto> findUserOperationPageByType(
			String userId,
			Pagination<AccountOperationRecordDto> pagination, Aide aide, Integer type)
			throws AccountException;

	/**
	 * 查询店铺的收支明细列表
	 * 
	 * @Title: findShopOperationPage
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param operationRecordDto 查询条件
	 * @param pagination 分页信息
	 * @param aide 辅助信息
	 * @return
	 * @throws AccountException
	 * @date 2015年3月19日 下午4:22:46
	 * @author lys
	 */
	Pagination<AccountOperationRecordDto> findShopOperationPage(
			AccountOperationRecordDto operationRecordDto,
			Pagination<AccountOperationRecordDto> pagination, Aide aide)
			throws AccountException;

	/**
	 * 通过主键查询资金操作记录
	 * 
	 * @Title: getOperationRecordById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id 主键
	 * @return
	 * @throws AccountException
	 * @date 2015年3月23日 下午3:13:43
	 * @author lys
	 */
	AccountOperationRecordDto getOperationRecordById(Long id, Long userId)
			throws AccountException;

	/**
	 * 查询可体现的次数
	 * @Title: getWithdrawCount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId 用户id
	 * @return
	 * @throws AccountException
	 * @date 2015年3月24日 上午10:48:47
	 * @author lys
	 */
	Integer getWithdrawCount(Long userId) throws AccountException;
	
	/**
	 * 是否存在支付密码
	 * @Title: existPaymentCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户id
	 * @return
	 * @throws AccountException
	 * @date 2015年3月25日 下午2:18:03  
	 * @author lys
	 */
	boolean existPaymentCode(Long userId) throws AccountException;

	/**
	 * 支付密码验证
	 * @Title: checkPaymentCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param paymentCode
	 * @return
	 * @throws AccountException
	 * @date 2015年3月25日 下午2:18:30  
	 * @author lys
	 */
	boolean checkPaymentCode(Long userId, String paymentCode) throws AccountException;

	/**
	 * 
	 * @Title: getUserFreezeAmount 
	 * @Description: 获取用户的冻结金额
	 * @param userId
	 * @return
	 * @date 2015年5月20日 下午9:15:58  
	 * @author cbc
	 */
	@Deprecated
	Long getUserFreezeAmount(Long userId);

	/**
	 * 
	 * @Title: getShopFreezeIncome 
	 * @Description: 获取店铺的冻结金额
	 * @param shopNo
	 * @return
	 * @date 2015年5月21日 上午9:22:14  
	 * @author cbc
	 */
	Long getShopFreezeIncome(String shopNo);

	/**
	 * 
	 * @Title: getShopYesterdayIncome 
	 * @Description: 获取店铺的昨天的收益
	 * @param shopNo
	 * @return
	 * @date 2015年5月21日 上午9:31:54  
	 * @author cbc
	 */
	Long getShopYesterdayIncome(String shopNo);

	/**
	 * 
	 * @Title: getShopTotalIncome 
	 * @Description: 获取店铺的累计收益
	 * @param shopNo
	 * @return
	 * @date 2015年5月21日 上午9:32:15  
	 * @author cbc
	 */
	Long getShopTotalIncome(String shopNo);
	
	/**
	 * 
	 * @Title: getFreezeAmount 
	 * @Description: 获取用户的冻结佣金(总店未付款订单和分店佣金之和)
	 * @param userId
	 * @return
	 * @throws AccountException
	 * @date 2015年8月4日 上午9:57:04  
	 * @author cbc
	 */
	Long getFreezeAmount(Long userId) throws AccountException;

}
