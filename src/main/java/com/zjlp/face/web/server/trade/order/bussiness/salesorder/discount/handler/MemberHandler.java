package com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.handler;

import java.util.List;

import org.springframework.util.Assert;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.AbstractOIDiscountHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderGenerate;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderItemDetail;

/**
 * 会员打折处理类
 * 
 * <p>
 * 
 * 该处理类处理的是会员打折的业务，根据每件商品的单价进行会员价进行折算
 * 
 * @ClassName: MemberHandler 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月23日 上午10:01:51
 */
public class MemberHandler extends AbstractOIDiscountHandler {
	
	private static final Integer MIN = 0;
	private static final Integer MAX = 100;
	private Integer rate = null;
	
	public MemberHandler(Integer rate) {
		this.rate = rate;
	}

	@Override
	protected Object excute(Object param) {
		
		log.writeLog("开始处理打折优惠活动：打折折扣率rate=", rate, "%");
		
		Assert.notNull(param, "param can't be null.");
		Assert.isTrue(param instanceof OrderGenerate, "param must be an OrderGenerate.");
		
		OrderGenerate order = (OrderGenerate) param;
		List<OrderItemDetail> detailList = order.getItemDetailList();
		
		GoodSku goodSku = null;
		Long price = null;
		Long discountPrice = null;
		Long singleMDiscount = null;  //单件减免价格
		Long quantity = null;
		for (OrderItemDetail detail : detailList) {
			if (null == detail) continue;
			
			goodSku = detail.getGoodSku();
			Assert.notNull(goodSku, "OrderItemDetail's goodSku can't be null.");
			price = goodSku.getSalesPrice();
			quantity = detail.getQuantity();
			
			if (!Constants.PRICE_PREFERENTIAL.equals(goodSku.getPreferentialPolicy())) {
				discountPrice = price;
				log.writeLog("GoodSku[ id=", goodSku.getId(), "] 不参与会员折扣！返回原价：", discountPrice);
			} else {
				discountPrice = CalculateUtils.getDiscountPrice(price, rate.intValue());
				log.writeLog("GoodSku[ id=", goodSku.getId(), "] 原价：", price, "会员价为:", discountPrice);
			}
			//会员价格
			detail.withDiscountPrice(discountPrice);
			detail.setCachePrice(CalculateUtils.get(discountPrice, quantity));
			//打折减免的价格
			singleMDiscount = price - discountPrice;
			detail.setMemberDiscountPrice(CalculateUtils.get(singleMDiscount, quantity));
			log.writeLog("会员打折优惠活动减免的价格为：", detail.getMemberDiscountPrice(),
					"，其中商品件数为：", quantity, "，单件商品减免价格为：", singleMDiscount);
		}
		
		log.writeLog("处理打折优惠活动结束!");
		
		return param;
	}
	
	@Override
	protected boolean check(Object param) {
		boolean cando = super.check(param);
		if (null == rate) {
			cando = false;
			log.writeLog("没有设置折扣率，该单不参与会员打折优惠活动！");
		}
		Assert.isTrue(MIN.compareTo(rate) <= 0 
				&& MAX.compareTo(rate) >= 0 , "rate is out of the range[0, 100]!");
		return cando;
	}

	@Override
	public String toString() {
		return "MemberHandler [会员打折处理类]";
	}

}
