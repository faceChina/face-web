package com.zjlp.face.web.server.product.template.domain.dto;

import java.io.Serializable;
import java.util.List;

public class ParamDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1691605326477292922L;
	private List<CarouselDiagramDto> list;

	public List<CarouselDiagramDto> getList() {
		return list;
	}

	public void setList(List<CarouselDiagramDto> list) {
		this.list = list;
	}
}
