package com.zjlp.face.web.server.product.good.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.good.dao.GoodImgDao;
import com.zjlp.face.web.server.product.good.domain.GoodImg;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;
import com.zjlp.face.web.server.product.good.service.GoodImgService;
@Service
public class GoodImgServiceImpl implements GoodImgService {
	@Autowired
	private GoodImgDao goodImaDao;

	
	@Override
	public void addOrEdit(GoodImg goodImg) {
		if (null == goodImg.getId()) {
			goodImaDao.add(goodImg);
		}else {
			goodImaDao.edit(goodImg);
		}
	}

	@Override
	public void deleteById(Long id) {
		goodImaDao.delete(id);
	}

	@Override
	public void deleteByGoodId(Long goodId) {
		goodImaDao.deleteByGoodId(goodId);
	}

	@Override
	public GoodImg getById(Long id) {
		return goodImaDao.getById(id);
	}

	@Override
	public GoodImg getFirst(Long goodId) {
		return goodImaDao.getFirstImg(goodId);
	}

	@Override
	public List<GoodImg> findByGoodIdAndType(Long goodId, Integer type) {
		return goodImaDao.findByGoodIdAndType(goodId, type);
	}

	@Override
	public List<GoodImgVo> findZoomByGoodIdAndType(Long goodId, Integer type) {
		return goodImaDao.findZoomByGoodIdAndType(goodId, type);
	}



}
