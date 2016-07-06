package com.zjlp.face.web.server.user.shop.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.DeliveryTemplateItemMapper;
import com.zjlp.face.web.server.user.shop.dao.DeliveryTemplateItemDao;
import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplateItem;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;

@Repository("deliveryTemplateItemDao")
public class DeliveryTemplateItemDaoImpl implements DeliveryTemplateItemDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(DeliveryTemplateItem item) {
		sqlSession.getMapper(DeliveryTemplateItemMapper.class).insertSelective(item);
		return item.getId();
	}

	@Override
	public List<DeliveryTemplateItemDto> findList(Long templateId) {
		return sqlSession.getMapper(DeliveryTemplateItemMapper.class).selectByTemplateId(templateId);
	}

	@Override
	public void remove(Long templateId) {
		sqlSession.getMapper(DeliveryTemplateItemMapper.class).deleteByTemplateId(templateId);
	}

	@Override
	public void removeByTemplateId(Long id) {
		sqlSession.getMapper(DeliveryTemplateItemMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public DeliveryTemplateItem getById(Long id) {
		return sqlSession.getMapper(DeliveryTemplateItemMapper.class).selectByPrimaryKey(id);
	}

}
