package com.zjlp.face.web.server.user.shop.producer.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.ShopException;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.factory.ShopFactory;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.shop.service.ShopService;

@Service
public class ShopProducerImpl implements ShopProducer{

	@Autowired
	private ShopService shopService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private ShopFactory freeShopFactory;

	@Override
	public Shop getShopByNo(String shopNo) throws ShopException {
		try {
			AssertUtil.hasLength(shopNo, "Param[shopNo] can not be null.");
			Shop shop = shopService.getShopByNo(shopNo);
			AssertUtil.notNull(shop, "Shop[no={}] is not exists.", "", shopNo);
			return shop;
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}

	@Override
	public Long getShopUserIdByNo(String shopNo) throws ShopException {
		AssertUtil.hasLength(shopNo, "Param[shopNo] can not be null.");
		//查询店铺所属
		Shop shop = this.getShopByNo(shopNo);
		AssertUtil.notNull(shop,"找不到该店铺,shopNo :"+shopNo);
		AssertUtil.notNull(shop.getUserId(),"该店铺未配置所属人,shopNo :"+shop.getUserId());
		return shop.getUserId();
	}

	@Override
	public boolean updateShopInfo(Shop shop) {
		AssertUtil.notNull(shop, "参数shop为空");
		try {
			String logoPath = _saveShopLogo(shop);
			shop.setShopLogoUrl(logoPath);
			this.shopService.editShop(shop);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * 保存店铺logo
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "static-access" })
	private String _saveShopLogo(Shop shop) {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
        if(null != shop.getShopLogoUrl()) {
        	 //上传图片到TFS
        	String zoomSizes = PropertiesUtil.getContexrtParam("shopLogoFile");
        	AssertUtil.hasLength(zoomSizes, "imgConfig.properties还未配置shopLogoFile字段");
            FileBizParamDto dto = new FileBizParamDto();
            dto.setImgData(shop.getShopLogoUrl());
            dto.setZoomSizes(zoomSizes);
            dto.setUserId(shop.getUserId());
            dto.setShopNo(shop.getNo());
            dto.setTableName("SHOP");
            dto.setTableId(shop.getNo());
            dto.setCode(ImageConstants.SHOP_LOGO_FILE);
            dto.setFileLabel(1);
            list.add(dto);
            
            String flag = imageService.addOrEdit(list);
            
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
            if(StringUtils.isNotBlank(fbpDto.get(0).getImgData())){
            	return fbpDto.get(0).getImgData();
            }
        }
        return null;
}

	@Override
	public boolean isOuterSupplier(String shopNo) throws ShopException {
		try {
			Shop shop = this.getShopByNo(shopNo);
			return ShopDto.PROXY_TYPE_OUTER.equals(shop.getProxyType());
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}

	@Override
	public boolean isInnerSupplier(String shopNo) throws ShopException {
		try {
			Shop shop = this.getShopByNo(shopNo);
			return ShopDto.PROXY_TYPE_INNER.equals(shop.getProxyType());
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}

	@Override
	public Shop getShopByAuthCode(String code) throws ShopException {
		try {
			AssertUtil.hasLength(code, "Param[code] can not be null.");
			Shop shop = shopService.getShopByAutoCode(code);
			AssertUtil.notNull(shop, "Shop[code={}] is not exists.", "输入的授权码错误", code);
			return shop;
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}

	@Override
	public boolean isFreeShop(String shopNo) throws ShopException {
		try {
			AssertUtil.hasLength(shopNo, "Param[shopNo] can not be null.");
			Shop shop = this.getShopByNo(shopNo);
			return ShopDto.FREE.equals(shop.getIsFree());
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}

	@Override
	public Shop addFreeShopForApp(ShopDto shopDto) throws ShopException {
		
		try {
			AssertUtil.notNull(shopDto.getUserId(), "参数免费店铺用户id为空");
			// 根据用户id查询免费店铺
			Shop shop = shopService.getShopByUserId(shopDto.getUserId());
			if(null != shop) {
				return shop;
			}
			// 生成免费店铺
			AssertUtil.notNull(shopDto.getName(), "参数免费店铺名称不能为空");
			shopDto.setType(Constants.SHOP_SC_TYPE);
			shopDto.setIsFree(ShopDto.FREE);
			// 新增免费店铺
			shop = freeShopFactory.addShop(shopDto);
			return shop;
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}

	@Override
	public Shop getShopByUserId(Long userId) throws ShopException {
		try {
			AssertUtil.notNull(userId, "参数userId为空");
			List<Shop> list = shopService.findShopListByUserId(userId);
			//AssertUtil.isTrue(null != list && list.size() == 1, "店铺数据不合法");
			return list.get(0);
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}
}
