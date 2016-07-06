package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.FancyNavigation;
import com.zjlp.face.web.server.product.material.domain.dto.FancyNavigationDto;

/**
* 
* @author Baojiang Yang
* @date 2015年8月24日 下午8:47:45
*
*/ 
public interface FancyNavigationDao {

	/**
	 * @Title: add
	 * @Description: (插入一条导航)
	 * @param fancyNavigation
	 * @return
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:00:17
	 */
	Long add(FancyNavigation fancyNavigation);

	/**
	 * @Title: getById
	 * @Description: (通过主键查找导航)
	 * @param id
	 * @return
	 * @return FancyNavigation 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:00:37
	 */
	FancyNavigation getById(Long id);

	/**
	 * @Title: updateById
	 * @Description: (通过主键更新导航)
	 * @param fancyNavigation
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:00:58
	 */
	void updateById(FancyNavigation fancyNavigation);

	/**
	 * @Title: deleteById
	 * @Description: (通过主键删除导航：软删除)
	 * @param id
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:01:12
	 */
	void deleteById(Long id);

	/**
	 * @Title: findAllNavigation
	 * @Description: (查出所有有效的导航：暂无参数)
	 * @return
	 * @return List<FancyNavigation> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:01:43
	 */
	List<FancyNavigationDto> findAllNavigation();

}
