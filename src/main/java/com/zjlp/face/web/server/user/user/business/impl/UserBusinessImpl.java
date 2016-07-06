package com.zjlp.face.web.server.user.user.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.UnsupportedEmojiException;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.payment.producer.PopularizeCommissionRecordProducer;
import com.zjlp.face.web.server.user.security.producer.RoleProducer;
import com.zjlp.face.web.server.user.security.service.PermissionService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserInvitationVo;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.server.user.user.service.UserService;

@Service("userBusiness")
public class UserBusinessImpl implements UserBusiness {

	private static final String MAX_INVITATION_LEVEL = "maxInvitationLevel";

	private Logger _log = Logger.getLogger(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private AccountProducer accountProducer;
	@Autowired
	private RoleProducer roleProducer;
	@Autowired(required=false)
	private ImageService imageService;
	@Autowired
	private ShopService shopService;
	//全民推广未注册用户注册时处理佣金接口
	@Autowired
	private PopularizeCommissionRecordProducer popularizeCommissionRecordProducer;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void wechatRegisterUser(String openId) throws UserException {
		try {
			AssertUtil.notNull(openId, "参数用户openId为空");
			// 根据用户openid查询是对应的微信用户
			User user = userService.getWechatUserByOpenId(openId);
//			 User user = userService.getUserByOpenId(openId);// TODO
			// 与预约注册字段保持一致
			// 用户不存在时，生产一个
			if (user == null) {
				User u = new User();
				u.setRegisterSourceUserId(openId);
//				u.setOpenId(openId);// TODO 与预约注册字段保持一致
				userService.wechatRegisterUser(u);
				// 添加权限
				roleProducer.addUserRoleRelation(u.getId());
				// 添加钱包
				accountProducer.addUserAccount(openId, u.getId(), Account.REMOTE_TYPE_USER);
				_log.info("微信注册用户成功,opneId = [" + openId + "]");
			} else {
				_log.info("微信用户已经注册,openId = " + openId);
			}
		} catch (AccountException ae) {
			_log.error("微信注册用户添加账户失败", ae);
			throw new AccountException(ae);
		} catch (SecurityException se) {
			_log.error("微信注册用户权限赋予失败", se);
			throw new SecurityException(se);
		} catch (Exception e) {
			_log.error("微信注册添加用户用户失败", e);
			throw new UserException(e);
		}
	}

	@Override
	public User getWechatUserByOpenId(String openId) {
		return userService.getWechatUserByOpenId(openId);
	}
	
	
	@Override
	public UserInfo getUserInfo(String username, String password)
			throws UserException {

		try {
			Assert.notNull(username, "用户为空！");
			Assert.notNull(password, "密码为空！");
			User user = userService.getUserByNameAndPasswd(username, password);
			if (null == user) {
				return null;
			}
			UserInfo userInfo = new UserInfo();

			List<String> roleIdList = permissionService
					.findAllPermissionByUserId(user.getId());
			List<Shop> shopList = null;

			userInfo.setLoginAccount(user.getLoginAccount());
			userInfo.setPasswd(user.getPasswd());
			userInfo.setToken(user.getToken());
			userInfo.setShopNoList(shopList);
			userInfo.setRoleList(roleIdList);
			userInfo.setType(1);
			userInfo.setUserId(user.getId());
			return userInfo;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public UserInfo getUserInfo(String username) throws UserException {

		try {
			Assert.notNull(username, "用户为空！");
			User user = userService.getUserByName(username);
			if (null == user) {
				return null;
			}
			UserInfo userInfo = new UserInfo();

			List<String> roleIdList = permissionService
					.findAllPermissionByUserId(user.getId());
			List<Shop> shopList = null;

			userInfo.setLoginAccount(user.getLoginAccount());
			userInfo.setPasswd(user.getPasswd());
			userInfo.setToken(user.getToken());
			userInfo.setShopNoList(shopList);
			userInfo.setRoleList(roleIdList);
			userInfo.setType(1);
			userInfo.setUserId(user.getId());
			return userInfo;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	/**
	 * 对密码进行加密
	 * 
	 * @Title: encyptPasswd
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param password
	 *            未加密的密码
	 * @return
	 * @date 2015年3月16日 下午2:40:32
	 * @author lys
	 */
	private String encyptPasswd(String password) {
		return DigestUtils.jzShaEncrypt(password);
	}

	
	@Override
	@Transactional
	public boolean editProfile(Long userId, String headimgurl, String nickname,
			String email) throws UserException {
		try {
			Assert.notNull(userId, "Param[userId] can not be null.");
			User user = new User(userId);
			if (StringUtils.isNotBlank(headimgurl)) {
				headimgurl = this.uploadPiceture(userId, headimgurl, true);
				user.setHeadimgurl(nvlString(headimgurl));
			}
			user.setNickname(nvlString(nickname));
			user.setEmail(nvlString(email));
			userService.edit(user);
			return true;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}
	
    /**
     * 上传图片	
     * @Title: uploadPiceture 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param userId
     * @param headimgurl
     * @param isHeadimgurl : true表示上传头像 false表示上传生意圈封面
     * @return
     * @date 2015年3月26日 下午3:23:15  
     * @author lys
     */
	@SuppressWarnings("all")
	@Transactional
	private String uploadPiceture(Long userId, String headimgurl, boolean isHeadimgurl) {
		//上传图片到TFS
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
        FileBizParamDto dto = new FileBizParamDto();
        dto.setImgData(headimgurl);
		dto.setZoomSizes(isHeadimgurl ? "400_400" : "640_400");
        dto.setUserId(userId);
        dto.setShopNo(null);
        dto.setTableName("USER");
        dto.setTableId(String.valueOf(userId));
		dto.setCode(isHeadimgurl ? ImageConstants.HEAD_IMG_FILE : ImageConstants.CIRCLE_COVER_FILE);
        dto.setFileLabel(1);
        list.add(dto);
        String flag = imageService.addOrEdit(list);
        //数据解析
        JSONObject jsonObject = JSONObject.fromObject(flag);
        AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
        String dataJson = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(dataJson);
        List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
        return fbpDto.get(0).getImgData();
	}
	
	private static String nvlString(String str) {
		return null == str ? "" : str;
	}

	@Override
	public boolean editUserPasswd(Long userId, String newPasswd,
			String confirmPasswd) throws UserException {
		try {
			Assert.notNull(userId, "Param[userId] can not be null.");
			Assert.hasLength(newPasswd, "Param[newPasswd] can not be null.");
			Assert.hasLength(confirmPasswd, "Param[confirmPasswd] can not be null.");
			//验证
			newPasswd = newPasswd.trim();
			confirmPasswd = confirmPasswd.trim();
			AssertUtil.isTrue(Constants.MIN_PASSWD_LEN.compareTo(newPasswd.length()) <= 0 ,
					"The length{} of passwd{} is less than 6", newPasswd.length(), newPasswd);
			AssertUtil.isTrue(newPasswd.equals(confirmPasswd), "newPasswd[{}] != confirmPasswd[{}].",
					"确认密码输入错误", newPasswd, confirmPasswd);
			userService.editPassWd(userId, this.encyptPasswd(newPasswd));
			return true;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public boolean editUserPasswd(Long userId, String oldPasswd,
			String newPasswd, String confirmPasswd) throws UserException {
		try {
			Assert.notNull(userId, "Param[userId] can not be null.");
			Assert.hasLength(oldPasswd, "Param[oldPasswd] can not be null.");
			Assert.hasLength(newPasswd, "Param[newPasswd] can not be null.");
			Assert.hasLength(confirmPasswd, "Param[confirmPasswd] can not be null.");
			//验证
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists, edit password faild.", userId);
			oldPasswd = this.encyptPasswd(oldPasswd.trim());
			AssertUtil.isTrue(oldPasswd.equals(user.getPasswd()), "Input passwd error.", "密码输入错误");
			this.editUserPasswd(userId, newPasswd, confirmPasswd);
			return true;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public User getUserById(Long userId) throws UserException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			User user = userService.getById(userId);
			return user;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}
	
    @Override
	public boolean editUserCell(Long userId, String oldCell, String newCell)
			throws UserException {
    	try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(oldCell, "Param[oldCell] can not be null.");
			AssertUtil.notNull(newCell, "Param[newCell] can not be null.");
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists.", userId);
			AssertUtil.isTrue(oldCell.equals(user.getCell()), "User[id={}]'s old cell is {}, but not {}", 
					userId, oldCell, newCell);
			userService.editCell(userId, newCell);
			return true;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public boolean editUserCell(Long userId, String cell) throws UserException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(cell, "Param[cell] can not be null.");
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists.", userId);
			userService.editCell(userId, cell);
			return true;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "UserException" })
    public String register(UserVo userVo) throws UserException{
        try {
        	Assert.notNull(userVo);
        	Assert.notNull(userVo.getPhone(), "手机号为空");
            Assert.notNull(userVo.getPhone(),"密码为空");
            
            /**  用户名与密码相等*/
    		if(userVo.getPhone().equals(userVo.getPwd())){
    		    return "用户名与密码不能相同！";
    		}
    		/**  验证码错误*/
    		if(0 == userVo.getFlag()){
    		    return "手机验证码错误！";
    		}
    		/** 验证码超时*/
    		if(3 == userVo.getFlag()){
    		    return "手机验证码超时！";
    		}
			/** 判断用户是否存在，防止重复注册*/
			User checkUser = userService.getAllUserByLoginAccount(userVo.getPhone());
			if (null != checkUser) {
				return "用户帐号重复";
			}
			Long userId = userService.add(userVo);
			roleProducer.addUserRoleRelation(userId);
			
			// 初始化钱包
			accountProducer.addUserAccount(userId, userVo.getPhone(), null, null);

			//处理全民推广未注册用户 注册时 佣金
			User user = userService.getById(userId);
			popularizeCommissionRecordProducer.registeredManageRecord(user);
			
		    return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "系统繁忙";
		}
    }

    @Override
    public String checkLoginAccount(String phone) throws UserException{
    	try {
    	    Assert.notNull(phone);
    	    User user = userService.getUserByName(phone);
            if(null == user){
                return "true";
            }
		} catch (UserException e) {
			throw new UserException(e);
		}
    	 return "false";
    }
    
	@Override
	public String checkUserPhone(String loginAccount)
			throws UserException {
		try {
			Assert.notNull(loginAccount);
		    User user = userService.getUserByName(loginAccount);
	        if(null == user){
	            return "该帐号不存在！";
	        }
	    	return "SUCCESS";
		} catch (Exception e) {
			return "请输入帐号！";
		}
	}
	
	@Override
	public String checkUserPhone(String loginAccount,String phone)
			throws UserException {
		try {
			Assert.notNull(loginAccount);
			Assert.notNull(phone);
		    User user = userService.getUserByName(loginAccount);
	        if(null == user){
	            return "该帐号不存在！";
	        }
	        if(!phone.equals(user.getCell())){
	        	return "绑定的手机号码输入不正确！";
	        }
	    	return "SUCCESS";
		} catch (Exception e) {
			return "请输入帐号和绑定手机号！";
		}
	}

	@Override
	public User getUserByLoginAccount(String loginAccount) throws UserException {
		try {
			AssertUtil.notNull(loginAccount, "Param[loginAccount] can not be null.");
			User user = userService.getUserByName(loginAccount);
			return user;
		} catch (UserException e) {
			throw new UserException(e);
		}
	}

	@Override
	public User getByLoginAccountRedis(String loginAccount) throws UserException {
		try {
			AssertUtil.notNull(loginAccount, "Param[loginAccount] can not be null.");
			User user = userService.getByLoginAccountRedis(loginAccount);
			return user;
		} catch (UserException e) {
			throw new UserException(e);
		}
	}

	@Override
	public User getUserByLoginAccountAndPasswd(String loginAccount, String passwd)  throws UserException {
		
		try {
			AssertUtil.notNull(loginAccount, "Param[loginAccount] can not be null.");
			AssertUtil.notNull(passwd, "Param[passwd] can not be null.");
			//根据登录名跟密码查询用户
			passwd = DigestUtils.jzShaEncrypt(passwd);
			return userService.getUserByNameAndPasswd(loginAccount, passwd);
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void registerAnonymousUser(String openId) throws UserException{
		
		try {
			AssertUtil.notNull(openId, "参数用户openid为空");
			// 根据用户openid查询是对应的匿名用户
			User user = userService.getUserByOpenId(openId);
			//用户不存在时，生产一个
			if(null == user) {
				User u = new User();
				u.setOpenId(openId);
				userService.addAnonymousUser(u);
				//添加权限
				roleProducer.addUserRoleRelation(u.getId());
				// TODO 添加钱包,正常登录
//				 accountProducer.addUserAccount(openId, u.getId(),
//				 Account.REMOTE_TYPE_USER);
			}
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public User getUserByOpenId(String openId) {
		return userService.getUserByOpenId(openId);
	}

	@Override
	public void registerForFreeShop(User user, byte[] pic) throws UserException {
        try {
        	AssertUtil.notNull(user.getNickname(), "用户昵称为空");
        	AssertUtil.notNull(user.getPasswd(), "用户密码为空");
        	AssertUtil.notNull(user.getCell(), "手机号（用户登陆名）为空");
			// 判断用户是否存在，防止重复注册
			User checkUser = userService.getAllUserByLoginAccount(user.getCell());
			AssertUtil.isTrue(null == checkUser, "请勿重复注册用户","请勿重复注册用户!");
			user.setLoginAccount(user.getCell());
		    userService.add(user);
		    AssertUtil.notNull(user.getId(), "注册用户失败!");
		    // 初始化钱包
		    accountProducer.addUserAccount(user.getId(), user.getCell(), null, null);
		    // 给用户添加权限
		    roleProducer.addUserRoleRelation(user.getId());
		    // 上传图片
		    if(null != pic){
				String headPicture = imageService.upload(pic);
				headPicture = this.uploadPiceture(user.getId(), headPicture, true);
				user.setHeadimgurl(nvlString(headPicture));
				userService.edit(user);
			}
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	@Transactional
	public void editFreeShopUserInfo(String headimgurl, String nickname,
			String shopName,String logoPath, Long userId, String shopNo) {
		try {
			Assert.notNull(userId, "Param[userId] can not be null.");
			User user = new User(userId);
			if (StringUtils.isNotBlank(headimgurl)) {
				headimgurl = this.uploadPiceture(userId, headimgurl, true);
				user.setHeadimgurl(nvlString(headimgurl));
			}
			user.setNickname(nvlString(nickname));
			userService.edit(user);
			Shop shop = new Shop();
			shop.setNo(shopNo);
			shop.setName(shopName);
			shop.setShopLogoUrl(logoPath);
			shopService.editShop(shop);
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public void editUserForFreeShop(String headPicture, String nickName,
			Long userId) throws UserException {
		try {
			Assert.notNull(userId, "Param[userId] can not be null.");
			User user = new User(userId);
			if (StringUtils.isNotBlank(headPicture)) {
				user.setHeadimgurl(nvlString(headPicture));
			}
			user.setNickname(nvlString(nickName));
			userService.edit(user);
		} catch (Exception e) {
			throw new UserException(e);
		}
	}
	
	@Override
	public boolean editProfile(Long userId, String headimgurl, String circlePictureUrl, String nickName,
			Integer sex, Integer areaCode, String address, String wechat, String signature, String lpNo)
			throws UserException {
		try {
			Assert.notNull(userId, "Param[userId] can not be null.");
			User user = new User(userId);
			if (StringUtils.isNotBlank(headimgurl)) {
				headimgurl = this.uploadPiceture(userId, headimgurl, true);
				user.setHeadimgurl(nvlString(headimgurl));
			}
			if (StringUtils.isNotBlank(circlePictureUrl)) {
				circlePictureUrl = this.uploadPiceture(userId, circlePictureUrl, false);
				user.setCirclePictureUrl(nvlString(circlePictureUrl));
			}
			user.setNickname(nickName);
			user.setSex(sex);
			user.setAreaCode(areaCode);
			user.setAddress(address);
			user.setWechat(wechat);
			user.setSignature(signature);
			if (null != lpNo && !"".equals(lpNo)) {
				user.setLpNo(lpNo.trim());
				if (this.isUpdatedLpNo(userId)) {
					throw new UserException("已修改过脸谱号不能再修改");
				}
				if (this.isExistLpNo(lpNo, userId)) {
					throw new UserException("该脸谱号已被占用");
				}
				if (!this.checkLpNo(lpNo)) {
					throw new UserException("该脸谱号不符合规则");
				}
			}
			userService.edit(user);
			return true;
		} catch (UncategorizedSQLException ue) {
			_log.error("不支持插入的Emoji表情!", ue);
			throw new UnsupportedEmojiException(ue);
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public void updateLpNoByUserId(Long userId, String lpNo) throws UserException{
		AssertUtil.notNull(userId, "参数userId不能为空");
		AssertUtil.notNull(lpNo, "参数lpNo不能为空");
		if (this.isUpdatedLpNo(userId)) {
			throw new UserException("已修改过脸谱号不能再修改");
		}
		if (this.isExistLpNo(lpNo, userId)) {
			throw new UserException("该脸谱号已被占用");
		}
		if (!this.checkLpNo(lpNo)) {
			throw new UserException("该脸谱号不符合规则");
		}
		userService.updateLpNoByUserId(userId, lpNo.trim());
	}

	/**
	 * 
	 * @Title: isUpdatedLpNo 
	 * @Description: 是否修改过脸谱号,true为修改过,false未修改过
	 * @param lpNo
	 * @param userId
	 * @return
	 * @date 2015年8月25日 下午4:16:04  
	 * @author cbc
	 */
	private boolean isUpdatedLpNo(Long userId) {
		User user = userService.getById(userId);
		return (null != user.getLpNo());
	}

	/**
	 * 
	 * @Title: checkLpNo 
	 * @Description: 验证刷脸号的合法性 
	 * @param lpNo
	 * @return
	 * @date 2015年8月25日 下午3:21:23  
	 * @author cbc
	 */
	private boolean checkLpNo(String lpNo) {
		return lpNo.matches("^[a-zA-Z]{1}([a-zA-Z0-9]|[-_]){5,19}$");
	}

	/**
	 * 
	 * @Title: isExistLpNo 
	 * @Description: 是否该刷脸号已被人占用
	 * @param lpNo
	 * @param userId
	 * @return
	 * @date 2015年8月28日 下午3:22:32  
	 * @author cbc
	 */
	private boolean isExistLpNo(String lpNo, Long userId) {
		Integer integer = userService.countLpNo(lpNo, userId);
		return integer > 0;
	}

	@Override
	public Pagination<User> getUserByLpNo(String phoneOrlpNo,Pagination<User> pagination) throws UserException {
		try {
			AssertUtil.notNull(phoneOrlpNo, "参数phoneOrlpNo为空");
			AssertUtil.notNull(pagination, "参数pagination为空");
			return userService.getUserByLpNo(phoneOrlpNo,pagination);
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public User getUserByMyInvitationCode(String myInvitationCode) {
		return userService.getUserByMyInvitationCode(myInvitationCode);
	}

	@Override
	public List<User> findInvitedUsers(String myInvitationCode) throws UserException {
		try {
			AssertUtil.notNull(myInvitationCode, "参数myInvitationCode为空");
			return this.userService.findInvitedUsers(myInvitationCode);
		} catch (Exception e) {
			_log.error("通过邀请码统计被邀请用户失败！", e);
			throw new UserException(e);
		}
	}

	@Override
	public List<User> findInvitedUsers(Long userId) throws UserException {
		try {
			AssertUtil.notNull(userId, "参数userId为空!");
			User user = this.userService.getById(userId);
			AssertUtil.notNull(user, "查询user接口为空,操作失败!");
			return this.findInvitedUsers(user.getMyInvitationCode());
		} catch (Exception e) {
			_log.error("通过userId统计被邀请用户失败！", e);
			throw new UserException(e);
		}
	}

	@Override
	public UserInvitationVo findMyInvitedRelationship(Long userId) throws UserException {
		try {
			AssertUtil.notNull(userId, "参数userId为空!");
			/** 检查用户是否存在 **/
			User existUser = this.userService.getById(userId);
			AssertUtil.notNull(existUser, "用户不存在,查询失败！");
			UserInvitationVo thisUser = new UserInvitationVo();
			/** 用户上游邀请层级 **/
			List<User> preUsers = this.findUserInviteList(userId);
			if (CollectionUtils.isNotEmpty(preUsers) && this._checkIsMaxInvitationLevel(userId)) {
				_log.error("用户上游邀请层级超过最大限度,操作失败！");
				throw new UserException();
			}
			this._userClone(existUser, thisUser);
			/** 用户处于邀请层级 Level **/
			thisUser.setLevel(CollectionUtils.isNotEmpty(preUsers) ? preUsers.size() - 1 : 0);
			/** 用户的邀请者 Level -1 **/
			User preUser = this.getUserByMyInvitationCode(existUser.getInvitationCode());
			if (preUser != null) {
				UserInvitationVo preUserVo = new UserInvitationVo();
				preUserVo.setLevel(CollectionUtils.isNotEmpty(preUsers) && preUsers.size() >= 2 ? preUsers.size() - 2 : -1);
				this._userClone(preUser, preUserVo);
				thisUser.setPreUser(preUserVo);
			}
			/** 用户的被邀请者 Level +1 **/
			List<User> nextUsers = this.findInvitedUsers(userId);
			if (CollectionUtils.isNotEmpty(nextUsers)) {
				List<UserInvitationVo> nextUserList = new ArrayList<UserInvitationVo>();
				for (User nextUser : nextUsers) {
					UserInvitationVo nextUserVo = new UserInvitationVo();
					nextUserVo.setLevel(CollectionUtils.isNotEmpty(preUsers) ? preUsers.size() : -1);
					this._userClone(nextUser, nextUserVo);
					nextUserList.add(nextUserVo);
				}
				thisUser.setNextUsers(nextUserList);
			}
			return thisUser;
		} catch (Exception e) {
			_log.error("通过userId统计被邀请用户失败！", e);
			throw new UserException(e);
		}
	}

	private void _userClone(User fromUser, UserInvitationVo toUser) {
		if (fromUser != null && toUser != null) {
			toUser.setId(fromUser.getId());
			toUser.setLoginAccount(fromUser.getLoginAccount());
			toUser.setMyInvitationCode(fromUser.getMyInvitationCode());
			toUser.setInvitationCode(fromUser.getInvitationCode());
			toUser.setNickname(fromUser.getNickname());
			toUser.setHeadimgurl(fromUser.getHeadimgurl());
		}
	}

	/**
	 * @Title: _checkIsMaxInvitationLevel
	 * @Description: (检查是否是最大邀请层级)
	 * @param userId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月28日 下午2:21:53
	 */
	private boolean _checkIsMaxInvitationLevel(Long userId) {
		/** 允许最大邀请层级 **/
		String maxInvitationLevel = PropertiesUtil.getContexrtParam(MAX_INVITATION_LEVEL);
		AssertUtil.hasLength(maxInvitationLevel, "subbranchConfig.properties未配置maxInvitationLevel字段");
		List<User> users = this.findUserInviteList(userId);
		if (CollectionUtils.isNotEmpty(users)) {
			return users.size() - 1 > Integer.valueOf(maxInvitationLevel) ? true : false;
		}
		return false;
	}

	@Override
	public List<User> findUserInviteList(Long userId) throws UserException {
		try {
			AssertUtil.notNull(userId, "用户ID为空,查询失败！");
			List<User> users = new ArrayList<User>();
			while (true) {
				User bottomedUser = this.getUserById(userId);
				if (null == bottomedUser) {
					break;
				}
				users.add(bottomedUser);
				String invitationCode = bottomedUser.getInvitationCode();
				if (StringUtils.isBlank(invitationCode)) {
					break;
				}
				User previousUser = this.userService.getUserByMyInvitationCode(invitationCode);
				if (previousUser == null) {
					break;
				}
				userId = previousUser.getId();
			}
			Collections.sort(users, new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					if (null == o1 && null == o2) {
						return 0;
					}
					if (null == o1) {
						return 1;
					}
					if (null == o2) {
						return -1;
					}
					return o1.getCreateTime().compareTo(o2.getCreateTime());
				}
			});
			return users;
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
			throw new UserException(e);
		}
	}

	@Override
	public UserVo getCountInvitationAmount(Long userId) throws UserException {
		try {
			AssertUtil.notNull(userId, "参数userId为空");
			return userService.getCountInvitationAmount(userId);
		} catch (Exception e) {
			_log.error("通过邀请码统计被邀请用户总数或二度邀请用户总数失败！", e);
			throw new UserException(e);
		}
	}

	@Override
	public void updateShuaLianUser(User user) throws UserException {
		try {
			AssertUtil.notNull(user, "参数user为空");
			AssertUtil.notNull(user.getId(), "参数userId为空");
			userService.updateShuaLianUser(user);
		} catch (Exception e) {
			_log.error("修改用户信息失败！", e);
			throw new UserException(e);
		}
	}
	
}
