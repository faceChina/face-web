package com.zjlp.face.web.server.product.template.domain;

import java.io.Serializable;
import java.util.Date;

public class TemplateDetail implements Serializable {
	
	private static final long serialVersionUID = 8659769549421431470L;

	private Long id;

    private Long owTemplateId;

    private String shopStrokesPath;

    private String cell;

    private String address;

    private String fontColor;

    private String backgroundColor;

    private Date createTime;

    private Date updateTime;
    
    private Integer isBasePic=0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwTemplateId() {
        return owTemplateId;
    }

    public void setOwTemplateId(Long owTemplateId) {
        this.owTemplateId = owTemplateId;
    }

    public String getShopStrokesPath() {
        return shopStrokesPath;
    }

    public void setShopStrokesPath(String shopStrokesPath) {
        this.shopStrokesPath = shopStrokesPath == null ? null : shopStrokesPath.trim();
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell == null ? null : cell.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor == null ? null : fontColor.trim();
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor == null ? null : backgroundColor.trim();
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

	public Integer getIsBasePic() {
		return isBasePic;
	}

	public void setIsBasePic(Integer isBasePic) {
		this.isBasePic = isBasePic;
	}
    
    
}