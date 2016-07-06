package com.zjlp.face.web.ctl;

import java.util.Date;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.operation.popularize.business.PopularizeBusiness;
import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;
import com.zjlp.face.web.server.user.shop.domain.Shop;

@Controller
@RequestMapping("u/publicity/")
public class PopularizeCtl extends BaseCtl {
	
	@Autowired
	private PopularizeBusiness popularizeBusiness;

	/*
	 * 获得该商铺的店铺推广设置
	 */
	private ShopPopularizeSetting getShopPopularize() {
		String shopNo = super.getShopNo();
		AssertUtil.hasLength(shopNo, "无此商店");
		return this.popularizeBusiness.findShopPopularizeByShopNo(shopNo);
	}
	
	/*
	 * 获得该商铺的分销推广设置
	 */
	private ShopPopularizeSetting getDistributionPopularize() {
		String shopNo = super.getShopNo();
		AssertUtil.hasLength(shopNo, "无此商店");
		return this.popularizeBusiness.getDistributionPopularizeByShopNo(shopNo);
	}
	
	/**
	 * 
	 * @Title: shopPopularizeSettingIndex 
	 * @Description: 跳转店铺推广设置页面
	 * @param model
	 * @return
	 * @date 2015年4月29日 下午5:07:55  
	 * @author cbc
	 */
	@RequestMapping("shop/setting")
	public String shopPopularizeSettingIndex(Model model) {
		ShopPopularizeSetting shopPopularizeSetting = this.getShopPopularize();
		if (null == shopPopularizeSetting) {
			model.addAttribute("shopPopularizeSetting", null);
		} else {
			model.addAttribute("shopPopularizeSetting", shopPopularizeSetting);
		}
		return "/m/operation/popularize/shop-spread";
	}
	
	/**
	 * 
	 * @Title: saveCommissionRate 
	 * @Description: 设置推广的佣金设置
	 * @param model
	 * @param commissionRate
	 * @return
	 * @date 2015年4月29日 下午8:21:08  
	 * @author cbc
	 */
	@RequestMapping(value="{type}/commissionRate/save", method=RequestMethod.POST)
	@ResponseBody
	public String saveCommissionRate(Model model, @PathVariable("type")String type,  Integer commissionRate) {
		ShopPopularizeSetting shopPopularizeSetting = null;
		if ("shop".equals(type)) {
			shopPopularizeSetting = this.getShopPopularize();
		}
		if ("distribution".equals(type)) {
			shopPopularizeSetting = this.getDistributionPopularize();
		}
		if (null == shopPopularizeSetting) {
			//说明之前没设置过推广
			shopPopularizeSetting = new ShopPopularizeSetting();
			shopPopularizeSetting.setCommissionRate(commissionRate);
			Date date = new Date();
			shopPopularizeSetting.setCreateTime(date);
			shopPopularizeSetting.setUpdateTime(date);
			shopPopularizeSetting.setShopNo(this.getShopNo());
			shopPopularizeSetting.setStatus(ShopPopularizeSetting.STATUS_OFF);
			if ("shop".equals(type)) {
				shopPopularizeSetting.setType(ShopPopularizeSetting.TYPE_SHOP);
			}
			if ("distribution".equals(type)) {
				shopPopularizeSetting.setType(ShopPopularizeSetting.TYPE_DISTRIBUTION);
			}
			
			//新增一条推广设置
			boolean boo = this.popularizeBusiness.savePopularizeSetting(shopPopularizeSetting);
			if (boo) {
				return super.getReqJson(true, JSONObject.fromObject(shopPopularizeSetting).toString());
			} else {
				return super.getReqJson(false, "保存失败");
			}
		} else {
			//说明之前设置过推广
			shopPopularizeSetting.setCommissionRate(commissionRate);
			Date date = new Date();
			shopPopularizeSetting.setUpdateTime(date);
			
			//更新之前的推广设置
			boolean boo = this.popularizeBusiness.updatePopularizeSetting(shopPopularizeSetting);
			if (boo) {
				return super.getReqJson(true, JSONObject.fromObject(shopPopularizeSetting).toString());
			} else {
				return super.getReqJson(false, "更新失败");
			}
		}
	}
	
