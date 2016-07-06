package com.zjlp.face.web.server.operation.member.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.zjlp.face.web.server.operation.member.domain.dto.MemberRule;

public class MemberVo implements Serializable {

	private static final long serialVersionUID = 988492697271914041L;

	private List<MemberRule> ruleList = null;

	public List<MemberRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<MemberRule> ruleList) {
		this.ruleList = ruleList;
	}
	
}
