package com.zjlp.face.web.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.web.server.user.shop.bussiness.LbsBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;

@Controller
@RequestMapping({ "/u/lbs/"})
public class LbsCtl extends BaseCtl {
	
	@Autowired
	private LbsBusiness lbsBusiness;
	@Autowired
	private ShopBusiness shopBusiness;

	/**
	 * 查询店铺地理位置
	 * @Title: index 
	 * @Description: (查询店铺地理位置) 
	 * @param shopLocation
	 * @param model
	 * @return
	 * @date 2014年9月28日 下午7:40:23  
	 * @author ah
	 */
	@RequestMapping(value = "index")
	public String index(ShopLocation shopLocation, Model model){
		//根据店铺编号查询店铺地理位置
		shopLocation = lbsBusiness.getShopLocationByShopNo(super.getShopNo());
		Shop shop = shopBusiness.getShopByNo(super.getShopNo());
		model.addAttribute("shopLocation", shopLocation);
		model.addAttribute("shop", shop);
		return "/m/lbs/basic-set";
	}
	
	/**
	 * 保存店铺地理位置
	 * @Title: saveShopLocation 
	 * @Description: (保存店铺地理位置) 
	 * @param reurl
	 * @param shopLocation
	 * @param model
	 * @return
	 * @date 2014年9月28日 下午8:26:53  
	 * @author ah
	 */
	@RequestMapping(value="save")
	public String saveShopLocation(ShopLocationDto locationDto, Model model,String cell) {
		Shop shop = super.getShop();
		//保存店铺地理位置
		locationDto.setShopNo(super.getShopNo());
		locationDto.resetCode(ShopLocationDto.Mode.CITY);
		ShopLocation shopLocation = ShopLocation.createBySub(locationDto);
		shopLocation.setShopName(shop.getName());
		lbsBusiness.saveShopLocation(shopLocation);
		
		shop.setCell(cell);
		shopBusiness.editShop(shop);
		
		return super.getRedirectUrl("/u/lbs/index");
	}
}
