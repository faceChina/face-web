package com.zjlp.face.web.server.trade.account.domain.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.zjlp.face.account.domain.AccountOperationRecord;
import com.zjlp.face.util.constants.ConstantsMethod;

public class AccountOperationRecordDto extends AccountOperationRecord {

	private static final long serialVersionUID = -8272165440394726363L;

	private Long userId;

	private String shopNo;
	//来源
	private String source;
	//超管动作
	private String userAction;
	
	//支付方式
	private String payWay;
	
	/**
	 * 
	 * @Title: getRecieve 
	 * @Description: 收入金额 
	 * @return
	 * @date 2015年8月4日 下午3:53:30  
	 * @author cbc
	 */
	public String getRecieve() {
		if (!getUserIsPay()) {
			return getUserOperationPrice();
		} else {
			return "— —";
		}
	}


	/**
	 * 
	 * @Title: getPay 
	 * @Description: 支出金额
	 * @return
	 * @date 2015年8月4日 下午3:53:41  
	 * @author cbc
	 */
	public String getPay() {
		if (getUserIsPay()) {
			return getUserOperationPrice();
		} else {
			return "— —";
		}
	}

	public String getPayWay() {
		Integer operationType = super.getOperationType();
		if (null == operationType) {
			return null;
		} else {
			switch (operationType) {
			case 1:
				return "银行卡-支付宝支付";
			case 2:
				return "钱包支付";
			default:
				return null;
			}
		}
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
	public static void setAttribution(List<AccountOperationRecordDto> list, Long userId) {
		if (null == list || list.isEmpty() || null == userId) {
			return;
		}
		for (AccountOperationRecordDto record : list) {
			if (null == record) continue;
			record.setUserId(userId);
		}
	}
	
	public static void setAttribution(List<AccountOperationRecordDto> list, String shopNo) {
		if (null == list || list.isEmpty() || null == shopNo) {
			return;
		}
		for (AccountOperationRecordDto record : list) {
			if (null == record) continue;
			record.setShopNo(shopNo);
		}
	}
	
	

	public String getUserAction() {
		Integer operationType = super.getOperationType();
		if (null == operationType)
			return null;
		switch (operationType) {
		case 1:
			return "在线支付";
		case 2:
			return "在线支付";
		case 3:
			return "提现";
		case 5:
			return "提现";
		case 6:
			return "提现";
		case 4:
			return "佣金收入";
		case 11:
			return "店铺收入";
		case 12:
			return "店铺收入"; // 平台
		case 13:
			return "佣金收入";
		case 14:
			return "店铺收入";
		case 15:
			return "店铺收入";
			// case 16: return "卖家退款";
		case 17:
			return "店铺收入";
		case 18:
			return "佣金收入";
		case 19:
			return "佣金收入";
		case 20:
			return "佣金收入";
		case 21:
			return "佣金收入";
		default:
			return null;
		}
	}
	
	public String getSource() {
		Integer operationType = super.getOperationType();
		if (null == operationType)
			return null;
		switch (operationType) {
		case 1:
			return "店铺钱包";
		case 2:
			return "店铺钱包";
		case 3:
			return "店铺钱包";
		case 5:
			return "店铺钱包";
		case 6:
			return "店铺钱包";
		case 4:
			return "店铺钱包";
		case 11:
			return "店铺订单-自营";
		case 12:
			return "脸谱平台"; // 平台
		case 13:
			return "全民推广";
//		case 14:
//			return "收入-彩票中奖";
		case 15:
			return "店铺钱包";
//			// case 16: return "卖家退款";
		case 17:
			return "脸谱平台";
		case 18:
			return "订单分销推广";
		case 19:
			return "全民推广";
		case 20:
			return "脸谱平台";
		case 21:
			return "佣金收入";
		default:
			return null;
		}
	}
	
	public String getAction() {
		Integer operationType = super.getOperationType();
		if (null == operationType)
			return null;
		switch (operationType) {
		case 1:
			return "在线支付";
		case 2:
			return "在线支付";
		case 3:
			return "提现";
		case 5:
			return "提现失败解冻";
		case 6:
			return "提现成功冻结金额转出";
		case 4:
			return "佣金转出";
		case 11:
			return "余额解冻";
		case 12:
			return "手续费收益"; // 平台
		case 13:
			return "佣金收益";
		case 14:
			return "收入-彩票中奖";
		case 15:
			return "店铺收入";
			// case 16: return "卖家退款";
		case 17:
			return "平台赠送";
		case 18:
			return "分销佣金收入";
		case 19:
			return "全民推广佣金收入";
		case 20:
			return "员工收益";
		case 21:
			return "佣金收入";
		default:
			return null;
		}
	}

	/**
	 * 收支对象
	 * 
	 * @Title: getTarget
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return
	 * @date 2015年1月14日 上午10:59:51
	 * @author lys
	 */
	public String getTarget() {
		Integer operationType = super.getOperationType();
		if (null == operationType)
			return null;
		boolean isform = getIsFrom();
		switch (operationType) {
		case 1:
			return this.getCardForView();
		case 2:
			return "我的钱包";
		case 3:
			return this.getCardForView(); // 提现银行卡
		case 5:
			return "平台账户";
		case 6:
			return this.getCardForView();
		case 4:
			return "佣金账户"; // TODO
		case 11:
			return "平台账户";
			// case 12: return "建设银行*********219"; //TODO 系统账户收益 卖家银行卡
		case 13:
			return "平台账户";
		case 14:
			return "平台账户";
		case 15:
			return isform ? "我的钱包" : "子账户";
			// case 16: return "卖家退款"; //TODO
		case 17:
			return "平台账户";
		case 18:
			return "平台账户";
		case 19:
			return "平台账户";
		case 20:
			return "我的钱包";
		case 21:
			return "佣金分成";
		default:
			return null;
		}
	}
	
	public Boolean getIsFrom() {
		//收支明细
		Integer roleType = null != this.userId ? ROLES[0] : ROLES[1];
		Integer operationType = super.getOperationType();
		if (null == roleType || null == operationType) {
			return null;
		}
		if (ROLES[0].equals(roleType) && USRFROMTYPES.contains(operationType)
				|| ROLES[1].equals(roleType) && SPFROMTYPES.contains(operationType)
				|| ROLES[2].equals(roleType) && PLATFROMTYPES.contains(operationType)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getBalanceString() {
		Boolean isFrom = getIsFrom();
		Boolean isCommissionOperation = isCommissionOperation();
		if (null == isFrom || null == isCommissionOperation) {
			return null;
		}
		Long price = null;
		if (isFrom) {
			if (isCommissionOperation) {
				price = super.getFromCommissionAfter();
			} else {
				price = super.getFromAmountAfter();
			}
		} else {
			if (isCommissionOperation) {
				price = super.getToCommissionAfter();
			} else {
				price = super.getToAmountAfter();
			}
		}
		return (null == price) ? null : new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
	}
	
	public String getOperationPrice() {
		Long price = super.getOperationAmount();
		if (null == price ) {
			return null;
		}
		String priceString = new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
		//提现
		if (null != super.getWithdrawalStates()) {
			return "-"+priceString;
		}
		//收支明细
		Boolean isFrom = getIsFrom();
		if (null == isFrom) {
			return null;
		} else if (isFrom) {
			return "-"+priceString;
		} else {
			return "+"+priceString;
		}
	}
	
	private Boolean isCommissionOperation() {
		Integer operationType = super.getOperationType();
		if (null == operationType) {
			return null;
		}
		if (4==operationType || 13 ==operationType || 14==operationType) {
			return true;
		}
		return false;
	}

	public String getCardForView() {
		String card = super.getBankCard();
		if (null == card || "".equals(card)) {
			return null;
		}
		return ConstantsMethod.replaceToHide(card, 3, 3);
	}

	// 角色类型
	private static final Integer[] ROLES = { 1, 2, 3 };
	
	// 用户
	// 用户资金流出
	public static final List<Integer> USRFROMTYPES = Arrays.asList(1, 2, 3); 
	// 用户资金流入
	public static final List<Integer> USRTOTYPES = Arrays.asList(14, 15, 16, 17, 19, 21); 
	
	// 店铺
	// 店铺资金流出
	public static final List<Integer> SPFROMTYPES = Arrays.asList(3, 15, 16); 
	// 店铺资金流入
	public static final List<Integer> SPTOTYPES = Arrays.asList(11, 13, 18); 
	
	
	// 平台
	// 平台资金流出
	public static final List<Integer> PLATFROMTYPES = Arrays.asList(3, 15, 16, 17); 
	// 平台资金流入
	public static final List<Integer> PLATTOTYPES = Arrays.asList(4, 11, 13, 15); 
	
	/**收入*/
	public static final List<Integer> RECIEVE_TYPES = Arrays.asList(14, 15, 16, 17, 19, 21);
	/**支出*/
	public static final List<Integer> PAY_TYPES = Arrays.asList(1, 2);
	/**提现*/
	public static final List<Integer> WITHDRAW_TYPES = Arrays.asList(3);
	
	/**
	 * 
	 * @Title: getUserIsFrom 
	 * @Description: 在用户角色下，判断是否为支出,true为支出，false为收入
	 * @return
	 * @date 2015年8月4日 下午3:46:44  
	 * @author cbc
	 */
	private Boolean getUserIsPay() {
		Integer type = super.getOperationType();
		return USRFROMTYPES.contains(type);
	}
	
	/**
	 * 
	 * @Title: getUserOperationPrice 
	 * @Description: 在用户角色下，获取操作金额
	 * @return
	 * @date 2015年8月4日 下午3:51:43  
	 * @author cbc
	 */
	public String getUserOperationPrice() {
		Long price = super.getOperationAmount();
		if (null == price ) {
			return null;
		}
		String priceString = new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
		//提现
		if (null != super.getWithdrawalStates()) {
			return "-"+priceString;
		}
		//收支明细
		Boolean isPay = getUserIsPay();
		if (null == isPay) {
			return null;
		} else if (isPay) {
			return "-"+priceString;
		} else {
			return "+"+priceString;
		}
	}
	/**
	 * 
	 * @Title: getUserBalanceString 
	 * @Description: 在用户角色下，获取余额
	 * @return
	 * @date 2015年8月4日 下午4:00:04  
	 * @author cbc
	 */
	public String getUserBalanceString() {
		Boolean isPay = getUserIsPay();
		Boolean isCommissionOperation = isCommissionOperation();
		if (null == isPay || null == isCommissionOperation) {
			return null;
		}
		Long price = null;
		if (isPay) {
			if (isCommissionOperation) {
				price = super.getFromCommissionAfter();
			} else {
				price = super.getFromAmountAfter();
			}
		} else {
			if (isCommissionOperation) {
				price = super.getToCommissionAfter();
			} else {
				price = super.getToAmountAfter();
			}
		}
		return (null == price) ? null : new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
	}
}
