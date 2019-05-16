package com.zhen.utils;

import java.io.*;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：TXT文件操作工具类
 * Auth：wuhengzhen
 * Date：2019-04-11
 * Time：14:02
 */
public class TxtFileUtil {
    /**
     * description :私有构造方法
     * author : wuhengzhen
     * date : 2019/4/11 14:02
     */
    private TxtFileUtil() {
    }

    /**
     * description :读取txt文件
     * author : wuhengzhen
     * date : 2019/4/11 14:04
     */
    public static void readFile() {
        // 绝对路径或相对路径都可以，写入文件时演示相对路径，读取以上路径的input.txt文件
        String pathname = "D:\\workspace\\test\\input.txt";
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(pathname);
             // 建立一个对象，它把文件内容转成计算机能读懂的语言
             BufferedReader br = new BufferedReader(reader)
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                // System.out.println(line);
                stringBuilder.append(line);
            }
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * description :写入TXT文件
     * author : wuhengzhen
     * date : 2019/4/11 14:06
     */
    public static void writeFile() {
        try {
            // 相对路径，如果没有则要建立一个新的output.txt文件(路径：D:\workspace\test\output.txt)
            File writeName = new File("../../test/output.txt");
            // 创建新文件,有同名的文件的话直接覆盖
            writeName.createNewFile();
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                // \r\n即为换行
                out.write("我会写入文件啦1\r\n");
                // \r\n即为换行
                out.write("我会写入文件啦2\r\n");
                // 把缓存区内容压入文件
                out.flush();
                System.out.println("写入文件成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * description : main method
     * author : wuhengzhen
     * date : 2019/4/11 14:10
     */
    public static void main(String[] args) {
        readFile();
    }
}
