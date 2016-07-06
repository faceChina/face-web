package com.zjlp.face.web.server.product.phone.service;

import java.util.Date;
import java.util.List;

import com.zjlp.face.web.exception.ext.PhoneVerificationCodeException;
import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;

public interface PhoneVerificationCodeService {

	/**
	 * 生成验证码
	 * 
	 * @Title: addPhoneVerificationCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 * @param type
	 * @return
	 * @date 2014年10月21日 下午3:07:10
	 * @author Administrator
	 */
	String addPhoneVerificationCode(String phone, Integer type);

	/**
	 * 查询验证码
	 * 
	 * @Title: selectByPhone
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phoneVerificationCode
	 * @return
	 * @date 2014年10月21日 下午3:07:31
	 * @author Administrator
	 */
	PhoneVerificationCode selectByPhone(
			PhoneVerificationCode phoneVerificationCode);

	/**
	 * 改变验证码状态
	 * 
	 * @Title: editPhoneVerificationCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phoneVerificationCode
	 * @date 2014年10月21日 下午3:07:50
	 * @author Administrator
	 */
	void editPhoneVerificationCode(PhoneVerificationCode phoneVerificationCode);

	/**
	 * 
	 * @Title: queryByPhone
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phoneVerificationCode
	 * @return
	 * @date 2014年12月8日 下午1:42:12
	 * @author fjx
	 */
	PhoneVerificationCode queryByPhone(
			PhoneVerificationCode phoneVerificationCode);

	/**
	 * 统计验证码次数
	 * 
	 * @Title: getPhoneCodeCount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 * @param start
	 * @param end
	 * @return
	 * @date 2014年10月21日 下午3:10:48
	 * @author Administrator
	 */
	Integer getPhoneCodeCount(String phone, Date start, Date end);

	/**
	 * 根据手机号和类型查找最近一条验证码
	 * 
	 * @Title: getYzmByPhoneAndType
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 *            手机号码
	 * @param type
	 *            验证码类型
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2015年4月2日 下午10:15:06
	 * @author lys
	 */
	PhoneVerificationCode getYzmByPhoneAndType(String phone, Integer type)
			throws PhoneVerificationCodeException;

	/**
	 * 创建手机验证码<br>
	 * 
	 * 注：<br>
	 * 
	 * 1.同一手机号码，在同一场景下，只能有一个有效的验证码 <br>
	 * 
	 * 2.续上，如果重新发送了一条，那么之前发送的同一场景，同一手机号的验证码失效<br>
	 * 
	 * 3.手机验证码为6位随机数字（不排除有重复的可能性）
	 * 
	 * @Title: createPhoneVerificationCode
	 * 
	 * @Description: (为用户一手机号码在某一场景下创建手机短信验证码)
	 * @param phone
	 *            手机号码
	 * @param type
	 *            场景
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2015年4月3日 下午6:55:15
	 * @author lys
	 */
	String createMobilecode(String phone, Integer type)
			throws PhoneVerificationCodeException;
	
	/**
	 * 测试验证码是否正确
	 * 
	 * @Title: testMobilecode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param phone
	 * @param type
	 * @param mobilecode
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2015年4月6日 上午10:25:19  
	 * @author lys
	 */
	boolean testMobilecode(String phone, Integer type, String mobilecode)
			throws PhoneVerificationCodeException;
	
	/**
	 * 统计某一手机号码在某一场景在某一开始时间发短信的条数
	 * 
	 * @Title: statisticCount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 *            手机号码
	 * @param type
	 *            场景
	 * @param datePoint
	 *            时间点
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2015年8月11日 下午2:02:07
	 * @author lys
	 */
	Integer statisticCount(String phone, Integer type, Date datePoint)
			throws PhoneVerificationCodeException;
	
	/**
	 * 统计某一手机号码在某一场景在某一段时间发短信的条数
	 * 
	 * @Title: statisticCount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 *            手机号码
	 * @param type
	 *            场景
	 * @param minisOffset
	 *            时间偏移量
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2015年8月11日 下午2:02:07
	 * @author lys
	 */
	Integer statisticCount(String phone, Integer type, Integer minisOffset)
			throws PhoneVerificationCodeException;

	/**
	 * 验证手机验证码
	 * 
	 * @Title: checkMobilecode
	 * 
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param phone
	 *            手机号码
	 * @param type
	 *            场景
	 * @param mobilecode
	 *            手机验证码
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2015年4月3日 下午7:17:26
	 * @author lys
	 */
	boolean checkMobilecode(String phone, Integer type, String mobilecode)
			throws PhoneVerificationCodeException;

	/**
	 * @Title: findPhoneCodeByCell
	 * @Description: (通过手机号查找验证码，该方法用于因为未知原因用户注册失败时保证验证码有效)
	 * @param cell
	 * @param createTime
	 * @return
	 * @return List<PhoneVerificationCode> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月31日 下午4:31:24
	 */
	List<PhoneVerificationCode> findPhoneCodeByCell(String cell, Date createTime);
}
