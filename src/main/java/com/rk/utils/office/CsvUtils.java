package com.rk.utils.office;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/***
 * 
 * CSV文件工具
 * @author ZHAOKANG
 *
 */
public class CsvUtils {

	public static List<List<String>> readCsv(String filePath) {
		String line = null;
		List<List<String>> list = new ArrayList<List<String>>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			while ((line = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				List<String> conList = new ArrayList<String>();
				while (st.hasMoreTokens()) {
					String token = st.nextToken();
					if (token.indexOf("\"") != -1) {
						String next;
						while ((next = st.nextToken()).indexOf("\"") == -1)
							token += next;
					}
					conList.add(token);
				}
				if (conList.size() > 0) {
					list.add(conList);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return list;
	}

	/**
	 * 导入
	 * 
	 * @param file
	 *            csv文件(路径+文件)
	 * @return
	 */
	public static List<List<String>> importCsv(String file) {
		List<List<String>> list = new ArrayList<List<String>>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(file)));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				List<String> conList = new ArrayList<String>();
				for (int i = 0; i < arr.length; ++i) {
					if (!("".equals(arr[0])))
						conList.add(arr[i]);
				}
				if (conList.size() > 0)
					list.add(conList);
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
