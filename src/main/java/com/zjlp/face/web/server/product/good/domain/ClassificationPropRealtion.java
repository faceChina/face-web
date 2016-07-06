package com.zjlp.face.web.server.product.good.domain;

import java.util.Date;
/**
 * 类目与标准属性关系表
 * @ClassName: ClassificationPropRealtion 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月16日 上午9:51:04
 */
public class ClassificationPropRealtion {
	
    private Long id;
	//商品类目ID
    private Long classificationId;
    //标准属性ID
    private Long propId;
    //创建时间
    private Date createTime;
    //排序
    private Integer sort;
    
    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}