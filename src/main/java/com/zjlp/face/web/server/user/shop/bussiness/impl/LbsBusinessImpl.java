package com.zjlp.face.web.server.user.shop.bussiness.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.LbsException;
import com.zjlp.face.web.server.user.shop.bussiness.LbsBusiness;
import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.shop.service.LbsService;
import com.zjlp.face.web.server.user.shop.service.VareaService;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

@Service
public class LbsBusinessImpl implements LbsBusiness {
	
	@Autowired
	private LbsService lbsService;
	@Autowired
	private VareaService vareaService;
	//地区编码
	private static final Integer CODE = 0;

	@Override
	public ShopLocationDto getShopLocationByShopNo(String shopNo) throws LbsException{
		try {
			AssertUtil.hasLength(shopNo, "店铺编号");
			//根据店铺编号查询店铺地理位置
			ShopLocation shopLocation = lbsService.getShopLocationByShopNo(shopNo);
			if (null == shopLocation) {
				return null;
			}
			ShopLocationDto dto = new ShopLocationDto(shopLocation);
			VaearDto vaear = vareaService.getVareaById(dto.getAreaCode());
			if (null == vaear) {
				vaear = vareaService.getCityById(dto.getAreaCode());
			}
			AssertUtil.notNull(vaear, "Result vaear can't be null.");
			dto.setPeCode(Integer.valueOf(vaear.getProvinceCode()));
			dto.setCityCode(Integer.valueOf(vaear.getCityCode()));
			if (null != vaear.getAreaCode()) {
				dto.setAreaCode(Integer.valueOf(vaear.getAreaCode()));
			} else {
				dto.setAreaCode(null);
			}
			dto.setAreaName(vaear.getAreaName());
			return dto;
		} catch (Exception e) {
			throw new LbsException(e);
		}
	}

	@Override
	public void saveShopLocation(ShopLocation shopLocation) throws LbsException{
		Date date = new Date();
		try {
			AssertUtil.notNull(shopLocation, "店铺地理位置信息");
			// 查询该店铺是否有地理位置
			ShopLocation sLocation = lbsService.getShopLocationByShopNo(shopLocation.getShopNo());
			if(null == sLocation) {
				//新增店铺地理位置
				shopLocation.setCreateTime(date);
				shopLocation.setUpdateTime(date);
				lbsService.addShopLocation(shopLocation);
				AssertUtil.notNull(shopLocation.getId(),  "店铺地理位置主键");
			} else {
				//编辑店铺地理位置
				shopLocation.setUpdateTime(date);
				lbsService.editShopLocation(shopLocation);
			}
		} catch (Exception e) {
			throw new LbsException(e);
		}

	}

	@Override
	public ShopLocationDto getShopLocationByShopNoForApp(String shopNo) throws LbsException{
		try {
			AssertUtil.hasLength(shopNo,  "店铺编号");
			//根据店铺编号查询店铺地理位置
			ShopLocationDto shopLocationDto = lbsService.getShopLocationDtoByShopNo(shopNo);
			if(null == shopLocationDto) {
				return shopLocationDto;
			}
			Integer areaCode = shopLocationDto.getAreaCode();
			if(null != shopLocationDto.getAreaCode() && !CODE.equals(shopLocationDto.getAreaCode())) {
				//查询地区信息
				VaearDto area = vareaService.getVareaById(areaCode);
				AssertUtil.notNull(area,  "地区");
				StringBuffer varea = new StringBuffer();
				varea.append(area.getProvinceName()).append(area.getCityName()).append(area.getAreaName()).append(shopLocationDto.getAddress());
				//设置店铺具体地理位置
				shopLocationDto.setArea(varea.toString());
				//设置店铺所在市名称
				shopLocationDto.setCityName(area.getCityName());
			}
			return shopLocationDto;
		} catch (Exception e) {
			throw new LbsException(e);
		}
	}
	
}
