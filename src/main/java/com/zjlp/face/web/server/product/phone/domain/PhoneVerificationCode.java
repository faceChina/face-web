package com.zjlp.face.web.server.product.phone.domain;

import java.io.Serializable;
import java.util.Date;

import com.zjlp.face.web.constants.GenerateCode;

public class PhoneVerificationCode implements Serializable {
	
	private static final long serialVersionUID = 819259767575682512L;
	//失效
	public static final Integer STATUS_UNVALID = -1;
	//测试验证通过
	public static final Integer STATUS_TESTED = 2;
	//有效
	public static final Integer STATUS_VALID = 1;
	//三分钟
	private static final Long PHONE_TIME_VALID = 180L * 1000;
	//默认次数（无数次）
	public static final Integer DEFAULT_COUNT = 5;
	//主键
	private Long id;
	//手机号码
    private String cell;
    //代码
    private String code;
    //状态
    private Integer status;
    //有效时间
    private Date validTime;
    //应用场景
    private Integer type;
    //剩余验证次数
    private Integer checkCount;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    
    public PhoneVerificationCode(){}
    
    public PhoneVerificationCode(Long id, Integer status, Date updateTime) {
    	this.id = id;
    	this.status = status;
    	this.updateTime = updateTime;
    }
    public PhoneVerificationCode(String cell, Integer type) {
    	this.cell = cell;
    	this.type = type;
	}
    public static PhoneVerificationCode getInstance(String cell, Integer type, Integer checkCount, Date validTime) {
		String code = GenerateCode.createRandom(true, 6);
		PhoneVerificationCode pvc = new PhoneVerificationCode();
		Date date = new Date();
		pvc.setCreateTime(date);
		pvc.setCheckCount(checkCount);
		pvc.setUpdateTime(date);
		pvc.setCode(code);
		pvc.setCell(cell);
		pvc.setStatus(PhoneVerificationCode.STATUS_VALID);
		pvc.setType(type);
		pvc.setValidTime(validTime);// 设置有效期
		return pvc;
    }
    
    public static PhoneVerificationCode getInstance(String cell, Integer type, Integer checkCount) {
    	return getInstance(cell, type, checkCount, new Date(new Date().getTime() + PHONE_TIME_VALID));
    }
    
    public static PhoneVerificationCode getInstance(String cell, Integer type, Date validTime) {
    	return getInstance(cell, type, DEFAULT_COUNT, validTime);
    }
    
    public static PhoneVerificationCode getInstance(String cell, Integer type) {
    	return getInstance(cell, type, DEFAULT_COUNT, new Date(new Date().getTime() + PHONE_TIME_VALID));
    }
    public boolean inCount(){
    	return null != checkCount && checkCount > 0;
    }
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCell() {
        return cell;
    }
    public void setCell(String cell) {
        this.cell = cell == null ? null : cell.trim();
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getValidTime() {
        return validTime;
    }
    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Integer checkCount) {
		this.checkCount = checkCount;
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
		return "PhoneVerificationCode [id=" + id + ", cell=" + cell + ", code="
				+ code + ", status=" + status + ", validTime=" + validTime
				+ ", type=" + type + ", checkCount=" + checkCount
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
}