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

import com.zjlp.face.web.appvo.OperateDataVo;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;

/**
 * 运营数据控制层（管家助手）
* @ClassName: OperateDataAssistantCtl
* @Description: 运营数据控制层（管家助手）
* @author wxn
* @date 2014年12月19日 下午1:03:34
*
 */
@Controller
@RequestMapping({ "/assistant/ass/"})
public class OperateDataAssistantCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(this.getClass());
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private OperateDataBusiness operateDataBusiness;
	
	/**
	* 查询用户近七天运营数据
	* @Title: operate
	* @Description: (查询用户近七天运营数据) 
	* @param model
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2014年12月11日 下午3:39:44
	 */
	@RequestMapping(value="operate",method = RequestMethod.POST)
	@ResponseBody
	public String operate(){
		//OperateVo operateVo = new OperateVo();
		try {
	/*			// 1.1  根据用户id查询运营数据
				operateVo = salesOrderBusiness.findOperateDataForHaaByUserId(super.getUserId());
			if (null == operateVo){
				return outFailure(AssConstantsUtil.SERVER_ERROR_CODE, "");
			}
			List<OperateDetailVo> list = operateVo.getOperateDetailVos();
			
			 List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
			
			for (OperateDetailVo operateDetailVo : list) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("day", operateDetailVo.getMonth());
				String amountStr = "0";
				if (null != operateDetailVo.getTurnove()){
					DecimalFormat df = new DecimalFormat("##0.00");
					amountStr = df.format(operateDetailVo.getTurnove()/100.0);
				}
				map.put("amountStr", amountStr);
				maplist.add(map);
			}*/
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("daysArray", "");
			return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("查询用户近七天运营数据", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	} 
	
	/**
	 * 供货商经营数据接口
	 * @Title: supplierOperateData 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj
	 * @return
	 * @date 2015年7月21日 上午10:42:24  
	 * @author lys
	 */
	@RequestMapping(value="supplieroperate",method = RequestMethod.POST)
	@ResponseBody
	public String supplierOperateData(@RequestBody JSONObject jsonObj){
		try {
			Boolean isRefresh = false;
			if (null == jsonObj || null == (isRefresh = jsonObj.getBoolean("isRefresh"))) {
				return outFailure(AssConstantsUtil.OperateData.NULL_PARAM_ISREFRESH);
			}
			Long userId = super.getUserId();
			Long recivePrice = operateDataBusiness.supplierRecivePrice(userId);
			Long payCommission = operateDataBusiness.supplierPayCommission(userId);
			Integer orderCount = operateDataBusiness.supplierPayOrderCount(userId);
			Integer consumerCount = operateDataBusiness.supplierConsumerCount(userId);
			OperateDataVo result = new OperateDataVo();
			result.setRecivePrice(recivePrice);
			result.setPayCommission(payCommission);
			result.setOrderCount(orderCount);
			result.setConsumerCount(consumerCount);
			//七天经营数据
			if (!isRefresh) {
				Map<String, Long> weekRecivePrice = operateDataBusiness.supplierRecivePrices(userId);
				Map<String, Long> weekPayCommission = operateDataBusiness.supplierPayCommissions(userId);
				result.setWeekPayCommission(OperateDataVo.mapToList(weekPayCommission));
				result.setWeekRecivePrice(OperateDataVo.mapToList(weekRecivePrice));
			}
			return super.outSucceed(result);
		} catch (Exception e) {
			_logger.error("查询供货商运营数据", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE);
		}
	}
	
	/**
	 * 
	 * @Title: subbranchOperateData 
	 * @Description: 分店经营数据统计 
	 * @param jsonObj
	 * @return
	 * @date 2015年7月22日 上午10:52:32  
	 * @author cbc
	 */
	@RequestMapping(value="subbranchoperate", method=RequestMethod.POST)
	@ResponseBody
	public String subbranchOperateData(@RequestBody JSONObject jsonObj) {
		try {
			Boolean isRefresh = false;
			if (null == jsonObj || null == (isRefresh = jsonObj.getBoolean("isRefresh"))) {
				return outFailure(AssConstantsUtil.OperateData.NULL_PARAM_ISREFRESH);
			}
			Long userId = super.getUserId();
			User user = userBusiness.getUserById(userId);
			String subbranchIdStr = jsonObj.getString("subbranchId");
			if (null == subbranchIdStr || "".equals(subbranchIdStr)) {
				return outFailure(AssConstantsUtil.OperateData.NULL_PARAM_SUBBRANCHID);
			}
			Long subbranchId = Long.valueOf(subbranchIdStr);
			OperateDataVo result = new OperateDataVo();
			Long todaySubbranchCommission = operateDataBusiness.countTodaySubbranchCommission(subbranchId, user.getCell());
			Long countTodayShopCommission = operateDataBusiness.countTodayShopCommission(subbranchId, user.getCell());
			Integer countTodayPayOrder = operateDataBusiness.countTodayPayOrder(subbranchId);
			Integer countTodayCustomer = operateDataBusiness.countTodayCustomer(subbranchId);
			Long countTodayCommission = operateDataBusiness.countTodayCommission(subbranchId);
			result.setReciveCommission(countTodayCommission);//今日佣金总收入
			result.setReciveSpCommission(countTodayShopCommission);//今日店铺佣金收入
			result.setReciveFdCommission(todaySubbranchCommission);//今日分店佣金收入
			result.setOrderCount(countTodayPayOrder);//今日付款订单
			result.setConsumerCount(countTodayCustomer);//今日客户数
			if (!isRefresh) {
				Map<String, Long> map = operateDataBusiness.countSubbranchWeekCommission(subbranchId);
				result.setWeekReciveCommission(OperateDataVo.mapToList(map));
			}
			return super.outSucceed(result);
		} catch (Exception e) {
			_logger.error("查询分店运营数据", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE);
		}
	}
	
}
