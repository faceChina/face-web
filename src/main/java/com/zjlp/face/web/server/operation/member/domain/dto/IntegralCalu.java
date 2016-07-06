package com.zjlp.face.web.server.operation.member.domain.dto;

import com.zjlp.face.util.exception.AssertUtil;


public class IntegralCalu {
	//默认跨度=1
	private static final Integer DEFAULT_STEP = 1;
	//首次签到送积分数
	private Integer first;
	//跨度
	private Integer step = DEFAULT_STEP;
	//每个跨度所加积分数
	private Integer stepAdd;
	//最大积分数
	private Integer max;
	
	public IntegralCalu(){}
	
	public IntegralCalu(Integer first, Integer stepAdd, Integer max) {
		this.first = first;
		this.stepAdd = stepAdd;
		this.max = max;
	}
	//当前签到赠送积分数
	public Integer getCurrentAdd(Integer currentCount) {
		AssertUtil.isTrue(currentCount > 0, "currentCount must great than 0.");
		Integer sum = first + (currentCount - 1) * stepAdd;
		return sum > max ? max : sum;
	}
	//下次签到赠送积分数
	public Integer getNextAdd(Integer currentCount) {
		return this.getCurrentAdd(currentCount + 1);
	}
	public Integer getFirst() {
		return first;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getStepAdd() {
		return stepAdd;
	}
	public void setStepAdd(Integer stepAdd) {
		this.stepAdd = stepAdd;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	
	public static void main(String[] args) {
		IntegralCalu integration = new IntegralCalu(3, 2, 20);
		for(int i = 1; i < 20; i++){
			System.out.println("第"+i+"次签到: 送积分="+integration.getCurrentAdd(i)+", 下次签到得积分："+integration.getNextAdd(i));
		}
	}
	
}
