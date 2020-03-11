package com.rk.utils.string;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static Pattern linePattern = Pattern.compile("_(\\w)");

	public static String fieldToColumn(String filed) {
		return filed.replaceAll("[A-Z]", "_$0").toLowerCase();
	}

	public static String columnToField(String column) {
		column = column.toLowerCase();
		Matcher matcher = linePattern.matcher(column);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static List<String> String2List(String s, String c) {
		return Arrays.asList(s.split(c));
	}

	public static String List2String(List<String> list, String c) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				sb.append(c);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}
}
