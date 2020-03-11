package com.rk.utils.http;

import com.rk.utils.date.DateUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * HTML 表生成工厂
 * 
 * @author ZhaoKang
 *
 */
public class HTMLFactory {
	private static final String CSS = "<style type=\"text/css\">table{font-size:12;border-collapse:collapse;border-spacing:0;border-left:1px solid #888;border-top:1px solid #888;background:#efefef;} th,td{border-right:1px solid #888;border-bottom:1px solid #888;padding:5px 15px;} th{font-weight:bold;background:#ccc;}</style>";
	private static final String HEAD = "<html><head>" + CSS + "</head><body><table>";
	private static final String FOOT = "</table></body></html>";

	public static String createHTML(Map<String, String> map, List<?> datas) {
		if (CollectionUtils.isEmpty(datas)) {
			return null;
		}
		StringBuffer html = new StringBuffer(HEAD);
		Class clazz = datas.get(0).getClass();
		Field[] fields = clazz.getDeclaredFields();
		Method getMethod = null;
		String fieldName;
		String mapName;
		String fieldFirstUpperName;
		StringBuffer getMethodName = new StringBuffer();
		Object value;
		html.append("<tr>");
		for (Field field : fields) {
			mapName = map.get(field.getName());
			if (mapName == null) {
				continue;
			}
			html.append("<td>").append(mapName).append("</td>");
		}
		html.append("</tr>");
		for (Object obj : datas) {
			html.append("<tr>");
			for (Field field : fields) {
				mapName = map.get(field.getName());
				if (mapName == null) {
					continue;
				}
				html.append("<td>");
				fieldName = field.getName();
				fieldFirstUpperName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1)
						.toUpperCase());
				getMethodName.append("get").append(fieldFirstUpperName);
				try {
					getMethod = clazz.getMethod(getMethodName.toString());
					if (getMethod != null) {
						value = getMethod.invoke(obj);
						if (value == null) {
							value = "";
						}
						if (field.getType().equals(Date.class)) {
							value = DateUtils.getDateString((Date) value, "yyyy-MM-dd HH:mm:ss");
						}
						if (field.getType().equals(Timestamp.class)) {
							value = DateUtils.getDateString(((Timestamp) value).getTimestamp(), "yyyy-MM-dd HH:mm:ss");
						}
						html.append(value);
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				getMethodName.delete(0, getMethodName.length());
				html.append("</td>");
			}
		}
		html.append(FOOT);
		return html.toString();
	}

	public static String createHTMLByRS(Map<String, String> map, ResultSet rs) {
		String html = null;
		try {
			if (!rs.next()) {
				return html;
			}

			StringBuffer htmlBuilder = new StringBuffer(HEAD);
			ResultSetMetaData meta = rs.getMetaData();
			String value = null;

			htmlBuilder.append("<tr>");
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				value = meta.getColumnName(i);
				if (map != null) {
					value = map.get(value) == null ? value : map.get(value);
				}
				if (value != null) {
					htmlBuilder.append("<td>").append(value).append("</td>");
				}
			}
			htmlBuilder.append("</tr>");

			int type;
			do {

				htmlBuilder.append("<tr>");
				for (int i = 1; i <= meta.getColumnCount(); i++) {

					htmlBuilder.append("<td>");
					type = meta.getColumnType(i);
					switch (type) {
					case Types.DATE:
						value = DateUtils.getDateString(rs.getDate(i), "yyyy-MM-dd HH:mm:ss");
						break;
					case Types.TIMESTAMP:
						value = DateUtils.getDateString(rs.getTimestamp(i), "yyyy-MM-dd HH:mm:ss");
						break;
					default:
						value = rs.getString(i);
						break;
					}

					htmlBuilder.append(value);
					htmlBuilder.append("</td>");
				}
				htmlBuilder.append("</tr>");
			} while (rs.next());
			htmlBuilder.append(FOOT);
			html = htmlBuilder.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return html;
	}
}
