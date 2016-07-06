package com.zjlp.face.web.server.product.phone.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.zjlp.face.web.exception.ext.PhoneDeviceException;
import com.zjlp.face.web.server.product.phone.dao.PhoneDeviceDao;
import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;
import com.zjlp.face.web.server.product.phone.service.PhoneDeviceService;
import com.zjlp.face.web.util.redis.RedisKey;

@Service("phoneDeviceService")
public class PhoneDeviceServiceImpl implements PhoneDeviceService{
	
//	private static final Integer FIVE_MINUTES = 24 * 60 * 60;

//	private static final String CACHE_KEY_PRE = "GETCACHEDATA_USER_PUSH_";

	private Logger logger = Logger.getLogger(this.getClass());
	 
	@Autowired
	private PhoneDeviceDao phoneDeviceDao;

	@Override
	public PhoneDevice getPhoneDevice(Long id) {
		Assert.notNull(id);
		return phoneDeviceDao.getPhoneDevice(id);
	}

	@Override
	public List<PhoneDevice> findPhoneDevice(final Long userId, final Integer type) {
		try {
			Assert.notNull(userId, "用户ID为空！");
			Assert.notNull(type, "Type为空！");
			String key = new StringBuilder(RedisKey.PhoneDevice_findPhoneDevice_key).append("_")
					.append(userId).append(type).toString();
			return phoneDeviceDao.findPhoneDevice(userId, type);
		/*	return RedisStringHelper.getAndSet(key, new AbstractRedisDaoSupport() {
				@Override
				public Object support() {
					return phoneDeviceDao.findPhoneDevice(userId, type);
				}
			});*/
		} catch (Exception e) {
			logger.error("查找用户PushId失败" + e);
			return phoneDeviceDao.findPhoneDevice(userId, type);
		}
	}

	@Override
	public Long addPhoneDevice(PhoneDevice phoneDevice) throws PhoneDeviceException {
		try {
			// 先检查 有没有这个用户
			Assert.notNull(phoneDevice.getUserId(), "用户为空！");
			Assert.notNull(phoneDevice.getPushUserId(), "PushId为空！");
			Assert.notNull(phoneDevice.getDeviceType(), "DeviceId为空！");
			Assert.notNull(phoneDevice.getType(), "Type为空！");
			// 先删除该设备绑定的账号
			this.deleteByPushId(phoneDevice);
			
			PhoneDevice rp = phoneDeviceDao.addPhoneDevice(phoneDevice);// 存数据库后返回ID
			final Long id = rp.getId();
			String key = new StringBuilder(RedisKey.PhoneDevice_findPhoneDevice_key).append("_")
					.append(phoneDevice.getUserId()).append(phoneDevice.getType()).toString();
			
			return id;
		/*	return RedisStringUtil.set(key, new AbstractRedisDaoSupport() {
				@Override
				public Object support() {
					return phoneDeviceDao.getPhoneDevice(id);// 根据ID查出phoneDevice，放入缓存
				}
			});*/
		} catch (Exception e) {
			throw new PhoneDeviceException("获取缓存异常", e);
		}
	}

	@Override
	public void editPhoneDevice(PhoneDevice phoneDevice) {
		phoneDeviceDao.editPhoneDevice(phoneDevice);
		
	}

	@Override
	public int deleteByPushId(PhoneDevice phoneDevice) throws PhoneDeviceException {
		try{
			Assert.notNull(phoneDevice.getPushUserId());
			Assert.notNull(phoneDevice.getUserId());
			return phoneDeviceDao.deleteByPushId(phoneDevice);
		}catch(Exception e){
			throw new PhoneDeviceException("删除pushUserId失败",e);
		}
	}

	@Override
	public PhoneDevice selectPhoneDeviceByUserIdAndPushId(Long userId,String pushUserId) {
		PhoneDevice phoneDevice =  new PhoneDevice();
		phoneDevice.setUserId(userId);
		phoneDevice.setPushUserId(pushUserId);
		return phoneDeviceDao.selectPhoneDeviceByUserIdAndPushId(phoneDevice);
	}

}
