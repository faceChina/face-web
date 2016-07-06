package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.util.page.Aide;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssAccountOperationVo;
import com.zjlp.face.web.appvo.AssPagination;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.account.business.WithdrawBusiness;
import com.zjlp.face.web.server.trade.account.domain.dto.AccountOperationRecordDto;
import com.zjlp.face.web.server.user.bankcard.business.BankCardBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.DataUtils;

@Controller
@RequestMapping({"/assistant/ass/account/"})
public class AccountAppCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private AccountBusiness accountBusiness;
	
	@Autowired
	private BankCardBusiness bankCardBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	@Autowired
	private WithdrawBusiness withdrawBusiness;
	
	
	private int getCardNumber(Long userId, Integer type){
		
		int count = 0;
		if (type.intValue() == 1 || type.intValue() == 2){
			count = bankCardBusiness.getCardNumber(userId, type);
		}else{
			count = bankCardBusiness.getCardNumber(userId,BankCard.USERFOR_PAY);
			count += bankCardBusiness.getCardNumber(userId,BankCard.USERFOR_SETTLE);
		}
		return count;
	}

	
	/**
	 * 我的钱包
	* @Title: userPurse
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param request
	* @param response
	* @param jsonInquiry
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月12日 上午11:02:01
	 */
	@RequestMapping(value="userPurse")
	@ResponseBody
	public String userPurse() {
		Long userId = getUserId();
		Account account = accountBusiness.getAccountByUserId(userId);
		//是否已经设置过支付密码
		if (StringUtils.isBlank(account.getPaymentCode())) {
			return outFailure(AssConstantsUtil.AccountCode.ACCOUNT_NO_PAYMENT_PASSWORD_CODE, account.getCell());
		}
		// 我的银行卡数
		int cardCounts = getCardNumber(userId,3);
		
		 Map<String,Object> dataMap = new HashMap<String,Object>();
		 
		 //钱包余额
		 dataMap.put("withdrawAmount",DataUtils.formatMoney("##0.00", account.getWithdrawAmount()));
		// 银行卡cardCounts
		 dataMap.put("cardCounts",cardCounts);
		 
		 // 我的银行卡列表
		 // 支付卡
		 dataMap.put("payCardList",bankCardBusiness.findPayCardList(userId));
		 // 提现卡
		 dataMap.put("settleCardList",bankCardBusiness.findSettleCardList(userId));
		 // 获取用户真实姓名和身份证号
	     User user = userBusiness.getUserById(userId);
		 dataMap.put("name",user.getContacts());
		 dataMap.put("phone",DataUtils.getJiamiPhone(user.getCell()));
		 String identity =  user.getIdentity();
		 if(null != identity && !"".equals(identity)){
			 StringBuffer buff = new StringBuffer();
			 buff.append(identity.substring(0, 3)).append("*********");
			 buff.append(identity.substring(identity.length()-4));
			 identity = buff.toString();
		 }
		 
		 dataMap.put("identity",identity);
		 return outSucceed(dataMap, false, "");
	}
	
	/**
	 * 收支详情列表
	* @Title: paymentDetails
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param request
	* @param response
	* @param jsonInquiry
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月12日 上午11:38:36
	 */
	@RequestMapping(value="paymentDetails")
	@ResponseBody
	@Deprecated
	public String paymentDetails(@RequestBody JSONObject jsonObj ,Pagination<AccountOperationRecordDto> pagination) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			
			Long userId = getUserId();
			Account account = accountBusiness.getAccountByUserId(userId);
			//我的账户页面
			AccountOperationRecordDto accountDto = new AccountOperationRecordDto();
			accountDto.setUserId(userId);
			Aide aide = new Aide();
			pagination =  accountBusiness.findUserOperationPage(accountDto, pagination, aide);
			
			List<AssAccountOperationVo> list =  new ArrayList<AssAccountOperationVo>();
			
			if(null != pagination.getDatas()){
				for (AccountOperationRecordDto obj : pagination.getDatas()) {
					AssAccountOperationVo assAccountOperationVo = new AssAccountOperationVo();
					assAccountOperationVo.setCreateTime(obj.getCreateTime().getTime());
					assAccountOperationVo.setBalanceStr(obj.getBalanceString());
					assAccountOperationVo.setOperationAmount(obj.getOperationAmount());
					assAccountOperationVo.setOperationTypeStr(obj.getAction());
					assAccountOperationVo.setPositive(false);
					assAccountOperationVo.setTarget(obj.getTarget());
					list.add(assAccountOperationVo);
				}
			}
			AssPagination<AssAccountOperationVo> newpag = new AssPagination<AssAccountOperationVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(list);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("payDetail", newpag);
			 return outSucceed(dataMap, false, "");
		} catch (NumberFormatException e) {
			_logger.error("查询 收支详情失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (AccountException e) {
			_logger.error("查询 收支详情失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("查询 收支详情失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: paymentList 
	 * @Description: 收支详情列表页 
	 * @param jsonObj
	 * @param pagination
	 * @return
	 * @date 2015年8月3日 下午5:26:02  
	 * @author cbc
	 */
	@RequestMapping(value="paymentList")
	@ResponseBody
	public String paymentList(@RequestBody JSONObject jsonObj ,Pagination<AccountOperationRecordDto> pagination) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			Object typeObj = jsonObj.get("type");
			Integer type = null;
			if (null != typeObj && !"".equals(typeObj)) {
				Integer typeInt = Integer.valueOf(typeObj.toString());
				if (0 != typeInt) {
					type = typeInt;
				}
			}
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Long userId = getUserId();
			Account account = accountBusiness.getAccountByUserId(userId);
			dataMap.put("withdrawAmount",DataUtils.formatMoney("##0.00", account.getWithdrawAmount()));
			Long freezeAmount = accountBusiness.getFreezeAmount(userId);
			dataMap.put("freezeAmount", DataUtils.formatMoney("##0.00", freezeAmount));
			
			Aide aide = new Aide();
			pagination = accountBusiness.findUserOperationPageByType(String.valueOf(userId), pagination, aide, type);
			
			List<AccountOperationRecordDto> datas = pagination.getDatas();
			List<AssAccountOperationVo> list =  new ArrayList<AssAccountOperationVo>();
			if (null != datas && !datas.isEmpty()) {
				for (AccountOperationRecordDto obj : datas) {
					AssAccountOperationVo assAccountOperationVo = new AssAccountOperationVo();
					assAccountOperationVo.setId(obj.getId());
					assAccountOperationVo.setCreateTime(obj.getCreateTime().getTime());
					assAccountOperationVo.setBalanceStr(obj.getUserBalanceString());
					assAccountOperationVo.setOperationAmount(obj.getOperationAmount());
					assAccountOperationVo.setOperationTypeStr(obj.getUserAction());
					if (AccountOperationRecordDto.USRFROMTYPES.contains(obj.getOperationType().intValue())) {
						assAccountOperationVo.setPositive(false);
					} 
					if (AccountOperationRecordDto.USRTOTYPES.contains(obj.getOperationType().intValue())) {
						assAccountOperationVo.setPositive(true);
					}
					list.add(assAccountOperationVo);
				}
			}
			AssPagination<AssAccountOperationVo> newpag = new AssPagination<AssAccountOperationVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(list);
			dataMap.put("payDetail", newpag);
			return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("查询 收支明细列表失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 
	 * @Title: paymentDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj
	 * @return
	 * @date 2015年8月3日 下午6:25:57  
	 * @author cbc
	 */
	@RequestMapping(value="paymentDetail")
	@ResponseBody
	public String paymentDetail(@RequestBody JSONObject jsonObj) {
		try {
			Object idObj = jsonObj.get("id");
			if (null == idObj || "".equals(idObj)) {
				return outFailure(AssConstantsUtil.AccountCode.NO_PARAM_ID, "");
			}
			Integer id = Integer.valueOf(idObj.toString());
			Long userId = super.getUserId();
			AccountOperationRecordDto obj = accountBusiness.getOperationRecordById(id.longValue(), userId);
			
			AssAccountOperationVo assAccountOperationVo = new AssAccountOperationVo();
			assAccountOperationVo.setId(obj.getId());
			assAccountOperationVo.setCreateTime(obj.getCreateTime().getTime());
			assAccountOperationVo.setBalanceStr(obj.getUserBalanceString());
			assAccountOperationVo.setOperationAmount(obj.getOperationAmount());
			assAccountOperationVo.setOperationTypeStr(obj.getUserAction());
			assAccountOperationVo.setSerialNumber(obj.getSerialNumber());
			if (AccountOperationRecordDto.USRFROMTYPES.contains(obj.getOperationType().intValue())) {
				assAccountOperationVo.setPositive(false);
			} 
			if (AccountOperationRecordDto.USRTOTYPES.contains(obj.getOperationType().intValue())) {
				assAccountOperationVo.setPositive(true);
			}
			assAccountOperationVo.setRemark(obj.getRemark());
			assAccountOperationVo.setPayWayFlag(obj.getPayWayFlag());
			return outSucceed(assAccountOperationVo, false, "");
		} catch (Exception e) {
			_logger.error("查询收支详情失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	
   /**
	* 银行卡提现
	* @Title: validWithdrawData
	* @Description: TODO( 银行卡提现)
	* @param bankCardDto
	* @param paymentCode
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月13日 上午9:27:17
	*/
	@RequestMapping(value="withdrawal")
	@ResponseBody
	public String withdrawal(@RequestBody JSONObject jsonObj) {
		try {
			// 验证码
			String mobilecode = jsonObj.getString("mobilecode");
			if (null == mobilecode||"".equals(mobilecode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long userId = getUserId();
			// 查询超管
			User user = userBusiness.getUserById(userId);
			// 返回1 验证通过
			Integer result = phoneBusiness.checkPhoneCode(user.getCell(), mobilecode,8);
			if (null == result || 1 != result.intValue()) {
				// 验证码错误
				return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE, "");
			}
			// 金额
			String withdrawPrice = jsonObj.getString("withdrawPrice");
			if (null == withdrawPrice ||"".equals(withdrawPrice)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String paymentCode = jsonObj.getString("paymentCode");
			if (null == paymentCode ||"".equals(paymentCode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			//银行卡用户信息
			String cardNo = jsonObj.getString("bankCard");
			if (null == cardNo ||"".equals(cardNo)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String bankCode = jsonObj.getString("bankCode");
			if (null == bankCode ||"".equals(bankCode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Account account = accountBusiness.getAccountByUserId(userId);
			BankCard bankCard = new BankCard();
			bankCard.setBankCard(cardNo);
			bankCard.setName(user.getContacts());
			bankCard.setBankCode(bankCode);
			withdrawBusiness.withdraw(bankCard, withdrawPrice, account.getId(),userId,paymentCode,user.getCell());
				return outSucceedByNoData();
		}catch (AccountException e) {
			_logger.error("银行卡提现失败:",e);
			//String errCode = e.getErrCode();
			String message = e.getMessage();
			if (message.contains("ACCT_40010")){
				return outFailure(AssConstantsUtil.AccountCode.ACCOUNT_NO_WITHDRAWAL_CODE, "");
			}else if (message.contains("The paymentcode")){
				return outFailure(AssConstantsUtil.AccountCode.PASSWORD_CODE, "");
			}else{
				return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
			}
		} catch (Exception e) {
			_logger.error("银行卡提现失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	

}
