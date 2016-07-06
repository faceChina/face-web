package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;

/**
 * 具有特定佣金率的商品的设置
 * @ClassName: PluAgencyRateSet 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月26日 上午9:15:02
 */
public class PluAgencyRateSet extends DefaultAgencyRateSet implements PluRateAble<Long> {

	private Map<Long, BigDecimal> pluSet = null;
	
	private BigDecimal commonRate = BigDecimal.ZERO;
	
	public PluAgencyRateSet(Subbranch subbranch) {
		super(subbranch);
	}
	
	@Override
	public BigDecimal getRate() {
		return commonRate;
	}

	@Override
	public BigDecimal getRateBy(Long key) {
		BigDecimal rate = null;
		
		if (null == pluSet || pluSet.isEmpty()) {
			log.writeLog("PluSet is empty, no plu rate exists.");
			rate = getRate();
			//log.writeLog("商品未设置特定佣金率的商品,返回固定佣金比率：",rate);
		}else{
			if (null == (rate = pluSet.get(key))) {
				rate = getRate();
				log.writeLog("商品[ID=", key , "]不支持单品佣金分成，返回固定佣金比率：", rate);
			} else {
				log.writeLog("商品[ID=", key , "]支持单品佣金分成，单品佣金比率：", rate);
			}
		}
		return rate;
	}

	/**
	 * 追加特定单品佣金率设置
	 * @Title: addPluSet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @param rate 佣金率
	 * @return
	 * @date 2015年9月26日 上午9:19:30  
	 * @author lys
	 */
	public PluAgencyRateSet addPluSet(Long id, BigDecimal rate) {
		if (null == pluSet) {
			pluSet = new HashMap<Long, BigDecimal>();
		}
		pluSet.put(id, rate);
		log.writeLog("单品佣金率[id=", id, ", rate=", rate, "]添加成功");
		return this;
	}
	
	public PluAgencyRateSet addPluSet(Long id, Integer rate) {
		this.addPluSet(id, BigDecimal.valueOf(rate));
		return this;
	}

	public Map<Long, BigDecimal> getPluSet() {
		return pluSet;
	}

	public void setCommonRate(BigDecimal commonRate) {
		this.commonRate = commonRate;
	}
	
	public void setCommonRate(Integer commonRate) {
		this.commonRate = BigDecimal.valueOf(commonRate);
	}
	
	public BigDecimal getCommonRate() {
		return commonRate;
	}
	
}
