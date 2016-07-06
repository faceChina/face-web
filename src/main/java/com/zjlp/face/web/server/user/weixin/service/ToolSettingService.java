package com.zjlp.face.web.server.user.weixin.service;

import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;

/**
 * 通讯方式设置基础服务接口
 * @ClassName: ToolSettingService
 * @Description: (通讯方式设置基础服务接口)
 * @author ah
 * @date 2014年7月25日 下午2:18:28
 */
public interface ToolSettingService {

	/**
	 * 新增通信方式设置
	 * @Title: addToolSetting
	 * @Description: (新增通信方式设置)
	 * @param toolSetting
	 * @date 2014年7月25日 下午2:19:16
	 * @author ah
	 */
	void addToolSetting(ToolSetting toolSetting);

	/**
	 * 根据产品编号查询通信方式设置
	 * @Title: getToolSettingByShopNo
	 * @Description: (根据产品编号查询通信方式设置)
	 * @param shopNo
	 * @return
	 * @date 2014年7月31日 下午9:06:13
	 * @author Administrator
	 */
	ToolSetting getToolSettingByShopNo(String shopNo);
	
	
	
}
