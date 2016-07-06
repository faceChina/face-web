package com.zjlp.face.web.server.operation.member.domain.dto;

import com.zjlp.face.web.server.operation.member.domain.ConsumptionAdjustmentRecord;

public class ConsumptionAdjustmentRecordDto extends ConsumptionAdjustmentRecord {
	
	private static final long serialVersionUID = 1335672823111352052L;
	/** 1:增 */
	public static final Integer type_add = 1;
	/** 2:减 */
	public static final Integer type_minus = 2;
	
	public static ConsumptionAdjustmentRecord init(Long cardId, Long adjustAmount, Long integral) {
		ConsumptionAdjustmentRecord record = new ConsumptionAdjustmentRecord();
		record.setMemberCardId(cardId);
		record.setType(adjustAmount < 0 ? type_minus : type_add);
		record.setAdjustmentAmount(Math.abs(adjustAmount));
		record.setIntegralAmout(integral);
		return record;
	}

	private String adjustAmount;

	public String getAdjustAmount() {
		return adjustAmount;
	}
	public void setAdjustAmount(String adjustAmount) {
		this.adjustAmount = adjustAmount;
	}
	
}
