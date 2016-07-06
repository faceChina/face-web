package com.zjlp.face.web.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;

@Controller
@RequestMapping("/u/account/user/")
public class UserCtl extends BaseCtl {

	@Autowired
	private UserBusiness userBusiness;
	
	/**
	 * 用户基本资料首页
	 * @Title: index 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月25日 下午3:24:56  
	 * @author lys
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(Model model) {
		Long userId = super.getUserId();
		User user = userBusiness.getUserById(userId);
		model.addAttribute("user", new UserVo(user));
		return "/m/user/basics/index";
	}
	
	/**
	 * 基本资料编辑页
	 * @Title: editPage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月25日 下午3:27:42  
	 * @author lys
	 */
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public String editPage(Model model) {
		Long userId = super.getUserId();
		User user = userBusiness.getUserById(userId);
		model.addAttribute("user", new UserVo(user));
		return "/m/user/basics/edit";
	}
	
	/**
	 * 编辑用户基本资料
	 * @Title: edit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param headimgurl
	 * @param nickName
	 * @param email
	 * @return
	 * @date 2015年3月25日 下午3:30:25  
	 * @author lys
	 */
	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String edit(String headimgurl, String nickname, String email) {
		userBusiness.editProfile(super.getUserId(), headimgurl, nickname, email);
		return super.getRedirectUrl("index");
	}
}
