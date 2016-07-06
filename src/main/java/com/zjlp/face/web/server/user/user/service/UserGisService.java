package com.zjlp.face.web.server.user.user.service;

import java.util.List;

import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

/**
 * 用户地理信息服务类
* @ClassName: UserGisService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年4月22日 下午3:45:12
*
 */
public interface UserGisService {
	/**
	 * 新增用户地理信息
	* @Title: add
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userGisVo
	* @return Long    返回类型
	* @author wxn  
	* @date 2015年4月22日 下午3:45:35
	 */
	Long add(UserGisVo userGisVo);
	/**
	 * 更新用户地理信息
	* @Title: edit
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userGis
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年4月22日 下午3:45:51
	 */
	void edit(UserGis userGis);
	/**
	 * 删除用户地理信息
	* @Title: deleteById
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年4月22日 下午3:46:06
	 */
	void deleteById(Long id);
	/**
	 * 获取用户地理信息
	* @Title: getUserGisByUserId
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @return
	* @return UserGis    返回类型
	* @throws
	* @author wxn  
	* @date 2015年4月22日 下午3:46:14
	 */
	UserGis getUserGisByUserId(Long userId);
	/**
	 * 操作用户位置附近的用户信息
	* @Title: findUserInNear
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userGisVo
	* @return
	* @return List<UserGisDto>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年4月22日 下午3:46:24
	 */
	List<UserGisDto> findUserInNear(UserGisVo userGisVo);
	/**
	 * 更新用户位置信息状态
	* @Title: updateStatus
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @param status 0 关闭 1启用
	* @return boolean    返回类型
	* @author wxn  
	* @date 2015年4月22日 下午3:46:57
	 */
	boolean updateStatus(Long id,Integer status);

	/**
	 * 查找附近50米内的用户
	 * @Title findLeiDaUser
	 * @param userGisVo
	 * @return List<UserGisDto>
	 * @author jushuang
	 * @date 2015.10.7 下午 3:09
	 */
	List<UserGisDto> findLeiDaUser(UserGisVo userGisVo);
	
	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 * @author jushuang
	 * @date 2015.10.8 下午 3:09
	 */
	boolean updateLeiDaStatusRadarEnable(UserGis userGis);
	
	UserGis getUserGisById(Long id);
}
