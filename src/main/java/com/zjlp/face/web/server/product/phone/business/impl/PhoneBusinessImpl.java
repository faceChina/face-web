package com.zjlp.face.web.server.product.phone.business.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.web.component.sms.Sms;
import com.zjlp.face.web.component.sms.Sms.SwitchType;
import com.zjlp.face.web.component.sms.SmsContent;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.PhoneVerificationCodeException;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;
import com.zjlp.face.web.server.product.phone.service.PhoneVerificationCodeService;
import com.zjlp.face.web.util.Logs;

@Service
public class PhoneBusinessImpl implements PhoneBusiness{

	private Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private PhoneVerificationCodeService phoneVerificationCodeService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "PhoneVerificationCodeException" })
	public void getPhoneCode(String phone,Integer type,SmsContent modcode)
			throws PhoneVerificationCodeException {
		this.createMobilecode(phone, type);
	}

	@Override
	public Integer checkPhoneCode(String phone, String phoneCode, Integer type)
			throws PhoneVerificationCodeException {
		try {
			_logger.info(getLogInfo("开始消费验证码：手机号码=",phone,"，手机验证码=",phoneCode,"验证类型=",type));
			boolean istrue = this.testMobilecode(phone, type, phoneCode);
			boolean is = this.checkMobilecode(phone, type, phoneCode);
			_logger.info(getLogInfo("开始消费验证码：手机号码=",phone,"，手机验证码=",phoneCode,"验证类型=",type,(istrue && is) ? "成功":"失败"));
			return istrue && is ? 1 : 0;
		} catch (Exception e) {
			_logger.info("check验证码发生异常，返回0，异常信息："+e.getMessage(), e);
			return 0;
		}
	}
	
	
	@Deprecated
	@Override
	public Integer fristCheckPhoneCode(String phone, String phoneCode,
			Integer type) throws PhoneVerificationCodeException {
		try {
			Assert.notNull(phone,"手机号码为空！");
			Assert.notNull(phoneCode,"手机验证码为空！");
			PhoneVerificationCode phoneVerificationCode = new PhoneVerificationCode();
			phoneVerificationCode.setCell(phone);
			phoneVerificationCode.setType(type);
			phoneVerificationCode = phoneVerificationCodeService.selectByPhone(phoneVerificationCode);
			if(null == phoneVerificationCode || null == phoneVerificationCode.getCode()){
				return 0;
			}
			Date date = new Date();
			//1.验证码超时处理
			if(date.getTime() > phoneVerificationCode.getValidTime().getTime()){
				return 3;
			}
			//2.验证验证码及应用场景
			if(phoneCode.trim().equals(phoneVerificationCode.getCode().trim()) && phoneVerificationCode.getType().intValue() == type.intValue()){
				return 1;
			}
			return 0;
		} catch (Exception e) {
			_logger.info("验证失败！" + e);
			return 0;
		}
	}
	
	/**
	 * 获取用户发送验证码次数
	 * @Title: checkCodeSum 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param phone
	 * @return
	 * @throws PhoneVerificationCodeException
	 * @date 2015年4月3日 上午12:43:24  
	 * @author fjx
	 */
	private Integer checkCodeSum(String phone) throws PhoneVerificationCodeException {
		try {
			Assert.notNull(phone,"手机号码为空！");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date start = calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date end = calendar.getTime();
			Integer count = phoneVerificationCodeService.getPhoneCodeCount(phone, start, end);
			Assert.notNull(count);
			return count;
		} catch (Exception e) {
			throw new PhoneVerificationCodeException(e);
		}

	}

	@Override
	public boolean checkMobilecode(String cell, Integer type, String mobilecode)
			throws PhoneVerificationCodeException {
		try {
			_logger.info(getLogInfo("验证验证码完毕：手机号码=",cell,"，手机验证码=",mobilecode,"验证类型=",type));
			boolean result = phoneVerificationCodeService.checkMobilecode(cell, type, mobilecode);
			_logger.info(getLogInfo("验证验证码完毕：手机号码=",cell,"，手机验证码=",mobilecode,"验证类型=",type,"结果：",result?"成功":"失败"));
			return result;
		} catch (Exception e) {
			throw new PhoneVerificationCodeException("验证码输入错误", e);
		}
	}

	@Override
	public String createMobilecode(String cell, Integer type)
			throws PhoneVerificationCodeException {
		try {
			Assert.notNull(cell, "手机号码为空！");
			Assert.notNull(type, "类型为空！");
			// 验证手机号码
			Pattern jz = Pattern.compile(Constants.MESSAGE_PHONE_RULE);
			if (!jz.matcher(cell).matches()) {
				_logger.info("手机号码验证失败！号码为：" + cell);
				return null;
			}
			String mobilecode = null;
			// 1.统计验证码发送次数
			Integer sum = this.checkCodeSum(cell);
			Logs.print(cell+"今日发送次数:"+sum);
			// 2.判断是否超过每日发送次数
			if (Constants.PHONE_CODE_COUNT.intValue() > sum.intValue()) {
				// 3.生成验证码记录
				mobilecode = phoneVerificationCodeService.createMobilecode(
						cell, type);
				// 4.发送验证码
				Sms.getInstance().sendUmsBySwitch(
						SwitchType.UMS_PHONECODE_SWITCH, SmsContent.UMS_202,
						cell, mobilecode);
			}
			Logs.print("验证码:" + mobilecode);
			return mobilecode;
		} catch (Exception e) {
			throw new PhoneVerificationCodeException("验证码发送失败！", e);
		}
	}
	
	@Override
	public String createMobilecodeByJson(String cell, Integer type)
			throws PhoneVerificationCodeException {
		try {
			Assert.notNull(cell, "手机号码为空！");
			Assert.notNull(type, "类型为空！");
			// 验证手机号码
			Pattern jz = Pattern.compile(Constants.MESSAGE_PHONE_RULE);
			if (!jz.matcher(cell).matches()) {
				_logger.info("手机号码验证失败！号码为：" + cell);
				return null;
			}
			String json = null;
			// 1.统计验证码发送次数
			Integer sum = this.checkCodeSum(cell);
			Logs.print(cell+"今日发送次数:"+sum);
			// 2.判断是否超过每日发送次数
			if (Constants.PHONE_CODE_COUNT.intValue() > sum.intValue()) {
				// 3.生成验证码记录
				String mobilecode = phoneVerificationCodeService.createMobilecode(
						cell, type);
				// 4.发送验证码
				json = Sms.getInstance().sendUmsByJson(
						SwitchType.UMS_PHONECODE_SWITCH, SmsContent.UMS_202,
						cell, mobilecode);
				json = addJsonItem(json, "mobilecode", mobilecode);
				return json;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("result", "-2");
			map.put("flag", "false");
			json = JSONObject.fromObject(map).toString();
			return json;
		} catch (Exception e) {
			throw new PhoneVerificationCodeException("验证码发送失败！", e);
		}
	}
	
	/**
	 * 向json数据中添加项目
	 * @Title: addJsonItem 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param json 原有的json字符串
	 * @param key 添加键
	 * @param value 添加值
	 * @return
	 * @date 2015年8月12日 下午2:52:13  
	 * @author lys
	 */
	private static String addJsonItem(String json, String key, Object value){
		JSONObject jsonObject = JSONObject.fromObject(json);
		jsonObject.element(key, value);
		return jsonObject.toString();
	}

	@Override
	public boolean testMobilecode(String cell, Integer type, String mobilecode)
			throws PhoneVerificationCodeException {
		try {
			_logger.info(getLogInfo("开始测试验证码：手机号码=",cell,"，手机验证码=",mobilecode,"验证类型=",type));
			boolean result = phoneVerificationCodeService.testMobilecode(cell, type, mobilecode);
			_logger.info(getLogInfo("测试验证码完毕：手机号码=",cell,"，手机验证码=",mobilecode,"验证类型=",type,"结果：",result?"成功":"失败"));
			return result;
		} catch (Exception e) {
			throw new PhoneVerificationCodeException(e);
		}
	}

	@Override
	public List<PhoneVerificationCode> findPhoneCodeByCell(String cell) throws PhoneVerificationCodeException {
		try {
			Assert.notNull(cell, "手机号码为空！");
			/** 查询最近24小时的验证码 */
			Date current = new Date();
			Date zeropoint = DateUtil.getZeroPoint(current);
			return this.phoneVerificationCodeService.findPhoneCodeByCell(cell, DateUtil.addHour(zeropoint, -24));
		} catch (Exception e) {
			throw new PhoneVerificationCodeException("根据手机号查找验证码失败！", e);
		}
	}

	@Override
	public void updatePhoneCode(Long id, Integer status) throws PhoneVerificationCodeException {
		try {
			PhoneVerificationCode phoneVerificationCode = new PhoneVerificationCode();
			phoneVerificationCode.setId(id);
			phoneVerificationCode.setUpdateTime(new Date());
			phoneVerificationCode.setStatus(status);
			phoneVerificationCode.setType(1);// 只更新注册类型的验证码
			// 获取验证码时效常量
			if (1 == status.intValue()) {
				Long s = Constants.PHONE_TIME_VALID;
				Date date = new Date();
				phoneVerificationCode.setValidTime(new Date(date.getTime() + s * 1000));// 有效期3分钟
			}
			this.phoneVerificationCodeService.editPhoneVerificationCode(phoneVerificationCode);
		} catch (Exception e) {
			throw new PhoneVerificationCodeException("更新验证码失败！", e);
		}
	}
	
	private String getLogInfo(Object...objects){
		if (null == objects || objects.length == 0) return null;
		StringBuilder sb = new StringBuilder();
		for (Object object : objects) {
			sb.append(String.valueOf(object));
		}
		return sb.toString();
	}

}
