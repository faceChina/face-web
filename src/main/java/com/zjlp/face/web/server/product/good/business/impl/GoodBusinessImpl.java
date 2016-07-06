package com.zjlp.face.web.server.product.good.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.exception.log.EC;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchGoodRelationService;
import com.zjlp.face.web.server.product.good.business.ClassificationBusiness;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodDetailVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodProfitVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.RecommendGoodVo;
import com.zjlp.face.web.server.product.good.factory.GoodFactory;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.server.product.good.service.ClassificationService;
import com.zjlp.face.web.server.product.good.service.GoodImgService;
import com.zjlp.face.web.server.product.good.service.GoodPropertyService;
import com.zjlp.face.web.server.product.good.service.GoodService;
import com.zjlp.face.web.server.product.good.service.GoodSkuService;
import com.zjlp.face.web.server.product.good.service.PropService;
import com.zjlp.face.web.server.trade.order.domain.vo.RspSelectedSkuVo;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.favorites.service.FavoritesService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
@Service
public class GoodBusinessImpl implements GoodBusiness {
	
	@Autowired
	private GoodFactory defaultGoodFactoryNew;
	@Autowired
	private GoodFactory defaultGoodFactory;
	@Autowired
	private GoodService goodService;
	@Autowired
	private GoodSkuService goodSkuService;
	@Autowired
	private GoodPropertyService goodPropertyService;
	@Autowired
	private ClassificationService classificationService;
	@Autowired
	private PropService propService;
	@Autowired
	private GoodImgService goodImgService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private GoodFactory freeGoodFactory;
	@Autowired
	private SubbranchProducer subbranchProducer;
	@Autowired
	private SubbranchGoodRelationService subbranchGoodRelationService;
	@Autowired
	private FavoritesService savoritesService;
	@Autowired
	private ClassificationBusiness classificationBusiness;
	
	
	@Override
	public Pagination<GoodVo> searchPageGood(GoodVo goodVo,
			Pagination<GoodVo> pagination) throws GoodException {
		try {
			//验证参数
			AssertUtil.notNull(goodVo, EC.prtNull("goodVo"));
			AssertUtil.notNull(goodVo.getShopNo(), EC.prtNull("goodVo.getShopNo()"),"找不到商品");
			
			//店铺验证
			Shop shop = shopService.getShopByNo(goodVo.getShopNo());
			AssertUtil.notNull(shop, "您访问的店铺不存在！","您访问的店铺不存在！");
			
			//查询
			if (null == goodVo.getStatus()) {
				goodVo.setStatus(Constants.GOOD_STATUS_DEFAULT);
			}
			return goodService.searchPageGood(goodVo,pagination);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "GoodException" })
	public GoodDetailVo getGoodDetailById(Long goodId) throws GoodException {
		//验证参数
		AssertUtil.notNull(goodId, "商品ID为空，查询商品详情失败！","找不到商品");
		//实例化对象
		GoodDetailVo goodDetailVo = new GoodDetailVo();
		//查询商品信息
		Good good = goodService.getGoodById(goodId);
		AssertUtil.notNull(good, "找不到商品！","找不到商品");
		AssertUtil.notTrue(Constants.GOOD_STATUS_UNDERCARRIAGE.equals(good.getStatus()), "商品已下架！","商品已下架");
		AssertUtil.notTrue(Constants.GOOD_STATUS_DELETE.equals(good.getStatus()), "找不到商品！","商品不存在 ");
		goodDetailVo.setGood(good);
		//查询商品属性（销售属性）
		GoodProperty salesGoodProperty = GoodProperty.getSalesProp();
		salesGoodProperty.setGoodId(goodId);
		List<GoodProperty> salesPropList = goodPropertyService.findGoodSalesProperties(salesGoodProperty);
		if (null != salesPropList && !salesPropList.isEmpty()) {
			/**
			 * 新增排序功能
			 */
//			goodDetailVo.setSalesPropList(salesPropList);
			Map<String, List<GoodProperty>> goodPropertyMap = classificationBusiness.findAllPropNameByClassificationId(good.getClassificationId());
			goodDetailVo.setSalesPropListToSort(salesPropList,goodPropertyMap);
			/**
			 * 
			 */
			
			// 免费商品查询商品规格属性
			//if(Constants.CLASSIFICATION_ID_FREE_GOOD.equals(good.getClassificationId())) {
			//在所有情况下获取 sku
				List<GoodSku> skuList = goodSkuService.findGoodSkusByGoodId(goodId);
				goodDetailVo.setSkuList(skuList);
			//}
		}else {
			//查询商品属性（非销售属性：显示使用）
			GoodProperty gnotKeyoodProperty = GoodProperty.getInputNoKeyProp();
			gnotKeyoodProperty.setGoodId(goodId);
			List<GoodProperty> notKeyPropList = goodPropertyService.findInputNotKeyGoodProperties(gnotKeyoodProperty);
			if (null != notKeyPropList && !notKeyPropList.isEmpty()) {
				goodDetailVo.setNotKeyPropList(notKeyPropList);
			}
			List<GoodSku> skuList = goodSkuService.findGoodSkusByGoodId(goodId);
			goodDetailVo.setSkuList(skuList);
		}
		//查询商品图片信息
		List<GoodImgVo> goodImgs = goodImgService.findZoomByGoodIdAndType(goodId, 1);
		AssertUtil.notEmpty(goodImgs, "找不到商品图片！","找不到商品");
		goodDetailVo.setGoodImgs(goodImgs);
        final Long id=good.getId();
        /** 单独线程修改浏览量*/
        exec.execute(new Thread() {
			public void run() {
				goodService.editBrowerTimeById(id);
			}
		});
		return goodDetailVo;
	}
	
