package com.zjlp.face.web.server.product.good.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品图片表
 * @ClassName: GoodImg 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年4月6日 下午1:28:08
 */
public class GoodImg implements Serializable{
	
	public static final Integer PDU_IMG_TYPE=2;
	
	private static final long serialVersionUID = -5778978050087890693L;
	//主键
	private Long id;
	//商品主键
    private Long goodId;
    
    private Integer type;
    //图片地址
    private String url;
    //图片位置
    private Integer position;
    //图片创建时间
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}