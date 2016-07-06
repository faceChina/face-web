package com.zjlp.face.web.server.product.template.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.zjlp.face.web.server.product.template.domain.Modular;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;

public class TemplateVo implements Serializable {

	private static final long serialVersionUID = -2419596349382218784L;

	private OwTemplateDto owTemplateHp;
	
	private List<CarouselDiagramDto> carouselList;
	
	private List<Modular> modularList;
	
	private String json;
	
	private String jsonCustom;

	public final OwTemplateDto getOwTemplateHp() {
		return owTemplateHp;
	}

	public final void setOwTemplateHp(OwTemplateDto owTemplateHp) {
		this.owTemplateHp = owTemplateHp;
	}

	public final List<CarouselDiagramDto> getCarouselList() {
		return carouselList;
	}

	public final void setCarouselList(List<CarouselDiagramDto> carouselList) {
		this.carouselList = carouselList;
	}

	public final List<Modular> getModularList() {
		return modularList;
	}

	public final void setModularList(List<Modular> modularList) {
		this.modularList = modularList;
	}
	
	public void setJson(String json){
		this.json = json;
	}
	
	public String getJson(){
		return this.json;
	}

	public String getJsonCustom() {
		return jsonCustom;
	}

	public void setJsonCustom(String jsonCustom) {
		this.jsonCustom = jsonCustom;
	}
	

}
