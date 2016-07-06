package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.operation.member.domain.MemberAttendanceRecord;

public interface MemberAttendanceRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberAttendanceRecord record);

    int insertSelective(MemberAttendanceRecord record);

    MemberAttendanceRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberAttendanceRecord record);

    int updateByPrimaryKey(MemberAttendanceRecord record);

	MemberAttendanceRecord selectLastRecordByMemberCardId(Long memberCardId);
}