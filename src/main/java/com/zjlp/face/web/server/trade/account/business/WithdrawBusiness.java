package com.zjlp.face.web.server.trade.account.business;

import com.zjlp.face.account.dto.WithdrawRecordDto;
import com.zjlp.face.account.dto.WithdrawReq;
import com.zjlp.face.account.dto.WithdrawRsp;
import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;

/**
 * 提现业务表
 * 
 * @ClassName: WithdrawBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年1月6日 上午11:41:57
 */
public interface WithdrawBusiness {

	/**
	 * 提现
	 * @Title: withdraw 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId
	 * @param price
	 * @param accountId
	 * @param userId
	 * @param paymentCode
	 * @throws AccountException
	 * @date 2015年3月17日 下午3:15:00  
	 * @author lys
	 */
	void withdraw(Long cardId, String price, Long accountId, Long userId, String paymentCode, String cellPhone) throws AccountException;
	
	/**
	 * 提现
	 * @Title: withdraw 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId
	 * @param price
	 * @param accountId
	 * @param userId
	 * @param paymentCode
	 * @throws AccountException
	 * @date 2015年3月17日 下午3:15:00  
	 * @author lys
	 */
	void withdraw(BankCard bankCard, String price, Long accountId, Long userId, String paymentCode, String cellPhone) throws AccountException;
	
	/**
	 * 验证提现参数
	 * @Title: checkWithdrawDataValid 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId
	 * @param price
	 * @param userId
	 * @param paymentCode
	 * @return
	 * @throws AccountException
	 * @date 2015年3月17日 下午2:57:19  
	 * @author lys
	 */
	boolean checkWithdrawDataValid(BankCard bankCard, String price, Long userId, 
			String paymentCode) throws AccountException;
	
	/**
	 * 获取可以提现次数
	 * 
	 * @Title: getLastWithdrawCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户id
	 * @return
	 * @date 2015年1月6日 上午11:48:40  
	 * @author Administrator
	 */
	Integer getLastWithdrawCount(Long userId) throws AccountException;
	
	/**
	 * 提现成功处理
	 * 
	 * @Title: afterSuccess 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param withdrawReq
	 * @param withdrawRsp
	 * @param account
	 * @param cardId
	 * @throws AccountException
	 * @date 2015年1月7日 上午10:06:48  
	 * @author Administrator
	 */
	void afterSuccess(WithdrawReq withdrawReq, WithdrawRsp withdrawRsp,
			Long cardId) throws AccountException;
	
    /**
     * 提现失败处理
     * 
     * @Title: afterFail 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param withdrawReq
     * @param withdrawRsp
     * @param account
     * @throws AccountException
     * @date 2015年1月7日 上午10:18:43  
     * @author Administrator
     */
    void afterFail(WithdrawReq withdrawReq, String errorMsg, Long accountId) throws AccountException;
    
    /**
     * 查找用户提现记录
     * 
     * @Title: findRecordPageForUser 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param pagination
     * @param recordDto
     * @return
     * @throws AccountException
     * @date 2015年1月21日 上午10:07:43  
     * @author lys
     */
    Pagination<WithdrawRecordDto> findRecordPageForUser(Pagination<WithdrawRecordDto> pagination, 
    		WithdrawRecordDto recordDto) throws AccountException;
    
    /**
     * 查找店铺提现记录
     * 
     * @Title: findRecordPageForShop 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param pagination
     * @param recordDto
     * @return
     * @throws AccountException
     * @date 2015年1月21日 上午10:07:52  
     * @author lys
     */
    Pagination<WithdrawRecordDto> findRecordPageForShop(Pagination<WithdrawRecordDto> pagination, 
    		WithdrawRecordDto recordDto) throws AccountException;
}
