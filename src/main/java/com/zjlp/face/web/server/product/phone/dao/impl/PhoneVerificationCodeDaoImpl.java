package com.zjlp.face.web.server.product.phone.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.PhoneVerificationCodeMapper;
import com.zjlp.face.web.server.product.phone.dao.PhoneVerificationCodeDao;
import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;

@Repository("phoneVerificationCodeDao")
public class PhoneVerificationCodeDaoImpl implements PhoneVerificationCodeDao{

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void addPhoneVerificationCode(PhoneVerificationCode phoneVerificationCode) {
		sqlSession.getMapper(PhoneVerificationCodeMapper.class).insertSelective(phoneVerificationCode);
	}

	@Override
	public PhoneVerificationCode selectByPhone(
			PhoneVerificationCode phoneVerificationCode) {
		return sqlSession.getMapper(PhoneVerificationCodeMapper.class).selectByPhone(phoneVerificationCode);
	}

	@Override
	public void editPhoneVerificationCode(
			PhoneVerificationCode phoneVerificationCode) {
		 sqlSession.getMapper(PhoneVerificationCodeMapper.class).updateByPrimaryKeySelective(phoneVerificationCode);
	}

	@Override
	public PhoneVerificationCode queryByPhone(
			PhoneVerificationCode phoneVerificationCode) {
		return sqlSession.getMapper(PhoneVerificationCodeMapper.class).queryByPhone(phoneVerificationCode);
	}

	@Override
	public int getPhoneCodeCount(String cell, Date start, Date end) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("cell", cell);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.getMapper(PhoneVerificationCodeMapper.class).getPhoneCodeCount(map);
	}

	@Override
	public PhoneVerificationCode getValidMobilecode(PhoneVerificationCode code) {
		return sqlSession.getMapper(PhoneVerificationCodeMapper.class).selectValidMobilecode(code);
	}

	@Override
	public void unvalidMobilecode(PhoneVerificationCode mobilecode) {
		sqlSession.getMapper(PhoneVerificationCodeMapper.class).unvalidMobilecode(mobilecode);
	}

	@Override
	public void unvalidMobilecodeById(PhoneVerificationCode mobilecode) {
		sqlSession.getMapper(PhoneVerificationCodeMapper.class).updateByPrimaryKeySelective(mobilecode);
	}

	@Override
	public List<PhoneVerificationCode> getPhoneCodeByCell(String cell, Date createTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cell", cell);
		map.put("createTime", createTime);
		return this.sqlSession.getMapper(PhoneVerificationCodeMapper.class).selectPhoneCodeByCell(map);
	}

	@Override
	public List<PhoneVerificationCode> getValidMobilecodeList(
			PhoneVerificationCode code) {
		return this.sqlSession.getMapper(PhoneVerificationCodeMapper.class).selectValidMobilecodeList(code);
	}

	@Override
	public void minusCount(Long id) {
		this.sqlSession.getMapper(PhoneVerificationCodeMapper.class).minusCount(id);
	}

	@Override
	public Integer getStatisticCount(PhoneVerificationCode code) {
		return this.sqlSession.getMapper(PhoneVerificationCodeMapper.class).selectStatisticCount(code);
	}
}
