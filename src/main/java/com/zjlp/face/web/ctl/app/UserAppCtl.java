package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssPagination;
import com.zjlp.face.web.appvo.Contacts;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.exception.ext.BusinessCardException;
import com.zjlp.face.web.exception.ext.UnsupportedEmojiException;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.product.im.producer.ImFriendsProducer;
import com.zjlp.face.web.server.user.businesscard.business.BusinessCardBusiness;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.SyncUtil;
import com.zjlp.face.web.util.VerificationRegexUtil;

@Controller
@RequestMapping("/assistant/ass/user/")
public class UserAppCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	private static final String[] INFO_FILEDS = {"user.headimgurl", "user.lpNo", "user.sex", "user.nickname", "user.loginAccount", "user.myInvitationCode", "card.shopType", "card.shopId", "card.phoneVisibility",
		"card.vAreaCode", "card.vAreaName", "card.companyCode", "card.companyName", "card.position", "card.phone", "card.defineUrl", "card.industryCode", "card.industryName", "card.template",
		"card.industryProvide", "card.industryRequirement", "shop.name", "shop.no", "subbranch.subbranchName", "subbranch.id", "cardUrl"};
		
	private static final String[] CARD_FIELDS = {"user.headimgurl", "user.nickname", "user.loginAccount", "user.id", "user.circlePictureUrl",  "card.companyName", "card.position", 
		"card.phone", "cardUrl"};
	
	private static final String[] SHOW_CARD_FIELDS = {"user.headimgurl","user.sex","user.lpNo", "user.myInvitationCode", "user.nickname", "user.loginAccount", "user.id", "user.circlePictureUrl", "card.companyName", "card.position", 
		"card.phone", "card.industryName", "card.phoneVisibility", "card.shopType", "card.template", "card.industryProvide","card.industryRequirement","card.backgroundPic", "card.picType", "card.defineUrl", "cardUrl", "showPhone", "shopUrl", "isInCase"};
		
	private static final String [] FIND_USERS_FIELDS = {"curPage", "start", "pageSize", "totalRow","datas.id","datas.lpNo","datas.nickname","datas.loginAccount","datas.headimgurl"};
	
	private static final String [] CARD_CASE_FIELDS = {"loginAccount", "nickName", "headimgurl", "backgroundPic", "companyName", "position", "industryName"};
	
	private static final String [] COUNT_INVITATION_AMOUNT = {"countMyInvitationAmount","countBisInvitationAmount"};
	
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private BusinessCardBusiness businessCardBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private ImFriendsProducer imFriendsProducer;
	
	/**
	 *  用户基本资料首页
	* @Title: index
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param req_json
	* @return String    返回类型
	* @author wxn  
	* @date 2015年3月31日 下午3:30:11
	 */
	@RequestMapping(value="index")
	@ResponseBody
	public String index(@RequestBody JSONObject req_json) {
		Long userId = super.getUserId();
		User user = userBusiness.getUserById(userId);
		String fields = req_json.optString("fields");
		return outSucceed(user,fields);
	}
	
	/**
	 * 
	 * @Title: info 
	 * @Description: 个人信息修改页面初始化
	 * @return
	 * @date 2015年8月25日 上午11:26:10  
	 * @author cbc
	 */
	@RequestMapping("info")
	@ResponseBody
	public String info() {
		try {
			Long userId = super.getUserId();
			User user = userBusiness.getUserById(userId);
			BusinessCard card = businessCardBusiness.getBusinessCardByUserId(userId);
			Shop shop = shopBusiness.getShopByUserId(userId);//获取用户店铺
			SubbranchVo subbranch = subbranchBusiness.getActiveSubbranchByUserId(userId);//获取用户分店
			Map<String, Object> map = new HashMap<String, Object>();
			String cardUrl = new StringBuilder("/app/card/").append(user.getId()).append(".htm").toString();
			if (card == null) {
				card = new BusinessCard();
				if (subbranch != null) {
					card.setShopType(BusinessCard.TYPE_BF);
				} else {
					card.setShopType(BusinessCard.TYPE_P);
				}
				card.setPhoneVisibility(BusinessCard.PHONE_PUBLIC);
			} else if (card.getPhoneVisibility() == null) {
				card.setPhoneVisibility(BusinessCard.PHONE_PUBLIC);
			}
			map.put("cardUrl", cardUrl);
			map.put("user", user);
			map.put("shop", shop);
			map.put("subbranch", subbranch);
			map.put("card", card);
			return outSucceed(map, true, INFO_FILEDS);
		} catch (Exception e) {
			_logger.error("获取用户资料失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: card 
	 * @Description: 发送名片
	 * @param jsonObj
	 * @return
	 * @date 2015年8月25日 下午2:26:43  
	 * @author cbc
	 */
	@RequestMapping("card")
	@ResponseBody
	public String card(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String loginAccount = jsonObj.optString("loginAccount");
			if (StringUtils.isBlank(loginAccount)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			User user = userBusiness.getUserByLoginAccount(loginAccount);
			BusinessCard card = businessCardBusiness.getBusinessCardByUserId(user.getId());
			String cardUrl = new StringBuilder("/app/card/").append(user.getId()).append(".htm").toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cardUrl", cardUrl);
			map.put("user", user);
			map.put("card", card);
			return outSucceed(map, true, CARD_FIELDS);
		} catch (Exception e) {
			_logger.error("获取用户名片失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: backgroundList 
	 * @Description: 名片默认背景图片 
	 * @return
	 * @date 2015年9月9日 上午11:33:42  
	 * @author cbc
	 */
	@RequestMapping("card/backgroundList")
	@ResponseBody
	public String backgroundList() {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= 10; i++) {
			if (i == 5) {
				continue;
			}
			list.add("/resource/app/template/page/visitingcard/img/defaultImg/bg"+i+".jpg");
		}
		return outSucceed(list);
	}
	
	/**
	 * 
	 * @Title: showCard 
	 * @Description: 名片展示
	 * @param jsonObj
	 * @return
	 * @date 2015年8月27日 下午2:53:17  
	 * @author cbc
	 */
	@RequestMapping("showCard")
	@ResponseBody
	public String showCard(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			if (!jsonObj.containsKey("loginAccount")) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String loginAccount = jsonObj.optString("loginAccount");
			User user = userBusiness.getUserByLoginAccount(loginAccount);
			if (null == user) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			BusinessCard card = businessCardBusiness.getBusinessCardByUserId(user.getId());
			SubbranchVo subbranch = subbranchBusiness.getActiveSubbranchByUserId(user.getId());
			Shop shop = shopBusiness.getShopByUserId(user.getId());
			String shopUrl = this.initShopUrl(shop, subbranch, card);
			String cardUrl = new StringBuilder("/app/card/").append(user.getId()).append(".htm").toString();
			Map<String, Object> map = new HashMap<String, Object>();
			if (card == null) {
				card = new BusinessCard();
				if (subbranch != null) {
					card.setShopType(BusinessCard.TYPE_BF);
				} else {
					card.setShopType(BusinessCard.TYPE_P);
				}
				card.setPhoneVisibility(BusinessCard.PHONE_PUBLIC);
			} else if (card.getPhoneVisibility() == null) {
				card.setPhoneVisibility(BusinessCard.PHONE_PUBLIC);
			}
			//名片是否已收藏
			boolean isInCase = false;
			if (null != card.getId()) {
				CardCase cardcase = businessCardBusiness.getCardCaseByUserId(super.getUserId(), card.getId());
				isInCase = null != cardcase;
				_logger.info("该名片收藏状态为:"+isInCase+"，用户id为："+super.getUserId()+"，卡号为："+card.getId());
			}
			map.put("cardUrl", cardUrl);
			map.put("user", user);
			map.put("card", card);
			map.put("shopUrl", shopUrl);
			map.put("isInCase", isInCase);
			map.put("showPhone", this.isShowPhone(super.getUserId(), loginAccount, card));
			return outSucceed(map, true, SHOW_CARD_FIELDS);
		} catch (Exception e) {
			_logger.error("展示用户名片失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: initShopUrl 
	 * @Description: 初始化名片页的店铺链接 
	 * @param shop
	 * @param subbranch
	 * @param card
	 * @return
	 * @date 2015年9月7日 下午2:29:51  
	 * @author cbc
	 */
	private String initShopUrl(Shop shop, SubbranchVo subbranch,
			BusinessCard card) {
		String shopUrl = null;
		if (null == card) {
			//如果用户没有设置名片，默认展示分店，没有分店展示总店
			if (null == subbranch) {
				shopUrl = new StringBuilder("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm").toString();
			} else {
				shopUrl = new StringBuilder("/wap/").append(subbranch.getSupplierShopNo()).append("/any/gwscIndex.htm?subbranchId=").append(subbranch.getId()).toString();
			}
		} else {
			//如果用户设置了名片的展示店铺
			if (BusinessCard.TYPE_BF.equals(card.getShopType())) {
				if (null != subbranch) {
					shopUrl = new StringBuilder("/wap/").append(subbranch.getSupplierShopNo()).append("/any/gwscIndex.htm?subbranchId=").append(subbranch.getId()).toString();
				} else {
					shopUrl = new StringBuilder("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm").toString();
				}
			} else if (BusinessCard.TYPE_P.equals(card.getShopType())) {
				shopUrl = new StringBuilder("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm").toString();
			} else if (BusinessCard.TYPE_GW.equals(card.getShopType())) {
				shopUrl = new StringBuilder("/wap/").append(shop.getNo()).append("/any/index.htm").toString();
			} 
		}
		return shopUrl;
	}

	/**
	 * 
	 * @Title: isShowPhone 
	 * @Description: 是否展示手机号码
	 * @param loginUserId 当前登录的用户的userId
	 * @param cardLoginAccount 名片的用户登陆账号
	 * @param 名片
	 * @return
	 * @date 2015年9月7日 下午2:07:37  
	 * @author cbc
	 */
	private boolean isShowPhone(Long loginUserId, String cardLoginAccount, BusinessCard card) {
		boolean showPhone = false;
		if (null != card) {
			if (BusinessCard.PHONE_PUBLIC.equals(card.getPhoneVisibility())) {
				//联系方式公开
				showPhone = true;
			} else if (null != loginUserId && BusinessCard.PHONE_FRIEND.equals(card.getPhoneVisibility())) {
				//联系方式仅好友可见
				User loginUser = userBusiness.getUserById(loginUserId);
				Boolean boo = imFriendsProducer.isFriend(loginUser.getLoginAccount(), cardLoginAccount);
				showPhone = boo;
			} else if (null != loginUserId && BusinessCard.PHONE_SELF.equals(card.getPhoneVisibility())) {
				//仅自己可见
				User loginUser = userBusiness.getUserById(loginUserId);
				if (loginUser.getLoginAccount().equals(cardLoginAccount)) {
					showPhone = true;
				}
			} else if (null == card.getPhoneVisibility()) {
				showPhone = true;
			}
		} else {
			showPhone = true;
		}
		return showPhone;
	}
	/**
	 * 更新用户资料
	* @Title: updateUser
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param request
	* @param response
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年3月11日 下午4:56:35
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public String updateUser(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj ){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long userId = super.getUserId();
			JSONObject jsonStr = jsonObj.optJSONObject("user");
			if (null != jsonStr) {
				User user = JsonUtil.toBean(jsonStr.toString(), User.class);
				user.setId(userId);
				userBusiness.editProfile(userId, user.getHeadimgurl(), user.getCirclePictureUrl(), user.getNickname(),user.getSex(), user.getAreaCode(), user.getAddress(), user.getWechat(), user.getSignature(), user.getLpNo());
			}
			JSONObject businessCardJson = jsonObj.optJSONObject("card");
			if (null != businessCardJson) {
				BusinessCard card = JsonUtil.toBean(businessCardJson.toString(), BusinessCard.class);
				BusinessCard oldCard = businessCardBusiness.getBusinessCardByUserId(userId);
				if (null == oldCard) {
					synchronized(SyncUtil.getLock("USER_"+userId)){
						oldCard = businessCardBusiness.getBusinessCardByUserId(userId);
						if(oldCard==null){
							card.setUserId(userId);
							businessCardBusiness.addBusinessCard(card);
						}
					}
				} else {
					card.setId(oldCard.getId());
					card.setUserId(userId);
					businessCardBusiness.updateBusinessCard(card);
				}
			}
			return outSucceedByNoData();
		} catch (UnsupportedEmojiException ue) {
			_logger.error("不支持插入的Emoji表情,更新用户资料失败",ue);
			return outFailure(AssConstantsUtil.System.UNSUPPORTED_EMOJI_ERROR, "");
		} catch (UserException e) {
			if ("该脸谱号已被占用".equals(e.getMessage())) {
				_logger.error("脸谱号已存在",e);
				return outFailure(AssConstantsUtil.UserCode.EXIST_LP_NO, "");
			} else if ("已修改过脸谱号不能再修改".equals(e.getMessage())) {
				_logger.error("已修改过脸谱号不能再修改",e);
				return outFailure(AssConstantsUtil.UserCode.UPDATED_LP_NO, "");
			} else {
				_logger.error("更新用户资料失败",e);
				return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
			}
		} catch (Exception e) {
			_logger.error("更新用户资料失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 修改登陆密码
	* @Title: changePasswd
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年3月31日 下午3:30:38
	 */
    @RequestMapping(value = "changePasswd")
    @ResponseBody
    public String changePasswd(@RequestBody JSONObject jsonObj){
    try{
		if (null == jsonObj ){
			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
		}
        Long userId = super.getUserId();
        String oldpasswd = jsonObj.getString("old_password");
        String passwd = jsonObj.getString("passwd");
        String confirm_password = jsonObj.getString("confirm_password");
        userBusiness.editUserPasswd(userId, oldpasswd, passwd, confirm_password);
    	return outSucceedByNoData();
        }catch(UserException e){
        	_logger.error("修改密码失败",e);
        	if ("Input passwd error.".equals(e.getMessage())){
        		return outFailure(AssConstantsUtil.AccountCode.OLD_PASSWORD_CODE,"");
        	}else{
        		return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE,"");
        	}
			
        }catch(MailException e){
        	_logger.error("发送修改密码邮件失败",e);
			return outSucceedByNoData();
        }catch (Exception e) {
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE,"");
		}
    
    }
    
    /**
	 * 
	 * @Title: getUserByLpNo 
	 * @Description: 根据脸谱号查找用户
	 * @param lpNo
	 * @param start
	 * @param pageSize
	 * @return
	 * @date 2015年8月31日 下午19:20:07  
	 * @author talo
	 */
    @RequestMapping(value = "findUsers")
    @ResponseBody
	public String findUsers (@RequestBody JSONObject jsonObj,Pagination<User> pagination) {
    	try{
	    	String phoneOrlpNo = jsonObj.getString("phoneOrlpNo");
	    	Integer curPage = jsonObj.getInt("curPage");
	        Integer start = jsonObj.getInt("start");
	        Integer pageSize = jsonObj.getInt("pageSize");
	        pagination.setCurPage(curPage);
	        pagination.setStart(start);
			pagination.setPageSize(pageSize);
	        pagination = userBusiness.getUserByLpNo(phoneOrlpNo,pagination);
			
	        AssPagination<User> userPage = new AssPagination<User>();
			userPage.setCurPage(pagination.getCurPage());
			userPage.setStart(pagination.getEnd());
			userPage.setPageSize(pagination.getPageSize());
			userPage.setTotalRow(pagination.getTotalRow());
			userPage.setDatas(pagination.getDatas());
	        return outSucceed(userPage, true,FIND_USERS_FIELDS);
    	} catch (Exception e) {
  			_logger.error("查找用户失败",e);
  			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
  		}
	}
    
    @RequestMapping(value = "countInvitationAmount")
    @ResponseBody
	public String countInvitationAmount (@RequestBody JSONObject jsonObj) {
    	try{
	    	Long userId = getUserId();
	    	UserVo userVo = userBusiness.getCountInvitationAmount(userId); 
	        return outSucceed(userVo, true,COUNT_INVITATION_AMOUNT);
    	} catch (Exception e) {
  			_logger.error("统计我的邀请或二度邀请失败",e);
  			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
  		}
	}

	/**
	 * @Title: validateConntacts
	 * @Description: (验证联系人是否已经注册)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月1日 下午7:43:29
	 */
	@RequestMapping(value = "validateConntacts")
	@ResponseBody
	public String validateConntacts(@RequestBody JSONObject jsonObj) {
		try {
			StopWatch clock = new StopWatch();
			clock.start();
			if (null == jsonObj) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			List<Contacts> list = new ArrayList<Contacts>();
			// [{id1,name1,phone1},{id2,name2,phone2},{id3,name3,phone3}]
			JSONArray contactList = jsonObj.getJSONArray("contactList");
			if (CollectionUtils.isEmpty(contactList)) {
				return outFailure(AssConstantsUtil.UserCode.NULL_CONTACTS_CODE, "");
			} else {
				for (Object arrayObj : contactList.toArray()) {
					if (arrayObj != null) {
						if (VerificationRegexUtil.newPhoneVer(arrayObj.toString())) {
							Contacts contact = new Contacts();
//							User user = this.userBusiness.getUserByLoginAccount(arrayObj.toString());
							User user = this.userBusiness.getByLoginAccountRedis(arrayObj.toString());
							if (user != null && user.getId() != null) {
								contact.setUserId(user.getId());// 用户ID
								contact.setPhoneNo(user.getLoginAccount());// 手机号
								contact.setName(StringUtils.isNotBlank(user.getNickname()) ? user.getNickname() : user.getLoginAccount());// 注册昵称
								contact.setType(1);// 已经注册
								contact.setLpNo(user.getLpNo());// 刷脸号
								contact.setHeadimgurl(user.getHeadimgurl());// 头像
								list.add(contact);
							} 
						} else {
							_logger.error("不符合格式的联系人：" + arrayObj.toString());
						}
					}
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contacts", list);
			clock.stop();
			_logger.info("验证[" + contactList.size() + "]个联系人耗时[" + clock.getTime() + "]毫秒 =[" + ((double) clock.getTime()) / 1000+ "]秒");
			return outSucceed(map, false, StringUtils.EMPTY);
		} catch (Exception e) {
			_logger.error("验证联系人失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	@RequestMapping(value = "addCardToCase")
	@ResponseBody
	public String addCardToCase(@RequestBody JSONObject jsonObj) {
		try {
			Long userId = jsonObj.getLong("userId");
			BusinessCard businessCard = businessCardBusiness.getBusinessCardByUserId(userId);
			if(null==businessCard){
				BusinessCard busCard = new BusinessCard();
				busCard.setUserId(userId);
				Long id = businessCardBusiness.addBusinessCard(busCard);
				CardCase cardCase = new CardCase(super.getUserId(), id);
				businessCardBusiness.addCardToCase(cardCase);
				return outSucceedByNoData();
			} 
			CardCase cardCase = new CardCase(super.getUserId(), businessCard.getId());
			businessCardBusiness.addCardToCase(cardCase);
			return outSucceedByNoData();
		} catch (BusinessCardException e) {
			if (CErrMsg.ALREADY_EXISTS.getErrCd().equals(e.getMessage())) {
				return outFailure(AssConstantsUtil.BusinessCard.CARDCASE_ALREADY_EXISTS);
			}
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE);
		}
	}
	
	@RequestMapping(value = "removeCardCase")
	@ResponseBody
	public String removeCardCase(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long userId = jsonObj.getLong("userId");
			BusinessCard businessCard = businessCardBusiness.getBusinessCardByUserId(userId);
			if(null==businessCard){
				return outFailure(AssConstantsUtil.BusinessCard.BUSSINESS_CARD_ERROR);
			}
			businessCardBusiness.removeCardFromCase(getUserId(), businessCard.getId());
			return outSucceedByNoData();
		} catch (UserException e) {
			_logger.error("删除失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("删除失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @param jsonObj
	 * @return
	 */
	@RequestMapping(value = "queryCardCase")
	@ResponseBody
	public String queryCardCase(@RequestBody JSONObject jsonObj) {
		try{
			Pagination<BusinessCardVo> pagination = super.initPagination(jsonObj);
			Long userId = super.getUserId();
			//用户输入查询条件
			String condition = jsonObj.getString("condition");
			BusinessCardVo businessCardVo = new BusinessCardVo();
			businessCardVo.setUserId(userId);
			businessCardVo.setCondition(condition);
			pagination = businessCardBusiness.findCardPageByCase(pagination, businessCardVo);
			pagination.setStart(pagination.getEnd());
			return super.outSucceed(pagination, true, CARD_CASE_FIELDS);
		} catch (Exception e) {
			_logger.error("查找失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	@RequestMapping(value = "queryList")
	@ResponseBody
	public String queryList(@RequestBody JSONObject jsonObj){
		try {
			Long userId = super.getUserId();
			//用户输入查询条件
			List<BusinessCardVo> list = businessCardBusiness.findCardByCase(userId);
			return super.outSucceed(list, true, CARD_CASE_FIELDS);
		} catch (Exception e) {
			_logger.error("查找失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
//	public static void main(String[] args) {
//		List<BusinessCardVo> list = new ArrayList<BusinessCardVo>();
//		BusinessCardVo vo = new BusinessCardVo();
//		vo.setLoginAccount("65464654656");;
//		vo.setNickName("asdfadf");
//		vo.setHeadimgurl("12451341234");
//		list.add(vo);
//		JSONInfo<List<BusinessCardVo>> info = new JSONInfo<List<BusinessCardVo>>();
//		info.setCode(AssConstantsUtil.REQUEST_OK_CODE);
//		info.setMsg("");
//		info.setJsessionid("12341234");
//		info.setData(list);
//		System.out.println(info.toJsonString(CARD_CASE_FIELDS));
//	}
	
}
