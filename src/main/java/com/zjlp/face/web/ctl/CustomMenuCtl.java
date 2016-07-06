package com.zjlp.face.web.ctl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.CustomMenuModular;
import com.zjlp.face.web.server.user.weixin.bussiness.CustomMenuBusiness;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuModularVo;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;

/**
 * 自定义菜单控制层
 * @ClassName: CustomMenuCtl 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月30日 下午3:58:20
 */
@Controller
@RequestMapping("/u/weixin/menu/")
public class CustomMenuCtl extends BaseCtl{
	
	@Autowired
	private CustomMenuBusiness customMenuBusiness;
	@Autowired(required=false)
	private ShopExternalService shopExternalService;
	
	@RequestMapping(value="list")
	public String list(CustomMenuDto customMenuDto, Model model) {
		
		customMenuDto.setShopNo(super.getShopNo());
		// 查询自定义菜单列表
		List<CustomMenuVo> customMenuVos = customMenuBusiness.findCustomMenuVoList(customMenuDto);
		
		// 模块列表
		List<CustomMenuModularVo> customMenuModularVos = new ArrayList<CustomMenuModularVo>();
		String customMenuModularJson = "";
		if(Constants.SHOP_GW_TYPE.equals(super.getShop().getType())) {
			customMenuModularVos.addAll(CustomMenuModular.getWgwModulars());
			customMenuModularJson = CustomMenuModular.getWgwCustomMenuModularJson();
		} else if(Constants.SHOP_SC_TYPE.equals(super.getShop().getType())) {
			customMenuModularVos.addAll(CustomMenuModular.getWscModulars());
			customMenuModularJson = CustomMenuModular.getWscCustomMenuModularJson();
		}
		model.addAttribute("customMenuVos", customMenuVos);
		model.addAttribute("customMenuModularVos", customMenuModularVos);
		model.addAttribute("customMenuModularJson", customMenuModularJson);
		return "/m/user/weixin/menu/custom-nav";
	}
	
	@RequestMapping(value="addOrEdit")
	@ResponseBody
	public String addOrEditCustomMenu(CustomMenuDto customMenuDto, Model model) {
		// 新增或编辑自定义菜单
		try {
			customMenuBusiness.generateCustomMenu(customMenuDto, super.getShop());
			return super.getReqJson(true, null);
		} catch (Exception e) {
			return super.getReqJson(false, "生成自定义菜单失败,请先绑定公众号");
		}
		
	}

}
