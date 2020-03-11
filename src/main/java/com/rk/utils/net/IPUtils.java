package com.rk.utils.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * IP工具类
 * @author ZhaoKang
 *
 */
public class IPUtils {

	public static String getLocalIP(String inetName) {
		String ip = null;
		try {
			Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
			Enumeration<InetAddress> ias;
			NetworkInterface ni;
			InetAddress ia;
			if (inetName == null) {
				inetName = "eth0";
			}
			while (nis.hasMoreElements()) {
				ni = nis.nextElement();
				if (inetName.equals(ni.getName())) {
					ias = ni.getInetAddresses();
					while (ias.hasMoreElements()) {
						ia = ias.nextElement();
						ip = ia.getHostAddress();
						if (ip.split("\\.").length == 4) {
							break;
						} else {
							ip = null;
						}

					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public static String getLocalIP() {
		return getLocalIP(null);
	}

	public static void main(String args[]) {
		System.out.println(getLocalIP());

	}
}
