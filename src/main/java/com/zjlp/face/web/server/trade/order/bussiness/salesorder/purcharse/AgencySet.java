package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

@Deprecated
public class AgencySet implements Serializable {
	
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
	private List<AgencySet> childs;
	private AgencySet parent;
    public AgencySet(Long subbranchId, String supplier, String supplierNick, 
			String purchaser, String purchaserNick, Integer rate){
    	this.subbranchId = subbranchId;
    	this.supplier = supplier;
		this.supplierNick = supplierNick;
		this.purchaser = purchaser;
		this.purchaserNick = purchaserNick;
		this.rate = new BigDecimal(rate);
		this.realRate = new BigDecimal(rate);
    }
	public AgencySet addChild(Long subbranchId, String supplier, String supplierNick, 
			String purchaser, String purchaserNick, Integer rate){
		Assert.notNull(supplier);
		Assert.notNull(purchaser);
		Assert.notNull(rate);
		AgencySet child = new AgencySet(subbranchId, supplier, supplierNick, purchaser, purchaserNick, rate);
		child.parent = this;
		child.leve = this.leve+1;
		child.realRate = realRate.multiply(new BigDecimal(rate)).divide(BIGDECIMAL_100);
		if (null == childs) {
			childs = new ArrayList<AgencySet>();
		}
		childs.add(child);
		return child;
	}
	public AgencySet getRoot(){
		AgencySet root = this;
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
	public List<AgencySet> getChilds() {
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
	public AgencySet getParent() {
		return parent;
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
	
	
}
