package com.zjlp.face.web.server.operation.redenvelope.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.ReceiveRedenvelopeRecordMapper;
import com.zjlp.face.web.server.operation.redenvelope.dao.ReceiveRedenvelopeRecordDao;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.vo.ReceivePerson;

@Repository
public class ReceiveRedenvelopeRecordDaoImpl implements
		ReceiveRedenvelopeRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertReceive(List<ReceiveRedenvelopeRecord> recordList) {
		sqlSession.getMapper(ReceiveRedenvelopeRecordMapper.class).insertReceive(recordList);
	}

	@Override
	public Integer countReceivePerson(Long sendRecordId, Date clickTime) {
		return sqlSession.getMapper(ReceiveRedenvelopeRecordMapper.class).countReceivePerson(sendRecordId, clickTime);
	}

	@Override
	public List<ReceivePerson> findReceivePerson(
			Pagination<ReceivePerson> pagination, Long sendRecordId, Date clickTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		map.put("sendRecordId", sendRecordId);
		map.put("clickTime", clickTime);
		return sqlSession.getMapper(ReceiveRedenvelopeRecordMapper.class).findReceivePerson(map);
	}

	@Override
	public ReceiveRedenvelopeRecord getReceiveRecordByReceiveUserIdAndSendId(
			Long redenvelopeId, Long receiveUserId) {
		return sqlSession.getMapper(ReceiveRedenvelopeRecordMapper.class).getReceiveRecordByReceiveUserIdAndSendId(redenvelopeId, receiveUserId);
	}

	@Override
	public Long sumHasReceiveMoney(Long redenvelopeId) {
		return  sqlSession.getMapper(ReceiveRedenvelopeRecordMapper.class).sumHasReceiveMoney(redenvelopeId);
	}

	@Override
	public Integer countNotReceive(Long redenvelopeId) {
		return sqlSession.getMapper(ReceiveRedenvelopeRecordMapper.class).countNotReceive(redenvelopeId);
	}

	@Override
	public ReceiveRedenvelopeRecord getLastReceive(Long redenvelopeId) {
		return sqlSession.getMapper(ReceiveRedenvelopeRecordMapper.class).getLastReceive(redenvelopeId);
	}

	@Override
	public ReceiveRedenvelopeRecord getBestLuckReceive(Long redenvelopeId) {
		return sqlSession.getMapper(ReceiveRedenvelopeRecordMapper.class).getBestLuckReceive(redenvelopeId);
	}

}
