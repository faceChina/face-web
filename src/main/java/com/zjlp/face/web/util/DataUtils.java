package com.zjlp.face.web.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;

/**
 * @author Baojiang Yang
 *
 */
public class DataUtils {

	/**
	 * list去重
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> removeSameElement(List<String> list) {
		List<String> results = null;
		if (list != null && !list.isEmpty()) {
			Set<String> set = new HashSet<String>();
			for (String current : list) {
				set.add(current);
			}
			results = new ArrayList<String>(set);
		}
		return results;
	}

	/**
	 * 计算两个Date之间的天数
	 * 
	 * @param early
	 * @param late
	 * @return
	 */
	public static final int daysBetween(Date early, Date late) {
		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;
		return days;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param qRcode
	 * @return
	 */
	public static boolean isNotBlankQRcode(MulChatQRCode qRcode) {
		if (qRcode == null) {
			return false;
		}
		if (qRcode.getId() == null) {
			return false;
		}
		if (StringUtils.isEmpty(qRcode.getGroupId())) {
			return false;
		}
		if (StringUtils.isEmpty(qRcode.getqRCode())) {
			return false;
		}
		if (qRcode.getStatus() == null) {
			return false;
		}
		if (StringUtils.isEmpty(qRcode.getCreateUser())) {
			return false;
		}
		if (qRcode.getCreateTime() == null) {
			return false;
		}
		return true;
	}
	/**
	 * 将金额格式化
	* @Title: formatMoney
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param pattern
	* @param money
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月12日 下午2:59:25
	 */
	public static String formatMoney(String pattern,Long money){
		
		if (!StringUtils.isNotBlank(pattern)){
			pattern = "##0.00";
		}
		if (money == null){
			money = 0L;
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(money/100.0);
	}
	/**
	 * 手机号码的中间加密
	 * @param phone 手机号码
	 * @return
	 */
	public static String getJiamiPhone(String phone) {
		StringBuilder sb = new StringBuilder();
		if (null == phone){
			return "";
		}
		sb.append(phone.substring(0, 3));
		for (int i = 0; i < phone.length() - 6; i++) {
			sb.append("*");
		}
		sb.append(phone.substring(phone.length() - 3, phone.length()));
		return sb.toString();
	}

	/**
	 * @Title: resetCurPage
	 * @Description: (根据pagination中start重新计算curPage)
	 * @param pagination
	 * @param jsonObj
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月15日 下午3:06:54
	 */
	public static void resetCurPage(Pagination<SubBranchSalesOrderVo> pagination, JSONObject jsonObj) {
		Object startobj = jsonObj.get("start");
		Object pageSizeObj = jsonObj.get("pageSize");
		if (null != startobj && !"".equals(startobj) && null != pageSizeObj && !"".equals(pageSizeObj)) {
			int start = Integer.parseInt(startobj.toString());
			int pageSize = Integer.parseInt(pageSizeObj.toString());
			pagination.setCurPage(start / pageSize + 1);
		}
	}

}
