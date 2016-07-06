package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.template.domain.CarouselDiagram;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;

public interface CarouselDiagramMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarouselDiagram record);

    int insertSelective(CarouselDiagram record);

    CarouselDiagram selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarouselDiagram record);

    int updateByPrimaryKey(CarouselDiagram record);
    
    List<CarouselDiagramDto> selectByOwTemplateId(Long record);
}