	@Override
	public GoodDetailVo getSellerGoodDetailById(Long goodId) throws GoodException {
		//验证参数
		AssertUtil.notNull(goodId, "商品ID为空，查询商品详情失败！","找不到商品");
		//实例化对象
		GoodDetailVo goodDetailVo = new GoodDetailVo();
		//查询商品信息
		Good good = goodService.getGoodById(goodId);
		AssertUtil.notNull(good, "找不到商品！","找不到商品");
		AssertUtil.notTrue(Constants.GOOD_STATUS_DELETE.equals(good.getStatus()), "找不到商品！","商品不存在 ");
		goodDetailVo.setGood(good);
		//查询商品属性（销售属性）
		GoodProperty salesGoodProperty = GoodProperty.getSalesProp();
		salesGoodProperty.setGoodId(goodId);
		List<GoodProperty> salesPropList = goodPropertyService.findGoodSalesProperties(salesGoodProperty);
		if (null != salesPropList && !salesPropList.isEmpty()) {
			goodDetailVo.setSalesPropList(salesPropList);
			// 免费商品查询商品规格属性
			//if(Constants.CLASSIFICATION_ID_FREE_GOOD.equals(good.getClassificationId())) {
			//在所有情况下获取 sku
				List<GoodSku> skuList = goodSkuService.findGoodSkusByGoodId(goodId);
				goodDetailVo.setSkuList(skuList);
			//}
		}else {
			//查询商品属性（非销售属性：显示使用）
			GoodProperty gnotKeyoodProperty = GoodProperty.getInputNoKeyProp();
			gnotKeyoodProperty.setGoodId(goodId);
			List<GoodProperty> notKeyPropList = goodPropertyService.findInputNotKeyGoodProperties(gnotKeyoodProperty);
			if (null != notKeyPropList && !notKeyPropList.isEmpty()) {
				goodDetailVo.setNotKeyPropList(notKeyPropList);
			}
			List<GoodSku> skuList = goodSkuService.findGoodSkusByGoodId(goodId);
			goodDetailVo.setSkuList(skuList);
		}
		//查询商品图片信息
		List<GoodImgVo> goodImgs = goodImgService.findZoomByGoodIdAndType(goodId, 1);
		AssertUtil.notEmpty(goodImgs, "找不到商品图片！","找不到商品");
		goodDetailVo.setGoodImgs(goodImgs);
        final Long id=good.getId();
        /** 单独线程修改浏览量*/
        exec.execute(new Thread() {
			public void run() {
				goodService.editBrowerTimeById(id);
			}
		});
		return goodDetailVo;
	}
	private ExecutorService exec=Executors.newSingleThreadExecutor();
	
