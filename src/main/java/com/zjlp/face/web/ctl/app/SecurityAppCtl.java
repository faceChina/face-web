package com.zjlp.face.web.ctl.app;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.constants.MobilecodeType;
import com.zjlp.face.web.exception.ext.PhoneVerificationCodeException;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.DataUtils;
@Controller
@RequestMapping({"/assistant/ass/security/"})
public class SecurityAppCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private PhoneBusiness  phoneBusiness;
	
	/**
	 * 判断是否设置支付密码
	* @Title: hasPaymentCode
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年4月24日 上午10:41:26
	 */
	@RequestMapping(value="index")
	@ResponseBody
	public String index(){
		try {
		Long userId = getUserId();
		User user = userBusiness.getUserById(userId);
		boolean hasPaymentCode = accountBusiness.existPaymentCode(userId);
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("phone",DataUtils.getJiamiPhone(user.getCell()));
		dataMap.put("pflag",String.valueOf(hasPaymentCode));
		return outSucceed(dataMap,"");
		} catch (Exception e) {
			_logger.error("获取信息失败",e);
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
		}
	}
	
	/**
	 * 支付密码模块验证码验证
	* @Title: checkCode
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月24日 上午10:09:41
	 */
	@RequestMapping(value="code/check")
	@ResponseBody
	public String checkCode(@RequestBody JSONObject jsonObj){
		try {
			// 验证码
			String mobilecode = jsonObj.getString("mobilecode");
			if (null == mobilecode||"".equals(mobilecode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			
			Long userId = getUserId();
			User user = userBusiness.getUserById(userId);
			Integer result = phoneBusiness.checkPhoneCode(user.getCell(), mobilecode, MobilecodeType.mobile_paymentcode_0.getScene());
			
			if (null == result || 1 != result.intValue()) {
				// 验证码错误
				return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE, "");
			}
			boolean hasPaymentCode = accountBusiness.existPaymentCode(userId);
			if (hasPaymentCode){
				// 身份证
				String identity = jsonObj.getString("identity");
				if (null == identity||"".equals(identity)||!identity.equals(user.getIdentity())){
					return outFailure(AssConstantsUtil.AccountCode.IDENTITY_ERROR_CODE, "");
				}
			}
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("验证失败",e);
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
		}
	}
	/**
	 * 首次设置支付密码
	* @Title: setPwd
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月24日 上午10:09:57
	 */
	@RequestMapping(value="setPwd")
	@ResponseBody
	public String setPwd(@RequestBody JSONObject jsonObj){
		try {
			Long userId = getUserId();
			User user = userBusiness.getUserById(userId);
			
			// 真实姓名
			String userName = jsonObj.getString("name");
			if (null == userName||"".equals(userName)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			// 身份证
			String identity = jsonObj.getString("identity");
			if (null == identity||"".equals(identity)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String newPaymentCode= jsonObj.getString("newPaymentCode");
			if (null == newPaymentCode||"".equals(newPaymentCode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String confirmCode= jsonObj.getString("confirmCode");
			if (null == confirmCode||"".equals(confirmCode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			boolean hasPaymentCode = accountBusiness.existPaymentCode(userId);
			//身份证验证
			if (hasPaymentCode && !user.getIdentity().equals(identity)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			accountBusiness.completInformation(userId, newPaymentCode,
					confirmCode, userName, identity);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("设置支付密码失败",e);
			return outFailure(AssConstantsUtil.System.STT_PAY_PWD_FAIL_CLOSE, "");
		}
	}
	
	
	/**
	 * 旧手机验证
	* @Title: checkCell
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月24日 上午10:10:14
	 */
	@RequestMapping(value = "checkCell")
	@ResponseBody
	public String checkCell(@RequestBody JSONObject jsonObj) {
		try {
			
			if(null ==jsonObj){
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE,"");
			}
			// 验证码
			String mobilecode = jsonObj.getString("mobilecode");
			if (null == mobilecode||"".equals(mobilecode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long userId = super.getUserId();
			User user = userBusiness.getUserById(userId);
			// 验证码验证
			Integer result = phoneBusiness.checkPhoneCode(user.getCell(), mobilecode, MobilecodeType.mobile_phonecode_0.getScene());
			if (null == result || 1 != result.intValue()) {
				// 验证码错误
				return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE, "");
			}
			return outSucceedByNoData();
		}catch(PhoneVerificationCodeException e){
			
			return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE,"");
		} catch (Exception e) {
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE,"");
		}
	}
	
	/**
	 * 新手机绑定
	* @Title: bind
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月21日 下午5:44:51
	 */
	@RequestMapping(value = "bind", method = RequestMethod.POST)
	@ResponseBody
	public String bind(@RequestBody JSONObject jsonObj) {
		try {
			if(null ==jsonObj){
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE,"");
			}
			// 验证码
			String mobilecode = jsonObj.getString("mobilecode");
			if (null == mobilecode||"".equals(mobilecode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String phone = jsonObj.optString("phone");
			// 验证码验证
			Integer result = phoneBusiness.checkPhoneCode(phone, mobilecode, MobilecodeType.mobile_phonecode_1.getScene());
			if (null == result || 1 != result.intValue()) {
				// 验证码错误
				return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE, "");
			}
			userBusiness.editUserCell(getUserId(), phone);
			return outSucceedByNoData();
		} catch(PhoneVerificationCodeException e){
			return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE,"");
		}catch (Exception e) {
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE,"");
		}
	}
	
	/**
	 * 重置支付密码
	* @Title: resetPaymentCode
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月24日 上午10:09:30
	 */
	@RequestMapping(value = "/paymentCode/reset")
	@ResponseBody
	public String resetPaymentCode(@RequestBody JSONObject jsonObj){
		try {
			Long userId = getUserId();
			boolean hasPaymentCode = accountBusiness.existPaymentCode(userId);
			if(!hasPaymentCode){
				return outFailure(AssConstantsUtil.AccountCode.ACCOUNT_NO_PAYMENT_PASSWORD_CODE, "");
			}
			String newPaymentCode= jsonObj.getString("newPaymentCode");
			if (null == newPaymentCode||"".equals(newPaymentCode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String confirmCode= jsonObj.getString("confirmCode");
			if (null == confirmCode||"".equals(confirmCode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			accountBusiness.completInformation(userId, newPaymentCode,
					confirmCode, null, null);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("设置支付密码失败",e);
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
		}
	}
}
