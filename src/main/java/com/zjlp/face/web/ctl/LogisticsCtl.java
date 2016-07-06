package com.zjlp.face.web.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.LogisticsException;
import com.zjlp.face.web.server.user.shop.bussiness.LogisticsBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.dto.PickUpStoreDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDistributionDto;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateVo;
import com.zjlp.face.web.server.user.shop.domain.vo.PickUpStoreVo;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

/**
 * 配送方式控制层
 * 
 * @ClassName: LogisticsCtl
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月27日 下午1:32:59
 */
@Controller
@RequestMapping("/u/shop/logistics/")
public class LogisticsCtl extends BaseCtl {

	@Autowired
	private LogisticsBusiness logisticsBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	
	/**
	 * 统一首页
	 * @Title: index 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月27日 下午4:05:37  
	 * @author lys
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(Model model) {
		Shop shop = shopBusiness.getShopByNo(super.getShopNo());
		ShopVo vo = new ShopVo(shop.getLogisticsTypes());
		model.addAttribute("shop", vo);
		return "/m/user/shop/logistics/index";
	}
	
	/**
	 * 修改配送方式
	 * @Title: altlogisticsTp 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年3月30日 下午3:15:28  
	 * @author lys
	 */
	@RequestMapping(value="altlogisticsTp", method=RequestMethod.POST)
	@ResponseBody
	public String altlogisticsTp(Integer[] logisticsTypeArr) {
		try {
			Shop shop = new Shop(super.getShopNo());
			Integer logisticsTypes = ShopVo.getLogisticsTypesByArr(logisticsTypeArr);
			if (logisticsTypes < 1) {
				return super.getReqJson(false, "必须开启一种配送方式！");
			}
			Shop s=shopBusiness.getShopByNo(getShopNo());
			ShopVo oldVo=new ShopVo();
			ShopVo newVo=new ShopVo();
			oldVo.setLogisticsTypes(s.getLogisticsTypes());
			newVo.setLogisticsTypes(logisticsTypes);
			Integer count=0;
			if(oldVo.getPsType()==0&&newVo.getPsType().equals(ShopVo.PS)){
				count=logisticsBusiness.countShopDistributionByShopNo(getShopNo());
				AssertUtil.isTrue(count>0, "请先增加配送范围","请先增加配送范围再来开启");
			}
			if(oldVo.getZtType()==0&&newVo.getZtType().equals(ShopVo.ZT)){
				count=logisticsBusiness.countPickUpStoreByShopNo(getShopNo());
				AssertUtil.isTrue(count>0, "请先增加自提点","请先增加自提点再来开启");
			}
			shop.setLogisticsTypes(logisticsTypes);
			shopBusiness.editShop(shop);
			return super.getReqJson(true, null);
		} catch (Exception e) {
			return super.getReqJson(false, e.getMessage());
		}
	}

	/**
	 * 快递首页
	 * @Title: dispatchList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月27日 下午2:27:11  
	 * @author lys
	 */
	@RequestMapping(value = "dispatchIndex")
	public String dispatchList(Model model) {
		String shopNo = super.getShopNo();
		DeliveryTemplateVo templateVo = logisticsBusiness
				.getDeliveryTemplateVoByShopNo(shopNo);
		if (null != templateVo) {
			model.addAttribute("dto", templateVo);
		}
		return "/m/user/shop/logistics/dispatch-index";
	}
	
	/**
	 * 添加快递模板页
	 * @Title: dispatchAddPage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月27日 下午3:06:45  
	 * @author lys
	 */
	@RequestMapping(value="dispatchAdd", method=RequestMethod.GET)
	public String dispatchAddPage(Model model) {
		VaearTree provice = logisticsBusiness.getProviceVaear();
		model.addAttribute("provice", provice);
		return "/m/user/shop/logistics/dispatch-edit";
	}
	
	/**
	 * 添加快递模板
	 * @Title: dispatchAdd 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param templateDto
	 * @param model
	 * @return
	 * @date 2015年3月27日 下午3:07:58  
	 * @author lys
	 */
	@RequestMapping(value="dispatchAdd", method=RequestMethod.POST)
	public String dispatchAdd(DeliveryTemplateDto templateDto, Model model) {
		String shopNo = super.getShopNo();
		logisticsBusiness.addDispatchTemplate(templateDto, shopNo);
		return super.getRedirectUrl("dispatchIndex");
	}
	
	/**
	 * 编辑快递模板页
	 * @Title: dispatchEdit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月27日 下午3:08:20  
	 * @author lys
	 */
	@RequestMapping(value="dispatchEdit", method=RequestMethod.GET)
	public String dispatchEditPage(Model model) {
		String shopNo = super.getShopNo();
		DeliveryTemplateDto templateDto = logisticsBusiness
				.getDeliveryTemplateByShopNo(shopNo);
		VaearTree provice = logisticsBusiness.getProviceVaear();
		model.addAttribute("dto", templateDto);
		model.addAttribute("provice", provice);
		return "/m/user/shop/logistics/dispatch-edit";
	}
	
