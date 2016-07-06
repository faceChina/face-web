package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.FancyNavigationItem;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午5:19:54
 *
 */
public interface FancyNavigationItemDao {
	/**
	 * @Title: add
	 * @Description: (插入一条导航细项)
	 * @param fancyNavigationItem
	 * @return
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:19:50
	 */
	Long add(FancyNavigationItem fancyNavigationItem);

	/**
	 * @Title: getById
	 * @Description: (通过主键查找导航细项)
	 * @param id
	 * @return
	 * @return FancyNavigationItem 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:20:17
	 */
	FancyNavigationItem getById(Long id);

	/**
	 * @Title: updateById
	 * @Description: (通过主键更新导航细项)
	 * @param fancyNavigationItem
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:20:52
	 */
	void updateById(FancyNavigationItem fancyNavigationItem);

	/**
	 * @Title: deleteById
	 * @Description: (通过主键删除导航细项：软删除)
	 * @param id
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:01:12
	 */
	void deleteById(Long id);

	/**
	 * @Title: selectItemByNavigationId
	 * @Description: (通过外键查出导航下的细项)
	 * @param navigationId
	 * @return
	 * @return List<FancyNavigationItem> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午5:22:37
	 */
	List<FancyNavigationItem> selectItemByNavigationId(Long navigationId);
}
