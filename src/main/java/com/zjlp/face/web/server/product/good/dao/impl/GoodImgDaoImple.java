package com.zjlp.face.web.server.product.good.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.GoodImgMapper;
import com.zjlp.face.web.server.product.good.dao.GoodImgDao;
import com.zjlp.face.web.server.product.good.domain.GoodImg;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;

@Repository
public class GoodImgDaoImple implements GoodImgDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(GoodImg goodImg) {
		sqlSession.getMapper(GoodImgMapper.class).insert(goodImg);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(GoodImgMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void edit(GoodImg goodImg) {
		sqlSession.getMapper(GoodImgMapper.class).updateByPrimaryKey(goodImg);
	}


	@Override
	public GoodImg getById(Long id) {
		return sqlSession.getMapper(GoodImgMapper.class).selectByPrimaryKey(id);
	}
	
	@Override
	public void deleteByGoodId(Long goodId) {
		sqlSession.getMapper(GoodImgMapper.class).deleteByGoodId(goodId);
	}

	@Override
	public GoodImg getFirstImg(Long goodId) {
		return sqlSession.getMapper(GoodImgMapper.class).selectFirst(goodId);
	}

	@Override
	public List<GoodImgVo> findZoomByGoodIdAndType(Long goodId, Integer type) {
		GoodImg goodImg=new GoodImg();
		goodImg.setGoodId(goodId);
		goodImg.setType(type);
		return sqlSession.getMapper(GoodImgMapper.class).selectZoomByGoodIdAndType(goodImg);
	}
	
	@Override
	public List<GoodImg> findByGoodIdAndType(Long goodId, Integer type) {
		GoodImg goodImg=new GoodImg();
		goodImg.setGoodId(goodId);
		goodImg.setType(type);
		return sqlSession.getMapper(GoodImgMapper.class).selectByGoodIdAndType(goodImg);
	}


}
