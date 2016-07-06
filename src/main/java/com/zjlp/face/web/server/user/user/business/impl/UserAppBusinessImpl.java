package com.zjlp.face.web.server.user.user.business.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;
import com.zjlp.face.web.server.product.phone.service.PhoneVerificationCodeService;
import com.zjlp.face.web.server.user.security.service.PermissionService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.factory.ShopFactory;
import com.zjlp.face.web.server.user.user.business.UserAppBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.util.JSONUtils;
import com.zjlp.face.web.util.ResultCode;

@Service("userAppBusiness")
public class UserAppBusinessImpl implements UserAppBusiness{

	private Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private PhoneVerificationCodeService phoneVerificationCodeService;
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired(required = false)
	private AccountService accountService;
	@Autowired
	private ShopFactory commonShopFactory;
	
	private static final Lock _lock = new ReentrantLock();
	
	/**
	 * 验证手机验证码
	 * @param phone			手机号码
	 * @param phoneCode		手机验证码
	 * @return				错误:{错误码:错误信息} <br>
	 * 						正确:{"success":"ok"}
	 */
	@Override
	public JSONObject checkPhoneCode(String phone,String phoneCode){
		if(StringUtils.isBlank(phone)){
			_logger.info("手机号码不能为空");
			return JSONUtils.resultErrCode(ResultCode.PHONE_NO_ISNULL, "手机号码不能为空");
		}
		
		if(StringUtils.isBlank(phoneCode)){
			_logger.info("手机验证码不能为空");
			return JSONUtils.resultErrCode(ResultCode.PHONECODE_NO_ISNULL, "手机验证码不能为空");
		}
		
		PhoneVerificationCode phoneVerificationCode = new PhoneVerificationCode();
		phoneVerificationCode.setCell(phone);
		phoneVerificationCode.setType(Constants.PHONE_CODE_ZC);
		phoneVerificationCode = phoneVerificationCodeService.selectByPhone(phoneVerificationCode);
		
		if(phoneVerificationCode==null || phoneVerificationCode.getCode()==null){
			_logger.info("无法验证手机验证码");
			return JSONUtils.resultErrCode(ResultCode.PHONECODE_ERROR, "手机验证码错误");
		}
		
		Date nowDate = new Date();
		//手机验证码是否超时
		if(nowDate.getTime() > phoneVerificationCode.getValidTime().getTime()){
			_logger.info("手机验证码超时");
			return JSONUtils.resultErrCode(ResultCode.PHONECODE_TIMEOUT, "手机验证码超时");
		}
		
		//验证手机验证码是否正确
		if(phoneCode.trim().equals(phoneVerificationCode.getCode().trim())){
			return JSONUtils.resultSuccess();
		}else{
			_logger.info("手机验证码错误,输入码:" + phoneCode.trim() + ",正确码:" + phoneVerificationCode.getCode().trim());
			return JSONUtils.resultErrCode(ResultCode.PHONECODE_ERROR, "手机验证码错误");
		}
	}
	
	/**
	 * 注册
	 * @param userVo		用户对象
	 * @return				错误:{错误码:错误信息} <br>
	 * 						正确:{"success":"ok"}
	 */
	@Override
	public JSONObject register(UserVo userVo){
		return this.register(userVo,false);
	}
	
