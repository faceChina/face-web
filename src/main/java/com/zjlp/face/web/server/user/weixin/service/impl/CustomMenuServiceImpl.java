package com.zjlp.face.web.server.user.weixin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.WechatException;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.weixin.dao.CustomMenuDao;
import com.zjlp.face.web.server.user.weixin.domain.CustomMenu;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;
import com.zjlp.face.web.server.user.weixin.service.CustomMenuService;

@Service
public class CustomMenuServiceImpl implements CustomMenuService {
	
	@Autowired
	private CustomMenuDao customMenuDao;

	@Override
	public List<CustomMenuVo> findCustomMenuVoList(CustomMenuDto customMenuDto) {
		try {
			// 查询自定义菜单列表
			List<CustomMenuVo> customMenuVos = customMenuDao.findCustomMenuVos(customMenuDto);
			List<CustomMenuVo> customMenus = new ArrayList<CustomMenuVo>();
			for (CustomMenuVo customMenuVo : customMenuVos) {
				if(null == customMenuVo.getPid()) {
					List<CustomMenuVo> subCustomMenus = new ArrayList<CustomMenuVo>();
					for (CustomMenuVo subCustomMenu : customMenuVos) {
						if(null != subCustomMenu.getPid() &&
						   subCustomMenu.getPid().equals(customMenuVo.getId())) {
							subCustomMenus.add(subCustomMenu);
						}
					}
					customMenuVo.setSubCustomMenus(subCustomMenus);
					customMenus.add(customMenuVo);
				}
			}
			return customMenus;
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

	@Override
	public void addCustomMenu(CustomMenu customMenu) {
		customMenuDao.add(customMenu);
		
	}

	@Override
	public void editCustomMenu(CustomMenu customMenu) {
		customMenuDao.edit(customMenu);
	}

	@Override
	public void addOrEditCustomMenu(List<CustomMenuVo> customMenuVos, Shop shop) throws WechatException{
		
		try {
			AssertUtil.notNull(shop.getNo(), "参数店铺编号为空");
			// 删除店铺当前菜单
			this.deleteCustomByShopNo(shop.getNo());
			for(CustomMenuVo customMenuVo : customMenuVos) {
				CustomMenu customMenu = _getCustomMenu(customMenuVo, shop.getNo());
				this.addCustomMenu(customMenu);
				// 新增二级菜单
				if(null != customMenuVo.getSubCustomMenus() &&
						customMenuVo.getSubCustomMenus().size() >0) {
					for (CustomMenuVo subCustomMenu : customMenuVo.getSubCustomMenus()) {
						subCustomMenu.setPid(customMenu.getId());
						CustomMenu subMenu = _getCustomMenu(subCustomMenu, shop.getNo());
						// 新增二级菜单
						this.addCustomMenu(subMenu);
					}
				}
			
			}
			
		} catch (Exception e) {
			throw new WechatException(e);
		}
		
	}

	private CustomMenu _getCustomMenu(CustomMenuVo customMenuVo, String shopNo) {
		Date date = new Date();
		CustomMenu customMenu = new CustomMenu();
		customMenu.setName(customMenuVo.getName());
		customMenu.setCode(customMenuVo.getCode());
		customMenu.setShopNo(shopNo);
		customMenu.setLink(customMenuVo.getLink());
		customMenu.setPid(customMenuVo.getPid());
		customMenu.setUpdateTime(date);
		customMenu.setCreateTime(date);
//		if(null != customMenuVo.getId()) {
//			customMenu.setId(customMenuVo.getId());
//		} else {
//			customMenu.setCreateTime(date);
//		}
		return customMenu;
	}

	@Override
	public void deleteCustomByShopNo(String shopNo) {
		customMenuDao.deleteCustomByShopNo(shopNo);
		
	}
}
