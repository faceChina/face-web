package com.zjlp.face.web.ctl.wap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;

@Controller
@RequestMapping("/wap/{shopNo}/any/shop/")
public class ShopCtlWap extends WapCtl {
	
	@Autowired
	private ShopBusiness shopBusiness;
	
	@RequestMapping(value="about",method = RequestMethod.GET)
	public String about(@PathVariable String shopNo ,Model model){
		Shop shop = shopBusiness.getShopByNo(shopNo);
		model.addAttribute("shop", shop);
		model.addAttribute("Success",false);
		return "/wap/user/shop/about";
	}
	

}