	@Override
	public JSONObject register(UserVo userVo,Boolean isGenarateShop){
		
		_lock.lock();
		try {
			try {
				/** step 1: params validation */
				if(userVo == null){
					_logger.info("用户对象为空");
					throw new Exception(ResultCode.USERVO_NULL);
//				return JSONUtils.resultErrCode(ResultCode.USERVO_NULL, "用户对象为空");
				}
				if(StringUtils.isBlank(userVo.getPhone())){
					_logger.info("手机号码不能为空");
					throw new Exception(ResultCode.PHONE_NO_ISNULL);
//				return JSONUtils.resultErrCode(ResultCode.PHONE_NO_ISNULL, "手机号码不能为空");
				}
				
				if(StringUtils.isBlank(userVo.getPwd())){
					_logger.info("密码不能为空");
					throw new Exception(ResultCode.PASSWORD_NO_ISNULL);
//				return JSONUtils.resultErrCode(ResultCode.PASSWORD_NO_ISNULL, "密码不能为空");
				}
				
				if(userVo.getPhone().equals(userVo.getPwd())){
					_logger.info("密码不能和用户名相同");
					throw new Exception(ResultCode.PAS_NAME_CANNOT_SAME);
//				return JSONUtils.resultErrCode(ResultCode.PAS_NAME_CANNOT_SAME, "密码不能和用户名相同");
				}
				
				if(null!=userVo.getInvitationCode() && userVo.getInvitationCode().trim().length()>0){
					Integer count=userService.getCountByMyInvitationCode(userVo.getInvitationCode());
					if(count==0){
						throw new Exception(ResultCode.INVITATION_CODE_ERROR);
					}
					User invitationUser = userService.getUserByMyInvitationCode(userVo.getInvitationCode());
					userVo.setInvitationUserId(invitationUser.getId());
				}
				/** step 2: init object */
				
				/** step 3: bussiness proccess */	
				//检测用户是否存在
				User checkUser = userService.getAllUserByLoginAccount(userVo.getPhone());
				if(null != checkUser){
					_logger.info("用户已存在");
					throw new Exception(ResultCode.USER_EXIST);
//				return JSONUtils.resultErrCode(ResultCode.USER_EXIST, "用户已存在");
				}
			} catch (Exception e) {
				_logger.error(e.getMessage(), e);
				return ResultCode.getResultMsg(e.getMessage());
			}
			
			try {
				//注册账号
				userService.register(userVo);
//			addRoleAndAccount(userVo);
			} catch (UserException e) {
				_logger.error(ResultCode.REGISTER_ACCOUNT_FAIL, e);
				return ResultCode.getResultMsg(ResultCode.REGISTER_ACCOUNT_FAIL);
			}
			
			//是否开店
			if(isGenarateShop){
				return activateShop(userVo.getLoginAccount());
			}
			
			return JSONUtils.resultSuccess();
		} catch (Exception e) {
			_logger.error(ResultCode.REGISTER_ACCOUNT_FAIL, e);
			return JSONUtils.resultErrCode(ResultCode.REGISTER_ACCOUNT_FAIL, "注册失败");
		} finally {
			_lock.unlock();
		}
	}
	
//	/**
//	 * 注册账号
//	 * @param userVo
//	 */
//	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName={"UserException"})
//	public void addRoleAndAccount(UserVo userVo) throws UserException{
//		try {
//			//新建用户
//			Long userId = userService.add(userVo);
//
//			if(userId != null)	throw new UserException(ResultCode.REGISTER_ACCOUNT_FAIL);
//
//			//赋予普通用户权限
//			permissionService.addUserRoleRelation(userId,Constants.STRING_ROLE_U);
//			//初始化钱包
//			accountService.addAccount(userId,userVo.getPhone(), null, null);
//		} catch (Exception e) {
//			_logger.info("注册出错:" + e.getMessage());
//			throw new UserException(ResultCode.REGISTER_ACCOUNT_FAIL, e);
//		}
//	}
	
	
	/**
	 * 登录
	 * @param userName		用户名
	 * @param password		密码
	 * @return				错误:{错误码:错误信息} <br>
	 * 						正确:{"success":"ok","userInfo",userInfo}
	 */
	@Override
	public JSONObject login(String userName,String password){
		if(StringUtils.isBlank(userName)){
			_logger.info("手机号码不能为空");
			return JSONUtils.resultErrCode(ResultCode.PHONE_NO_ISNULL, "手机号码不能为空");
		}
		
		if(StringUtils.isBlank(password)){
			_logger.info("密码不能为空");
			return JSONUtils.resultErrCode(ResultCode.PASSWORD_NO_ISNULL, "密码不能为空");
		}
		
		User user = userService.getUserByName(userName);
		if (null == user) {
			_logger.info("用户名不存在");
			return JSONUtils.resultErrCode(ResultCode.USERNAME_NO_EXIST, "用户名不存在");
		}
		
		if(!user.getPasswd().equals(DigestUtils.jzShaEncrypt(password))){
			_logger.info("密码错误");
			return JSONUtils.resultErrCode(ResultCode.USERNAME_NO_EXIST, "密码错误");
		}
		
		UserInfo userInfo = new UserInfo();
		List<String> roleIdList = permissionService.findAllPermissionByUserId(user.getId());
		
		userInfo.setLoginAccount(user.getLoginAccount());
		userInfo.setPasswd(user.getPasswd());
		userInfo.setToken(user.getToken());
		userInfo.setShopNoList(null);
		userInfo.setRoleList(roleIdList);
		//用户类型，1:普通用户，2：超级用户，3：管理员帐户
		userInfo.setType(1);
		userInfo.setUserId(user.getId());
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("userInfo", userInfo);
		
		return JSONUtils.resultSuccessObject(resultMap);
	}
	
