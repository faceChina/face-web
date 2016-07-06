package com.zjlp.face.web.ctl.wap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;

@Controller
@RequestMapping("/wap/{shopNo}/buy")
public class ShopWapCtl extends WapCtl {
	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private ShopBusiness shopBusiness;
	
	@RequestMapping(value = "/shop/activate", method = RequestMethod.POST)
	@ResponseBody
	public String activate(@RequestParam String code){
		try{
			UserInfo user = super.getUser();
			String loginAccount = user.getLoginAccount();
			Integer states = shopBusiness.activateShop(code, loginAccount);
			return super.getReqJson(true, states.toString());
		}catch(Exception e){
			log.error(e.getMessage(), e);
			return super.getReqJson(false, null);
		}
	}
	
}