	@Override
	public GoodDetailVo getGoodSalesDetailById(Long goodId)
			throws GoodException {
		//验证参数
		AssertUtil.notNull(goodId, "商品ID为空，查询商品详情失败！","找不到商品");
		//实例化对象
		GoodDetailVo goodDetailVo = new GoodDetailVo();
		//查询商品信息
		Good good = goodService.getGoodById(goodId);
		AssertUtil.notNull(good, "找不到商品！","找不到商品");
		goodDetailVo.setGood(good);
		//查询商品属性（销售属性）
		GoodProperty salesGoodProperty = GoodProperty.getSalesProp();
		salesGoodProperty.setGoodId(goodId);
		List<GoodProperty> salesPropList = goodPropertyService.findGoodProperties(salesGoodProperty);
		if (null != salesPropList && !salesPropList.isEmpty()) {
			goodDetailVo.setSalesPropList(salesPropList);
		}else {
			//查询商品属性（非销售属性：显示使用）
			GoodProperty gnotKeyoodProperty = GoodProperty.getInputNoKeyProp();
			gnotKeyoodProperty.setGoodId(goodId);
			gnotKeyoodProperty.setIsSalesProp(false);//非销售属性
			gnotKeyoodProperty.setIsKeyProp(false);//非关键属性
			List<GoodProperty> notKeyPropList = goodPropertyService.findGoodProperties(gnotKeyoodProperty);
			if (null != notKeyPropList && !notKeyPropList.isEmpty()) {
				goodDetailVo.setNotKeyPropList(notKeyPropList);
				List<GoodSku> skuList = goodSkuService.findGoodSkusByGoodId(goodId);
				goodDetailVo.setSkuList(skuList);
			}
		}
		return goodDetailVo;
	}


	@Override
	public void addGood(GoodParam goodParam) throws GoodException {
		defaultGoodFactory.addGood(goodParam);
	}

	@Override
	public void addGoodNew(GoodParam goodParam) throws GoodException {
		defaultGoodFactoryNew.addGood(goodParam);
	}

	@Override
	public void editGoodNew(GoodParam goodParam) throws GoodException {
		defaultGoodFactoryNew.editGood(goodParam);
		
	}

	@Override
	public void editGood(GoodParam goodParam) throws GoodException {
		defaultGoodFactory.editGood(goodParam);
		
	}
	
