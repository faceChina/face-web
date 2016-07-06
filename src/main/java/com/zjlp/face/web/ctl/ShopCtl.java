package com.zjlp.face.web.ctl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;

@Controller
@RequestMapping("/u/shop/")
public class ShopCtl extends BaseCtl {
	
	@Autowired
	private ShopBusiness shopBusiness;
	
	@RequestMapping(value="about",method = RequestMethod.GET)
	public String about(Model model){
		Shop shop = shopBusiness.getShopByNo(super.getShopNo());
		_shopContentIsNull(shop);
		model.addAttribute("shop", shop);
		model.addAttribute("Success",-1);
		return "/m/user/shop/about";
	}
	
	@RequestMapping(value="add",method = RequestMethod.POST)
	public String add(Shop shop ,Model model){
		shop.setNo(super.getShopNo());
		_shopContentIsNull(shop);
		shopBusiness.editShop(shop);
		model.addAttribute("shop", shop);
		String addedShopContent = shopBusiness.getShopByNo(shop.getNo()).getShopContent();
		if(shop.getShopContent().equals(addedShopContent)){
			model.addAttribute("Success",1);
		}else{
			model.addAttribute("Success",0);
		}
		
		return "/m/user/shop/about";
	}
	
	public void _shopContentIsNull(Shop shop){
		if(null==shop.getShopContent()){
			shop.setShopContent("<span style=\"font-size: 24px; color: rgb(216, 216, 216);\">"
					+ "<p>店铺正在完善中...</p>"
					+ "<p>如有不便，尽请谅解！</p></span>");
		}
	}
	
	@RequestMapping(value="editName",method=RequestMethod.POST)
	@ResponseBody
	public String editName(String no,String name){
		Shop edit=new Shop();
		edit.setNo(no);
		edit.setName(name);
		edit.setUpdateTime(new Date());
		shopBusiness.editShop(edit);
		return getReqJson(true, "");
	}
	
	/**
	 * 
	 * @Title: signPic 
	 * @Description: 店招页面初始化
	 * @param model
	 * @return
	 * @date 2015年9月15日 上午9:33:58  
	 * @author cbc
	 */
	@RequestMapping(value="signPic", method=RequestMethod.GET)
	public String signPic(Model model) {
		Long userId = super.getUserId();
		if (userId == null) {
			return super.getRedirectUrl("/login");
		}
		Shop shop = shopBusiness.getShopByUserId(userId);
		model.addAttribute("shop", shop);
		List<String> src = new ArrayList<String>();
		for (int i = 1; i <= 6; i++) {
			src.add("/resource/base/img/dianzhao/"+i+".jpg");
		}
		model.addAttribute("srcList", src);
		if (shop.getIsDefaultSignPic() == Shop.SIGN_PIC_DEFAULT && shop.getSignPic() != null) {
			String signPic = shop.getSignPic();
			int begin = signPic.lastIndexOf("/")+1;
			int end = signPic.lastIndexOf(".");
			String num = signPic.substring(begin, end);
			model.addAttribute("num", num);
		}
		model.addAttribute("shop", shop);
		return "/m/user/shop/dianpu";
	}
	
	/**
	 * 
	 * @Title: saveSignPic 
	 * @Description: 保存店招图片
	 * @param model
	 * @param signPic
	 * @param isDefaultSignPic
	 * @return
	 * @date 2015年9月15日 上午10:13:39  
	 * @author cbc
	 */
	@RequestMapping(value="signPic", method=RequestMethod.POST)
	@ResponseBody
	public String saveSignPic(Model model, String signPic, Integer isDefaultSignPic) {
		try {
			Long userId = super.getUserId();
			Shop oldShop = shopBusiness.getShopByUserId(userId);
			if (StringUtils.isNotBlank(signPic) && isDefaultSignPic != null) {
				Shop shop = new Shop();
				shop.setUserId(userId);
				shop.setNo(oldShop.getNo());
				shop.setSignPic(signPic);
				shop.setIsDefaultSignPic(isDefaultSignPic);
				shopBusiness.editShop(shop);
			}
			return super.getReqJson(true, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			return super.getReqJson(false, "保存失败");
		}
	}
	
}
