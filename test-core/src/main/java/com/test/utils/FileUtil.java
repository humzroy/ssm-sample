package com.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：File操作工具类
 * Auth：wuhengzhen
 * Date：2018-10-23
 * Time：13:58
 */
public class FileUtil {
    /**
     * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
     */
    private FileUtil() {
    }

    private static File file;
    private static boolean flag;
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private final static String SEPARATOR = "|";

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     * 对外
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) throws Exception {
        flag = false;
        file = new File(sPath);
        // 判断目录或文件是否存在,不存在返回 false
        if (!file.exists()) {
            return flag;
        } else {
            // 判断是否为文件,为文件时调用删除文件方法
            if (file.isFile()) {
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     * 对内
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteFile(String sPath) throws Exception {
        flag = false;
        file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * 对内
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String sPath) throws Exception {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                // 删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        return dirFile.delete();
    }

    /**
     * description: 获得当前目录下，筛选的文件名称
     * param  [path, isFileFlag, targetType 文件路径，是否是文件，筛选的文件类型
     * return java.util.List<java.lang.String>
     * author: wuhengzhen
     * date: 2018/8/31 13:14
     */
    public static List<String> getDirectoryOrFile(String path, boolean isFileFlag, String targetType) throws Exception {
        // 获得指定文件对象
        File file = new File(path);
        // 定义返回的文件名称数组
        List<String> fileArr = new ArrayList<>();
        if (!isFileFlag && StringUtils.isNotBlank(targetType)) {
            logger.info("传入的参数异常");
            throw new Exception();
        }
        if (StringUtils.isNotBlank(targetType)) {
            // 获得该文件夹内的所有文件
            File[] array = file.listFiles();
            for (int i = 0; i < array.length; i++) {
                if (targetType.indexOf(SEPARATOR) != -1) {
                    String[] targetTypes = targetType.split(SEPARATOR);
                    for (String type : targetTypes) {
                        doAddFileName(array[i], isFileFlag, type, fileArr);
                    }
                } else {
                    doAddFileName(array[i], isFileFlag, targetType, fileArr);
                }
            }
        } else {
            doAddFileName(path, isFileFlag, fileArr);
        }
        return fileArr;
    }

    /**
     * description: 获得当前目录下的子文件夹名称
     * param: [path, isFileFlag] 输入的目录路径, 是文件（true），是目录（false）
     * return: java.lang.String 返回的所有子文件夹名称字符串数组
     * author: wuhengzhen
     * date: 2018/8/29 13:44
     */
    private static void doAddFileName(String path, boolean isFileFlag, List<String> fileArr) throws Exception {
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            if (isFileFlag) {
                // 如果是文件
                if (array[i].isFile()) {
                    // 输出文件名字
                    logger.info("文件名称：" + array[i].getName());
                    fileArr.add(array[i].getName());
                }
            } else {
                //如果是目录
                if (array[i].isDirectory()) {
                    // 输出文件名字
                    logger.info("目录名称：" + array[i].getName());
                    fileArr.add(array[i].getName());
                }
            }
        }
    }

    /**
     * 获得当前目录下的子文件夹名称
     *
     * @param file
     * @param isFileFlag
     * @param targetType
     * @param fileArr
     */
    private static void doAddFileName(File file, boolean isFileFlag, String targetType, List<String> fileArr) {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (isFileFlag) {
            // 如果是文件
            if (file.isFile() && fileType.equals(targetType)) {
                // 输出文件名字
                logger.info("文件名称：" + file.getName());
                fileArr.add(file.getName());
            }
        } else {
            //如果是目录
            if (file.isDirectory()) {
                // 输出文件名字
                logger.info("目录名称：" + file.getName());
                fileArr.add(file.getName());
            }
        }
    }

    /**
     * description: 获得某一目录下所有的文件的文件名，以“|”分割
     * param: [path]
     * return: java.lang.String
     * author: wuhengzhen
     * date: 2018/8/29 16:43
     */

    public static String getFileNameStr(String path) throws Exception {
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        StringBuffer fileName = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            //如果是文件
            if (array[i].isFile()) {
                // 只输出文件名字
                logger.info("文件名称：" + array[i].getName());
                if (i + 1 == array.length) {
                    fileName.append(array[i].getName());
                } else {
                    fileName.append(array[i].getName()).append(FileUtil.SEPARATOR);
                }
            }
        }
        return fileName.toString();
    }

    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流
        FileInputStream fis = new FileInputStream(filePath);
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];
        // 用于保存实际读取的字节数
        int hasRead = 0;
        while ((hasRead = fis.read(bbuf)) > 0) {
            sb.append(new String(bbuf, 0, hasRead));
        }
        fis.close();
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }
}
