package com.zjlp.face.web.server.product.good.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.good.dao.GoodShopTypeRelationDao;
import com.zjlp.face.web.server.product.good.dao.ShopTypeDao;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.good.service.ShopTypeService;
@Service
public class ShopTypeServiceImpl implements ShopTypeService{
	
	
	@Autowired
	private ShopTypeDao shopTypeDao;

	@Autowired
	private GoodShopTypeRelationDao goodShopTypeRelationDao;
	
	@Override
	public ShopType getShopTypeById(Long id) {
		return shopTypeDao.getById(id);
	}
	
	@Override
	public void addShopType(ShopType shopType) {
		if(shopType.getImgPath()==null || "".equals(shopType.getImgPath())){
			shopType.setImgPath("/resource/base/img/goods_sort.jpg");
		}
		shopTypeDao.add(shopType);
	}

	@Override
	public void editShopType(ShopType shopType) {
		shopTypeDao.edit(shopType);
	}

	@Override
	public void deleteShopTypeById(Long id) {
		shopTypeDao.delete(id);
	}
	
	@Override
	public List<GoodShopTypeRelation> findGoodShopTypeRelationByGoodId(
			Long goodId) {
		return goodShopTypeRelationDao.findGoodShopTypeRelationByGoodId(goodId);
	}

	@Override
	public List<GoodShopTypeRelation> findGoodShopTypeRelationByShopTypeId(
			Long shopTypeId) {
		return goodShopTypeRelationDao.findGoodShopTypeRelationByShopTypeId(shopTypeId);
	}

//	@Override
//	public Pagination<ShopType> findPageShopType(ShopType shopType,
//			Pagination<ShopType> pagination) {
//		Integer totalRow = shopTypeDao.getPageCount(shopType);
//		List<ShopType> datas = shopTypeDao.findPageShopType(shopType,pagination.getStart(),pagination.getPageSize());
//		pagination.setTotalRow(totalRow);
//		pagination.setDatas(datas);
//		return pagination;
//	}

	@Override
	public List<ShopTypeVo> findShopType(String shopNo, Long goodId) {
		return shopTypeDao.findShopType(shopNo, goodId);
	}

	@Override
	public void deleteAllGoodShopTypeByGoodId(Long goodId) {
		goodShopTypeRelationDao.deleteAllGoodShopTypeByGoodId(goodId);
	}


	@Override
	public Pagination<ShopTypeVo> findPageShopType(ShopTypeVo shopTypeVo,
			Pagination<ShopTypeVo> pagination) {
		Integer totalRow = shopTypeDao.getPageCount(shopTypeVo);
		List<ShopTypeVo> datas = shopTypeDao.findPageShopType(shopTypeVo,pagination.getStart(),pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public List<ShopTypeVo> findShopType(ShopTypeVo shopTypeVo) {
		return shopTypeDao.findShopTypeList(shopTypeVo);
	}

	@Override
	public List<ShopTypeVo> findAppointShopType(ShopTypeVo shopTypeVo) {
		return shopTypeDao.findAppointShopTypeList(shopTypeVo);
	}

}
