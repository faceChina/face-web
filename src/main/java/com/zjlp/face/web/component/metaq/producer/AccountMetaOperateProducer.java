package com.zjlp.face.web.component.metaq.producer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.jzwgj.metaq.vo.AccountMessage;
import com.jzwgj.metaq.vo.Topic;
import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.component.metaq.MetaAnsyHelper;
import com.zjlp.face.web.component.metaq.log.MetaLog;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
import com.zjlp.face.web.server.trade.payment.service.CommissionRecordService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.util.constantsUtil.ConstantsUtil;

/**
 * 钱包推送
 * 
 * @author Baojiang Yang
 * @date 2015年7月21日 下午4:47:13
 *
 */
@Component
public class AccountMetaOperateProducer {

	private Logger _logger = Logger.getLogger(this.getClass());

	@Autowired
	private ShopService shopService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private MetaQProviderClinet metaQProviderClinet;
	@Autowired
	private CommissionRecordService commissionRecordService;

	public void senderAnsy(String orderNo, Integer type) {
		AssertUtil.notNull(orderNo, "订单号不能为空!");
		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + orderNo + "==========" + Thread.currentThread().getName());
			// 推送开关
			String switchProducer = PropertiesUtil.getContexrtParam("SWITCH_METAQ_PRODUCER_ORDER");
			if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
				_logger.warn(MetaLog.getString("Switch.Order"));
				return;
			}
			executor = Executors.newSingleThreadExecutor();
			AccountMessage accountMessage = new AccountMessage();
			List<CommissionRecord> recordList = this.commissionRecordService.findByOrderNo(orderNo);
			AssertUtil.notEmpty(recordList, "收益分成相关人不存在.");
			for (CommissionRecord current : recordList) {
				AssertUtil.notNull(current.getAccountId(), "没有找到账户.");
				AssertUtil.notNull(current.getCommission(), "收益为空，无需操作.");
				/** 如果到账未0元,无需PUSH,苗青提 **/
				if (current.getCommission() == 0) {
					return;
				}
				Account account = this.accountService.getAccountById(current.getAccountId());
				AssertUtil.notNull(account, "账户不存在.");
				AssertUtil.notNull(account.getRemoteId(), "外部ID不存在.");
				AssertUtil.notNull(account.getRemoteType(), "外部ID类型不存在.");
				/**remoteType为1时,remoteId就是userId remoteType为2时,remoteId是shopNo*/
				if (1 == account.getRemoteType().intValue()) {
					Long userId = Long.parseLong(account.getRemoteId().toString());
					accountMessage.setUserId(userId);
					List<Subbranch> subList = this.subbranchService.findByUserId(userId);
					if (CollectionUtils.isNotEmpty(subList) && subList.size() == 1) {
						accountMessage.setSubName(subList.get(0).getShopName());
						Shop shop = this.shopService.getShopByNo(subList.get(0).getSupplierShopNo());
						if (shop != null) {
							accountMessage.setShopName(shop.getName());
						}
					} else {
						_logger.error("店铺不存在或者多个店铺，脏数据");
						return;
					}
				} else if (2 == account.getRemoteType().intValue()) {
					Shop shop = this.shopService.getShopByNo(account.getRemoteId());
					if (shop != null) {
						accountMessage.setUserId(shop.getUserId());
						accountMessage.setShopName(shop.getName());
					}
				} else {
					return;
				}
				accountMessage.setCommission(current.getCommission());// 收益
				accountMessage.setType(type);// 消息类型
				accountMessage.setDescription(new StringBuilder("您有一笔资金").append(CalculateUtils.converFenToYuan(current.getCommission())).append("元已经到账，快去钱包看看吧").toString());
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				accountMessage.setPushTime(format.format(new Date()));
				accountMessage.setUserType(ConstantsUtil.U_ADMIN);
				accountMessage.setLink(ConstantsUtil.LINK_MY_WALLET);// 点击跳转我的钱包
				executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.DEALTOPIC, accountMessage.toJson()));
			}
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
