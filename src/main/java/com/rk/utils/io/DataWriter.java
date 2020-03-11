package com.rk.utils.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据写入
 * @author ZhaoKang
 *
 */
public class DataWriter {

	public static int writeAppend(List<String> datas, String file, String charset) {

		int result = 1;
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(file, true), charset);
			for (String data : datas) {
				osw.write(data);
			}
			osw.close();
			result = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (osw != null)
					osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int writeAppend(String data, String file, String charset) {

		int result = 1;
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(file, true), charset);
			osw.write(data);
			osw.close();
			result = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (osw != null)
					osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int write(List<String> datas, String file, String charset) {
		int result = 1;
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), charset);
			for (String data : datas) {
				osw.write(data);
			}
			osw.close();
			result = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int write(String data, String file, String charset) {
		int result = 1;
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), charset);
			osw.write(data);
			osw.close();
			result = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			list.add(i + "aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		}
		String file = "d:/a.txt";
		write(list, file, "GBK");
	}

}
