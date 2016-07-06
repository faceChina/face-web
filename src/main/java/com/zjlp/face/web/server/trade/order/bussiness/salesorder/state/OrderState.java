package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state;

public interface OrderState {

	/**
	 * 状态下执行
	 * @Title: execute 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @throws Exception
	 * @date 2015年6月25日 下午4:45:20  
	 * @author lys
	 */
	void execute() throws Exception;
	
	/**
	 * 订单状态集合
	 * @ClassName: States 
	 * @Description: (这里用一句话描述这个类的作用) 
	 * @author lys
	 * @date 2015年6月25日 下午4:49:55
	 */
	public enum States {
		
		/** 待生成 */
		WAIT_GENERATE(0),
		
		/** 待付款 */
		WAIT_PAY(1),
		
		/** 待发货 */
		WAIT_SEND(2),
		
		/** 待收货 */
		WAIT_RECIVE(3);
		
		private Integer status;
		private States(Integer status) {
			this.status = status;
		}
		public Integer getStatus(){
			return this.status;
		}
	}
	
	public enum Type {
		
		/** 普通订单 */
		ORDINARY(1),
		
		/** 预约订单 */
		APPOINT(3),
		
		/** 代理订单 */
		AGENCY(4);
		
		private Integer value;
		private Type(Integer value) {
			this.value = value;
		}
		public Integer getValue() {
			return value;
		}
		
	}
}
