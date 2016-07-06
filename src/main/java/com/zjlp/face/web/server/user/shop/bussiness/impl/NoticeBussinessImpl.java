package com.zjlp.face.web.server.user.shop.bussiness.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.user.shop.bussiness.NoticeBussiness;
import com.zjlp.face.web.server.user.shop.domain.Notice;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.NoticeService;
import com.zjlp.face.web.server.user.shop.service.ShopService;

@Service
public class NoticeBussinessImpl implements NoticeBussiness {
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private NoticeService noticeService;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Long addNotice(Notice notice) {
		AssertUtil.notNull(notice, "参数notice不能为空");
		AssertUtil.notNull(notice.getShopNo(), "notice.shopNo不能为空");
		AssertUtil.isTrue(this.checkShopNo(notice.getShopNo()), "shopNo验证未通过");
		Notice oldNotice = this.getNoticeByShopNo(notice.getShopNo());
		if (null != oldNotice) {
			Notice newNotice = new Notice();
			newNotice.setId(oldNotice.getId());
			newNotice.setStatus(Constants.UNVALID);
			noticeService.unvalidNotice(newNotice);
		}
		return noticeService.addNotice(notice);
	}

	/**
	 * 
	 * @Title: checkShopNo 
	 * @Description: 店铺合法性校验
	 * @param shopNo
	 * @return
	 * @date 2015年9月21日 下午4:13:58  
	 * @author cbc
	 */
	private boolean checkShopNo(String shopNo) {
		Shop shop = shopService.getShopByNo(shopNo);
		return (null != shop && shop.getStatus()>=Constants.VALID);
	}

	@Override
	public Notice getNoticeByShopNo(String shopNo) {
		AssertUtil.notNull(shopNo, "shopNo不能为空");
		return noticeService.getNoticeByShopNo(shopNo);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void setNotice(Long userId, Notice notice, Integer isNotice) {
		AssertUtil.notNull(userId, "userId不能为空");
		AssertUtil.notNull(notice, "notice不能为空");
		AssertUtil.notNull(isNotice, "isNotice不能为空");
		AssertUtil.isTrue(Shop.checkIsNotice(isNotice), "isNotice校验未通过");
		Shop shop = shopService.getShopByUserId(userId);
		Shop newShop = new Shop();
		newShop.setNo(shop.getNo());
		newShop.setIsNotice(isNotice);
		shopService.editShop(newShop);
		notice.setShopNo(shop.getNo());
		this.addNotice(notice);
	}

}
