package com.zjlp.face.web.server.product.phone.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.DeviceInfoMapper;
import com.zjlp.face.web.server.product.phone.dao.DeviceInfoDao;
import com.zjlp.face.web.server.product.phone.domain.DeviceInfo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年9月7日 下午7:31:36
 *
 */
@Repository("DeviceInforDao")
public class DeviceInfoDaoImpl implements DeviceInfoDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public DeviceInfo getById(Long id) {
		return this.sqlSession.getMapper(DeviceInfoMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public Long add(DeviceInfo deviceInfo) {
		this.sqlSession.getMapper(DeviceInfoMapper.class).insert(deviceInfo);
		return deviceInfo.getId();
	}

	@Override
	public void updateById(DeviceInfo deviceInfo) {
		this.sqlSession.getMapper(DeviceInfoMapper.class).updateByPrimaryKey(deviceInfo);
	}

	@Override
	public List<DeviceInfo> findByDeviceId(String deviceId) {
		return this.sqlSession.getMapper(DeviceInfoMapper.class).selectByDeviceId(deviceId);
	}


}
