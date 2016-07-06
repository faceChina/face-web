package com.zjlp.face.web.server.product.template.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.TemplateDetailMapper;
import com.zjlp.face.web.server.product.template.dao.TemplateDetailDao;
import com.zjlp.face.web.server.product.template.domain.TemplateDetail;

@Repository
public class TemplateDetailDaoImpl implements TemplateDetailDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(TemplateDetail templateDetail) {
		sqlSession.getMapper(TemplateDetailMapper.class).insertSelective(templateDetail);

	}

	@Override
	public void edit(TemplateDetail templateDetail) {
		sqlSession.getMapper(TemplateDetailMapper.class).updateByPrimaryKeySelective(templateDetail);
	}

	@Override
	public TemplateDetail getById(Long id) {
		return sqlSession.getMapper(TemplateDetailMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(TemplateDetailMapper.class).deleteByPrimaryKey(id);

	}

	@Override
	public TemplateDetail getByTemplateId(Long tempalteId) {
		return sqlSession.getMapper(TemplateDetailMapper.class).selectByTemplateId(tempalteId);
	}

}
