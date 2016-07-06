package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDistributionDto;

public interface ShopDistributionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopDistribution record);

    int insertSelective(ShopDistribution record);

    ShopDistribution selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopDistribution record);

    int updateByPrimaryKey(ShopDistribution record);

	ShopDistribution selectByIdAndShopNo(ShopDistribution distribution);

	List<ShopDistribution> findListByShopNo(String shopNo);

	Integer selectPsCount(ShopDistributionDto dto);

	List<ShopDistributionDto> selectPsPage(ShopDistributionDto dto);
}