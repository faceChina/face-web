package com.zjlp.face.web.server.product.template.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.OwTemplateMapper;
import com.zjlp.face.web.server.product.template.dao.OwTemplateDao;
import com.zjlp.face.web.server.product.template.domain.OwTemplate;
@Repository
public class OwTemplateDaoImpl implements OwTemplateDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(OwTemplate owTemplate) {
		sqlSession.getMapper(OwTemplateMapper.class).insertSelective(owTemplate);
	}

	@Override
	public void edit(OwTemplate owTemplate) {
		sqlSession.getMapper(OwTemplateMapper.class).updateByPrimaryKeySelective(owTemplate);
	}

	@Override
	public OwTemplate getById(Long id) {
		return sqlSession.getMapper(OwTemplateMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(OwTemplateMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<OwTemplate> getByShopNo(OwTemplate owTemplate) {
		return sqlSession.getMapper(OwTemplateMapper.class).selectByShopNo(owTemplate);
	}

	@Override
	public OwTemplate getOwTemplateByShopNoAndCode(OwTemplate owTemplate) {
		return sqlSession.getMapper(OwTemplateMapper.class).selectByShopNoAndCode(owTemplate);
	}

	@Override
	public OwTemplate getCurrent(OwTemplate owTemplate) {
		return sqlSession.getMapper(OwTemplateMapper.class).selectCurrent(owTemplate);
	}

	@Override
	public void editStatus(OwTemplate owTemplate) {
		sqlSession.getMapper(OwTemplateMapper.class).updateStatus(owTemplate);
		
	}

	@Override
	public Integer countTemplate(OwTemplate owTemplate) {
		return sqlSession.getMapper(OwTemplateMapper.class).countTemplate(owTemplate);
	}

}
