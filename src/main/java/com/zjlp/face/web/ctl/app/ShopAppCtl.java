package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssPagination;
import com.zjlp.face.web.appvo.AssShopVo;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.ShopException;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.product.template.domain.vo.TemplateVo;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.AssShopBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.LbsBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.NoticeBussiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Notice;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.FoundShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.shop.domain.vo.RecommendShopVo;
import com.zjlp.face.web.server.user.shop.producer.ShopLocationProducer;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;
import com.zjlp.face.web.server.user.user.producer.UserGisProducer;
import com.zjlp.face.web.util.AssConstantsUtil;
/**
 * 店铺管理
* @ClassName: ShopCtl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年4月14日 上午9:42:32
*
 */
@Controller
@RequestMapping({ "/assistant/ass/shop/"})
public class ShopAppCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	private static final String NET_DOMAIN_URL = PropertiesUtil.getContexrtParam("NET_DOMAIN_URL");
	
	private static final String[] RECOMMEND_SHOPS = { "curPage", "start", "pageSize", "totalRow",
			"datas.no", "datas.name", "datas.address", "datas.shopLogoUrl", "datas.shopUrl" };
	
	private static final String[] SHOPS_IN_NEAR = { "my_latitude", "my_longitude", "shop.id",
			"shop.shopNo", "shop.shopName", "shop.lat", "shop.lng", "shop.address",
			"shop.shopPictureUrl", "shop.shopUrl" };
	
	private static final String[] SHOPLOCATION_FILLTER = {"lat","lng","address","cell"};
	
	private static final String[] SHOP_INFO = {"shop.no", "shop.userId", "shop.name", "shop.shopLogoUrl", "shop.zoomSignPic", "shop.isDefaultSignPic", "shop.recruitButton", "shop.cell", "shopLocation.lat",
		"shopLocation.lng", "shopLocation.areaCode", "shopLocation.address", "shopLocation.areaName", "isShowSignPic", "isNotice", "noticeContent"};
	
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private UserGisProducer userGisProducer;
	@Autowired
	private TemplateBusiness templateBusiness;
	@Autowired
	private ShopLocationProducer shopLocationProducer;
