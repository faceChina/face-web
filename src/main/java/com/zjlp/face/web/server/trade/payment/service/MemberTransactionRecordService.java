package com.zjlp.face.web.server.trade.payment.service;

import java.util.List;

import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

public interface MemberTransactionRecordService {

	/**
	 * 添加会员卡资金交易记录
	 * @Title: addMemberTransactionRecord 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param transactionRecord 会员卡资金交易记录
	 * @return
	 * @throws MemberException
	 * @date 2015年4月11日 上午11:31:18  
	 * @author lys
	 */
	void addMemberTransactionRecord(MemberTransactionRecord transactionRecord) throws MemberException;
	
	/**
	 * 根据流水号查询记录
	 * @Title: getTransactionRecordBySerialNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param serialNo
	 * @return
	 * @date 2015年4月11日 下午2:19:16  
	 * @author lys
	 */
	MemberTransactionRecord getTransactionRecordBySerialNo(String serialNo);
	
	/**
	 * 查找会员卡资金流转记录
	 * @Title: findTransactionRecordList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param transactionRecordDto 查询条件（可能包含分页信息）
	 * @return
	 * @throws MemberException
	 * @date 2015年4月11日 上午11:34:00  
	 * @author lys
	 */
	List<MemberTransactionRecordDto> findTransactionRecordList(MemberTransactionRecordDto transactionRecordDto) throws MemberException;

	Integer getTransactionRecordCount(MemberTransactionRecordDto dto) throws MemberException;
}
