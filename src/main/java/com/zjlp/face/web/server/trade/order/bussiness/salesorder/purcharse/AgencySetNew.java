package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

@Deprecated
public class AgencySetNew implements Serializable,Cloneable {
	
	private static final long serialVersionUID = -8984320511372833943L;
	private static final BigDecimal BIGDECIMAL_100 = new BigDecimal(100);
	private Integer leve = 0;
	//分店绑定id
	private Long subbranchId;
	//上级分销商
	private String supplier;
	private String supplierNick;
	//下级分销商
	private String purchaser;
	private String purchaserNick;
	//对应上级利润的利率
	private BigDecimal rate = BIGDECIMAL_100;
	//计算用的利率(包含了下级所应该获取的利润)
	private BigDecimal realRate = BIGDECIMAL_100;
	private List<AgencySetNew> childs;
	private AgencySetNew parent;
	//(分店)单品(多商品时)对应的佣金率
	private Map<Object,Integer> mapRate;
	
	public AgencySetNew(Long subbranchId, String supplier, String supplierNick, 
			String purchaser, String purchaserNick, Integer rate,Map<Object,Integer> mapRate){
    	this.subbranchId = subbranchId;
    	this.supplier = supplier;
		this.supplierNick = supplierNick;
		this.purchaser = purchaser;
		this.purchaserNick = purchaserNick;
		this.rate = new BigDecimal(rate);
		this.realRate = new BigDecimal(rate);
		this.mapRate = new HashMap<Object, Integer>(mapRate);
    }

	public AgencySetNew addChild(Long subbranchId, String supplier, String supplierNick, 
			String purchaser, String purchaserNick, Integer rate,Map<Object,Integer> mapRate){
		Assert.notNull(supplier);
		Assert.notNull(purchaser);
		Assert.notNull(rate);
		AgencySetNew child = new AgencySetNew(subbranchId, supplier, supplierNick, purchaser, purchaserNick, rate,mapRate);
		child.parent = this;
		child.leve = this.leve+1;
		child.realRate = realRate.multiply(new BigDecimal(rate)).divide(BIGDECIMAL_100);
		if (null == childs) {
			childs = new ArrayList<AgencySetNew>();
		}
		childs.add(child);
		return child;
	}
	public AgencySetNew getRoot(){
		AgencySetNew root = this;
		while(root.leve.intValue() != 0) {
			root = root.getParent();
		}
		return root;
	}
	public Long getSubbranchId() {
		return subbranchId;
	}
	public Integer getLeve() {
		return leve;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getPurchaser() {
		return purchaser;
	}
	public List<AgencySetNew> getChilds() {
		return childs;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public String getSupplierNick() {
		return supplierNick;
	}
	public String getPurchaserNick() {
		return purchaserNick;
	}
	public BigDecimal getRealRate() {
		return realRate;
	}
	public boolean hasChilds(){
		return null != childs && !childs.isEmpty();
	}
	public boolean isRoot(){
		return 0 == this.leve.intValue();
	}
	public AgencySetNew getParent() {
		return parent;
	}
	public Map<Object, Integer> getMapRate() {
		return mapRate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public void setRealRate(BigDecimal realRate) {
		this.realRate = realRate;
	}
	@Override
	public String toString() {
		return new StringBuilder("AgencySet [leve=").append(leve)
				.append(", subbranchId=").append(subbranchId)
				.append(", supplier=").append(supplier).append(", supplierNick=")
				.append(supplierNick).append(", purchaser=")
				.append(purchaser).append(", purchaserNick=").append(purchaserNick)
				.append(", rate=").append(rate).append(", realRate=")
				.append(realRate).append(", childs=").append(childs)
				.append("]").toString();
	}

	@Override
	protected AgencySetNew clone() throws RuntimeException {
		try {
			return (AgencySetNew) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
