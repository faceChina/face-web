package com.zjlp.face.web.ctl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;


@Controller
@RequestMapping("/u/menu/")
public class MenuCtl extends BaseCtl{

	@Autowired
	private ShopBusiness shopBusiness;
	
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	
	@RequestMapping(value="init")
	public String menuInit(HttpServletRequest request, Model model){
		Shop shop = (Shop)request.getSession().getAttribute("shopInfo");
		if(null == shop){
			List<Shop> list = shopBusiness.findShopListByUserId(super.getUserId());
			model.addAttribute("isSeller", (null == list || list.isEmpty()) ? false : true);
			return "/m/menu/left-user"; 
		}
		List<Shop> shopList = shopBusiness.findShopListByUserId(super.getUserId());
		List<ShopVo> voList = ShopVo.getVoList(shopList);
		Integer orderCount = this.salesOrderBusiness.findOrderCountByShopNo(shop.getNo());
		model.addAttribute("orderCount", orderCount);
		model.addAttribute("shopList", voList);
		model.addAttribute("shopName", shop.getName());
		if(Constants.SHOP_GW_TYPE.equals(shop.getType())){
			return "/m/menu/left-gw"; 
		}
		if(Constants.SHOP_SC_TYPE.equals(shop.getType())){
			if (shop.getIsFree() == 2) {
				return "/m/menu/left-dl";
			}
			return "/m/menu/left-sc"; 
		}
		return "";
	}
}
