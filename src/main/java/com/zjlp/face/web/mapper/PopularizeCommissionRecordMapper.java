package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.trade.payment.domain.PopularizeCommissionRecord;


public interface PopularizeCommissionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PopularizeCommissionRecord record);

    int insertSelective(PopularizeCommissionRecord record);

    PopularizeCommissionRecord selectByPrimaryKey(Long id);
   
    PopularizeCommissionRecord selectByPrimaryKeyForLock(Long id);

    int updateByPrimaryKeySelective(PopularizeCommissionRecord record);

    int updateByPrimaryKey(PopularizeCommissionRecord record);
    
    List<PopularizeCommissionRecord> selectByOrderNo(String orderNo);
    
    List<PopularizeCommissionRecord> selectByPopuCell(String popuCell);

	Long getUsersPopularizeAmount(Long accountId);
}