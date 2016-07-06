package com.zjlp.face.web.server.product.template.domain.dto;

import com.zjlp.face.web.server.product.template.domain.CarouselDiagram;

public class CarouselDiagramDto extends CarouselDiagram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5090922166583196631L;
	
	private String bindIds;
	
	//是否是本地图片
    private Integer isBasePic = 0;
    
	public Integer getIsBasePic() {
		return isBasePic;
	}

	public void setIsBasePic(Integer isBasePic) {
		this.isBasePic = isBasePic;
	}

	public String getBindIds() {
		return bindIds;
	}

	public void setBindIds(String bindIds) {
		this.bindIds = bindIds;
	}
}
