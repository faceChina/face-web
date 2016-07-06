package com.zjlp.face.web.server.operation.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MemberTransactionRecordMapper;
import com.zjlp.face.web.mapper.SendIntegralRecodeMapper;
import com.zjlp.face.web.server.operation.member.dao.SendIntegralRecodeDao;
import com.zjlp.face.web.server.operation.member.domain.SendIntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

@Repository("sendIntegralRecodeDao")
public class SendIntegralRecodeDaoImp implements SendIntegralRecodeDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(SendIntegralRecode sendIntegralRecode) {
		sqlSession.getMapper(SendIntegralRecodeMapper.class).insertSelective(sendIntegralRecode);
		return sendIntegralRecode.getId();
	}

	@Override
	public void updateStatus(Long id, Integer status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", status);
		sqlSession.getMapper(SendIntegralRecodeMapper.class).updateStatus(param);
		
	}

	@Override
	public Long sumSendIntegral(SendIntegralRecode record) {
		return sqlSession.getMapper(SendIntegralRecodeMapper.class).sumSendIntegral(record);
	}

	@Override
	public void editClaimIntegralStatus(SendIntegralRecodeDto record) {
		sqlSession.getMapper(SendIntegralRecodeMapper.class).updateClaimIntegralStatus(record);
	}

	@Override
	public SendIntegralRecode getLastRecord(
			SendIntegralRecode sendIntegralRecode) {
		return sqlSession.getMapper(SendIntegralRecodeMapper.class).queryLastIntegral(sendIntegralRecode);
	}

	/**
	 * 查询积分记录列表
	 */
	@Override
	public List<SendIntegralRecodeDto> querySendIntegralRecode(
			SendIntegralRecodeDto dto) {
		return sqlSession.getMapper(SendIntegralRecodeMapper.class).querySendIntegralRecode(dto);
	}

	/**
	 * 查询积分记录条数
	 */
	@Override
	public Integer getSendIntegralRecode(SendIntegralRecodeDto dto) {
		return sqlSession.getMapper(SendIntegralRecodeMapper.class).selectSendIntegralCount(dto);
	}
}
