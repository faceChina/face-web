package com.zjlp.face.web.server.user.weixin.dao;

import java.util.List;

import com.zjlp.face.web.server.user.weixin.domain.CustomMenu;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;

/**
 * 自定义菜单持久层
 * @ClassName: CustomMenuDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月30日 下午5:06:03
 */
public interface CustomMenuDao {

	/**
	 * 新增自定义菜单
	 * @Title: add 
	 * @Description: (新增自定义菜单) 
	 * @param customMenu
	 * @date 2015年4月30日 下午5:07:35  
	 * @author ah
	 */
	void add(CustomMenu customMenu);
	
	/**
	 * 编辑自定义菜单
	 * @Title: edit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenu
	 * @date 2015年4月30日 下午5:08:19  
	 * @author ah
	 */
	void edit(CustomMenu customMenu);
	
	/**
	 * 根据主键查询自定义菜单
	 * @Title: getCustomMenuById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年4月30日 下午5:13:53  
	 * @author ah
	 */
	CustomMenu getCustomMenuById(Long id);
	
	/**
	 * 查询自定义菜单列表
	 * @Title: findCustomMenuDtos 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenuDto
	 * @return
	 * @date 2015年4月30日 下午5:15:15  
	 * @author ah
	 */
	List<CustomMenuVo> findCustomMenuVos(CustomMenuDto customMenuDto);

	/**
	 * 根据店铺编号删除自定义菜单
	 * @Title: deleteCustomByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @date 2015年5月8日 上午11:42:30  
	 * @author ah
	 */
	void deleteCustomByShopNo(String shopNo);
}
