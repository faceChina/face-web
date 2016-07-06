package com.zjlp.face.web.server.product.template.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ModularMapper;
import com.zjlp.face.web.server.product.template.dao.ModularDao;
import com.zjlp.face.web.server.product.template.domain.Modular;
@Repository
public class ModularDaoImpl implements ModularDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(Modular modular) {
		sqlSession.getMapper(ModularMapper.class).insertSelective(modular);
	}

	@Override
	public void edit(Modular modular) {
		sqlSession.getMapper(ModularMapper.class).updateByPrimaryKeySelective(modular);
	}

	@Override
	public Modular getById(Long id) {
		return sqlSession.getMapper(ModularMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(ModularMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<Modular> findModularByOwTemplateId(Long owTemplateId) {
		return sqlSession.getMapper(ModularMapper.class).selectByOwTemplateId(owTemplateId);
	}

	@Override
	public void updateModularSort(Modular modular) {
		sqlSession.getMapper(ModularMapper.class).updateModularSort(modular);
	}

	@Override
	public Integer getModularMaxSort(Long owTemplateId) {
		return sqlSession.getMapper(ModularMapper.class).getModularMaxSort(owTemplateId);
	}

	@Override
	public void deleteModularByOwTemplateId(Long owTemplateId) {
		sqlSession.getMapper(ModularMapper.class).deleteByOwTemplateId(owTemplateId);
		
	}

	@Override
	public void editModular(Modular modular) {
		sqlSession.getMapper(ModularMapper.class).editModular(modular);
		
	}

}
