package com.zjlp.face.web.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;

public interface CommissionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommissionRecord record);

    int insertSelective(CommissionRecord record);

    CommissionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommissionRecord record);

    int updateByPrimaryKey(CommissionRecord record);
    
    int updateIsToAccount(CommissionRecord record);
    
    List<CommissionRecord> selectByOrderNo(String orderNo);

	Long getUserFreezeAmount(Long accountId);

	Long getUsersShopsFreezeAmount(Long userId);

	Long getShopFreezeIncome(String shopNo);

	Long getShopIncomeByTime(@Param("shopNo")String shopNo, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
	
	String getShopName(Long id);
	
	String getSubbranchShopName(Long id);
	
}