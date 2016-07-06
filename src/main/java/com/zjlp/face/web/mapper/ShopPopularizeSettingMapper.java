package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;

public interface ShopPopularizeSettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopPopularizeSetting record);

    int insertSelective(ShopPopularizeSetting record);

    ShopPopularizeSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopPopularizeSetting record);

    int updateByPrimaryKey(ShopPopularizeSetting record);

	List<ShopPopularizeSetting> selectByParam(ShopPopularizeSetting param);

	ShopPopularizeSetting selectShopPopularizeSetting(
			ShopPopularizeSetting setting);
}