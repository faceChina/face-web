package com.zjlp.face.web.server.operation.marketing.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.MarketingException;
import com.zjlp.face.web.server.operation.marketing.dao.MarketingActivityDao;
import com.zjlp.face.web.server.operation.marketing.dao.MarketingActivityDetailDao;
import com.zjlp.face.web.server.operation.marketing.dao.MarketingToolDao;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.marketing.service.MarketingService;

/**
 * 营销工具基础服务
 * @ClassName: MaketingServiceImpl 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月14日 下午3:36:45
 */
@Service("maketingService")
public class MarketingServiceImpl implements MarketingService {
	
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private MarketingActivityDao marketingActivityDao;
	@Autowired
	private MarketingActivityDetailDao marketingActivityDetailDao;
	@Autowired
	private MarketingToolDao marketingToolDao;

	@Override
	public Long addTool(MarketingTool tool) throws MarketingException {
		try {
			AssertUtil.notNull(tool, "param[tool] can't be null");
			MarketingToolDto.checkInput(tool);
			Date date = new Date();
			tool.setCreateTime(date);
			tool.setUpdateTime(date);
			Long id = marketingToolDao.add(tool);
			log.info(new StringBuilder("Add ").append(tool).append(" end."));
			return id;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public Long addActivity(MarketingActivity activity)
			throws MarketingException {
		try {
			AssertUtil.notNull(activity, "param[activity] can't be null");
			this.checkActivity(activity);
			//重复验证
//			MarketingActivity old = this.getValidActivityByToolId(activity.getToolId());
//			AssertUtil.isNull(old, "MarketingActivity[toolId={}] is already exists.",
//					activity.getToolId());
			//添加
			Date date = new Date();
			activity.setCreateTime(date);
			activity.setUpdateTime(date);
			Long id = marketingActivityDao.add(activity);
			log.info(new StringBuilder("Add ").append(activity).append(" end."));
			return id;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	private void checkActivity(MarketingActivity activity) {
		AssertUtil.notNull(activity.getRemoteType(), "[Activity Check] remoteType can't be null.");
		AssertUtil.notNull(activity.getRemoteId(), "[Activity Check] remoteId can't be null.");
		AssertUtil.notNull(activity.getToolId(), "[Activity Check] toolId can't be null.");
		AssertUtil.notNull(activity.getStatus(), "[Activity Check] status can't be null.");
		AssertUtil.notNull(activity.getIsTimeLimit(), "[Activity Check] isTimeLimit can't be null.");
		if (MarketingActivityDto.HAS_TIME_LIMIT.equals(activity.getIsTimeLimit())) {
			AssertUtil.notNull(activity.getStartTime(), "[Activity Check] startTime can't be null.");
			AssertUtil.notNull(activity.getEndTime(), "[Activity Check] endTime can't be null.");
		}
	}

	@Override
	public Long addActivityDetail(MarketingActivityDetail activityDetail)
			throws MarketingException {
		try {
			AssertUtil.notNull(activityDetail, "param[activityDetail] can't be null");
			Date date = new Date();
			activityDetail.setUpdateTime(date);
			activityDetail.setCreateTime(date);
			Long id = marketingActivityDetailDao.add(activityDetail);
			log.info(new StringBuilder("Add ").append(activityDetail).append(" end."));
			return id;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public void addActivityDetail(List<MarketingActivityDetail> detailList)
			throws MarketingException {
		try {
			AssertUtil.notEmpty(detailList, "param[detailList] can't be null");
			for (MarketingActivityDetail detail : detailList) {
				if (null == detail) continue;
				this.addActivityDetail(detail);
			}
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingTool getToolById(Long id) throws MarketingException {
		try {
			AssertUtil.notNull(id, "param[id] can't be null");
			MarketingTool tool = marketingToolDao.getById(id);
			AssertUtil.notNull(tool, "MarketingTool[id={}] is not exists.", id);
			return tool;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingTool getToolByType(String remoteId, Integer remoteType,
			Integer marketingType, Integer productType)
			throws MarketingException {
		try {
			AssertUtil.notNull(remoteId, "param[remoteId] can't be null");
			AssertUtil.notNull(remoteType, "param[remoteType] can't be null");
			AssertUtil.notNull(marketingType, "param[marketingType] can't be null");
			AssertUtil.notNull(productType, "param[productType] can't be null");
			MarketingTool tool = new MarketingTool();
			tool.setRemoteId(remoteId);
			tool.setRemoteType(remoteType);
			tool.setMarketingType(marketingType);
			tool.setProductType(productType);
			return marketingToolDao.getToolByType(tool);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingActivity getActivityById(Long id) throws MarketingException {
		try {
			AssertUtil.notNull(id, "param[id] can't be null");
			MarketingActivity activity = marketingActivityDao.getById(id);
			AssertUtil.notNull(activity, "MarketingActivity[id={}] is not exists.", id);
			return activity;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingActivity getValidActivityByToolId(Long toolId)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "param[toolId] can't be null");
			return marketingActivityDao.getValidActivityByToolId(toolId);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MarketingActivity> findActivityListByToolId(Long toolId)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "param[toolId] can't be null");
			return marketingActivityDao.findListByToolId(toolId);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingActivityDetail getDetailById(Long id)
			throws MarketingException {
		try {
			AssertUtil.notNull(id, "param[id] can't be null");
			MarketingActivityDetail detail = marketingActivityDetailDao.getValidById(id);
			return detail;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MarketingActivityDetailDto> findListByActivityId(Long activityId)
			throws MarketingException {
		try {
			AssertUtil.notNull(activityId, "param[activityId] can't be null");
			MarketingActivityDetail detail = new MarketingActivityDetail();
			detail.setActivityId(activityId);
			return marketingActivityDetailDao.findDtoList(detail);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MarketingActivityDetail> findListByToolId(Long toolId)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "param[toolId] can't be null");
			MarketingActivityDetail detail = new MarketingActivityDetail();
			detail.setToolId(toolId);
			return this.findDetailList(detail);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}
	
	private List<MarketingActivityDetail> findDetailList(MarketingActivityDetail detail) {
		return marketingActivityDetailDao.findList(detail);
	}

	@Override
	public boolean editToolStatus(Long toolId, Integer status)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "param[toolId] can't be null.");
			AssertUtil.notNull(status, "param[status] can't be null.");
			MarketingTool tool = new MarketingTool();
			tool.setId(toolId);
			tool.setStatus(status);
			tool.setUpdateTime(new Date());
			marketingToolDao.edit(tool);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}
	
	@Override
	public boolean editActivityStatusByToolId(Long toolId, Integer status)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "param[toolId] can't be null.");
			AssertUtil.notNull(status, "param[status] can't be null.");
			MarketingActivity activity = new MarketingActivity();
			activity.setToolId(toolId);
			activity.setStatus(status);
			activity.setUpdateTime(new Date());
			marketingActivityDao.editStatus(activity);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean editDetailStatusByToolId(Long toolId, Integer status)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "param[toolId] can't be null.");
			AssertUtil.notNull(status, "param[status] can't be null.");
			MarketingActivityDetail detail = new MarketingActivityDetail();
			detail.setToolId(toolId);
			detail.setUpdateTime(new Date());
			detail.setStatus(status);
			marketingActivityDetailDao.editStatus(detail);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean delActivity(Long activityId)
			throws MarketingException {
		try {
			AssertUtil.notNull(activityId, "param[activityId] can't be null");
			marketingActivityDao.delById(activityId);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean delActivityDetail(Long id) throws MarketingException {
		try {
			AssertUtil.notNull(id, "param[id] can't be null");
			marketingActivityDetailDao.delById(id);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}
	
	@Override
	public boolean delActivityDetailByActivityId(Long activityId)
			throws MarketingException {
		try {
			AssertUtil.notNull(activityId, "param[activityId] can't be null");
			marketingActivityDetailDao.delByActivityId(activityId);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean editDetail(MarketingActivityDetail detail)
			throws MarketingException {
		try {
			AssertUtil.notNull(detail, "param[detail] can't be null");
			AssertUtil.notNull(detail.getId(), "param[detail.id] can't be null");
			detail.setUpdateTime(new Date());
			marketingActivityDetailDao.edit(detail);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MarketingActivityDto> findActivityPage(MarketingActivityDto dto)
			throws MarketingException {
		try {
			AssertUtil.notNull(dto, "param[dto] can't be null");
			return marketingActivityDao.findPage(dto);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public Integer getActivityCount(MarketingActivityDto dto)
			throws MarketingException {
		try {
			AssertUtil.notNull(dto, "param[dto] can't be null");
			return marketingActivityDao.getCount(dto);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean editActivity(MarketingActivity activity)
			throws MarketingException {
		try {
			AssertUtil.notNull(activity, "param[activity] can't be null");
			AssertUtil.notNull(activity.getId(), "param[activity.id] can't be null");
			Date date = new Date();
			activity.setCreateTime(date);
			activity.setUpdateTime(date);
			marketingActivityDao.edit(activity);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

}
