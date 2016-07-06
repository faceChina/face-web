package com.zjlp.face.web.server.user.weixin.service;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.weixin.domain.CustomMenu;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;

/**
 * 自定义菜单基础服务层
 * @ClassName: CustomMenuService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年5月6日 下午1:48:54
 */
public interface CustomMenuService {

	/**
	 * 查询自定义菜单列表
	 * @Title: findCustomMenuVoList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenuDto
	 * @return
	 * @date 2015年5月6日 下午1:52:58  
	 * @author ah
	 */
	List<CustomMenuVo> findCustomMenuVoList(CustomMenuDto customMenuDto);
	
	/**
	 * 新增自定义菜单
	 * @Title: addCustomMenu 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenu
	 * @date 2015年5月7日 下午5:08:30  
	 * @author ah
	 */
	void addCustomMenu(CustomMenu customMenu);
	
	/**
	 * 编辑自定义菜单
	 * @Title: editCustomMenu 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenu
	 * @date 2015年5月7日 下午5:09:33  
	 * @author ah
	 */
	void editCustomMenu(CustomMenu customMenu);

	/**
	 * 新增或修改自定义菜单
	 * @Title: addOrEditCustomMenu 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenuVos
	 * @param shop
	 * @date 2015年5月7日 下午5:12:48  
	 * @author ah
	 */
	void addOrEditCustomMenu(List<CustomMenuVo> customMenuVos, Shop shop);
	
	/**
	 * 根据店铺编号删除自定义菜单
	 * @Title: deleteCustomByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @date 2015年5月8日 上午11:41:11  
	 * @author ah
	 */
	void deleteCustomByShopNo(String shopNo);

}
