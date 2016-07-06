package com.zjlp.face.web.ctl.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.redenvelope.business.RedenvelopeBusiness;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.vo.ReceivePerson;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.payment.bussiness.PaymentBusiness;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.Logs;

@Controller
@RequestMapping("assistant/ass/redenvelope/")
public class RedenvelopeAppCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private RedenvelopeBusiness redenvelopeBusiness;
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private PaymentBusiness paymentBusiness;
	
	public static final String[] LOOK_FIELD = {"totalCount", "myReceiveMoney", "hasReceiveMoney", "hasReceiveCount", "totalMoney", "time", "receiveUsers.headUrl", "receiveUsers.nickname", "receiveUsers.receiveTime", "receiveUsers.amount",
		"receiveUsers.bestLuck","start", "curPage", "pageSize", "totalRow", "clickTime"};
	
	@RequestMapping(value="todaySendAmount", method=RequestMethod.POST)
	@ResponseBody
	public String getTodaySendAmount() {
		try {
			Long amountToday = redenvelopeBusiness.countUserSendAmountToday(getUserId());
			return outSucceed(amountToday, false, "");
		} catch (Exception e) {
			_logger.error("查询用户今日发红包金额失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 发红包请求
	 * @Title: insert 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj
	 * @return
	 * @date 2015年10月14日 上午9:27:01  
	 * @author cbc
	 */
	@RequestMapping(value="insert", method=RequestMethod.POST)
	@ResponseBody
	public String insert(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj ){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long userId = getUserId();
			JSONObject jsonStr = jsonObj.optJSONObject("sendRecord");
			if (jsonStr == null) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			SendRedenvelopeRecord record = JsonUtil.toBean(jsonStr.toString(), SendRedenvelopeRecord.class);
			record.setSendUserId(userId);
			if (record.getType().equals(Constants.TYPE_PERSON)) {
				User receiveUser = userBusiness.getUserByLoginAccount(record.getTargetId());
				record.setTargetId(String.valueOf(receiveUser.getId()));
			}
			Long id = redenvelopeBusiness.send(record);
			Long amount = accountBusiness.getWithdrawAmount(userId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("amount", amount);
			return outSucceed(map, false, "");
		} catch (Exception e) {
			_logger.error("红包信息入库失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: send 
	 * @Description: 将红包拆解为小红包
	 * @return
	 * @date 2015年10月14日 上午9:37:04  
	 * @author cbc
	 */
	@RequestMapping(value="send", method=RequestMethod.POST)
	@ResponseBody
	public String send(@RequestBody JSONObject jsonObj) {
		try {
			Object idObject = jsonObj.get("id");
			if (null == idObject) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long id = Long.valueOf(idObject.toString());
			Object payChannelObject = jsonObj.get("payChannel");
			if (null == payChannelObject) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Integer payChannel = Integer.valueOf(payChannelObject.toString());
			redenvelopeBusiness.afterPay(id, payChannel);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("拆解小红包失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	@RequestMapping("pay/wallet")
	@ResponseBody
	public String redenvelopeWalletPay(@RequestBody JSONObject jsonObj){
		String msg="";
		try{
			Logs.print(jsonObj);
			Long userId = getUserId();
			Object idObject = jsonObj.get("id");
			if (null == idObject) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long id = Long.valueOf(idObject.toString());
			Object paymentCodeObject = jsonObj.get("paymentCode");
			if (null == paymentCodeObject) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			_logger.info("红包"+id+"钱包支付.");
			WalletTransactionRecord wtr = paymentBusiness.redenvelopeWalletPayConsumer(userId, paymentCodeObject.toString(), id);
			if(wtr!=null){
				try{
					redenvelopeBusiness.afterPay(id, 1);
					return outSucceedByNoData();
				}catch(Exception e){
					_logger.error("拆解小红包失败:"+id);
					return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
				}
			}
		}catch(Exception e){
			_logger.error(e.getMessage(),e);
			msg=e.getMessage();
		}
		if("红包状态异常".equals(msg)){
			return outFailure(AssConstantsUtil.Pay.ORDER_PAID, "红包已支付");
		}else if("支付密码不存在".equals(msg)){
			return outFailure(AssConstantsUtil.Pay.PAYMENTCODE_EMPTY, "支付密码不存在");
		}else if("支付密码不正确".equals(msg)){
			return outFailure(AssConstantsUtil.Pay.PAYMENTCODE_ERROR, "支付密码不正确");
		}else if(msg.indexOf("钱包余额不足")!=-1){
			return outFailure(AssConstantsUtil.Pay.AMOUNT_INSUFFICIENT, "钱包余额不足");
		}  
		return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, msg);
	}
	/**
	 * 
	 * @Title: look 
	 * @Description: 查看红包
	 * @param jsonObj
	 * @return
	 * @date 2015年10月15日 上午9:37:05  
	 * @author cbc
	 */
	@RequestMapping(value="look", method=RequestMethod.POST)
	@ResponseBody
	public String look(@RequestBody JSONObject jsonObj, Pagination<ReceivePerson> pagination) {
		try {
			pagination = super.initPagination(jsonObj);
			Object idObject = jsonObj.get("id");
			if (null == idObject) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Object isReflashObject = jsonObj.get("isReflash");
			if (null == isReflashObject) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Object clickTimeObj = jsonObj.get("clickTime");
			Date clickTime = new Date();
			if (null != clickTimeObj) {
				clickTime = DateUtil.LongToDate(Long.valueOf(clickTimeObj.toString()));
			} 
			Integer isReflash = Integer.valueOf(isReflashObject.toString());
			Long id = Long.valueOf(idObject.toString());
			Long myReceiveMoney = null; //我领取的钱
			Long hasReceiveMoney = null; //已领取的金额
			Long time = null;//多久领完
			Integer totalCount = null;//红包总数
			Long totalMoney = null;//红包总额
			boolean isAllReceive = false;
			
			SendRedenvelopeRecord send = redenvelopeBusiness.getSendRocordById(id);
			pagination = redenvelopeBusiness.findReceivePerson(pagination, id, clickTime);
			isAllReceive = (pagination.getTotalRow() == send.getNumber().intValue());
			if (isAllReceive && send.getType().equals(Constants.TYPE_GROUP_LUCK)) {
				//如果全部领取则找出手气最佳
				ReceiveRedenvelopeRecord bestLuck =  redenvelopeBusiness.getBestLuckReceive(id);
				for (ReceivePerson person : pagination.getDatas()) {
					if (bestLuck.getId().equals(person.getId())) {
						person.setBestLuck(true);
					}
				}
			}
			if (isReflash.equals(0)) {
				//页面初始化
				ReceiveRedenvelopeRecord receive =  redenvelopeBusiness.getReceiveRecordByReceiveUserIdAndSendId(id, getUserId());
				if (send.getSendUserId().equals(getUserId())) {
					//查看的自己的红包，已领取金额，总红包金额
					totalMoney = send.getAmount();
					hasReceiveMoney = redenvelopeBusiness.sumHasReceiveMoney(id);
				} 
				if (null != receive) {
					myReceiveMoney = receive.getAmount();
				}
				if (isAllReceive) {
					//如果全部领取则最后领取时间
					time = redenvelopeBusiness.caculateLastReceiveTime(id);
				}
				totalCount = send.getNumber();
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("totalCount", totalCount);
			map.put("myReceiveMoney", myReceiveMoney);
			map.put("hasReceiveMoney", hasReceiveMoney);
			map.put("hasReceiveCount", pagination.getTotalRow());//已领红包数
			map.put("totalMoney", totalMoney);//已领红包数
			map.put("time", time);
			map.put("receiveUsers", pagination.getDatas());
			map.put("start", pagination.getEnd());
			map.put("curPage", pagination.getCurPage());
			map.put("pageSize", pagination.getPageSize());
			map.put("totalRow", pagination.getTotalRow());
			map.put("clickTime", clickTime);
			return outSucceed(map, true, LOOK_FIELD);
		} catch (Exception e) {
			_logger.error("查看红包失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 抢红包
	 * @Title: grap 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj
	 * @return
	 * @date 2015年10月14日 下午3:45:23  
	 * @author lys
	 */
	@RequestMapping(value="grap", method=RequestMethod.POST)
	@ResponseBody
	public String grap(@RequestBody JSONObject jsonObj) {
		if (!jsonObj.containsKey("redenvelopeId")) {
			return outFailure(AssConstantsUtil.Redenvelope.REDENVELOPE_ID_NULL);
		}
		Long redenvelopeId = jsonObj.getLong("redenvelopeId");
		Long id = redenvelopeBusiness.grabRedenvelope(super.getUserId(), redenvelopeId);
		return super.outSucceed(id);
	}
	
	/**
	 * 拆红包
	 * @Title: open 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj
	 * @return
	 * @date 2015年10月14日 下午4:11:20  
	 * @author lys
	 */
	@RequestMapping(value="open", method=RequestMethod.POST)
	@ResponseBody
	public String open(@RequestBody JSONObject jsonObj) {
		if (!jsonObj.containsKey("redenvelopeId")) {
			return outFailure(AssConstantsUtil.Redenvelope.REDENVELOPE_ID_NULL);
		}
		Long redenvelopeId = jsonObj.getLong("redenvelopeId");
		boolean isopened = redenvelopeBusiness.openRedenvelope(super.getUserId(), redenvelopeId);
		return null;
	}
	
}
