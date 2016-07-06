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
import org.springframework.util.Assert;

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
 * 代理分配金额场景
* @ClassName: ProxyDistributeScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年5月12日 下午2:09:51 
*
 */
@Component("proxyDistributeScene")
public class ProxyDistributeScene implements DistributeScene {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccountProducer accountProducer;
	
	@Autowired
	private CommissionRecordService commissionRecordService;
	
	@Autowired
	private PurchaseOrderProducer purchaseOrderProducer;
	
	@Autowired
	private DistributeScene defaultDistributeScene;
	
	/**
	 * 根据SalesOrder 的shopNo 一步一步往上计算，直到没有供货商，那么他就是供货商
	 * 分销商利润 = 出货价 - 采购价
	 * 供货商利润 = 出货价 - 手续费 + 运费
	 * 最多三级 bf2 --> bf1 --> p
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public void distributeCalculation(SalesOrder salesOrder, FeeDto orderFee,Long amount,Date date)
			throws PayException {
		AssertUtil.notNull(salesOrder, "订单对象为空");
		AssertUtil.notNull(orderFee, "订单手续费对象为空");
		AssertUtil.notNull(amount, "金额为空");
		try {
			String baseLog = new StringBuffer("订单[").append(salesOrder.getOrderNo()).append("]分配推广佣金，").toString();
			//采购订单
			Map<String,PurchaseOrder> map = _getPurchaseOrderMap(salesOrder.getOrderNo());
			//店销推广过来的
			if(null == map || map.isEmpty()){
				//走默认场景
				log.info(new StringBuffer(baseLog).append("当前从店销推广过来，接下来按照普通场景走").toString());
				defaultDistributeScene.distributeCalculation(salesOrder, orderFee, amount, date);
				return;
			}
			//分销的
			_recursive(map,salesOrder.getShopNo(),salesOrder, orderFee,amount,date);
		} catch (Exception e) {
			throw new PayException("代理分配金额场景发生异常",e);
		}
	}
	
	/**
	 * 递归出每一级代理关系
	 * @Title: _recursive
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param map
	 * @param shopNo 采购商
	 * @param salesOrder
	 * @param orderFee
	 * @param amount
	 * @param date
	 * @throws PayException
	 * @return void
	 * @author phb
	 * @date 2015年5月13日 下午1:57:43
	 */
	private void _recursive(Map<String,PurchaseOrder> map,String shopNo,SalesOrder salesOrder, FeeDto orderFee,Long amount,Date date) throws PayException{
		Assert.notEmpty(map);
		Assert.hasLength(shopNo);
		try {
			String baseLog = new StringBuffer("订单[").append(salesOrder.getOrderNo()).append("]分配推广佣金，").toString();
			
			PurchaseOrder po = map.get(shopNo);
			//没有采购单了，当前shopNo 为供货商
			if(null == po){
				//走默认场景
				log.info(new StringBuffer(baseLog).append("分销关系计算完成，下面计算供货商利益，供货商：").append(shopNo).toString());
				SalesOrder order = (SalesOrder) salesOrder.clone();
				order.setShopNo(shopNo);//供货商店铺
				defaultDistributeScene.distributeCalculation(order, orderFee, amount,date);
				return;
			}
			log.info(new StringBuffer(baseLog).append("采购商：").append(shopNo).append(" 供应商：").append(po.getSupplierNo())
					.append(" 采购价：").append(po.getTotalPurchasePrice())
					.append(" 出货价：").append(po.getTotalSalesPrice())
					.append(" 总利润：").append(po.getTotalProfitPrice())
					.toString());
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
			cr.setType(4);//分销利益
			cr.setIsToType(1);
			cr.setCreateTime(date);
			cr.setUpdateTime(date);
			commissionRecordService.addCommissionRecord(cr);
			log.info(new StringBuffer(baseLog).append("本条记录完成").toString());
			
			//剩余的钱
			log.info(new StringBuffer(baseLog).append("分配推广佣金前总额：").append(amount).toString());
			amount = CalculateUtils.getDifference(amount,po.getTotalProfitPrice());
			log.info(new StringBuffer(baseLog).append("分配推广佣金后总额：").append(amount).toString());
			
			//自己调用自己
			_recursive(map,po.getSupplierNo(),salesOrder, orderFee, amount,date);
		} catch (Exception e) {
			throw new PayException("代理分配金额场景发生异常",e);
		}
	}
	
	/**
	 * 获取订单对应采购单 map
	 * 				采购商为		key
	 * 				采购订单为 	value
	 * @Title: getPurchaseOrderMap
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNo
	 * @return
	 * @throws PayException
	 * @return Map<String,PurchaseOrder>
	 * @author phb
	 * @date 2015年5月13日 上午11:38:03
	 */
	private Map<String,PurchaseOrder> _getPurchaseOrderMap(String orderNo)throws PayException{
		try {
			Map<String,PurchaseOrder> map = new HashMap<String, PurchaseOrder>();
			List<PurchaseOrder> list = purchaseOrderProducer.findPurchaseOrderList(orderNo, 1);
			if(null == list){
				return null;
			}
			for (PurchaseOrder purchaseOrder : list) {
				map.put(purchaseOrder.getPurchaserNo(), purchaseOrder);
			}
			return map;
		} catch (Exception e) {
			throw new PayException("获取订单对应采购单 map 发生异常",e);
		}
	}

}
