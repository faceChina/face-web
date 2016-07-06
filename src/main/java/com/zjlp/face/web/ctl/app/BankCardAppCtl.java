package com.zjlp.face.web.ctl.app;

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

import com.zjlp.face.web.constants.Bank;
import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.user.bankcard.business.BankCardBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.util.AssConstantsUtil;

/**
 * 银行卡
 * @author LYS
 *
 */
@Controller
@RequestMapping({"/assistant/ass/account/bankcard/"})
public class BankCardAppCtl extends BaseCtl {
	//日志
	Logger _logger = Logger.getLogger(getClass());
	//服务
	@Autowired
	private AccountBusiness accountBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private BankCardBusiness bankCardBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	
	public static final String[] BANK_JSON_FIELDS = {"bankCode","bankName"};
	/**
	 * 获取银行列表接口
	* @Title: getBankList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param request
	* @param response
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月12日 下午8:04:05
	 */
	@RequestMapping(value = "getBankList")
	@ResponseBody
	public String getBankList() {
		
		try {
			 Map<String,Object> dataMap = new HashMap<String,Object>();
			 dataMap.put("withdrawCardList",Bank.withdrawCardList);
			//dataMap.put("debiBbankList",Bank.getDebitCardList());
			 return outSucceed(Bank.withdrawCardList, true, BANK_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("获取银行列表失败"+e.getMessage(),e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	
	
	/**
	 * 删除消费银行卡
	 * @param bankCardDto
	 * @param model
	 * @return
	 */
	@RequestMapping(value="delPayCard")
	@ResponseBody
	public String  delPayCard(@RequestBody JSONObject jsonObj) {
		
		try {
			Long cardId = jsonObj.getLong("cardId");
			if (null==cardId){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			bankCardBusiness.removeBankCard(getUserId(), cardId);
			return outSucceedByNoData();
		} catch (BankCardException e) {
			_logger.error("删除消费银行卡:",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("删除消费银行卡:",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 添加消费银行卡
	 * @param bankCardDto
	 * @param model
	 * @return
	 */
	@RequestMapping(value="addCard")
	@ResponseBody
	public String addCard(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			// 验证码
			String mobilecode = jsonObj.getString("mobilecode");
			if (null == mobilecode||"".equals(mobilecode)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			//手机
			String phone =  jsonObj.getString("phone");
			 if (null == phone || "".equals(phone)){
				 return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			 }
			Integer result = phoneBusiness.checkPhoneCode(phone, mobilecode, 7);
			if (null == result || 1 != result.intValue()) {
				// 验证码错误
				return outFailure(AssConstantsUtil.System.AUTH_CODE_ERROR_CODE, "");
			}
			// 身份证号
			/*String identity =  jsonObj.getString("identity");
			if (null == identity || "".equals(identity)){
				return outFailure(AssConstantsUtil.DATA_ERROR_CODE, "");
			}*/
			//银行编码
			 String bankCode =  jsonObj.getString("bankCode");
			 if (null == bankCode || "".equals(bankCode)){
				 return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			 }
			//银行卡号
			 String bankCardNo =  jsonObj.getString("bankCardNo");
			 if (null == bankCardNo || "".equals(bankCardNo)){
				 return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			 }
			//用户名
			String name =  jsonObj.getString("name");
			if (null == name || "".equals(name)){
				 return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			 }
			//贷记卡有效期
		    String endTime = jsonObj.optString("endTime");
		    //贷记卡安全码
		    String cvv = jsonObj.optString("cvv");
			bankCardBusiness.addPayBankCard(getUserId(), bankCode, bankCardNo, phone,
					endTime, cvv);
			return outSucceedByNoData();
		} catch (BankCardException e) {
			_logger.error("添加消费银行卡失败:",e);
			return outFailure(AssConstantsUtil.AccountCode.ADD_CARD_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("添加消费银行卡失败:",e);
			return outFailure(AssConstantsUtil.AccountCode.ADD_CARD_ERROR_CODE, "");
		}
	}
	/**
	 * 默认银行卡的设置
	 * @param bankCardDto
	 * @param model
	 * @return
	 */
	@RequestMapping(value="setDefault")
	@ResponseBody
	public String setDefault(@RequestBody JSONObject jsonObj) {
		try {
			Long cardId = jsonObj.getLong("cardId");
			if (null==cardId){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			/*Integer type = jsonObj.getInt("type");
			if (null==type){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}*/
			bankCardBusiness.setDefaultCard(getUserId(), cardId);
			return outSucceedByNoData();
		} catch (BankCardException e) {
			_logger.error("设置默认银行卡失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("设置默认银行卡失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * =================================================
	 * APP 2.2版本 功能
	 * =================================================
	 */
	/**
	 * 卡bin获取
	* @Title: getCardbin
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param bankCard
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年3月6日 下午2:23:07
	 */
	@RequestMapping(value ="cardbin", method = RequestMethod.POST)
	@ResponseBody
	public String getCardbin(@RequestBody JSONObject jsonObj) {
		try {
			String bankCard = jsonObj.getString("bankCard");
			if (null==bankCard || "".equals(bankCard)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			BankCard card = bankCardBusiness.getCardInfo(bankCard);
			if (null == card) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			if (null == Bank.getBankByCode(card.getBankCode())) {
				return outFailure(AssConstantsUtil.AccountCode.CARD_PLATFORM_NOT_SUPPORT, "");
			}
			return outSucceed(card, true,new String[]{"bankType", "bankCode", "bankName"});
		} catch (Exception e) {
			_logger.error("卡号输入错误:",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
}
