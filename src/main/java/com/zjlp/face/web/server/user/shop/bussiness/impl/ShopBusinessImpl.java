package com.zjlp.face.web.server.user.shop.bussiness.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.ShopException;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.FoundShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.shop.domain.vo.RecommendShopVo;
import com.zjlp.face.web.server.user.shop.factory.ShopFactory;
import com.zjlp.face.web.server.user.shop.service.AuthorizationCodeService;
import com.zjlp.face.web.server.user.shop.service.LbsService;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
import com.zjlp.face.web.server.user.user.service.VaearService;

@Service
public class ShopBusinessImpl implements ShopBusiness{
	
	/** 未使用 */
	private final static Integer CODE_UNUSED = 0;
	
	/** 已使用 */
	private final static Integer CODE_USED = 1;
	
	/** 无此授权码 */
	private final static Integer CODE_INVALID = -1;
	
	
	private Logger _log = Logger.getLogger(this.getClass());
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopFactory commonShopFactory;
	
	@Autowired
	private ShopFactory freeShopFactory;
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private AuthorizationCodeService authorizationCodeService;
	@Autowired(required = false)
	private ShopExternalService shopExternalService;
	@Autowired
	private UserProducer userProducer;
	@Autowired
	private LbsService lbsService;
	@Autowired
	private VaearService vaearService;
	
	@Override
	public Shop addCommonShop(ShopDto dto) throws ShopException{
		return commonShopFactory.addShop(dto);
	}

	@Override
	public Shop getShopByNo(String shopNo){
		return shopService.getShopByNo(shopNo);
	}
	
	@Override
	public List<Shop> findShopListByUserId(Long userId){
		return shopService.findShopListByUserId(userId);
	}
	
	@Override
	public void editShop(Shop shop){
		AssertUtil.notNull(shop, "参数shop不能为空");
		if (shop.getSignPic() != null && shop.getIsDefaultSignPic() == Shop.SIGN_PIC_UPLOAD) {
			String url = this.uploadSignPic(shop);
			shop.setSignPic(url);
		}
		shopService.editShop(shop);
	}
	

	/**
	 * 
	 * @Title: uploadSignPic 
	 * @Description: 店招图片上传到图片服务器 
	 * @param shop
	 * @return
	 * @date 2015年9月14日 下午3:05:28  
	 * @author cbc
	 */
	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	private String uploadSignPic(Shop shop) {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		if (null != shop.getSignPic()) {
			String zoomSizes = PropertiesUtil.getContexrtParam("shopSignPic");
			AssertUtil.hasLength(zoomSizes, "imgConfig.properties还未配置shopSignPic字段");
			FileBizParamDto file = new FileBizParamDto(zoomSizes, shop.getUserId(), shop.getNo(), "shop", shop.getNo(), ImageConstants.SHOP_SIGN_PIC, 1);
			file.setImgData(shop.getSignPic());
			list.add(file);
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "ShopException" })
	public Integer activateShop(String code, String loginAccount){
		try{
			AuthorizationCode authorizationCode = authorizationCodeService.getAuthorizationCodeByCode(code);
			if(null == authorizationCode){
				return CODE_INVALID;//无此授权码
			}
			//授权码未使用
			if(CODE_UNUSED.intValue() == authorizationCode.getStatus()){
				Shop shop = shopService.getShopByNo(authorizationCode.getSourceShopNo());
				ShopDto dto = new ShopDto();
				dto.setAuthorizationCode(code);
				dto.setLoginAccount(loginAccount);
				dto.setAuthorizationCodeType(authorizationCode.getType());
				dto.setType(authorizationCode.getShopTypeByAuthorationCodeType());
				if(Constants.SHOP_GW_TYPE.intValue() == authorizationCode.getShopTypeByAuthorationCodeType()){
					dto.setName("官网");
				}else if(2 == authorizationCode.getType()){
					dto.setName("商城(内)");
				}else if(3 == authorizationCode.getType()){
					dto.setName("商城(外)");
				}else{
					throw new ShopException("授权码类型错误");
				}
				dto.setOnInvitationCode(shop.getInvitationCode());
				Date date = new Date();
				dto.setActivationTime(date);
				dto.setSigningTime(date);
				Shop newShop = commonShopFactory.addShop(dto);
				AuthorizationCode ac = new AuthorizationCode();
				ac.setId(authorizationCode.getId());
				ac.setDestinationShopNo(newShop.getNo());
				ac.setStatus(CODE_USED);
				ac.setActivationTime(date);
				ac.setUpdateTime(date);
				authorizationCodeService.editById(ac);
			}
			return authorizationCode.getStatus();
		}catch(Exception e){
			throw new ShopException(e);
		}
	}
	
