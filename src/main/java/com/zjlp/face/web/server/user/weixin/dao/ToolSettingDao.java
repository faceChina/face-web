package com.zjlp.face.web.server.user.weixin.dao;

import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;

/**
 * 消息回复设置持久层
 * @ClassName: ToolSettingDao
 * @Description: (消息回复设置持久层)
 * @author ah
 * @date 2015年3月23日 下午7:58:11
 */
public interface ToolSettingDao {

	/**
	 * 根据店铺编号查询消息回复设置
	 * @Title: getByShopNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 * @return
	 * @date 2015年3月23日 下午7:59:28
	 * @author ah
	 */
	ToolSetting getByShopNo(String shopNo);

	/**
	 * 编辑消息回复设置
	 * @Title: edit
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param toolSetting
	 * @date 2015年3月23日 下午8:00:07
	 * @author ah
	 */
	void edit(ToolSetting toolSetting);

	void addToolSetting(ToolSetting toolSetting);
}
