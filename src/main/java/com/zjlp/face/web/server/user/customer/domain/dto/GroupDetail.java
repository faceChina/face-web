package com.zjlp.face.web.server.user.customer.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.AppGroup;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月11日 下午2:20:21
 *
 */
public class GroupDetail extends AppGroup implements Serializable {

	private static final long serialVersionUID = -1L;

	/** 当前分组下客户信息 **/
	private List<AppCustomer> groupDetails;

	/** 未分组客户 **/
	private List<AppCustomerVo> ungroupCustomer;

	public List<AppCustomer> getGroupDetails() {
		return groupDetails;
	}

	public void setGroupDetails(List<AppCustomer> groupDetails) {
		this.groupDetails = groupDetails;
	}

	public List<AppCustomerVo> getUngroupCustomer() {
		return ungroupCustomer;
	}

	public void setUngroupCustomer(List<AppCustomerVo> ungroupCustomer) {
		this.ungroupCustomer = ungroupCustomer;
	}

}
