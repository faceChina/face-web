package com.zjlp.face.web.server.product.im.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ImMessageMapper;
import com.zjlp.face.web.server.product.im.dao.ImMessageDao;
import com.zjlp.face.web.server.product.im.domain.ImMessage;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;

@Repository
public class ImMessageDaoImpl implements ImMessageDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(ImMessage imMessage) {
		sqlSession.getMapper(ImMessageMapper.class).insertSelective(imMessage);
	}

	@Override
	public void edit(ImMessage imMessage) {
		sqlSession.getMapper(ImMessageMapper.class).updateByPrimaryKeySelective(imMessage);
	}

	@Override
	public void deleteById(Long id) {
		sqlSession.getMapper(ImMessageMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByUserId(Long userId) {
		sqlSession.getMapper(ImMessageMapper.class).deleteByUserId(userId);
	}

	@Override
	public ImMessage getById(Long id) {
		return sqlSession.getMapper(ImMessageMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<ImMessageDto> findImUserMessage(ImMessageDto imMessageDto,Integer start,Integer pageSize) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("dto", imMessageDto);
		params.put("start", start);
		params.put("pageSize", pageSize);
		return sqlSession.getMapper(ImMessageMapper.class).selectImUserMessage(params);
	}

	@Override
	public Integer getCount(ImMessage imMessage) {
		return sqlSession.getMapper(ImMessageMapper.class).selectImUserMessageCount(imMessage);
	}

}
