package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.operation.member.domain.ConsumptionAdjustmentRecord;

public interface ConsumptionAdjustmentRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ConsumptionAdjustmentRecord record);

    int insertSelective(ConsumptionAdjustmentRecord record);

    ConsumptionAdjustmentRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConsumptionAdjustmentRecord record);

    int updateByPrimaryKey(ConsumptionAdjustmentRecord record);
}