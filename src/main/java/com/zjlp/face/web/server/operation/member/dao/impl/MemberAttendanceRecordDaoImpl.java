package com.zjlp.face.web.server.operation.member.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MemberAttendanceRecordMapper;
import com.zjlp.face.web.server.operation.member.dao.MemberAttendanceRecordDao;
import com.zjlp.face.web.server.operation.member.domain.MemberAttendanceRecord;

@Repository("memberAttendanceRecordDao")
public class MemberAttendanceRecordDaoImpl implements MemberAttendanceRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(MemberAttendanceRecord record) {
		sqlSession.getMapper(MemberAttendanceRecordMapper.class).insertSelective(record);
		return record.getId();
	}

	@Override
	public MemberAttendanceRecord getLastRecordByMemberCardId(Long memberCardId) {
		return sqlSession.getMapper(MemberAttendanceRecordMapper.class).selectLastRecordByMemberCardId(memberCardId);
	}

}
