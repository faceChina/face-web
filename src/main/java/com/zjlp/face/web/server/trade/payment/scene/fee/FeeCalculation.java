package com.zjlp.face.web.server.trade.payment.scene.fee;

import org.apache.log4j.Logger;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;

/**
 * 手续费计算策略
* @ClassName: FeeCalculation 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月20日 下午2:15:05 
*
 */
public class FeeCalculation {

	private Logger _logger = Logger.getLogger(this.getClass());
	
	private FeeScene feeScene;
	
	@SuppressWarnings("unused")
	private FeeCalculation(){};
	/**
	 * @param type 支付类型  1钱包支付 2.借记卡 3.贷记卡 4.微信 5.捷蓝
	 */
	public FeeCalculation(Integer type){
		try {
			/** 0,不收手续费;1,收手续费*/
			String isPlatformFee = PropertiesUtil.getContexrtParam("SWITCH_PLATFORM_IS_GET_FEE");
			AssertUtil.hasLength(isPlatformFee, "未配置SWITCH_PLATFORM_IS_GET_FEE");
			
			//借记卡
			if(type == 2){
				feeScene = new DebitCardScene();
				_logger.info("当前手续费收取场景[平台不收手续费+借记卡]");
			} 
			//信用卡
			else if(type == 3){
				feeScene = new CreditCardScene();
				_logger.info("当前手续费收取场景[平台不收手续费+信用卡]");
			}
			/*//平台收取手续费+2借记卡
			else if("1".equals(isPlatformFee) && type == 2){
				feeScene = new PlatformAndDebitCardScene();
				_logger.info("当前手续费收取场景[平台收取手续费+借记卡]");
			} 
			//平台收取手续费+3信用卡
			else if("1".equals(isPlatformFee) && type == 3){
				feeScene = new PlatformAndCreditCardScene();
				_logger.info("当前手续费收取场景[平台收取手续费+信用卡]");
			} */
			//平台不收取手续费+4微信支付
			else if("0".equals(isPlatformFee) && type == 4){
				feeScene = new WechatScene();
				_logger.info("当前手续费收取场景[平台不收取手续费+微信支付]");
			}
			//平台收取手续费+4微信支付
			else if("1".equals(isPlatformFee) && type == 4){
				feeScene = new PlatformAndWechatScene();
				_logger.info("当前手续费收取场景[平台收取手续费+微信支付]");
			}
			//钱包付款
			else if(type == 1) {
				feeScene = new WalletScene();
				_logger.info("当前手续费收取场景[钱包支付，不收取手续费]");
			} 
			else if(type == 5) {
				feeScene = new BindPayScene();
				_logger.info("当前手续费收取场景[捷蓝支付]");
			}
			else if(type == 6) {
				feeScene = new AlipayScene();
				_logger.info("当前手续费收取场景[支付宝支付]");
			}
			else{
				_logger.info("当前手续费收取场景[参数异常]");
				throw new PaymentException("计算手续费场景异常");
			}
		} catch (Exception e) {
			throw new PaymentException(e);
		}
	}
	
	public FeeDto calculation(Long amount){
		try {
			return feeScene.calculation(amount);
		}  catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException("计算手续费发生异常",e);
		}
	}
}
