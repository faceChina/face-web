package com.zjlp.face.web.ctl.wap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.http.cookies.Cookies;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;

@Controller
public class LoginWapCtl extends WapCtl{
	
	@Autowired
	private PhoneBusiness phoneBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	
    @RequestMapping(value = "/wap/{shopNo}/login")
	public String wapLoginShopNo(Model model){
		
		return "/wap/login/login";
	}
    
    @RequestMapping(value = "/wap/index")
 	public String iindex(Model model){
 		
 		return "/wap/index/index";
 	}
    
	@RequestMapping(value="/any/registration")
	public String registration(String type,Model model,HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("redirUrl", null);
		return "/wap/login/registration";
	}
	

	@RequestMapping(value="/any/appRegister")
	@ResponseBody
	public String register(String mobile,String mobilecode,String pwd,String repwd,String nickname,Model model){
		try {
			Integer states = phoneBusiness.checkPhoneCode(mobile, mobilecode,Constants.PHONE_CODE_ZC);
			if (null == states || 1 != states) {
				return "验证码错误";
			}
		} catch (Exception e) {
			return "验证码错误";
		}
		try {
			UserVo userVo = new UserVo();
			userVo.setPhone(mobile);
			userVo.setPwd(pwd);
			userVo.setFlag(1);
			userVo.setNickname(nickname);
			return userBusiness.register(userVo);
		} catch (Exception e) {
			e.printStackTrace();
			return "4";
		}
	}
	
	
	@RequestMapping(value="/any/editPassword",method=RequestMethod.POST)
	@ResponseBody
	public String editPassword(String oldPassword,String password,
			String confirmPassword,HttpServletRequest request){
		try {
			Long userId = super.getUserId();
			Shop shop = super.getShop();
			userBusiness.editUserPasswd(userId, password, confirmPassword);
			if(null != shop && StringUtils.isNotBlank(shop.getNo())){
				HttpSession session = request.getSession(false);
				if(null != session){
					session.invalidate();
				}
			    if(true) {
			        SecurityContext context = SecurityContextHolder.getContext();
			        context.setAuthentication(null);
			    }
			    SecurityContextHolder.clearContext();
				HttpSession newSession = request.getSession();
				newSession.setAttribute("shop", shop);
			}
			return super.getReqJson(true, userId.toString());
		} catch (Exception e) {
			return super.getReqJson(false, e.getMessage());
		}
	}
     
    
    @RequestMapping(value="/any/empty")
	public String empty(Model model) {
		return "/wap/index/empty";
	}
    
    @RequestMapping("/any/freeze-shop")
	public String shopFreeze(){
		return "/wap/index/freeze-shop";
	}
    
    @RequestMapping(value = "/wap/{shopNo}/buy/logout")
	public String wapLogout(Model model,HttpServletRequest request, HttpServletResponse response){
		Shop shop = super.getShop();
		if(null != shop && StringUtils.isNotBlank(shop.getNo())){
			HttpSession session = request.getSession(false);
			String lpOpenId = (String) request.getSession().getAttribute("anonymousOpenId");
//			String openId = (String) request.getSession().getAttribute(iCardBusiness.getKey(shop.getNo()));
			if(null != session){
				session.invalidate();
			}
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(null);
	        SecurityContextHolder.clearContext();
			HttpSession newSession = request.getSession();
			newSession.setAttribute("shop", shop);
			newSession.setAttribute("anonymousOpenId", lpOpenId);
//			newSession.setAttribute(iCardBusiness.getKey(shop.getNo()), openId);
		}
		return super.getRedirectUrl("/wap/{shopNo}/login");
    }
    
    @RequestMapping(value="/any/wap/goodsDetail/login",method=RequestMethod.POST)
	@ResponseBody
	public String goodsDetailLogin(String phone,String password){
    	try {
    		User user = userBusiness.getUserByLoginAccountAndPasswd(phone, password);
    		
    		if(user == null){
    			return "密码错误";
    		}
			
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "系统异常";
		}
	}
    
    @RequestMapping(value="/any/wap/goodsDetail/tgtLogin",method=RequestMethod.POST)
	@ResponseBody
	public String goodsDetailTgtLogin(HttpServletRequest request,HttpServletResponse response){
    	Cookie tgtCookie = Cookies.get(request, "CASTGC");
    	
    	if(null == tgtCookie){
    		return "false";
    	}
    	
    	return "true";
	}
}