	@Override
	public Shop getShopByInvitationCode(String invitationCode){
		return shopService.getShopByInvitationCode(invitationCode);
	}

	@Override
	public String checkDate(String no) throws ShopException {
    	try {
			Assert.hasLength(no, "商铺编号为空，查询失败！");
 	        Shop shop = shopService.getShopByNo(no);
 	        Date date = new Date();
 	        if(shop.getEffectiveTime().getTime() > date.getTime()){
 	            return "1";
 	        }
 	        return "0";
 		} catch (Exception e) {
 			e.printStackTrace();
 			return "0";
 		}
	}
	
	@Override
	public boolean checkIntoShop(String no, Long userId)
			throws ShopException {
		try {
			AssertUtil.notNull(no, "商铺编号为空，查询失败！");
			AssertUtil.notNull(userId, "用户ID为空");
            Date date = new Date();
 	        Shop shop = shopService.getShopByNo(no);
 	        if(shop.getEffectiveTime().getTime() < date.getTime()){
				_log.info("产品已过期");
 	         return false;
 	        }
            List<Shop> shopList =  shopService.findShopListByUserId(userId);
            for(Shop sp : shopList){
                if(no.equals(sp.getNo())){
                	return  true;
                }
            }
		} catch (Exception e) {
			_log.info("进入产品失败" + e);
			return false;
		}
		return false;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void bindshop(Shop shop) {
		
		AssertUtil.notNull(shop.getNo(), "店铺编号为空");
		if(StringUtils.isNotBlank(shop.getWechat()) && 
		   StringUtils.isNotBlank(shop.getPasswd())) {
			com.zjlp.face.shop.dto.ShopDto dto = new com.zjlp.face.shop.dto.ShopDto();
			dto.setNo(shop.getNo());
			dto.setWechat(shop.getWechat());
			dto.setPasswd(shop.getPasswd());
			shopExternalService.bindShop(dto);
		}
		shopService.editShop(shop);
	}

	@Override
	public Long findSellerIdByShopNo(String shopNo) {
		return this.shopService.findSellerIdByShopNo(shopNo);
	}

	@Override
	public Pagination<Shop> getFoundShop(FoundShopDto shopCriteria,
			Pagination<Shop> pagination) throws ShopException {
		// TODO AssertUtil.notNull(shopCriteria, "shopCriteria为空，无法查询", "找不到店铺");
		Pagination<Shop> foundShop = shopService.getFoundShop(shopCriteria, pagination);
		return foundShop;
	}
	@Override
	public Pagination<Shop> findRecruitShop(Pagination<Shop> pagination, String keyword) {
		return this.shopService.findRecruitShop(pagination, keyword);
	}


	@Override
	public Shop getShopByUserId(Long userId) throws ShopException{
		try {
			AssertUtil.notNull(userId, "参数用户id为空");
			return shopService.getShopByUserId(userId);
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}
	@Override
	public Shop getShopByAuthCode(String code) {
		return shopService.getShopByAutoCode(code);
	}

	@Override
	public List<ShopDto> findShopList(Long userId, String shopNo,String shopName) throws ShopException {
		Assert.notNull(userId, "参数[userId]不能为空");
		try {
			List<ShopDto> list = shopService.findShopList(userId, shopNo, shopName);
			return list;
		} catch (Exception e) {
			throw new ShopException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void unbindshop(Shop shop) throws ShopException{
		try {
			AssertUtil.notNull(shop.getNo(), "店铺编号为空");
			if(StringUtils.isBlank(shop.getWechat())) {
				throw new ShopException("请输入公众号");
			}
			if(StringUtils.isBlank(shop.getPasswd())) {
				throw new ShopException("请输入公众号密码");
			}
			
			Shop model = shopService.getShopByNo(shop.getNo());
			AssertUtil.notNull(model, "没有查询到该店铺");
			if(!model.getWechat().equals(shop.getWechat())){
				throw new ShopException("公众号:" + shop.getWechat() + "没有绑定该该店铺");
			}
			if(!model.getPasswd().equals(shop.getPasswd())){
				throw new ShopException("公众号:" + shop.getWechat() + ",密码错误,请重新输入");
			}
			
			Date time = new Date();
			shop.setWechat("");
			shop.setPasswd("");
			shop.setAppId("");
			shop.setAppSecret("");
			shop.setAuthenticate(Constants.AUTHENTICATE_TYPE_NO);
			shop.setUpdateTime(time);
			shop.setBindingTime(time);
			shopService.editShop(shop);
		} catch (Exception e) {
			throw new ShopException(e.getMessage(),e);
		}
	}

	@Override
	public List<RecommendShopVo> getRecommendShop(Long userId) throws ShopException {
		try {
			AssertUtil.notNull(userId, "用户ID为空");
			return shopService.getRecommendShop(userId);
		} catch (Exception e) {
			throw new ShopException(e.getMessage(),e);
		}
	}

	@Override
	public boolean isShopLocation(String shopNo) throws ShopException {
		try {
			AssertUtil.hasLength(shopNo, "店铺NO不可为空");
			boolean isCell = shopService.isShopCell(shopNo);
			if(isCell){
				ShopLocation shopLocation = lbsService.getShopLocationByShopNo(shopNo);
				if(shopLocation == null){
					return false;
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			_log.error("查询店铺联系方式和地址出错:" + e.getMessage(), e);
			throw new ShopException(e.getMessage(),e);
		}
	}

	@Override
	public ShopLocationDto getShopLocation(String shopNo) throws ShopException {
		try {
			AssertUtil.hasLength(shopNo, "店铺NO不可为空");
			
			Shop shop = shopService.getShopByNo(shopNo);
			AssertUtil.notNull(shop, "没有该店铺");
			
			ShopLocationDto shopLocationDto = lbsService.getShopLocationDtoByShopNo(shopNo);
			AssertUtil.notNull(shopLocationDto, "该店铺未设置地址");
			
			shopLocationDto.setCell(shop.getCell());
			
			return shopLocationDto;
		} catch (Exception e) {
			_log.error("查询店铺联系方式和地址出错:" + e.getMessage(), e);
			throw new ShopException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public void addShopLocation(String shopNo, String lat, String lng,String address, String cell, String area) throws ShopException {
		try {
			AssertUtil.hasLength(shopNo, "店铺NO不可为空");
			AssertUtil.hasLength(lat, "店铺纬度不可为空");
			AssertUtil.hasLength(lng, "店铺经度不可为空");
			AssertUtil.hasLength(address, "店铺地址不可为空");
			AssertUtil.hasLength(cell, "店铺联系方式不可为空");
			AssertUtil.hasLength(area, "店铺区域名称不可为空");
			
			Shop shop = shopService.getShopByNo(shopNo);
			AssertUtil.notNull(shop, "没有该店铺");
			
			ShopLocation shopLocation = lbsService.getShopLocationByShopNo(shopNo);
			AssertUtil.isNull(shopLocation, "该店铺已设置过地址");
			
			shopLocation = new ShopLocation();

			Date now = new Date();
			
			shop.setCell(cell);
			shop.setUpdateTime(now);
			shopService.editShop(shop);
			
			shopLocation.setAddress(address);
			shopLocation.setLat(lat);
			shopLocation.setLng(lng);
			shopLocation.setCreateTime(now);
			shopLocation.setUpdateTime(now);
			shopLocation.setShopName(shop.getName());
			shopLocation.setShopNo(shopNo);
			
			//查询地区编码信息
			Integer areaCode = vaearService.getAreaByAreaName(area);
			if(areaCode != null){
				shopLocation.setAreaCode(areaCode);
			}
			
			lbsService.addShopLocation(shopLocation);
		} catch (Exception e) {
			_log.error("添加店铺联系方式和地址出错:" + e.getMessage(), e);
			throw new ShopException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public void editShopLocation(String shopNo, String lat, String lng,String address, String cell,String area) throws ShopException {
//		try {
//			AssertUtil.hasLength(shopNo, "店铺NO不可为空");
//			
//			Shop shop = shopService.getShopByNo(shopNo);
//			AssertUtil.notNull(shop, "没有该店铺");
//			
//			Date now = new Date();
//			
//			ShopLocation shopLocation = null;
//			
//			if(StringUtils.isNotBlank(lat) || StringUtils.isNotBlank(lng) || StringUtils.isNotBlank(address)){
//				AssertUtil.hasLength(lat, "店铺纬度不可为空");
//				AssertUtil.hasLength(lng, "店铺经度不可为空");
//				AssertUtil.hasLength(address, "店铺地址不可为空");
//				AssertUtil.hasLength(area, "店铺区域名称不可为空");
//				
//				shopLocation = lbsService.getShopLocationByShopNo(shopNo);
//				AssertUtil.notNull(shopLocation, "该店铺没有设置地址");
//				
//				shopLocation.setLat(lat);
//				shopLocation.setLng(lng);
//				shopLocation.setAddress(address);
//				shopLocation.setUpdateTime(now);
//				
//				//查询地区编码信息
//				Integer areaCode = vaearService.getAreaByAreaName(area);
//				if(areaCode != null){
//					shopLocation.setAreaCode(areaCode);
//				}
//				
//				lbsService.editShopLocation(shopLocation);
//			}
//			
//			if(StringUtils.isNotBlank(cell)){
//				shop.setCell(cell);
//				shop.setUpdateTime(now);
//				shopService.editShop(shop);
//			}
//		} catch (Exception e) {
//			_log.error("编辑店铺联系方式和地址出错:" + e.getMessage(), e);
//			throw new ShopException(e.getMessage(),e);
//		}
		
		try {
			AssertUtil.hasLength(shopNo, "店铺NO不可为空");
			AssertUtil.hasLength(lat, "店铺纬度不可为空");
			AssertUtil.hasLength(lng, "店铺经度不可为空");
			AssertUtil.hasLength(address, "店铺地址不可为空");
			AssertUtil.hasLength(area, "店铺区域名称不可为空");
			AssertUtil.hasLength(cell, "店铺联系方式不可为空");
			
			Shop shop = shopService.getShopByNo(shopNo);
			AssertUtil.notNull(shop, "没有该店铺");
			
			Date now = new Date();
			
			ShopLocation shopLocation = lbsService.getShopLocationByShopNo(shopNo);
			
			boolean isAdd = false;
			
			if(shopLocation == null){
				isAdd = true;
				shopLocation = new ShopLocation();
				shopLocation.setCreateTime(now);
			}
			
			shop.setCell(cell);
			shop.setUpdateTime(now);
			shopService.editShop(shop);
			
			shopLocation.setAddress(address);
			shopLocation.setLat(lat);
			shopLocation.setLng(lng);
			shopLocation.setUpdateTime(now);
			shopLocation.setShopName(shop.getName());
			shopLocation.setShopNo(shopNo);
			
			//查询地区编码信息
			Integer areaCode = null;
					
			try {
				areaCode =vaearService.getAreaByAreaName(area);
			} catch (Exception e) {
				_log.error("查询地址编码信息出错,出现脏数据:" + e.getMessage(), e);
			}
			
			if(areaCode != null){
				shopLocation.setAreaCode(areaCode);
			}
			
			if(isAdd){
				lbsService.addShopLocation(shopLocation);
			}else{
				lbsService.editShopLocation(shopLocation);
			}
			
		} catch (Exception e) {
			_log.error("编辑店铺联系方式和地址出错:" + e.getMessage(), e);
			throw new ShopException(e.getMessage(),e);
		}
	}
}
