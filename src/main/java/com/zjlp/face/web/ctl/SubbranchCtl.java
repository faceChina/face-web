package com.zjlp.face.web.ctl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;

@Controller
@RequestMapping(value="/u/subbranch/")
public class SubbranchCtl extends BaseCtl {

	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	
	/**
	 * 
	 * @Title: subbranchList 
	 * @Description: 分店列表页
	 * @param model
	 * @param pagination
	 * @param subbranchVo
	 * @param delivery
	 * @return
	 * @date 2015年8月17日 上午11:29:05  
	 * @author cbc
	 */
	@RequestMapping(value="list")
	public String subbranchList(Model model, Pagination<SubbranchVo> pagination, SubbranchVo subbranchVo, Integer delivery) {
		Long userId = super.getUserId();
		List<Shop> shop = shopBusiness.findShopListByUserId(userId);
		subbranchVo.setSupplierShopNo(shop.get(0).getNo());
		subbranchVo.setSuperiorUserId(userId);
		if (null != delivery && -1 == delivery) {
			subbranchVo.setDeliver(null);
		} else {
			subbranchVo.setDeliver(delivery);
		}
		pagination = subbranchBusiness.findSubbranchWithNamePhoneDelievery(pagination, subbranchVo);
		model.addAttribute("pagination", pagination);
		model.addAttribute("userName", subbranchVo.getUserName());
		model.addAttribute("userBindingCell", subbranchVo.getUserBindingCell());
		model.addAttribute("delivery", delivery);
		return "/m/operation/subbranch/subbranch";
	}
	
	/**
	 * 分店详情页
	 * @Title: subbranchDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @param id
	 * @return
	 * @date 2015年8月17日 上午11:29:36  
	 * @author cbc
	 */
	@RequestMapping(value="detail")
	public String subbranchDetail(Model model, Long id) {
		Subbranch subbranch = subbranchBusiness.findSubbranchById(id);
		Shop supplierShop = null;
		String subbranchName = null;
		if (null != subbranch) {
			supplierShop = shopBusiness.getShopByNo(subbranch.getSupplierShopNo());
			subbranchName = supplierShop.getName() + "-" +subbranch.getShopName();
		}
		model.addAttribute("subbranchName", subbranchName);
		model.addAttribute("subbranch", subbranch);
		model.addAttribute("id", id);
		return "/m/operation/subbranch/subbranch-amend";
	}
	
	/**
	 * 分店解绑按钮
	 * @Title: unBinding 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @param id
	 * @return
	 * @date 2015年8月17日 上午11:29:48  
	 * @author cbc
	 */
	@RequestMapping("unbinding")
	public String unBinding(Model model, Long id) {
		Long userId = super.getUserId();
		subbranchBusiness.unBindSubbranch(userId, id);
		return super.getRedirectUrl("list");
	}
	
	/**
	 * 分店设置发货权限和佣金比例
	 * @Title: setSubbranch 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @param id
	 * @param profit
	 * @param deliver
	 * @return
	 * @date 2015年8月17日 上午11:30:01  
	 * @author cbc
	 */
	@RequestMapping(value="set", method=RequestMethod.POST)
	public String setSubbranch(Model model, Long id, Integer profit, Integer deliver) {
		subbranchBusiness.setSubbranchProfit(id, profit);
		subbranchBusiness.authorizedDeliver(id, deliver);
		return super.getRedirectUrl("list");
	}
	
}
