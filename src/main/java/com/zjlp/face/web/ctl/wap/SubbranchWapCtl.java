package com.zjlp.face.web.ctl.wap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;

@Controller
@RequestMapping("/wap/{shopNo}")
public class SubbranchWapCtl extends WapCtl{
	@Autowired private SubbranchBusiness subbranchBusiness;
	@Autowired private ShopBusiness shopBusiness;
	
	/**
	 * @Description: 分享成为分销
	 * @param model
	 * @param shopNo
	 * @param subbrachid
	 * @return
	 * @author: huangxilei
	 */
	@RequestMapping(value="/any/subbranch/new")
	public String newSubbranch(Model model,@PathVariable("shopNo")String shopNo,Long subbranchId){
		if(subbranchId!=null){
			Subbranch subbranch=subbranchBusiness.findSubbranchById(subbranchId);
			shopNo=subbranch.getSupplierShopNo();
		}
		Shop shop=shopBusiness.getShopByNo(shopNo);
		model.addAttribute("applyShop", shop);
		model.addAttribute("applySubbranchId", subbranchId);
		return "/wap/operation/subbranch/agency-apply";
	}
	
	/**
	 * @Description: 建立分销
	 * @param model
	 * @param shopNo
	 * @param subbrachId
	 * @param nickname
	 * @param phoneNo
	 * @return
	 * @author: huangxilei
	 */
	@ResponseBody
	@RequestMapping(value="/any/subbranch/add")
	public String addSubbranch(Model model,@PathVariable("shopNo")String shopNo,Long subbranchId,String nickname, String phoneNo){
		// 用户不存在 -1905L;
		// 总店不存在 -1906L;
		// 已经是分店，不能再申请成为分店 -1907L;
		// 分店不存在 -1909L;
		// 查询结果条件不符合，操作失败 -1910L;
		// 自己不能申请成为自己的分店 -1911L;
		// 所申请分店已经是最大代理等级，暂时不能申请成为其代理 -1920L;
		Long status=null;
		if(subbranchId==null){
			status=subbranchBusiness.applyAsSubByIdWithoutUser(shopNo, subbranchId, nickname, phoneNo);
		}else{
			status=subbranchBusiness.applyAsSubByIdWithoutUser(null, subbranchId, nickname, phoneNo);
		}
		
		return String.valueOf(status);
	}
	/**
	 * @Description: 成功并且完成分销关系建立
	 * @param model
	 * @param shopNo
	 * @return
	 * @author: huangxilei
	 */
	@RequestMapping(value="/any/subbranch/finish")
	public String finish(Model model,@PathVariable("shopNo")String shopNo, HttpServletRequest request){
		Shop shop=shopBusiness.getShopByNo(shopNo);
		model.addAttribute("shop", shop);
		String userAgent = request.getHeader("user-agent");
		String contexrtParam = PropertiesUtil.getContexrtParam("APP_USER_AGENT");
		AssertUtil.hasLength(contexrtParam, "config.properties配置APP_USER_AGENT字段");
		boolean isAppBrowser = false;
		if (StringUtils.isNotBlank(userAgent) && userAgent.contains(contexrtParam)) {
			isAppBrowser = true;
		}
		model.addAttribute("isAppBrowser", isAppBrowser);
		return "/wap/operation/subbranch/agency-apply-success";
	}
	
}
