package com.zjlp.face.web.ctl.wap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.shop.domain.GrandInfo;
import com.zjlp.face.shop.domain.WechatDevInfo;
import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.weixin.bussiness.WechatBusiness;
import com.zjlp.face.web.server.user.weixin.domain.MessageContent;

/**
 * 微信消息控制层（前端）
 * @ClassName: WechatWapCtl 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月1日 下午2:20:08
 */
@Controller
public class WechatWapCtl extends WapCtl{
	
	@Autowired
	private WechatBusiness wechatBusiness;
	
	@Autowired
	private ShopExternalService shopExternalService;
	
	private Log _log = LogFactory.getLog(getClass());
//	WeixinUtil wt = new WeixinUtil();
	
	/**
	 * 查询消息内容
	 * @Title: detail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param messageContent
	 * @param model
	 * @return
	 * @date 2015年4月1日 下午2:20:20  
	 * @author ah
	 */
	@RequestMapping(value="/wap/{shopNo}/any/wechat/detail")
	public String detail(MessageContent messageContent, Model model) {
		
		//查询消息内容
		messageContent = wechatBusiness.getMessageContentById(messageContent.getId());
		model.addAttribute("messageContent", messageContent);
		return "/wap/user/weixin/detail";
	}
	
	@RequestMapping(value = "/wechat/getOpenId")
	public String Oauth2MeUrl(HttpServletRequest request, String code, String state, Model model){
		try{
			Shop shop = super.getShop();
			String appId = shop.getAppId();
			Assert.hasLength(appId, "该店铺未设置appid");
			String appSecret = shop.getAppSecret();
			Assert.hasLength(appSecret, "该店铺未设置appSecret");
			WechatDevInfo devInfo = new WechatDevInfo();
			devInfo.setAppId(appId);
			devInfo.setAppSecret(appSecret);
			// 通过code换取网页授权access_token
			GrandInfo grandInfo = shopExternalService.getDynamicAccessoken(code, devInfo);
			request.getSession().setAttribute(shop.getNo()+"shopOpenId", grandInfo.getOpenId());
		}catch(Exception e){
			_log.error("默认授权获取shopOpenId失败", e);
		}
		return "redirect:"+state;
	}
	
	@RequestMapping(value = "/wechat/getAnonymousOpenId")
	public String Oauth2AnoyUrl(HttpServletRequest request, String code, String state, Model model){
		try{
			
			String appId = PropertiesUtil.getContexrtParam("WECAHT_APP_ID");
			Assert.hasLength(appId, "WECAHT_APP_ID未配置");
			String appSecret = PropertiesUtil.getContexrtParam("WECAHT_APP_SECRET");
			Assert.hasLength(appSecret, "WECAHT_APP_SECRET未配置");
			WechatDevInfo devInfo = new WechatDevInfo();
			devInfo.setAppId(appId);
			devInfo.setAppSecret(appSecret);
			// 通过code换取网页授权access_token
			GrandInfo grandInfo = shopExternalService.getDynamicAccessoken(code, devInfo);
			request.getSession().setAttribute("anonymousOpenId", grandInfo.getOpenId());
		}catch(Exception e){
			_log.error("默认授权获取anonymousOpenId失败", e);
		}
		return "redirect:"+state;
	}
	
//	@RequestMapping(value="/wechat/goodPageWap")
//	public String goodPage(Model model) {
//		// 微信公众号appId
//    	String appId = PropertiesUtil.getContexrtParam("WECAHT_APP_ID");
//    	// 微信公众号appSecret
//    	String appSecret = PropertiesUtil.getContexrtParam("WECAHT_APP_SECRET");
//    	// 微管家路径
//    	String wgjUrl = PropertiesUtil.getContexrtParam("WGJ_URL");
//		String token = shopExternalService.getAccessToken(appId, appSecret);
//		String signature = wt.getSignature(token, "rfOEfBdBznhLFkZW", "1422009542",wgjUrl+ "/wechat/goodPageWap.htm");
//		model.addAttribute("signature", signature);
//		return "/wap/user/weixin/goodPage";
//	}
	
}
