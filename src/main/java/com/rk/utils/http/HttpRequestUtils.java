package com.rk.utils.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP请求工具类
 * 
 * @author ZhaoKang
 *
 */
public class HttpRequestUtils {

	public static Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param timeout
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String get(String url, String param, String charset, int timeout) {
		return sendGet(url, param, charset, timeout, null);
	}

	public static String get(String url, String param, int timeout) {
		return sendGet(url, param, "UTF-8", timeout, null);
	}

	public static String get(String url, String param, String charset, int timeout, Map<String, String> header) {
		return sendGet(url, param, charset, timeout, header);
	}

	public static String get(String url, String param, String charset, int timeout, Map<String, String> header,
			String path) {
		return sendGet(url, param, charset, timeout, header, path);
	}

	public static String sendGet(String url, String param, String charset, int timeout, Map<String, String> header) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			connection.setConnectTimeout(timeout / 2);
			connection.setReadTimeout(timeout / 2);
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			if (header != null) {
				for (String h : header.keySet()) {
					connection.setRequestProperty(h, header.get(h));
				}
			}
			// connection.setRequestProperty("user-agent", "Mozilla/10.0 (compatible; MSIE 10.0; Windows NT 7.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				log.debug(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param timeout
	 * @return 所代表远程资源的响应结果
	 */
	public static String post(String url, String param, String charset, int timeout) {
		return sendPost(url, param, charset, timeout, null);
	}

	public static String post(String url, String param, String charset, int timeout, String path) {
		return sendPost(url, param, charset, timeout, null, path);
	}

	public static String post(String url, String param, String charset, int timeout, Map<String, String> header) {
		return sendPost(url, param, charset, timeout, header);
	}

	public static String post(String url, String param, String charset, int timeout, Map<String, String> header,
			String path) {
		return sendPost(url, param, charset, timeout, header, path);
	}

	public static String post(String url, String param, int timeout) {
		return sendPost(url, param, "UTF-8", timeout, null);
	}

	private static String sendPost(String url, String param, String charset, int timeout, Map<String, String> header) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			conn.setConnectTimeout(timeout / 2);
			conn.setReadTimeout(timeout / 2);

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			// conn.setRequestProperty("user-agent", "Mozilla/10.0 (compatible; MSIE 10.0; Windows NT 7.1;SV1)");
			if (header != null) {
				for (String h : header.keySet()) {
					conn.setRequestProperty(h, header.get(h));
				}
			}
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static String sendPost(String url, String param, String charset, int timeout, Map<String, String> header,
			String path) {
		PrintWriter out = null;
		InputStream in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			conn.setConnectTimeout(timeout / 2);
			conn.setReadTimeout(timeout / 2);

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			// conn.setRequestProperty("user-agent", "Mozilla/10.0 (compatible; MSIE 10.0; Windows NT 7.1;SV1)");
			if (header != null) {
				for (String h : header.keySet()) {
					conn.setRequestProperty(h, header.get(h));
				}
			}
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			FileOutputStream fos = new FileOutputStream(new File(path));
			in = conn.getInputStream();
			byte b[] = new byte[1024];
			int len;
			while ((len = in.read(b)) > 0) {
				fos.write(b, 0, len);
			}
			fos.close();
		} catch (Exception e) {
			log.error("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String sendGet(String url, String param, String charset, int timeout, Map<String, String> header,
			String path) {
		String result = "";
		InputStream in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			connection.setConnectTimeout(timeout / 2);
			connection.setReadTimeout(timeout / 2);
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			if (header != null) {
				for (String h : header.keySet()) {
					connection.setRequestProperty(h, header.get(h));
				}
			}
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// 定义 BufferedReader输入流来读取URL的响应
			FileOutputStream fos = new FileOutputStream(new File(path));
			in = connection.getInputStream();
			byte b[] = new byte[1024];
			int len;
			while ((len = in.read(b)) > 0) {
				fos.write(b, 0, len);
			}
			fos.close();

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String args[]) {
	}
}
