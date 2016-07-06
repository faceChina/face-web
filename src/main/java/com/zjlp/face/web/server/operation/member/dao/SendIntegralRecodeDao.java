package com.zjlp.face.web.server.operation.member.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zjlp.face.web.server.operation.member.domain.SendIntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;

@Repository("sendIntegralRecodeDao")
public interface SendIntegralRecodeDao {

	Long add(SendIntegralRecode sendIntegralRecode);

	void updateStatus(Long id, Integer status);

	Long sumSendIntegral(SendIntegralRecode record);

	void editClaimIntegralStatus(SendIntegralRecodeDto record);

	SendIntegralRecode getLastRecord(SendIntegralRecode sendIntegralRecode);
	
	//查询积分数据记录
	List<SendIntegralRecodeDto> querySendIntegralRecode(
			SendIntegralRecodeDto dto);
	
	//查询积分记录条数
	Integer getSendIntegralRecode(SendIntegralRecodeDto dto);
}
