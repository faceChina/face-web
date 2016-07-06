package com.zjlp.face.web.server.trade.payment.scene.dis;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.producer.PurchaseOrderProducer;
import com.zjlp.face.web.server.trade.payment.domain.PopularizeCommissionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
import com.zjlp.face.web.server.trade.payment.service.PopularizeCommissionRecordService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
/**
 * 推广分配金额场景
* @ClassName: PopularizeDistributeScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年5月12日 下午2:11:51 
*
 */
@Component("popularizeDistributeScene")
public class PopularizeDistributeScene implements DistributeScene {
	
	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private DistributeScene proxyDistributeScene;
	
	@Autowired
	private PopularizeCommissionRecordService popularizeCommissionRecordService;
	
	@Autowired
	private PurchaseOrderProducer purchaseOrderProducer;
	
	@Autowired
	private UserProducer userProducer;
	
	@Autowired
	private AccountProducer accountProducer;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public void distributeCalculation(SalesOrder salesOrder, FeeDto orderFee,Long amount,Date date)
			throws PayException {
		AssertUtil.notNull(salesOrder, "订单对象为空");
		AssertUtil.notNull(orderFee, "订单手续费对象为空");
		try {
			String baseLog = new StringBuffer("订单[").append(salesOrder.getOrderNo()).append("]分配推广佣金，").toString();
			//推广金额分配
			//根据订单号和 合作方式为推广 找到推广采购单
			List<PurchaseOrder> list = purchaseOrderProducer.findPurchaseOrderList(salesOrder.getOrderNo(), 2);
//			AssertUtil.notEmpty(list, "没有找到推广采购单");
			
			for (PurchaseOrder po : list) {
				AssertUtil.notNull(po, "推广采购单为空");
				AssertUtil.hasLength(po.getPurchaserNo(), "推广者手机号为空");
				AssertUtil.isTrue(po.getTotalProfitPrice() >= 0, "推广佣金异常");
				log.info(new StringBuffer(baseLog).append("采购订单验证完成").toString());
				
				//待分推广佣金记录
				PopularizeCommissionRecord record = new PopularizeCommissionRecord();
				//查看用户是否存在
				User user = userProducer.getUserByUserName(po.getPurchaserNo());
				if(null == user){
					record.setStatus(0);
					log.info(new StringBuffer(baseLog).append("当前推广人没有账户").toString());
				} else {
					record.setStatus(1);
					record.setUserId(user.getId());
					log.info(new StringBuffer(baseLog).append("当前推广人账户UserId：").append(user.getId()).toString());
				}
				record.setPopuCell(po.getPurchaserNo());
				record.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
				record.setOrderNo(salesOrder.getOrderNo());
				record.setOrderStatus(0);
				record.setCommission(po.getTotalProfitPrice());
				record.setUpdateTime(date);
				record.setCreateTime(date);
				popularizeCommissionRecordService.addRecord(record);
				log.info(new StringBuffer(baseLog).append("保存待分推广佣金记录完成").toString());
				
				//剩余的钱
				log.info(new StringBuffer(baseLog).append("分配推广佣金前总额：").append(amount).toString());
				amount = CalculateUtils.getDifference(amount,po.getTotalProfitPrice());
				log.info(new StringBuffer(baseLog).append("分配推广佣金后总额：").append(amount).toString());
			}
			
			//剩余的走代理场景 代理金额分配
			proxyDistributeScene.distributeCalculation(salesOrder, orderFee, amount,date);
		} catch (Exception e) {
			throw new PayException("推广分配金额场景发生异常",e);
		}
	}

}
