package com.zjlp.face.web.appvo;

import com.zjlp.face.web.server.product.good.domain.Good;

public class AssGood extends Good {
	
    /**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	//市场价
    private String marketPriceStr;
    //销售价
    private String salesPriceStr;
    
	public String getMarketPriceStr() {
		return marketPriceStr;
	}
	public void setMarketPriceStr(String marketPriceStr) {
		this.marketPriceStr = marketPriceStr;
	}
	public String getSalesPriceStr() {
		return salesPriceStr;
	}
	public void setSalesPriceStr(String salesPriceStr) {
		this.salesPriceStr = salesPriceStr;
	} 
    
    

}
