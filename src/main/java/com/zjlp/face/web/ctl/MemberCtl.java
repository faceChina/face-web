package com.zjlp.face.web.ctl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.member.business.MemberBusiness;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberRule;
import com.zjlp.face.web.server.operation.member.domain.vo.MemberVo;
import com.zjlp.face.web.server.product.good.domain.vo.TempImg;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

@Controller
@RequestMapping("/u/member/")
public class MemberCtl extends BaseCtl {
	
	@Autowired
	private MemberBusiness memberBusiness;

	//会员设置编辑页
	@RequestMapping(value="enact/edit", method=RequestMethod.GET)
	public String enactPage(Model model) throws Exception {
		MemberEnactment enactment = memberBusiness.getMemberEnactment(super.getUserId());
		model.addAttribute("enact", enactment);
		model.addAttribute("img", TempImg.getTempMapByType(TempImg.MEMBER));
		return "/m/operation/member/enact-edit";
	}
	
	//会员设置编辑
	@RequestMapping(value="enact/edit", method=RequestMethod.POST)
	@ResponseBody
	public String enactEdit(MemberEnactment enactment, Model model) {
		try {
			Long sellerId = super.getUserId();
			memberBusiness.editMemberEnactment(enactment, sellerId);
			return super.getReqJson(true, null);
		} catch (MemberException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	//会员规则列表
	@RequestMapping(value="rule/list", method=RequestMethod.GET)
	public String ruleList(Model model) {
		Long sellerId = super.getUserId();
		List<MemberRule> list = memberBusiness.findMemberList(sellerId);
		model.addAttribute("list", list);
		return "/m/operation/member/rule-list";
	}
	
	//保存规则列表
	@RequestMapping(value="rule/edit", method=RequestMethod.POST)
	public String ruleEdit(MemberVo rule, Model model) {
		Long sellerId = super.getUserId();
		memberBusiness.editRule(rule.getRuleList(), sellerId);
		return super.getRedirectUrl("list");
	}
	
	//删除规则
	@RequestMapping(value="rule/del", method=RequestMethod.POST)
	@ResponseBody
	public String delRule(@RequestParam Long id) {
		try {
			Long sellerId = super.getUserId();
			memberBusiness.delDetailById(id, sellerId);
			return super.getReqJson(true, null);
		} catch (MemberException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	//积分首页
	@RequestMapping(value="integral/index", method=RequestMethod.GET)
	public String integralIndex(Model model) {
		Long sellerId = super.getUserId();
		MarketingTool dj =  memberBusiness.getMarketingTool(sellerId, 
				MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_JF);
		MarketingTool qd =  memberBusiness.getMarketingTool(sellerId, 
				MarketingToolDto.CJ_TYPE_QD, MarketingToolDto.PD_TYPE_JF);
		MarketingTool xf =  memberBusiness.getMarketingTool(sellerId, 
				MarketingToolDto.CJ_TYPE_ZF, MarketingToolDto.PD_TYPE_JF);
		model.addAttribute("dj", dj.getStatus());
		model.addAttribute("qd", qd.getStatus());
		model.addAttribute("xf", xf.getStatus());
		model.addAttribute("djid", dj.getId());
		model.addAttribute("qdid", qd.getId());
		model.addAttribute("xfid", xf.getId());
		return "/m/operation/member/integral-index";
	}
	
	//积分营销活动状态修改
	@RequestMapping(value="integral/altstatus", method=RequestMethod.POST)
	@ResponseBody
	public String changeJfToolStatus(@RequestParam Long toolId, @RequestParam Integer status) {
		try {
			memberBusiness.editMarketingRuleStatus(super.getUserId(), toolId, status);
			return super.getReqJson(true, null);
		} catch (MemberException e) {
			return super.getReqJson(false, null);
		}
	}
	
	//积分低价规则设置
	@RequestMapping(value="integral/djedit", method=RequestMethod.GET)
	public String djEditPage(Model model) {
		Long sellerId = super.getUserId();
		List<MarketingActivityDetail> list = memberBusiness.findDetail(sellerId, 
				MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_JF);
		if (!CollectionUtils.isEmpty(list)) {
			model.addAttribute("id", list.get(0).getId());
			model.addAttribute("maxVal", list.get(0).getMaxVal());
		}
		return "/m/operation/member/integral-dj";
	}
	
	@RequestMapping(value="integral/djedit", method=RequestMethod.POST)
	public String djEdit(Long id, @RequestParam Integer maxVal, Model model) {
		Long sellerId = super.getUserId();
		MarketingActivityDetail detail = new MarketingActivityDetail();
		detail.setId(id);
		detail.setPremiseVal(100);
		detail.setResultVal(100);
		detail.setMaxVal(maxVal);
		memberBusiness.addOrEditStandardActivity(sellerId, MarketingToolDto.CJ_TYPE_DG, 
				MarketingToolDto.PD_TYPE_JF, detail);
		return super.getRedirectUrl("index");
	}
	
	//积分签到规则设置
	@RequestMapping(value="integral/qdedit", method=RequestMethod.GET)
	public String qdEditPage(Model model) {
		Long sellerId = super.getUserId();
		List<MarketingActivityDetail> list = memberBusiness.findDetail(sellerId, 
				MarketingToolDto.CJ_TYPE_QD, MarketingToolDto.PD_TYPE_JF);
		if (!CollectionUtils.isEmpty(list)) {
			model.addAttribute("id", list.get(0).getId());
			model.addAttribute("first", list.get(0).getBaseVal());
			model.addAttribute("stepAccumulate", list.get(0).getStepAccumulate());
			model.addAttribute("maxVal", list.get(0).getMaxVal());
		}
		return "/m/operation/member/integral-qd";
	}
	
	@RequestMapping(value="integral/qdedit", method=RequestMethod.POST)
	public String qdEdit(Long id, @RequestParam Integer first, @RequestParam Integer stepAccumulate,
			@RequestParam  Integer maxVal, Model model) {
		Long sellerId = super.getUserId();
		MarketingActivityDetail detail = new MarketingActivityDetail();
		detail.setId(id);
		detail.setBaseVal(first);
		detail.setStepAccumulate(stepAccumulate);
		detail.setStep(1);
		detail.setMaxVal(maxVal);
		memberBusiness.addOrEditStandardActivity(sellerId, MarketingToolDto.CJ_TYPE_QD, 
				MarketingToolDto.PD_TYPE_JF, detail);
		return super.getRedirectUrl("index");
	}
	
	//积分消费送规则设置
	@RequestMapping(value="integral/xfedit", method=RequestMethod.GET)
	public String xfEditPage(Model model) {
		Long sellerId = super.getUserId();
		List<MarketingActivityDetail> list = memberBusiness.findDetail(sellerId, 
				MarketingToolDto.CJ_TYPE_ZF, MarketingToolDto.PD_TYPE_JF);
		if (!CollectionUtils.isEmpty(list)) {
			model.addAttribute("id", list.get(0).getId());
			model.addAttribute("premiseVal", list.get(0).getPremiseVal());
			model.addAttribute("resultVal", list.get(0).getResultVal());
		}
		return "/m/operation/member/integral-xf";
	}
	
	@RequestMapping(value="integral/xfedit", method=RequestMethod.POST)
	public String xfEdit(Long id, @RequestParam  String premiseVal,  
			@RequestParam Integer resultVal, Model model) {
		Long sellerId = super.getUserId();
		MarketingActivityDetail detail = new MarketingActivityDetail();
		detail.setPremiseVal(CalculateUtils.converYuantoPenny(premiseVal).intValue());
		detail.setResultVal(resultVal);
		detail.setId(id);
		memberBusiness.addOrEditStandardActivity(sellerId, MarketingToolDto.CJ_TYPE_ZF, 
				MarketingToolDto.PD_TYPE_JF, detail);
		return super.getRedirectUrl("index");
	}
	
	//会员卡充值
	@RequestMapping("czmg/list")
	public String czList(Pagination<MarketingActivityDto> pagination, Model model) {
		Long sellerId = super.getUserId();
		MarketingTool cz =  memberBusiness.getMarketingTool(sellerId, 
				MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY);
		pagination = memberBusiness.findCzPage(pagination, cz.getId());
		model.addAttribute("pagination", pagination);
		model.addAttribute("cz", cz.getStatus());
		model.addAttribute("czid", cz.getId());
		return "/m/operation/member/member-cz";
	}
	
	@RequestMapping(value="czmg/altstatus", method=RequestMethod.POST)
	@ResponseBody
	public String changeCzToolStatus(@RequestParam Long toolId, @RequestParam Integer status) {
		try {
			memberBusiness.editMarketingRuleStatus(super.getUserId(), toolId, status);
			return super.getReqJson(true, null);
		} catch (MemberException e) {
			return super.getReqJson(false, null);
		}
	}
	
	//编辑会员充值
	@RequestMapping(value="czmg/edit", method=RequestMethod.GET)
	public String czEditPage(MarketingActivityDto activity, Model model) {
		if (null != activity.getId()) {
			Long sellerId = super.getUserId();
			MarketingActivityDto active = memberBusiness.
					getMarketingActivityById(activity.getId(), sellerId);
			model.addAttribute("active", active);
		}
		return "/m/operation/member/member-cz-edit";
	}
	
	@RequestMapping(value="czmg/edit", method=RequestMethod.POST)
	@ResponseBody
	public String czEdit(MarketingActivityDto activity, HttpServletRequest request) {
		try {
			Date startTime = DateUtil.timeFormat.parse(request.getParameter("startTime"));
			Date endTime = DateUtil.timeFormat.parse(request.getParameter("endTime"));
			activity.setStartTime(startTime);
			activity.setEndTime(endTime);
			Long sellerId = super.getUserId();
			//过滤空值
			filterNullObject(activity.getDetailList());
			//规则验证
			MarketingActivityDetailDto.checkCzGz(activity.getDetailList());
			//新增或修改
			memberBusiness.addOrEditNotStandardActivity(sellerId, 
					MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY, activity);
			return super.getReqJson(true, null);
		} catch (BaseException e) {
			return super.getReqJson(false, e.getExternalMsg());
		} catch (Exception e) {
			return super.getReqJson(false, "修改充值规则失败");
		}
	}
	//空值过滤
	private static void filterNullObject(List<MarketingActivityDetailDto> detailList) {
		for (int i = detailList.size() - 1; i >= 0; i--) {
			MarketingActivityDetailDto dto = detailList.get(i);
			if (null == dto.getPremiseVal() || null == dto.getResultVal()) {
				detailList.remove(i);
			}
		}
	}
	
	//删除充值活动
	@RequestMapping(value="czmg/del", method=RequestMethod.POST)
	@ResponseBody
	public String czDel(@RequestParam Long id) {
		try {
			Long sellerId = super.getUserId();
			memberBusiness.delNotStantdardActivity(id, sellerId);
			return super.getReqJson(true, null);
		} catch (MemberException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	//活动详情删除
	@RequestMapping(value="czmg/deldetail", method=RequestMethod.POST)
	@ResponseBody
	public String czdetailDel(@RequestParam Long id) {
		try {
			Long sellerId = super.getUserId();
			memberBusiness.delDetailById(id, sellerId);
			return super.getReqJson(true, null);
		} catch (MemberException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	//会员管理
	@RequestMapping(value = "hymg/list")
	public String memberList(MemberCardDto cardDto, Integer index,
			Pagination<MemberCardDto> pagination, Model model) {
		Long sellerId = super.getUserId();
		cardDto.setSellerId(sellerId);
		List<MemberRule> ruleList = memberBusiness.findMemberList(sellerId);
		pagination = memberBusiness.findMemberCardPageForSeller(cardDto, pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("index", null == index ? 0 : index);
		model.addAttribute("ruleList", ruleList);
		model.addAttribute("cardDto", cardDto);
		return "/m/operation/member/member-card-list";
	}
	
	// 充值记录
	@RequestMapping(value = "hymg/czlist")
	public String czList(Long cardId,
			Pagination<MemberTransactionRecordDto> pagination, Model model) {
		Long sellerId = super.getUserId();
		MemberTransactionRecordDto dto = new MemberTransactionRecordDto();
		dto.setSellerId(sellerId.toString());
		dto.setType(MemberTransactionRecordDto.TYPE_RECHAGE);
		dto.setMemberCardId(cardId);
		pagination = memberBusiness.findTransactionPage(dto, pagination);
		MemberCard card = memberBusiness.getMemberCardById(cardId, sellerId, null);
		model.addAttribute("pagination", pagination);
		model.addAttribute("cardId", cardId);
		model.addAttribute("cardNo", card.getMemberCard());
		return "/m/operation/member/member-cz-list";
	}
	
	//消费记录
	@RequestMapping(value = "hymg/xflist")
	public String xfList(Long cardId, Pagination<MemberTransactionRecordDto> pagination, Model model) {
		Long sellerId = super.getUserId();
		MemberTransactionRecordDto dto = new MemberTransactionRecordDto();
		dto.setSellerId(sellerId.toString());
		dto.setType(MemberTransactionRecordDto.TYPE_CONSUM);
		dto.setMemberCardId(cardId);
		pagination = memberBusiness.findTransactionPage(dto, pagination);
		MemberCard card = memberBusiness.getMemberCardById(cardId, sellerId, null);
		model.addAttribute("pagination", pagination);
		model.addAttribute("cardId", cardId);
		model.addAttribute("cardNo", card.getMemberCard());
		return "/m/operation/member/member-xf-list";
	}
	
	//会员编辑
	@RequestMapping(value = "hymg/view")
	public String view(@RequestParam Long id, Model model) {
		Long sellerId = super.getUserId();
		MemberCard card = memberBusiness.getMemberCardById(id, sellerId, null);
		List<MemberRule> ruleList = memberBusiness.findMemberList(sellerId);
		String memberName = MemberRule.getMemberRuleByAmount(ruleList, card.getAmount()).getName();
		model.addAttribute("memberName", memberName);
		model.addAttribute("card", card);
		return "/m/operation/member/member-view";
	}
	
	@RequestMapping(value = "hymg/editpage", method=RequestMethod.GET)
	public String editMbPage(@RequestParam Long id, Model model) {
		Long sellerId = super.getUserId();
		MemberCard card = memberBusiness.getMemberCardById(id, sellerId, null);
		List<MemberRule> ruleList = memberBusiness.findMemberList(sellerId);
		String memberName = MemberRule.getMemberRuleByAmount(ruleList, card.getAmount()).getName();
		model.addAttribute("card", card);
		model.addAttribute("memberName", memberName);
		return "/m/operation/member/member-edit";
	}
	
	@RequestMapping(value = "hymg/edit", method=RequestMethod.POST)
	@ResponseBody
	public String editMb(MemberCard card) {
		try {
			Long sellerId = super.getUserId();
			memberBusiness.editMemberCard(card, sellerId);
			return super.getReqJson(true, null);
		} catch (MemberException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
}
