package com.zjlp.face.web.server.user.shop.dao;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.dto.PickUpStoreDto;

public interface PickUpStoreDao {

	PickUpStore getByIdAndShopNo(PickUpStore store);

	void removeById(Long id);

	List<PickUpStore> findList(String shopNo);

	void editById(PickUpStore pickUpStore);

	Long add(PickUpStore pickUpStore);

	Integer getZtCount(PickUpStoreDto dto);

	List<PickUpStoreDto> findZtPage(PickUpStoreDto dto);

}
