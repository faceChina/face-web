package com.zjlp.face.web.ctl.wap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.web.server.operation.popularize.business.PopularizeBusiness;
import com.zjlp.face.web.util.WeixinUtil;

@Controller
@RequestMapping("/wap/{shopNo}/any/popularize/")
public class PopularizeWapCtl extends WapCtl  {
	
	@Autowired
	private PopularizeBusiness popularizeBusiness;
	@Autowired
	private ShopExternalService shopExternalService;
	/**
	 * 分享连接输入手机号
	 * @Title: shareInit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年5月13日 上午10:05:43  
	 * @author dzq
	 */
	@RequestMapping(value="share",method=RequestMethod.GET)
	public String shareInit(HttpServletRequest request,Model model){
		//获取需要分享的页面感地址（此页面必须支持Get请求）
		String shareUri = request.getHeader("Referer");
		model.addAttribute("shareUri", shareUri);
		return "/wap/operation/popularize/share";
	}
	
	/**
	 * 提交分享链接
	 * @Title: shareSubmit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年5月13日 上午10:05:56  
	 * @author dzq
	 */
	@RequestMapping(value="share",method=RequestMethod.POST)
	public String shareSubmit(@RequestParam("moblie") String moblie,@RequestParam String shareUri,Model model){
		StringBuilder uribBuilder = new StringBuilder(shareUri);
		if (-1!=shareUri.indexOf("htm?")) {
			uribBuilder.append("&");
		}else{
			uribBuilder.append("?");
		}
		uribBuilder.append("shareId=").append(moblie);
		// 微信公众号appId
    	String appId = PropertiesUtil.getContexrtParam("WECAHT_APP_ID");
    	// 微信公众号appSecret
    	String appSecret = PropertiesUtil.getContexrtParam("WECAHT_APP_SECRET");
    	// 微管家路径
    	String wgjUrl = PropertiesUtil.getContexrtParam("WGJ_URL");
		// 获取token
		String token = shopExternalService.getAccessToken(appId, appSecret);
		String signature = new WeixinUtil().getSignature(token, "rfOEfBdBznhLFkZW", "1422009542",wgjUrl+ "/wap/"+super.getShopNo()+"/any/popularize/share.htm");
		model.addAttribute("signature", signature);
		model.addAttribute("appId", appId);
		model.addAttribute("shop", super.getShop());
		model.addAttribute("wgjUrl", wgjUrl);
		model.addAttribute("shareLink", uribBuilder.toString());
		return "/wap/operation/popularize/share1";
	}

}
