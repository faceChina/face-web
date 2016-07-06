package com.zjlp.face.web.constants;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuModularVo;

/**
 * 自定义菜单模块
 * @ClassName: CustomMenuModular 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月30日 上午11:47:00
 */
public enum CustomMenuModular {

	CUSTOM_MENU_WGW_INDEX("官网首页", 1, "modular_index", "/{shopNo}/any/index"),
	CUSTOM_MENU_WGW_WSCINDEX("商城首页", 1, "modular_wscIndex", "/{shopNo}/any/gwscIndex"),
	CUSTOM_MENU_WGW_LBS("地址导航", 1, "modular_lbs", "/{shopNo}/any/lbs/navigation"),
	CUSTOM_MENU_WGW_PINDEX("个人中心", 1, "modular_pindex", "/{shopNo}/buy/personal/index"),
	CUSTOM_MENU_WGW_RECRUIT("招募分店", 1, "modular_recruit", "/{shopNo}/any/subbranch/new"),
//	CUSTOM_MENU_WGW_COMMISSION("佣金领取", 1, "modular_commission", "/{shopNo}/buy/account/index"),
//	CUSTOM_MENU_WGW_ICARD("我的名片", 1, "modular_icard", "/{shopNo}/any/icard/my-card"),
//	CUSTOM_MENU_WGW_FAVORITES("我的收藏", 1, "modular_favorites", "/{shopNo}/any/icard-collect/my-collection"),
	
	CUSTOM_MENU_WSC_INDEX("官网首页", 2, "modular_index", "/{shopNo}/any/index"),
	CUSTOM_MENU_WSC_LBS("地址导航", 2, "modular_lbs", "/{shopNo}/any/lbs/navigation"),
	CUSTOM_MENU_WSC_PINDEX("个人中心", 2, "modular_pindex", "/{shopNo}/buy/personal/index"),
	CUSTOM_MENU_WSC_RECRUIT("招募分店", 2, "modular_recruit", "/{shopNo}/any/subbranch/new"),;
//	CUSTOM_MENU_WSC_COMMISSION("佣金领取", 2, "modular_commission", "/{shopNo}/buy/account/index"),
//	CUSTOM_MENU_WSC_ICARD("我的名片", 2, "modular_icard", "/{shopNo}/any/icard/my-card"),
//	CUSTOM_MENU_WSC_FAVORITES("我的收藏", 2, "modular_favorites", "/{shopNo}/any/icard-collect/my-collection"),;
	
	private String name;
	private Integer type;
	private String code;
	private String url;
	
	private CustomMenuModular(String name, Integer type, String code, String url){
		this.name = name;
		this.type = type;
		this.code = code;
		this.url = url;
	}
	
	public static List<CustomMenuModularVo> wgwModulars;
	
	public static List<CustomMenuModularVo> wscModulars;
	
	public static String wgwCustomMenuModularVoJson;
	
	public static String wscCustomMenuModularVoJson;
	
	/**
	 * 查询官网自定义订单模块列表
	 * @Title: getWgwModulars 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年4月30日 下午2:22:18  
	 * @author ah
	 */
	public static List<CustomMenuModularVo> getWgwModulars() {
		if(null == wgwModulars) {
			wgwModulars = new ArrayList<CustomMenuModularVo>();
			for (CustomMenuModular customMenuModular : CustomMenuModular.values()) {
				if(Constants.SHOP_GW_TYPE.equals(customMenuModular.getType())) {
					CustomMenuModularVo customMenuModularVo = new CustomMenuModularVo();
					customMenuModularVo.setName(customMenuModular.getName());
					customMenuModularVo.setCode(customMenuModular.getCode());
					customMenuModularVo.setUrl(customMenuModular.getUrl());
					wgwModulars.add(customMenuModularVo);
				}
			}
		}
		return wgwModulars;
	}
	
	/**
	 * 获取官网自定义订单模块json
	 * @Title: getWgwCustomMenuModularJson 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年4月30日 下午3:48:13  
	 * @author ah
	 */
	public static String getWgwCustomMenuModularJson() {
		
		if(StringUtils.isBlank(wgwCustomMenuModularVoJson)) {
			wgwCustomMenuModularVoJson = JSONArray.fromObject(getWgwModulars()).toString();
		}
		return wgwCustomMenuModularVoJson;
	}
	
	/**
	 * 查询商城自定义菜单模块列表
	 * @Title: getWscModulars 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年4月30日 下午2:23:48  
	 * @author ah
	 */
	public static List<CustomMenuModularVo> getWscModulars() {
		if(null == wscModulars) {
			wscModulars = new ArrayList<CustomMenuModularVo>();
			for (CustomMenuModular customMenuModular : CustomMenuModular.values()) {
				if(Constants.SHOP_SC_TYPE.equals(customMenuModular.getType())) {
					CustomMenuModularVo customMenuModularVo = new CustomMenuModularVo();
					customMenuModularVo.setName(customMenuModular.getName());
					customMenuModularVo.setCode(customMenuModular.getCode());
					customMenuModularVo.setUrl(customMenuModular.getUrl());
					wscModulars.add(customMenuModularVo);
				}
			}
		}
		return wscModulars;
	}
	public static String getPath(String code){
		for(CustomMenuModular customMenuModular : CustomMenuModular.values()) {
			if(customMenuModular.getCode().equals(code)) {
				return customMenuModular.getUrl();
			}
		}
		return null;
	}
	/**
	 * 获取商城自定义菜单模块json
	 * @Title: getWscCustomMenuModularJson 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年4月30日 下午3:49:02  
	 * @author ah
	 */
	public static String getWscCustomMenuModularJson() {
		
		if(StringUtils.isBlank(wscCustomMenuModularVoJson)) {
			wscCustomMenuModularVoJson = JSONArray.fromObject(getWscModulars()).toString();
		}
		return wscCustomMenuModularVoJson;
	}
	
	public static void main(String[] args) {
		System.out.println(getWgwCustomMenuModularJson());
		System.out.println(getWscCustomMenuModularJson());
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
