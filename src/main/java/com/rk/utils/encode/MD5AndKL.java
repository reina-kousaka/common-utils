package com.rk.utils.encode;


/**
 * MD5加密
 * @author ZhaoKang
 *
 */
public class MD5AndKL {

	private static char r = 't';

	// 可逆的加密算法
	public static String KL(String inStr) {
		// String s = new String(inStr);
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ r);
		}
		String s = new String(a);
		return s;
	}

	// 加密后解密
	public static String JM(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ r);
		}
		String k = new String(a);
		return k;
	}

	// 测试主函数
	public static void main(String args[]) {
		String s = new String("123456");
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + MD5Encoder.encode(s));
		System.out.println("MD5后再加密：" + KL(MD5Encoder.encode(s)));
		System.out.println("解密为MD5后的：" + JM(KL(MD5Encoder.encode(s))));
	}
}