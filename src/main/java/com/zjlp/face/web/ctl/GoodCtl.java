package com.zjlp.face.web.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.product.good.business.ClassificationBusiness;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.Prop;
import com.zjlp.face.web.server.product.good.domain.PropValue;
import com.zjlp.face.web.server.product.good.domain.vo.ClassificationVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.good.factory.GoodFactory;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateVo;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;
import com.zjlp.face.web.server.user.shop.producer.LogisticsProducer;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;

@Controller
@RequestMapping("/u/good/good/")
public class GoodCtl extends BaseCtl{
	
    private Log _logger = LogFactory.getLog(GoodCtl.class);
	
	public static final String REDIRECT_GOOD_LIST = "/u/good/good/list";
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;
	@Autowired
	private GoodBusiness goodBusiness;
	@Autowired
	private ClassificationBusiness classificationBusiness;
	@Autowired
	private GoodFactory protocolGoodFactory;
	@Autowired
	private GoodFactory xianjuGoodFactory;
	@Autowired
	private LogisticsProducer logisticsProducer;
	@Autowired
	private ShopProducer shopProducer;
	
    //获取商品分类列表
    @RequestMapping(value = "list")
    public String list(GoodVo goodVo, Pagination<GoodVo> pagination, Model model){
    	goodVo.setShopNo(super.getShopNo());
        if (null == goodVo.getStatus()) {
        	goodVo.setStatus(Constants.ISDEFAULT);
		}
        goodVo.setServiceId(Constants.SERVICE_ID_COMMON);
        pagination = goodBusiness.searchPageGood(goodVo,pagination);
        List<ShopTypeVo> shopTypelist = shopTypeBusiness.findShopType(super.getShopNo());
        model.addAttribute("shopTypeList", shopTypelist);
        model.addAttribute("pagination", pagination);
    	return "/m/product/goods/good/prolist";
    }
    
    //获取推荐到首页商品分类列表
    @RequestMapping(value = "listRecommend",method=RequestMethod.GET)
    public String listRecommend(GoodVo goodVo, Pagination<GoodVo> pagination, Model model){
    	goodVo.setShopNo(super.getShopNo());
        if (null == goodVo.getStatus()) {
        	goodVo.setStatus(Constants.ISDEFAULT);;
		}
        goodVo.setServiceId(Constants.SERVICE_ID_COMMON);
        pagination = goodBusiness.searchPageGood(goodVo,pagination);
        List<ShopTypeVo> shopTypelist = shopTypeBusiness.findShopType(super.getShopNo());
        model.addAttribute("shopTypeList", shopTypelist);
        model.addAttribute("pagination", pagination);
    	return "/m/product/goods/good/prolist-recommend";
    }
    
    
    //获取商品分类列表
	@RequestMapping(value = "listClassification")
	public String listClassification( Model model){
		String shopNo = super.getShopNo();
		List<Classification> classificationList = classificationBusiness.findClassificationByPid(0L, 0);
		classificationList = this.filterClassificationsByShopNo(classificationList, shopNo);
		model.addAttribute("classificationList",classificationList);
		ClassificationVo classificationVo = classificationBusiness.getLatestClassification(shopNo);
		model.addAttribute("classificationVo",classificationVo);
		return "/m/product/goods/good/pronew-type";
	}

