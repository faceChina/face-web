package com.zjlp.face.web.server.product.template.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.CarouselDiagramMapper;
import com.zjlp.face.web.server.product.template.dao.CarouselDiagramDao;
import com.zjlp.face.web.server.product.template.domain.CarouselDiagram;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
@Repository
public class CarouselDiagramDaoImpl implements CarouselDiagramDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(CarouselDiagram carouselDiagram) {
		sqlSession.getMapper(CarouselDiagramMapper.class).insertSelective(carouselDiagram);
		return carouselDiagram.getId();
	}

	@Override
	public void edit(CarouselDiagram carouselDiagram) {
		sqlSession.getMapper(CarouselDiagramMapper.class).updateByPrimaryKeySelective(carouselDiagram);
	}

	@Override
	public CarouselDiagram getById(Long id) {
		return sqlSession.getMapper(CarouselDiagramMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(CarouselDiagramMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<CarouselDiagramDto> findCarouselDiagramByOwTemplateId(Long owTemplateId) {
		return sqlSession.getMapper(CarouselDiagramMapper.class).selectByOwTemplateId(owTemplateId);
	}
}
