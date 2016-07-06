package com.zjlp.face.web.server.user.user.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserGis implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -7413010006546203512L;
	
	/**主键**/
	private Long id;
	/**用户ID**/
	private Long userId;
	/**经度**/
	private BigDecimal longitude;
	/**纬度**/
	private BigDecimal latitude;
	/**状态  0 关闭,1启用**/
	private Integer status;
	/**创建时间**/
	private Date createTime;
	/**更新时间**/
	private Date updateTime;
	/**雷达开启状态**/
	private Integer radarEnable;
	
	public Integer getRadarEnable() {
		return radarEnable;
	}
	public void setRadarEnable(Integer radarEnable) {
		this.radarEnable = radarEnable;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
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
	@Override
	public String toString() {
		
			return	new StringBuilder().append("UserGis [id=").append(id)
					.append(", userId=").append(userId).append(", longitude=")
					.append(longitude).append(", latitude=").append(latitude)
					.append(", status=").append(status).append(", createTime=")
					.append(createTime).append(", updateTime=").append(updateTime)
					.append("]").toString();
	}
}
