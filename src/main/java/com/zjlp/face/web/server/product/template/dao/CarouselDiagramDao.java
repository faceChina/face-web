package com.zjlp.face.web.server.product.template.dao;

import java.util.List;

import com.zjlp.face.web.server.product.template.domain.CarouselDiagram;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;

public interface CarouselDiagramDao {

	Long add(CarouselDiagram carouselDiagram);
	
	void edit(CarouselDiagram carouselDiagram);
	
	CarouselDiagram getById(Long id);
	
	void delete(Long id);
	
	List<CarouselDiagramDto> findCarouselDiagramByOwTemplateId(Long owTemplateId);
}
