package com.zjlp.face.web.server.user.user.dao;

import java.util.List;

import com.zjlp.face.web.server.user.user.domain.VArea;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

/**
 * 地区dao
 * 
 * @ClassName: VaearDao
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月13日 下午5:38:04
 */
public interface VaearDao {

	/**
	 * 根据地区编码获取地区信息
	 * 
	 * @Title: getAreaByAreaCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param code
	 *            地区编码
	 * @return
	 * @date 2015年3月13日 下午5:38:15
	 * @author lys
	 */
	VaearDto getAreaByAreaCode(Integer code);

	/**
	 * 查询所有地区编码信息
	 * @Title: selectAll 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年3月28日 上午11:45:29  
	 * @author lys
	 * @param parentId 
	 */
	List<VArea> findAllByParentId(String parentId);
	
	/**
	 * 根据区域名称查询地区编码信息
	 * @param areaName
	 * @return
	 */
	VArea getAreaByAreaName(String areaName);

	/**
	 * 根据主键查询城市
	 * @Title: getCityByAreaCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param areaCode 编码
	 * @return
	 * @date 2015年9月17日 上午10:31:15  
	 * @author lys
	 */
	VaearDto getCityByAreaCode(Integer areaCode);
}
