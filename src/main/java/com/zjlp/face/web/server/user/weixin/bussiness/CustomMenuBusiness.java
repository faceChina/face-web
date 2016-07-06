package com.zjlp.face.web.server.user.weixin.bussiness;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;

/**
 * 菜单业务接口
 * @ClassName: MenuBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月29日 下午8:35:44
 */
public interface CustomMenuBusiness {

	/**
	 * 查询自定义菜单
	 * @Title: findCustomMenuVoList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenuDto
	 * @return
	 * @date 2015年4月29日 下午9:00:48  
	 * @author ah
	 */
	List<CustomMenuVo> findCustomMenuVoList(CustomMenuDto customMenuDto);
	
	/**
	 * 生成自定义菜单
	 * @Title: generateCustomMenu 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenuDto
	 * @param shop
	 * @date 2015年4月30日 上午9:18:24  
	 * @author ah
	 */
	void generateCustomMenu(CustomMenuDto customMenuDto, Shop shop);
}
