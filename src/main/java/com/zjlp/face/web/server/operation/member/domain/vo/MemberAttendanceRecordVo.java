package com.zjlp.face.web.server.operation.member.domain.vo;

import com.zjlp.face.web.server.operation.member.domain.dto.MemberAttendanceRecordDto;

public class MemberAttendanceRecordVo {

	private MemberAttendanceRecordDto recordDto;
	//总积分
	private Integer integral;
	//当前送积分
	private Integer currentAddIntegral;
	//下次送积分
	private Integer nexAddIntegral;
	
	public MemberAttendanceRecordVo(){}
	public MemberAttendanceRecordVo(Integer currentAddIntegral, Integer nexAddIntegral){
		this.currentAddIntegral = currentAddIntegral;
		this.nexAddIntegral = nexAddIntegral;
	}

	public MemberAttendanceRecordDto getRecordDto() {
		return recordDto;
	}
	public void setRecordDto(MemberAttendanceRecordDto recordDto) {
		this.recordDto = recordDto;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Integer getCurrentAddIntegral() {
		return currentAddIntegral;
	}
	public void setCurrentAddIntegral(Integer currentAddIntegral) {
		this.currentAddIntegral = currentAddIntegral;
	}
	public Integer getNexAddIntegral() {
		return nexAddIntegral;
	}
	public void setNexAddIntegral(Integer nexAddIntegral) {
		this.nexAddIntegral = nexAddIntegral;
	}
	
}
