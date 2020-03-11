package com.rk.utils.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件处理
 *
 * @author ZhaoKang
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 移动文件
     *
     * @param sourcePath 源文件路径
     * @param destPath   目标文件路径
     */
    public static void moveFile(String sourcePath, String destPath) {
        try {
            FileUtils.copyFile(new File(sourcePath), new File(destPath));
            // FileUtils.forceDelete(new File(sourcePath));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("file move error.", e);
        }
    }

    /**
     * 复制文件
     *
     * @param sourcePath 源文件路径
     * @param destPath   目标文件路径
     */
    public static void copyFile(File sourceFile, String destPath) {
        try {
            FileUtils.copyFile(sourceFile, new File(destPath));
        } catch (Exception e) {
            logger.error("file move error.", e);
            e.printStackTrace();
        }
    }

    public static boolean createDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.isDirectory())
            return file.mkdirs();
        else
            return true;
    }

    public static boolean createParent(String fileName) {
        boolean flag = false;
        try {
            File file = new File(fileName);
            File pfile = new File(file.getParent());
            if (!pfile.exists()) {
                pfile.mkdirs();
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void saveFile(StringBuffer buffer, String filePath, String fileName, String encoding)
            throws IOException {
        int c = 0;
        byte[] bb = new byte[1024 * 2];
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(buffer.toString().getBytes(encoding));
            FileOutputStream os = new FileOutputStream(filePath + fileName);
            while ((c = in.read(bb)) != -1) {
                os.write(bb, 0, c);
            }
            os.close();
            in.close();
        } catch (IOException ex) {
            throw new IOException("保存文件发生严重错误");
        }
    }

    public static File saveFile(String buffer, String filePath, String fileName, String encoding) throws IOException {
        OutputStreamWriter outputStream = null;
        try {
            File d = new File(filePath + fileName);
            if (!d.getParentFile().isDirectory()) {
                d.getParentFile().mkdirs();
            }
            outputStream = new OutputStreamWriter(new FileOutputStream(d), encoding);
            outputStream.write(buffer);
            outputStream.flush();// 可写可不写，在关闭时会自动刷新，但写上会更安全
            return d;
        } catch (IOException ex) {
            throw new IOException("保存文件发生严重错误");
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                System.out.println("e:====" + e);
            }
        }

    }

    public static String loadFile(String filePath, String fileName, String encoding) throws IOException {
        File file = new File(filePath + fileName);
        if (fileName == null || fileName.equals("")) {
            throw new NullPointerException("无效的文件路径");
        }
        long len = file.length();
        byte[] bytes = new byte[(int) len];

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        int r = bufferedInputStream.read(bytes);
        if (r != len)
            throw new IOException("读取文件不正确");
        bufferedInputStream.close();
        return new String(bytes, encoding);
    }

    public static String loadFile(File file, String charset) throws IOException {
        long len = file.length();
        byte[] bytes = new byte[(int) len];

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        int r = bufferedInputStream.read(bytes);
        if (r != len)
            throw new IOException("读取文件不正确");
        bufferedInputStream.close();
        return new String(bytes, charset);
    }

    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        if (file.exists())
            file.delete();
    }

    public static void moveFile(String bakPath, String filePath, String fileName) {
        File file = new File(bakPath + fileName);
        if (file.exists())
            file.delete();
        new File(filePath + fileName).renameTo(file);
    }

    public static void moveLogFile(String filePath, String bak, String fileName) {
        File file = new File(bak + fileName);
        new File(filePath + fileName).renameTo(file);
    }

    /**
     * 从url获得文件名。如http://www.fhpt.com/some/a.txt将得到a.txt
     *
     * @param url
     * @return 文件名
     */
    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 从文件名获得文件扩展名。如a.doc将得到doc
     *
     * @param fileName文件名
     * @return 扩展名
     */
    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 从文件名获得mime-type。如jpg将得到image/jpeg
     *
     * @param fileName文件名
     * @return 扩展名
     */
    public static String getMimeType(String fileName) {
        String ext = getExtension(fileName);
        String mimeType;
        if (ext.equals("doc") || ext.equals("docx")) {
            mimeType = "application/msword";
        } else if (ext.equals("ppt") || ext.equals("pptx")) {
            mimeType = "application/vnd.ms-powerpoint";
        } else if (ext.equals("xls") || ext.equals("xlsx")) {
            mimeType = "application/vnd.ms-excel";
        } else if (ext.equals("txt") || ext.equals("html")) {
            mimeType = "text/xml";
        } else if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("jpe")) {
            mimeType = "image/jpeg";
        } else if (ext.equals("png")) {
            mimeType = "image/png";
        } else {
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }

    public static HashMap<String, String> convHashMap(String str) {
        Pattern p = Pattern.compile("[\\{\\}\\=\\, ]++");
        String[] split = p.split(str);
        /*
         * for (String s: split ) { System.out.println( '\"' + s + '\"' ); }
         */
        // prints:
        // ""
        // "apple"
        // "red"
        // "mango"
        // "orange"
        // "peach"
        // "yellow"
        // put it back together again.
        HashMap<String, String> h2 = new HashMap<String, String>(11);
        for (int i = 1; i < split.length; i += 2) {
            h2.put(split[i], split[i + 1]);
        }
        return h2;
    }

    /**
     * 获取文件大小
     *
     * @param filepath 文件路径
     * @return 文件大小
     * @throws Exception
     */
    public static int getFilesize(String filepath) throws Exception {
        int size = 0;
        File f = new File(filepath);

        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            size = fis.available();
        } else {
            System.out.println("File is not exists: " + filepath);
        }

        return size;
    }

    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return 文件大小
     * @throws Exception
     */
    public static int getFilesize(File file) throws Exception {
        int size = 0;

        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            System.out.println("File is not exists: " + file.getAbsolutePath());
        }

        return size;
    }

    /**
     * 根据根目录及文件名称获取文件的全路径如:c:/a/b.txt
     *
     * @param rootPath
     * @param fileName
     * @return
     */
    public static String getFileRealPath(String rootPath, String fileName) {
        File root = new File(rootPath);
        if (root.isDirectory()) {
            File fileArray[] = root.listFiles();
            for (File f : fileArray) {
                if (f.getName().equals(fileName)) {
                    return f.getPath();
                } else if (f.isDirectory()) {
                    String path = getFileRealPath(f.getPath(), fileName);
                    if (path != null) {
                        return path;
                    }
                }
            }
        }
        return null;
    }

    public static void main(String arg[]) throws Exception {
        // try {
        // String a = FileUtil.getExtension("t.txt");
        // System.out.println(a);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // String rootPath = "E:/kankan/";
        // String fileName = "VodMovieInfo.cfg";
        // String path = getFileRealPath(rootPath, fileName);
        // System.out.println("path is:" +path);
    }

    public static void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files.length > 0) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteFolder(f);
                    } else if (f.exists()) {
                        f.delete();
                    }
                }
            }
            folder.delete();
        }
    }

    public static void copyDir(File file, String path) {
        System.out.println("###############copy dir");
        System.out.println("###############file:" + file.getPath());
        System.out.println("###############path:" + path);
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                if (file.listFiles().length > 0) {
                    for (File f : file.listFiles()) {
                        if (f.isDirectory()) {
                            copyDir(f, dir.getPath() + File.separator + f.getName());
                        } else {
                            copyFile(f, dir.getPath() + File.separator + f.getName());
                        }
                    }
                }
            } else {
                copyFile(file, path + File.separator + file.getName());
            }
        }
    }
}
