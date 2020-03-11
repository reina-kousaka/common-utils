package com.rk.utils.executor.cmd;

import java.util.Collection;
import java.util.List;

/**
 * 工具
 * @author ZhaoKang
 *
 */
public class CommonUtils {

	public static boolean verifyString(String s) {
		if (s != null && s.length() > 0) {
			return true;
		}
		return false;
	}

	public static boolean verifyCollection(Collection<?> s) {
		if (s != null && s.size() > 0) {
			return true;
		}
		return false;
	}

	public static String castArrayTOString(List<String> list, String type) {
		StringBuffer sb = new StringBuffer();
		for (String s : list) {
			sb.append(s).append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	/**
	 * 字符串引号显式化
	 * @param value
	 * @return
	 */
	public static String transQuote(String value) {
		value = value.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"");
		return value;
	}

}
