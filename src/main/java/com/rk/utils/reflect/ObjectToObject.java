package com.rk.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @category 两个相同类型的类赋值
 * @author 赵康
 * 
 */
public class ObjectToObject {

	public static final int SET_METHOD = 1;
	public static final int GET_METHOD = 2;

	/**
	 * 
	 * @param <T>
	 * @param aidT
	 *            将要赋值的实体
	 * @param orginT
	 *            用于赋值的实体
	 */
	public static <T> void copyObject(T aidT, T orginT) {
		Class<? extends Object> aidClass = aidT.getClass();
		Class<? extends Object> orginClass = orginT.getClass();
		Field[] fields = aidClass.getDeclaredFields();
		Method setMethod = null;
		Method getMethod = null;
		String fieldName;
		String fieldFirstUpperName;
		StringBuffer setMethodName = new StringBuffer();
		StringBuffer getMethodName = new StringBuffer();
		for (Field field : fields) {
			fieldName = field.getName();
			fieldFirstUpperName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1)
					.toUpperCase());
			setMethodName.append("set").append(fieldFirstUpperName);
			getMethodName.append("get").append(fieldFirstUpperName);
			try {
				setMethod = aidClass.getMethod(setMethodName.toString(),
						new Class[] { aidClass.getDeclaredField(fieldName).getType() });
				getMethod = orginClass.getMethod(getMethodName.toString());
				if (setMethod != null && getMethod != null) {
					Object value = getMethod.invoke(orginT);
					if (value != null) {
						setMethod.invoke(aidT, new Object[] { value });
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// e.printStackTrace();
				continue;
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} finally {
				setMethodName.delete(0, setMethodName.length());
				getMethodName.delete(0, getMethodName.length());
			}
		}
		// return aidT;
	}

	public static String makeMethodName(String fieldName, int methodType) {
		StringBuffer sb = new StringBuffer();
		fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
		if (methodType == SET_METHOD) {
			sb.append("set");
		} else if (methodType == GET_METHOD) {
			sb.append("get");
		}
		sb.append(fieldName);
		return sb.toString();
	}

	/**
	 * 
	 * @param <T>
	 * @param map
	 *            成员映射
	 * @param clazz
	 *            目标类
	 */
	public static <T> T mapObject(Map<String, ?> map, Class<T> clazz) {
		Method setMethod = null;
		String fieldFirstUpperName;
		StringBuffer setMethodName = new StringBuffer();
		T t = null;
		Object value;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		for (String fieldName : map.keySet()) {

			fieldFirstUpperName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1)
					.toUpperCase());
			setMethodName.append("set").append(fieldFirstUpperName);

			try {
				setMethod = clazz.getMethod(setMethodName.toString(), new Class[] { clazz.getDeclaredField(fieldName)
						.getType() });
				if (setMethod != null) {
					value = map.get(fieldName);
					if (value != null) {
						setMethod.invoke(t, new Object[] { value });
					}
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} finally {
				setMethodName.delete(0, setMethodName.length());
			}
		}

		return t;
	}

	/**
	 * 
	 * @param <T>
	 * @param map
	 *            成员映射
	 * @param clazz
	 *            目标类
	 */
	public static Map<String, Object> objectToMap(Object orginT) {
		Class<? extends Object> orginClass = orginT.getClass();
		Map<String, Object> objectMap = new HashMap<String, Object>();
		Field[] fields = orginClass.getDeclaredFields();
		Method setMethod = null;
		Method getMethod = null;
		String fieldName;
		String fieldFirstUpperName;
		StringBuffer getMethodName = new StringBuffer();
		Object value;
		for (Field field : fields) {
			fieldName = field.getName();
			fieldFirstUpperName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1)
					.toUpperCase());
			getMethodName.append("get").append(fieldFirstUpperName);
			try {
				getMethod = orginClass.getMethod(getMethodName.toString());
				if (setMethod != null && getMethod != null) {
					value = getMethod.invoke(orginT);
					objectMap.put(fieldName, value);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// e.printStackTrace();
				continue;
			} finally {
				getMethodName.delete(0, getMethodName.length());
			}
		}
		return objectMap;
	}


	public static Object getFieldValue(Object orginT, String field) {
		Class<? extends Object> orginClass = orginT.getClass();
		Method getMethod;
		Object value = null;
		String getMethodName = "get" + field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase());
		try {
			getMethod = orginClass.getMethod(getMethodName);
			if (getMethod != null) {
				value = getMethod.invoke(orginT);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return value;
	}
}
