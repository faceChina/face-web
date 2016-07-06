package com.zjlp.face.web.server.trade.payment.scene.dis;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.producer.PurchaseOrderProducer;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
import com.zjlp.face.web.server.trade.payment.service.CommissionRecordService;
/**
 * 员工分配金额场景
* @ClassName: EmployeeDistributeScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年5月19日 下午8:33:05 
*
 */
@Component("employeeDistributeScene")
public class EmployeeDistributeScene implements DistributeScene {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccountProducer accountProducer;
	
	@Autowired
	private PurchaseOrderProducer purchaseOrderProducer;
	
	@Autowired
	private CommissionRecordService commissionRecordService;
	
	@Autowired
	private DistributeScene popularizeDistributeScene;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public void distributeCalculation(SalesOrder salesOrder, FeeDto orderFee,
			Long amount, Date date) throws PayException {
		AssertUtil.notNull(salesOrder, "订单对象为空");
		AssertUtil.notNull(orderFee, "订单手续费对象为空");
		AssertUtil.notNull(amount, "金额为空");
		try {
			String baseLog = new StringBuffer("订单[").append(salesOrder.getOrderNo()).append("]分配员工佣金，").toString();
			
			List<PurchaseOrder> list = purchaseOrderProducer.findPurchaseOrderList(salesOrder.getOrderNo(), 3);
			AssertUtil.notEmpty(list, "没有找到推广采购单");
			AssertUtil.isTrue(list.size() == 1, "员工佣金采购单出现多条记录");
			PurchaseOrder po = list.get(0);
			
			//记录分销的待分佣金
			//获取商户钱包
			Account shopAccount = accountProducer.getAccountByRemoteId(po.getPurchaserNo(), Account.REMOTE_TYPE_SHOP);
			AssertUtil.notNull(shopAccount, "没有找到店铺钱包记录");
			//记录商户待获得收益
			CommissionRecord cr = new CommissionRecord();
			cr.setAccountId(shopAccount.getId());
			cr.setCommission(po.getTotalProfitPrice());
			cr.setIsToAccount(0);
			cr.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
			cr.setOrderNo(salesOrder.getOrderNo());
			cr.setOrderStates(1);
			cr.setType(6);//员工利益
			cr.setIsToType(1);
			cr.setCreateTime(date);
			cr.setUpdateTime(date);
			commissionRecordService.addCommissionRecord(cr);
			log.info(new StringBuffer(baseLog).append("本条记录完成").toString());
			
			//剩余的钱
			log.info(new StringBuffer(baseLog).append("分配推广佣金前总额：").append(amount).toString());
			amount = CalculateUtils.getDifference(amount,po.getTotalProfitPrice());
			log.info(new StringBuffer(baseLog).append("分配推广佣金后总额：").append(amount).toString());
			
			//走默认场景
			log.info(new StringBuffer(baseLog).append("当前从员工利益，进入全民推广利益").toString());
			popularizeDistributeScene.distributeCalculation(salesOrder, orderFee, amount, date);
		}catch(Exception e){
			throw new PayException("员工分配金额场景发生异常",e);
		}
	}

}
