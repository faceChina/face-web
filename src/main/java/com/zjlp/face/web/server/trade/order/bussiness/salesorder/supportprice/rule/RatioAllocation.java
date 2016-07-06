package com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice.rule;

import java.math.BigDecimal;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice.AbstractSupportRule;

/**
 * 按比例进行分配
 * 
 * <p>
 * 1.遇到精度问题时，将往后迁移
 * 
 * @ClassName: RatioAllocation 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月22日 上午11:55:24
 */
public class RatioAllocation extends AbstractSupportRule {
	
	@Override
	protected void support() throws RuntimeException {
		//检查参数
		Assert.notEmpty(super.priceMap);
		//如果分配价格和总价格相等,则全部分配
		if (super.supportPrice.equals(super.totalPrice)) {
			super.supportMap = super.priceMap;
			return;
		}
		//循环计算并累计lessPrice
		//当前价格
		BigDecimal price = null;
		//分成比例  = price * supportPrice / totalPrice
		BigDecimal divideRate = null;
		//收集因精度问题实际上第一次低价的总额
		BigDecimal sumPrice = BigDecimal.ZERO;
		for (Long id : super.priceMap.keySet()) {
			price = super.priceMap.get(id);
			divideRate = price.multiply(supportPrice).divide(totalPrice, BigDecimal.ROUND_DOWN);
			price = price.subtract(divideRate);
			sumPrice = sumPrice.add(divideRate);
			supportMap.put(id, divideRate);
			priceMap.put(id, priceMap.get(id).subtract(divideRate));
		}
		//如果还有剩余,进行循环分配
		if (!sumPrice.equals(supportPrice)) {
			BigDecimal lessPrice = supportPrice.subtract(sumPrice);
			BigDecimal swapPrice = null;
			for (Long id : super.priceMap.keySet()) {
				price = super.priceMap.get(id);
				if (BigDecimal.ZERO.compareTo(price) >= 0) {
					continue;
				}
				swapPrice = lessPrice.compareTo(price) >= 0 ? price : lessPrice;
				price = price.subtract(swapPrice);
				supportMap.put(id, supportMap.get(id).add(swapPrice));
				priceMap.put(id, priceMap.get(id).subtract(swapPrice));
				lessPrice = lessPrice.subtract(swapPrice);
				if (BigDecimal.ZERO.compareTo(swapPrice) >= 0) {
					break;
				}
			}
		}
	}

}
