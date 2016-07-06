package com.zjlp.face.web.server.operation.member.dao;

import com.zjlp.face.web.server.operation.member.domain.MemberAttendanceRecord;

/**
 * 签到记录Dao
 * @ClassName: MemberAttendanceRecordDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月11日 上午9:20:43
 */
public interface MemberAttendanceRecordDao {

	Long add(MemberAttendanceRecord record);

	MemberAttendanceRecord getLastRecordByMemberCardId(Long memberCardId);

}
