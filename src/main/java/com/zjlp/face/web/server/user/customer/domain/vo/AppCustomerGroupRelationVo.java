package com.zjlp.face.web.server.user.customer.domain.vo;

import com.zjlp.face.web.server.user.customer.domain.AppCustomerGroupRelation;

public class AppCustomerGroupRelationVo extends AppCustomerGroupRelation {

	private static final long serialVersionUID = 2870389460323278422L;

	/** 用户ID **/
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
