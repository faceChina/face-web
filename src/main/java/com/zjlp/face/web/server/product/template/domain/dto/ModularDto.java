package com.zjlp.face.web.server.product.template.domain.dto;

import java.io.Serializable;

import com.zjlp.face.web.server.product.template.domain.Modular;

public class ModularDto extends Modular implements Serializable {
 
	private static final long serialVersionUID = -4795358146409424428L;

	//基础模块类型
    private String baseType;
    //绑定的id
    private String bindIds;
    
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
}
