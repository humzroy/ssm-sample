package com.zhen.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Description：Zip压缩/解压缩工具类
 * Author：wuhengzhen
 * Date：2018-09-20
 * Time：13:27
 */
public class ZipUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 缓冲器大小
     */
    private static final int BUFFER_SIZE = 2 * 1024;
    private static final String SEPARATOR = "/";

    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir           压缩文件夹路径
     * @param zipFileName      目标压缩文件
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         <p>
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */

    public static boolean toZip(String srcDir, String zipFileName, boolean keepDirStructure) throws RuntimeException {
        boolean flag;
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFileName));
            File sourceFile = new File(srcDir);
            flag = compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);
            long end = System.currentTimeMillis();
            logger.info("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 压缩成ZIP 方法2
     *
     * @param srcFiles    需要压缩的文件列表
     * @param zipFileName 目标压缩文件
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */

    public static void toZip(List<File> srcFiles, String zipFileName) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            logger.info("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         <p>
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static boolean compress(File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure) throws Exception {
        boolean flag = false;
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
            flag = true;
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (keepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + SEPARATOR));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                    flag = true;
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (keepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        flag = compress(file, zos, name + SEPARATOR + file.getName(), keepDirStructure);
                    } else {
                        flag = compress(file, zos, file.getName(), keepDirStructure);
                    }
                }
            }
        }
        return flag;
    }

    /**
     * zip解压
     *
     * @param srcFile     zip源文件
     * @param destDirPath 解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                logger.info("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + SEPARATOR + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + SEPARATOR + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            logger.info("解压完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解压缩方法
     *
     * @param zipFileName 压缩文件名
     * @param dstPath     解压目标路径
     * @return
     */
    public static boolean unZip(String zipFileName, String dstPath) throws Exception {
        logger.info("zip unCompressing...");
        ZipInputStream zipInputStream = null;
        OutputStream outputStream;
        FileInputStream fileInputStream = null;
        try {
            zipInputStream = new ZipInputStream(fileInputStream);
            fileInputStream = new FileInputStream(zipFileName);
            ZipEntry zipEntry;
            //缓冲器
            byte[] buffer = new byte[BUFFER_SIZE];
            //每次读出来的长度
            int readLength;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                //若是zip条目目录，则需创建这个目录
                if (zipEntry.isDirectory()) {
                    File dir = new File(dstPath + SEPARATOR + zipEntry.getName());
                    if (!dir.exists()) {
                        dir.mkdirs();
                        logger.info("create mkdirs：" + dir.getCanonicalPath());
                    }
                    //跳出
                    continue;
                }
                //若是文件，则需创建该文件
                File file = createFile(dstPath, zipEntry.getName());
                logger.info("create file：" + file.getCanonicalPath());
                outputStream = new FileOutputStream(file);
                while ((readLength = zipInputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                    outputStream.write(buffer, 0, readLength);
                }
                outputStream.close();
                logger.info("file unCompressed: " + file.getCanonicalPath());
            }
            // end while
        } catch (FileNotFoundException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            logger.error("unzip fail！zipFileName：" + zipFileName);
            return false;
        } catch (IOException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            logger.error("unzip fail！zipFileName：" + zipFileName);
            return false;
        } finally {
            if (zipInputStream != null) {
                zipInputStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        logger.info("unzip success！zipFileName：" + zipFileName);
        return true;
    }

    /**
     * 压缩方法
     * （可以压缩空的子目录）
     *
     * @param srcPath     压缩源路径
     * @param zipFileName 目标压缩文件
     * @return
     */
    public static boolean zip(String srcPath, String zipFileName) {
        logger.info("zip compressing...");

        File srcFile = new File(srcPath);
        //所有要压缩的文件
        List<File> fileList = getAllFiles(srcFile);
        //缓冲器
        byte[] buffer = new byte[BUFFER_SIZE];
        ZipEntry zipEntry = null;
        //每次读出来的长度
        int readLength = 0;
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (File file : fileList) {
                //若是文件，则压缩这个文件
                if (file.isFile()) {
                    zipEntry = new ZipEntry(getRelativePath(srcPath, file));
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zipOutputStream.putNextEntry(zipEntry);
                    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                    while ((readLength = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                        zipOutputStream.write(buffer, 0, readLength);
                    }
                    inputStream.close();
                    logger.info("file compressed: " + file.getCanonicalPath());
                } else {//若是目录（即空目录）则将这个目录写入zip条目
                    zipEntry = new ZipEntry(getRelativePath(srcPath, file) + SEPARATOR);
                    zipOutputStream.putNextEntry(zipEntry);
                    logger.info("dir compressed: " + file.getCanonicalPath() + SEPARATOR);
                }
            }
            // end for
            zipOutputStream.close();
        } catch (FileNotFoundException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            logger.error("zip fail!");
            return false;
        } catch (IOException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            logger.error("zip fail!");
            return false;
        }
        logger.info("zip success!");
        return true;
    }

    /**
     * 取的给定源目录下的所有文件及空的子目录
     * 递归实现
     *
     * @param srcFile
     * @return
     */
    private static List<File> getAllFiles(File srcFile) {
        List<File> fileList = new ArrayList<>();
        File[] tmp = srcFile.listFiles();

        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].isFile()) {
                fileList.add(tmp[i]);
                logger.info("add file: " + tmp[i].getName());
            }
            if (tmp[i].isDirectory()) {
                //若不是空目录，则递归添加其下的目录和文件
                if (tmp[i].listFiles().length != 0) {
                    fileList.addAll(getAllFiles(tmp[i]));
                } else {//若是空目录，则添加这个目录到fileList
                    fileList.add(tmp[i]);
                    logger.info("add empty dir: " + tmp[i].getName());
                }
            }
        }
        // end for
        return fileList;
    }

    /**
     * 取相对路径
     * 依据文件名和压缩源路径得到文件在压缩源路径下的相对路径
     *
     * @param dirPath 压缩源路径
     * @param file
     * @return 相对路径
     */
    private static String getRelativePath(String dirPath, File file) {
        File dir = new File(dirPath);
        StringBuilder relativePath = new StringBuilder();
        relativePath.append(file.getName());
        while (true) {
            file = file.getParentFile();
            if (file == null) {
                break;
            }
            if (file.equals(dir)) {
                break;
            } else {
                relativePath.append(file.getName()).append(SEPARATOR).append(file.getName());
            }
        }
        // end while
        return relativePath.toString();
    }

    /**
     * 创建文件
     * 根据压缩包内文件名和解压缩目的路径，创建解压缩目标文件，
     * 生成中间目录
     *
     * @param dstPath  解压缩目的路径
     * @param fileName 压缩包内文件名
     * @return 解压缩目标文件
     * @throws IOException
     */
    private static File createFile(String dstPath, String fileName) throws IOException {
        //将文件名的各级目录分解
        String[] dirs = fileName.split(SEPARATOR);
        File file = new File(dstPath);
        //文件有上级目录
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                //依次创建文件对象知道文件的上一级目录
                file = new File(file, dirs[i]);
            }
            if (!file.exists()) {
                //文件对应目录若不存在，则创建
                file.mkdirs();
                logger.info("create mkdirs：" + file.getCanonicalPath());
            }
            //创建文件
            file = new File(file, dirs[dirs.length - 1]);
            return file;
        } else {
            if (!file.exists()) {
                //若目标路径的目录不存在，则创建
                file.mkdirs();
                logger.info("create mkdirs：" + file.getCanonicalPath());
            }
            //创建文件
            file = new File(file, dirs[0]);
            return file;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        /** 测试压缩方法1  */
        boolean flag = ZipUtil.toZip("E:/logs", "E:/mytest01.zip", true);
        logger.info("" + flag);
    }
}
