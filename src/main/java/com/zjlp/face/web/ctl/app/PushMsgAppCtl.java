package com.zjlp.face.web.ctl.app;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.server.product.push.business.AppPushMessageBusiness;
import com.zjlp.face.web.server.product.push.domain.AppPushMessage;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.ConvertUtil;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年7月21日 下午3:50:16
 *
 */
@Controller
@RequestMapping({ "/assistant/ass/push/" })
public class PushMsgAppCtl extends BaseCtl {

	private Logger _logger = Logger.getLogger(getClass());

	private static final String COMMA = ",";
	
	private static final int ONE = 1;

	private static final int ZERO = 0;

	@Autowired
	private AppPushMessageBusiness appPushMessageBusiness;

	/**
	 * @Title: findUnReadMsg
	 * @Description: (根据用户ID查找未读PUSH消息)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月21日 下午3:50:25
	 */
	@RequestMapping(value = "findUnReadMsg")
	@ResponseBody
	public String findUnReadMsg(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long userId = super.getUserId();
			if (userId == null) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 消息类型,0交易消息 1商品消息 2系统消息 3预定消息 4预约消息，为空则不过滤
			Object msgTypeObj = jsonObj.get("msgType");
			Integer msgType = null;
			if (msgTypeObj != null && StringUtils.isNotBlank(msgTypeObj.toString())) {
				msgType = Integer.parseInt(msgTypeObj.toString());
			}
//			if (msgType == null) {
//				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
//			}
			AppPushMessage appPushMessage = new AppPushMessage();
			appPushMessage.setUserId(userId);
			appPushMessage.setIsRead(ONE);
			if (msgType != null) {
				appPushMessage.setMsgType(msgType);
			}
			List<AppPushMessage> result = this.appPushMessageBusiness.findAppPushMessageByUserId(appPushMessage);
			return outSucceed(result, false, "");
		} catch (Exception e) {
			_logger.info("查找未读PUSH消息失败");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: updateUnReadMsg
	 * @Description: (根据消息ID查找未读PUSH消息)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月21日 下午3:50:37
	 */
	@RequestMapping(value = "updateUnReadMsg")
	@ResponseBody
	public String updateUnReadMsg(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String msgIdStr = jsonObj.optString("msgIdList");// str="1,2,3,4,5";
			if (StringUtils.isEmpty(msgIdStr)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			List<Long> msgIdList = ConvertUtil.splitString(msgIdStr, COMMA);
			for (Long id : msgIdList) {
				AppPushMessage appPushMessage = new AppPushMessage();
				appPushMessage.setId(id);
				appPushMessage.setIsRead(ZERO);
				this.appPushMessageBusiness.updateAppPushMessage(appPushMessage);
				_logger.info("更新PUSH消息成功.");
			}
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.info("更新PUSH消息失败");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: removeReadMsg
	 * @Description: (移除未读消息)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月21日 下午3:50:46
	 */
	@RequestMapping(value = "removeReadMsg")
	@ResponseBody
	public String removeReadMsg(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String msgIdStr = jsonObj.optString("msgIdList");// str="1,2,3,4,5";
			if (StringUtils.isEmpty(msgIdStr)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			List<Long> msgIdList = ConvertUtil.splitString(msgIdStr, COMMA);
			if (CollectionUtils.isNotEmpty(msgIdList)) {
				for (Long id : msgIdList) {
					AppPushMessage appPushMessage = new AppPushMessage();
					appPushMessage.setId(id);
					appPushMessage.setIsRead(ZERO);
					AppPushMessage readMsg = this.appPushMessageBusiness.findReadMsgById(appPushMessage);
					if (readMsg != null) {
						_logger.info("即将删除 PUSH消息 ，ID=" + readMsg.getId());
						this.appPushMessageBusiness.removeAppPushMessage(readMsg.getId());
						_logger.info("删除 PUSH消息成功.");
					}
				}
			}
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.info("删除PUSH消息失败");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	@RequestMapping(value = "findFancyMsg")
	@ResponseBody
	public String findFancyMsg(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object idObj = jsonObj.get("msgId");
			Long id = null;
			if (null != idObj && !"".equals(idObj)) {
				id = Long.valueOf(idObj.toString());
			}
			// 消息类型 刷脸精选
			AppPushMessage appPushMessage = new AppPushMessage();
			appPushMessage.setId(id);
			appPushMessage.setIsRead(ONE);
			appPushMessage.setMsgType(2);
			List<AppPushMessage> result = this.appPushMessageBusiness.findAppPushMessage(appPushMessage);
			return outSucceed(result, false, "");
		} catch (Exception e) {
			_logger.info("查找未读PUSH消息失败");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

}
