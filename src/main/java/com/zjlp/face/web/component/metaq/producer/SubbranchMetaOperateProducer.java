package com.zjlp.face.web.component.metaq.producer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.jzwgj.metaq.vo.SubbranchMessage;
import com.jzwgj.metaq.vo.Topic;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.component.metaq.MetaAnsyHelper;
import com.zjlp.face.web.component.metaq.log.MetaLog;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.util.Logs;
import com.zjlp.face.web.util.constantsUtil.ConstantsUtil;

/**
 * 分店推送消息
 * 
 * @author Baojiang Yang
 * @date 2015年7月9日 下午1:42:40
 *
 */
@Component
public class SubbranchMetaOperateProducer {

	private Logger _logger = Logger.getLogger(this.getClass());

	@Autowired
	private ShopService shopService;
	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private MetaQProviderClinet metaQProviderClinet;

	// type:10新分店加盟 11总店设置佣金 12 送店设置发货权限
	public void senderAnsy(Long subId, Integer type, Boolean isShopsSub) {
		AssertUtil.notNull(subId, "分店ID不能为空");
		AssertUtil.notNull(type, "消息类型不能为空");
		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + subId + "==========" + Thread.currentThread().getName());
			// 推送开关
			String switchProducer = PropertiesUtil.getContexrtParam("SWITCH_METAQ_PRODUCER_ORDER");
			if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
				_logger.warn(MetaLog.getString("Switch.Order"));
				return;
			}
			executor = Executors.newSingleThreadExecutor();
			SubbranchMessage subbranchMessage = new SubbranchMessage();
			Subbranch subbranch = this.subbranchService.findByPrimarykey(subId);
			AssertUtil.notNull(subbranch, "分店不存在！");
			subbranchMessage.setSubId(subId);// 分店ID
			subbranchMessage.setType(type);// 消息类型
			if (ConstantsUtil.PUSH_NEW_SUBBRANCH_MESSAGE == type) {
				subbranchMessage.setUserId(subbranch.getSuperiorUserId());// 推给总店用户消息
				subbranchMessage.setDescription(new StringBuilder("主人，您有新的分公司（手机").append(subbranch.getUserBindingCell()).append("）加盟，快去看看吧").toString());
				if (isShopsSub) {
					subbranchMessage.setLink(ConstantsUtil.LINK_MY_P_CHILD_SHOP);// 点击跳转总店的分店管理
				} else {
					subbranchMessage.setLink(ConstantsUtil.LINK_MY_BF_CHILD_SHOP);// 点击跳转分店分店管理
				}

			}
			if (ConstantsUtil.PUSH_PROFIT_MESSAGE == type) {
				subbranchMessage.setUserId(subbranch.getUserId());// 推给分店用户消息
				subbranchMessage.setDescription(new StringBuilder("东家已为您设置专属佣金为 ").append(subbranch.getProfit()).append("%，推广商品就可以获得丰厚佣金喽").toString());
				subbranchMessage.setLink(ConstantsUtil.LINK_MY_BF_SHOP);// 点击跳转分店
			}
			if (ConstantsUtil.PUSH_DELIVER_MESSAGE == type) {
				subbranchMessage.setUserId(subbranch.getUserId());// 推给分店用户消息
				subbranchMessage.setDescription(new StringBuilder("东家已").append(subbranch.getDeliver() == 1 ? "赋予您发货权限，记得在买家付款后及时发货哟" : "回收您的发货权限").toString());
				subbranchMessage.setLink(ConstantsUtil.LINK_MY_BF_SHOP);// 点击跳转分店
			}
			if (ConstantsUtil.PUSH_SUBBRANCH_FREEZE_SHOP_MESSAGE == type) {
				List<Shop> shopList=shopService.findShopListByUserId(subbranch.getUserId());
				String name=shopList.size()>0?shopList.get(0).getName():"";
				subbranchMessage.setUserId(subbranch.getUserId());// 推给分店用户消息
				subbranchMessage.setDescription("您的公司"+name+"，已被上级冻结自有店铺，除买家无法访问公司，其他暂不受影响！");
				subbranchMessage.setLink(ConstantsUtil.LINK_MY_P_SHOP);// 点击跳转总店
			}
			if (ConstantsUtil.PUSH_SUBBRANCH_UNFREEZE_SHOP_MESSAGE == type) {
				List<Shop> shopList=shopService.findShopListByUserId(subbranch.getUserId());
				String name=shopList.size()>0?shopList.get(0).getName():"";
				subbranchMessage.setUserId(subbranch.getUserId());// 推给分店用户消息
				subbranchMessage.setDescription("你的公司"+name+"，已被开启自有公司权限，赶紧去看看吧！");
				subbranchMessage.setLink(ConstantsUtil.LINK_MY_P_SHOP);// 点击跳转总店
			}
			Logs.print(subbranchMessage);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			subbranchMessage.setPushTime(format.format(new Date()));
			subbranchMessage.setUserType(ConstantsUtil.U_ADMIN);
			Subbranch sub = this.subbranchService.findByPrimarykey(subId);
			if (sub != null) {
				subbranchMessage.setSubName(sub.getShopName());
				Shop shop = this.shopService.getShopByNo(sub.getSupplierShopNo());
				if (shop != null) {
					subbranchMessage.setShopName(shop.getName());
				}
			}
			executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.DEALTOPIC, subbranchMessage.toJson()));
			_logger.info("分店消息推送完成.");
		} catch (Exception e) {
			_logger.error("消息推送失败！", e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}

}
