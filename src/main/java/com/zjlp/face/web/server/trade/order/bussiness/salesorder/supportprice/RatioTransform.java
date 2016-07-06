package com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice;

import java.math.BigDecimal;

import org.springframework.util.Assert;


/**
 * 抵价物基类（不能接受精度丢失）
 * 
 * <p>
 * 1.第一序列单位为系统统一货币：人民币，基本单位：分
 * 
 * 2.第二序列单位为虚拟货币：自定义，基本单位：自定义
 * 
 * 3.该继承类应该定义其转化规则
 * 
 * @ClassName: SupportUnit 
 * 
 * @Description: (这里用一句话描述这个类的作用) 
 * 
 * @author lys
 * 
 * @date 2015年9月22日 下午3:49:04
 */
public class RatioTransform {
	
	//第一比例
	private BigDecimal systemRatio;
	//第二比例
	private BigDecimal virRatio;
	
	public RatioTransform(Integer systemRatio, Integer virRatio) {
		Assert.notNull(systemRatio, "systemRatio can't be null.");
		Assert.notNull(virRatio, "virRatio can't be null.");
		this.systemRatio = BigDecimal.valueOf(systemRatio);
		this.virRatio = BigDecimal.valueOf(virRatio);
	}
	
	public Long getSystemValue(Long virValue) {
		return systemRatio.multiply(BigDecimal.valueOf(virValue))
				.divide(virRatio).longValue();
	}
	
	public Long getVirValue(Long systemValue) {
		return virRatio.multiply(BigDecimal.valueOf(systemValue))
				.divide(systemRatio).longValue();
	}
	
}
