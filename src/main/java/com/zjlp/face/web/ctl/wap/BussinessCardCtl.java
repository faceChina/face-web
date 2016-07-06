package com.zjlp.face.web.ctl.wap;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.product.im.producer.ImFriendsProducer;
import com.zjlp.face.web.server.user.businesscard.business.BusinessCardBusiness;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.card.QRCodeUtil;

@RequestMapping("/app/card/")
@Controller
public class BussinessCardCtl extends WapCtl {
	
	@Autowired
	private UserBusiness userBussiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private BusinessCardBusiness businessCardBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private ImFriendsProducer imFriendsProducer;
	@Autowired
	private ImageService imageService;
	

	/**
	 * 
	 * @Title: card 
	 * @Description: 名片页面
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 * @date 2015年9月8日 上午11:34:29  
	 * @author cbc
	 */
	@RequestMapping("{userId}")
	public String card(@PathVariable Long userId, Model model) {
		User user = userBussiness.getUserById(userId);
		Long loginUserId = super.getUserId();
		BusinessCard card = businessCardBusiness.getBusinessCardByUserId(user.getId());
		SubbranchVo subbranch = subbranchBusiness.getActiveSubbranchByUserId(user.getId());
		Shop shop = shopBusiness.getShopByUserId(user.getId());
		card = this.initUrl(card, shop, subbranch);
		String qrCode = null;
		try {
			qrCode = this.createQRCode(user.getLoginAccount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String toChatShopNo = null;
		if (BusinessCard.TYPE_BF.equals(card.getShopType()) && subbranch != null) {
			toChatShopNo = subbranch.getSupplierShopNo();
		} else {
			toChatShopNo = shop.getNo();
		}
		model.addAttribute("toChatShopNo", toChatShopNo);
		model.addAttribute("qrCode", qrCode);
		model.addAttribute("user", user);
		model.addAttribute("card", card);
		model.addAttribute("shopUrl", card.getShopUrl());
		model.addAttribute("shareUrl", card.getCooperationUrl());
		model.addAttribute("showPhone", this.isShowPhone(loginUserId, user.getLoginAccount(), card));
		if (null == card.getTemplate() || "BC_FREE_V1".equals(card.getTemplate())) {
			return "/wap/product/template/visitingcard/index";
		} else if ("BC_FREE_V2".equals(card.getTemplate())) {
			return "/wap/product/template/visitingcard2/index";
		}
		return "/wap/index/empty";
	}

	/**
	 * 
	 * @Title: isShowPhone 
	 * @Description: 是否展示电话 
	 * @param loginUserId
	 * @param loginAccount
	 * @param card
	 * @return
	 * @date 2015年9月10日 下午7:20:12  
	 * @author cbc
	 */
	private boolean isShowPhone(Long loginUserId, String cardLoginAccount,
			BusinessCard card) {
		boolean showPhone = false;
		if (null != card) {
			if (BusinessCard.PHONE_PUBLIC.equals(card.getPhoneVisibility())) {
				//联系方式公开
				showPhone = true;
			} else if (null != loginUserId && BusinessCard.PHONE_FRIEND.equals(card.getPhoneVisibility())) {
				//联系方式仅好友可见
				User loginUser = userBussiness.getUserById(loginUserId);
				Boolean boo = imFriendsProducer.isFriend(loginUser.getLoginAccount(), cardLoginAccount);
				showPhone = boo;
			} else if (null != loginUserId && BusinessCard.PHONE_SELF.equals(card.getPhoneVisibility())) {
				//仅自己可见
				User loginUser = userBussiness.getUserById(loginUserId);
				if (loginUser.getLoginAccount().equals(cardLoginAccount)) {
					showPhone = true;
				}
			} else if (null == card.getPhoneVisibility()) {
				showPhone = true;
			} 
		} else {
			showPhone = true;
		}
		return showPhone;
	}

	/**
	 * 
	 * @Title: initUrl 
	 * @Description: 初始化页面链接 
	 * @param card
	 * @param shop
	 * @param subbranch
	 * @date 2015年9月8日 上午11:50:24  
	 * @author cbc
	 */
	private BusinessCard initUrl(BusinessCard card, Shop shop, SubbranchVo subbranch) {
		if (null == card) {
			//如果用户没有设置名片，默认展示分店，没有分店展示总店
			card = new BusinessCard();
			if (null == subbranch) {
				card.setShopType(BusinessCard.TYPE_P);
				card.setShopUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm").toString());
				card.setCooperationUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/subbranch/new.htm").toString());
			} else {
				card.setShopType(BusinessCard.TYPE_BF);
				card.setShopUrl(new StringBuilder("/wap/").append(subbranch.getSupplierShopNo()).append("/any/gwscIndex.htm?subbranchId=").append(subbranch.getId()).toString());
				card.setCooperationUrl(new StringBuilder("/wap/").append(subbranch.getSupplierShopNo()).append("/any/subbranch/new.htm?subbranchId=").append(subbranch.getId()).toString());
			}
		} else {
			//如果用户设置了名片的展示店铺
			if (BusinessCard.TYPE_BF.equals(card.getShopType())) {
				if (subbranch == null) {
					card.setShopUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm").toString());
					card.setCooperationUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/subbranch/new.htm").toString());
				} else {
					card.setShopUrl(new StringBuilder("/wap/").append(subbranch.getSupplierShopNo()).append("/any/gwscIndex.htm?subbranchId=").append(subbranch.getId()).toString());
					card.setCooperationUrl(new StringBuilder("/wap/").append(subbranch.getSupplierShopNo()).append("/any/subbranch/new.htm?subbranchId=").append(subbranch.getId()).toString());
				}
			} else if (BusinessCard.TYPE_P.equals(card.getShopType())) {
				card.setShopUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm").toString());
				card.setCooperationUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/subbranch/new.htm").toString());
			} else if (BusinessCard.TYPE_GW.equals(card.getShopType())) {
				card.setShopUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/index.htm").toString());
				card.setCooperationUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/subbranch/new.htm").toString());
			} else if (BusinessCard.TYPE_DU.equals(card.getShopType())) {
				if (null == subbranch) {
					card.setShopUrl(card.getDefineUrl());
					card.setCooperationUrl(new StringBuilder("/wap/").append(shop.getNo()).append("/any/subbranch/new.htm").toString());
				} else {
					card.setShopUrl(card.getDefineUrl());
					card.setCooperationUrl(new StringBuilder("/wap/").append(subbranch.getSupplierShopNo()).append("/any/subbranch/new.htm?subbranchId=").append(subbranch.getId()).toString());
				}
			}
		}
		return card;
	}

	/**
	 * 
	 * @Title: createQRCode 
	 * @Description: 通过名片的账号生成二维码 
	 * @param loginAccount
	 * @return
	 * @date 2015年9月8日 上午10:35:27  
	 * @author cbc
	 * @throws Exception 
	 */
	private String createQRCode(String loginAccount) throws Exception {
		byte[] b = null;
		b = QRCodeUtil.encode("bf:"+loginAccount, b, false);
		String json = imageService.upload(b);
		
		JSONObject jsonObj = JSONObject.fromObject(json);
		AssertUtil.isTrue("SUCCESS".equals(jsonObj.getString("flag")), "上传图片失败:");
		String path = jsonObj.getString("source");
		return path;
	}
	
}
