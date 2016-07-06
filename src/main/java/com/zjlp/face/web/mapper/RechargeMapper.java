package com.zjlp.face.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.trade.recharge.domain.Recharge;

public interface RechargeMapper {
    int deleteByPrimaryKey(String rechargeNo);

    int insert(Recharge record);

    int insertSelective(Recharge record);

    Recharge selectByPrimaryKey(String rechargeNo);

    int updateByPrimaryKeySelective(Recharge record);

    int updateByPrimaryKey(Recharge record);

	List<Recharge> selectList(Recharge recharge);

	Recharge getRechargeByTSN(String transactionSerialNumber);

	List<Recharge> findRechargeListByUserAccountAndAccountType(
			@Param("userAccount")String memberCardId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
}