	/**
	 * 根据协议商品店铺号过滤协议商品类目
	 * @Title: filterClassificationsByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param classificationList
	 * @param shopNo
	 * @return
	 * @date 2015年4月1日 上午11:16:02  
	 * @author lys
	 */
	private List<Classification> filterClassificationsByShopNo(
			List<Classification> classificationList, String shopNo) {
		//String protocolShop = PropertiesUtil.getContexrtParam(Constants.PROTOCOL_GOOD_SHOPNO);
		if (null == classificationList || classificationList.isEmpty()) {
			return classificationList;
		}
		//if (null == protocolShop || !protocolShop.equals(shopNo)) {
			for (int i = classificationList.size() -1; i >=0; i--) {
				if (null == classificationList.get(i)) continue; 
				if (Constants.GOOD_CLASSIFICATION_PROTOCOL
						.equals(classificationList.get(i).getCategory())) {
					classificationList.remove(i);
				}
			}
		//}
		return classificationList;
	}
	   //获取商品列表
	@RequestMapping(value = "queryClassification")
	@ResponseBody
	public String queryClassification(Long pid,Integer level,Integer type){
		List<Classification> classificationList = classificationBusiness.findClassificationByPid(pid, level);
		if(type==2){
			for(Iterator<Classification> ite=classificationList.iterator();ite.hasNext();){
				Classification c=ite.next();
				if(c.getId().intValue()==Constants.LEIMU_MENHU){
					ite.remove();
				}
			}
		}
		List<Object> l = new ArrayList<Object>();
		l.add(_convert(classificationList, -1l));
		return JSONArray.fromObject(l).toString();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List _convert(List<Classification> list, long id){
		List l = new ArrayList();
		for(Classification c : list){
			Map map = new HashMap();
			map.put("id", c.getId());
			map.put("title", c.getName());
			if(c.getId() == id){
				map.put("active", true);
			}
			l.add(map);
		}
		return l;
	}
    
	/**
     * @Description: 商品新增:门户商品,其他类商品,手机端商品
     * @date: 2015年7月9日 上午10:04:50
     * @author: zyl
     */
    @RequestMapping(value = "add/{classificationId}",method=RequestMethod.GET)
    public String addInit(@PathVariable Long classificationId,Model model){
    	String shopNo = super.getShopNo();
	   	Classification classification = classificationBusiness.getClassificationById(classificationId);
		AssertUtil.notNull(classification, "类目不存在，操作失败！");
		model.addAttribute("classification", classification);
		List<ShopTypeVo> shopTypeList  = shopTypeBusiness.findShopType(super.getShopNo());
		model.addAttribute("shopTypeList", shopTypeList);
		//查询类目全称
		String classFullName = classificationBusiness.getClassificationName(classification,null);
		model.addAttribute("classFullName", classFullName);
    	if(classification.getId().intValue()==Constants.LEIMU_MENHU){
			return "/m/product/goods/good/add-xianju";
		}else if(classification.getId().intValue()==Constants.LEIMU_QITA){
			boolean hasSalesProp = classificationBusiness.hasSalesProp(classificationId);
			if(!hasSalesProp){
				model.addAttribute("isNotKeyInput", true);
			}
			return "/m/product/goods/good/add-qita";
		}else{
			boolean hasSalesProp = classificationBusiness.hasSalesProp(classificationId);
			if(!hasSalesProp){
				model.addAttribute("isNotKeyInput", true);
			}
	        //商品属性查询
	    	Map<Prop, List<PropValue>> propMap = classificationBusiness.findAllPropByClassificationId(classificationId);
	    	model.addAttribute("propMap", propMap);
	    	//店铺物流信息查询
	        this._logisticsInit(shopNo,model);
	        
	    	return "/m/product/goods/good/add";
		}

    }
    
    /**
     * @Description: 商品新增:门户商品,其他类商品,手机端商品
     * @date: 2015年7月9日 上午10:04:50
     * @author: zyl
     */
    @RequestMapping(value = "add/{classificationId}",method=RequestMethod.POST)
    public String add(@PathVariable Long classificationId,GoodParam goodParam) throws Exception{
    	goodParam.setUserId(super.getUserId());
		Classification classification = classificationBusiness.getClassificationById(classificationId);
		AssertUtil.notNull(classification, "类目不存在，操作失败！");
		goodParam.setServiceId(1l);
    	goodParam.setShopNo(super.getShopNo());
    	goodParam.setClassificationId(classificationId);
		goodParam.setHasProtocol(false);
		if(classification.getId().equals(Constants.LEIMU_MENHU)){
			goodParam.setSalesPrice(0l);
			goodParam.setMarketPrice(0l);
			xianjuGoodFactory.addGood(goodParam);
		}else {
			boolean hasSalesProp = classificationBusiness.hasSalesProp(classificationId);
	    	goodParam.setHasSalesProp(hasSalesProp);
	        if(!hasSalesProp){
				/** 设置非关键属性类型Sku参数 */
				goodParam.setNokeySku(goodParam);
			}
			/** 3 商品关联关系 */
	    	goodBusiness.addGood(goodParam);
		}
		return super.getRedirectUrl(REDIRECT_GOOD_LIST);
    }
    
    
    /**
     * @Description: 商品修改:门户商品,其他类商品,手机端商品
     * @date: 2015年7月9日 上午10:04:50
     * @author: zyl
     */
    @RequestMapping(value = "edit/{goodId}",method=RequestMethod.GET)
    public String editInit(@PathVariable Long goodId,Model model,Long shopTypeId,String goodName,Integer curPage){
    	model.addAttribute("shopTypeId",shopTypeId );
    	model.addAttribute("goodName",goodName );
    	model.addAttribute("curPage",curPage );
    	String shopNo = super.getShopNo();
    	//商品查询
    	Good good = goodBusiness.getGoodById(goodId);
    	model.addAttribute("good",good);
    	//查询商品图片
		List<GoodImgVo> goodImgs = goodBusiness.findGoodImgByGoodIdAndType(goodId, 1);
		Map<String, GoodImgVo> goodImgMap = new LinkedHashMap<String, GoodImgVo>();
		for (GoodImgVo goodImgVo : goodImgs) {
			goodImgMap.put(String.valueOf(goodImgVo.getPosition()), goodImgVo);
		}
		model.addAttribute("goodImgs", goodImgMap);
	   	Classification classification = classificationBusiness.getClassificationById(good.getClassificationId());
		AssertUtil.notNull(classification, "类目不存在，操作失败！");
		model.addAttribute("classification", classification);
    	//判断是类目是否为协议商品类目
    	boolean hasProtocol= classificationBusiness.hasProtocolClassification(good.getClassificationId());
		if (hasProtocol) {
			//查询商品SKU
        	List<GoodSku> skuList = goodBusiness.findGoodSkuByGoodId(goodId);
        	model.addAttribute("skuList", skuList);
			return "/m/product/goods/good/add-protocol";
		}else if(classification.getId().intValue()==Constants.LEIMU_MENHU){
			List<ShopTypeVo> shopTypeList  = shopTypeBusiness.findShopType(super.getShopNo(),goodId);
	        model.addAttribute("shopTypeList", shopTypeList);
			return "/m/product/goods/good/add-xianju";
		}else {
	    	boolean hasSalesProp = classificationBusiness.hasSalesProp(good.getClassificationId());
	        if(!hasSalesProp){
	        	if (14 == good.getClassificationId().intValue()) {
	        		//补丁：免费店铺的商品
		        	GoodProperty salesInputProperty = GoodProperty.getSalesInputProp();
		        	salesInputProperty.setGoodId(goodId);
	        		List<GoodProperty> salesInputProperties =  goodBusiness.findSalesInputKeyGoodProperties(salesInputProperty);
	        	  	model.addAttribute("isSalesInput", true);
	            	model.addAttribute("salesInputProperties", salesInputProperties);
	              	List<GoodSku> skuList = goodBusiness.findGoodSkuByGoodId(goodId);
		        	model.addAttribute("skuList", skuList);
				}else{
		        	GoodProperty notKeyProperty = GoodProperty.getInputNoKeyProp();
		        	notKeyProperty.setGoodId(goodId);
		        	model.addAttribute("isNotKeyInput", true);
					//查询商品非关键属性
		        	List<GoodProperty> notKeyProperties =  goodBusiness.findInputNotKeyGoodProperties(notKeyProperty);
		        	model.addAttribute("notKeyProperties", notKeyProperties);
					//查询商品SKU
		        	List<GoodSku> skuList = goodBusiness.findGoodSkuByGoodId(goodId);
		        	model.addAttribute("skuList", skuList);
				}
	        }else {
		    	//标准属性查询
		    	Map<Prop, List<PropValue>> propMap = classificationBusiness.findAllPropByClassificationId(good.getClassificationId());
		    	model.addAttribute("propMap", propMap);
		    	//商品属性查询
				//查询商品属性（颜色）
				GoodProperty color =GoodProperty.getColorProp();
				color.setGoodId(goodId);
				List<GoodProperty> colorList = goodBusiness.findGoodProperties(color);
				model.addAttribute("colorJson", JsonUtil.fromObject(colorList, false, GoodParam.BASE_JSON_COLOR_SIZE).toString());
				Map<Long, GoodProperty> propertyImgs = new HashMap<Long, GoodProperty>();
				for (GoodProperty goodProperty : colorList) {
					propertyImgs.put(goodProperty.getPropValueId(), goodProperty);
				}
				model.addAttribute("propertyImgs", propertyImgs);
				//查询商品尺寸
				GoodProperty size = GoodProperty.getEnumProp();
				size.setGoodId(goodId);
				List<GoodProperty> sizeList = goodBusiness.findGoodProperties(size);
				model.addAttribute("sizeJson", JsonUtil.fromObject(sizeList, false, GoodParam.BASE_JSON_COLOR_SIZE));
				//查询商品SKU
				String itemJson = goodBusiness.findGoodSkuJsonByGoodId(goodId);
				model.addAttribute("itemJson", itemJson);
			}
	    	//店铺物流信息查询
	        this._logisticsInit(shopNo,model);
	    	//商品类别查询
	        List<ShopTypeVo> shopTypeList  = shopTypeBusiness.findShopType(super.getShopNo(),goodId);
	        model.addAttribute("shopTypeList", shopTypeList);
	    	return "/m/product/goods/good/add";
		}
    }
    
    /**
     * 店铺物流信息查询
     */
    private void _logisticsInit(String shopNo,Model model){
    	//配送方式的获取
    	Shop shop = shopProducer.getShopByNo(shopNo);
    	ShopVo shopVo = new ShopVo(shop);
    	model.addAttribute("shopVo", shopVo);
    	if (ShopVo.KD.equals(shopVo.getKdType())) {
    		//运费模版
        	DeliveryTemplateVo deliveryTemplateVo = logisticsProducer.getDeliveryTemplateVoByShopNo(shopNo);
        	model.addAttribute("deliveryTemplateVo", deliveryTemplateVo);
    	}
    	if (ShopVo.PS.equals(shopVo.getPsType())) {
    		//店铺配送
        	List<ShopDistribution>  shopDistributionList = logisticsProducer.findShopDistributionList(shopNo);
        	StringBuilder shopDistributionStr = new StringBuilder();
        	for (ShopDistribution shopDistribution : shopDistributionList) {
        		shopDistributionStr.append("、").append(shopDistribution.getName());
    		}
        	shopDistributionStr.delete(0, 1);
        	model.addAttribute("shopDistributionStr", shopDistributionStr.toString());
    	}
    	if (ShopVo.ZT.equals(shopVo.getZtType())) {
    		//门店自取
        	List<PickUpStore> pickUpStoreList = logisticsProducer.findPickUpStoreList(shopNo);
        	StringBuilder pickUpStoreStr = new StringBuilder();
        	for (PickUpStore pickUpStore : pickUpStoreList) {
        		pickUpStoreStr.append("、").append(pickUpStore.getName());
    		}
        	pickUpStoreStr.delete(0, 1);
        	model.addAttribute("pickUpStoreStr", pickUpStoreStr.toString());
    	}
    }
    
    /**
     * @Description: 商品修改:门户商品,其他类商品,手机端商品
     * @date: 2015年7月9日 上午10:04:50
     * @author: zyl
     */
    @RequestMapping(value = "edit/{goodId}",method=RequestMethod.POST)
    public String edit(@PathVariable Long goodId,Model model,GoodParam goodParam,Long shopTypeId,String goodName,Integer curPage) throws Exception{
    	model.addAttribute("shopTypeId",shopTypeId );
    	model.addAttribute("name",goodName );
    	model.addAttribute("curPage",curPage );
    	goodParam.setUserId(super.getUserId());
    	goodParam.setServiceId(1l);
        AssertUtil.notNull(goodId,"商品ID为空，修改失败");
    	goodParam.setShopNo(super.getShopNo());
    	Good good = goodBusiness.getGoodById(goodId);
    	AssertUtil.notNull(good,"商品为空，修改失败");
    	Classification classification = classificationBusiness.getClassificationById(good.getClassificationId());
		AssertUtil.notNull(classification, "类目不存在，操作失败！");
	 	boolean hasProtocol= classificationBusiness.hasProtocolClassification(good.getClassificationId());
    	boolean hasSalesProp = classificationBusiness.hasSalesProp(classification.getId());
    	goodParam.setHasSalesProp(hasSalesProp);
    	goodParam.setHasProtocol(hasProtocol);
		goodParam.setId(goodId);
		if (hasProtocol) {
			goodParam.setMarketPrice(goodParam.getSalesPrice());
			goodParam.setProtocolSku(goodParam);
			protocolGoodFactory.editGood(goodParam);
		}else if(classification.getId().equals(Constants.LEIMU_MENHU)){
			goodParam.setSalesPrice(0l);
			goodParam.setMarketPrice(0l);
			xianjuGoodFactory.editGood(goodParam);
		}else {
			if(14== good.getClassificationId().intValue()){
				//补丁：免费店铺的商品
				goodParam.setFreeSku(goodParam);
				goodBusiness.editGoodForFree(goodParam);
			}else{
			    if(!hasSalesProp){
					/** 设置非关键属性类型Sku参数 */
					goodParam.setNokeySku(goodParam);
				}
		    	goodBusiness.editGood(goodParam);
			}
		}
    	return super.getRedirectUrl(null,REDIRECT_GOOD_LIST,"?status="+good.getStatus());
    }
    
    /**
     * 商品批量上架
     * @Title: upShelvesGood
     * @Description: (这里用一句话描述这个方法的作用)
     * @return
     * @date 2014年7月21日 上午10:38:44
     * @author dzq
     */
    @RequestMapping(value = "upShelves", method = RequestMethod.POST)
    @ResponseBody
    public String upShelvesGood(@RequestParam(required=true) String idsJson,Model model){
    	try {
    		List<String> ids = JsonUtil.toArray(idsJson, String.class);
			goodBusiness.upShelvesAllGood(ids);
			/** 发布MetaQ 商品上架 */
//			try {
//				goodMetaOperateProducer.senderAllAnsy(super.getShopNo(),super.getShop().getUserId()
//						, goodList, GoodMessage.GOOD_UPSHELF);
//			} catch (Exception e) {
//				//异常已在内部处理
//				_logger.error("MetaQ推送商品上架失败！",e);
//			}
			return super.getReqJson(true, "上架成功");
		} catch (BaseException e) {
			_logger.error(e.getMessage(),e);
			return super.getReqJson(false, e.getExternalMsg());
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			return super.getReqJson(false, "上架成功");
		}
    }
    
    
    /**
     * 商品批量下架
     * @Title: underGood
     * @Description: (这里用一句话描述这个方法的作用)
     * @return
     * @date 2014年7月21日 上午10:37:05
     * @author dzq
     */
    @RequestMapping(value = "downShelves", method = RequestMethod.POST)
    @ResponseBody
    public String downShelvesGood(@RequestParam(required=true) String idsJson,Model model){
    	try {
    		List<String> ids = JsonUtil.toArray(idsJson, String.class);
			goodBusiness.downShelvesAllGood(ids, getUserId());
//			/** 发布MetaQ 商品下架 */
//			try {
//				goodMetaOperateProducer.senderAllAnsy(super.getShopNo(),super.getShop().getUserId()
//						, goodList, GoodMessage.GOOD_OUT);
//			} catch (Exception e) {
//				//异常已在内部处理
//				_logger.error("MetaQ推送商品下架失败！",e);
//			}
			return super.getReqJson(true, "下架成功");
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			return super.getReqJson(false, "下架失败");
		}
    }
    
    
    /**
     * 删除商品
     * 
     * @Title: remove
     * @Description: (这里用一句话描述这个方法的作用)
     * @author dzq
     */
    @RequestMapping(value = "remove/{goodId}",method=RequestMethod.POST)
    @ResponseBody
    public String remove(@PathVariable Long goodId,Model model){
        try {
        	goodBusiness.removeGood(getUserId(), goodId);
	    	return super.getReqJson(true, "删除成功");
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			return super.getReqJson(false, "删除失败");
		}
    }
    
    
    /**
     * 商品批量删除
     * @Title: removeAll 
     */
    @RequestMapping(value = "removeAll",method=RequestMethod.POST)
    public String removeAll(@RequestParam(required=true) String idsJson,Integer status) throws Exception{
	    	List<String> ids = JsonUtil.toArray(idsJson, String.class);
			goodBusiness.removeAllGood(getUserId(), ids);
	        return super.getRedirectUrl(REDIRECT_GOOD_LIST);
    }
    
    /**
     * 对商品进行排序
     */
    @RequestMapping(value = "sort")
    @ResponseBody
    public String sortType(String subId, String tarId){
        try{
            goodBusiness.sortGood(Long.parseLong(subId), Long.parseLong(tarId));
            return "1";
        }catch(Exception e){
			_logger.error(e.getMessage(),e);
            return "-1";
        }
    }
    
    /**
     * 推荐商品至首页
     * @Title: recommendIndex
     * @Description: (这里用一句话描述这个方法的作用)
     * @return
     * @date 2014年7月21日 上午10:39:39
     * @author dzq
     */
    @RequestMapping(value = "spreadIndex", method = RequestMethod.POST)
    @ResponseBody
    public String spreadIndex(@RequestParam(required=true) Long goodId){
    	try {
    		goodBusiness.spreadIndex(goodId);
    		return super.getReqJson(true, "推广成功");
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			return super.getReqJson(false, "推广失败");
		}
    }
    
    /**
     * @Description: 四阶段商品新增:门户商品,其他类商品,手机端商品,单属性商品,双属性商品,三属性商品
     * @return
     * @throws Exception
     * @date: 2015年7月9日 上午9:46:42
     * @author: zyl
     */
    @RequestMapping(value = "addnew/{classificationId}",method=RequestMethod.POST)
    public String addnew(@PathVariable Long classificationId,GoodParam goodParam) throws Exception{
    	goodParam.setUserId(super.getUserId());
		Classification classification = classificationBusiness.getClassificationById(classificationId);
		AssertUtil.notNull(classification, "类目不存在，操作失败！");
		goodParam.setServiceId(1l);
    	goodParam.setShopNo(super.getShopNo());
    	goodParam.setClassificationId(classificationId);
		goodParam.setHasProtocol(false);
		if(classification.getId().equals(Constants.LEIMU_MENHU)){
			goodParam.setSalesPrice(0l);
			goodParam.setMarketPrice(0l);
			xianjuGoodFactory.addGood(goodParam);
		}else {
			boolean hasSalesProp = classificationBusiness.hasSalesProp(classificationId);
	    	goodParam.setHasSalesProp(hasSalesProp);
	        if(!hasSalesProp){
				/** 设置非关键属性类型Sku参数 */
				goodParam.setNokeySku(goodParam);
			}
	        if(classification.getId().equals(Constants.LEIMU_QITA)){
	        	goodBusiness.addGood(goodParam);
	        }else{
	        	goodBusiness.addGoodNew(goodParam);
	        }
		}
		return super.getRedirectUrl(REDIRECT_GOOD_LIST);
    }
    /**
     * @Description: 四阶段商品修改:门户商品,其他类商品,手机端商品,单属性商品,双属性商品,三属性商品
     * @return
     * @date: 2015年7月9日 上午9:45:33
     * @author: zyl
     */
    @RequestMapping(value = "editnew/{goodId}",method=RequestMethod.GET)
    public String editInitnew(@PathVariable Long goodId,Model model,Long shopTypeId,String goodName,Integer curPage){
    	model.addAttribute("shopTypeId",shopTypeId );
    	model.addAttribute("goodName",goodName );
    	model.addAttribute("curPage",curPage );
    	String shopNo = super.getShopNo();
    	//商品查询
    	Good good = goodBusiness.getGoodById(goodId);
    	model.addAttribute("good",good);
    	//查询商品图片
		List<GoodImgVo> goodImgs = goodBusiness.findGoodImgByGoodIdAndType(goodId, 1);
		Map<String, GoodImgVo> goodImgMap = new LinkedHashMap<String, GoodImgVo>();
		for (GoodImgVo goodImgVo : goodImgs) {
			goodImgMap.put(String.valueOf(goodImgVo.getPosition()), goodImgVo);
		}
		model.addAttribute("goodImgs", goodImgMap);
	   	Classification classification = classificationBusiness.getClassificationById(good.getClassificationId());
		AssertUtil.notNull(classification, "类目不存在，操作失败！");
		model.addAttribute("classification", classification);
		//查询商品分类
		List<ShopTypeVo> shopTypeVoList = shopTypeBusiness.findShopType(shopNo, good.getId());
		model.addAttribute("shopTypeList", shopTypeVoList);
		if(classification.getId().intValue()==Constants.LEIMU_MENHU){
			List<ShopTypeVo> shopTypeList  = shopTypeBusiness.findShopType(super.getShopNo(),goodId);
	        model.addAttribute("shopTypeList", shopTypeList);
			return "/m/product/goods/good/add-xianju";
		}else if(14 == good.getClassificationId().intValue()){
    		//补丁：免费店铺的商品
        	GoodProperty salesInputProperty = GoodProperty.getSalesInputProp();
        	salesInputProperty.setGoodId(goodId);
    		List<GoodProperty> salesInputProperties =  goodBusiness.findSalesInputKeyGoodProperties(salesInputProperty);
    	  	model.addAttribute("isSalesInput", true);
        	model.addAttribute("salesInputProperties", salesInputProperties);
          	List<GoodSku> skuList = goodBusiness.findGoodSkuByGoodId(goodId);
        	model.addAttribute("skuList", skuList);
        	return "/m/product/goods/good/add-qita";
		}else if(classification.getId().intValue()==Constants.LEIMU_QITA){
        	GoodProperty notKeyProperty = GoodProperty.getInputNoKeyProp();
        	notKeyProperty.setGoodId(goodId);
        	model.addAttribute("isNotKeyInput", true);
			//查询商品非关键属性
        	List<GoodProperty> notKeyProperties =  goodBusiness.findInputNotKeyGoodProperties(notKeyProperty);
        	model.addAttribute("notKeyProperties", notKeyProperties);
			//查询商品SKU
        	List<GoodSku> skuList = goodBusiness.findGoodSkuByGoodId(goodId);
        	model.addAttribute("skuList", skuList);
        	return "/m/product/goods/good/add-qita";
		}
		//查询类目全称
		String classFullName = classificationBusiness.getClassificationName(classification,null);
		model.addAttribute("classFullName", classFullName);
		
		//查询类目下所有属性的集合
		Map<Prop, List<PropValue>> propMap = classificationBusiness.findAllPropByClassificationId(good.getClassificationId());
		model.addAttribute("propMap", propMap);
		//查询商品的sku
		List<GoodSku> goodSkuList = goodBusiness.findGoodSkuListByGoodId(good.getId());
		List<Long> propValueIdList=new ArrayList<Long>();
		List<GoodProperty> goodPropertyList=goodBusiness.findGoodPropertyListByGoodId(goodId);
		Map<String,GoodProperty> propValueIdmap=new HashMap<String,GoodProperty>();
		Map<String,GoodProperty> goodPropertyIdMap=new HashMap<String,GoodProperty>();
		for(GoodProperty gp:goodPropertyList){
			propValueIdmap.put(String.valueOf(gp.getPropValueId()), gp);
			goodPropertyIdMap.put(String.valueOf(gp.getId()), gp);
		}
		if(!propMap.isEmpty()){
			for(List<PropValue> pvList:propMap.values()){
				for(PropValue pv:pvList){
					GoodProperty gp = propValueIdmap.get(pv.getId().toString());
					if(null!=gp){
						pv.setName(gp.getPropValueAlias());
					}
				}
			}
			List<Long> sortList=new ArrayList<Long>();
			for(Prop p:propMap.keySet()){
				sortList.add(p.getId());
			}
			for(GoodSku gs:goodSkuList){
				if(gs.getSkuProperties().startsWith(";")){
					gs.setSkuProperties(gs.getSkuProperties().substring(1));
				}
				String[] ids=gs.getSkuProperties().split(";");
				String propValueIds="";
				/** 把GoodPropertyID转成PropValueID*/
				String[] arr=new String[sortList.size()];
				for(int i=0;i<ids.length;i++){
					GoodProperty gp=goodPropertyIdMap.get(ids[i]);
					arr[sortList.indexOf(gp.getPropId())]=String.valueOf(gp.getPropValueId());
					propValueIdList.add(gp.getPropValueId());
				}
				for(int i=0;i<arr.length;i++){
					propValueIds+=arr[i]+";";
				}
				gs.setSkuProperties(propValueIds);
			}
		}
		model.addAttribute("goodSkuList", goodSkuList);
		model.addAttribute("goodSkus",JsonUtil.fromCollection(goodSkuList,false,new String[]{"id","skuProperties","name","salesPrice","stock","barCode","picturePath"}));
		model.addAttribute("propValueIdList", JsonUtil.fromCollection(propValueIdList));
		return "/m/product/goods/good/add";
    }
    
    /**
     * @Description: 四阶段商品修改:门户商品,其他类商品,手机端商品,单属性商品,双属性商品,三属性商品
     * @return
     * @throws Exception
     * @date: 2015年7月9日 上午9:42:27
     * @author: zyl
     */
    @RequestMapping(value = "editnew/{goodId}",method=RequestMethod.POST)
    public String editnew(@PathVariable Long goodId,Model model,GoodParam goodParam,Long shopTypeId,String goodName,Integer curPage) throws Exception{
    	model.addAttribute("shopTypeId",shopTypeId );
    	model.addAttribute("name",goodName );
    	model.addAttribute("curPage",curPage );
    	goodParam.setUserId(super.getUserId());
    	goodParam.setServiceId(1l);
    	goodParam.setShopNo(super.getShopNo());
    	Good good = goodBusiness.getGoodById(goodId);
    	AssertUtil.notNull(good,"商品为空，修改失败");
    	Classification classification = classificationBusiness.getClassificationById(good.getClassificationId());
		AssertUtil.notNull(classification, "类目不存在，操作失败！");
    	boolean hasSalesProp = classificationBusiness.hasSalesProp(classification.getId());
    	goodParam.setHasSalesProp(hasSalesProp);
    	goodParam.setHasProtocol(false);
		goodParam.setId(goodId);
		if(classification.getId().equals(Constants.LEIMU_MENHU)){
			goodParam.setSalesPrice(0l);
			goodParam.setMarketPrice(0l);
			xianjuGoodFactory.editGood(goodParam);
		}else {
			if(14== good.getClassificationId().intValue()){
				//补丁：免费店铺的商品
				goodParam.setFreeSku(goodParam);
				goodBusiness.editGoodForFree(goodParam);
			}else if(classification.getId().intValue()==Constants.LEIMU_QITA){
				if(!hasSalesProp){
					/** 设置非关键属性类型Sku参数 */
//					goodParam.setNokeySku(goodParam);
					goodParam.setQITASku(goodParam);
				}
				goodBusiness.editGood(goodParam);
			}else{
			    if(!hasSalesProp){
					/** 设置非关键属性类型Sku参数 */
					goodParam.setNokeySku(goodParam);
				}
		    	goodBusiness.editGoodNew(goodParam);
			}
		}
    	return super.getRedirectUrl(null,REDIRECT_GOOD_LIST,"?status="+good.getStatus());
    }
}
