package com.zjlp.face.web.http.open.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.exception.ext.DeviceInfoException;
import com.zjlp.face.web.server.product.phone.business.DeviceInfoBusiness;
import com.zjlp.face.web.util.IPUtil;

/**
 * 友钱设备注册servelet
 * 
 * @author Baojiang Yang
 * @date 2015年9月8日 下午2:25:25
 *
 */
public class RegisterDeviceInfoSevlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	private static final String SEMICOLON = ";";

	private static final String PERMISSION_ID = "channelId";

	private static final String REMOTE_ADDRESS = "remoteAddress";

	private static final String CHANNEL_ID = "c";

	private static final String DEVICE_INFO = "d";

	private static final String REPLAY = "replay";

	private Logger Log = Logger.getLogger(this.getClass());

	@Autowired
	private DeviceInfoBusiness deviceInfoBusiness;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		Log.info("init()  invoked...");
		super.init(servletConfig);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletConfig.getServletContext());
		Log.info("init()  completed.");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 打印请求头所有参数,影响性能,非必要的是去掉
			this._printAllParameters(req);
			PrintWriter out = resp.getWriter();
			// deviceInfo 上报设备信息
			String deviceInfo = req.getParameter(DEVICE_INFO);
			// channelId 分配给有钱的标识
			String channelId = req.getParameter(CHANNEL_ID);
			if (StringUtils.isBlank(deviceInfo) || StringUtils.isBlank(channelId)) {
				replyError(RspCode.CODE_204, resp, resp.getWriter());
				return;
			}
			// replay 二次激活标志必须为1
			String replay = req.getParameter(REPLAY);
			if (securityCheck(req)) {
				if (StringUtils.isBlank(replay)) { // 首次任务入库
					Long id = this.deviceInfoBusiness.addOrUpdateDevice(deviceInfo, true);
					addOrCallResult(resp, out, deviceInfo, id);
					return;
				} else if (StringUtils.isNotBlank(replay) && replay.equals("1")) { // 二次入库
					Long id = this.deviceInfoBusiness.addOrUpdateDevice(deviceInfo, false);
					addOrCallResult(resp, out, deviceInfo, id);
					return;
				} else {
					replyError(RspCode.CODE_204, resp, resp.getWriter());
					return;
				}
			} else {
				checkPerResult(req, resp, deviceInfo);
			}
		} catch (DeviceInfoException de) {
			replyError(RspCode.CODE_207, resp, resp.getWriter());
			Log.error("设备注册失败！", de);
		} catch (Exception e) {
			replyError(RspCode.CODE_207, resp, resp.getWriter());
			Log.error("doGet() error!", e);
		}
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	private void addOrCallResult(HttpServletResponse resp, PrintWriter out, String deviceInfo, Long id) throws IOException {
		if (id > 0) {
			Log.info("设备[" + deviceInfo + "]首次入库成功.");
			replyMessage(RspCode.CODE_201, resp, out);
		} else if (id.equals(0L)) {
			Log.info("设备[" + deviceInfo + "]二次入库成功.");
			replyMessage(RspCode.CODE_202, resp, out);
		} else if (id.equals(-1L)) {
			Log.info("设备[" + deviceInfo + "]已经注册,无需重复注册.");
			replyMessage(RspCode.CODE_203, resp, out);
		} else {
			Log.info("未知错误!");
			replyError(RspCode.CODE_204, resp, resp.getWriter());
		}
	}

	/**
	 * @Title: _printAllParameters
	 * @Description: (打印出所有请求头所有参数)
	 * @param request
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月16日 下午7:01:35
	 */
	private void _printAllParameters(HttpServletRequest request) {
		try {
			AssertUtil.notNull(request, "request不能为空");
			Enumeration<?> paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						Log.info("参数：" + paramName + "=" + paramValue);
					}
				}
			}
		} catch (Exception e) {
			Log.info("打印参数异常", e);
		}
	}

	private void checkPerResult(HttpServletRequest req, HttpServletResponse resp, String deviceInfo) throws IOException {
		Log.info("设备[" + deviceInfo + "]注册失败.");
		if (!permissionCheck(req)) {
			Log.info("设备[" + deviceInfo + "]注册失败,channelId不正确或者过期！");
			replyError(RspCode.CODE_205, resp, resp.getWriter());
		}
		if (!ipCheck(req)) {
			Log.info("设备[" + deviceInfo + "]注册失败,远程IP不在白名单！");
			replyError(RspCode.CODE_206, resp, resp.getWriter());
		}
	}

	/**
	 * @Title: securityCheck
	 * @Description: (安全检查)
	 * @param req
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月8日 下午2:51:58
	 */
	private boolean securityCheck(HttpServletRequest req) {
		return permissionCheck(req) && ipCheck(req);
	}

	/**
	 * @Title: ipCheck
	 * @Description: (IP白名单过滤)
	 * @param req
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月8日 下午2:52:09
	 */
	private boolean ipCheck(HttpServletRequest req) {
		try {
			AssertUtil.notNull(req, "req不能为空");
			String remoteAddr = IPUtil.getRemoteAddr(req);
			AssertUtil.notNull(remoteAddr, "remoteAddr为空");
			Log.info("友钱远程客户端请求IP :" + remoteAddr);
			AssertUtil.isTrue(IPUtil.isValidIP(remoteAddr));
			/** 远程请求地址白名单 **/
			String remoteAddress = PropertiesUtil.getContexrtParam(REMOTE_ADDRESS);
			String[] whiteList = remoteAddress.split(SEMICOLON);
			AssertUtil.notEmpty(whiteList);
			for (String ip : whiteList) {
				if (IPUtil.isValidIP(ip) && remoteAddr.equals(ip)) {
					Log.info("友钱远程客户端请求IP [" + remoteAddr + "]校验成功");
					return true;
				}
			}
			Log.info("友钱远程客户端请求IP [" + remoteAddr + "]校验失败！");
			return false;
		} catch (Exception e) {
			Log.error("友钱远程客户端请求IP校验错误!", e);
			return false;
		}
	}

	/**
	 * @Title: permissionCheck
	 * @Description: (标识检查)
	 * @param req
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月8日 下午2:12:06
	 */
	private boolean permissionCheck(HttpServletRequest req) {
		try {
			AssertUtil.notNull(req, "req不能为空");
			// channelId 分配给有钱的标识
			String channelId = req.getParameter(CHANNEL_ID);
			Log.info("校验channelId=[" + channelId + "]");
			AssertUtil.notNull(channelId, "channelId不能为空");
			/** CP分配给有钱的唯一标识 **/
			String perChannelId = PropertiesUtil.getContexrtParam(PERMISSION_ID);
			AssertUtil.hasLength(perChannelId, "youqianConfig.properties未配置字段channelId");
			return channelId.equals(perChannelId);
		} catch (Exception e) {
			Log.error("权限检查错误!", e);
			return false;
		}
	}

	private void replyMessage(RspCode code, HttpServletResponse response, PrintWriter out) throws IOException {
		try {
			putDatas(code, response, out);
		} catch (Exception e) {
			Log.error("replyMessage() error!");
		} finally {
			if (null != out)
				out.close();
		}
	}

	private void putDatas(RspCode code, HttpServletResponse response, PrintWriter out) {
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("code", code.getCode());
		jsonObj.put("message", code.getMsg());
		out.println(jsonObj);
		out.println();
		out.flush();
	}

	private void replyError(RspCode code, HttpServletResponse response, PrintWriter out) {
		try {
			putDatas(code, response, out);
		} catch (Exception e) {
			Log.error("replyError() error!");
		} finally {
			if (null != out)
				out.close();
		}
	}




}
