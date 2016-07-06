package com.zjlp.face.web.server.product.good.domain.vo;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.Classification;

public class ClassificationVo extends Classification{

	private static final long serialVersionUID = 3095589096510986660L;
	/** 商品类目全名 使用‘>’分开*/ 
	private String allName;
	
	private List<String> nameList;
	
	public List<String> getNameList() {
		return nameList;
	}
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}
	public String getAllName() {
		return allName;
	}
	public void setAllName(String allName) {
		this.allName = allName;
	}

}
