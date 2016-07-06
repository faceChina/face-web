package com.zjlp.face.web.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;

public interface SendRedenvelopeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SendRedenvelopeRecord record);

    int insertSelective(SendRedenvelopeRecord record);

    SendRedenvelopeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SendRedenvelopeRecord record);

    int updateByPrimaryKey(SendRedenvelopeRecord record);

	Long countUserSendAmountToday(@Param("userId")Long userId, @Param("todayZeroPoint")Date todayZeroPoint);

}