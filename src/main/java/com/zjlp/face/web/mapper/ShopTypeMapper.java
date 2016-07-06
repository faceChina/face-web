package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;

public interface ShopTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopType record);

    int insertSelective(ShopType record);

    ShopType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopType record);

    int updateByPrimaryKey(ShopType record);

	Integer selectPageCount(Map<String, Object> map);

	List<ShopTypeVo> selectPageShopType(Map<String, Object> map);

	List<ShopTypeVo> selectListByShopNoAndGoodId(Map<String, Object> map);

	List<ShopTypeVo> findShopTypeList(ShopTypeVo shopTypeVo);

	List<ShopTypeVo> findAppointShopTypeList(ShopTypeVo shopTypeVo);
}