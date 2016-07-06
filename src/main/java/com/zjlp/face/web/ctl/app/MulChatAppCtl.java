package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.server.user.mulchat.business.MulChatInformationBusiness;
import com.zjlp.face.web.server.user.mulchat.business.MulChatQRCodeBusiness;
import com.zjlp.face.web.server.user.mulchat.business.MulChatViewNickBusiness;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatInformation;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatViewNick;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.DataUtils;

/**
 * 群组信息以及我在群组显示昵称
 * 
 * @author Baojiang Yang
 *
 */
@Controller
@RequestMapping({ "/assistant/ass/mulchat/" })
public class MulChatAppCtl extends BaseCtl {

	private Logger _logger = Logger.getLogger(getClass());
	
	private static final String[] FIND_MUL_CHAT_GROUP_FILLTER = { "groupId", "name", "pictureUrl" };
	
	private static final String[] FIND_MUL_CHAT_NICK_FILLTER = { "groupId", "loginAccount", "viewNick" };

	private static final String[] ADD_QR_CODE_FILLTER = { "qRCode", "overdueDate","qRCodeUrl" };

	private static final String[] SCAN_QRCODE_FILLTER = { "qRCode", "createUser", "groupId" };

	@Autowired
	private MulChatInformationBusiness mulChatInfBusiness;

	@Autowired
	private MulChatViewNickBusiness mulChatViewNickBusiness;

	@Autowired
	private MulChatQRCodeBusiness mulChatQRCodeBusiness;