//	@Autowired
//	private RecruitProducer	recruitProducer;
	@Autowired
	private AssShopBusiness assShopBusiness;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private LbsBusiness lbsBusiness;
	@Autowired
	private NoticeBussiness noticeBussiness;
	
	
	/**
	* @Title: overView
	* @Description: 各类店铺信息列表
	* @param type  1 官网 ,2 商城,3 脸谱
	* @param shopName
	* @return String    返回类型
	 * @throws Exception 
	* @throws
	* @author wxn  
	* @date 2014年12月16日 上午10:16:14
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public String overView(@RequestBody JSONObject jsonObj) throws Exception {
		
		try {
			String shopName = "";
			
			if (null != jsonObj && jsonObj.isEmpty()) {
				Object shopnameobj = jsonObj.get("shopName");
				shopName = null == shopnameobj ? "" : shopnameobj.toString();
			}
			
			List<AssShopVo> shopList = new ArrayList<AssShopVo>();
			List<ShopDto> list = shopBusiness.findShopList(getUserId(), null, shopName);
			for (ShopDto shopDto : list) {
					AssShopVo shop = new AssShopVo();
					shop.setShopNo(shopDto.getNo());
					shop.setName(shopDto.getName());
					
					Map<String,Integer> countMap = salesOrderBusiness.getShopSalesOrderCountInfo(shop.getShopNo());
					// 今日订单金额
					Integer payPrice = countMap.get("payPrice");
					// 今日订单数
					Integer orderNumber = countMap.get("orderNumber");
					// 今日代发货数
					Integer sendOrderNumber = countMap.get("sendOrderNumber");
					shop.setOrder_PayCount(0);
					shop.setOrder_sendOrderNumber(sendOrderNumber);
					shop.setOrder_TodayCount(orderNumber);
					shop.setOrder_TotalAmount(0L);
//					shop.setOrder_TotalAmountStr(DataUtil.formatMoney("##0.00", payPrice.longValue()));
					//店铺模板图
					OwTemplateDto owTemplateDto= templateBusiness.getCurrentHomePageOwTemplate(shopDto.getNo(),shopDto.getType());
					shop.setPicturePath(NET_DOMAIN_URL + owTemplateDto.getPath());
					int role = assShopBusiness.getShopRole(shopDto);
					shop.setRole(role);
//					boolean isInner = recruitProducer.isInnerDistributionShop(shopDto.getNo());
//					shop.setInner(isInner);
					shopList.add(shop);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("shopName",shopName);
			dataMap.put("shopList", shopList);
			 return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("店铺信息列表", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 查找发现店铺
	 * 
	 * @param jsonObj
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "findRecommendShops")
    @ResponseBody
	public String findRecommendShops(@RequestBody JSONObject jsonObj, Pagination<Shop> pagination) {
		try {
			//检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			// 排序信息
			Integer orderBy = jsonObj.optInt("orderBy");
			String searchKey = jsonObj.optString("searchKey");
			FoundShopDto shop = new FoundShopDto();
			// 排序条件
			shop.setOrderBy(orderBy);
			// 精确查找条件 "店铺名""地址",如果为null或者空串,置为null来过滤数字类型的店铺名
			shop.setSearchKey(StringUtils.isBlank(searchKey) ? null : searchKey);
			pagination = shopBusiness.getFoundShop(shop, pagination);
			
			List<FoundShopDto> result = new ArrayList<FoundShopDto>();
			for (Shop current : pagination.getDatas()) {
				FoundShopDto dto = new FoundShopDto();
				dto.setNo(current.getNo());//店铺ID
				dto.setName(current.getName());//店铺名字
				dto.setAddress(current.getAddress());//店铺地址
//				try {
//					OwTemplateDto owTemplateDto = templateBusiness.getCurrentHomePageOwTemplate(current.getNo(), current.getType());
//					dto.setShopPictureUrl(NET_DOMAIN_URL + owTemplateDto.getPath());// 店铺背景图
//				} catch (Exception e) {
//					_logger.info("脏数据，无效的店铺莫版图URL！");
//					dto.setShopPictureUrl(NET_DOMAIN_URL);
//				}
				dto.setShopLogoUrl(current.getShopLogoUrl());
				String shopUrl = new StringBuilder("/wap/").append(current.getNo()).append("/any/gwscIndex.htm").toString();// 店铺url
				dto.setShopUrl(shopUrl);
				result.add(dto);
			}
			// 重新封装Pagination对象
			AssPagination<FoundShopDto> newpag = new AssPagination<FoundShopDto>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(result);
			return outSucceed(newpag, true, RECOMMEND_SHOPS);
		} catch (Exception e) {
			_logger.error("店铺信息列表", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	@RequestMapping(value = "findShopsInNear")
	@ResponseBody
	public String findShopsInNear() {
		try {
			Long userId = super.getUserId();
			UserGis userGis = userGisProducer.getUserGisByUserId(userId);
			if (null == userGis) {
				return outFailure(AssConstantsUtil.UserCode.NO_USERGIS_CODE, "");
			}
			userGisProducer.updateStatus(userGis.getId(), 1);
			UserGisVo userGisVo = new UserGisVo();
			userGisVo.setUserId(userId);
			userGisVo.setLatitude(userGis.getLatitude());
			userGisVo.setLongitude(userGis.getLongitude());
			userGisVo.setNumber(200);
			List<ShopLocationDto> list = shopLocationProducer.findShopsInNear(userGisVo);
			List<ShopLocationDto> results = new ArrayList<ShopLocationDto>();
			for (ShopLocationDto current : list) {
//				if (StringUtils.isNotBlank(current.getShopNo()) && current.getType() != null) {
//					try {
//					OwTemplateDto owTemplateDto= templateBusiness.getCurrentHomePageOwTemplate(current.getShopNo(),current.getType());
//					current.setShopPictureUrl(NET_DOMAIN_URL + owTemplateDto.getPath());// 店铺背景图
//					} catch (Exception e) {
//						_logger.info("脏数据，无效的店铺莫版图URL！");
//						current.setShopPictureUrl(NET_DOMAIN_URL);
//					}
//				}
				Shop shop = this.shopBusiness.getShopByNo(current.getShopNo());
				current.setShopPictureUrl(shop.getShopLogoUrl());
				current.setShopName(shop.getName());// shop_location表shopName字段冗余
				String shopUrl = "/wap/" + current.getShopNo() + "/any/gwscIndex.htm";// 店铺url
				current.setShopUrl(shopUrl);
				results.add(current);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("my_latitude", userGis.getLatitude());
			data.put("my_longitude", userGis.getLongitude());
			data.put("shop", results);
			return outSucceed(data, true, SHOPS_IN_NEAR);
		} catch (UserException e) {
			_logger.error("查询附件的店铺失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("查询附件的店铺失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	* 推荐店铺
	* @Title: recommendShop
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return String    返回类型
	* @author talo  
	* @date 2015年9月1日 下午4:35:21
	*/
	@RequestMapping(value = "recommendShop")
	@ResponseBody
	public String recommendShop (@RequestBody JSONObject jsonObj) {
		try {
//			Long userId = jsonObj.getLong("userId");
			Long userId = null;
			Object userIdObj = jsonObj.get("userId");
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			} else  {
				userId = super.getUserId();
			}
			List<RecommendShopVo> datas = shopBusiness.getRecommendShop(userId);
			return outSucceed(datas, false, "");
		} catch (Exception e) {
			_logger.error("推荐店铺失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 免费开店
	* @Title: addFree
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @throws Exception
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月29日 下午4:47:21
	 */
	@Deprecated
	@RequestMapping(value = "addFree", method = RequestMethod.POST)
	@ResponseBody
	public String addFree(@RequestBody JSONObject jsonObj){
		
		return outFailure(AssConstantsUtil.System.INTERFACE_CLOSE, "");
		/*try {
			
			if (null == jsonObj || "".equals(jsonObj)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			String shopName = jsonObj.getString("shopName");
			if (null == shopName || "".equals(shopName)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long userId = super.getUserId();
			User user = userBusiness.getUserById(userId);
			ShopDto shop = new ShopDto();
			shop.setName(shopName);
			shop.setUserId(userId);
			shop.setCell(user.getCell());
			shopProducer.addFreeShopForApp(shop);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("新增免费店铺失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}*/
	}
	
	/**
	 * 查询店铺是否设置过联系方式/地址
	 * @param jsonObj
	 * @return
	 */
	@Deprecated
	@RequestMapping(value="isShopLocation")
	@ResponseBody
	public String isShopLocation(@RequestBody JSONObject jsonObj) {
		try {
			String shopNo = jsonObj.getString("shopNo");
			
			boolean isContact = shopBusiness.isShopLocation(shopNo);
			return outSucceed(isContact, false,"");
		} catch(ShopException e){
			_logger.error("查询店铺联系方式和地址出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("查询店铺联系方式和地址出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 查询店铺联系方式/地址
	 * @param jsonObj
	 * @return
	 */
	@Deprecated
	@RequestMapping(value="getShopLocation")
	@ResponseBody
	public String getShopLocation(@RequestBody JSONObject jsonObj) {
		try {
			String shopNo = jsonObj.getString("shopNo");
			
			ShopLocationDto shopLocationDto = shopBusiness.getShopLocation(shopNo);
			return outSucceed(shopLocationDto, true, SHOPLOCATION_FILLTER);
		} catch(ShopException e){
			_logger.error("查询店铺联系方式和地址出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("查询店铺联系方式和地址出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 添加店铺联系方式/地址
	 * @param jsonObj
	 * @return
	 */
	@Deprecated
	@RequestMapping(value="addShopLocation")
	@ResponseBody
	public String addShopLocation(@RequestBody JSONObject jsonObj) {
		try {
			String shopNo = jsonObj.getString("shopNo");
			String lat = jsonObj.getString("lat");
			String lng = jsonObj.getString("lng");
			String address = jsonObj.getString("address");
			String area = jsonObj.getString("area");
			String cell = jsonObj.getString("cell");
			
			shopBusiness.addShopLocation(shopNo,lat,lng,address,cell,area);
			return outSucceedByNoData();
		} catch(ShopException e){
			_logger.error("添加店铺联系方式和地址出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("添加店铺联系方式和地址出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 修改店铺联系方式/地址
	 * @param jsonObj
	 * @return
	 */
	@RequestMapping(value="editShopLocation")
	@ResponseBody
	public String editShopLocation(@RequestBody JSONObject jsonObj) {
		try {
			String shopNo = jsonObj.getString("shopNo");
			String lat = jsonObj.getString("lat");
			String lng = jsonObj.getString("lng");
			String address = jsonObj.getString("address");
			String area = jsonObj.getString("area");
			String cell = jsonObj.getString("cell");
			
			shopBusiness.editShopLocation(shopNo,lat,lng,address,cell,area);
			return outSucceedByNoData();
		} catch(ShopException e){
			_logger.error("编辑店铺联系方式和地址出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("编辑店铺联系方式和地址出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: shopInfo 
	 * @Description: 店铺信息修改页面初始化
	 * @return
	 * @date 2015年9月14日 下午3:50:50  
	 * @author cbc
	 */
	@RequestMapping(value="info", method=RequestMethod.POST)
	@ResponseBody
	public String shopInfo() {
		try {
			Long userId = getUserId();
			Shop shop = shopBusiness.getShopByUserId(userId);
			ShopLocation shopLocation = lbsBusiness.getShopLocationByShopNo(shop.getNo());
			if (shopLocation == null) {
				shopLocation = new ShopLocation();
			}
			Notice notice = noticeBussiness.getNoticeByShopNo(shop.getNo());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shop", shop);
			map.put("shopLocation", shopLocation);
			map.put("isShowSignPic", this.isShowSignPic(shop.getNo()));
			map.put("isNotice", shop.getIsNotice());
			map.put("noticeContent", notice != null ? notice.getNoticeContent(): null);
			return outSucceed(map, true, SHOP_INFO);
		} catch (Exception e) {
			_logger.error("查询店铺信息失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: isShowSignPic 
	 * @Description: 是否可以设置店招 
	 * @param no
	 * @return
	 * @date 2015年9月16日 下午4:41:42  
	 * @author cbc
	 */
	private boolean isShowSignPic(String shopNo) {
		TemplateVo dto = templateBusiness.getTemplateDtoByShop(shopNo, Constants.SHOP_SC_TYPE);
		AssertUtil.notNull(dto, "店铺未设置模板");
		String code = dto.getOwTemplateHp().getCode();
		return "HP_GW_WSC_V31_6".equals(code);
	}

	/**
	 * 
	 * @Title: signPicList 
	 * @Description: 店铺招牌图片列表
	 * @return
	 * @date 2015年9月14日 下午3:34:53  
	 * @author cbc
	 */
	@RequestMapping(value="signPic/list", method=RequestMethod.POST)
	@ResponseBody
	public String signPicList() {
		try {
			List<String> src = new ArrayList<String>();
			for (int i = 1; i <= 6; i++) {
				src.add("/resource/base/img/dianzhao/"+i+".jpg");
			}
			return outSucceed(src);
		} catch (Exception e) {
			_logger.error("查询店铺招牌图片列表失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: editSignPic 
	 * @Description: 更换店铺店招
	 * @param jsonObj
	 * @return
	 * @date 2015年9月14日 下午3:49:26  
	 * @author cbc
	 */
	@RequestMapping(value="signPic/edit", method=RequestMethod.POST)
	@ResponseBody
	public String editSignPic(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String signPic = jsonObj.optString("signPic");
			if (StringUtils.isBlank(signPic)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Object object = jsonObj.get("isDefaultSignPic");
			if (null == object) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Integer isDefaultSignPic = Integer.valueOf(object.toString());
			Long userId = getUserId();
			Shop oldShop = shopBusiness.getShopByUserId(userId);
			Shop shop = new Shop();
			shop.setUserId(userId);
			shop.setSignPic(signPic);
			shop.setIsDefaultSignPic(isDefaultSignPic);
			shop.setNo(oldShop.getNo());
			shopBusiness.editShop(shop);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("更新店铺店招失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: editRecruitButton 
	 * @Description: 是否显示招募按钮
	 * @param jsonObj
	 * @return
	 * @date 2015年9月14日 下午4:22:28  
	 * @author cbc
	 */
	@RequestMapping(value="recruitButton/edit", method=RequestMethod.POST)
	@ResponseBody
	public String editRecruitButton(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object object = jsonObj.get("recruitButton");
			if (null == object) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Integer recruitButton = Integer.valueOf(object.toString());
			Long userId = getUserId();
			Shop oldShop = shopBusiness.getShopByUserId(userId);
			Shop shop = new Shop();
			shop.setUserId(userId);
			shop.setNo(oldShop.getNo());
			shop.setRecruitButton(recruitButton);
			shopBusiness.editShop(shop);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("更新店铺是否显示招募按钮失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: notice 
	 * @Description: 店铺公告
	 * @param jsonObj
	 * @return
	 * @date 2015年9月21日 下午5:16:32  
	 * @author cbc
	 */
	@RequestMapping(value="notice/edit", method=RequestMethod.POST)
	@ResponseBody
	public String notice(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object object = jsonObj.get("status");
			if (null == object) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Object content = jsonObj.get("noticeContent");
			if (null == content) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String noticeContent = content.toString();
			Integer isNotice = Integer.valueOf(object.toString());
			Long userId = getUserId();
			Notice notice = new Notice();
			notice.setNoticeContent(noticeContent);
			noticeBussiness.setNotice(userId, notice, isNotice);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("更新店铺公告失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
}