	/**
	 * 编辑快递模板
	 * @Title: dispatchEdit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年3月27日 下午3:10:17  
	 * @author lys
	 */
	@RequestMapping(value="dispatchEdit", method=RequestMethod.POST)
	public String dispatchEdit(DeliveryTemplateDto templateDto, Model model) {
		String shopNo = super.getShopNo();
		logisticsBusiness.editDispatchTemplate(templateDto, shopNo);
		return super.getRedirectUrl("dispatchIndex");
	}
	
	/**
	 * 删除快递模板
	 * @Title: dispatchDel 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月27日 下午3:17:44  
	 * @author lys
	 */
	@Deprecated
//	@RequestMapping(value="dispatchDel", method=RequestMethod.GET)
	public String dispatchDel(@RequestParam Long id) {
		String shopNo = super.getShopNo();
		logisticsBusiness.delDispatchTemplate(id, shopNo);
		return super.getRedirectUrl("dispatchIndex");
	}
	
	
	/**
	 * 删除快递子项
	 * @Title: dispatchItemDel 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月30日 上午9:57:20  
	 * @author lys
	 */
	@RequestMapping(value="itemDel", method=RequestMethod.POST)
	@ResponseBody
	public String dispatchItemDel(@RequestParam Long id) {
		try {
			String shopNo = super.getShopNo();
			logisticsBusiness.delDispatchTemplateItem(id, shopNo);
			return super.getReqJson(true, null);
		} catch (LogisticsException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	//-------------------------------店铺配送---------------------------------
	/**
	 * 店铺配送分页查询
	 * @Title: shopDistributionList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param model
	 * @return
	 * @date 2015年3月27日 下午8:34:45  
	 * @author lys
	 */
	@RequestMapping("pslist")
	public String shopDistributionList(Pagination<ShopDistributionDto> pagination, Model model) {
		pagination = logisticsBusiness.findShopDistributionPage(pagination, super.getShopNo());
		model.addAttribute("pagination", pagination);
		return "/m/user/shop/logistics/dispatching-shopship-list";
	}
	
	/**
	 * 新增或修改店铺配送信息
	 * @Title: addOrEditPs 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopDistribution
	 * @return
	 * @date 2015年3月27日 下午8:30:52  
	 * @author lys
	 */
	@RequestMapping(value="editps", method=RequestMethod.POST)
	public String addOrEditPs(ShopDistribution shopDistribution) {
		logisticsBusiness.addOrEditShopDistribution(shopDistribution, super.getShopNo());
		return super.getRedirectUrl("pslist");
	}
	
	/**
	 * 删除店铺配送信息
	 * @Title: delPs 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月27日 下午8:36:39  
	 * @author lys
	 */
	@RequestMapping(value="delps", method=RequestMethod.POST)
	@ResponseBody
	public String delPs(@RequestParam Long id) {
		try {
			logisticsBusiness.delShopDistribution(id, super.getShopNo());
			return super.getReqJson(true, null);
		} catch (LogisticsException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	//-------------------------------自提点设置---------------------------------
	/**
	 * 自提点分页查询
	 * @Title: ztlist 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param model
	 * @return
	 * @date 2015年3月28日 上午9:59:07  
	 * @author lys
	 */
	@RequestMapping(value="ztlist")
	public String ztlist(Pagination<PickUpStoreVo> pagination, Model model) {
		pagination = logisticsBusiness.findPickUpStorePage(pagination, super.getShopNo());
		model.addAttribute("pagination", pagination);
		return "/m/user/shop/logistics/take-list";
	}
	
	/**
	 * 自提点修改页
	 * @Title: addOrEditPage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param model
	 * @return
	 * @date 2015年3月28日 上午10:17:42  
	 * @author lys
	 */
	@RequestMapping(value="ztedit", method=RequestMethod.GET)
	public String addOrEditPage(Long id, Model model) {
		if (null != id) {
			PickUpStore store = logisticsBusiness.getPickUpStoreById(id, super.getShopNo());
			model.addAttribute("store", store);
		}
		return "/m/user/shop/logistics/take-add";
	}
	
	/**
	 * 新增或修改自提点
	 * @Title: addOrEdit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @param model
	 * @return
	 * @date 2015年3月28日 上午10:22:34  
	 * @author lys
	 */
	@RequestMapping(value="ztedit", method=RequestMethod.POST)
	public String addOrEdit(PickUpStoreDto dto, Model model) {
		logisticsBusiness.addOrEditPickUpStore(dto, super.getShopNo());
		return super.getRedirectUrl("ztlist");
	}
	
	/**
	 * 删除自提点
	 * @Title: delzt 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月28日 上午10:26:10  
	 * @author lys
	 */
	@RequestMapping(value="ztdel", method=RequestMethod.POST)
	@ResponseBody
	public String delzt(@RequestParam Long id) {
		try {
			logisticsBusiness.delPickUpStore(id, super.getShopNo());
			return super.getReqJson(true, null);
		} catch (LogisticsException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}

}
