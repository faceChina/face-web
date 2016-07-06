package com.zjlp.face.web.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * IP工具类
 * 
 * @author Baojiang Yang
 * @date 2015年9月8日 下午2:44:22
 *
 */
public class IPUtil {
	
	
	private static Logger log = Logger.getLogger(IPUtil.class);

	public static final String IP_REGEX = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
			+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
			+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

	
	/**
	 * 构造函数.
	 */
	private IPUtil() {
	}

	/**
	 * 获取客户端IP地址.<br>
	 * 支持多级反向代理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 客户端真实IP地址
	 */
	public static String getRemoteAddr(final HttpServletRequest request) {
		try {
			String remoteAddr = request.getHeader("X-Forwarded-For");
			// 如果通过多级反向代理，X-Forwarded-For的值不止一个，而是一串用逗号分隔的IP值，此时取X-Forwarded-For中第一个非unknown的有效IP字符串
			if (isEffective(remoteAddr) && (remoteAddr.indexOf(",") > -1)) {
				String[] array = remoteAddr.split(",");
				for (String element : array) {
					if (isEffective(element)) {
						remoteAddr = element;
						break;
					}
				}
			}
			if (!isEffective(remoteAddr)) {
				remoteAddr = request.getHeader("X-Real-IP");
			}
			if (!isEffective(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
			return remoteAddr;
		} catch (Exception e) {
			log.error("get romote ip error,error message:" + e.getMessage());
			return "";
		}
	}

	/**
	 * 获取客户端源端口
	 * 
	 * @param request
	 * @return
	 */
	public static Long getRemotePort(final HttpServletRequest request) {
		try {
			String port = request.getHeader("remote-port");
			if (StringUtils.isNotBlank(port)) {
				try {
					return Long.parseLong(port);
				} catch (NumberFormatException ex) {
					log.error("convert port to long error , port: " + port);
					return 0l;
				}
			} else {
				return 0l;
			}
		} catch (Exception e) {
			log.error("get romote port error,error message:" + e.getMessage());
			return 0l;
		}
	}

	/**
	 * 远程地址是否有效.
	 * 
	 * @param remoteAddr
	 *            远程地址
	 * @return true代表远程地址有效，false代表远程地址无效
	 */
	private static boolean isEffective(final String remoteAddr) {
		boolean isEffective = false;
		if ((null != remoteAddr) && (!"".equals(remoteAddr.trim())) && (!"unknown".equalsIgnoreCase(remoteAddr.trim()))) {
			isEffective = true;
		}
		return isEffective;
	}
	
	/**
	 * @Title: isValidIP
	 * @Description: (检查IP合法有效)
	 * @param strIP
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月8日 下午3:30:35
	 */
	public static boolean isValidIP(String strIP) {
		return Pattern.matches(IP_REGEX, strIP);
	}
}
