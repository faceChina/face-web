package com.zjlp.face.web.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.web.constants.ConstantsSession;

@Controller
public class LoginCtl extends BaseCtl{
	
	/**
	 * 登陆失败跳转
	 * @Title: toLogin 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月11日 下午4:13:31  
	 * @author fjx
	 */
	@RequestMapping("/tologin")
	public String toLogin(Model model,HttpServletRequest request){
		
		String userName =  (String) request.getSession().getAttribute(ConstantsSession.SESSION_REMEBER_USERNAME);
		if (null != userName && !"".equals(userName)) {
			request.getSession().removeAttribute(ConstantsSession.SESSION_REMEBER_USERNAME);
			model.addAttribute("username",userName);
		}

		String type = (String) request.getSession().getAttribute("loginType");
		
		if("wap".equals(type)){
			return getRedirectUrl("/wap/login");
		}
		if("free".equals(type)) {
			return getRedirectUrl("/free/login");
		}
		return getRedirectUrl("/login");
		
	}
	
	
	/**
	 * session失效跳转
	 * @Title: sessionlost 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月11日 下午5:29:53  
	 * @author fjx
	 */
	@RequestMapping("/sessionlost")
	public String sessionlost(){
		
		return "/wap/common/overTime";
	}
	
	
	/**
	 * 登录
	 * @Title: login 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月11日 下午5:32:42  
	 * @author fjx
	 */
	@RequestMapping("/login")
	public String login(Model model){
		
		return "/m/login/login";
	}
	
	
	/**
	 * 注销
	 * @Title: tologinout 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月11日 下午5:32:26  
	 * @author fjx
	 */
	@RequestMapping("/tologinout")
	public String tologinout(Model model){
		
		return super.getRedirectUrl("/login");
		
	}
	
	
	/**
	 * 访问资源没有权限
	 * @Title: accessDenied 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月11日 下午5:30:21  
	 * @author fjx
	 */
	@RequestMapping("/accessDenied")
	public String accessDenied(Model model){
		
		return "/m/login/accessDenied";
	}
	

}
