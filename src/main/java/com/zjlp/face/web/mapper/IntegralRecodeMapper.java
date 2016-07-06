package com.zjlp.face.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;

public interface IntegralRecodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IntegralRecode record);

    int insertSelective(IntegralRecode record);

    IntegralRecode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IntegralRecode record);

    int updateByPrimaryKey(IntegralRecode record);

	List<IntegralRecode> findIntegralRecordListByMemberCardId(@Param("memberCardId")Long memberCardId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
}