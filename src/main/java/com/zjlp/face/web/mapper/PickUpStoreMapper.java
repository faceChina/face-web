package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.dto.PickUpStoreDto;

public interface PickUpStoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PickUpStore record);

    int insertSelective(PickUpStore record);

    PickUpStore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PickUpStore record);

    int updateByPrimaryKey(PickUpStore record);

	PickUpStore selectByIdAndShopNo(PickUpStore store);

	List<PickUpStore> selectListByShopNo(String shopNo);

	Integer selectZtCount(PickUpStoreDto dto);

	List<PickUpStoreDto> selectZtPage(PickUpStoreDto dto);
}