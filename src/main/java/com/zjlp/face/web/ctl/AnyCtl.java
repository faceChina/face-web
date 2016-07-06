package com.zjlp.face.web.ctl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zjlp.face.util.constants.ConstantsMethod;
import com.zjlp.face.web.component.sms.SmsContent;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.util.ParameterRequestWrapper;

@Controller
@RequestMapping("/any/")
public class AnyCtl extends BaseCtl {
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private PhoneBusiness phoneBusiness;
	
	/**
	 * 检查用户名是否重复
	 * @Title: checkLoginAccount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param phone
	 * @param model
	 * @return
	 * @date 2014年8月9日 下午3:15:44  
	 * @author Administrator
	 */
	@RequestMapping(value="checkLoginAccount")
	@ResponseBody
	public String checkLoginAccount(String phone,Model model){
		return userBusiness.checkLoginAccount(phone);
	}
	
	
	
	/**
	 * 
	 * 发送注册码
	 * @Title: registerPhoneCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param phone
	 * @param type
	 * @return
	 * @date 2014年12月25日 下午4:17:44  
	 * @author fjx
	 */
	
	@RequestMapping(value="web/registerCode")
	@ResponseBody
	public String registerPhoneCode(String phone,Integer type){
		phoneBusiness.getPhoneCode(phone,type,SmsContent.UMS_201);
		return "true";
	}
	
	
	@RequestMapping(value="usergreement")
	public String usergreement(){
		return "/wap/login/usergreement";
	}
	
	
	@RequestMapping(value="web/registerfor")
	public ModelAndView registerForward(String mobile,String mobilecode,String pwd,String repwd,
			HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		
		Integer codeStates = phoneBusiness.checkPhoneCode(mobile, mobilecode,Constants.PHONE_CODE_ZC);
		UserVo userVo = new UserVo();
		userVo.setPhone(mobile);
		userVo.setPwd(pwd);
		userVo.setFlag(codeStates);
		
		
		String flag = userBusiness.register(userVo);
		if ("success".equals(flag)) {
			response.sendRedirect("/login.htm");
			return null;
		}
		response.sendRedirect("/company/join.jsp?mobile="+mobile+"&flag="+codeStates);
		return null;
	}
	
	
	@RequestMapping(value="findPassword")
	public String findPassword(Model model,HttpServletRequest request){
		String redirUrl = (String) request.getSession().getAttribute("redirUrl");
		if (StringUtils.isBlank(redirUrl)) {
			redirUrl =  request.getHeader("Referer");
		}
		String type = request.getParameter("type");
		model.addAttribute("type",type);
		
		
		if("4".equals(type)){
			request.getSession().setAttribute("goodDetailsMap", request.getParameterMap());
		}
		
		return "/wap/login/password-find";
	}
	
	/**
	 * 验证登录帐号和绑定手机号码
	 * @Title: checkUserPhone
	 * @Description: (用于忘记密码)
	 * @param loginAccount
	 * @param phone
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月6日 下午8:17:27
	 */
	@RequestMapping(value="checkUserPhone")
	@ResponseBody
	public String checkUserPhone(String loginAccount,String phone,Model model){
	    return userBusiness.checkUserPhone(loginAccount,phone);
	}
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="web/checkUser")
	@Token(save=true)
	public String checkUserPasswd(String accounts,String mobilecode,String codeType,String type,Model model){
		
		Integer falg = phoneBusiness.fristCheckPhoneCode(accounts, mobilecode,Integer.parseInt(codeType));	
				
		if(1 != falg.intValue()){
			model.addAttribute("message","验证码错误");
			if("1".equals(type)){
				return "/m/common/error-back";
			}else{
				return "/wap/common/error-back";
			}
		}
		
		model.addAttribute("accounts",accounts);
		model.addAttribute("mobilecode",mobilecode);
		
		model.addAttribute("type",type);
		
		if("1".equals(type)){
			return "/m/user/password/password-2";
		}else{
			return "/wap/login/password-set";
		}
	}
	
	@Token(remove=true)
	@RequestMapping(value="web/savePassword")
	public String changePassword(String accounts,String mobilecode,String newpwd,String codeType,String type,Model model,HttpServletRequest request,HttpServletResponse response){
			UserInfo userInfo = userBusiness.getUserInfo(accounts);
			if(null == userInfo){
				return "/wap/common/error404";
			}
			
			Integer falg = phoneBusiness.checkPhoneCode(accounts, mobilecode,Integer.parseInt(codeType));
			if(1 != falg.intValue()){
				return "/wap/common/error404";
			}
			userBusiness.editUserPasswd(userInfo.getUserId(), newpwd, newpwd);
			
			if("1".equals(type)){
				return "/m/user/password/password-3";
			}else if("2".equals(type)){
				return super.getForwardUrl("/wap/login");
			}else if("3".equals(type)){
				return super.getForwardUrl("/free/login");
			}else if("4".equals(type)){
				//TODO 补丁,商品详细页面购物登录和流程更改
				Map goodDetailsMap = new HashMap((Map)request.getSession().getAttribute("goodDetailsMap")); 
				
				String[] skipurls = (String[])goodDetailsMap.get("skipurl");
				String skipurl = skipurls[0];
				
				goodDetailsMap.put("username", new String[]{userInfo.getLoginAccount()}); 
				goodDetailsMap.put("password", new String[]{newpwd});
				goodDetailsMap.put("iscallback",new String[]{"true"});
				
				HttpServletRequest req = (HttpServletRequest) request;   
				ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(req,goodDetailsMap);     
				request = wrapRequest; //这是rquest就和本身的request一样了
				
				try {
					request.getRequestDispatcher(skipurl).forward(request,response); 
					request.getSession().setAttribute("goodDetailsMap",null);
					return null;
				} catch (ServletException e) {
					throw new RuntimeException("没有找到返回页面",e);
				} catch (IOException e) {
					throw new RuntimeException("没有找到返回页面",e);
				}
			}else{
				throw new RuntimeException("没有找到返回页面");
			}
	}
	
	
	@RequestMapping(value="retrievePassword")
	public String retrievePassword(Model model){
		return "/m/user/password/password-1";
	}
	
	/**
	 * 获取账号的手机号
	 * @Title: getcell 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param loginAccount
	 * @return
	 * @date 2015年4月7日 下午7:35:04  
	 * @author lys
	 */
	@RequestMapping(value="getcell",method=RequestMethod.POST)
	@ResponseBody
	public String getcell(@RequestParam String loginAccount) {
		try {
			User user = userBusiness.getUserByLoginAccount(loginAccount);
			if (null == user) {
				return super.getReqJson(false, "该账号不存在");
			}
			return super.getReqJson(true, ConstantsMethod.replaceToHide(user.getCell(), 3, 3));
		} catch (UserException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
		
	}

}
