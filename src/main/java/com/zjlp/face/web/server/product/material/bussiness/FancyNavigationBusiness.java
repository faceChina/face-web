package com.zjlp.face.web.server.product.material.bussiness;

import java.util.List;

import com.zjlp.face.web.exception.ext.FancyNavigationException;
import com.zjlp.face.web.server.product.material.domain.dto.FancyNavigationDto;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午5:31:06
 *
 */
public interface FancyNavigationBusiness {

	/**
	 * @Title: findAllNavigation
	 * @Description: (查找所有系统导航以及细项)
	 * @return
	 * @throws FancyNavigationException
	 * @return List<FancyNavigationDto> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:40:49
	 */
	List<FancyNavigationDto> findAllNavigation() throws FancyNavigationException;

}
