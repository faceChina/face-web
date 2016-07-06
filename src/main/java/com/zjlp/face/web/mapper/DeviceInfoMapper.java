package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.phone.domain.DeviceInfo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年9月7日 下午7:29:50
 *
 */
public interface DeviceInfoMapper {

	DeviceInfo selectByPrimaryKey(Long id);

	int insert(DeviceInfo deviceInfo);

	int updateByPrimaryKey(DeviceInfo deviceInfo);

	List<DeviceInfo> selectByDeviceId(String deviceId);

}
