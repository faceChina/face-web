package com.zjlp.face.web.server.operation.member.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ConsumptionAdjustmentRecordMapper;
import com.zjlp.face.web.server.operation.member.dao.ConsumptionAdjustmentRecordDao;
import com.zjlp.face.web.server.operation.member.domain.ConsumptionAdjustmentRecord;

@Repository("consumptionAdjustmentRecordDao")
public class ConsumptionAdjustmentRecordDaoImpl implements
		ConsumptionAdjustmentRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(ConsumptionAdjustmentRecord adjustmentRecord) {
		sqlSession.getMapper(ConsumptionAdjustmentRecordMapper.class).insertSelective(adjustmentRecord);
		return adjustmentRecord.getId();
	}

}
