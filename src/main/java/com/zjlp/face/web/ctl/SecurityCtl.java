package com.zjlp.face.web.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.constants.ConstantsMethod;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.web.constants.MobilecodeType;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;

@Controller
@RequestMapping("/u/account/security/")
public class SecurityCtl extends BaseCtl {

	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	private static Integer[] page_index = {1, 2};

	/**
	 * 安全设置首页
	 * 
	 * @Title: index
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午5:17:06
	 * @author lys
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model) {
		User user = userBusiness.getUserById(getUserId());
		model.addAttribute("phone",
				ConstantsMethod.replaceToHide(user.getCell(), 3, 4));
		return "/m/user/security/index";
	}

	// --------------修改登陆密码---------------

	/**
	 * 登陆密码修改
	 * 
	 * @Title: editLoginCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param passwd
	 *            旧密码
	 * @param newPasswd
	 *            新密码
	 * @param confirmPasswd
	 *            确认密码
	 * @return
	 * @date 2015年3月23日 下午7:19:08
	 * @author lys
	 */
	@RequestMapping(value = "logincode", method = RequestMethod.POST)
	@ResponseBody
	public String editLoginCode(@RequestParam String password,
			@RequestParam String newPasswd, @RequestParam String confirmPasswd) {
		try {
			userBusiness.editUserPasswd(super.getUserId(), password, newPasswd,
					confirmPasswd);
			return super.getReqJson(true, "修改成功");
		} catch (UserException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}

	// --------------手机绑定-----------------
	/**
	 * 手机重新绑定
	 * 
	 * @Title: phoneCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param retUrl	
	 *            跳转页
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午7:25:06
	 * @author lys
	 */
	@RequestMapping(value = "bindPhone", method = RequestMethod.GET)
	public String phoneCode(@RequestParam String retUrl, String errorMsg, Model model) {
		User user = userBusiness.getUserById(getUserId());
		model.addAttribute("phone",
				ConstantsMethod.replaceToHide(user.getCell(), 3, 3));
		model.addAttribute("retUrl", retUrl);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("pageIndex", page_index[0]);
		return "/m/user/security/security-phone-bind";
	}
	
	/**
	 * 旧手机验证
	 * @Title: checkCell 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param retUrl
	 * @param oldMobilecode
	 * @param model
	 * @return
	 * @date 2015年4月6日 上午10:08:56  
	 * @author lys
	 */
	@Token(save = true)
	@RequestMapping(value = "checkcell", method = RequestMethod.POST)
	public String checkCell(@RequestParam String retUrl,
			@RequestParam String mobilecode, Model model) {
		try {
			model.addAttribute("retUrl", retUrl);
			Long userId = super.getUserId();
			User user = userBusiness.getUserById(userId);
			// 验证码验证
			phoneBusiness.checkMobilecode(user.getCell(),
					MobilecodeType.mobile_phonecode_0.getScene(), mobilecode);
			model.addAttribute("retUrl", retUrl);
			model.addAttribute("pageIndex", page_index[1]);
			return "/m/user/security/security-phone-bind";
		} catch (BaseException e) {
			model.addAttribute("errorMsg", e.getExternalMsg());
			return super.getRedirectUrl("bindPhone");
		}
	}
	
	

	/**
	 * 新手机绑定
	 * @Title: bind 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param retUrl
	 * @param newPhone
	 * @param newMobilecode
	 * @param model
	 * @return
	 * @date 2015年4月6日 上午10:08:50  
	 * @author lys
	 */
	@Token(remove = true)
	@RequestMapping(value = "bind", method = RequestMethod.POST)
	public String bind(@RequestParam String retUrl,
			@RequestParam String cell, @RequestParam String mobilecode,
			Model model) {
		try {
			model.addAttribute("retUrl", retUrl);
			phoneBusiness.checkMobilecode(cell, MobilecodeType.mobile_phonecode_1.getScene(),
					mobilecode);
			userBusiness.editUserCell(getUserId(), cell);
			return "/m/user/security/security-phone-success";
		} catch (BaseException e) {
			model.addAttribute("errorMsg", e.getExternalMsg());
			return super.getRedirectUrl("bindPhone");
		}
	}

	// ----------------支付密码&姓名&身份证------------------

	/**
	 * 绑定手机验证
	 * 
	 * @Title: bindPhone
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param retUrl
	 *            最终跳转页
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午4:30:55
	 * @author lys
	 */
	@RequestMapping(value = "paymentCode", method = RequestMethod.GET)
	public String bindPhone(@RequestParam(required = true) String retUrl, String errorMsg, String checkIdentity,
			Model model) {
		Long userId = super.getUserId();
		User user = userBusiness.getUserById(userId);
		boolean hasPaymentCode = accountBusiness.existPaymentCode(userId);
		model.addAttribute("phone",
				ConstantsMethod.replaceToHide(user.getCell(), 3, 4));
		model.addAttribute("retUrl", retUrl);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("checkIdentity", checkIdentity);
		model.addAttribute("pageIndex", page_index[0]);
		if (hasPaymentCode) {
			return "/m/user/security/security-code-edit";
		} else {
			return "/m/user/security/security-code-set";
		}
	}
	
	/**
	 * 设置支付密码身份证&验证码验证
	 * @Title: checkPhone 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param retUrl
	 * @param mobilecode
	 * @param checkIdentity
	 * @param model
	 * @return
	 * @date 2015年4月6日 下午12:59:45  
	 * @author lys
	 */
	@Token(save=true)
	@RequestMapping(value = "editPage", method = RequestMethod.POST)
	public String checkPhone(@RequestParam(required = true) String retUrl, 
			@RequestParam String mobilecode, String checkIdentity, Model model) {
		try {
			model.addAttribute("retUrl", retUrl);
			Long userId = super.getUserId();
			User user = userBusiness.getUserById(userId);
			//验证码验证
			phoneBusiness.checkMobilecode(user.getCell(), 
					MobilecodeType.mobile_paymentcode_0.getScene(), mobilecode);
			boolean hasPaymentCode = accountBusiness.existPaymentCode(userId);
			//身份证验证
			String identity = user.getIdentity() == null ? "" : user.getIdentity();
			boolean isTrue = hasPaymentCode && !identity.equals(checkIdentity);
			AssertUtil.isTrue(!isTrue, "", "身份证信息输入错误，请重新获取验证码");
			model.addAttribute("pageIndex", page_index[1]);
			if (hasPaymentCode) {
				return "/m/user/security/security-code-edit";
			} else {
				return "/m/user/security/security-code-set";
			}
		} catch (BaseException e) {
			model.addAttribute("errorMsg", e.getExternalMsg());
			model.addAttribute("checkIdentity", checkIdentity);
			return super.getRedirectUrl("paymentCode");
		}
	}

	/**
	 * 用户资料补全
	 * 
	 * @Title: paymentCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param retUrl
	 *            跳转页
	 * @param userName
	 *            用户名
	 * @param identity
	 *            身份证
	 * @param paymentCode
	 *            支付密码
	 * @param confirmCode
	 *            确认支付密码
	 * @return
	 * @date 2015年3月23日 下午4:50:24
	 * @author lys
	 */
	@Token(remove=true)
	@RequestMapping(value = "editCode", method = RequestMethod.POST)
	public String paymentCode(@RequestParam String retUrl, String userName,
			String identity, @RequestParam String paymentCode,
			@RequestParam String confirmCode, Model model) {
		model.addAttribute("retUrl", retUrl);
		Long userId = super.getUserId();
		accountBusiness.completInformation(userId, paymentCode,
				confirmCode, userName, identity);
		return "/m/user/security/security-code-success";
	}
	
}
