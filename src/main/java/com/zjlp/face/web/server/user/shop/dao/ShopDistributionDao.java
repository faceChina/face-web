package com.zjlp.face.web.server.user.shop.dao;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDistributionDto;

public interface ShopDistributionDao {

	void removeById(Long id);

	ShopDistribution getById(Long id);

	ShopDistribution getByIdAndShopNo(ShopDistribution distribution);

	List<ShopDistribution> findList(String shopNo);

	void editById(ShopDistribution distribution);

	Long add(ShopDistribution distribution);

	Integer getPsCount(ShopDistributionDto dto);

	List<ShopDistributionDto> findPsPage(ShopDistributionDto dto);

}
