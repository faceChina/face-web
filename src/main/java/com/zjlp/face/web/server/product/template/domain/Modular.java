package com.zjlp.face.web.server.product.template.domain;

import java.io.Serializable;
import java.util.Date;

public class Modular implements Serializable, Cloneable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4320255458681119466L;
	//模块编号
	private Long id;
	//模块所属模版编号
    private Long owTemplateId;
    //模块CODE
    private String code;
    //模块中文名称
    private String nameZh;
    //模块中文名称颜色
    private String nameZhColor;
    //模块英文名称
    private String nameEn;
    //模块英文名称颜色
    private String nameEnColor;
    //背景色
    private String backgroundColor;
    //透明度
    private Integer transparency;
    //模块排序
    private Integer sort;
    //模块图片路径
    private String imgPath;
    //模块资源路径（链接）
    private String resourcePath;
    //模块状态
    private Integer status;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //模块类型(1、标准 2、自定义)
    private Integer type;
    //基础模块类型
    private String baseType;
    //绑定的id
    private String bindIds;
    //是否是本地图片
    private Integer isBasePic = 0;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh == null ? null : nameZh.trim();
    }

    public String getNameZhColor() {
        return nameZhColor;
    }

    public void setNameZhColor(String nameZhColor) {
        this.nameZhColor = nameZhColor == null ? null : nameZhColor.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getNameEnColor() {
        return nameEnColor;
    }

    public void setNameEnColor(String nameEnColor) {
        this.nameEnColor = nameEnColor == null ? null : nameEnColor.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath == null ? null : resourcePath.trim();
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

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Integer getTransparency() {
		return transparency;
	}

	public void setTransparency(Integer transparency) {
		this.transparency = transparency;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBaseType() {
		return baseType;
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

	public String getBindIds() {
		return bindIds;
	}

	public void setBindIds(String bindIds) {
		this.bindIds = bindIds;
	}

	public Integer getIsBasePic() {
		return isBasePic;
	}

	public void setIsBasePic(Integer isBasePic) {
		this.isBasePic = isBasePic;
	}
}