	/**
	 * 添加群组信息
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addGroupInfo")
	@ResponseBody
	public String addGroupInfo(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群组ID
			String groupId = jsonObj.optString("groupId");
			if (!isNB(groupId)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			// 群名
			String name = jsonObj.optString("name");
			// 群头像
			String pictureUrl = jsonObj.optString("pictureUrl");
			MulChatInformation mulChatInfor = new MulChatInformation();
			mulChatInfor.setGroupId(groupId);
			mulChatInfor.setName(name);
			mulChatInfor.setPictureUrl(pictureUrl);
			mulChatInfor.setCreateTime(new Date());
			mulChatInfor.setUpdateTime(new Date());
			this.mulChatInfBusiness.insert(mulChatInfor);
			return outSucceed("", false, "");
		} catch (Exception e) {
			_logger.error("添加群信息失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 移除群组信息
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "removeGroupInfo")
	@ResponseBody
	public String removeGroupInfo(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群ID
			String groupId = jsonObj.optString("groupId");
			if (!isNB(groupId)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			this.mulChatInfBusiness.deleteByPrimarykey(groupId);
			return outSucceed("", false, "");
		} catch (Exception e) {
			_logger.error("删除群组信息失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 更新群组信息
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateGroupInfo")
	@ResponseBody
	public String updateGroupInfo(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群ID
			String groupId = jsonObj.optString("groupId");
			if (!isNB(groupId)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			// 群名
			String name = jsonObj.optString("name");
			// 群头像
			String pictureUrl = jsonObj.optString("pictureUrl");
			if (!isNB(name) && !isNB(pictureUrl)) {// 同时为空则表示本次请求没有任何更新
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			MulChatInformation mulChatInfor = new MulChatInformation();
			mulChatInfor.setGroupId(groupId);
			mulChatInfor.setName(name);
			mulChatInfor.setPictureUrl(pictureUrl);
			mulChatInfor.setCreateTime(new Date());
			mulChatInfor.setUpdateTime(new Date());
			this.mulChatInfBusiness.updateByPrimaryKey(mulChatInfor);
			return outSucceed("", false, "");
		} catch (Exception e) {
			_logger.error("更新群组信息失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 查找群组信息
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findGroupInfo")
	@ResponseBody
	public String findGroupInfo(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群ID
			String groupId = jsonObj.optString("groupId");
			if (!isNB(groupId)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			MulChatInformation mulChatInfor = this.mulChatInfBusiness.selectByPrimarykey(groupId);
			if (mulChatInfor == null) {
				mulChatInfor = new MulChatInformation();
				return outSucceed(mulChatInfor, true, FIND_MUL_CHAT_GROUP_FILLTER);
			}
			return outSucceed(mulChatInfor, true, FIND_MUL_CHAT_GROUP_FILLTER);
		} catch (Exception e) {
			_logger.error("查找群组信息失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	* @Title: findGroupInfoList
	* @Description: (查找群组信息列表)
	* @param jsonObj
	* @return
	* @throws Exception
	* @return String    返回类型
	* @throws
	* @author Baojiang Yang  
	* @date 2015年10月8日 上午9:31:02 
	*/
	@RequestMapping(value = "findGroupInfoList")
	@ResponseBody
	public String findGroupInfoList(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群ID数组
			JSONArray groupIdArray = jsonObj.getJSONArray("groupIdArray");
			if (!CollectionUtils.isEmpty(groupIdArray)) {
				List<MulChatInformation> groupInfoList = new ArrayList<MulChatInformation>();
				for (Object groupInfoObj : groupIdArray) {
					if (groupInfoObj != null && isNB(groupInfoObj.toString())) {
						MulChatInformation mulChatInfor = this.mulChatInfBusiness.selectByPrimarykey(groupInfoObj.toString());
						groupInfoList.add(mulChatInfor);
					}
				}
				return outSucceed(groupInfoList, true, FIND_MUL_CHAT_GROUP_FILLTER);
			} else {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
		} catch (Exception e) {
			_logger.error("查找群组信息列表失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 添加群组显示昵称
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addMulChatNick")
	@ResponseBody
	public String addMulChatNick(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群组ID
			String groupId = jsonObj.optString("groupId");
			// 用户账户
			String loginAccount = jsonObj.optString("loginAccount");
			if (!isNB(groupId) || !isNB(loginAccount)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String viewNick = jsonObj.optString("viewNick");
			MulChatViewNick mulChatViewNick = new MulChatViewNick();
			mulChatViewNick.setGroupId(groupId);
			mulChatViewNick.setLoginAccount(loginAccount);
			mulChatViewNick.setViewNick(viewNick);
			mulChatViewNick.setCreateTime(new Date());
			mulChatViewNick.setUpdateTime(new Date());
			this.mulChatViewNickBusiness.insert(mulChatViewNick);
			return outSucceed("", false, "");
		} catch (Exception e) {
			_logger.error("添加群组显示昵称失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 删除群组昵称
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "removeMulChatNick")
	@ResponseBody
	public String removeMulChatNick(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群组ID
			String groupId = jsonObj.optString("groupId");
			// 用户账户
			String loginAccount = jsonObj.optString("loginAccount");
			if (!isNB(groupId) || !isNB(loginAccount)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			MulChatViewNick mulChatViewNick = new MulChatViewNick();
			mulChatViewNick.setGroupId(groupId);
			mulChatViewNick.setLoginAccount(loginAccount);
			this.mulChatViewNickBusiness.delete(mulChatViewNick);
			return outSucceed("", false, "");
		} catch (Exception e) {
			_logger.error("删除群组昵称失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 更新群组显示昵称
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateMulChatNick")
	@ResponseBody
	public String updateMulChatNick(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群组ID
			String groupId = jsonObj.optString("groupId");
			// 用户账户
			String loginAccount = jsonObj.optString("loginAccount");
			if (!isNB(groupId) || !isNB(loginAccount)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String viewNick = jsonObj.optString("viewNick");
			MulChatViewNick mulChatViewNick = new MulChatViewNick();
			mulChatViewNick.setGroupId(groupId);
			mulChatViewNick.setLoginAccount(loginAccount);
			mulChatViewNick.setViewNick(viewNick);
			mulChatViewNick.setCreateTime(new Date());
			mulChatViewNick.setUpdateTime(new Date());
			this.mulChatViewNickBusiness.update(mulChatViewNick);
			return outSucceed("", false, "");
		} catch (Exception e) {
			_logger.error("更新群组显示昵称失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 查找群组显示昵称
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findMulChatNick")
	@ResponseBody
	public String findMulChatNick(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群组ID
			String groupId = jsonObj.optString("groupId");
			// 用户账户
			String loginAccount = jsonObj.optString("loginAccount");
			if (!isNB(groupId) || !isNB(loginAccount)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			MulChatViewNick mulChatViewNick = new MulChatViewNick();
			mulChatViewNick.setGroupId(groupId);
			mulChatViewNick.setLoginAccount(loginAccount);
			MulChatViewNick result = this.mulChatViewNickBusiness.select(mulChatViewNick);
			return outSucceed(result, true, FIND_MUL_CHAT_NICK_FILLTER);
		} catch (Exception e) {
			_logger.error("查找群组显示昵称失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 查找群组显示昵称列表
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findMulChatNickList")
	@ResponseBody
	public String findMulChatNickList(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 群组ID
			String groupId = jsonObj.optString("groupId");
			if (!isNB(groupId)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			List<MulChatViewNick> results = this.mulChatViewNickBusiness.findGroupNickList(groupId);
			return outSucceed(results, true, FIND_MUL_CHAT_NICK_FILLTER);
		} catch (Exception e) {
			_logger.error(" 查找群组显示昵称列表失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 添加群二维码
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addMulChatQRCode")
	@ResponseBody
	public String addMulChatQRCode(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 用户登录名
			String createUser = jsonObj.optString("createUser");
			// 群组ID
			String groupId = jsonObj.optString("groupId");
			if (!isNB(groupId) || !isNB(createUser)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			MulChatQRCode qRCode = new MulChatQRCode(null);
			qRCode.setGroupId(groupId);
			qRCode.setCreateUser(createUser);
			MulChatQRCode result = this.mulChatQRCodeBusiness.addMulChatQRCode(qRCode);
			if (result != null) {
				if (result.getErrorCode() == null) {
					return outSucceed(result, true, ADD_QR_CODE_FILLTER);
				} else {
					return outFailure(result.getErrorCode(), "");
				}
			} else {
				return outFailure(AssConstantsUtil.MulChatCode.INSERT_QRCODE_ERROR_CODE, "插入二维码失败");
			}
		} catch (Exception e) {
			_logger.error("插入二维码失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 扫描二维码验证
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "scanMulChatQRCode")
	@ResponseBody
	public String scanMulChatQRCode(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 二维码
			String qRCodeStr = jsonObj.optString("qRCode");
			if (!isNB(qRCodeStr)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			MulChatQRCode qRCode = this.mulChatQRCodeBusiness.findMulChatQRCode(qRCodeStr);
			if (DataUtils.isNotBlankQRcode(qRCode)) {
				Date currentDate = new Date();
				if (currentDate != null && qRCode.getCreateTime() != null && currentDate.after(qRCode.getCreateTime())) {
					int days = DataUtils.daysBetween(qRCode.getCreateTime(), currentDate);
					if (days > 7) {
						try {
							this.mulChatQRCodeBusiness.removeByPrimarykey(qRCode.getqRCode());
						} catch (Exception e) {
							_logger.info("删除过期二维码失败.");
						}
						return outFailure(AssConstantsUtil.MulChatCode.OVERDUE_QRCODE_ERROR_CODE, "二维码已失效");
					} else {
						return outSucceed(qRCode, true, SCAN_QRCODE_FILLTER);
					}
				} else {
					return outFailure(AssConstantsUtil.MulChatCode.INVALID_QRCODE_ERROR_CODE, "无效的二维码或者不存在");
				}
			} else {
				return outFailure(AssConstantsUtil.MulChatCode.INVALID_QRCODE_ERROR_CODE, "无效的二维码或者不存在");
			}
		} catch (Exception e) {
			_logger.error(" 扫描二维码验证失败！");
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "扫描二维码验证失败");
		}
	}

	private static boolean isNB(String str) {
		return StringUtils.isNotBlank(str);
	}

}
