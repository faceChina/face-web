package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;

public interface ShopTypeDao {
	
	void add(ShopType shopTyp);
	
	void edit(ShopType shopTyp);
	
	ShopType getById(Long id);
	
	void delete(Long id);

	Integer getPageCount(ShopType shopType);

	List<ShopTypeVo> findPageShopType(ShopType shopType,Integer start,Integer pageSize);

	List<ShopTypeVo> findShopType(String shopNo, Long goodId);

	List<ShopTypeVo> findShopTypeList(ShopTypeVo shopTypeVo);

	List<ShopTypeVo> findAppointShopTypeList(ShopTypeVo shopTypeVo);

}
