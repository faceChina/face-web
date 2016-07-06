package com.zjlp.face.web.server.product.phone.dao;

import java.util.List;

import com.zjlp.face.web.server.product.phone.domain.DeviceInfo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年9月7日 下午7:29:45
 *
 */
public interface DeviceInfoDao {

	/**
	 * @Title: getById
	 * @Description: (根据主键查找)
	 * @param id
	 * @return
	 * @return DeviceInfor 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月7日 下午7:33:13
	 */
	DeviceInfo getById(Long id);

	/**
	 * @Title: add
	 * @Description: (插入)
	 * @param deviceInfo
	 * @return
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月7日 下午7:35:09
	 */
	Long add(DeviceInfo deviceInfo);

	/**
	 * @Title: updateById
	 * @Description: (根据主键更新)
	 * @param deviceInfo
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月7日 下午7:35:39
	 */
	void updateById(DeviceInfo deviceInfo);

	/**
	 * @Title: findByDeviceId
	 * @Description: (根据设备ID查找)
	 * @param deviceId
	 * @return
	 * @return List<DeviceInfor> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月7日 下午7:30:44
	 */
	List<DeviceInfo> findByDeviceId(String deviceId);

}
