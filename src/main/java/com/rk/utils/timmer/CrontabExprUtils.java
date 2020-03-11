package com.rk.utils.timmer;

import com.rk.utils.date.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Crontab表达式工具
 * @author ZhaoKang
 *
 */
public class CrontabExprUtils {

	public static Date checkDate(String workDate, String expr) throws ParseException {
		boolean matched = false;
		Date time = null;

		String M = workDate.substring(4, 6);
		String d = workDate.substring(7, 8);
		String[] exprs = expr.split(" ");
		String h = exprs[2].length() == 2 ? exprs[2] : "0" + exprs[2];
		String m = exprs[1].length() == 2 ? exprs[1] : "0" + exprs[1];
		String s = exprs[0].length() == 2 ? exprs[0] : "0" + exprs[0];

		if (exprs[3].equals("?") || exprs[3].equals("*") || Integer.parseInt(exprs[3]) == Integer.parseInt(d)) {
			if (exprs[4].equals("?") || exprs[4].equals("*") || Integer.parseInt(exprs[4]) == Integer.parseInt(M)) {
				matched = true;
			}
		}
		if (matched) {
			time = DateUtils.getDate(workDate + h + m + s, "yyyyMMddhhmmss");
		}

		return time;
	}

	public static void main(String args[]) throws ParseException {
		Date date = CrontabExprUtils.checkDate("20160601", "0 7 7 1 * ?");
		System.out.println(date);
	}
}
