package com.zjlp.face.web.server.product.phone.business;

import com.zjlp.face.web.exception.ext.DeviceInfoException;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年9月7日 下午7:40:09
 *
 */
public interface DeviceInfoBusiness {

	/**
	 * @Title: addDeviceInforOrCallback
	 * @Description: (注册设备)
	 * @param deviceId
	 * @param isFirstTime
	 * @return
	 * @throws DeviceInfoException
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月8日 下午7:56:29
	 */
	Long addOrUpdateDevice(String deviceId, boolean isFirstTime) throws DeviceInfoException;

	/**
	 * @Title: callback
	 * @Description: (设备回调验证,用于奖励)
	 * @param deviceId
	 * @throws DeviceInfoException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月8日 下午7:56:09
	 */
	void callback(String deviceId) throws DeviceInfoException;

}
