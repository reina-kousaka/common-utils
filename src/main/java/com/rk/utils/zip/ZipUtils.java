package com.rk.utils.zip;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zip解压缩工具
 * 
 * @author ZhaoKang
 *
 */
public class ZipUtils {

	private static Logger log = LoggerFactory.getLogger(ZipUtils.class);
	private static String ENCODING;

	public static void zipSingleFile(String file, String zipFile) throws IOException {
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		// zip的名称为
		File zip = new File(zipFile);
		zipOut.setComment(zip.getName());
		loopFiles(zipOut, file, "");
		zipOut.flush();
		zipOut.close();
	}

	public static void zipMultiFile(Collection<String> files, String zipFile) throws IOException {
		log.debug("zip file:" + zipFile);
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		if (ENCODING != null) {
			zipOut.setEncoding(ENCODING);
		}
		// zip的名称为
		File zip = new File(zipFile);
		zipOut.setComment(zip.getName());
		for (String f : files) {
			loopFiles(zipOut, f, "");
		}
		zipOut.flush();
		zipOut.close();
	}

	public static void zipMultiFile(Collection<String> files, String zipFile, String encoding) throws IOException {
		if (encoding != null) {
			ENCODING = encoding;
		}
		zipMultiFile(files, zipFile);
		ENCODING = null;
	}

	private static void loopFiles(ZipOutputStream zipOut, String fileName, String prefPath) throws IOException {
		if (prefPath.length() != 0) {
			prefPath += "/";
		}
		File file = new File(fileName);
		if (file.isDirectory()) {
			for (File f : file.listFiles())
				loopFiles(zipOut, f.getPath(), prefPath + file.getName());
		} else {
			zipFile(zipOut, file, prefPath + file.getName());
		}
	}

	private static void zipFile(ZipOutputStream zipOut, File file, String name) throws IOException {
		if (file.exists()) {
			System.out.println("compress file:" + file.getPath());
			FileInputStream input = new FileInputStream(file);
			ZipEntry entry = new ZipEntry(name);
			zipOut.putNextEntry(entry);
			byte[] b = new byte[256];
			int len;
			while ((len = input.read(b)) > 0) {
				zipOut.write(b, 0, len);
			}
			input.close();
		} else {
			log.error("can't find file:" + file.getPath());
		}
	}

	private static void zipString(ZipOutputStream zipOut, String content, String name) throws IOException {
		if (content != null && content.length() > 0) {
			System.out.println("compress content:");
			ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes());
			ZipEntry entry = new ZipEntry(name);
			zipOut.putNextEntry(entry);
			byte[] b = new byte[256];
			int len;
			while ((len = bais.read(b)) > 0) {
				zipOut.write(b, 0, len);
			}
			bais.close();
		} else {
			log.error("content is empty:");
		}
	}

	private static void zipString(ZipOutputStream zipOut, String content) throws IOException {
		if (content != null && content.length() > 0) {
			System.out.println("compress content:");
			ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes());
			byte[] b = new byte[256];
			int len;
			while ((len = bais.read(b)) > 0) {
				zipOut.write(b, 0, len);
			}
			bais.close();
		} else {
			log.error("content is empty:");
		}
	}

	public static void unzipFile(String unZipfileName, String exPath) {
		FileOutputStream fos;
		File file;
		InputStream is;

		try {
			ZipFile zipFile = new ZipFile(unZipfileName);
			String fileName;
			for (Enumeration<ZipEntry> entries = zipFile.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				fileName = entry.getName();
				System.out.println("extra file:" + fileName);
				if (exPath != null) {
					fileName = exPath + fileName;
				}
				file = new File(fileName);

				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					File parent = file.getParentFile();
					if (parent != null && !parent.exists()) {
						parent.mkdirs();
					}
					is = zipFile.getInputStream(entry);
					fos = new FileOutputStream(file);
					byte[] b = new byte[256];
					int len;
					while ((len = is.read(b)) > 0) {
						fos.write(b, 0, len);
					}
					fos.close();
					is.close();
				}
			}
			zipFile.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void unzipFile(String unZipfileName, String exPath, String encoding) {
		FileOutputStream fos;
		File file;
		InputStream is;
		InputStreamReader isr;
		OutputStreamWriter osw;

		try {
			ZipFile zipFile = new ZipFile(unZipfileName);
			String fileName;

			for (Enumeration<ZipEntry> entries = zipFile.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				fileName = entry.getName();
				System.out.println("extra file:" + fileName);
				if (exPath != null) {
					fileName = exPath + "/" + fileName;
				}
				file = new File(fileName);

				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					is = zipFile.getInputStream(entry);
					isr = new InputStreamReader(is, zipFile.getEncoding());
					fos = new FileOutputStream(file);
					osw = new OutputStreamWriter(fos, encoding);
					char[] c = new char[256];
					int len;
					while ((len = isr.read(c)) > 0) {
						osw.write(c, 0, len);
					}
					osw.close();
					fos.close();
					isr.close();
					is.close();
				}
			}
			zipFile.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 压缩与解压缩工具
	 * <p>
	 * 压缩 ze d:/work/lib/model.zip D:/model UTF-8
	 * <p>
	 * 解压 x d:/tmp/test.zip D:/tmp
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		String type = args[0];
		String zipName = args[1];
		if (type.indexOf("z") != -1) {
			String fileName = args[2];
			String[] fileNames = fileName.split(";");
			if (type.indexOf("e") != -1) {
				ENCODING = args[3];
			}
			Set<String> files = new HashSet<String>();
			files.addAll(Arrays.asList(fileNames));
			zipMultiFile(files, zipName);
		} else if (type.indexOf("x") != -1) {
			String exPath = null;
			if (type.indexOf("p") != -1) {
				exPath = args[2];
			}
			unzipFile(zipName, exPath);
		}
	}
}
