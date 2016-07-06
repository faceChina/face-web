package com.zjlp.face.web.server.user.user.service;

import java.util.List;

import com.zjlp.face.web.exception.ext.AddressException;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

/**
 * 收货地址基础服务
 * 
 * @ClassName: AddressService
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月13日 下午5:16:26
 */
public interface AddressService {

	/**
	 * 添加收货地址
	 * 
	 * @Title: addAddress
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param address
	 *            收货地址
	 * @return 插入记录主键
	 * @throws AddressException
	 * @date 2015年3月13日 下午5:18:45
	 * @author lys
	 */
	Long addAddress(Address address) throws AddressException;

	/**
	 * 通过用户id查询用户收货地址
	 * 
	 * <p>
	 * 
	 * 注：依sort字段进行排序
	 * 
	 * @Title: findAddressByUserId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            所属用户
	 * @return
	 * @throws AddressException
	 * @date 2015年3月13日 下午5:20:37
	 * @author lys
	 */
	List<Address> findAddressByUserId(Long userId) throws AddressException;

	/**
	 * 根据主键查询收货地址
	 * 
	 * @Title: getAddressById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws AddressException
	 * @date 2015年3月13日 下午5:23:07
	 * @author lys
	 */
	Address getAddressById(Long id) throws AddressException;

	/**
	 * 根据主键查询正常可用的收货地址
	 * 
	 * @Title: getValidAddressById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws AddressException
	 * @date 2015年3月13日 下午5:23:26
	 * @author lys
	 */
	Address getValidAddressById(Long id) throws AddressException;

	/**
	 * 逻辑删除指定收货地址
	 * 
	 * @Title: removeAddressById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws AddressException
	 * @date 2015年3月13日 下午5:25:53
	 * @author lys
	 */
	boolean removeAddressById(Long id) throws AddressException;

	/**
	 * 查询指定用户的默认收货地址
	 * 
	 * @Title: getDefaultAddress
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @return
	 * @throws AddressException
	 * @date 2015年3月13日 下午5:27:21
	 * @author lys
	 */
	Address getDefaultAddress(Long userId) throws AddressException;
	
	/**
	 * 修改收货地址
	 * @Title: editAddress 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param address 收货地址
	 * @return
	 * @throws AddressException
	 * @date 2015年3月23日 下午4:39:56  
	 * @author lys
	 */
	boolean editAddress(Address address) throws AddressException;

	/**
	 * 设置指定的收货地址为默认地址
	 * <p>
	 * 注：<br>
	 * 1.如果已有默认收货地址，则清除其默认状态<br>
	 * 2.更新指定收货地址为默认地址<br>
	 * 
	 * @Title: setDefaultAddress
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @param userId
	 * @return
	 * @throws AddressException
	 * @date 2015年3月13日 下午5:28:23
	 * @author lys
	 */
	boolean setDefaultAddress(Long id, Long userId) throws AddressException;

	/**
	 * 查询地区信息
	 * 
	 * @Title: getAreaByAreaCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param areaCode
	 *            地区编码
	 * @return
	 * @date 2015年3月13日 下午6:24:27
	 * @author lys
	 */
	VaearDto getAreaByAreaCode(Integer areaCode) throws AddressException;
	
	/**
	 * 查询地址信息
	 * @Title: getVaearTree 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @throws AddressException
	 * @date 2015年3月28日 下午1:06:53  
	 * @author lys
	 */
	VaearTree getVaearTree() throws AddressException;

	/**
	 * 查询省份信息
	 * @Title: getProviceVaear 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @throws AddressException
	 * @date 2015年3月28日 下午3:40:10  
	 * @author lys
	 */
	VaearTree getProviceVaear() throws AddressException;
	
	/**
	 * 获取用户的最大排序
	 * @Title: getMaxSortByUserId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @return
	 * @throws AddressException
	 * @date 2015年3月30日 下午9:13:29  
	 * @author lys
	 */
	Integer getMaxSortByUserId(Long userId) throws AddressException;
	
	/**
	 * 是否为正确的收货地址
	 * @Title: isValidAddress 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param provice
	 * @param city
	 * @param country
	 * @return
	 * @throws AddressException
	 * @date 2015年4月6日 下午5:06:35  
	 * @author lys
	 */
	boolean isValidAddress(String provice, String city, String country) throws AddressException;
}
