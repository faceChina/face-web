package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.zjlp.face.util.date.DateStyle;
import com.zjlp.face.util.date.DateUtil;

public class OperateDataVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4573583031159080711L;
	public static final long l_zero = 0L;
	public static final int i_zero = 0;
	//今日付款金额
	private Long recivePrice = l_zero;
	//佣金支出
	private Long payCommission = l_zero;
	//今日付款订单数
	private Integer orderCount = i_zero;
	//客户数
	private Integer consumerCount = i_zero;
	//今日佣金收入
	private Long reciveCommission = l_zero;
	//店铺佣金收入
	private Long reciveSpCommission = l_zero;
	//分店提成佣金
	private Long reciveFdCommission = l_zero;
	//近一周销售情况
	private List<DayData> weekRecivePrice;
	private List<DayData> weekPayCommission;
	private List<DayData> weekReciveCommission;
	
	public Long getRecivePrice() {
		return recivePrice;
	}
	public void setRecivePrice(Long recivePrice) {
		this.recivePrice = recivePrice;
	}
	public Long getPayCommission() {
		return payCommission;
	}
	public void setPayCommission(Long payCommission) {
		this.payCommission = payCommission;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Integer getConsumerCount() {
		return consumerCount;
	}
	public void setConsumerCount(Integer consumerCount) {
		this.consumerCount = consumerCount;
	}
	public Long getReciveCommission() {
		return reciveCommission;
	}
	public void setReciveCommission(Long reciveCommission) {
		this.reciveCommission = reciveCommission;
	}
	public Long getReciveSpCommission() {
		return reciveSpCommission;
	}
	public void setReciveSpCommission(Long reciveSpCommission) {
		this.reciveSpCommission = reciveSpCommission;
	}
	public Long getReciveFdCommission() {
		return reciveFdCommission;
	}
	public void setReciveFdCommission(Long reciveFdCommission) {
		this.reciveFdCommission = reciveFdCommission;
	}
	public List<DayData> getWeekRecivePrice() {
		return weekRecivePrice;
	}
	public void setWeekRecivePrice(List<DayData> weekRecivePrice) {
		this.weekRecivePrice = weekRecivePrice;
	}
	public List<DayData> getWeekPayCommission() {
		return weekPayCommission;
	}
	public void setWeekPayCommission(List<DayData> weekPayCommission) {
		this.weekPayCommission = weekPayCommission;
	}
	
	public List<DayData> getWeekReciveCommission() {
		return weekReciveCommission;
	}
	public void setWeekReciveCommission(List<DayData> weekReciveCommission) {
		this.weekReciveCommission = weekReciveCommission;
	}

	static class DayData {
		private Long time;
		private Long value;
		public DayData(){}
		public DayData(Long time, Long value){
			this.time = time;
			this.value = value;
		}
		public Long getTime() {
			return time;
		}
		public void setTime(Long time) {
			this.time = time;
		}
		public Long getValue() {
			return value;
		}
		public void setValue(Long value) {
			this.value = value;
		}
	}
	public static List<DayData> mapToList(Map<String, Long> map) {
		List<DayData> temp = new LinkedList<OperateDataVo.DayData>();
		Long tempkey = null;
		for (String key : map.keySet()) {
			tempkey = DateUtil.StringToDate(key, DateStyle.YYYY_MM_DD).getTime();
			temp.add(new DayData(tempkey, map.get(key)));
		}
		return temp;
	}
//	public static void main(String[] args) {
//		Map<String, Long> map = new LinkedHashMap<String, Long>();
//		map.put("2015-07-14", 100L);
//		map.put("2015-07-15", 100L);
//		map.put("2015-07-16", 100L);
//		map.put("2015-07-17", 100L);
//		List<DayData> data = mapToList(map);
//		
//		OperateDataVo result = new OperateDataVo();
//		result.setRecivePrice(100L);
//		result.setPayCommission(100L);
//		result.setOrderCount(100);
//		result.setConsumerCount(100);
//		//七天经营数据
//		result.setWeekPayCommission(data);
//		result.setWeekRecivePrice(data);
//		
//		JSONInfo<OperateDataVo> info = new JSONInfo<OperateDataVo>();
//		info.setCode(AssConstantsUtil.REQUEST_OK_CODE);
//		info.setMsg("");
//		info.setData(result);
//		System.out.println(info.toJsonString());
//	}
	
}
