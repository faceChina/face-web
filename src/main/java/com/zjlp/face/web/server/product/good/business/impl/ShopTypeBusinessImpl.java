package com.zjlp.face.web.server.product.good.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.ImgTemplateVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.good.domain.vo.TempImg;
import com.zjlp.face.web.server.product.good.service.GoodService;
import com.zjlp.face.web.server.product.good.service.ShopTypeService;
@Service
public class ShopTypeBusinessImpl implements ShopTypeBusiness {

	@Autowired
	private ShopTypeService shopTypeService;
	@Autowired
	private GoodService goodService;

	@Override
	public void addShopType(ShopType shopType) throws GoodException {
		try {
			Date date = new Date();
			shopType.setCreateTime(date);
			shopType.setUpdateTime(date);
			shopTypeService.addShopType(shopType);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}
	
	@Override
	public void editShopType(ShopType shopType) throws GoodException {
		try {
			Date date = new Date();
			shopType.setUpdateTime(date);
			shopTypeService.editShopType(shopType);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}
	
	@Override
	public ShopType getShopTypeById(Long id) {
		try {
			return shopTypeService.getShopTypeById(id);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}

	@Override
	public void deleteShopTypeById(Long id) throws GoodException {
		try {
			shopTypeService.deleteShopTypeById(id);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}

//	@Override
//	public Pagination<ShopTypeVo> findPageShopType(ShopTypeVo shopTypeVo,
//			Pagination<ShopTypeVo> pagination) throws GoodException {
//		try {
//			AssertUtil.notNull(shopTypeVo, "店铺对象为空，查询店铺商品分类失败！");
//			AssertUtil.hasLength(shopTypeVo.getShopNo(), "店铺号为空，查询店铺商品分类失败！");
//			return shopTypeService.findPageShopType(shopTypeVo,pagination);
//		} catch (Exception e) {
//			throw new GoodException(e.getMessage(),e);
//		}
//	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "ShopTypeException" })
	public void sortShopType(Long upId, Long downId) throws GoodException {
		try {
			AssertUtil.notNull(upId, "upId为空，排序失败！");
			AssertUtil.notNull(downId, "downId为空，排序失败！");
			ShopType upShopType = shopTypeService.getShopTypeById(upId);
			AssertUtil.notNull(upShopType, "upShopType为空，排序失败！");
            ShopType downShopType = shopTypeService.getShopTypeById(downId);
    		AssertUtil.notNull(downShopType, "downShopType为空，排序失败！");
    		Long tempSort;
    		if(null==upShopType.getSort()){
    			tempSort = upId;
    		}else{
    			tempSort = upShopType.getSort();
	        }
    		
    		if(null==downShopType.getSort()){
    			upShopType.setSort(downId);
    		}
    		else{
    			upShopType.setSort(downShopType.getSort());
            }
            downShopType.setSort(tempSort);
            shopTypeService.editShopType(upShopType);
            shopTypeService.editShopType(downShopType);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}

	@Override
	public List<ShopTypeVo> findShopType(String shopNo) throws GoodException {
		return shopTypeService.findShopType(shopNo,null);
	}

	@Override
	public List<ShopTypeVo> findShopType(String shopNo, Long goodId)
			throws GoodException {
		return shopTypeService.findShopType(shopNo,goodId);
	}


	@Override
	public Pagination<ShopTypeVo> findPageShopType(ShopTypeVo shopTypeVo,
			Pagination<ShopTypeVo> pagination) throws GoodException {
		try{
            return shopTypeService.findPageShopType(shopTypeVo, pagination);
        }catch(Exception e){
            throw new GoodException(e);
        }
	}

	@Override
	public Pagination<ImgTemplateVo> findShopTypeTempImg(String type,
			String color, Pagination<ImgTemplateVo> pagination)
			throws GoodException {
		try {
			StringBuilder sb = new StringBuilder(TempImg.SHOPTYPE);
			if (!StringUtils.isBlank(type)) {
				sb.append(TempImg.FLORD_LINE).append(type);
			}
			if (!StringUtils.isBlank(color)) {
				sb.append(TempImg.FLORD_LINE).append(type).append(TempImg.SPLIT_LINE).append(color);
			}
			//获取指定分类的数据 
			Map<String, ImgTemplateVo> map = TempImg.getTempMapByType(sb.toString());
			if (null == map || map.isEmpty()) {
				pagination.setTotalRow(0);
				pagination.setDatas(null);
				return pagination;
			}
			//分页数据设置
			ImgTemplateVo[] array = new ImgTemplateVo[pagination.getTotalRow()];
			array = map.values().toArray(array);
			pagination.setPageSize(30);
			int[] showPageSize={30};
			pagination.setShowPageSize(showPageSize);
			pagination.setTotalRow(map.values().size());
			int end = ((pagination.getStart() + pagination.getPageSize()) > map.values().size()) ? map.values().size() : (pagination.getStart() + pagination.getPageSize());
			array = Arrays.copyOfRange(array, pagination.getStart(), end);
			pagination.setDatas(Arrays.asList(array));
			return pagination;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}

	@Override
	public List<ShopTypeVo> findShopTypeListAndGoodByShopNo(String shopNo,
			int shopTypeNum, int goodNum) {
		
        List<ShopTypeVo> list = shopTypeService.findShopType(shopNo, null);
		List<ShopTypeVo> shopTypeList = new ArrayList<ShopTypeVo>();
        if(null != list){
			for(int i = 0; shopTypeList.size() < shopTypeNum && i < list.size(); i++){
				ShopTypeVo vo = (ShopTypeVo) list.get(i);
				Good good=new Good();
				good.setShopNo(shopNo);
                List<GoodVo> goodList = goodService.findGoodByShopTypeId(good, vo.getId(), goodNum);
				if (null != goodList && goodList.size() != 0) {
					vo.setGoodList(goodList);
					shopTypeList.add(vo);
				}
            }
        }
		return shopTypeList;
	}

	@Override
	public List<ShopTypeVo> findShopType(ShopTypeVo shopTypeVo) {
		return shopTypeService.findShopType(shopTypeVo);
	}

	@Override
	public List<ShopTypeVo> findAppointShopType(ShopTypeVo shopTypeVo) {
		return shopTypeService.findAppointShopType(shopTypeVo);
	}

	@Override
	public List<ShopTypeVo> findShopTypeListAndGoodForWap(String shopNo, int shopTypeNum, int goodNum) {
		List<ShopTypeVo> list = shopTypeService.findShopType(shopNo, null);
		List<ShopTypeVo> shopTypeList = new ArrayList<ShopTypeVo>();
        if(null != list){
			for(int i = 0; shopTypeList.size() < shopTypeNum && i < list.size(); i++){
				ShopTypeVo vo = (ShopTypeVo) list.get(i);
                Good good=new Good();
                good.setShopNo(shopNo);
				List<GoodVo> goodList = goodService.findGoodVoForWapByShopTypeId(good, vo.getId(), goodNum);
				if (null != goodList && goodList.size() != 0) {
					vo.setGoodList(goodList);
					shopTypeList.add(vo);
				}
            }
        }
		return shopTypeList;
	}

	@Override
	public Long addShopTypeToApp(String name, String shopNo) throws GoodException {
		try {
			if(StringUtils.isBlank(name)){
				throw new GoodException("商品分类名称不能为空");
			}
			if(StringUtils.isBlank(shopNo)){
				throw new GoodException("店铺NO不能为空");
			}
			
			ShopType shopType = new ShopType();
			shopType.setImgPath("/resource/base/img/goods_sort.jpg");
			shopType.setFontColor("333333");
			
			shopType.setName(name);
			shopType.setServiceId(Constants.SERVICE_ID_COMMON);
			shopType.setShopNo(shopNo);
			
			Date date = new Date();
			shopType.setCreateTime(date);
			shopType.setUpdateTime(date);
			
			shopTypeService.addShopType(shopType);
			
			AssertUtil.notNull(shopType.getId(),"新增商品分类失败");
			
			return shopType.getId();
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}
}
