package com.zjlp.face.web.server.operation.member.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 会员卡签到记录表
 * @ClassName: MemberAttendanceRecord 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月10日 下午4:57:46
 */
public class MemberAttendanceRecord implements Serializable {
	private static final long serialVersionUID = 7266377168581070016L;
	//主键
	private Long id;
	//会员卡ID
    private Long memberCardId;
    //连续签到次数 自动累加
    private Integer attendanceNumber;
    //签到时间
    private Date attendanceTime;
    //创建时间
    private Date createTime;
    
    public MemberAttendanceRecord(){}
    public MemberAttendanceRecord(Long memberCardId, Integer attendanceNumber, Date attendanceTime) {
    	this.memberCardId = memberCardId;
    	this.attendanceNumber = attendanceNumber;
    	this.attendanceTime = attendanceTime;
    	this.createTime = attendanceTime;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberCardId() {
		return memberCardId;
	}
	public void setMemberCardId(Long memberCardId) {
		this.memberCardId = memberCardId;
	}
	public Integer getAttendanceNumber() {
		return attendanceNumber;
	}
	public void setAttendanceNumber(Integer attendanceNumber) {
		this.attendanceNumber = attendanceNumber;
	}
	public Date getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(Date attendanceTime) {
		this.attendanceTime = attendanceTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return new StringBuilder("MemberAttendanceRecord [id=").append(id)
				.append(", memberCardId=").append(memberCardId)
				.append(", attendanceNumber=").append(attendanceNumber)
				.append(", attendanceTime=").append(attendanceTime)
				.append(", createTime=").append(createTime).append("]")
				.toString();
	}
	
}