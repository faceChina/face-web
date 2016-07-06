package com.zjlp.face.web.server.product.phone.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.PhoneDeviceMapper;
import com.zjlp.face.web.server.product.phone.dao.PhoneDeviceDao;
import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;
@Repository("phoneDeviceDao")
public class PhoneDeviceDaoImpl implements PhoneDeviceDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public PhoneDevice getPhoneDevice(Long id) {
		return sqlSession.getMapper(PhoneDeviceMapper.class).getPhoneDivice(id);
	}

	@Override
	public List<PhoneDevice> findPhoneDevice(Long userId, Integer type) {
		Map<String,Number> map = new HashMap<String, Number>();
		map.put("userId", userId);
		map.put("type", type);
		return sqlSession.getMapper(PhoneDeviceMapper.class).findPhoneDevice(map);
	}

	@Override
	public PhoneDevice addPhoneDevice(PhoneDevice phoneDevice) {
		sqlSession.getMapper(PhoneDeviceMapper.class).insert(phoneDevice);
		return phoneDevice;
	}

	@Override
	public void editPhoneDevice(PhoneDevice PhoneDevice) {
		sqlSession.getMapper(PhoneDeviceMapper.class).updateByPushId(PhoneDevice);
		
	}

	@Override
	public int deleteByPushId(PhoneDevice phoneDevice) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", phoneDevice.getUserId());
		map.put("pushUserId", phoneDevice.getPushUserId());
		return sqlSession.getMapper(PhoneDeviceMapper.class).deleteByPushId(map);
	}

	@Override
	public PhoneDevice selectPhoneDeviceByUserIdAndPushId(
			PhoneDevice phoneDevice) {
		return sqlSession.getMapper(PhoneDeviceMapper.class).selectPhoneDeviceByUserIdAndPushId(phoneDevice);
	}

}
