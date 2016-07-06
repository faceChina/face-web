package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.operation.member.domain.SendIntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

public interface SendIntegralRecodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SendIntegralRecode record);

    int insertSelective(SendIntegralRecode record);

    SendIntegralRecode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SendIntegralRecode record);

    int updateByPrimaryKey(SendIntegralRecode record);

	void updateStatus(Map<String, Object> param);

	Long sumSendIntegral(SendIntegralRecode record);

	void updateClaimIntegralStatus(SendIntegralRecodeDto record);
	
	SendIntegralRecode queryLastIntegral(SendIntegralRecode record);
	
	List<SendIntegralRecodeDto> querySendIntegralRecode(
			SendIntegralRecodeDto dto);
	
	Integer selectSendIntegralCount(SendIntegralRecodeDto dto);
}