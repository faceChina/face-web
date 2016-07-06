package com.zjlp.face.web.server.product.good.factory.element;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.dao.GoodPropertyDao;
import com.zjlp.face.web.server.product.good.dao.GoodSkuDao;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;

@Service("freeGoodPropertyAndSkuElementFactory")
public class FreeGoodPropertyAndSkuElementFactory implements ElementFactory<GoodParam> {
	
	private static final String GOOD_VERSION="V_0.0.1"; 
	
	/** 免费商品属性名称 */
	private static final String FREE_PROPERTY_NAME = "BASE"; 
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private GoodSkuDao goodSkuDao;
	@Autowired
	private GoodPropertyDao goodPropertyDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodParam create(GoodParam goodParam) throws GoodException {
		
		try {
			AssertUtil.notNull(goodParam, "参数商品属性为空");
			AssertUtil.notEmpty(goodParam.getSkuList(), "免费商品属性为空");
			List<GoodSku> goodSkus = goodParam.getSkuList();
			for (GoodSku goodSku : goodSkus) {
				// 新增商品属性
				GoodProperty goodProperty = this.add(goodParam.getId(), 
						goodSku.getAttributeName(), goodParam.getDate(), goodParam.getStatus());
				// 新增商品规格属性
				goodSku.setSkuProperties(GoodSku.sortDbProperties(goodProperty.getId().toString()));
				goodSku.setSkuPropertiesName(goodProperty.getName());
				this._addGoodSku(goodParam, goodSku);
			}
			return null;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodParam edit(GoodParam goodParam) throws GoodException {
		
		try {
			
			//参数验证
			AssertUtil.notNull(goodParam.getId(), "修改商品SKU失败，商品主键为空！");
			AssertUtil.notNull(goodParam.getClassificationId(), "修改商品SKU失败，商品主键为空！");
			//参数准备
			Long goodId = goodParam.getId();
			String version = String.valueOf(System.currentTimeMillis());
			Map<Long, GoodSku> compareMap = new HashMap<Long, GoodSku>();
			List<GoodSku> newList = goodParam.getSkuList();;
			AssertUtil.notNull(newList, "修改商品SKU失败，商品newList为空！");
			List<GoodSku> oldList = goodSkuDao.findGoodSkusByGoodId(goodId);
			AssertUtil.notNull(oldList, "修改商品SKU失败，商品oldList为空！");
			//对象对比
			for(GoodSku goodSku : newList){
				if (null == goodParam.getPreferentialPolicy()) {
					goodParam.setPreferentialPolicy(0);
				}
				goodSku.setPreferentialPolicy(goodParam.getPreferentialPolicy());
				goodSku.setName(goodParam.getName());
				if(null == goodSku.getId()){
					// 新增商品属性
					GoodProperty goodProperty = this.add(goodParam.getId(), 
							goodSku.getAttributeName(), goodParam.getDate(), goodParam.getStatus());
					// 新增商品规格属性
					goodSku.setSkuProperties(GoodSku.sortDbProperties(goodProperty.getId().toString()));
					goodSku.setSkuPropertiesName(goodProperty.getName());
					this._addGoodSku(goodParam, goodSku);
				}else{
					// 已存在数据放入Map
					compareMap.put(goodSku.getId(), goodSku);
				}
			}
			for(GoodSku goodSku : oldList){
				// 主键存在则修改，主键不存在则删除
				if(compareMap.containsKey(goodSku.getId())){
					// 主键存在，判断对象是否不等
					if(!goodSku.equals(compareMap.get(goodSku.getId()))){
						// 参数不相同则修改商品版本
						GoodSku editSku = compareMap.get(goodSku.getId());
						
						//修改商品规格属性
						editSku.setSkuPropertiesName(editSku.getAttributeName());
						editSku.setName(goodParam.getName());
						editSku.setVersion(version);
						editSku.setUpdateTime(goodParam.getDate());
						goodSkuDao.editGoodSku(editSku);
						
						//修改商品属性
						GoodProperty goodProperty = new GoodProperty();
						goodProperty.setId(_proccessSkuProperties(goodSku.getSkuProperties()));
						goodProperty.setName(editSku.getAttributeName());
						goodProperty.setPropValueName(editSku.getAttributeName());
						goodProperty.setUpdateTime(goodParam.getDate());
						goodPropertyDao.edit(goodProperty);
					}
				}else{
					
					// 逻辑删除商品属性
					goodPropertyDao.removeById(_proccessSkuProperties(goodSku.getSkuProperties()));
					// 逻辑删除商品规格属性
					goodSkuDao.removeIdById(goodSku.getId());
				}
			}
			if (_logger.isDebugEnabled()) {
				_logger.debug("edit FREE_GOOD success! goodId is "+goodParam.getId());
			}
			return null;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}
	
	private Long _proccessSkuProperties(String skuProperties){
		if(!StringUtils.hasLength(skuProperties)||skuProperties.length()==2){
			_logger.info("skuProperties为:"+skuProperties);
			return null;
		}
		StringBuilder idbuBuilder = new StringBuilder(skuProperties);
		if (skuProperties.startsWith(";")) {
			idbuBuilder.delete(0, 1);
		}
		if (skuProperties.endsWith(";")) {
			idbuBuilder.delete(idbuBuilder.length()-1, idbuBuilder.length());
		}
		return Long.valueOf(idbuBuilder.toString());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private GoodProperty add(Long goodId, String attributeName, Date date, Integer status){
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setGoodId(goodId);//设置商品ID
		goodProperty.setCreateTime(date);
		goodProperty.setUpdateTime(date);
		if (null == status) {
			goodProperty.setStatus(Constants.GOOD_STATUS_DEFAULT);
		} else {
			goodProperty.setStatus(status);
		}
		goodProperty.setPropValueCode(null);
		goodProperty.setPropValueName(attributeName);
		goodProperty.setPropName(FREE_PROPERTY_NAME);//设置属性名称
		goodProperty.setPropId(null);//设置属性ID
		goodProperty.setIsColorProp(false);
		goodProperty.setIsEnumProp(false);
		goodProperty.setIsInputProp(true);
		goodProperty.setIsKeyProp(false);
		goodProperty.setIsSalesProp(true);
		goodProperty.setIsStandard(false);
		goodProperty.setName(attributeName);
		goodPropertyDao.add(goodProperty);
	   	AssertUtil.notNull(goodProperty.getId(),"操作商品Property失败，商品ID:"+goodId);
	   	return goodProperty;
	}
	
	/**
	 * 新增Sku
	 * @Title: _addGoodSku 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId	
	 * @param goodSku
	 * @date 2015年3月25日 下午11:57:47  
	 * @author Administrator
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void _addGoodSku(GoodParam goodParam,GoodSku goodSku){
		AssertUtil.isTrue(null!=goodSku.getStock()&&0<goodSku.getStock(), "发布商品SKU失败，商品库存: "+goodSku.getStock());
		AssertUtil.isTrue(null!=goodSku.getSalesPrice()&&0<goodSku.getSalesPrice(), "发布商品SKU失败，商品销售价格: "+goodSku.getSalesPrice());
		goodSku.setGoodId(goodParam.getId());//设置商品ID
		goodSku.setName(goodParam.getName());//名称
		goodSku.setVersion(GOOD_VERSION);
		goodSku.setServiceId(goodParam.getServiceId());
		goodSku.setMarketPrice(goodParam.getMarketPrice());
		//商品相关优惠
		goodSku.setPreferentialPolicy(goodParam.getPreferentialPolicy());
		if (null == goodParam.getStatus()) {
			goodSku.setStatus(Constants.GOOD_STATUS_DEFAULT);
		} else {
			goodSku.setStatus(goodParam.getStatus());
		}
		goodSku.setCreateTime(goodParam.getDate());
		goodSku.setUpdateTime(goodParam.getDate());
		goodSkuDao.add(goodSku);
	   	AssertUtil.notNull(goodSku.getId(),"新增商品分类关联失败，商品ID:"+goodParam.getId());
	}
}
