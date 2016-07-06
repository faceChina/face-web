package com.zjlp.face.web.server.user.businesscard.business.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.BusinessCardException;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.user.businesscard.business.BusinessCardBusiness;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;
import com.zjlp.face.web.server.user.businesscard.service.BusinessCardService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.util.PinYinUtil;

@Repository("businessCardBusiness")
public class BusinessCardBusinessImpl implements BusinessCardBusiness {
	
	@Autowired
	private BusinessCardService businessCardService;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private ShopService shopService;
	
	@Override
	@Transactional
	public Long addBusinessCard(BusinessCard businessCard) throws BusinessCardException {
		try {
			AssertUtil.notNull(businessCard, "参数businessCard不能为空");
			AssertUtil.notNull(businessCard.getUserId(), "参数businessCard.userId不能为空");
			AssertUtil.isTrue(this.isExistUser(businessCard.getUserId()), "不存在该用户");
			AssertUtil.notTrue(this.hasCard(businessCard.getUserId()), "该用户已经有名片不能再增加");
			if (null != businessCard.getPhone()) {
				AssertUtil.isTrue(this.checkPhone(businessCard.getPhone()), "电话号码验证不通过");
			}
			if (null != businessCard.getShopType()) {
				AssertUtil.isTrue(BusinessCard.checkShopType(businessCard.getShopType()), "shopType验证未通过");
			}
			if (null != businessCard.getDefineUrl() && businessCard.getShopType().intValue() == 4) {
				AssertUtil.isTrue(this.checkDefine(businessCard.getDefineUrl()), "自定义网址未填写正确，请填写www.o2osl.com！！");
			}
			this.initShopType(businessCard);
			businessCardService.addBusinessCard(businessCard);
			if (businessCard.getBackgroundPic() != null) {
				String url = this.uploadPic(businessCard);
				BusinessCard card = new BusinessCard();
				card.setUserId(businessCard.getUserId());
				card.setBackgroundPic(url);
				card.setId(businessCard.getId());
				this.updateBusinessCard(card);
			}
			return businessCard.getId();
		} catch (Exception e) {
			throw new BusinessCardException(e);
		}
	}

	/**
	 * 
	 * @Title: initShopType 
	 * @Description: 初始化名片店铺参数
	 * @param businessCard
	 * @date 2015年9月9日 上午10:07:13  
	 * @author cbc
	 */
	private void initShopType(BusinessCard businessCard) {
		if (businessCard.getShopType() == null) {
			SubbranchVo subbranch = subbranchService.getActiveSubbranchByUserId(businessCard.getUserId());
			if (null != subbranch) {
				businessCard.setShopType(BusinessCard.TYPE_BF);
				businessCard.setShopId(String.valueOf(subbranch.getId()));
			} else {
				Shop shop = shopService.getShopByUserId(businessCard.getUserId());
				businessCard.setShopType(BusinessCard.TYPE_P);
				businessCard.setShopId(shop.getNo());
			} 
		}
		AssertUtil.isTrue(BusinessCard.checkShopType(businessCard.getShopType()), "shopType校验未通过");
	}

	/**
	 * 
	 * @Title: uploadPic 
	 * @Description:上传图片
	 * @param businessCard
	 * @date 2015年9月7日 下午3:11:06  
	 * @author cbc
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "static-access" })
	private String uploadPic(BusinessCard businessCard) throws Exception {
		if (businessCard.getPicType() != null && businessCard.getPicType().intValue() == BusinessCard.PIC_TYPE_DEFAULT && null != businessCard.getBackgroundPic()) {
			//默认图片则将模糊图片路径入库
			return businessCard.getBackgroundPic().replace(".jpg", "_1080.jpg");
		}
		if (businessCard.getPicType() != null && businessCard.getPicType().intValue() == BusinessCard.PIC_TYPE_CUSTOM) {
			//用户上传的自定义图片上传到七牛
			List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
			if (null != businessCard.getBackgroundPic()) {
				String zoomSizes = PropertiesUtil.getContexrtParam("bussinessCardBackgroundFile");
				AssertUtil.hasLength(zoomSizes, "imgConfig.properties还未配置bussinessCardBackgroundFile字段");
				FileBizParamDto file = new FileBizParamDto(zoomSizes, businessCard.getUserId(), null, "business_card", String.valueOf(businessCard.getId()), ImageConstants.BUSSINESS_CARD_BACKGROUND_FILE, 1);
				file.setImgData(businessCard.getBackgroundPic());
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
		}
		return null;
	}

	/**
	 * 
	 * @Title: checkPhone 
	 * @Description: 验证用户电话号码合法性
	 * @param phone
	 * @return
	 * @date 2015年8月27日 下午5:08:13  
	 * @author cbc
	 */
	private boolean checkPhone(String phone) {
		if ("".equals(phone)) {
			return true;
		}
		String str = "(^(\\d{3,4}-)?\\d{7,8})$|(1[34578]{1}[0-9]{9})";
		return phone.matches(str);
	}
	
