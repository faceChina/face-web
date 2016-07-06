package com.zjlp.face.web.ctl.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.server.operation.subbranch.business.UnregisteredSubUserBusiness;
import com.zjlp.face.web.server.product.phone.business.DeviceInfoBusiness;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;
import com.zjlp.face.web.server.user.user.business.UserAppBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.server.user.user.producer.UserGisProducer;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.ResultCode;

@Controller
@RequestMapping("/assistant/any/")
public class AnyAppCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	@Autowired
	private UserGisProducer userGisProducer;
	@Autowired
	private UserAppBusiness userAppBusiness;
	@Autowired
	private DeviceInfoBusiness deviceInfoBusiness;
	@Autowired
	private UnregisteredSubUserBusiness unregisteredSubUserBusiness;
	
	/**
	 * @Title: registerForward
	 * @Description: (用户注册：不送官网)
	 * @param jsonObj
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String 返回类型
	 * @throws
	 * @author Xiaonin Wu
	 * @date 2015年6月23日 下午4:34:06
	 */
	@RequestMapping(value="registerfor")
	@ResponseBody
	public String registerForward(@RequestBody JSONObject jsonObj) throws ServletException, IOException{
		try {	
			if (null == jsonObj ){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String phone = jsonObj.getString("phone");
			String mobilecode = jsonObj.getString("mobilecode");
			String pwd = jsonObj.getString("pwd");
			Integer codeStates = phoneBusiness.checkPhoneCode(phone, mobilecode,Constants.PHONE_CODE_ZC);
			if (null == codeStates || 1 != codeStates.intValue()) {
				_logger.info("验证码验证错误");
				// 验证码错误
				return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE, "");
			}
			UserVo userVo = new UserVo();
			userVo.setPhone(phone);
			userVo.setPwd(pwd);
			userVo.setFlag(codeStates);
			String flag = userBusiness.register(userVo);
			if ("success".equals(flag)) {
				return outSucceedByNoData();
			}else if ("用户名与密码不能相同！".equals(flag)){
				_logger.info("用户名与密码不能相同！");
				// 用户名密码相同
				return outFailure(AssConstantsUtil.UserCode.LOGIN_ACCOUNT_PWD_SAME_CODE, "");
			}else if ("用户帐号重复".equals(flag)){
				_logger.info("用户帐号重复");
				// 用户已注册
				return outFailure(AssConstantsUtil.UserCode.LOGIN_ACCOUNT_EXIST_CODE, "");
			}else if ("手机验证码超时！".equals(flag)){
				_logger.info("手机验证码超时！");
				// 手机验证码超时！
				return outFailure(AssConstantsUtil.System.REQUEST_FAILED, "");
			}else{
				return outFailure(AssConstantsUtil.System.REQUEST_FAILED, "");
			}
		} catch (Exception e) {
	       	_logger.error("注册失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * @Title: registerForward
	 * @Description: (用户注册：送官网)
	 * @param jsonObj
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 下午4:34:55
	 */
	@RequestMapping(value = "registeWithFreeShop")
	@ResponseBody
	public String registeWithFreeShop(@RequestBody JSONObject jsonObj) throws ServletException, IOException {
		String phone = null;
		String mobilecode = null;
		String invitationCode=null;
		try {
			if (null == jsonObj) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			phone = jsonObj.getString("phone");
			mobilecode = jsonObj.getString("mobilecode");
			invitationCode=jsonObj.get("invitationCode")!=null ? jsonObj.get("invitationCode").toString() : null;
			String pwd = jsonObj.getString("pwd");
			Integer codeStates = phoneBusiness.checkPhoneCode(phone, mobilecode, Constants.PHONE_CODE_ZC);
			codeStates=1;
			if (null == codeStates || 1 != codeStates.intValue()) {
				_logger.info("注册验证码验证错误，返回结果="+String.valueOf(codeStates)+"，手机号="+phone+"，验证码="+mobilecode);
				// 验证码错误
				return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE, "");
			}
			UserVo userVo = new UserVo();
			userVo.setPhone(phone);
			userVo.setInvitationCode(invitationCode);
			userVo.setPwd(pwd);
			userVo.setFlag(codeStates);
			JSONObject json = userAppBusiness.register(userVo);
			if ("ok".equals(json.optString(ResultCode.SUCCESS))) {
				JSONObject jsonShop = userAppBusiness.activateShopLock(phone);
				if ("ok".equals(jsonShop.optString(ResultCode.SUCCESS))) {
					try {
						jsonShop.remove(ResultCode.SUCCESS);
						// 通过分享已经成为分店但是未注册用户，住的时候完善分店关系
						this.unregisteredSubUserBusiness.completeSubRelationship(phone);
					} catch (Exception e) {
						_logger.info("没有待完善的分店关系或者完善分店关系失败！");
					}
					/** 使得该手机下所有验证码失效 **/
					this._updatePhoneCode(phone, mobilecode, Constants.PHONE_CODE_ZC, false);
					return outSucceed(jsonShop, false, "");
				} else if ("登录账户不能为空".equals(jsonShop.optString(ResultCode.LOGINACCOUNT_NO_NULL))) {
					return outFailure(Integer.parseInt(ResultCode.LOGINACCOUNT_NO_NULL), "");
				} else if (jsonShop.optString(ResultCode.ACTIVATE_GW_SHOP_FAIL).startsWith("生成官网失败:")) {
					return outFailure(Integer.parseInt(ResultCode.ACTIVATE_GW_SHOP_FAIL), "");
				} else {
					return outFailure(Integer.parseInt(ResultCode.ACTIVATE_GW_SHOP_FAIL), "");
				}
			} else if ("用户对象为空".equals(json.optString(ResultCode.USERVO_NULL))) {
				return outFailure(AssConstantsUtil.UserCode.NULL_USER_CODE, "");
			} else if ("手机号码不能为空".equals(json.optString(ResultCode.PHONE_NO_ISNULL))) {
				return outFailure(AssConstantsUtil.UserCode.LOGIN_ACCOUNT_EXIST_CODE, "");
			} else if ("密码不能为空".equals(json.optString(ResultCode.PASSWORD_NO_ISNULL))) {
				return outFailure(AssConstantsUtil.UserCode.NULL_PASSWORD_CODE, "");
			} else if ("密码不能和用户名相同".equals(json.optString(ResultCode.PAS_NAME_CANNOT_SAME))) {
				return outFailure(AssConstantsUtil.UserCode.LOGIN_ACCOUNT_PWD_SAME_CODE, "");
			} else if ("用户已存在".equals(json.optString(ResultCode.USER_EXIST))) {
				return outFailure(AssConstantsUtil.UserCode.LOGIN_ACCOUNT_EXIST_CODE, "");
			} else if ("赋予权限出错".equals(json.optString(ResultCode.ENDOW_USER_ROLE_ERROR))) {
				return outFailure(AssConstantsUtil.UserCode.ADD_GUANWANG_CODE, "");
			} else if ("初始化钱包出错".equals(json.optString(ResultCode.INIT_WALLET_ERROR))) {
				return outFailure(AssConstantsUtil.UserCode.INIT_WALLET_CODE, "");
			} else if("请输入正确的邀请码".equals(json.optString(ResultCode.INVITATION_CODE_ERROR))){
				return outFailure(AssConstantsUtil.UserCode.INVITATION_CODE_ERROR, "");
			}else {
				return outFailure(AssConstantsUtil.System.REQUEST_FAILED, "");
			}
		} catch (Exception e) {
			_logger.error("注册失败", e);
			/** 使得该手机下所有验证码有效 **/
			this._updatePhoneCode(phone, mobilecode, Constants.PHONE_CODE_ZC, true);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: _updatePhoneCode
	 * @Description: (因未知原因导致注册失败的，使得验证码有效或者失效)
	 * @param rollBackCell
	 * @param isValid
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月31日 下午4:55:30
	 */
	private void _updatePhoneCode(String rollBackCell, String mobilecode, Integer type, boolean isValid) {
		try {
			List<PhoneVerificationCode> phoneCodeList = this.phoneBusiness.findPhoneCodeByCell(rollBackCell);
			if (CollectionUtils.isNotEmpty(phoneCodeList)) {
				for (PhoneVerificationCode current : phoneCodeList) {
					//筛选
					if (null != current && current.getCode().equals(mobilecode)
							&& current.getType().equals(type)) {
						continue;
					}
					if (null != current) {
						this.phoneBusiness.updatePhoneCode(current.getId(), isValid ? 1 : -1);
					}
				}
			}
		} catch (Exception e) {
			_logger.error("验证码有效或者失效操作失败！", e);
		}
	}

	/**
	 * 发送注册码
	 * 
	 * @Title: registerPhoneCode
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return void 返回类型
	 * @throws
	 * @author wxn
	 * @date 2015年3月19日 下午2:05:07
	 */
	@RequestMapping(value="registerCode")
	@ResponseBody
	public String registerPhoneCode(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj ){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String falg = "false";
			String phone = jsonObj.getString("phone");
			Integer type = jsonObj.getInt("type");
			falg =  userBusiness.checkLoginAccount(phone);
			if ("false".equals(falg)){
				return outFailure(AssConstantsUtil.UserCode.LOGIN_ACCOUNT_EXIST_CODE, "");
			}else{
				String json = phoneBusiness.createMobilecodeByJson(phone, type);
				JSONObject jsonObject = JSONObject.fromObject(json);
				if (!"true".equals(jsonObject.get("flag"))) {
					String result = jsonObject.get("result").toString();
					if ("32".equals(result)) {
						return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_MUTIL, "");
					} if ("-2".equals(result)) {
						return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_MUTIL_2);
					} else {
						return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_FAIL, "");
					}
				}
				return outSucceedByNoData();
			}
		} catch (Exception e) {
	       	_logger.error("获取注册验证码失败",e);
	       	return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	* @Title: checkUserPhone
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param loginAccount
	* @param phone
	* @param model
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2014年12月23日 上午10:23:51
	 */
	@RequestMapping(value="sendPhoneCode")
	@ResponseBody
	public String sendPhoneCode(@RequestBody JSONObject jsonObj){
		
		try {
		String loginAccount = jsonObj.getString("loginAccount");
		String phone   = jsonObj.getString("phone");
		int codeType   = jsonObj.getInt("codeType");
		
		String flag = "SUCCESS";
		if(null == loginAccount || "".equals(loginAccount) || null == phone || "".equals(phone)){
			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE,"");
		}
	    Pattern pattern = Pattern.compile("^(13|15|18|17)\\d{9}$"); 
	    if(pattern.matcher(phone).matches() && pattern.matcher(loginAccount).matches()){
	    	// 0 没有用户  2 手机没有绑定用户  1 手机账号正确
	    	flag = userBusiness.checkUserPhone(loginAccount);
	    	//通过验证发送验证码
	    	if ("SUCCESS".equals(flag)){
	    		String json = phoneBusiness.createMobilecodeByJson(phone, codeType);
	    		JSONObject jsonObject = JSONObject.fromObject(json);
	    		if (!"true".equals(jsonObject.get("flag"))) {
	    			String result = jsonObject.get("result").toString();
	    			if ("32".equals(result)) {
	    				return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_MUTIL, "");
	    			} if ("-2".equals(result)) {
	    				return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_MUTIL_2);
	    			} else {
	    				return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_FAIL, "");
	    			}
	    		}
	    		return outSucceedByNoData();
	    	}else{
	    		if ("该帐号不存在！".equals(flag)){
	    			return outFailure(AssConstantsUtil.UserCode.ACCOUNT_ERROR_CODE,"");
	    		}else if("2".equals(flag)){
	    			return outFailure(AssConstantsUtil.UserCode.PHONE_NO_BINDING_CODE,"");
	    		}else{
	    			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE,"");
	    		}
	    	}
	    }else{
	    	return outFailure(AssConstantsUtil.UserCode.PHONE_OR_ACCOUNT_ERROR_CODE,"");
	    }
		} catch (Exception e) {
			_logger.error("获取验证码失败" , e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE,"");
		}
	}
	
	@RequestMapping(value = "checkUser")
	@ResponseBody
	public String checkUserPasswd(@RequestBody JSONObject jsonObj) {
		try {// 检测提交的参数
			String phone = jsonObj.getString("phone");
			String phonecode = jsonObj.getString("phoneCode");
			String loginAccount = jsonObj.optString("loginAccount");
			int codeType = jsonObj.getInt("codeType");
			User user = this.userBusiness.getUserByLoginAccount(loginAccount);
			if (user == null) {
				return outFailure(AssConstantsUtil.UserCode.ACCOUNT_ERROR_CODE, "");
			} else {
				// 登录号与绑定号匹配
				if (!user.getCell().equals(phone)) {
					return outFailure(AssConstantsUtil.System.PHONE_MISMATCHES_CLOSE, "");
				} else {
					Integer falg = phoneBusiness.fristCheckPhoneCode(phone, phonecode, codeType);
					if (1 == falg.intValue()) {
						return outSucceedByNoData();
					} else {
						// 验证码错误 或超时
						return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE, "");
					}
				}
			}
		} catch (Exception e) {
			_logger.error("验证码用户校验失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	@RequestMapping(value="savePassword")
	@ResponseBody
	public String changePassword(@RequestBody JSONObject jsonObj){
		
	try {	//检测提交的参数
		String loginAccount = jsonObj.getString("loginAccount");
		String phone   = jsonObj.getString("phone");
		String phonecode   = jsonObj.getString("phoneCode");
		String newpwd   = jsonObj.getString("newPwd");
		int codeType   = jsonObj.getInt("codeType");
		
		UserInfo userInfo = userBusiness.getUserInfo(loginAccount);
		if(null == userInfo){
			return outFailure(AssConstantsUtil.UserCode.USERNAME_ERROR_CODE,"");
		}
		//匹配登录号和绑定号
		User user = this.userBusiness.getUserByLoginAccount(loginAccount);
		if (user == null) {
			return outFailure(AssConstantsUtil.UserCode.ACCOUNT_ERROR_CODE, "");
		}
		if (!user.getCell().equals(phone)) {
			return outFailure(AssConstantsUtil.System.PHONE_MISMATCHES_CLOSE, "");
		}
		
		Integer falg = phoneBusiness.checkPhoneCode(phone, phonecode,codeType);
		if(0 == falg.intValue()){
			//验证码错误 或超时
			return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE,"");
		}else if(1 == falg.intValue()){
			userBusiness.editUserPasswd(userInfo.getUserId(),newpwd, newpwd);
			return outSucceedByNoData();
		}else{
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE,"");
		}
		} catch (Exception e) {
			_logger.error("修改密码失败" , e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE,"");
		}
	}
	
	
	@RequestMapping(value="/gis/save")
	@ResponseBody
	public String gisSave(@RequestBody JSONObject req_json){
		try {
			if (null == req_json ){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String loginAccount = req_json.getString("loginAccount");
			User user = userBusiness.getUserByLoginAccount(loginAccount);
			if (null == user){
				return outFailure(AssConstantsUtil.UserCode.ACCOUNT_ERROR_CODE,"");
			}
			UserGis oldUserGis = userGisProducer.getUserGisByUserId(user.getId());
			if (null == oldUserGis){
				UserGisVo userGisVo = JsonUtil.toBean(req_json.toString(), UserGisVo.class);
				userGisVo.setUserId(user.getId());
				userGisProducer.add(userGisVo);
			}else{
				UserGis newUserGis = JsonUtil.toBean(req_json.toString(), UserGis.class);
				newUserGis.setId(oldUserGis.getId());
				userGisProducer.edit(newUserGis);
			}
			return outSucceedByNoData();
		} catch (UserException e) {
			_logger.error("保存用户地理位置信息失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("保存用户地理位置信息失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
			
		}
	}

	/**
	 * @Title: youqianActivity
	 * @Description: (友钱活动检测)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月7日 下午5:36:51
	 */
	@RequestMapping("youqianActivity")
	@ResponseBody
	public String youqianActivity(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String deviceInfo = jsonObj.optString("deviceInfo");
			if (StringUtils.isBlank(deviceInfo)) {
				return outFailure(AssConstantsUtil.Activity.NULL_DEVICE_INFO_ERROR_CODE, "");
			}
			this.deviceInfoBusiness.callback(deviceInfo);
			Map<String, Object> map = new HashMap<String, Object>();
			return outSucceedWithoutSession(map, false, StringUtils.EMPTY);
		} catch (Exception e) {
			_logger.error("上传设备信息失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
}
