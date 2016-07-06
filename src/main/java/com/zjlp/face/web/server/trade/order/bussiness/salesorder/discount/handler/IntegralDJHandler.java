package com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.AbstractOIDiscountHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderGenerate;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderItemDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice.RatioTransform;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice.SupportRule;

/**
 * 积分抵价处理类
 * 
 * @ClassName: IntegralHandler
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年9月23日 上午10:04:22
 */
public class IntegralDJHandler extends AbstractOIDiscountHandler {

	// 用于抵价的积分
	private BigDecimal integral;
	// 活动规则
	private MarketingActivityDetail detail;
	// 抵价算法
	private SupportRule supportRule;
	// 比例算法 1 ： 100
	private RatioTransform transform;

	public IntegralDJHandler(Long integral, MarketingActivityDetail detail, 
			SupportRule supportRule) {
		if (null != detail) {
			log.writeLog("积分抵价优惠活动启动！");
			this.integral = BigDecimal.valueOf(integral);
			this.supportRule = supportRule;
			this.detail = detail;
			this.transform = new RatioTransform(detail.getPremiseVal(), detail.getResultVal());
		}
	}

	@Override
	protected Object excute(Object param) {

		log.writeLog("开始处理积分抵价处理类：可用于抵价的积分个数为：", integral);

		Assert.isTrue(param instanceof OrderGenerate, "param must be an OrderGenerate.");

		OrderGenerate order = (OrderGenerate) param;
		List<OrderItemDetail> detailList = order.getItemDetailList();
		
		Long price = this.caluIntegralPrice(detailList);

		// 初始化抵价规则实例
		this.initSupportRule(detailList, price);

		// 抵价计算
		supportRule.excute();

		// 处理订单价格以及优惠券抵价情况
		this.dealOrderPrice(detailList, supportRule.getPriceMap(),
				supportRule.getSupportMap());

		log.writeLog("处理积分抵价处理类（不可合并）结束。");
		
		return param;
	}

	private Long caluIntegralPrice(List<OrderItemDetail> detailList) {
		
		BigDecimal consumPrice = BigDecimal.ZERO;
		Long subDiscount = null;
		for (OrderItemDetail orderItemDetail : detailList) {
			if (null == orderItemDetail) continue;
			subDiscount = orderItemDetail.getCachePrice();
			consumPrice = consumPrice.add(BigDecimal.valueOf(subDiscount));
		}
		
		BigDecimal conditionDecimal = new BigDecimal(detail.getPremiseVal());//条件
		BigDecimal resultDecimal = new BigDecimal(detail.getResultVal());//结果
		BigDecimal maxRateDecimal = new BigDecimal(detail.getMaxVal());//最大抵扣比例
		log.writeLog("当前匹配的积分送规则为：", conditionDecimal.longValue(), "积分抵扣", 
				resultDecimal.longValue(), "分，最大可抵扣比例", maxRateDecimal.longValue(), "%");
		/** 计算优惠最大价格     公式： （ 消费总额/最大抵扣比例） */
		Long maxPrice = consumPrice.multiply(maxRateDecimal.divide(new BigDecimal(100))).longValue();
		log.writeLog("本次消费最大可抵扣金额为", maxPrice, "分");

		/** 计算积分抵扣的优惠价格     公式： （ 用户积分数/积分条件）*积分结果 */
		Long deductionPrice = integral.divide(conditionDecimal).multiply(resultDecimal).longValue();
		if (deductionPrice.longValue() > maxPrice.longValue()) {
			deductionPrice = maxPrice;
		}
		/** 计算已抵扣的积分数     公式： （积分抵扣的优惠价格/积分结果）*积分条件) */
		Long integral = new BigDecimal(deductionPrice).divide(resultDecimal).multiply(conditionDecimal).longValue();
		log.writeLog("当前使用", integral, "积分抵扣的优惠价格为", deductionPrice, "分");
		return deductionPrice;
		
	}

	private void dealOrderPrice(List<OrderItemDetail> detailList,
			Map<Long, BigDecimal> priceMap, Map<Long, BigDecimal> supportMap) {

		Long skuId = null;
		BigDecimal discount = null;
		BigDecimal integralPrice = null;
		for (OrderItemDetail detail : detailList) {

			if (null == detail) continue;
			skuId = detail.getGoodSku().getId();
			log.writeLog("Sku[Id=", skuId, "] 积分抵价前的价格为：", detail.getCachePrice());
			discount = priceMap.get(skuId);
			integralPrice = supportMap.get(skuId);

//			detail.withDiscountPrice(discount.longValue());
			detail.setCachePrice(discount.longValue());
			detail.setIntegral(transform.getVirValue(integralPrice.longValue()));
			detail.setIntegralPrice(integralPrice.longValue());
			detail.setIntegralRate(100L);
			log.writeLog("Sku[Id=", skuId, "] 积分抵价的价格为：", detail.getCachePrice(), ", 使用积分个数为：",
					detail.getIntegral(), "， 抵价比例为 1 ： 100， 抵价价格为：", detail.getIntegralPrice());
		}
	}

	private void initSupportRule(List<OrderItemDetail> detailList, Long price) {

		this.supportRule.withSupportPrice(price);

		for (OrderItemDetail detail : detailList) {
			if (null == detail)
				continue;
			Assert.notNull(detail.getGoodSku());
			Assert.notNull(detail.getCachePrice());
			supportRule.addPriceById(detail.getGoodSku().getId(),
					detail.getCachePrice());
		}

	}

	@Override
	protected boolean check(Object param) {
		boolean cando = super.check(param);
		if (null == integral) {
			cando = false;
			log.writeLog("未设置参与抵价的积分，该单未参与积分抵价活动！");
		}
		if (null == supportRule) {
			cando = false;
			log.writeLog("未设置抵价规则，该单未参与积分抵价活动！");
		}
		if (null == detail || null == detail.getPremiseVal()
				|| 0 > detail.getPremiseVal().intValue()
				|| null == detail.getResultVal()
				|| 0 > detail.getResultVal().intValue()
				|| null == detail.getMaxVal()
				|| 1 > detail.getResultVal().intValue()) {
			cando = false;
			log.writeLog("该店铺积分抵扣规则不完整");
		}
		return cando;
	}

	@Override
	public String toString() {
		return "IntegralDJHandler [积分抵价处理类]";
	}

}
