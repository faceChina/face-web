package com.zjlp.face.web.ctl.app;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.server.product.material.bussiness.FancyNavigationBusiness;
import com.zjlp.face.web.server.product.material.domain.dto.FancyNavigationDto;
import com.zjlp.face.web.util.AssConstantsUtil;

/**
 * 刷脸精选导航
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午7:19:32
 *
 */
@Controller
@RequestMapping("/assistant/ass/fancy/")
public class FancyAppCtl extends BaseCtl {

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private FancyNavigationBusiness fancyNavigationBusiness;
	
	private static final String[] NAVIGATION_FILLTER = { "id", "name", "type", "link", "subItems.id", "subItems.name",
			"subItems.type", "subItems.link", };
	
	/**
	 * @Title: navigationList
	 * @Description: (刷脸精选导航)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月24日 下午8:06:58
	 */
	@RequestMapping(value="navigationList")
	@ResponseBody
	public String navigationList(@RequestBody JSONObject jsonObj) {
		try {
			List<FancyNavigationDto> list = this.fancyNavigationBusiness.findAllNavigation();
			return outSucceed(list, true, NAVIGATION_FILLTER);
		} catch (Exception e) {
			_log.error("根据号码查找店铺失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

}
