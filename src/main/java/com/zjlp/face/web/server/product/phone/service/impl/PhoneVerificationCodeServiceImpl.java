package com.zjlp.face.web.server.product.phone.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.GenerateCode;
import com.zjlp.face.web.exception.ext.PhoneVerificationCodeException;
import com.zjlp.face.web.server.product.phone.dao.PhoneVerificationCodeDao;
import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;
import com.zjlp.face.web.server.product.phone.service.PhoneVerificationCodeService;

@Service
public class PhoneVerificationCodeServiceImpl implements
		PhoneVerificationCodeService {

	private Logger log = Logger.getLogger(getClass());
	private static final Long PHONE_TIME_VALID_10M = 10 * 60 * 1000L;
	@Autowired
	PhoneVerificationCodeDao phoneVerificationCodeDao;

	@Override
	public String addPhoneVerificationCode(String phone, Integer type) {
		// 获取验证码时效常量
		Long s = Constants.PHONE_TIME_VALID;
		// 随机生成6位验证码
		String code = GenerateCode.createRandom(true, 6);
		PhoneVerificationCode pvc = new PhoneVerificationCode();
		Date date = new Date();
		pvc.setCreateTime(date);
		pvc.setUpdateTime(date);
		pvc.setCode(code);
		pvc.setCell(phone);
		pvc.setStatus(PhoneVerificationCode.STATUS_VALID);
		pvc.setType(type);
		pvc.setValidTime(new Date(date.getTime() + s * 1000));// 设置有效期
		phoneVerificationCodeDao.addPhoneVerificationCode(pvc);
		return code;
	}

	@Override
	public PhoneVerificationCode selectByPhone(
			PhoneVerificationCode phoneVerificationCode) {
		return phoneVerificationCodeDao.selectByPhone(phoneVerificationCode);
	}

	@Override
	public void editPhoneVerificationCode(
			PhoneVerificationCode phoneVerificationCode) {
		phoneVerificationCodeDao
				.editPhoneVerificationCode(phoneVerificationCode);
	}

	@Override
	public PhoneVerificationCode queryByPhone(
			PhoneVerificationCode phoneVerificationCode) {
		return phoneVerificationCodeDao.queryByPhone(phoneVerificationCode);
	}

	@Override
	public Integer getPhoneCodeCount(String phone, Date start, Date end) {
		return phoneVerificationCodeDao.getPhoneCodeCount(phone, start, end);
	}

	@SuppressWarnings("unused")
	private boolean checkInputCode(PhoneVerificationCode code, String mobilecode) {
		mobilecode = mobilecode.trim();
		AssertUtil.isTrue(mobilecode.equals(code.getCode()), 
				"Expect yzm[id={}, code={}] but is {}", 
				code.getId(), code.getCode(), mobilecode);
		return true;
	}

	private boolean checkStatus(Long id, Integer oldStatus, Integer newStatus) {
		log.info("[Check log] oldStatus="+String.valueOf(oldStatus)+", newStatus="+String.valueOf(newStatus));
		if (PhoneVerificationCode.STATUS_UNVALID.equals(newStatus)) {
			AssertUtil.isTrue(PhoneVerificationCode.STATUS_TESTED.equals(oldStatus), 
					"Mobilecode[id={}]'s status is not tested.", id);
		} else if (PhoneVerificationCode.STATUS_TESTED.equals(newStatus)) {
			AssertUtil.isTrue(PhoneVerificationCode.STATUS_VALID.equals(oldStatus), 
					"Mobilecode[id={}]'s status is not valid.", id);
		} else {
			AssertUtil.isTrue(false, "Expect status is {}", newStatus);
		}
		return true;
	}
	
	private boolean checkValidTime(PhoneVerificationCode code, Date date) {
		log.info("开始check验证码有效时间。");
		if (date.getTime() >= code.getValidTime().getTime()) {
			log.info("[Check log] code="+code.getCode()+", is timeout.");
			//超时
			this.editStatus(code.getId(), code.getStatus(), 
					PhoneVerificationCode.STATUS_UNVALID);
			return false;
		}
		log.info("check验证码有效时间完毕，有效。");
		return true;
	}
	
	@Override
	public PhoneVerificationCode getYzmByPhoneAndType(String phone, Integer type)
			throws PhoneVerificationCodeException {
		try {
			AssertUtil.hasLength(phone, "Param[phone] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			PhoneVerificationCode code = new PhoneVerificationCode();
			code.setCell(phone);
			code.setType(type);
			code = phoneVerificationCodeDao.getValidMobilecode(code);
			AssertUtil.notNull(code, "PhoneVerificationCode[type={}, phone={}] can not found.",
					type, phone);
			return code;
		} catch (Exception e) {
			throw new PhoneVerificationCodeException(e);
		}
	}

	@Override
	public String createMobilecode(String phone, Integer type)
			throws PhoneVerificationCodeException {
		try {
			AssertUtil.hasLength(phone, "Param[phone] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			/** 4.7 验证码策略修改  start */
			//某一手机一场景的验证码全部失效
//			this.unvalidMobilecode(phone, type);
			/** 4.7 验证码策略修改  end */
			//创建某一场景，某一手机的验证码
			PhoneVerificationCode newCode = this.mobilecodeProduct(phone, type);
			return newCode.getCode();
		} catch (Exception e) {
			throw new PhoneVerificationCodeException("获取手机验证码失败", e);
		}
	}

	/**
	 * 某一手机一场景的验证码全部失效
	 * @Title: unvalidMobilecode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param mobilecode 条件：手机号+场景
	 * @date 2015年4月3日 下午7:03:49  
	 * @author lys
	 */
	private void unvalidMobilecode(String phone, Integer type) {
		PhoneVerificationCode mobilecode = new PhoneVerificationCode(phone, type);
		mobilecode.setUpdateTime(new Date());
		mobilecode.setStatus(PhoneVerificationCode.STATUS_UNVALID);
		phoneVerificationCodeDao.unvalidMobilecode(mobilecode);
	}
	
	/**
	 * 修改手机验证码的状态
	 * @Title: editStatus 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param newStatus
	 * @param oldStatus
	 * @date 2015年4月6日 上午10:31:32  
	 * @author lys
	 */
	private void editStatus(Long id, Integer oldStatus, Integer newStatus) {
		PhoneVerificationCode mobilecode = new PhoneVerificationCode();
		mobilecode.setId(id);
		mobilecode.setUpdateTime(new Date());
		mobilecode.setStatus(newStatus);
		phoneVerificationCodeDao.unvalidMobilecodeById(mobilecode);
		log.info(this.getLogInfo("修改验证码状态：旧状态=", oldStatus,"新状态=",newStatus));
	}
	
	/**
	 * 创建某一场景，某一手机的验证码
	 * @Title: mobilecodeProduct 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param phone 手机号码
	 * @param type 场景
	 * @return
	 * @date 2015年4月3日 下午7:09:56  
	 * @author lys
	 */
	@Transactional
	private PhoneVerificationCode mobilecodeProduct(String phone, Integer type) {
		PhoneVerificationCode pvc = PhoneVerificationCode.getInstance(phone, type, 
				new Date(new Date().getTime() + PHONE_TIME_VALID_10M));
		phoneVerificationCodeDao.addPhoneVerificationCode(pvc);
		return pvc;
	}
	
	

	@Override
	public boolean testMobilecode(String phone, Integer type, String mobilecode)
			throws PhoneVerificationCodeException {
		try {
			AssertUtil.hasLength(phone, "Param[phone] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			/** 4.7 验证码策略修改  start */
			PhoneVerificationCode codeRecord = this.getYzmByPhoneAndType(phone, type, mobilecode);
			/** 4.7 验证码策略修改  end */
			//状态验证
			this.checkStatus(codeRecord.getId(), codeRecord.getStatus(), 
					PhoneVerificationCode.STATUS_TESTED);
			//次数验证
			boolean incount = this.ckAndMinusCount(codeRecord, false);
			AssertUtil.isTrue(incount, "code is out of count!");
			//时间验证
			boolean intime = this.checkValidTime(codeRecord, new Date());
			AssertUtil.isTrue(intime, "Yzm[id={},type={}] is timeout.", 
					codeRecord.getId(), codeRecord.getType());
			//状态修改
			this.editStatus(codeRecord.getId(), codeRecord.getStatus(), 
					PhoneVerificationCode.STATUS_TESTED);
			log.info("测试验证码完毕，结果：成功");
			return true;
		} catch (Exception e) {
			log.info(this.getLogInfo("测试验证码发生异常:", e.getMessage()));
			throw new PhoneVerificationCodeException(e);
		}
	}

	/** 4.7 验证码策略修改  start */
	private PhoneVerificationCode getYzmByPhoneAndType(String phone,
			Integer type, String mobilecode) {
		try {
			log.info(getLogInfo("开始获取消费验证码：手机号", phone, "验证码",mobilecode,"类型",type));
			AssertUtil.hasLength(phone, "Param[phone] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			PhoneVerificationCode code = new PhoneVerificationCode();
			code.setCell(phone);
			code.setType(type);
			List<PhoneVerificationCode> list = phoneVerificationCodeDao.getValidMobilecodeList(code);
			AssertUtil.notEmpty(list, "PhoneVerificationCode[type={}, phone={}] can not found.",
					type, phone);
			//筛选
			code = null;
			mobilecode = mobilecode.trim();
			for (PhoneVerificationCode phoneVerificationCode : list) {
				if (null != phoneVerificationCode && phoneVerificationCode.getCode().equals(mobilecode)) {
					code = phoneVerificationCode;
					break;
				}
			}
			log.info(getLogInfo("获取消费验证码：", String.valueOf(code)));
			return code;
		} catch (Exception e) {
			log.info(this.getLogInfo("获取消费验证码发生异常:", e.getMessage()));
			throw new PhoneVerificationCodeException(e);
		}
	}
	/** 4.7 验证码策略修改  end */

	@Override
	public Integer statisticCount(String phone, Integer type, Date datePoint)
			throws PhoneVerificationCodeException {
		try {
			PhoneVerificationCode code = new PhoneVerificationCode();
			code.setCell(phone);
			code.setType(type);
			code.setCreateTime(datePoint);
			Integer count = phoneVerificationCodeDao.getStatisticCount(code);
			return null == count ? 0 : count;
		} catch (Exception e) {
			log.info(this.getLogInfo("统计验证码个数发生异常:", e.getMessage()));
			throw new PhoneVerificationCodeException(e);
		}
	}

	@Override
	public Integer statisticCount(String phone, Integer type,
			Integer minisOffset) throws PhoneVerificationCodeException {
		Date date = new Date();
		Date datePoint = DateUtil.addMinute(date, -1 * minisOffset);
		return statisticCount(phone, type, datePoint);
	}
	
	@Override
	public boolean checkMobilecode(String phone, Integer type, String mobilecode)
			throws PhoneVerificationCodeException {
		try {
			AssertUtil.hasLength(phone, "Param[phone] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			/** 4.7 验证码策略修改  start */
			PhoneVerificationCode codeRecord = this.getYzmByPhoneAndType(phone, type, mobilecode);
			/** 4.7 验证码策略修改  end */
			this.checkStatus(codeRecord.getId(), codeRecord.getStatus(), 
					PhoneVerificationCode.STATUS_UNVALID);
			log.info("验证验证码状态完毕。");
			//次数验证
			boolean incount = this.ckAndMinusCount(codeRecord, true);
			AssertUtil.isTrue(incount, "code is out of count!");
			//时间验证
			boolean intime = this.checkValidTime(codeRecord, new Date());
			AssertUtil.isTrue(intime, "Yzm[id={},type={}] is timeout.", 
					codeRecord.getId(), codeRecord.getType());
			this.editStatus(codeRecord.getId(), codeRecord.getStatus(), 
					PhoneVerificationCode.STATUS_UNVALID);
			/** 4.7 验证码策略修改  start */
			//其余的验证码全部失效
			this.unvalidMobilecode(phone, type);
			log.info(this.getLogInfo("同一场景的验证码全部失效：手机号码=", phone,"场景=",type));
			/** 4.7 验证码策略修改  end */
			log.info("验证验证码完毕，结果：成功");
			return true;
		} catch (Exception e) {
			log.info(this.getLogInfo("验证验证码发生异常:", e.getMessage()));
			throw new PhoneVerificationCodeException(e);
		}
	}
	
	private boolean ckAndMinusCount(PhoneVerificationCode code, boolean minFlag) {
		log.info("开始check验证码的验证次数");
		AssertUtil.notNull(code, "Param[code] can not be null.");
		//验证次数是否还可以验证
		if (!code.inCount()) {
			log.info("验证码验证次数已满，此验证码失效。");
			this.editStatus(code.getId(), code.getStatus(), 
					PhoneVerificationCode.STATUS_UNVALID);
			return false;
		}
		if (minFlag) {
			//次数减
			phoneVerificationCodeDao.minusCount(code.getId());
			log.info("可验证次数减一");
		}
		return true;
	}

	@Override
	public List<PhoneVerificationCode> findPhoneCodeByCell(String cell, Date createTime) {
		try {
			AssertUtil.hasLength(cell, "Param[cell] can not be null.");
			return this.phoneVerificationCodeDao.getPhoneCodeByCell(cell, createTime);
		} catch (Exception e) {
			throw new PhoneVerificationCodeException(e);
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
