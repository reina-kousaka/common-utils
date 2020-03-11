package com.rk.utils.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rk.utils.reflect.ObjectToObject;

/**
 * JSON工具
 * @author ZhaoKang
 *
 */
public class GsonUtils {

	public static <T> List<T> json2List(String json, Type type) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		List<T> list = gson.fromJson(json, type);
		return list;
	}

	public static <T> List<T> json2List(String json, Class<T> clazz) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Type type = new TypeToken<List<Map<String, ?>>>() {
		}.getType();
		List<Map<String, ?>> list = gson.fromJson(json, type);
		List<T> result = new ArrayList<T>();
		T t;
		for (Map<String, ?> o : list) {
			t = ObjectToObject.mapObject(o, clazz);
			result.add(t);
		}
		return result;
	}

	public static String toJson(Object obj) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if (obj != null) {
			return gson.toJson(obj);
		} else {
			return "[]";
		}
	}

	public static <T> T toBean(String json, Class<T> clazz) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.fromJson(json, clazz);
	}

	public static String msg(String result, String... msg) {
		return "{\"result\":\"" + result + "\",\"message\":\"" + msg + "\"}";
	}
}
