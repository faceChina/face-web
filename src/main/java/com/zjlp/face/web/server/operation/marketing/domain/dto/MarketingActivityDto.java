package com.zjlp.face.web.server.operation.marketing.domain.dto;

import java.util.Date;
import java.util.List;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.page.Aide;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;

public class MarketingActivityDto extends MarketingActivity {
	
	/** 有时间限定 */
	public static final Integer HAS_TIME_LIMIT = 1;
	/** 没有时间限定 */
	public static final Integer NO_TIME_LIMIT = -1;
	/** 标准结束时间  9999-12-31 23:59:59 */
	public static final Date DEFAULT_ENDTIME = DateUtil.StringToDate("99991231235959");
	private static final long serialVersionUID = -6857954022562691166L;
	private Aide aide = new Aide();
	private List<MarketingActivityDetailDto> detailList;
	private String name;
	public Aide getAide() {
		return aide;
	}
	public void setAide(Aide aide) {
		this.aide = aide;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MarketingActivityDetailDto> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<MarketingActivityDetailDto> detailList) {
		this.detailList = detailList;
	}
	public static MarketingActivity initActivity(Long sellerId, Long toolId) {
		MarketingActivity activity = new MarketingActivity();
		activity.setRemoteId(String.valueOf(sellerId));
		activity.setRemoteType(1);
		activity.setToolId(toolId);
		activity.setStatus(Constants.VALID);
		activity.setIsTimeLimit(NO_TIME_LIMIT);
		return activity;
	}
	
	public static MarketingActivity initActivity(Long sellerId, Long toolId, 
			Date startTime, Date endTime) {
		MarketingActivity activity = new MarketingActivity();
		activity.setRemoteId(String.valueOf(sellerId));
		activity.setRemoteType(1);
		activity.setToolId(toolId);
		activity.setStatus(Constants.VALID);
		activity.setIsTimeLimit(HAS_TIME_LIMIT);
		activity.setStartTime(startTime);
		activity.setEndTime(endTime);
		return activity;
	}
	
	public String getZsGz() {
		if (null == detailList || detailList.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < detailList.size(); i++) {
			MarketingActivityDetailDto dto = detailList.get(i);
			if (null == dto) continue;
			sb.append("满 ").append(CalculateUtils.converFenToYuan(dto.getPremiseVal().longValue()))
			  .append("元  送").append(CalculateUtils.converFenToYuan(dto.getResultVal().longValue())).append("元").append("<br>");
		}
		return sb.substring(0, sb.length() - 2);	
	}
	
	public static boolean isValidTime(Date startTime, Date endTime,
			List<? extends MarketingActivity> activitys, Long filterId) {
		boolean flag = true;
		for (MarketingActivity activity : activitys) {
			if (null == activity
					|| (null != activity.getId() && null != filterId && activity
							.getId().equals(filterId))) {
				continue;
			}
			flag = (startTime.before(activity.getStartTime()) && endTime
					.before(activity.getStartTime()))
					|| (startTime.after(activity.getEndTime()) && endTime
							.after(activity.getEndTime()));
			if (!flag)
				return false;
		}
		return flag;
	}
	public int getIsActive() {
		Date now = new Date();
		if (now.compareTo(super.getStartTime()) < 0) {
			return 1;
		} else if (now.compareTo(super.getEndTime()) >= 0) {
			return -1;
		}
		return 0;
	}

}
