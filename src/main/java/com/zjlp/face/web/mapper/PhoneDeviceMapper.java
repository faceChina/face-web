package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;

public interface PhoneDeviceMapper {
	
	PhoneDevice getPhoneDivice(Long id);

	List<PhoneDevice> findPhoneDevice(Map<String, Number> map);

	int insert(PhoneDevice phoneDevice);

	void updateByPushId(PhoneDevice phoneDevice);

	int deleteByPushId(Map<String, Object> map);

	PhoneDevice selectPhoneDeviceByUserIdAndPushId(PhoneDevice phoneDevice);

}
