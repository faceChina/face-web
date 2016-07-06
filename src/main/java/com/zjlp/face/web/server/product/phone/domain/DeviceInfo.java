package com.zjlp.face.web.server.product.phone.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 友钱设备注册
 * 
 * @author Baojiang Yang
 * @date 2015年9月7日 下午7:02:18
 *
 */
public class DeviceInfo implements Serializable{

	private static final long serialVersionUID = 7560176349732965246L;

	/** 主键 **/
	private Long id;
	/** 设备注册ID **/
	private String deviceInfo;
	/** 状态：0第一次 1第二次 **/
	private Integer replay;
	/** 状态：-1删除 1正常 **/
	private Integer status;
	/** 创建时间 **/
	private Date createTime;
	/** 更新时间 **/
	private Date updateTime;

	public DeviceInfo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Integer getReplay() {
		return replay;
	}

	public void setReplay(Integer replay) {
		this.replay = replay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
