package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

public interface ShopLocationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopLocation record);

    int insertSelective(ShopLocation record);

    ShopLocation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopLocation record);

    int updateByPrimaryKey(ShopLocation record);

	ShopLocation selectByShopNo(String shopNo);

	ShopLocationDto selectShopLocationDtoByShopNo(String shopNo);

	List<ShopLocationDto> selectShopGisPage(UserGisVo userGisVo);
}