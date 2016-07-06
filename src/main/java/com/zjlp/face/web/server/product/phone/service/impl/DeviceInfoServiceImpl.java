package com.zjlp.face.web.server.product.phone.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.phone.dao.DeviceInfoDao;
import com.zjlp.face.web.server.product.phone.domain.DeviceInfo;
import com.zjlp.face.web.server.product.phone.service.DeviceInfoService;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年9月7日 下午7:38:01
 *
 */
@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {

	@Autowired
	private DeviceInfoDao deviceInfoDao;

	@Override
	public DeviceInfo getById(Long id) {
		return this.deviceInfoDao.getById(id);
	}

	@Override
	public Long add(DeviceInfo deviceInfo) {
		return this.deviceInfoDao.add(deviceInfo);
	}

	@Override
	public void updateById(DeviceInfo deviceInfo) {
		this.deviceInfoDao.updateById(deviceInfo);
	}

	@Override
	public List<DeviceInfo> findByDeviceId(String deviceId) {
		return this.deviceInfoDao.findByDeviceId(deviceId);
	}

}
