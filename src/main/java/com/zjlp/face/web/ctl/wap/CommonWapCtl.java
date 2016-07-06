package com.zjlp.face.web.ctl.wap;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.web.ctl.BaseCtl;
import com.zjlp.face.web.server.user.businesscard.business.BusinessCardBusiness;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;
import com.zjlp.face.web.server.user.mulchat.service.MulChatQRCodeService;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.NumberConversion;
import com.zjlp.face.web.util.card.QRCodeUtil;

@Controller
public class CommonWapCtl extends BaseCtl {
	private Logger _log = Logger.getLogger(getClass());

	private static final String ENCODE = "utf-8";
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private BusinessCardBusiness businessCardBusiness;
	@Autowired
	private ImageService imageService;
	@Autowired
	private MulChatQRCodeService mulChatQRCodeService;

	/**
	 * 下单/发货短信
	 * 
	 * @Title: index
	 * @Description: 简化短信通知链接，重定向到个人中心首页
	 * @param model
	 * @return
	 * @date 2015年7月24日 上午10:21:43
	 * @author talo
	 */
	@RequestMapping("/uc/{no}")
	public String i(@PathVariable String no, Model model) {
		String shopNo = NumberConversion.getShopNo(no);
		return super.getRedirectUrl("/wap/" + shopNo + "/buy/personal/index");
	}

	@RequestMapping("/any/code/invite")
	public String myInvitionCode(Long userId, Model model) {
		User user = userBusiness.getUserById(userId);
		model.addAttribute("myInvitationCode", user.getMyInvitationCode());
		model.addAttribute("user", user);
		if (user != null) {
			BusinessCard businessCard = businessCardBusiness
					.getBusinessCardByUserId(user.getId());
			model.addAttribute("businessCard", businessCard);
		}
		return "/wap/index/myInvitationCode";
	}

	// /**
	// * 个人 二维码分享
	// *
	// * @param id userid
	// * @param model
	// * @return
	// */
	// @RequestMapping("/any/code/qrc/{code}/myQRCode")
	// public String myQRCode(@PathVariable String code, Model model) {
	// // --个人二维码分享
	// User user = userBusiness.getUserById(Long.parseLong(code));
	// if (user != null) {
	// // TODO 生成二维码 需要提出来为公共服务
	// String qrCode = null;
	// try {
	// qrCode = this.createQRCode("bf:"+user.getLoginAccount());
	// } catch (Exception e) {
	// _log.error("创建二维码失败！",e);
	// }
	// model.addAttribute("qrCode", qrCode);
	// }else{
	// _log.warn("无此用户！");
	// return null;
	// }
	//
	// return "/wap/index/QRcode-down";
	// }
	// /**
	// * 群 二维码分享
	// *
	// * @param id 群code
	// * @param model
	// * @return
	// */
	// @RequestMapping("/any/code/qrc/{code}/mulQRCode")
	// public String mulQRCode(@PathVariable String code, Model model) {
	// // --群二维码分享
	// // TODO 生成二维码 需要提出来为公共服务
	// //创建前校验
	// MulChatQRCode existQRCOde = mulChatQRCodeService.findByQRCode(code);
	// if(existQRCOde!=null&&existQRCOde.getqRCode()!=null){
	// String qrCode=existQRCOde.getqRCode();
	// try {
	// qrCode = this.createQRCode("gf:"+code);
	// } catch (Exception e) {
	// _log.error("创建二维码失败！",e);
	// }
	// model.addAttribute("qrCode", qrCode);
	// }else{
	// _log.warn("无此群组！");
	// return null;
	// }
	//
	// return "/wap/index/QRcode-down";
	// }
	/**
	 * 个人 二维码分享
	 * 
	 * @param id
	 *            userid
	 * @param model
	 * @return
	 */
	@RequestMapping("/any/code/qrc/{code}/myQRCode")
	public String myQRCode(@PathVariable String code, Model model) {
		model.addAttribute("imgCpath", "/qrcimg/code/img_myQRCode_" + code
				+ ".jpg");
		model.addAttribute("codeTpye",0);
		return "/wap/index/QRcode-down";
	}

	/**
	 * 群 二维码分享
	 * 
	 * @param id
	 *            群code
	 * @param model
	 * @return
	 */
	@RequestMapping("/any/code/qrc/{code}/mulQRCode")
	public String mulQRCode(@PathVariable String code, Model model) {
		model.addAttribute("imgCpath", "/qrcimg/code/img_mulQRCode_" + code
				+ ".jpg");
		model.addAttribute("codeTpye",1);
		return "/wap/index/QRcode-down";
	}

	/**
	 * 二维码输出
	 * 
	 * @param code
	 * @param model
	 */
	@RequestMapping("/code/img_{imgParm}")
	public void img(@PathVariable String imgParm, Model model,
			HttpServletResponse response) {
		byte[] qrBt = null;
		OutputStream out = null;
		try {
			if (imgParm != null && imgParm.split("_").length == 2) {
				// 拆分code
				String type = imgParm.split("_")[0];
				String code = imgParm.split("_")[1];
				if ("myQRCode".equals(type)) {
					// --个人二维码分享
					User user = userBusiness.getUserById(Long.parseLong(code));
					// 创建前校验
					if (user != null) {
						qrBt = this
								.createQRCode("bf:" + user.getLoginAccount());
					} else {
						_log.warn("无此用户！");
					}
				} else if ("mulQRCode".equals(type)) {
					// --群二维码分享
					// 创建前校验
					MulChatQRCode existQRCOde = mulChatQRCodeService
							.findByQRCode(code);
					if (existQRCOde != null && existQRCOde.getqRCode() != null) {
						String qrCode = existQRCOde.getqRCode();
						qrBt = this.createQRCode("gf:" + qrCode);
					} else {
						_log.warn("无此群组！");
					}
				}
				response.setContentType("image/jpg");
				out = response.getOutputStream();
				if (qrBt != null) {
					out.write(qrBt);
				}
			}
		} catch (Exception e) {
			_log.error("二维码生成失败!", e);
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
			}

		}
	}

	/**
	 * 生成二维码 字节
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	private byte[] createQRCode(String code) throws Exception {
		byte[] b = null;
		b = QRCodeUtil.encode(code, b, false);
		return b;
	}
}
