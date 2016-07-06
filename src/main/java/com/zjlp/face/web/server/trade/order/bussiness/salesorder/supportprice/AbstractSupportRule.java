package com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.zjlp.face.web.util.PrintLog;

/**
 * 计算模板方法
 * @ClassName: AbstractSupportRule 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月22日 上午11:52:17
 */
public abstract class AbstractSupportRule implements SupportRule {
	
	protected static PrintLog log = new PrintLog();
	//提供抵价的总价
	protected BigDecimal supportPrice;
	//需要抵价的价格与ID键值对
	protected Map<Long, BigDecimal> priceMap = new HashMap<Long, BigDecimal>(5);
	//抵价多少键值对
	protected Map<Long, BigDecimal> supportMap = new HashMap<Long, BigDecimal>(5);
	//用于抵价的总价格
	protected BigDecimal totalPrice = BigDecimal.ZERO;
	
	public void addPriceById(Long goodId, Long price){
		Assert.notNull(goodId, "goodId can't be null.");
		Assert.notNull(price, "price can't be null.");
		if (priceMap.containsKey(goodId)) {
			log.writeLog("key-value by goodId=", goodId, " is already exists.");
			Assert.isTrue(false);
		}
		log.writeLog("key-value is add: goodId=", goodId, ", price=", price);
		
		BigDecimal priceB = BigDecimal.valueOf(price);
		//需付价格集合初始化
		priceMap.put(goodId, priceB);
		//抵价集合初始化
		supportMap.put(goodId, BigDecimal.ZERO);
		//累计总价格
		totalPrice = totalPrice.add(priceB);
	}
	
	@Override
	public void withSupportPrice(Long supportPrice) {
		this.supportPrice = BigDecimal.valueOf(supportPrice);
	}

	@Override
	public void excute() throws RuntimeException {
		
		//检验参数
		this.check();
		
		//计算分配
		this.support();
		
		//结果判断
		this.checkResult();
	}
	
	protected void check() {
		Assert.notNull(supportPrice, "supportPrice can't be null.");
		//校验规则：抵价不能大于总价格
		boolean isGt = totalPrice.compareTo(supportPrice) >= 0;
		if (!isGt) {
			log.writeLog("totalPrice=", totalPrice ," is less than supportPrice=",
					supportPrice, ", can't support.");
		}
		Assert.isTrue(isGt, "totalPrice is less than supportPrice, can't support.");
	}
	
	/**
	 * 计算分配
	 * @Title: support 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @throws RuntimeException
	 * @date 2015年9月22日 上午11:53:45  
	 * @author lys
	 */
	protected abstract void support() throws RuntimeException;
	
	/**
	 * 结果校验
	 * @Title: checkResult 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @throws RuntimeException
	 * @date 2015年9月22日 上午11:53:25  
	 * @author lys
	 */
	protected void checkResult() {
		//default do nothing.
	}

	@Override
	public Map<Long, BigDecimal> getPriceMap() {
		return this.priceMap;
	}

	@Override
	public Map<Long, BigDecimal> getSupportMap() {
		return this.supportMap;
	}

}
