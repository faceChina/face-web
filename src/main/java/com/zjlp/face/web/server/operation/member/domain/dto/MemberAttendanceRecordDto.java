package com.zjlp.face.web.server.operation.member.domain.dto;

import java.util.Date;

import com.zjlp.face.util.date.DateStyle;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.web.server.operation.member.domain.MemberAttendanceRecord;

public class MemberAttendanceRecordDto extends MemberAttendanceRecord {

	private static final long serialVersionUID = 946350922160108720L;
	
	/**
	 * 是否为昨日签到记录
	 * @Title: getIsPreDay 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年4月11日 上午11:07:11  
	 * @author lys
	 */
	public static boolean isPreDayRecord(MemberAttendanceRecord attendanceRecord) {
		return isDayRecordOf(attendanceRecord, DateUtil.addDay(new Date(), -1));
	}
	
	public static boolean isTodayRecord(MemberAttendanceRecord attendanceRecord) {
		return isDayRecordOf(attendanceRecord, new Date());
	}
	/**
	 * 是否为某天签到的记录
	 * @Title: isDayRecordOf 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param attendanceRecord 签到记录
	 * @param date 日期
	 * @return
	 * @date 2015年4月11日 下午1:49:28  
	 * @author lys
	 */
	public static boolean isDayRecordOf(MemberAttendanceRecord attendanceRecord, Date date) {
		Date attendanceTime = null;
		if (null == attendanceRecord || null == date 
				|| null == (attendanceTime = attendanceRecord.getAttendanceTime())) {
			return false;
		}
		return DateUtil.DateToString(attendanceTime, DateStyle.YYYY_MM_DD).equals(
				DateUtil.DateToString(date, DateStyle.YYYY_MM_DD));
	}
	
	public static MemberAttendanceRecord getNextRecord(MemberAttendanceRecord record, Long cardId) {
		if (null == record) {
			record = init(cardId);
		} else {
			record.setAttendanceNumber(record.getAttendanceNumber() + 1);
			record.setAttendanceTime(new Date());
			record.setId(null);
			record.setMemberCardId(cardId);
			record.setCreateTime(null);
		}
		return record;
	}
	
	public static MemberAttendanceRecord init(Long cardId) {
		MemberAttendanceRecord record = new MemberAttendanceRecord();
		record.setAttendanceNumber(1);
		record.setAttendanceTime(new Date());
		record.setMemberCardId(cardId);
		return record;
	}

}
