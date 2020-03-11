package com.rk.utils.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @author ZhaoKang
 *
 */
public class ExceptionUtils {

	/**
	 * 获取错误堆栈
	 * @param e
	 * @return
	 */
	public static String getMsg(Exception e) {
		String msg;
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		msg = sw.toString();
		try {
			sw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return msg;
	}
}
