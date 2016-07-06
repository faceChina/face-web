package com.zjlp.face.web.server.user.shop.service;

import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

/**
 * 地区基础服务接口
 * @ClassName: VareaService 
 * @Description: (地区基础服务接口) 
 * @author ah
 * @date 2014年8月13日 上午10:35:54
 */
public interface VareaService {

	/**
	 * 根据主键查询地区
	 * @Title: getVareaById 
	 * @Description: (根据主键查询地区) 
	 * @param areaCode
	 * @return
	 * @date 2014年8月13日 上午10:41:08  
	 * @author ah
	 */
	public VaearDto getVareaById(Integer areaCode);

	/**
	 * 根据主键查询城市
	 * @Title: getCityById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param areaCode 城市编码
	 * @return
	 * @date 2015年9月17日 上午10:30:15  
	 * @author lys
	 */
	public VaearDto getCityById(Integer areaCode);
}
