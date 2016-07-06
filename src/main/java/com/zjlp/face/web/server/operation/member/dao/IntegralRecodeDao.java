package com.zjlp.face.web.server.operation.member.dao;

import java.util.List;

import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;

public interface IntegralRecodeDao {
	
	void add(IntegralRecode integralRecode);

	List<IntegralRecode> findIntegralRecordListByMemberCardId(Long memberCardId, String beginTime, String endTime);


}
