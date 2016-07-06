package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.JSONInfo;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.member.business.MemberBusiness;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.util.AssConstantsUtil;

@Controller
@RequestMapping({ "/assistant/ass/member/" })
public class MemberAppCtl extends BaseCtl  {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private MemberBusiness memberBusiness;
	
	//积分记录会员列表记录字段过滤
	public static final String[] SEND_INTEGRAL_JSON_LISTS = { "start", "pageSize", "totalRow", "totalPage", "datas.id", "datas.claimTime",
		"datas.createTime", "datas.integral", "datas.sellerId", "datas.userId", "datas.statisticsIntegral"};
	/**
	 * 卖家赠送积分
	 * @Title: sendIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月8日 下午1:59:13  
	 * @author lys
	 */
	@RequestMapping(value="sendIntegral")
	@ResponseBody
	public String sendIntegral(@RequestBody JSONObject jsonObj) {
		String userId = jsonObj.getString("userId");
		String integral = jsonObj.getString("integral");
		//参数用户id为空
		if (StringUtils.isBlank(userId)) {
			log.error("【赠送积分】用户userid为空，返回。");
			return outFailure(AssConstantsUtil.Member.NULL_USERID_CODE);
		}
		//参数积分个数为空
		if (StringUtils.isBlank(integral) || !StringUtils.isNumeric(integral)) {
			log.error("【赠送积分】用户integral为空，返回。");
			return outFailure(AssConstantsUtil.Member.NULL_INTEGRAL_CODE);
		}
		try {
			//赠送积分
			memberBusiness.sendIntegral(Long.valueOf(userId), super.getUserId(), Long.valueOf(integral));
			return outSucceedByNoData();
		} catch (Exception e) {
			log.error("【赠送积分】赠送积分发生异常，返回。", e);
			return outFailure(AssConstantsUtil.Member.SEND_SYSERR_CODE);
		}
	}
	
	@RequestMapping(value = "queryJflb")
	@ResponseBody
	public String querySendIntegralRecode(@RequestBody JSONObject jsonObj){
		//实例化分页信息
		Pagination<SendIntegralRecodeDto> pagination = super.initPagination(jsonObj);
		//用户ID
		if (!jsonObj.containsKey("userId")) {
			log.error("【查找赠送积分列表 】参数用户id为空。");
			return outFailure(AssConstantsUtil.Member.NULL_USERID_SEND_SYSERR_CODE);
		}
		Long userId = jsonObj.getLong("userId");
		try {
			SendIntegralRecodeDto sendDto = new SendIntegralRecodeDto(super.getUserId(), userId);
			pagination = memberBusiness.queryIntegralRecode(sendDto, pagination);
			if (pagination != null) {
				pagination.setStart(pagination.getEnd());
			}
			return super.outSucceed(pagination, true, SEND_INTEGRAL_JSON_LISTS);
		} catch (MemberException e) {
			log.error("【查找赠送积分列表 】查找赠送积分列表异常。", e);
			return outFailure(AssConstantsUtil.Member.LIST_SEND_SYSERR_CODE);
		}
	}
	
	
	public static void main(String[] args) {
		Pagination<SendIntegralRecodeDto> pagination = new Pagination<SendIntegralRecodeDto>();
		
		SendIntegralRecodeDto dto = new SendIntegralRecodeDto(11L, 12L, 50L);
		Date date = new Date();
		dto.setClaimTime(date);
		dto.setCreateTime(date);
		dto.setUpdateTime(date);
		dto.setStatisticsIntegral(dto.getStatisticsIntegral());
		List<SendIntegralRecodeDto> datas = new ArrayList<SendIntegralRecodeDto>();
		datas.add(dto);
		pagination.setDatas(datas);
		pagination.setTotalRow(1);
		
		JSONInfo<Pagination<SendIntegralRecodeDto>> info = new JSONInfo<Pagination<SendIntegralRecodeDto>>();
		info.setCode(AssConstantsUtil.REQUEST_OK_CODE);
		info.setMsg("");
		info.setJsessionid("aasdfasdasdf");
		info.setData(pagination);
		System.out.println(info.toJsonString(SEND_INTEGRAL_JSON_LISTS));
	}
}
