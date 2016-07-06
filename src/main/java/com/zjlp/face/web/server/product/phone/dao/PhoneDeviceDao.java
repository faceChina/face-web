package com.zjlp.face.web.server.product.phone.dao;

import java.util.List;

import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;


public interface PhoneDeviceDao {
	/**
	 * 根据id查找PhoneDevice
	* @Title: getPhoneDevice
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @return
	* @return PhoneDevice    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月6日 下午2:17:31
	 */
	PhoneDevice getPhoneDevice(Long id);
	/**
	 * 根据userId获取userId下所有的PhoneDevice
	* @Title: findPhoneDevice
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @return
	* @return List<PhoneDevice>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月6日 下午2:17:59
	 */
	List<PhoneDevice> findPhoneDevice(Long userId,Integer type);
	/**
	 * 新增PhoneDevice
	* @Title: addUser
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param PhoneDevice
	* @return
	* @return Long    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月6日 下午2:18:47
	 */
	PhoneDevice addPhoneDevice(PhoneDevice phoneDevice);
	
	/**
	 * 编辑PhoneDevice
	* @Title: editPhoneDevice
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param PhoneDevice
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月6日 下午4:48:16
	 */
	void editPhoneDevice(PhoneDevice phoneDevice);
	/**
	 * 删除pushUserId的数据
	* @Title: deleteByPushId
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param pushUserId
	* @return
	* @return int    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月7日 下午5:09:30
	 */
	int deleteByPushId(PhoneDevice phoneDevice);
	
	/**
	 * @param phoneDevice
	 * @return
	 */
	public PhoneDevice selectPhoneDeviceByUserIdAndPushId(PhoneDevice phoneDevice);
}
