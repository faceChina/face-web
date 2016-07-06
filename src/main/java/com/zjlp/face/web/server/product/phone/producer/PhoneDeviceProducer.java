package com.zjlp.face.web.server.product.phone.producer;

import java.util.List;

import com.zjlp.face.web.exception.ext.PhoneDeviceException;
import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;

public interface PhoneDeviceProducer {
	
	PhoneDevice getPhoneDevice(Long id);
	
	List<PhoneDevice> findPhoneDevice(Long userId,Integer type);
	
	Long addPhoneDevice(PhoneDevice phoneDevice) throws PhoneDeviceException;

	void editPhoneDevice(PhoneDevice phoneDevice);
	
	int deleteByPushId(PhoneDevice phoneDevice) throws PhoneDeviceException;

	PhoneDevice selectPhoneDeviceByUserIdAndPushId(Long userId,String pushUserId);
}
