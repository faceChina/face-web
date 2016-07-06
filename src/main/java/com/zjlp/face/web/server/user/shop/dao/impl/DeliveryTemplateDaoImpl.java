package com.zjlp.face.web.server.user.shop.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.DeliveryTemplateMapper;
import com.zjlp.face.web.server.user.shop.dao.DeliveryTemplateDao;
import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplate;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;

@Repository("deliveryTemplateDao")
public class DeliveryTemplateDaoImpl implements DeliveryTemplateDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(DeliveryTemplate template) {
		sqlSession.getMapper(DeliveryTemplateMapper.class).insertSelective(template);
		return template.getId();
	}

	@Override
	public void remove(Long id) {
		sqlSession.getMapper(DeliveryTemplateMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public DeliveryTemplate getById(Long id) {
		return sqlSession.getMapper(DeliveryTemplateMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<DeliveryTemplateDto> findListByShopNo(String shopNo) {
		return sqlSession.getMapper(DeliveryTemplateMapper.class).selectListByShopNo(shopNo);
	}

	@Override
	public DeliveryTemplate getByIdAndShopNo(DeliveryTemplate template) {
		return sqlSession.getMapper(DeliveryTemplateMapper.class).selectByIdAndShopNo(template);
	}

	@Override
	public void editById(DeliveryTemplate template) {
		sqlSession.getMapper(DeliveryTemplateMapper.class).updateByPrimaryKeySelective(template);
	}

}
