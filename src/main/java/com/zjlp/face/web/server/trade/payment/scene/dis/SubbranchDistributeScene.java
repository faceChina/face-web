package com.zjlp.face.web.server.trade.payment.scene.dis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.operation.subbranch.domain.OweRecord;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.operation.subbranch.service.OweRecordService;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.producer.PurchaseOrderProducer;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
import com.zjlp.face.web.server.trade.payment.service.CommissionRecordService;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.producer.UserProducer;

/**
 * 分店分成场景
* @ClassName: SubbranchDistributeScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zyl 
* @date 2015年6月24日 下午2:09:51 
*
 */
@Component("subbranchDistributeScene")
public class SubbranchDistributeScene implements DistributeScene {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccountProducer accountProducer;
	
	@Autowired
	private CommissionRecordService commissionRecordService;
	
	@Autowired
	private PurchaseOrderProducer purchaseOrderProducer;
	
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private UserProducer userProducer;
	@Autowired
	private SubbranchProducer subbranchProducer;
	@Autowired
	private OweRecordService oweRecordService;
	@Autowired
	private SalesOrderProducer salesOrderProducer;
	
	/**
	 * 1.采购单从高到低排序
	 * 2.先计算总店分成
	 * 3.再循环计算分店分成
	 */
	@Deprecated
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public void distributeCalculation_backup(SalesOrder salesOrder, FeeDto orderFee,Long amount,Date date)
			throws PayException {
		AssertUtil.notNull(salesOrder, "订单对象为空");
		AssertUtil.notNull(orderFee, "订单手续费对象为空");
		AssertUtil.notNull(amount, "金额为空");
		try {
			List<PurchaseOrder> list = purchaseOrderProducer.findPurchaseOrderList(salesOrder.getOrderNo(), 1);
			AssertUtil.notEmpty(list, "订单数据异常"+salesOrder.getOrderNo());
			List<OrderItem> itemList=salesOrderProducer.getOrderItemListByOrderNo(salesOrder.getOrderNo());
			Long price=0l;
			for(OrderItem oi:itemList){
				price+=CalculateUtils.get(oi.getPrice(), oi.getQuantity());
			}
			Map<Long,Subbranch> map=new HashMap<Long,Subbranch>();
			Long subAmount=0l;
			for(PurchaseOrder po:list){
//				User subordinateUser=userProducer.getUserByUserName(po.getPurchaserNo());
				Subbranch subbranch=subbranchProducer.findSubbranchById(po.getSubbranchId());
				AssertUtil.notNull(subbranch, CErrMsg.NULL_RESULT.getErrCd(), 
						CErrMsg.NULL_RESULT.getErrorMesage(), "分店查询");
				map.put(po.getSubbranchId(), subbranch);
				subAmount+=po.getTotalProfitPrice();
			}
			/** 采购单从高到低排序*/
			for(int i=0;i<list.size()-1;i++){
				for(int j=i+1;j<list.size();j++){
					Subbranch subbranch=map.get(list.get(j).getSubbranchId());
					if(i==0){
						if(subbranch.getPid().longValue()==0l){
							PurchaseOrder po=list.get(i);
							list.set(0, list.get(j));
							list.set(j, po);
						}
					}else{
						if(subbranch.getPid().longValue()==map.get(list.get(i).getSubbranchId()).getId()){
							if(j!=i+1){
								PurchaseOrder po=list.get(i+1);
								list.set(i+1, list.get(j));
								list.set(j, po);
							}
						}
					}
				}
			}
			/** subAmount:下级应分金额*/
			/** subProfit:下级可分金额*/
			/** pProfit:总店可分金额*/
			/** isDeliverOfBf是否是Bf发货（true）*/
			Long pProfit=0l;
			Long subProfit=0l;
			boolean isDeliverOfBf = false;
			Long postFee = null == salesOrder.getPostFee() ? 0L : salesOrder.getPostFee();
			/** 是否由p来发货,邮费分配*/
			if(map.get( list.get(0).getSubbranchId()).getDeliver()==0){
				pProfit=price+postFee-subAmount-orderFee.getPayFee()-orderFee.getPlatformFee();
			    subProfit=amount-postFee-(price-subAmount);
			}else{
				pProfit=price-subAmount-orderFee.getPayFee()-orderFee.getPlatformFee();
				subProfit=amount-(price-subAmount)-postFee;
				isDeliverOfBf=true;
			}

			if(pProfit>amount){
				pProfit=amount;
			}
			subAmount=subAmount+salesOrder.getAdjustPrice();
			if(subProfit<0){
				subProfit=0l;
			}
			/** 总店分成*/
			Account shopAccount = accountProducer.getAccountByRemoteId(list.get(0).getSupplierNo(), Account.REMOTE_TYPE_SHOP);
			CommissionRecord cr = new CommissionRecord();
			cr.setAccountId(shopAccount.getId());
			cr.setCommission(pProfit);
			cr.setIsToAccount(0);
			cr.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
			cr.setOrderNo(salesOrder.getOrderNo());
			cr.setOrderStates(1);
			cr.setType(4);//总店分成
			cr.setIsToType(1);
			cr.setCreateTime(date);
			cr.setUpdateTime(date);
			commissionRecordService.addCommissionRecord(cr);
			/** 分店分成*/
			for(int i=0;i<list.size();i++){
				PurchaseOrder po=list.get(i);
				Subbranch subbranch=map.get(po.getSubbranchId());
				Account account = accountProducer.getAccountByRemoteId(subbranch.getUserId().toString(), Account.REMOTE_TYPE_USER);
				/** 生成欠款记录*/
				if(subProfit<subAmount){
					OweRecord or=new OweRecord();
					or.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
					or.setOrderNo(salesOrder.getOrderNo());
					or.setPurchaseOrderId(po.getId());
					or.setOweUserId(subbranch.getSuperiorUserId());
					or.setUserId(subbranch.getUserId());
					or.setPrice(subAmount);
					or.setPayPrice(subProfit);
					or.setOwePrice(subAmount-subProfit);
					or.setStatus(0);
					or.setCreateTime(date);
					or.setUpdateTime(date);
					oweRecordService.insertSelective(or);
				}
				/** 生成分钱记录*/
				if(subProfit>0){
					cr = new CommissionRecord();
					cr.setAccountId(account.getId());
					if(i==0&&isDeliverOfBf){
						cr.setCommission(subProfit>po.getTotalProfitPrice()?po.getTotalProfitPrice()+postFee:subProfit+postFee);
					}else{
						cr.setCommission(subProfit>po.getTotalProfitPrice()?po.getTotalProfitPrice():subProfit);
					}
					cr.setIsToAccount(0);
					cr.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
					cr.setOrderNo(salesOrder.getOrderNo());
					cr.setOrderStates(1);
					cr.setType(7);//BF分成
					cr.setIsToType(1);
					cr.setCreateTime(date);
					cr.setUpdateTime(date);
					commissionRecordService.addCommissionRecord(cr);
				}
				/** 计算下级可分金额*/
				if(i<list.size()-1){
					subProfit=subProfit-po.getTotalProfitPrice();
					if(subProfit<0){
						subProfit=0l;
					}
					subAmount=subAmount-po.getTotalProfitPrice();
				}
			}
		} catch (Exception e) {
			log.error("",e);
			throw new PayException("分店分配金额发生异常",e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public void distributeCalculation(SalesOrder salesOrder, FeeDto orderFee,Long amount,Date date)
			throws PayException {
		AssertUtil.notNull(salesOrder, "订单对象为空");
		AssertUtil.notNull(orderFee, "订单手续费对象为空");
		AssertUtil.notNull(amount, "金额为空");
		try {
			log.info("【结算开始。】");
			List<PurchaseOrder> list = purchaseOrderProducer.findPurchaseOrderList(salesOrder.getOrderNo(), 1);
			AssertUtil.notEmpty(list, "订单数据异常"+salesOrder.getOrderNo());
			//下级佣金总支出
			Map<Long,Subbranch> map = new HashMap<Long,Subbranch>();
			Long subAmount = this.calcuteZc(map, list);
			//采购单从高到低排序
			this.sort(list, map);
			//计算订单总价格
			Long price = this.calutePrice(salesOrder.getOrderNo());
			//发货权限
			boolean isBfRight = map.get(list.get(0).getSubbranchId()).getDeliver() == 1;
			log.info("【发货权限】该订单订单操作权限为"+(isBfRight ? "分店" : "总店"));
			//邮费
			Long postFee = null == salesOrder.getPostFee() ? 0L : salesOrder.getPostFee();
			log.info("【邮费】邮费="+postFee);
			//总店佣金分成记录
			Account shopAccount = accountProducer.getAccountByRemoteId(list.get(0).getSupplierNo(), Account.REMOTE_TYPE_SHOP);
			//总店利润 = 实付款 - 分成支出 - 手续费  [+ （邮费）]
			Long pProfit = this.calcutePProfit(subAmount, price, orderFee, isBfRight, postFee);
			/** 手续费依次从P,bf1,bf2,bf3扣取*/
			/** owe:应从下级扣取金额*/
			Long owe=0l;
			if(pProfit<0){
				owe=pProfit;
				log.info("orderNo:"+salesOrder.getOrderNo()+",owe:"+owe);
				pProfit=0l;
			}
			log.info("【总店利润】总店利润="+pProfit);
			AssertUtil.isTrue(pProfit >= 0, "供货商利润小于0，支付失败！");
			CommissionRecord cr =  this.createCommissionRecord(salesOrder.getOrderNo(), salesOrder.getTransactionSerialNumber(),
					date, pProfit, shopAccount.getId(), CommissionRecord.TYPE_4);
			commissionRecordService.addCommissionRecord(cr);
			//分店佣金分成记录
			for (PurchaseOrder purchaseOrder : list) {
				Subbranch subbranch = map.get(purchaseOrder.getSubbranchId());
				Account account = accountProducer.getAccountByRemoteId(subbranch.getUserId().toString(), Account.REMOTE_TYPE_USER);
				//佣金
				Long commission = this.caluteSubProfit(subbranch, purchaseOrder.getTotalProfitPrice(), postFee);
				commission+=owe;
				if(commission<0){
					owe=commission;
					log.info("orderNo:"+salesOrder.getOrderNo()+",owe:"+owe);
					commission=0l;
				}else{
					owe=0l;
				}
				log.info("【分店佣金信息】利益分成者：" + subbranch.getUserCell() + "，佣金：" + commission);
				AssertUtil.isTrue(commission >= 0, "供货商利润小于0，支付失败！");
				cr = this.createCommissionRecord(salesOrder.getOrderNo(), salesOrder.getTransactionSerialNumber(), 
						date, commission, account.getId(), CommissionRecord.TYPE_7);
				commissionRecordService.addCommissionRecord(cr);
			}
		} catch (Exception e) {
			log.error("分店分配金额发生异常", e);
			throw new PayException("分店分配金额发生异常", e);
		}
	}
	
	//下级分店佣金
	private Long caluteSubProfit(Subbranch subbranch, Long commission, Long postFee) {
		if (null != subbranch.getDeliver() && 1 == subbranch.getDeliver().intValue()) {
			commission = CalculateUtils.getSum(commission, postFee);
		}
		return commission;
	}

	//计算订单总价格
	private Long calutePrice(String orderNo) {
		List<OrderItem> itemList=salesOrderProducer.getOrderItemListByOrderNo(orderNo);
		SalesOrder salesOrder = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
		AssertUtil.notNull(salesOrder, "订单为空");
		AssertUtil.notEmpty(itemList, CErrMsg.NULL_RESULT.getErrCd(), 
				CErrMsg.NULL_RESULT.getErrorMesage(), "订单细项");
		Long price = 0L; //salesOrder.getPrice(); //考虑兼容
		Long integral = salesOrder.getIntegral(); 
		for(OrderItem oi:itemList){
			//price = CalculateUtils.getSum(price, oi.getPrice(), oi.getQuantity());
			price = CalculateUtils.getSum(price, oi.getDiscountPrice(), oi.getQuantity()); //会员优惠价
		}
		return price - integral;
	}

	//佣金总支出
	private Long calcuteZc(Map<Long, Subbranch> map, List<PurchaseOrder> list) {
		Long subAmount = 0L;
		for(PurchaseOrder po : list){
			Subbranch subbranch = subbranchProducer.findSubbranchById(po.getSubbranchId());
			AssertUtil.notNull(subbranch, CErrMsg.NULL_RESULT.getErrCd(), 
					CErrMsg.NULL_RESULT.getErrorMesage(), "分店查询");
			map.put(po.getSubbranchId(), subbranch);
			subAmount = CalculateUtils.getSum(subAmount, po.getTotalProfitPrice());
		}
		return subAmount;
	}
	
	//总店利润
	private Long calcutePProfit(Long subAmount, Long price,
			FeeDto orderFee, boolean isBfRight, Long postFee) {
		//分销佣金支出
		Long pProfit = CalculateUtils.getSum(price, subAmount * -1);
		//手续费
		pProfit = CalculateUtils.getSum(pProfit, orderFee.getPayFee() * -1);
		pProfit = CalculateUtils.getSum(pProfit, orderFee.getPlatformFee() * -1);
		//邮费
		if (!isBfRight) {
			pProfit = CalculateUtils.getSum(pProfit, postFee);
		}
		return pProfit;
	}

	//采购单从高到低排序
	private void sort(List<PurchaseOrder> list, Map<Long, Subbranch> map) {
		for(int i=0;i<list.size()-1;i++){
			for(int j=i+1;j<list.size();j++){
				Subbranch subbranch=map.get(list.get(j).getSubbranchId());
				if(i==0){
					if(subbranch.getPid().longValue()==0l){
						PurchaseOrder po=list.get(i);
						list.set(0, list.get(j));
						list.set(j, po);
					}
				}else{
					if(subbranch.getPid().longValue()==map.get(list.get(i).getSubbranchId()).getId()){
						if(j!=i+1){
							PurchaseOrder po=list.get(i+1);
							list.set(i+1, list.get(j));
							list.set(j, po);
						}
					}
				}
			}
		}
	}

	//佣金分成记录生成
	private CommissionRecord createCommissionRecord(String orderNo, String transcationNumber, Date date,
			Long commission, Long accountId, Integer type) {
		CommissionRecord cr = new CommissionRecord();
		cr.setAccountId(accountId);
		cr.setCommission(commission);
		cr.setIsToAccount(CommissionRecord.IS_TO_ACCOUNT);
		cr.setTransactionSerialNumber(transcationNumber);
		cr.setOrderNo(orderNo);
		cr.setOrderStates(Constants.VALID);
		cr.setType(type);//BF分成
		cr.setIsToType(CommissionRecord.TOPAYTYPE_USER_PURSE);
		cr.setCreateTime(date);
		cr.setUpdateTime(date);
		return cr;
	}
	
}
