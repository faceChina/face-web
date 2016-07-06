package com.zjlp.face.web.server.operation.redenvelope.dao.impl;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.web.mapper.SendRedenvelopeRecordMapper;
import com.zjlp.face.web.server.operation.redenvelope.dao.SendRedenvelopeRecordDao;
import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;

@Repository
public class SendRedenvelopeRecordDaoImpl implements SendRedenvelopeRecordDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long countUserSendAmountToday(Long userId) {
		Date todayZeroPoint = DateUtil.getZeroPoint(new Date());
		return sqlSession.getMapper(SendRedenvelopeRecordMapper.class).countUserSendAmountToday(userId, todayZeroPoint);
	}

	@Override
	public Long insertSend(SendRedenvelopeRecord sendRedenvelopeRecord) {
		sqlSession.getMapper(SendRedenvelopeRecordMapper.class).insertSelective(sendRedenvelopeRecord);
		return sendRedenvelopeRecord.getId();
	}

	@Override
	public SendRedenvelopeRecord getSendRocordById(Long id) {
		return sqlSession.getMapper(SendRedenvelopeRecordMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void updateSendRocordStatus(SendRedenvelopeRecord newRecord) {
		sqlSession.getMapper(SendRedenvelopeRecordMapper.class).updateByPrimaryKeySelective(newRecord);
	}

	
}
