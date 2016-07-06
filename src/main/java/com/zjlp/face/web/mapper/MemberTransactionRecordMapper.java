package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

import org.apache.ibatis.annotations.Param;


public interface MemberTransactionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberTransactionRecord record);

    int insertSelective(MemberTransactionRecord record);

    MemberTransactionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberTransactionRecord record);

    int updateByPrimaryKey(MemberTransactionRecord record);

    MemberTransactionRecord selectByTSN(String transactionSerialNumber);
    
	List<MemberTransactionRecord> findMemberTransactionRecordListByTime(
			@Param("memberCardId")Long memberCardId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
    
	List<MemberTransactionRecordDto> selectTransactionRecordList(
			MemberTransactionRecordDto dto);

	Integer selectTransactionCount(MemberTransactionRecordDto dto);
}