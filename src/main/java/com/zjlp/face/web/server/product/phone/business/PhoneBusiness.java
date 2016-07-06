package com.zjlp.face.web.server.product.phone.business;

import java.util.List;

import com.zjlp.face.web.component.sms.SmsContent;
import com.zjlp.face.web.exception.ext.PhoneVerificationCodeException;
import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;

public interface PhoneBusiness {

	/**
	 * 获取手机验证码
	 * 
	 * @Title: getPhoneCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 *            手机号
	 * @param type
	 *            应用场景
	 * @param modcode
	 *            短信模版编号
	 * @throws PhoneVerificationCodeException
	 * @date 2015年3月24日 上午9:29:25
	 * @author fjx
	 */
	void getPhoneCode(String phone, Integer type, SmsContent modcode)
			throws PhoneVerificationCodeException;

	/**
	 * 检查验证码
	 * 
	 * @Title: checkPhoneCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 * @param phoneCode
	 * @param type
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2014年10月21日 下午2:10:37
	 * @author fjx
	 */
	Integer checkPhoneCode(String phone, String phoneCode, Integer type)
			throws PhoneVerificationCodeException;

	/**
	 * 
	 * @Title: fristCheckPhoneCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 * @param phoneCode
	 * @param type
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2015年3月24日 上午9:31:16
	 * @author fjx
	 */
	@Deprecated
	Integer fristCheckPhoneCode(String phone, String phoneCode, Integer type)
			throws PhoneVerificationCodeException;
	
	
	String createMobilecode(String cell, Integer type) throws PhoneVerificationCodeException;
	
	String createMobilecodeByJson(String cell, Integer type)
			throws PhoneVerificationCodeException;
	
	boolean testMobilecode(String cell, Integer type, String mobilecode) 
			throws PhoneVerificationCodeException;
	
	boolean checkMobilecode(String cell, Integer type, String mobilecode) 
			throws PhoneVerificationCodeException;

	/**
	 * @Title: findPhoneCodeByCell
	 * @Description: (通过手机号查找验证码，该方法用于因为未知原因用户注册失败时保证验证码有效)
	 * @param cell
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @return List<PhoneVerificationCode> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月31日 下午4:24:28
	 */
	List<PhoneVerificationCode> findPhoneCodeByCell(String cell) throws PhoneVerificationCodeException;

	/**
	 * @Title: updatePhoneCode
	 * @Description: (更新验证码，一般用于将验证码有效或者失效)
	 * @param id
	 * @param status
	 * @throws PhoneVerificationCodeException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月31日 下午4:46:50
	 */
	void updatePhoneCode(Long id, Integer status) throws PhoneVerificationCodeException;

}
