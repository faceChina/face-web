package com.zjlp.face.web.constants;

/**
 * redis键管理
 * @ClassName: CacheKey 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年2月4日 上午11:04:28
 */
public enum CacheKey {

	/** 左侧菜单订单统计 */
	LEFT_MENU_ORDER_COUNT("LEFT_MENU_ORDER_COUNT_{}"),
	/** 左侧菜单公告个数 */
	LEFT_MENU_ANNOUNCEMENT_COUNT("LEFT_MENU_ANNOUNCEMENT_COUNT_{}"),
	
	/** 图标库缓存 */
	TEMPLATE_IMG("TEMPLATE_IMG"),
	
	/**官网首页热门模版*/
	WGJ_OWTEMPLATE_HP_WGW_HOT("WGJ_OWTEMPLATE_HP_WGW_HOT"),
	/**官网首页其他模版*/
	WGJ_OWTEMPLATE_HP_WGW_OTHER("WGJ_OWTEMPLATE_HP_WGW_OTHER"),
	/**官网商城首页热门模版*/
	WGJ_OWTEMPLATE_HP_WGW_SC_HOT("WGJ_OWTEMPLATE_HP_WGW_SC_HOT"),
	/**官网商城首页其他模版*/
	WGJ_OWTEMPLATE_HP_WGW_SC_OTHER("WGJ_OWTEMPLATE_HP_WGW_SC_OTHER"),
	/**商城首页热门模版*/
	WGJ_OWTEMPLATE_HP_WSC_HOT("WGJ_OWTEMPLATE_HP_WSC_HOT"),
	/**商城首页其他模版*/
	WGJ_OWTEMPLATE_HP_WSC_OTHER("WGJ_OWTEMPLATE_HP_WSC_OTHER"),
	;
	String template;
	private CacheKey(String remplateParam){
		this.template = remplateParam;
	}
	public String getTemplate() {
		return template;
	}
	public String getCacheKey(Object...args){
		String content = this.template;
		if (null == content || args == null || args.length <= 0) {
			return content;
		}
		for (Object obj : args) {
			content = content.replaceFirst("\\{\\}", String.valueOf(obj));
		}
		return content;
	}
	
	public static void main(String[] args) {
		System.out.println(TEMPLATE_IMG.getCacheKey());
	}
	
}
