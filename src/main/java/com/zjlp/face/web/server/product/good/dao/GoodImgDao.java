package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodImg;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;

public interface GoodImgDao {
	
	void add(GoodImg goodImg);
	
	void edit(GoodImg goodImg);
	
	void delete(Long id);
	
	void deleteByGoodId(Long goodId);
	
	GoodImg getById(Long id);
	
	GoodImg getFirstImg(Long goodId);
	
	List<GoodImg> findByGoodIdAndType(Long goodId, Integer type);
	
	List<GoodImgVo> findZoomByGoodIdAndType(Long goodId, Integer type);
	
	
	

}
