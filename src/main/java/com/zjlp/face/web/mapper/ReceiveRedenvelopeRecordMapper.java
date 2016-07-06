package com.zjlp.face.web.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.vo.ReceivePerson;

public interface ReceiveRedenvelopeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReceiveRedenvelopeRecord record);

    int insertSelective(ReceiveRedenvelopeRecord record);

    ReceiveRedenvelopeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReceiveRedenvelopeRecord record);

    int updateByPrimaryKey(ReceiveRedenvelopeRecord record);

	void insertReceive(@Param("recordList")List<ReceiveRedenvelopeRecord> recordList);

	Integer countReceivePerson(@Param("sendRecordId")Long sendRecordId, @Param("clickTime")Date clickTime);

	List<ReceivePerson> findReceivePerson(Map<String, Object> map);

	ReceiveRedenvelopeRecord getReceiveRecordByReceiveUserIdAndSendId(
			@Param("redenvelopeId")Long redenvelopeId, @Param("receiveUserId")Long receiveUserId);

	Long sumHasReceiveMoney(Long redenvelopeId);

	Integer countNotReceive(Long redenvelopeId);

	ReceiveRedenvelopeRecord getLastReceive(Long redenvelopeId);

	ReceiveRedenvelopeRecord getBestLuckReceive(Long redenvelopeId);
}