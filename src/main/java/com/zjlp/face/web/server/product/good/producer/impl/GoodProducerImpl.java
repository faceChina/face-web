package com.zjlp.face.web.server.product.good.producer.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;
import com.zjlp.face.web.server.product.good.service.ClassificationService;
import com.zjlp.face.web.server.product.good.service.GoodImgService;
import com.zjlp.face.web.server.product.good.service.GoodPropertyService;
import com.zjlp.face.web.server.product.good.service.GoodService;
import com.zjlp.face.web.server.product.good.service.GoodShopTypeRelationService;
import com.zjlp.face.web.server.product.good.service.GoodSkuService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GoodProducerImpl implements GoodProducer {

	@Autowired
	private GoodService goodService;
	@Autowired
	private GoodSkuService goodSkuService;
	@Autowired
	private GoodPropertyService goodPropertyService;
	@Autowired
	private GoodImgService goodImgService;
	@Autowired
	private GoodShopTypeRelationService goodShopTypeRelationService;
	
	@Autowired
	private ClassificationService classificationService;

	@Override
	public Good getGoodById(Long id) {
		AssertUtil.notNull(id, "参数[id]为空，调用失败！");
		return goodService.getGoodById(id);
	}

	@Override
	public List<GoodSku> findGoodSkusByGoodId(Long goodId) {
		AssertUtil.notNull(goodId, "参数[goodId]为空，调用失败！");
		return goodSkuService.findGoodSkusByGoodId(goodId);
	}

	@Override
	public List<GoodProperty> findGoodProperties(GoodProperty goodProperty) {
		AssertUtil.notNull(goodProperty, "参数对象为空，调用失败！");
		AssertUtil.notNull(goodProperty.getGoodId(), "参数[goodId]为空，调用失败！");
		return goodPropertyService.findGoodProperties(goodProperty);
	}

	@Override
	public GoodSku getGoodSkuById(Long skuId) {
		AssertUtil.notNull(skuId, "参数[skuId]为空，调用失败！");
		return goodSkuService.getById(skuId);
	}
	
	@Override
	public GoodSku getGoodSkuStatelessById(Long skuId) {
		AssertUtil.notNull(skuId, "参数[skuId]为空，调用失败！");
		return goodSkuService.getById(skuId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "Exception" })
	public void editGoodSkuStock(Long skuId, Long stock) {
		AssertUtil.notNull(skuId, "参数[skuId]为空，扣取库存失败！");
		AssertUtil.notNull(stock, "参数[stock]为空，扣取库存失败！");
		AssertUtil.isTrue(0L < stock, "参数[stock]小于0，扣取库存失败！");
		Date date = new Date();
		GoodSku dbsGoodSku = goodSkuService.getById(skuId);
		AssertUtil.notNull(dbsGoodSku, "商品不存在", "商品已下架");
		Long balanceStock = CalculateUtils.getBalanceStocks(
				dbsGoodSku.getStock(), stock);
		AssertUtil.isTrue(
				null != balanceStock && 0L <= balanceStock.longValue(),
				"商品库存不足，扣取库存失败！", "商品库存不足");
		GoodSku goodSku = new GoodSku();
		goodSku.setId(skuId);
		goodSku.setStock(balanceStock);
		if (0L == balanceStock.longValue()) {
			// 库存为0自动下架
			goodSku.setStatus(Constants.GOOD_STATUS_UNDERCARRIAGE);
		}
		goodSku.setUpdateTime(date);
		goodSkuService.edit(goodSku);
		AssertUtil.notNull(dbsGoodSku.getGoodId(), "找不到商品，扣取库存失败 skuId："
				+ skuId, "商品已下架");

		Good dbGood = goodService.getGoodById(dbsGoodSku.getGoodId());
		AssertUtil.notNull(dbGood, "商品已下架购买失败", "商品已下架");
		Long goodBalanceStock = CalculateUtils.getBalanceStocks(
				dbGood.getInventory(), stock);
		AssertUtil.isTrue(
				null != goodBalanceStock && 0L <= goodBalanceStock.longValue(),
				"商品库存不足，扣取库存失败！", "商品库存不足");
		Good good = new Good();
		good.setId(dbsGoodSku.getGoodId());
		good.setInventory(goodBalanceStock);
		good.setSalesVolume(CalculateUtils.getSum(dbGood.getSalesVolume(),
				stock));
		if (0L == goodBalanceStock.longValue()) {
			// 库存为0自动下架
			good.setStatus(Constants.GOOD_STATUS_UNDERCARRIAGE);
		}
		good.setUpdateTime(date);
		goodService.edit(good);
	}

	@Override
	public List<Good> findGoodsByShopNo(String shopNo) {
		AssertUtil.hasLength(shopNo, "参数[shopNo]为空，查询失败！");
		Good good = new Good();
		good.setShopNo(shopNo);
		return goodService.findGoodsList(good);
	}

	@Override
	public Classification getClassificationById(Long classificationId) {
		return classificationService.getClassificationById(classificationId);
	}


	@Override
	public List<GoodProperty> findGoodSalesProperties(
			GoodProperty salesGoodProperty) {
		return goodPropertyService.findGoodSalesProperties(salesGoodProperty);
	}

	@Override
	public List<GoodProperty> findInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty) {
		return goodPropertyService.findInputNotKeyGoodProperties(gnotKeyoodProperty);
	}

	@Override
	public List<GoodImgVo> findZoomByGoodId(Long goodId) {
		return goodImgService.findZoomByGoodIdAndType(goodId, 1);
	}
	

	@Override
	public void editGood(Good browerGood) {
		goodService.edit(browerGood);
	}

	@Override
	public void addGoodShopTypeRelation(GoodShopTypeRelation relation) {
		goodShopTypeRelationService.add(relation);
	}
	
	@Override
	public GoodSku selectGoodSkuByGoodIdAndPrprerty(Long goodId,
			String skuProperties) {
		return goodSkuService.selectGoodskuByGoodIdAndPrprerty(goodId, skuProperties);
	}

	@Override
	public List<GoodSku> findGoodSkuImgListByGoodId(Long goodId) {
		return goodSkuService.findGoodSkuImgListByGoodId(goodId);
	}

}
