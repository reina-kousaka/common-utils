package com.rk.utils.encode;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * des字符串加密
 * 
 * @author ZhaoKang
 *
 */
public class DesEncryptUtil {

	public static Logger logger = LoggerFactory.getLogger(DesEncryptUtil.class);

	/**
	 * <p>
	 * 二进制转十六进制
	 * </p>
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String result = "";
		String temp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			temp = (Integer.toHexString(b[n] & 0XFF));
			if (temp.length() == 1) {
				result = result.concat("0").concat(temp);
			} else {
				result = result.concat(temp);
			}
		}
		return result.toUpperCase(); // 转成大写
	}

	/**
	 * <p>
	 * 十六进制转二进制
	 * </p>
	 * 
	 * @param b
	 * @return
	 */
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			logger.info("长度不是偶数");
		}

		byte[] b2 = new byte[b.length / 2];
		try {
			for (int n = 0; n < b.length; n += 2) {
				String item = new String(b, n, 2);
				// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
				b2[n / 2] = (byte) Integer.parseInt(item, 16);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b2;
	}

	/**
	 * <p>
	 * 加密
	 * </p>
	 * 
	 * @param s
	 * @return
	 */
	public static String encode(String s, String keyText) {
		String result = "";
		try {
			byte[] keyData = keyText.getBytes();
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] b = cipher.doFinal(s.getBytes("UTF-8"));
			result = byte2hex(b);
		} catch (NoSuchAlgorithmException e) {
			logger.info("No Such Algorithm", e);
			System.exit(-1);
		} catch (InvalidKeyException e) {
			logger.info("Invalid Key", e);
			System.exit(-1);
		} catch (InvalidKeySpecException e) {
			logger.info("Invalid Key Spec", e);
			System.exit(-1);
		} catch (NoSuchPaddingException e) {
			logger.info("No Such Padding", e);
			System.exit(-1);
		} catch (IllegalBlockSizeException e) {
			logger.info("Illegal Block Size", e);
			System.exit(-1);
		} catch (BadPaddingException e) {
			logger.info("Bad Padding", e);
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			logger.info("Unsupported Encoding", e);
			System.exit(-1);
		}

		return result;
	}

	/**
	 * <p>
	 * 解密
	 * </p>
	 * 
	 * @param s
	 * @return
	 */
	public static String decode(String s, String keyText) {
		String result = null;
		try {

			byte[] keyData = keyText.getBytes();
			byte[] b = hex2byte(s.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] bb = cipher.doFinal(b);
			result = new String(bb, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info("Unsupported Encoding", e);
		} catch (NoSuchAlgorithmException e) {
			logger.info("No Such Algorithm", e);
		} catch (InvalidKeyException e) {
			logger.info("Invalid Key", e);
		} catch (InvalidKeySpecException e) {
			logger.info("Invalid Key Spec", e);
		} catch (NoSuchPaddingException e) {
			logger.info("No Such Padding", e);
		} catch (IllegalBlockSizeException e) {
			logger.info("Illegal Block Size", e);
		} catch (BadPaddingException e) {
			logger.info("Bad Padding", e);
		}

		return result;
	}

	public static void main(String args[]) {
		System.out.println(DesEncryptUtil.encode("136,4444", "testtest"));
	}
}