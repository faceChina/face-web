package com.zjlp.face.web.server.user.shop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 运费模板中运费信息对象
 * @ClassName: DeliveryTemplateItem 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年3月27日 上午9:52:26
 */
public class DeliveryTemplateItem implements Serializable {
	
	private static final long serialVersionUID = 2300048560016166550L;
	//主键
	private Long id;
	//运费模版ID
    private Long postageTemplateId;
    //邮费子项涉及的地区,多个地区用逗号连接数量串
    private String destination;
    //首费标准
    private Integer startStandard;
    //首费(单位：分)
    private Long startPostage;
    //增费标准
    private Integer addStandard;
    //续件运费(单位：分)
    private Long addPostage;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostageTemplateId() {
        return postageTemplateId;
    }

    public void setPostageTemplateId(Long postageTemplateId) {
        this.postageTemplateId = postageTemplateId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public Integer getStartStandard() {
        return startStandard;
    }

    public void setStartStandard(Integer startStandard) {
        this.startStandard = startStandard;
    }

    public Long getStartPostage() {
        return startPostage;
    }

    public void setStartPostage(Long startPostage) {
        this.startPostage = startPostage;
    }

    public Integer getAddStandard() {
        return addStandard;
    }

    public void setAddStandard(Integer addStandard) {
        this.addStandard = addStandard;
    }

    public Long getAddPostage() {
        return addPostage;
    }

    public void setAddPostage(Long addPostage) {
        this.addPostage = addPostage;
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