	/**
	 * @Title:checkDefine
	 * @description 验证网址是否合法
	 * @param defineUrl
	 * @return
	 * @date 2015.9.22
	 * @author jushuang
	 */
	private boolean checkDefine(String defineUrl){
		if (defineUrl.contains("www.o2osl.com")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: hasCard 
	 * @Description: 用户已经有名片，有则返回true
	 * @param userId
	 * @return
	 * @date 2015年8月26日 下午2:55:01  
	 * @author cbc
	 */
	private boolean hasCard(Long userId) {
		BusinessCard card = this.getBusinessCardByUserId(userId);
		return null != card;
	}

	/**
	 * 
	 * @Title: isExistUser 
	 * @Description: 查询是否存在该用户, 存在则返回true
	 * @param userId
	 * @return
	 * @date 2015年8月26日 上午11:58:17  
	 * @author cbc
	 */
	private boolean isExistUser(Long userId) {
		User user = userService.getById(userId);
		return user != null;
	}
	

	@Override
	public void updateBusinessCard(BusinessCard businessCard) throws BusinessCardException {
		try {
			AssertUtil.notNull(businessCard, "参数businessCard不能为空");
			AssertUtil.notNull(businessCard.getId(), "参数businessCard.id不能为空");
			AssertUtil.notNull(businessCard.getUserId(), "参数businessCard.userId不能为空");
			if (businessCard.getPhone() != null) {
				AssertUtil.isTrue(this.checkPhone(businessCard.getPhone()), "电话号码验证不通过");
			}
			if (businessCard.getBackgroundPic() != null) {
				String url = this.uploadPic(businessCard);
				businessCard.setBackgroundPic(url);
			}
			if (businessCard.getShopType() != null) {
				AssertUtil.isTrue(BusinessCard.checkShopType(businessCard.getShopType()), "shopType校验未通过");
			}
			if (businessCard.getDefineUrl() != null && businessCard.getShopType().intValue() == 4) {
				AssertUtil.isTrue(this.checkDefine(businessCard.getDefineUrl()), "自定义网址未填写正确，请填写www.o2osl.com！！");
			}
			businessCardService.updateBusinessCard(businessCard);
		} catch (Exception e) {
			throw new BusinessCardException(e);
		}
	}
	
	@Override
	public BusinessCard getBusinessCardByUserId(Long userId) throws BusinessCardException {
		try {
			AssertUtil.notNull(userId, "参数userId不能为空");
			return businessCardService.getBusinessCardByUserId(userId);
		} catch (Exception e) {
			throw new BusinessCardException(e);
		}
	}
	/**************************************名片夹******************************************************************************/
	@Override
	public boolean addCardToCase(CardCase cardCase)
			throws BusinessCardException {
		try {
			//参数验证
			AssertUtil.notNull(cardCase, "参数cardCase不能为空");
			AssertUtil.notNull(cardCase.getUserId(), "参数userId不能为空");
			AssertUtil.notNull(cardCase.getCardId(), "参数cardId不能为空");
			//数据权限验证(是否有该用户&是否有该名片&该名片是否为该用户所有)
			User user = userService.getById(cardCase.getUserId());
			AssertUtil.notNull(user, "不存在该用户");
			BusinessCard card = businessCardService.getById(cardCase.getCardId());
			AssertUtil.notNull(card, "不存在该名片");
			AssertUtil.isTrue(!card.getUserId().equals(user.getId()), "用户不能收藏自己的名片");
			//收藏该名片  拼音转化的问题
			String nickName = PinYinUtil.getPingYin(user.getNickname());
			cardCase.setUserPingyin(nickName);
			businessCardService.addCardCase(cardCase);
			return true;
		} catch (Exception e) {
			throw new BusinessCardException(e);
		}
	}

	@Override
	public boolean removeCardFromCase(Long userId, Long cardId)
			throws BusinessCardException {
		try {
			//参数验证
			AssertUtil.notNull(userId, "参数userId不能为空");
			AssertUtil.notNull(cardId, "参数cardId不能为空");
			CardCase cardCase = this.getCardCaseByUserId(userId, cardId);
			//删除
			businessCardService.deleteById(cardCase.getId());
			return true;
		} catch (Exception e) {
			throw new BusinessCardException(e);
		}
	}

	@Override
	public Pagination<BusinessCardVo> findCardPageByCase(
			Pagination<BusinessCardVo> pagination, BusinessCardVo businessCardVo)
			throws BusinessCardException {
		try {
			//参数验证
			AssertUtil.notNull(businessCardVo, "param[vo] can't be null.");
			AssertUtil.notNull(businessCardVo.getUserId(), "param[vo.userid] can't be null.");
			//查询
			Integer totalRow = businessCardService.selectCardCaseCount(businessCardVo);
			businessCardVo.getAide().setStartNum(pagination.getStart());
			businessCardVo.getAide().setPageSizeNum(pagination.getPageSize());
			List<BusinessCardVo> datas = businessCardService.queryCardCase(businessCardVo);
			pagination.setTotalRow(totalRow);
			pagination.setDatas(datas);
			return pagination;
		} catch (Exception e) {
			throw new BusinessCardException(e);
		}
	}
	
	@Override
	public List<BusinessCardVo> findCardByCase(Long userId) throws BusinessCardException{
		try {
			AssertUtil.notNull(userId, "userId不能为空！");
			return businessCardService.queryCardCase(userId);
		} catch (RuntimeException e) {
			throw new BusinessCardException(e.getMessage(), e);
		}
	}
	

	@Override
	public CardCase getCardCaseByUserId(Long userId, Long cardId)
			throws BusinessCardException {
		try {
			AssertUtil.notNull(userId, "参数userId不能为空");
			AssertUtil.notNull(cardId, "参数cardId不能为空");
			return businessCardService.getCardCaseByUserId(userId,cardId);
		} catch (Exception e) {
			throw new BusinessCardException();
		}
	}

	
	
}
