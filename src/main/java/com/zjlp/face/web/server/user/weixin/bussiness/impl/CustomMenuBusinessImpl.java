package com.zjlp.face.web.server.user.weixin.bussiness.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.CustomMenuModular;
import com.zjlp.face.web.exception.ext.WechatException;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.weixin.bussiness.CustomMenuBusiness;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.Button;
import com.zjlp.face.web.server.user.weixin.domain.vo.CommonView;
import com.zjlp.face.web.server.user.weixin.domain.vo.ComplexButton;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;
import com.zjlp.face.web.server.user.weixin.domain.vo.Menu;
import com.zjlp.face.web.server.user.weixin.service.CustomMenuService;

@Service
public class CustomMenuBusinessImpl implements CustomMenuBusiness {

	@Autowired
	private CustomMenuService customMenuService;
	@Autowired(required=false)
	private ShopExternalService shopExternalService;
	
	@Override
	public List<CustomMenuVo> findCustomMenuVoList(CustomMenuDto customMenuDto) throws WechatException{
		
		try {
			AssertUtil.notNull(customMenuDto.getShopNo(), "参数店铺编号为空");
			// 查询自定义菜单列表
			List<CustomMenuVo> customMenuVos = customMenuService.findCustomMenuVoList(customMenuDto);
			return customMenuVos;
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void generateCustomMenu(CustomMenuDto customMenuDto, Shop shop) throws WechatException{

		try {
			AssertUtil.notNull(customMenuDto.getCustomMenuJson(), "参数自定义菜单为空");
			AssertUtil.notNull(shop.getNo(), "参数店铺编号为空");
			AssertUtil.notNull(shop.getAppId(), "参数店铺appId为空");
			AssertUtil.notNull(shop.getAppSecret(), "参数店铺appSecret为空");
			// 获取accessToken
			String accessToken = shopExternalService.getAccessToken(shop.getAppId(), shop.getAppSecret());
			AssertUtil.notNull(accessToken, "获取店铺公众号的accessToken失败");
			// 组装自定义菜单
			List<CustomMenuVo> customMenuVos = _generateCutomMenu(customMenuDto.getCustomMenuJson());
			// 微信自定菜单生成
			shopExternalService.generateMenu(_generateWechatMenu(customMenuVos, shop), accessToken);
			
			// 新增或修改自定义菜单
			customMenuService.addOrEditCustomMenu(customMenuVos, shop);
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

	/**
	 * 生成微信端菜单
	 * @Title: _generateWechatMenu 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenuVos
	 * @return
	 * @date 2015年5月7日 下午8:44:34  
	 * @author ah
	 */
	private String _generateWechatMenu(List<CustomMenuVo> customMenuVos, Shop shop) {
		Menu menu = new Menu();
		List<Button> buttons = new ArrayList<Button>();
		for (CustomMenuVo customMenuVo : customMenuVos) {
			if(null != customMenuVo.getSubCustomMenus() 
					&& customMenuVo.getSubCustomMenus().size() > 0) {
				ComplexButton complexButton = new ComplexButton();
				complexButton.setName(customMenuVo.getName());
				List<Button> sub_button = new ArrayList<Button>();
				for (CustomMenuVo subMenu : customMenuVo.getSubCustomMenus()) {
					CommonView view = new CommonView();  
			        view.setName(subMenu.getName());  
			        view.setType("view");  
			        view.setUrl(_generateUrl(subMenu,shop));
			        sub_button.add(view);
				}
				complexButton.setSub_button(sub_button);
				buttons.add(complexButton);
			} else {
				CommonView view = new CommonView();  
		        view.setName(customMenuVo.getName());  
		        view.setType("view");  
		        view.setUrl(_generateUrl(customMenuVo,shop));
		        buttons.add(view);
			}
		}
		menu.setButton(buttons);
		return JSONObject.fromObject(menu).toString();
	}

	/**
	 * 生成URL
	 * @Title: _generateUrl 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenuVo
	 * @return
	 * @date 2015年5月7日 下午9:03:23  
	 * @author ah
	 */
	private String _generateUrl(CustomMenuVo customMenuVo, Shop shop) {
		if(StringUtils.isNotBlank(customMenuVo.getCode()) && 
				customMenuVo.getCode().indexOf("link_path") != -1) {
			return _coverUrlLink(customMenuVo.getLink());
		} else if(StringUtils.isNotBlank(customMenuVo.getCode()) && 
				customMenuVo.getCode().indexOf("modular") != -1) {
			return _coverPath(CustomMenuModular.getPath(customMenuVo.getCode()), shop.getNo());
		}
		return null;
	}

	/**
     * 路径url转换
     * 
     * @param path
     * @param shopNo
     * @return
     */
    public static String _coverPath(String path, String shopNo){
        if(StringUtils.isBlank(path)){
            return path;
        }
        if(path.startsWith(Constants.URL_ANY)) {
        	return path+Constants.URL_SUFIX;
        }
        if(path.startsWith(Constants.URL_PREFIX) || path.endsWith(Constants.URL_SUFIX)){
            return path.replace(Constants.URL_SHOPNO, shopNo);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.URL_PREFIX);
        sb.append(path.replace(Constants.URL_SHOPNO, shopNo));
        if (path.indexOf(Constants.URL_SUFIX) == -1) {
        	sb.append(Constants.URL_SUFIX);
		}
        String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
        return wgjurl+sb.toString();
    }
    
	/**
     * 外部输入链接处理
     * 
     * @param urlLink
     * @return
     */
    public static String _coverUrlLink(String urlLink){
        if(StringUtils.isBlank(urlLink)){
            return urlLink;
        }
		if (urlLink.contains(Constants.HTTPS) || urlLink.contains(Constants.HTTP)) {
            return urlLink;
        }
        return Constants.HTTP + urlLink;
    }

    
	/**
	 * 组装自定义菜单
	 * @Title: _generateCutomMenu 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param customMenuJson
	 * @return
	 * @date 2015年5月7日 下午2:01:57  
	 * @author ah
	 */
	private List<CustomMenuVo> _generateCutomMenu(String customMenuJson) {
		JSONArray jsonArray = JSONArray.fromObject(customMenuJson);
		List<CustomMenuVo> customMenuVos = new ArrayList<CustomMenuVo>();
		for(int i=0; i<jsonArray.size(); i++) {
			JSONObject customMenu = jsonArray.getJSONObject(i);
			CustomMenuVo customMenuVo = new CustomMenuVo();
			customMenuVo.setId(StringUtils.isBlank(customMenu.getString("id"))?null: customMenu.getLong("id"));
			customMenuVo.setName(customMenu.getString("name"));
			if(StringUtils.isNotBlank(customMenu.getString("subMenu"))) {
				List<CustomMenuVo> subCustomMenu = JsonUtil.toArrayBean(customMenu.getString("subMenu"), CustomMenuVo.class);
				customMenuVo.setSubCustomMenus(subCustomMenu);
			} else {
				customMenuVo.setCode(customMenu.getString("code"));
				if(StringUtils.isNotBlank(customMenu.getString("code")) &&
						customMenu.getString("code").indexOf("link_path") > -1) {
					customMenuVo.setLink(customMenu.getString("link"));
				}
			}
			customMenuVos.add(customMenuVo);
		}
		return customMenuVos;
	}

}
