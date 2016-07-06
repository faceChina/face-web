package com.zjlp.face.web.ctl.wap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.web.server.user.shop.bussiness.LbsBusiness;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;

/**
 * 店铺地理位置前端
 * @ClassName: LbsAppCtl 
 * @Description: (店铺地理位置前端) 
 * @author ah
 * @date 2014年9月29日 下午3:55:24
 */
@Controller
@RequestMapping({ "/wap/{shopNo}/any/lbs/" })
public class LbsWapCtl extends WapCtl {

	@Autowired
	private LbsBusiness lbsBusiness;
	
	@RequestMapping(value = "navigation")
	public String navigation(ShopLocationDto shopLocationDto, Model model){
		//查询店铺地理位置
		shopLocationDto = lbsBusiness.getShopLocationByShopNoForApp(super.getShop().getNo());
		model.addAttribute("shopLocation", shopLocationDto);
		return "/wap/lbs/navigation";
	}
}
