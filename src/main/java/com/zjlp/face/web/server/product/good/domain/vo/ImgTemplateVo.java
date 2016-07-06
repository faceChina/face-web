package com.zjlp.face.web.server.product.good.domain.vo;

import java.io.Serializable;
 
public class ImgTemplateVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5516919658171798714L;
	//编码  key1
	private String code;
	//类别 key2
	private String type;
	//颜色 key3
	private String color;
	//名称 
	private String name;
	//图片路径
	private String imgPath;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
