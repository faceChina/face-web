package com.zjlp.face.web.server.user.user.domain;

import java.io.Serializable;

/**
 * 地区信息
 * 
 * @ClassName: VArea
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月13日 下午5:13:24
 */
public class VArea implements Serializable {
	private static final long serialVersionUID = -6882746011905337847L;
	// 编码
	private Integer code;
	// 上级编码
	private String parentId;
	// 地区
	private String area;
	// 拼音
	private String pinyin;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin == null ? null : pinyin.trim();
	}
}