package com.zjlp.face.web.server.product.phone.producer.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.PhoneDeviceException;
import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;
import com.zjlp.face.web.server.product.phone.producer.PhoneDeviceProducer;
import com.zjlp.face.web.server.product.phone.service.PhoneDeviceService;

@Repository("phoneDeviceProducer")
public class PhoneDeviceProducerImpl implements PhoneDeviceProducer{
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private PhoneDeviceService phoneDeviceService;

	@Override
	public PhoneDevice getPhoneDevice(Long id) {
		AssertUtil.notNull(id, "参数[id]为空，调用失败！");
		return this.phoneDeviceService.getPhoneDevice(id);
	}

	@Override
	public List<PhoneDevice> findPhoneDevice(Long userId, Integer type) {
		AssertUtil.notNull(userId, "参数[userId]为空，调用失败！");
		AssertUtil.notNull(type, "参数[type]为空，调用失败！");
		return this.phoneDeviceService.findPhoneDevice(userId, type);
	}

	@Override
	public Long addPhoneDevice(PhoneDevice phoneDevice) throws PhoneDeviceException {
		if (phoneDevice != null) {
			this.phoneDeviceService.addPhoneDevice(phoneDevice);
			logger.info("Add device completed!");
		}
		logger.info("Find device failed!");
		return null;
	}

	@Override
	public void editPhoneDevice(PhoneDevice phoneDevice) {
		PhoneDevice existDevice = null;
		if (phoneDevice != null && phoneDevice.getId() != null) {
			existDevice = phoneDeviceService.getPhoneDevice(phoneDevice.getId());
			if (existDevice != null && duplicateDevice(existDevice, phoneDevice)) {
				return;
			} else if (existDevice != null) {
				this.phoneDeviceService.editPhoneDevice(phoneDevice);
			} else {
				AssertUtil.notNull(phoneDevice, "找不到Device信息：" + phoneDevice.getId());
			}
		}

	}
	
	@Override
	public int deleteByPushId(PhoneDevice phoneDevice) throws PhoneDeviceException {
		AssertUtil.notNull(phoneDevice.getPushUserId(), "参数[pushUserId]为空，调用失败！");
		AssertUtil.notNull(phoneDevice.getUserId(), "参数[getUserId]为空，调用失败！");
		return this.phoneDeviceService.deleteByPushId(phoneDevice);
	}

	@Override
	public PhoneDevice selectPhoneDeviceByUserIdAndPushId(Long userId, String pushUserId) {
		AssertUtil.notNull(userId, "参数[userId]为空，调用失败！");
		AssertUtil.notNull(pushUserId, "参数[pushUserId]为空，调用失败！");
		return this.phoneDeviceService.selectPhoneDeviceByUserIdAndPushId(userId, pushUserId);
	}

	@SuppressWarnings("unused")
	private boolean alreadyExistDevice(Long id) {
		if (id != null) {
			PhoneDevice existDevice = null;
			try {
				existDevice = phoneDeviceService.getPhoneDevice(id);
			} catch (Exception e) {
				logger.info("Find device fail!" + e);
			}
			return existDevice != null && existDevice.getId() != null;
		}
		return false;
	}

	/**
	 * @Title: duplicateDevice
	 * @Description: (判断设备是都完全相同)
	 * @param exist
	 * @param edit
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月16日 下午4:09:20
	 */
	public boolean duplicateDevice(PhoneDevice exist, PhoneDevice edit) {
		if (exist != null && edit != null) {
			if (!exist.getUserId().equals(edit.getUserId())) {
				return false;
			}
			if (isNotBlank(exist.getPushUserId()) && exist.getPushUserId().equals(edit.getPushUserId())) {
				return false;
			}
			if (!exist.getStatus().equals(edit.getStatus())) {
				return false;
			}
			if (!exist.getDeviceType().equals(edit.getDeviceType())) {
				return false;
			}
			if (!exist.getType().equals(edit.getType())) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}
}
