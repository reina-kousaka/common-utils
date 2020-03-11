package com.rk.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * ftp工具
 * @author ZhaoKang
 *
 */
public class FTPUtils {

	// String host;
	// String username;
	// String password;
	//
	// public FTPUtils(String host, String username, String password) {
	// this.host = host;
	// this.username = username;
	// this.password = password;
	// }

	public static int put(String host, String username, String password, String localFile, String remoteFile) {
		int result = 1;
		FTPClient client = new FTPClient();
		try {
			client.connect(host);
			client.login(username, password);
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			File file = new File(localFile);
			if (!file.exists()) {
				return 2;
			}
			if (client.storeFile(remoteFile, new FileInputStream(file))) {
				result = 0;
			}
			client.quit();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
