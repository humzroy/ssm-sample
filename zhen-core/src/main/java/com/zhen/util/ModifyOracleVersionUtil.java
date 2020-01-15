package com.zhen.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：修改ORACL版本工具
 * Author：wuhengzhen
 * Date：2018-11-01
 * Time：10:56
 */
public class ModifyOracleVersionUtil {

    /**
     * description : 将oracle11g的dmp文件转换为10g（此处是修改为V10.02.01）
     * author : wuhengzhen
     * date : 2018-11-1 10:57
     */
    public static void oracle11gTo10g(String fileName) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "rw");
            byte[] b = new byte[50];
            raf.read(b);
            for (byte aB : b) {
                System.out.print(aB + ",");
            }
            // 这个是修改成10g：V10.02.01。如果在修改前自己可以截出头部的几个字节确认一下，是不是就是修改第12,18字节
            b[12] = 48;
            b[18] = 49;
            raf.seek(0);
            raf.write(b);
            System.out.println("导出成功！");
        } catch (Exception ex) {
            System.err.println("导出异常！");
            ex.printStackTrace();
        } finally {
            if (raf != null) {
                raf.close();
            }
        }
    }

    /**
     * description :遍历文件夹
     * author : wuhengzhen
     * date : 2018-11-1 11:01
     */
    public void traverseFolder(String path, List<String> list) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder(file2.getAbsolutePath(), list);
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        list.add(file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    /**
     * description :获取路径下的所有文件/文件夹
     * author : wuhengzhen
     * date : 2018-11-1 11:02
     *
     * @param directoryPath  需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     */
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (isAddDirectory) {
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    public static void main(String[] args) {
        // 将文件夹下11g的dmp文件改为10g
        // String dir = "D:\\dmp";
        // List<String> fileNameList = getAllFile(dir, false);
        // for (String aFileNameList : fileNameList) {
        //     try {
        //         oracle11gTo10g(aFileNameList);
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // }

        // 单个文件修改
        try {
            oracle11gTo10g("D:\\tmp\\test.dmp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
