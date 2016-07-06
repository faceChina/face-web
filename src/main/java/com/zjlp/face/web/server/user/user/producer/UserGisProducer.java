package com.zjlp.face.web.server.user.user.producer;

import java.util.List;

import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

/**
 * 用户地理信息类
* @ClassName: UserGisProducer
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年4月20日 下午2:30:57
*
 */
public interface UserGisProducer {
	
	/**
	 * 新增用户地理信息
	* @Title: add
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userGis
	* @return Long    返回类型
	* @author wxn  
	* @date 2015年4月20日 下午2:31:16
	 */
	Long add(UserGisVo userGisVo)throws UserException;
	/**
	 * 更新用户地理信息
	* @Title: edit
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userGis
	* @return boolean    返回类型
	* @author wxn  
	* @date 2015年4月20日 下午2:31:32
	 */
	boolean edit(UserGis userGis);
	/**
	 * 删除用户地理信息
	* @Title: deleteByUserId
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @return boolean    返回类型
	* @author wxn  
	* @date 2015年4月20日 下午2:32:25
	 */
	boolean deleteByUserId(Long id);
	/**
	 * 根据用户ID获取用户地理信息
	* @Title: getUserGisByUserId
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @return
	* @return UserGis    返回类型
	* @throws
	* @author wxn  
	* @date 2015年4月20日 下午2:32:38
	 */
	UserGis getUserGisByUserId(Long userId);
	/**
	 * 查询用户地理信息
	* @Title: findSalesOrderPage
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userGisVo
	* @param pagination
	* @return Pagination<UserGis>    返回类型
	* @author wxn  
	* @date 2015年4月20日 下午2:33:13
	 */
	List<UserGisDto> findUserInNear(UserGisVo userGisVo);
	/**
	 * 更新用户地理信息状态
	* @Title: updateStatus
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @param status
	* @return boolean    返回类型
	* @author wxn  
	* @date 2015年4月20日 下午2:52:33
	 */
	boolean updateStatus(Long id,Integer status);
	
	/**
	 * 声波查询用户列表
	 * @param userGisVo 查询信息
	 * @return
	 */
	List<UserGisDto> findUserByLeida(UserGisVo userGisVo);
	
	/**
	 * 关闭声波收索
	 * @param userGisVo
	 * @return
	 */
	boolean closeLeida(UserGisVo userGisVo);
	
	UserGis getUserGisById(Long id);
	
	//boolean updateLeiDaStatusRadarEnables(Long id,Integer status);

}
