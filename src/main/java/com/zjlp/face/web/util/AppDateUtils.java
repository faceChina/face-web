package com.zjlp.face.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ybj
 *
 */
public class AppDateUtils {

	private static final String DATEFORMAT = "yyyy-MM-dd";

	private static final String SHORT = "SHORT";

	private static final String MEDIUM = "MEDIUM";

	private static final String FULL = "FULL";
	
	private static final String LINE = "-";

	private static final int ZERO = 0;

	private static final int FOUR = 4;

	private static final int SIX = 6;

	private static final int EIGHT = 8;

	private static DateFormat format;
	

	private static void newDateFormat() {
		if (format == null) {
			format = new SimpleDateFormat(DATEFORMAT);
		}
	}

	/**
	 * @param date
	 * @param type
	 * @return
	 */
	public static String dateToString(Date date, String type) {
		String str = null;
		if (date != null && StringUtils.isNotBlank(type)) {
			newDateFormat();
			if (SHORT.equals(type)) {
				// 07-1-18
				format = DateFormat.getDateInstance(DateFormat.SHORT);
				str = format.format(date);
			} else if (MEDIUM.equals(type)) {
				// 2007-1-18
				format = DateFormat.getDateInstance(DateFormat.MEDIUM);
				str = format.format(date);
			} else if (FULL.equals(type)) {
				// 2007年1月18日 星期四
				format = DateFormat.getDateInstance(DateFormat.FULL);
				str = format.format(date);
			}
		}
		return str;
	}

	/**
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		Date date = null;
		if (StringUtils.isNotBlank(str)) {
			if (Identity.checkDate(str)) {
				str = addLine(str);
			}
			newDateFormat();
			try {
				// Fri Feb 24 00:00:00 CST 2012
				date = format.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// 2012-02-24
			date = java.sql.Date.valueOf(str);

		}
		return date;
	}

	/**
	 * @param str
	 * @return
	 */
	public static String addLine(final String str) {
		if (StringUtils.isNotBlank(str) && str.length() == EIGHT) {
			try {
				return new StringBuilder(str.substring(ZERO, FOUR)).append(LINE)
						.append(str.substring(FOUR, SIX)).append(LINE)
						.append(str.substring(SIX, EIGHT)).toString();
			} catch (Exception e) {
				return StringUtils.EMPTY;
			}

		}
		return StringUtils.EMPTY;
	}

}
