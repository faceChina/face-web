package com.zjlp.face.web.server.user.user.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserGisVo implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -7413010006546203512L;
	
	public static final int LEIDA_OPEN = 1;  // 雷达声波开启
	public static final int LEIDA_CLOSE = -1;  // 雷达声波关闭
	
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
	/**雷达状态开启或关闭**/
	private Integer radarEnable;
	
	public Integer getRadarEnable() {
		return radarEnable;
	}
	public void setRadarEnable(Integer radarEnable) {
		this.radarEnable = radarEnable;
	}
	/**左边经度**/
	private BigDecimal leftLongitude;
	/**右边经度**/
	private BigDecimal rightLongitude;
	/**下边纬度**/
	private BigDecimal downLatitude;
	/**上边纬度**/
	private BigDecimal topLatitude;
	/**获取记录数量**/
	private Integer number;
	
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
	
	public BigDecimal getLeftLongitude() {
		return leftLongitude;
	}
	public void setLeftLongitude(BigDecimal leftLongitude) {
		this.leftLongitude = leftLongitude;
	}
	public BigDecimal getRightLongitude() {
		return rightLongitude;
	}
	public void setRightLongitude(BigDecimal rightLongitude) {
		this.rightLongitude = rightLongitude;
	}
	public BigDecimal getDownLatitude() {
		return downLatitude;
	}
	public void setDownLatitude(BigDecimal downLatitude) {
		this.downLatitude = downLatitude;
	}
	public BigDecimal getTopLatitude() {
		return topLatitude;
	}
	public void setTopLatitude(BigDecimal topLatitude) {
		this.topLatitude = topLatitude;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
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
