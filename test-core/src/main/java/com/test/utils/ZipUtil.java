package com.test.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
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
    private static final int BUFFER = 8192;
    private static final String SEPARATOR = "/";

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
                System.out.println("add file: " + tmp[i].getName());
            }
            if (tmp[i].isDirectory()) {
                //若不是空目录，则递归添加其下的目录和文件
                if (tmp[i].listFiles().length != 0) {
                    fileList.addAll(getAllFiles(tmp[i]));
                } else {//若是空目录，则添加这个目录到fileList
                    fileList.add(tmp[i]);
                    System.out.println("add empty dir: " + tmp[i].getName());
                }
            }
        }    // end for

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
                relativePath.append(file.getName()).append("/").append(file.getName());
            }
        }    // end while
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
        String[] dirs = fileName.split("/");
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
            byte[] buffer = new byte[BUFFER];
            //每次读出来的长度
            int readLength;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                //若是zip条目目录，则需创建这个目录
                if (zipEntry.isDirectory()) {
                    File dir = new File(dstPath + "/" + zipEntry.getName());
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
                while ((readLength = zipInputStream.read(buffer, 0, BUFFER)) != -1) {
                    outputStream.write(buffer, 0, readLength);
                }
                outputStream.close();
                logger.info("file unCompressed: " + file.getCanonicalPath());
            }    // end while
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("unzip fail！zipFileName：" + zipFileName);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
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
        System.out.println("zip compressing...");

        File srcFile = new File(srcPath);
        //所有要压缩的文件
        List<File> fileList = getAllFiles(srcFile);
        //缓冲器
        byte[] buffer = new byte[BUFFER];
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
                    while ((readLength = inputStream.read(buffer, 0, BUFFER)) != -1) {
                        zipOutputStream.write(buffer, 0, readLength);
                    }
                    inputStream.close();
                    System.out.println("file compressed: " + file.getCanonicalPath());
                } else {//若是目录（即空目录）则将这个目录写入zip条目
                    zipEntry = new ZipEntry(getRelativePath(srcPath, file) + "/");
                    zipOutputStream.putNextEntry(zipEntry);
                    System.out.println("dir compressed: " + file.getCanonicalPath() + "/");
                }
            }    // end for

            zipOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("zip fail!");

            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("zip fail!");

            return false;
        }
        System.out.println("zip success!");
        return true;
    }

    /**
     * 函数名：getFileAndFold
     * 作用：使用递归，获取指定文件夹内的所有文件
     *
     * @param path：文件夹路径
     * @param deep：表示文件的层次深度，控制前置空格的个数 前置空格缩进，显示文件层次结构
     */
    public static String getFileAndFold(String path, int deep) {
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        StringBuilder fileName = new StringBuilder();

        for (File anArray : array) {
            //如果是文件
            if (anArray.isFile()) {
                // 只输出文件名字
                logger.info("文件名称：" + anArray.getName());
                fileName.append(anArray.getName()).append(";");
            } else if (anArray.isDirectory()) {
                // 如果是文件夹
                logger.info("文件夹名称：" + anArray.getName());
                fileName.append(anArray.getName()).append(";");
                // 文件夹需要调用递归 ，深度+1
                getFileAndFold(anArray.getPath(), deep + 1);
            }
        }
        return fileName.toString();
    }

    /**
     * 函数名：getFile
     * 获取指定文件夹里的所有文件
     *
     * @param path：文件夹路径
     * @return
     * @author Luxin.xiao
     * @date 2018/8/27 13:48
     */
    public static String getFile(String path) throws Exception {
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        StringBuilder fileName = new StringBuilder();
        for (File anArray : array) {
            //如果是文件
            if (anArray.isFile()) {
                // 只输出文件名字
                logger.info("文件名称：" + anArray.getName());
                fileName.append(anArray.getName()).append(";");
            }
        }
        return fileName.toString();
    }

    /**
     * 移动文件去指定的文件夹
     *
     * @param oriPath:原始文件夹路径
     * @param newPath:新文件夹路径
     * @return
     * @author Luxin.xiao
     * @date 2018/8/27 13:52
     */
    public static boolean moveFile(String oriPath, String newPath) throws Exception {
        // 获得指定文件对象
        File oriFile = new File(oriPath);
        File newFile = new File(newPath);
        if (newFile.exists()) {
            newFile.delete();
        }
        if (oriFile.renameTo(new File(newPath))) {
            logger.info("File is moved successful！");
            logger.info("文件转移成功！");
            return true;
        } else {
            logger.info("File is failed to move！");
            logger.info("文件转移失败！");
            return false;
        }
    }

    /**
     * @description: 压缩zip文件
     * @param: [sourcePath, zipPath, encoding] 文件或文件夹路径 ，生成的zip文件存在路径（包括文件名）,编码方式
     * @return: void
     * @author: Qu.ZeHu
     * @date: 2018/8/30 13:50
     */
    public static void createZip(String sourcePath, String zipPath, String zipFileName, String encoding) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            if (!zipPath.endsWith(SEPARATOR)) {
                zipPath = zipPath + SEPARATOR;
            }
            fos = new FileOutputStream(zipPath + zipFileName);
            zos = new ZipOutputStream(fos);
            //此处修改字节码方式。
            writeZip(new File(sourcePath), "", zos);
        } catch (FileNotFoundException e) {
            logger.error("创建ZIP文件失败", e);
        } finally {
            try {
                if (zos != null) {
                    zos.closeEntry();
                    zos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                try {
                    java.util.zip.ZipFile zip = new java.util.zip.ZipFile(new File(zipPath + zipFileName));
                    zip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                logger.error("创建ZIP文件失败", e);
            }

        }
    }

    /**
     * 写入Zip
     *
     * @param file
     * @param parentPath
     * @param zos
     */
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            //处理文件夹
            if (file.isDirectory()) {
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                if (files.length != 0) {
                    for (File f : files) {
                        writeZip(f, parentPath, zos);
                    }
                } else {       //空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                        zos.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }
                    zos.closeEntry();
                } catch (FileNotFoundException e) {
                    logger.error("创建ZIP文件失败", e);
                } catch (IOException e) {
                    logger.error("创建ZIP文件失败", e);
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        logger.error("创建ZIP文件失败", e);
                    }
                }
            }
        }
    }

    /**
     * 压缩zip格式文件
     *
     * @param targetFile  输出的文件。
     * @param sourceFiles 带压缩的文件数组。
     * @return 如果所有文件压缩成功，则返回true；如果有任何文件未成功压缩，则返回false。
     * @throws IOException 如果出错后无法删除目标文件或无法覆盖目标文件。
     */
    public static boolean compressZip(File targetFile, File... sourceFiles) throws IOException {
        ZipOutputStream zipOut;
        boolean flag;
        if (targetFile.exists() && !targetFile.delete()) {
            throw new IOException();
        }
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(targetFile));
            BufferedOutputStream out = new BufferedOutputStream(zipOut);
            flag = compressZip(zipOut, out, "", sourceFiles);
            out.close();
            zipOut.close();
        } catch (IOException e) {
            targetFile.delete();
            throw new IOException(e);
        }
        return flag;
    }

    /**
     * 是否成功压缩Zip
     *
     * @param zipOut
     * @param out
     * @param filePath
     * @param sourceFiles
     * @return
     * @throws IOException
     */
    private static boolean compressZip(ZipOutputStream zipOut, BufferedOutputStream out, String filePath, File... sourceFiles)
            throws IOException {
        if (null != filePath && !"".equals(filePath)) {
            filePath += filePath.endsWith(File.separator) ? "" : File.separator;
        } else {
            filePath = "";
        }
        boolean flag = true;
        for (File file : sourceFiles) {
            if (null == file) {
                continue;
            }
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                if (null == fileList) {
                    return false;
                } else if (1 > fileList.length) {
                    zipOut.putNextEntry(new ZipEntry(filePath + file.getName() + File.separator));
                } else {
                    // 只要flag有一次为false，整个递归的结果都为false。
                    flag = compressZip(zipOut, out, filePath + File.separator + file.getName(), fileList) && flag;
                }
            } else {
                zipOut.putNextEntry(new ZipEntry(filePath + file.getName()));
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                int bytesRead;
                while (-1 != (bytesRead = in.read())) {
                    out.write(bytesRead);
                }
                in.close();
            }
            out.flush();
        }
        return flag;
    }

}
