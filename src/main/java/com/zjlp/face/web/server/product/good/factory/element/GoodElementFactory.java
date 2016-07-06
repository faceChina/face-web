package com.zjlp.face.web.server.product.good.factory.element;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.GenerateCode;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.dao.GoodDao;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.server.product.good.service.PropService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.util.Logs;

@Service("goodElementFactory")
@Transactional(propagation = Propagation.REQUIRED)
public class GoodElementFactory implements ElementFactory<GoodParam> {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	private static final Long CLASSIFICATION_ID_FREE = 14L;
	
	public static final String  GOOD_WAP_URL ="/wap/{shopNo}/any/item/{goodId}.htm";
	
	@Autowired
	private GoodDao goodDao;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private PropService propService;
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodParam create(GoodParam goodParam) throws GoodException {
		try {
			//参数验证
			AssertUtil.hasLength(goodParam.getShopNo(), "发布商品失败，店铺号为空！");
			AssertUtil.notNull(goodParam.getClassificationId(), "发布商品失败，类目主键为空！");
			AssertUtil.hasLength(goodParam.getName(), "发布商品失败，商品名称为空！");
			AssertUtil.notNull(goodParam.getMarketPrice(), "发布商品失败，市场价格为空！");
			if(!goodParam.getClassificationId().equals(Constants.LEIMU_MENHU)){
				AssertUtil.notEmpty(goodParam.getSkuList(), "发布商品失败，商品skuList为空！");
			}
//			AssertUtil.notNull(goodParam.getLogisticsMode(), "发布商品失败，商品LogisticsMode为空！");
			//设置商品基本信息
			goodParam.setProductNumber(GenerateCode.generateWord(20));//商品编码
			/** 查询是否具备销售属性，销售属性可以组成价格和库存单位量 */
			boolean isSalesProp = propService.hasSalesProp(goodParam.getClassificationId());
			goodParam.setHasSalesProp(isSalesProp);
			if (isSalesProp || CLASSIFICATION_ID_FREE.equals(goodParam.getClassificationId())) {
				goodParam.setInventory(GoodSku.calculateSkuInventory(goodParam.getSkuList()));//初始化商品库存
				goodParam.setSalesPrice(GoodSku.getMinSkuPrice(goodParam.getSkuList()));//初始化商品价格
			}else {
				if (null == goodParam.getInventory()) {
					goodParam.setInventory(0L);
				}
				AssertUtil.notNull(goodParam.getSalesPrice(), "发布商品失败，市场价格为空！");
			}
            if (null == goodParam.getPreferentialPolicy()) {
				goodParam.setPreferentialPolicy(0);//初始化优惠信息
			}
            if (null == goodParam.getIsOffline()) {
				goodParam.setIsOffline(1);//默认为线上商品
			}
            if (null == goodParam.getIsSpreadIndex()) {
				goodParam.setIsSpreadIndex(0);//商品默认不推荐至首页
			}
            if (null == goodParam.getLogisticsMode() ) {
            	goodParam.setLogisticsMode(0);
			}
            if(2==goodParam.getLogisticsMode() && goodParam.getPostFee()==null){
            	goodParam.setPostFee(0l);
            }
            Shop shop = shopProducer.getShopByNo(goodParam.getShopNo());
            goodParam.setShopName(shop.getName());//店铺名称
			goodParam.setNick("");//(TODO 未设置卖家昵称,一阶段暂不设置)
            goodParam.setPicUrl("");//商品主图片
			goodParam.setDetailWapUrl("");//设置商品Wap访问地址
			if(null==goodParam.getStatus()){
				goodParam.setStatus(Constants.GOOD_STATUS_DEFAULT);//初始化商品状态
			} 
			goodParam.setBrowerTime(0L);// 初始化浏览次数
			goodParam.setSalesVolume(0l);// 初始化销量
			goodParam.setListTime(goodParam.getDate());//初始化商品上架时间
			goodParam.setCreateTime(goodParam.getDate());//初始化创建时间
			goodParam.setUpdateTime(goodParam.getDate());//初始化修改时间
			goodDao.add(goodParam);
            Good good = new Good();
            good.setId(goodParam.getId());
            good.setSort(goodParam.getId().intValue());
            String detailWapUrl = GOOD_WAP_URL
            					.replace("{shopNo}", goodParam.getShopNo())
            					.replace("{goodId}", String.valueOf(goodParam.getId()));
            good.setDetailWapUrl(detailWapUrl);
            goodDao.edit(good);
    		if (_logger.isDebugEnabled()) {
    			_logger.debug("create GOOD success! goodId is "+goodParam.getId());
    		}
            return goodParam;
		} catch (Exception e) {
			Logs.error(e);
			throw new GoodException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodParam edit(GoodParam goodParam) throws GoodException {
		try {
			//参数验证
			AssertUtil.hasLength(goodParam.getShopNo(), "编辑商品失败，店铺号为空！");
			AssertUtil.notNull(goodParam.getClassificationId(), "编辑商品失败，类目主键为空！");
			AssertUtil.hasLength(goodParam.getName(), "编辑商品失败，商品名称为空！");
			AssertUtil.notNull(goodParam.getMarketPrice(), "编辑商品失败，市场价格为空！");
			if(!goodParam.getClassificationId().equals(Constants.LEIMU_MENHU)){
				AssertUtil.notEmpty(goodParam.getSkuList(), "发布商品失败，商品skuList为空！");
			}
			//设置库存和价格
			goodParam.setInventory(GoodSku.calculateSkuInventory(goodParam.getSkuList()));//初始化商品库存
			goodParam.setSalesPrice(GoodSku.getMinSkuPrice(goodParam.getSkuList()));//初始化商品价格
			goodParam.setUpdateTime(goodParam.getDate());
			goodDao.editGood(goodParam);
	   		if (_logger.isDebugEnabled()) {
    			_logger.debug("edit GOOD success! goodId is "+goodParam.getId());
    		}
			return goodParam;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}

}
