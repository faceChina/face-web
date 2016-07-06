package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssGoodVo;
import com.zjlp.face.web.appvo.AssPagination;
import com.zjlp.face.web.appvo.DeliveryWayVo;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.business.ClassificationBusiness;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.business.GoodShopTypeRelationBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodImg;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodProfitVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.RecommendGoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.LogisticsBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateVo;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.util.AssConstantsUtil;

@Controller
@RequestMapping({ "/assistant/ass/good/"})
public class GoodAppCtl extends BaseCtl {

    private Log _logger = LogFactory.getLog(GoodAppCtl.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    
    private static final Integer UP_SHELE = 1;//商品上架状态
    private static final Integer DOWN_SHELE = 3;//商品下架状态
    
    
    public static final String[] LIST_JSON_FIELDS = {"curPage","start","pageSize","totalRow","datas.id","datas.shopNo","datas.inventory","datas.goodInfopath","datas.goodInfopathForSeller",
    	"datas.name","datas.path","datas.salesPriceStr","datas.classificationId","datas.isPcGoods"};
    
    
    public static final String[] EDIT_JSON_FIELDS ={"good.id","good.shopNo","good.no","good.classificationId","good.name","good.marketPrice","good.salesPrice","good.inventory",
    	"good.postFee","good.logisticsMode","good.preferentialPolicy","good.goodContent",
    	"goodImgs.id","goodImgs.goodId","goodImgs.url","goodImgs.position",
    	"classification.id","classification.name",
    	"itemJson",
    	"notKeyProperties.id","notKeyProperties.goodId","notKeyProperties.propName","notKeyProperties.propValueName",
    	"color.id","color.propValueAlias","color.propValueId",
    	"shopTypeList.id","shopTypeList.name","shopTypeList.goodId","shopTypeList.count",
    	"goodSkus.id","goodSkus.stock","goodSkus.skuPropertiesName","goodSkus.salesPrice", "deliveryWayVo.express", "deliveryWayVo.pickUp", "deliveryWayVo.shopDelivery"};
    public static final String[] BASE_JSON_COLOR_SIZE = {"name","code"};
    
    public static final String[] BASE_JSON_FILE_SIZE = {"id","goodId","name","path"};
    
    public static final String[] BASE_JSON_CUSTOMATTRIBUTE_SIZE = {"id","goodId","name","value"};
    
    public static final String[] BASE_JSON_SHOPTYPE = {"id","name","count"};
    
	public static final String[] SUB_GOODS_JSON_FIELDS = { "curPage", "start", "pageSize", "totalRow", "datas.id",
			"datas.picUrl", "datas.name", "datas.marketPriceStr", "datas.salesVolume", "datas.favoritesQuantity",
			"datas.preProfitStr", "datas.profitDou", "datas.goodInfopath", "datas.goodInfopathForSeller" };

	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private GoodBusiness goodBusiness;
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private ClassificationBusiness classificationBusiness;
	@Autowired
	private GoodShopTypeRelationBusiness goodShopTypeRelationBusiness;
	@Autowired
	private LogisticsBusiness logisticsBusiness;
	
	
	  
    /**
     * 商品列表（ 官网、商城）
    * @Title: listGood
    * @Description: 商品列表（ 官网、商城）
    * @param goodDto
    * @param pagination
    * @return String    返回类型
    * @author wxn  
    * @date 2015年1月26日 下午1:53:05
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public String listGood(@RequestBody JSONObject jsonObj, Pagination<GoodVo> pagination){
      try {
		//检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String shopNo = jsonObj.optString("shopNo");
			
			if (null == shopNo || "".equals(shopNo) ){
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			GoodVo goodVo = new GoodVo();
			goodVo.setShopNo(shopNo);
			Integer states = jsonObj.optInt("states");
			 if (null == states) {
		        	goodVo.setStatus(Constants.ISDEFAULT);
			}else{
				goodVo.setStatus(states);
			}
			 String isPcGoods = jsonObj.optString("isPcGoods");
			 goodVo.setIsPcGoods(null); 
			 if(!StringUtils.isBlank(isPcGoods)&&("1".equals(isPcGoods)||"0".equals(isPcGoods))){
					 goodVo.setIsPcGoods(isPcGoods);
			 }
		    // 查询商品
	        pagination = goodBusiness.searchPageGood(goodVo,pagination);
	        
	        List<AssGoodVo> goodVoList = new ArrayList<AssGoodVo>();
		    for (GoodVo good : pagination.getDatas()) {
		    	AssGoodVo  assgoodVo = new AssGoodVo();
		    	assgoodVo.setId(good.getId());
		    	assgoodVo.setShopNo(good.getShopNo());
		    	assgoodVo.setInventory(good.getInventory());
		    	assgoodVo.setName(good.getName());
				assgoodVo.setPath(good.getPicUrl());
		    	assgoodVo.setSalesPrice(good.getSalesPrice());
		    	assgoodVo.setClassificationId(good.getClassificationId());
		    	assgoodVo.setIsPcGoods(good.getIsPcGoods());
		    	goodVoList.add(assgoodVo);
			}
			//重新封装Pagination对象
			AssPagination<AssGoodVo> newpag = new AssPagination<AssGoodVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(goodVoList);
			 return outSucceed(newpag, true, LIST_JSON_FIELDS);
		} catch (NumberFormatException e) {
			_logger.error("查询商品列表失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (GoodException e) {
			_logger.error("查询商品列表失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("查询商品列表失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
    }
    /**
    * 初始化商品新增
    * @Title: initAdd
    * @Description: 初始化商品新增
    * @return
    * @return String    返回类型
    * @author wxn  
    * @date 2015年1月26日 下午2:53:26
     */
    @RequestMapping(value = "initAdd")
    @ResponseBody
    public String initAdd(@RequestBody JSONObject jsonObj){
		try {
			String shopNo = jsonObj.optString("shopNo");
			if (null == shopNo || "".equals(shopNo) ){
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
	    	//商品类别查询
	        List<ShopTypeVo> shopTypeList  = shopTypeBusiness.findShopType(shopNo);
			 return outSucceed(shopTypeList, true,BASE_JSON_SHOPTYPE);
		} catch (Exception e) {
			_logger.error("初始化商品新增失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
    }
    
    /**
     * 保存商品
    * @Title: publishGood
    * @Description: 保存商品
    * @return String    返回类型
    * @author wxn  
    * @date 2015年1月26日 下午4:25:36
     */
	@RequestMapping(value = "add")
	@ResponseBody
    public String publishGood(@RequestBody JSONObject jsonObj ){
    	try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			GoodParam goodParam = new GoodParam();
			//普通商品servierID为1，预约商品2,代理商品3
			goodParam.setServiceId(1L);
			goodParam.setUserId(super.getUserId());
			boolean flag = this._getGoodData(jsonObj, goodParam);
			if (!flag) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
//			this._getGoodPropertyData(jsonObj, goodParam);
			this._getGoodImgsData(jsonObj, goodParam);
			this._getShopTypeData(jsonObj, goodParam);
			goodParam.setHasProtocol(false);
			boolean hasSalesProp = classificationBusiness.hasSalesProp(goodParam.getClassificationId());
	    	goodParam.setHasSalesProp(hasSalesProp);
//	    	if(!hasSalesProp){
//				/** 设置非关键属性类型Sku参数 */
//				goodParam.setNokeySku(goodParam);
//			}
//	    	goodBusiness.addGood(goodParam);
			Object sku_arr_obj = jsonObj.get("freeGoodSku");
			if (sku_arr_obj != null && StringUtils.isNotBlank(sku_arr_obj.toString())) {
				goodParam.setFreeGoodSku(jsonObj.getString("freeGoodSku"));
				goodParam.setFreeSku(goodParam);
				goodBusiness.addGoodForFree(goodParam);
			} 
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("保存商品失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
    }
    
    /**
     * 初始化商品编辑
    * @Title: initEdit
    * @Description: 初始化商品编辑
    * @return String    返回类型
    * @author wxn  
    * @date 2015年1月27日 上午9:23:29
     */
    @RequestMapping(value = "initedit")
    @ResponseBody
    public String initEdit(@RequestBody JSONObject jsonObj){
    	
       	try {
			//检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long goodId = jsonObj.getLong("goodId");
			if (null == goodId) {
				return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
			}
			String shopNo = jsonObj.getString("shopNo");
			if (null == shopNo || "".equals(shopNo) ){
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
	    	//商品查询
	    	Good good = goodBusiness.getGoodById(goodId);
	    	
	    	dataMap.put("good",good);
	    	//查询商品图片 
			List<GoodImgVo> goodImgs = goodBusiness.findGoodImgByGoodIdAndType(goodId,1);
			 
			dataMap.put("goodImgs", goodImgs);
		   	Classification classification = classificationBusiness.getClassificationById(good.getClassificationId());
			AssertUtil.notNull(classification, "类目不存在，操作失败！");
			dataMap.put("classification", classification);
	    	//判断是类目是否为协议商品类目
	    	boolean hasProtocol= classificationBusiness.hasProtocolClassification(good.getClassificationId());
			if (hasProtocol) {
				// 协议类商品
				return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
			}else {
				boolean hasSalesProp = classificationBusiness.hasSalesProp(good.getClassificationId());
				if (!hasSalesProp) {
					// 普通商品==》其他商品
					GoodProperty notKeyProperty = GoodProperty.getInputNoKeyProp();
					notKeyProperty.setGoodId(goodId);
					// 查询商品非关键属性
					List<GoodProperty> notKeyProperties = goodBusiness.findInputNotKeyGoodProperties(notKeyProperty);
					dataMap.put("notKeyProperties", notKeyProperties);
					// 查询商品SKU
					List<GoodSku> goodSkus = goodBusiness.findGoodSkuByGoodId(goodId);
					dataMap.put("goodSkus", goodSkus);
				} else {
					// 查询商品SKU
					String itemJson = goodBusiness.findGoodSkuJsonByGoodId(goodId);
					dataMap.put("itemJson", itemJson);
				}
//				}
		    	//店铺物流信息查询
//		        this._logisticsInit(shopNo,model);
				/*******************配送方式模板查询start********************/
				DeliveryWayVo deliveryWayVo = this.initDelivery(shopNo);
				dataMap.put("deliveryWayVo", deliveryWayVo);
				/*******************配送方式模板查询end**********************/
		    	//商品类别查询
		        List<ShopTypeVo> shopTypeList  = shopTypeBusiness.findShopType(shopNo,goodId);
		        dataMap.put("shopTypeList", shopTypeList);
		        return outSucceed(dataMap, true, EDIT_JSON_FIELDS);
			}
		} catch (Exception e) {
			_logger.error("初始化商品编辑失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
    }
    
    /**
     * 
     * @Title: initDelivery 
     * @Description: 初始化配送模板
     * @param shopNo
     * @return
     * @date 2015年7月30日 上午11:28:35  
     * @author cbc
     */
    private DeliveryWayVo initDelivery(String shopNo) {
    	Shop shop = shopBusiness.getShopByNo(shopNo);
		Integer integer = shop.getLogisticsTypes();
		ShopVo shopVo = new ShopVo();
		shopVo.setLogisticsTypes(integer);
		
		Integer kdType = shopVo.getKdType();//快递
		Integer psType = shopVo.getPsType();//店铺配送
		Integer ztType = shopVo.getZtType();
		DeliveryTemplateVo deliveryTemplateVo = null;
		List<ShopDistribution> distributionList  = null;
		List<PickUpStore> pickUpList = null;
		
		if (kdType.intValue() > 0) {
			deliveryTemplateVo = logisticsBusiness.getDeliveryTemplateVoByShopNo(shopNo);
		}
		if (psType.intValue() > 0) {
			distributionList = logisticsBusiness.findShopDistributionByShopNo(shopNo);
		}
		if (ztType.intValue() > 0) {
			pickUpList = logisticsBusiness.findPickUpByShopNo(shopNo);
		}
		
		DeliveryWayVo deliveryWayVo = new DeliveryWayVo(deliveryTemplateVo, distributionList, pickUpList);
		return deliveryWayVo;
    }
    
    /**
    * 保存编辑
    * @Title: edit
    * @Description: 保存编辑
    * @return String    返回类型
    * @author wxn  
    * @date 2015年1月27日 上午9:23:47
     */
    @RequestMapping(value = "edit")
    @ResponseBody
    public String edit(@RequestBody JSONObject jsonObj ){
    	try {
			
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			GoodParam goodParam = new GoodParam();
			goodParam.setUserId(super.getUserId());
			boolean flag = this._getGoodData(jsonObj, goodParam);
			if (!flag) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			
			this._getGoodImgsData(jsonObj, goodParam);
			this._getShopTypeData(jsonObj, goodParam);
	    	Long goodId = goodParam.getId();
	        // 1.后台验证
	        AssertUtil.notNull(goodId,"商品ID为空，修改失败");
	    	Good good = goodBusiness.getGoodById(goodId);
	    	AssertUtil.notNull(good,"商品为空，修改失败");
	    	Classification classification = classificationBusiness.getClassificationById(good.getClassificationId());
			AssertUtil.notNull(classification, "类目不存在，操作失败！");
		 	boolean hasProtocol= classificationBusiness.hasProtocolClassification(good.getClassificationId());
	    	boolean hasSalesProp = classificationBusiness.hasSalesProp(classification.getId());
	    	goodParam.setHasSalesProp(hasSalesProp);
	    	goodParam.setHasProtocol(hasProtocol);
			if (!hasSalesProp) {
//				 this._getGoodPropertyData(jsonObj, goodParam);
//				 /** 设置非关键属性类型Sku参数 */
//				 goodParam.setNokeySku(goodParam);
				Object sku_arr_obj = jsonObj.get("freeGoodSku");
				if (sku_arr_obj != null && StringUtils.isNotBlank(sku_arr_obj.toString())) {
					goodParam.setFreeGoodSku(jsonObj.getString("freeGoodSku"));
					goodParam.setFreeSku(goodParam);
				}
				goodBusiness.editGoodForFree(goodParam);
			} else {
				/*GoodProperty sales = GoodProperty.getSalesProp();
				sales.setGoodId(goodId);
				List<GoodProperty> salesList = goodBusiness.findGoodProperties(sales);*/
				Map<String, GoodProperty> goodPropertyMap = new HashMap<String, GoodProperty>();
				//查询商品属性（颜色）
				GoodProperty color =GoodProperty.getColorProp();
				color.setGoodId(goodId);
				List<GoodProperty> colorList = goodBusiness.findGoodProperties(color);
				for (int i = 0; i < colorList.size(); i++) {
					goodPropertyMap.put("color"+i, colorList.get(i));
				}
				//查询商品尺寸
				GoodProperty size = GoodProperty.getEnumProp();
				size.setGoodId(goodId);
				List<GoodProperty> sizeList = goodBusiness.findGoodProperties(size);
				
				for (int i = 0; i < sizeList.size(); i++) {
					goodPropertyMap.put("size"+i, sizeList.get(i));
				}
				
				goodParam.setGoodPropertyMap(goodPropertyMap);
				this._getSkuData(jsonObj, goodParam);
				goodBusiness.editGood(goodParam);
			}
		return outSucceedByNoData();
		} catch (GoodException e) {
			_logger.error("保存商品编辑失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("保存商品编辑失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
    }

    /**
     * 删除商品
    * @Title: remove
    * @Description: 删除商品
    * @return String    返回类型
    * @author wxn  
    * @date 2015年1月27日 上午9:53:18
     */
    @RequestMapping(value = "remove")
    @ResponseBody
    public String remove(@RequestBody JSONObject jsonObj){
		try {
			//检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long goodId = jsonObj.getLong("goodId");
			if (null == goodId) {
				return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
			}
			goodBusiness.removeGood(getUserId(), goodId);
			return outSucceedByNoData();
		} catch (GoodException e) {
			_logger.error("删除商品失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("删除商品失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
    }
    /**
     * 商品批量下架
    * @Title: downShelvesGood
    * @Description:  商品批量下架
    * @return String    返回类型
    * @author wxn  
    * @date 2015年1月27日 上午9:57:46
     */
    @RequestMapping(value = "downShelves", method = RequestMethod.POST)
    @ResponseBody
    public String downShelvesGood(@RequestBody JSONObject jsonObj){
    	try {
    		
			if (null == jsonObj || jsonObj.isEmpty()) {
    			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
    		}
    		String idsJson = jsonObj.getString("idsJson");
    		if (null == idsJson || "".equals(idsJson) ){
    			return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
    		}
    		
    		List<String> ids = JsonUtil.toArray(idsJson, String.class);
			List<Good> goodList = goodBusiness.downShelvesAllGood(ids, getUserId());
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			return outFailure(AssConstantsUtil.AccountCode.ADD_CARD_ERROR_CODE, "");
		}
    }
    /**
     * 商品批量上架
    * @Title: upShelvesGood
    * @Description: 商品批量上架
    * @return String    返回类型
    * @author wxn  
    * @date 2015年1月27日 上午9:58:00
     */
	@RequestMapping(value = "upShelves")
	@ResponseBody
	public String upShelvesGood(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String idsJson = jsonObj.getString("idsJson");
			if (null == idsJson || "".equals(idsJson)) {
				return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
			}
			List<String> ids = JsonUtil.toArray(idsJson, String.class);
			List<Good> goodList = goodBusiness.upShelvesAllGood(ids);
			_logger.info(goodList);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
			if ("库存为零，上架失败！".equals(e.getMessage().toString())) {
				return outFailure(AssConstantsUtil.GoodCode.DENIE_UP_SHELVES, "");
			}
			return outFailure(AssConstantsUtil.GoodCode.ERROR_UP_SHELVES, "");
		}
	}
    
	/**
	 * 获取商品分类
	* @Title: getShopType
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年4月14日 上午9:40:50
	 */
    @RequestMapping(value = "getShopType")
    @ResponseBody
    public String getShopType(@RequestBody JSONObject jsonObj){
    	try{
    	//检测提交的参数
		String shopNo = jsonObj.getString("shopNo");
		if (null == shopNo || "".equals(shopNo) ){
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
		}
		// 商品分类查询
		List<ShopTypeVo> shopTypeList  = shopTypeBusiness.findShopType(shopNo);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("customAttribute", JsonUtil.fromObject(shopTypeList, false,BASE_JSON_SHOPTYPE).toString());
		return outSucceed(dataMap, false, "");
		}catch(Exception e) {
			_logger.error(e.getMessage(),e);
			return outFailure(AssConstantsUtil.AccountCode.ADD_CARD_ERROR_CODE, "");
		}
	}
    
    
    /**
     * 商品
    * @Title: _getGoodData
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param jsonObj
    * @param goodParam
    * @return true 说明方法执行成功 false说明方法执行失败
    * @throws
    * @author wxn  
    * @date 2015年4月6日 上午10:49:18
     */
	private boolean _getGoodData(JSONObject jsonObj, GoodParam goodParam) {
    	JSONObject json_obj = jsonObj.getJSONObject("good");
    	/**店铺编号**/
    	goodParam.setShopNo(json_obj.optString("shopNo"));
    	/**商品ID**/
    	Object id_obj = json_obj.get("goodId");
		if (null != id_obj && StringUtils.isNotBlank(id_obj.toString())) {
			goodParam.setId(Long.valueOf(id_obj.toString()));
    	}
    	goodParam.setServiceId(1l);
    	/**商品类目**/
		goodParam.setClassificationId(json_obj.getLong("classificationId"));
    	/**商品名称**/
    	goodParam.setName(json_obj.optString("name"));
    	/**商品货号**/
    	/**商品上下架状态*/
    	Object object = json_obj.get("status");
    	if (null != object) {
    		Integer status = (Integer) object;
    		if (status == UP_SHELE || status == DOWN_SHELE) {
    			goodParam.setStatus((Integer) object);
			} else {
				return false;
			}
		}
		// goodParam.setNo(json_obj.optString("no"));
    	/**商品市场价**/
    	Object marketPrice_obj = json_obj.get("marketPrice");
    	if(null != marketPrice_obj && StringUtils.isNotBlank(marketPrice_obj.toString())){
//    		double marketPrice = Double.valueOf((String)marketPrice_obj);
//    		long temp = (long)marketPrice;
//    		goodParam.setMarketPrice(temp);
    		goodParam.setMarketPrice(Long.valueOf(marketPrice_obj.toString()));
    	}
    	/**商品商城价**/
		Object salesPrice_obj = json_obj.get("salesPrice");
		if (null != salesPrice_obj && StringUtils.isNotBlank(salesPrice_obj.toString())) {
//			double salesPrice = Double.valueOf((String) salesPrice_obj);
//			long temp = (long) salesPrice;
//			goodParam.setSalesPrice(temp);
			goodParam.setSalesPrice(Long.valueOf(salesPrice_obj.toString()));
		}
    	/**商品数量**/
//		goodParam.setInventory(json_obj.getLong("inventory"));
    	/**商品详情**/
		goodParam.setGoodContent(json_obj.optString("goodContent"));
    	/**是否享受会员折扣**/
//    	int isJoinMember =  json_obj.getInt("preferentialPolicy");
//    	if (isJoinMember == 1 ) {
//    		goodParam.setIsJoinMember(true);
//    	}else {
//    		goodParam.setIsJoinMember(false);
//    	}
    	/**物流方式 统一运费**/
		int logisticsMode = 2;
		Object logisticsModeObj = json_obj.get("logisticsMode");
		if (logisticsModeObj != null && StringUtils.isNotBlank(logisticsModeObj.toString())) {
			logisticsMode = Integer.parseInt(logisticsModeObj.toString());
		}
		/**物流方式**/
		goodParam.setLogisticsMode(logisticsMode);
    	if ( logisticsMode == 2 ) {
    		/**统一运费**/
    		Object postFee_obj = json_obj.get("postFee");
        	if(null != postFee_obj && StringUtils.isNotBlank(postFee_obj.toString())){
//        		double postFee = Double.valueOf((String)postFee_obj);
//        		long temp = (long)postFee;
//            	goodParam.setPostFee(temp);
        		goodParam.setPostFee(Long.valueOf(postFee_obj.toString()));
        	}
    	}
    	return true;
    	
    }
    /**
     * 商品图片
    * @Title: _getGoodImgsData
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param jsonObj
    * @param goodParam
    * @return void    返回类型
    * @throws
    * @author wxn  
    * @date 2015年4月6日 上午11:21:15
     */
    private void _getGoodImgsData(JSONObject jsonObj,GoodParam goodParam){
    	JSONArray json_arr = jsonObj.getJSONArray("goodImgs");
    	GoodImg[] goodImgs = new GoodImg[json_arr.size()];
    	
    	for (int i = 0; i < json_arr.size(); i++) {
    		JSONObject json_obj = json_arr.getJSONObject(i);
    		GoodImg img = new GoodImg();
    		String id = json_obj.getString("id");
    		if (StringUtils.isNotBlank(id)){
    			img.setId(Long.valueOf(id));
    		}
    		img.setUrl(json_obj.optString("url"));
    		img.setPosition(json_obj.optInt("position"));
    		goodImgs[i] = img;
		}
    	goodParam.setGoodImgs(goodImgs);
    }
    /**
     * 商品属性
    * @Title: _getGoodPropertyData
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param jsonObj
    * @param goodParam
    * @return void    返回类型
    * @throws
    * @author wxn  
    * @date 2015年4月6日 上午11:21:25
     */
    private void _getGoodPropertyData(JSONObject jsonObj,GoodParam goodParam){
    	JSONArray json_arr = jsonObj.getJSONArray("goodProperty");
    	Map<String, GoodProperty> goodPropertyMap = new HashMap<String, GoodProperty>();
    	for (int i = 0; i < json_arr.size(); i++) {
    		JSONObject json_obj = json_arr.getJSONObject(i);
    		GoodProperty goodProperty = new GoodProperty();
    		String id = json_obj.getString("id");
    		if (StringUtils.isNotBlank(id)){
    			goodProperty.setId(Long.valueOf("id"));
    		}
    		goodProperty.setPropName(json_obj.optString("propName"));
    		goodProperty.setPropValueName(json_obj.optString("propValueName"));
    		goodPropertyMap.put(goodProperty.getPropName(), goodProperty);
		}
    	goodParam.setGoodPropertyMap(goodPropertyMap);
    }
    /**
     * 商品店铺分类
    * @Title: _getShopTypeData
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param jsonObj
    * @param goodParam
    * @return void    返回类型
    * @throws
    * @author wxn  
    * @date 2015年4月6日 上午11:21:52
     */
    private void _getShopTypeData(JSONObject jsonObj,GoodParam goodParam){
    	String shoptype_Str = jsonObj.optString("shopTypeIds");
    	if (StringUtils.isBlank(shoptype_Str)){
    		return ;
    	}
    	String[] shoptype_Arr =  shoptype_Str.split(",");
    	GoodShopTypeRelation[] shoptype = new GoodShopTypeRelation[shoptype_Arr.length];
    	for (int i = 0; i < shoptype_Arr.length; i++) {
    		if(StringUtils.isBlank(shoptype_Arr[i])){
    			continue;
    		}
    		GoodShopTypeRelation goodShopTypeRelation = new GoodShopTypeRelation();
    		goodShopTypeRelation.setShopTypeId(Long.valueOf(shoptype_Arr[i]));
    		shoptype[i] = goodShopTypeRelation;
		}
    	/**暂时处理方案，客户端分类还未开发，开发后修改**/
    	/*if(null != goodParam.getId()){
    		List<GoodShopTypeRelation> shoptypeList =  goodShopTypeRelationBusiness.findGoodShopTypeRelationByGoodId(goodParam.getId());
    		shoptype = shoptypeList.toArray(shoptype);
    	}*/
    	goodParam.setShoptype(shoptype);
    }
    /**
     * 
    * @Title: _getSkuData
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param jsonObj
    * @param goodParam
    * @return void    返回类型
    * @throws
    * @author wxn  
    * @date 2015年4月6日 下午2:26:05
     */
    private void _getSkuData(JSONObject jsonObj,GoodParam goodParam){
    	JSONArray sku_arr = jsonObj.getJSONArray("skuList");
    	List<GoodSku> skuList = JSONArray.toList(sku_arr,GoodSku.class );
    	goodParam.setSkuList(skuList);
    }
    
    @RequestMapping(value = "addShopType")
    @ResponseBody
    public String addShopType(@RequestBody JSONObject jsonObj){
		try {
			String shopNo = jsonObj.optString("shopNo");
			String name = jsonObj.optString("name");
			if(StringUtils.isBlank(shopNo)){
				return outFailure(AssConstantsUtil.ShopType.NULL_SHOP_NO_ERROR_CODE, "");
			}
			if(StringUtils.isBlank(name)){
				return outFailure(AssConstantsUtil.ShopType.NULL_SHOP_TYPE_NAME_ERROR_CODE, "");
			}
			
	        Long shopTypeId = shopTypeBusiness.addShopTypeToApp(name, shopNo);
	        Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", shopTypeId);
			return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("商品分类新增失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
    }
    
    /**
	* 推荐商品
	* @Title: recommendGood
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return String    返回类型
	* @author talo  
	* @date 2015年9月1日 下午4:35:21
	*/
	@RequestMapping(value = "recommendGood")
	@ResponseBody
	public String recommendGood (@RequestBody JSONObject jsonObj,Pagination<RecommendGoodVo> pagination) {
		try {
			String goodName = jsonObj.getString("goodName");
			Long userId = null;
			Object userIdObj = jsonObj.get("userId");
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			} else  {
				userId = super.getUserId();
			}
			pagination = super.initPagination(jsonObj);
			pagination = goodBusiness.getRecommendGood(userId, goodName, pagination);
			
			AssPagination<RecommendGoodVo> goodPage = new AssPagination<RecommendGoodVo>();
			goodPage.setCurPage(pagination.getCurPage());
			goodPage.setStart(pagination.getEnd());
			goodPage.setPageSize(pagination.getPageSize());
			goodPage.setTotalRow(pagination.getTotalRow());
			goodPage.setDatas(pagination.getDatas());
			return outSucceed(goodPage, false, "");
		} catch (Exception e) {
			_logger.error("推荐商品失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: subbranchGoods
	 * @Description: (查询分店商品列表)
	 * @param jsonObj
	 * @param pagination
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月1日 下午1:58:28
	 */
	@RequestMapping(value = "subbranchGoods")
	@ResponseBody
	public String subbranchGoods(@RequestBody JSONObject jsonObj, Pagination<GoodProfitVo> pagination) {
		try {
			// 检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			// 用户ID
			Long userId = null;
			Object userIdObj = jsonObj.get("userId");
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			} else  {
				userId = super.getUserId();
			}
			// 分店ID
			Long subbranchId = null;
			Object subIdObj = jsonObj.get("subbranchId");
			if (subIdObj != null && StringUtils.isNotBlank(subIdObj.toString())) {
				subbranchId = Long.parseLong(subIdObj.toString());
			} else {
				return outFailure(AssConstantsUtil.GoodCode.NULL_SUB_ID_CODE, "");
			}
			// 排序
			Integer sortBy = 0;
			Object sortByObj = jsonObj.get("sortBy");
			if (sortByObj != null && StringUtils.isNotBlank(sortByObj.toString())) {
				sortBy = Integer.parseInt(sortByObj.toString());
			}
			// 查找关键字
			String searchKey = jsonObj.optString("searchKey");
			if (StringUtils.isNotBlank(searchKey)) {
				searchKey = searchKey.replaceAll("\\s*", "");
			}
			// 查询商品
			pagination = this.goodBusiness.findSubbranchGoods(subbranchId, userId, sortBy, searchKey, pagination);
			// 重新封装Pagination对象
			AssPagination<GoodProfitVo> newpag = new AssPagination<GoodProfitVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(pagination.getDatas());
			return outSucceed(newpag, true, SUB_GOODS_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("查询分店商品列表失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
}