	/**
	 * 
	 * @Title: savePopularzeStatus 
	 * @Description: 设置推广开关 
	 * @param model
	 * @param status
	 * @return
	 * @date 2015年4月30日 上午9:49:20  
	 * @author cbc
	 */
	@RequestMapping(value="{type}/status/save", method=RequestMethod.POST)
	@ResponseBody
	public String savePopularzeStatus(HttpSession session, Model model, Integer status, @PathVariable("type")String type) {
		ShopPopularizeSetting shopPopularizeSetting = null;
		if ("shop".equals(type)) {
			shopPopularizeSetting = this.getShopPopularize();
		} 
		if ("distribution".equals(type)) {
			shopPopularizeSetting = this.getDistributionPopularize();
		}
		if (null == shopPopularizeSetting) {
			//说明之前没设置过店铺推广，默认设置佣金比例为1%
			shopPopularizeSetting = new ShopPopularizeSetting();
			shopPopularizeSetting.setCommissionRate(1);
			Date date = new Date();
			shopPopularizeSetting.setCreateTime(date);
			shopPopularizeSetting.setUpdateTime(date);
			shopPopularizeSetting.setShopNo(this.getShopNo());
			shopPopularizeSetting.setStatus(status);
			if ("shop".equals(type)) {
				shopPopularizeSetting.setType(ShopPopularizeSetting.TYPE_SHOP);
			} 
			if ("distribution".equals(type)) {
				shopPopularizeSetting.setType(ShopPopularizeSetting.TYPE_DISTRIBUTION);
			}
			
			//新增一条推广设置
			boolean boo = this.popularizeBusiness.savePopularizeSetting(shopPopularizeSetting);
			if (boo) {
				this.initPopularizeSettingSession(session, status, type);
				return super.getReqJson(true, JSONObject.fromObject(shopPopularizeSetting).toString());
			} else {
				return super.getReqJson(false, "保存失败");
			}
		} else {
			//说明之前设置过推广
			shopPopularizeSetting.setStatus(status);
			Date date = new Date();
			shopPopularizeSetting.setUpdateTime(date);
			
			//更新之前的推广设置
			boolean boo = this.popularizeBusiness.updatePopularizeSetting(shopPopularizeSetting);
			if (boo) {
				this.initPopularizeSettingSession(session, status, type);
				return super.getReqJson(true, JSONObject.fromObject(shopPopularizeSetting).toString());
			} else {
				return super.getReqJson(false, "更新失败");
			}
		}
	}
	
	/*
	 * 更改session中的推广设置
	 */
	private void initPopularizeSettingSession(HttpSession session, Integer status, String type) {
		Shop sessionShop = (Shop) session.getAttribute("shop");
		if (status.intValue() == 1) {
			if ("shop".equals(type)) {
				sessionShop.setIsShopPopularizeOpenSession(true);
			} 
			session.setAttribute("shop", sessionShop);
		} 
		if (status.intValue() == 2) {
			if ("shop".equals(type)) {
				sessionShop.setIsShopPopularizeOpenSession(false);
			} 
			session.setAttribute("shop", sessionShop);
		}
	}
	
	/**
	 * 
	 * @Title: distributionPopularizeSettingIndex 
	 * @Description: 分销推广设置
	 * @param model
	 * @return
	 * @date 2015年4月30日 上午11:12:17  
	 * @author cbc
	 */
	@RequestMapping("distribution/setting")
	public String distributionPopularizeSettingIndex(Model model) {
		/*Integer isFree = super.getShop().getIsFree();
		if (1 == isFree) {
			//说明是供货商，抛出错误
			throw new PopularizeException("供货商无法设置分销商推广");
		}*/
		
		ShopPopularizeSetting distributionPopularize = this.getDistributionPopularize();
		model.addAttribute("distributionPopularize", distributionPopularize);
		return "/m/operation/popularize/dist-set";
	}
}