	/**
	 * 生成官网店铺
	 * @param loginAccount		登录账号
	 * @return					错误:{错误码:错误信息} <br>
	 * 							正确:{"success":"ok"}
	 */
	@Override
	public JSONObject activateShop(String loginAccount){
		if(StringUtils.isBlank(loginAccount)){
			_logger.info("登录账户不能为空");
			return JSONUtils.resultErrCode(ResultCode.LOGINACCOUNT_NO_NULL, "登录账户不能为空");
		}

		Date nowDate = new Date();
		
		ShopDto dto = new ShopDto();
		//无授权码
//		dto.setAuthorizationCode(null);
//		dto.setAuthorizationCodeType(null);
		dto.setLoginAccount(loginAccount);
		//店铺类型官网
		dto.setType(Constants.SHOP_GW_TYPE);
		dto.setName(loginAccount);
		//系统自动生成,无邀请码
//		dto.setOnInvitationCode(null);
		dto.setActivationTime(nowDate);
		dto.setSigningTime(nowDate);
		Shop newShop = new Shop();
		
		try {
			newShop = commonShopFactory.addShop(dto);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.info("生成官网失败:" + e.getMessage());
			return JSONUtils.resultErrCode(ResultCode.ACTIVATE_GW_SHOP_FAIL, "生成官网失败:" + e.getMessage());
		}
		
		//TODO 不生成授权码
//		AuthorizationCode ac = new AuthorizationCode();
//		ac.setId(authorizationCode.getId());
//		ac.setDestinationShopNo(newShop.getNo());
//		ac.setStatus(CODE_USED);
//		ac.setActivationTime(date);
//		ac.setUpdateTime(date);
//		authorizationCodeService.editById(ac);
		// return JSONUtils.resultSuccess();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", newShop.getNo());
		map.put("shopName", newShop.getName());
		return JSONUtils.resultSuccessObject(map);
	}
	
	@Override
	public JSONObject activateShopLock(String loginAccount){
		if(StringUtils.isBlank(loginAccount)){
			_logger.info("登录账户不能为空");
			return JSONUtils.resultErrCode(ResultCode.LOGINACCOUNT_NO_NULL, "登录账户不能为空");
		}

		Date nowDate = new Date();
		
		ShopDto dto = new ShopDto();
		//无授权码
//		dto.setAuthorizationCode(null);
//		dto.setAuthorizationCodeType(null);
		dto.setLoginAccount(loginAccount);
		//店铺类型官网
		dto.setType(Constants.SHOP_GW_TYPE);
		dto.setName(loginAccount);
		//系统自动生成,无邀请码
//		dto.setOnInvitationCode(null);
		dto.setActivationTime(nowDate);
		dto.setSigningTime(nowDate);
		Shop newShop = new Shop();
		
		try {
			newShop = commonShopFactory.addShopLock(dto);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.info("生成官网失败:" + e.getMessage());
			return JSONUtils.resultErrCode(ResultCode.ACTIVATE_GW_SHOP_FAIL, "生成官网失败:" + e.getMessage());
		}
		
		//TODO 不生成授权码
//		AuthorizationCode ac = new AuthorizationCode();
//		ac.setId(authorizationCode.getId());
//		ac.setDestinationShopNo(newShop.getNo());
//		ac.setStatus(CODE_USED);
//		ac.setActivationTime(date);
//		ac.setUpdateTime(date);
//		authorizationCodeService.editById(ac);
		// return JSONUtils.resultSuccess();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", newShop.getNo());
		map.put("shopName", newShop.getName());
		return JSONUtils.resultSuccessObject(map);
	}
}
