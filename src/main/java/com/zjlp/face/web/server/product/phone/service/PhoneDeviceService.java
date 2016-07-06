package com.zjlp.face.web.server.product.phone.service;

import java.util.List;

import com.zjlp.face.web.exception.ext.PhoneDeviceException;
import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;

/**
 *PhoneDevice服务接口
* @ClassName: PhoneDeviceService
* @Description: (PhoneDevice服务接口)
* @author wxn
* @date 2015年1月14日 上午10:52:28
 */
public interface PhoneDeviceService {
	/**
	 * 根据id查找PhoneDevice
	* @Title: getPhoneDevice
	* @Description: (这里用一句话描述这个方法的作用)
	* @param id
	* @return
	* @return PhoneDevice    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月6日 下午2:17:31
	 */
	public PhoneDevice getPhoneDevice(Long id);
	/**
	 * 根据userId获取userId下所有的PhoneDevice
	* @Title: findPhoneDevice
	* @Description: (根据userId获取userId下所有的PhoneDevice)
	* @param userId 用户ID
	* @return
	* @return List<PhoneDevice>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月6日 下午2:17:59
	 */
	public List<PhoneDevice> findPhoneDevice(Long userId,Integer type);
	/**
	 * 新增PhoneDevice
	* @Title: addUser
	* @Description: (这里用一句话描述这个方法的作用)
	* @param PhoneDevice
	* @return
	* @return Long    返回类型
	* @throws UserPushException
	* @author wxn  
	* @date 2015年1月6日 下午2:18:47
	 */
	public Long addPhoneDevice(PhoneDevice phoneDevice) throws PhoneDeviceException;
	/**
	 * 编辑PhoneDevice
	* @Title: editUserPhoneDevice
	* @Description: (编辑PhoneDevice)
	* @param PhoneDevice
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月6日 下午4:48:16
	 */
	public void editPhoneDevice(PhoneDevice phoneDevice);
	
	/**
	 * 删除pushUserId的数据
	* @Title: deleteByPushId
	* @Description: (删除pushUserId的数据)
	* @param pushUserId 
	* @return
	* @return int    返回类型
	* @throws UserPushException
	* @author wxn  
	* @date 2015年1月7日 下午5:09:30
	 */
	public int deleteByPushId(PhoneDevice phoneDevice) throws PhoneDeviceException;
	/**
	 * 根据userId和pushUserId查找PhoneDevice
	* @Title: selectUserPushByUserIdAndPushId
	* @Description: (根据userId和pushUserId查找PhoneDevice)
	* @param userId
	* @param pushUserId
	* @return
	* @return PhoneDevice    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月14日 上午10:49:38
	 */
	public PhoneDevice selectPhoneDeviceByUserIdAndPushId(Long userId,String pushUserId);


}
