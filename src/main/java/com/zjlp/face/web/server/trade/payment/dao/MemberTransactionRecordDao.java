package com.zjlp.face.web.server.trade.payment.dao;

import java.util.List;

import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

/**
 * 会员卡交易记录dao
 * @ClassName: MemberTransactionRecordDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月11日 下午2:24:49
 */
public interface MemberTransactionRecordDao {

	void add(MemberTransactionRecord transactionRecord);

	MemberTransactionRecord getByTransactionSerialNumber(String transactionSerialNumber);

	List<MemberTransactionRecordDto> findTransactionRecordList(
			MemberTransactionRecordDto dto);

	Integer getTransactionCount(MemberTransactionRecordDto dto);
	
	List<MemberTransactionRecord> findMemberTransactionRecordListByTime(
			Long memberCardId, String beginTime, String endTime);
}