	@Override
	public List<GoodSku> findGoodSkuByGoodId(Long goodId) throws GoodException {
		try {
			AssertUtil.notNull(goodId, "商品ID为空，查询商品SKU失败！");
			return goodSkuService.findGoodSkusByGoodId(goodId);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	public RspSelectedSkuVo selectGoodskuByGoodIdAndPrprerty(Long goodId, String skuProperties) {
		try {
			//验证参数
			AssertUtil.notNull(goodId, "商品ID为空，查询商品SKU失败！");
			AssertUtil.hasLength(skuProperties, "sku属性字符串为空，，查询商品SKU失败！");
			Good g=goodService.getGoodById(goodId);
			GoodSku goodSku=null;
			if(g.getClassificationId()==14){
				/** 手机端发布商品*/
				goodSku=goodSkuService.getById(Long.valueOf(skuProperties));
			}else{
				goodSku =  goodSkuService.selectGoodskuByGoodIdAndPrprerty(goodId,skuProperties);
			}
			AssertUtil.notNull(goodSku, "找不到最小库存单位量");
			RspSelectedSkuVo rssv = new RspSelectedSkuVo();
			rssv.setId(goodSku.getId());
			rssv.setPicturePath(goodSku.getPicturePath());
			rssv.setSalesPrice(goodSku.getSalesPrice());
			rssv.setStock(goodSku.getStock());
			return rssv;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	public GoodVo getGoodEditById(Long goodId) throws GoodException {
		try {
			AssertUtil.notNull(goodId, "商品ID为空，查询商品失败！");
			Good good = goodService.getGoodById(goodId); 
			AssertUtil.notNull(good, "商品为空，查询商品失败！");
			AssertUtil.notNull(good.getClassificationId(), "商品为空，查询商品失败！");
			propService.findPropsByClassificationId(good.getClassificationId());
			return (GoodVo) good;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	public List<GoodProperty> findGoodProperties(GoodProperty goodProperty)
			throws GoodException {
		try {
			AssertUtil.notNull(goodProperty, "参数对象为空，查询商品属性失败！");
			AssertUtil.notNull(goodProperty.getGoodId(), "商品ID为空，查询商品属性失败！");
			return goodPropertyService.findGoodProperties(goodProperty);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<GoodProperty> findInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty) throws GoodException {
		try {
			AssertUtil.notNull(gnotKeyoodProperty, "参数对象为空，查询商品属性失败！");
			AssertUtil.notNull(gnotKeyoodProperty.getGoodId(), "商品ID为空，查询商品属性失败！");
			return goodPropertyService.findInputNotKeyGoodProperties(gnotKeyoodProperty);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}
	
	//(TODO)
	
	@Override
	public List<GoodProperty> findSalesInputKeyGoodProperties(
			GoodProperty salesInputProperty) {
		try {
			AssertUtil.notNull(salesInputProperty, "参数对象为空，查询商品属性失败！");
			AssertUtil.notNull(salesInputProperty.getGoodId(), "商品ID为空，查询商品属性失败！");
			return goodPropertyService.findSalesInputKeyGoodProperties(salesInputProperty);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "GoodException" })
	public List<Good> upShelvesAllGood(List<String> ids) throws GoodException {
		try {
			List<Good> goodList = new ArrayList<Good>();
			for (String id : ids) {
				Good good = goodService.upShelvesGood(Long.valueOf(id));
				goodList.add(good);
			}
			return goodList;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "GoodException" })
	public List<Good> downShelvesAllGood(List<String> ids, Long userId) {
		try {
			List<Good> goodList = new ArrayList<Good>();
			for (String id : ids) {
				Good good = goodService.downShelvesGood(Long.valueOf(id));
				goodList.add(good);
			}
			return goodList;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "GoodException" })
	public void removeGood(Long userId, Long goodId) throws GoodException {
		try {
	        Assert.notNull(goodId, "商品id不存在");
			goodService.removeGood(goodId);
		} catch (Exception e) {
			throw new GoodException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "GoodException" })
	public void removeAllGood(Long userId, List<String> ids)
			throws GoodException {
		try {
			for (String id : ids) {
				this.removeGood(userId, Long.valueOf(id));
			}
		} catch (Exception e) {
			throw new GoodException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "GoodException" })
	public void sortGood(Long upId, Long downId) throws GoodException{
		Assert.notNull(upId, "商品upId不存在");
		Assert.notNull(downId, "商品downId不存在");
        try{
            Good upGood = goodService.getGoodById(upId);
            Good downGood = goodService.getGoodById(downId);
            Integer tempSort = upGood.getSort();
            upGood.setSort(downGood.getSort());
            downGood.setSort(tempSort);
            goodService.edit(upGood);
            goodService.editGood(downGood);
        }catch(Exception e){
            throw new GoodException(e);
        }
	}

	@Override
	public Good getGoodById(Long goodId)throws GoodException {
        try{
			Assert.notNull(goodId, "商品goodId不存在");
			return goodService.getGoodById(goodId);
	    }catch(Exception e){
	        throw new GoodException(e);
	    }
	}

	@Override
	public List<GoodImgVo> findGoodImgByGoodIdAndType(Long goodId, Integer type) throws GoodException {
        try{
			Assert.notNull(goodId, "商品Id不存在");
			return goodImgService.findZoomByGoodIdAndType(goodId,type);
	    }catch(Exception e){
	        throw new GoodException(e);
	    }
	}

	@Override
	public String findGoodSkuJsonByGoodId(Long goodId) throws GoodException {
		List<GoodSku> skuList=  goodSkuService.findGoodSkusByGoodId(goodId);
		if (null == skuList || skuList.isEmpty()) {
			return null;
		}
		for (GoodSku goodSku : skuList) {
			String skuSkuProperties = goodSku.getSkuProperties();
			if (StringUtils.isNotBlank(skuSkuProperties)) {
				String skuProperties = null;
				if (skuSkuProperties.startsWith(";")&&skuSkuProperties.endsWith(";")) {
					skuProperties = skuSkuProperties.substring(1,skuSkuProperties.length()-1);
				}else {
					skuProperties = skuSkuProperties;
				}
				String[] goodPropertyIds = skuProperties.split(";");
				for (String goodPropertyId : goodPropertyIds) {
					AssertUtil.hasLength(goodPropertyId, "商品goodPropertyId不存在,查询商品信息失败");
					GoodProperty goodProperty = goodPropertyService.getGoodPropertyById(Long.valueOf(goodPropertyId));
					AssertUtil.notNull(goodProperty, "商品goodProperty对象不存在,查询商品信息失败");
					if (goodProperty.getIsColorProp()) {
						goodSku.setColorId(String.valueOf(goodProperty.getPropValueId()));
						goodSku.setColorName(goodProperty.getPropValueAlias());
						continue;
					}
					if (goodProperty.getIsEnumProp()) {
						goodSku.setSizeId(String.valueOf(goodProperty.getPropValueId()));
						goodSku.setSizeName(goodProperty.getPropValueAlias());
						continue;
					}
				}
				goodSku.setPriceYuan(CalculateUtils.converPennytoYuan(goodSku.getSalesPrice()));
				goodSku.setSkuId(String.valueOf(goodSku.getId()));
			}
		}
		Collections.sort(skuList,new Comparator<GoodSku>() {
			@Override
			public int compare(GoodSku o1, GoodSku o2) {
				if (StringUtils.isBlank(o1.getColorId()) || StringUtils.isBlank(o2.getColorId()) ) {
					return 0;
				}
				return Long.valueOf(o1.getColorId()).compareTo(Long.valueOf(o2.getColorId()));
			}
		});
		return JsonUtil.fromObject(skuList, false, GoodParam.BASE_JSON_ITEM).toString();
	}
	

	@Override
	public void spreadIndex(Long goodId) throws GoodException {
		Assert.notNull(goodId,"商品主键为空，推广失败！");
		try {
			Good checkGood = goodService.getGoodById(goodId);
			AssertUtil.notNull(checkGood,"商品为空，推广失败");
			Good good = new Good();
			good.setId(checkGood.getId());
			//未推广的商品推广至首页，已推广的商品取消推广
			int isSpreadIndex = 1 == checkGood.getIsSpreadIndex().intValue()?0:1;
			good.setIsSpreadIndex(isSpreadIndex);
			good.setUpdateTime(new Date());
			goodService.editGood(good);
		} catch (Exception e) {
			throw new GoodException(e);
		}
	}

	@Override
	public GoodVo getGoodNum(GoodVo goodVo) throws GoodException{
		
		try {
			AssertUtil.notNull(goodVo.getShopNo(), "店铺编号为空");
			// 查询商品总数与最新商品个数
			goodVo.setStatus(Constants.GOOD_STATUS_DEFAULT);
			return goodService.getGoodNum(goodVo);
		} catch (Exception e) {
			throw new GoodException(e);
		}
	}

	@Override
	public Integer countGoodNum(String shopNo, Integer status){
		AssertUtil.notNull(shopNo, "店铺编号为空");
		GoodVo goodVo=new GoodVo();
		goodVo.setShopNo(shopNo);
		goodVo.setStatus(status);
		return goodService.countGoodVoNum(goodVo);
	}

	@Override
	public List<Good> findGoodsByShopNo(String shopNo) {
		AssertUtil.hasLength(shopNo, "参数[shopNo]为空，查询失败！");
		Good good = new Good();
		good.setShopNo(shopNo);
		good.setStatus(Constants.VALID);
		return goodService.findGoodsList(good);
	}

	@Override
	public List<Good> findGoodByIds(List<String> goodIdList) {
		return goodService.findGoodByIds(goodIdList);
	}

	@Override
	public GoodSku getGoodSkuById(Long id) {
		return goodSkuService.getById(id);
	}

	@Override
	public List<GoodSkuVo> findGoodSkuByAppointmentIdAndShopTypeId(Long appointmentId, Long shopTypeId) {
		return goodSkuService.findGoodSkuByAppointmentIdAndShopTypeId(appointmentId,shopTypeId);
	}






	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "GoodException" })
	public void upShelvesGood(Long id) {
		try {
			AssertUtil.notNull(id, "参数商品id为空");
			// 商品上架
			goodService.upShelvesGood(Long.valueOf(id));
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "GoodException" })
	public void downShelvesGood(Long id) throws GoodException{
		try {
			AssertUtil.notNull(id, "参数商品id为空");
			// 商品下架
			goodService.downShelvesGood(Long.valueOf(id));
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	public void addGoodForFree(GoodParam goodParam)  throws GoodException {
		// 免费店铺新增商品
		freeGoodFactory.addGood(goodParam);
	}

	@Override
	public void editGoodForFree(GoodParam goodParam) {
		// 免费店铺编辑商品
		freeGoodFactory.editGood(goodParam);;
	}

	@Override
	public Pagination<GoodVo> findGoodVoPageForWap(String shopNo, GoodVo goodVo, Pagination<GoodVo> pagination) {
		return goodService.findGoodVoPageForWap(shopNo,goodVo, pagination);
	}

	@Override
	public Integer countGoodVoNum(GoodVo goodVo) {
		return goodService.countGoodVoNum(goodVo);
	}

	@Override
	public Integer getOnSellCount(String shopNo) {
		AssertUtil.hasLength(shopNo, "参数shopNo不能为空");
		return goodService.getCountByStatus(shopNo, Constants.GOOD_STATUS_DEFAULT);
	}

	@Override
	public Integer getUnShelveCount(String shopNo) {
		AssertUtil.hasLength(shopNo, "参数shopNo不能为空");
		return goodService.getCountByStatus(shopNo, Constants.GOOD_STATUS_UNDERCARRIAGE);
	}

	@Override
	public GoodProperty getGoodPropertyById(Long id) {
		return goodPropertyService.getGoodPropertyById(id);
	}

	@Override
	public List<GoodSku> findGoodSkuListByGoodId(Long id) {
		return goodSkuService.findGoodSkuImgListByGoodId(id);
	}

	@Override
	public List<GoodProperty> findGoodPropertyListByGoodId(Long goodId) {
		return goodPropertyService.findGoodPropertyListByGoodId(goodId);
	}

	@Override
	public Pagination<RecommendGoodVo> getRecommendGood(Long userId,String goodName,
			Pagination<RecommendGoodVo> pagination) throws GoodException {
		try {
			AssertUtil.notNull(userId, "参数userId为空");
			AssertUtil.notNull(goodName, "参数goodName为空");
			return goodService.getRecommendGood(userId, goodName, pagination);
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	@Override
	public Pagination<GoodProfitVo> findSubbranchGoods(Long subbranchId, Long userId, Integer sortBy, String searchKey,
			Pagination<GoodProfitVo> pagination) throws GoodException {
		try {
			AssertUtil.notNull(subbranchId, "subbranchId不能为空！");
			AssertUtil.notNull(pagination, "pagination不能为空！");
			AssertUtil.notNull(userId, "userId不能为空！");
			Pagination<GoodProfitVo> page = this.goodService.findSubbranchGoods(subbranchId, userId, sortBy, searchKey, pagination);
			for (GoodProfitVo currrent : page.getDatas()) {
				/**被收藏数**/
				List<Favorites> favorites = this.savoritesService.findFavoritesGoods(1, currrent.getId().toString());
				currrent.setFavoritesQuantity(CollectionUtils.isNotEmpty(favorites) ? favorites.size() : 0);
				/** 市场价 **/
				currrent.setMarketPriceStr(CalculateUtils.converFenToYuan(currrent.getMarketPrice()));
				/** 上级佣金积 **/
				Double preProfitD = this._computeProfit(subbranchId, currrent.getId());
				/** 转Long **/
				Long preProfitL = Math.round(preProfitD * 100);
				/** 计算预计佣金 **/
				Long totalPrice = currrent.getMarketPrice() * preProfitL * currrent.getProfit() / 10000;
				currrent.setPreProfitStr(CalculateUtils.converFenToYuan(totalPrice));
				/** 显示佣金积 **/
				currrent.setProfitDou(preProfitD * (Double.parseDouble(currrent.getProfit().toString())));
			}
			return page;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(), e);
		}
	}

	/**
	 * @Title: _computeProfit
	 * @Description: (计算代理体系中BF2以上佣金率积)
	 * @param subbranchId
	 * @param goodId
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月2日 下午4:41:47
	 */
	private Double _computeProfit(Long subbranchId, Long goodId) {
		Double totalRate = 1D;
		/** 查出代理体系中基本佣金率 **/
		List<Subbranch> subList = this.subbranchProducer.findSubbranchList(subbranchId);
		if (CollectionUtils.isNotEmpty(subList) && subList.size() > 1) {
			/** 移除最下级 ,因为最下级佣金已经查出 **/
			subList.remove(subList.size() - 1);
			/** 查出P给BF1设的单品佣金 **/
			SubbranchGoodRelation subbranchGoodRelation = new SubbranchGoodRelation();
			subbranchGoodRelation.setSubbranchId(subList.get(0).getId());
			subbranchGoodRelation.setGoodId(goodId);
			Integer singleRate = this.subbranchGoodRelationService.getSubbranchGoodRate(subbranchGoodRelation);
			/** 如果设置单品佣金 **/
			if (singleRate != null) {
				/** 基础佣金不使用 **/
				subList.remove(0);
				totalRate *= (Double.parseDouble(singleRate.toString().toString()) / 100);
			}
			/** 否则使用基本佣金 **/
			for (Subbranch sub : subList) {
				totalRate *= (Double.parseDouble(sub.getProfit().toString()) / 100);
			}
		}
		return totalRate;
	}

}
