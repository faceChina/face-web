package com.zjlp.face.web.server.product.phone.business.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.metaq.utils.HttpClientUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.exception.ext.DeviceInfoException;
import com.zjlp.face.web.server.product.phone.business.DeviceInfoBusiness;
import com.zjlp.face.web.server.product.phone.domain.DeviceInfo;
import com.zjlp.face.web.server.product.phone.service.DeviceInfoService;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年9月7日 下午7:43:00
 *
 */
@Service("deviceInfoBusiness")
public class DeviceInfoBusinessImpl implements DeviceInfoBusiness {

	private Logger _log = Logger.getLogger(getClass());

	private static final String CALLBACK_ADDRESS_FIR = "callbackAddressFirst";

	private static final String CALLBACK_ADDRESS_SEC = "callbackAddressSecond";

	private static final String DEFAUTL_CHARSET = "UTF-8";

	private static final String RET_TYPE = "ret_type";

	private static final String REPLAY = "replay";

	private static final String SUCCESS = "success";

	@Autowired
	private DeviceInfoService deviceInfoService;

	@Override
	public synchronized Long addOrUpdateDevice(String deviceId, boolean isFirstTime) throws DeviceInfoException {
		try {
			AssertUtil.notNull(deviceId, "Param[deviceId] can not be null.");
			List<DeviceInfo> existDevices = this.deviceInfoService.findByDeviceId(deviceId);
			DeviceInfo deviceInfo = new DeviceInfo();
			Date date = new Date();
			deviceInfo.setUpdateTime(date);
			if (CollectionUtils.isEmpty(existDevices)) {// 首次注册设备信息
				deviceInfo.setDeviceInfo(deviceId);
				// 首次为0,二次为2
				deviceInfo.setReplay(isFirstTime ? 0 : 2);
				deviceInfo.setStatus(1);
				deviceInfo.setCreateTime(date);
				return this.deviceInfoService.add(deviceInfo);
			}
			DeviceInfo firstDevice = existDevices.get(0);
			if (null != firstDevice && !isFirstTime && new Integer(1).equals(firstDevice.getReplay())) {// 二次注册设备新
				// 第二次任务，无需重复注册设备，修改replay=2
				deviceInfo.setId(existDevices.get(0).getId());
				deviceInfo.setReplay(2);
				this.deviceInfoService.updateById(deviceInfo);
				return 0L;
			} else {
				_log.info("设备[" + deviceId + "]已经注册.");
				return -1L;
			}
		} catch (Exception e) {
			_log.error("注册设备失败！");
			throw new DeviceInfoException(e);
		}
	}

	@Override
	public void callback(String deviceId) throws DeviceInfoException {
		try {
			AssertUtil.notNull(deviceId, "Param[deviceId] can not be null.");
			List<DeviceInfo> existDevices = this.deviceInfoService.findByDeviceId(deviceId);
			if (CollectionUtils.isEmpty(existDevices)) {
				_log.info("设备[" + deviceId + "]未注册或者已经完成二次任务,无需回调.");
				return;
			}
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setUpdateTime(new Date());
			DeviceInfo existDevice = existDevices.get(0);
			String ret_type = null;
			String result = null;
			switch (existDevice.getReplay()) {
			case 0:
				String callbackAddressFir = PropertiesUtil.getContexrtParam(CALLBACK_ADDRESS_FIR);
				AssertUtil.hasLength(callbackAddressFir, "youqianConfig.properties未配置字段callbackAddressFir");
				ret_type = PropertiesUtil.getContexrtParam(RET_TYPE);
				AssertUtil.hasLength(ret_type, "youqianConfig.properties未配置字段ret_type");
				StringBuilder sb1 = new StringBuilder(callbackAddressFir);
				sb1.append("?did=").append(deviceId).append("&ret_type=").append(ret_type);
				result = HttpClientUtils.getInstances().doGet(sb1.toString(), DEFAUTL_CHARSET);
				if (!SUCCESS.equals(result)) {
					_log.warn("首次回调失败,MSG=" + result);
					return;
				}
				/** 如果回调成功,更新状态replay = 1 **/
				deviceInfo.setId(existDevice.getId());
				deviceInfo.setReplay(1);// 标识完成首次任务
				this.deviceInfoService.updateById(deviceInfo);
				_log.info("首次回调成功,用户将会获得奖励！");
				break;

			case 1:
				_log.info("设备成功注册并且已经完成首次下载任务");
				break;

			case 2:
				String callbackAddressSec = PropertiesUtil.getContexrtParam(CALLBACK_ADDRESS_SEC);
				AssertUtil.hasLength(callbackAddressSec, "youqianConfig.properties未配置字段callbackAddressSec");
				ret_type = PropertiesUtil.getContexrtParam(RET_TYPE);
				AssertUtil.hasLength(ret_type, "youqianConfig.properties未配置字段ret_type");
				String replay = PropertiesUtil.getContexrtParam(REPLAY);
				AssertUtil.hasLength(replay, "youqianConfig.properties未配置字段repaly");
				StringBuilder sb2 = new StringBuilder(callbackAddressSec);
				sb2.append("?did=").append(deviceId).append("&ret_type=").append(ret_type).append("&replay=").append(replay);
				result = HttpClientUtils.getInstances().doGet(sb2.toString(), DEFAUTL_CHARSET);
				if (!SUCCESS.equals(result)) {
					_log.warn("二次回调失败,MSG=" + result);
					return;
				}
				/** 如果二次回调成功,更新状态status = -1 **/
				deviceInfo.setId(existDevice.getId());
				deviceInfo.setStatus(-1);
				this.deviceInfoService.updateById(deviceInfo);
				_log.info("二次回调成功,用户将获得奖励");
				break;
			default:
				_log.info("设备未注册或者已经完成所有任务");
				break;
			}
		} catch (Exception e) {
			_log.error("回调验证失败！");
			throw new DeviceInfoException(e);
		}
	}

}
