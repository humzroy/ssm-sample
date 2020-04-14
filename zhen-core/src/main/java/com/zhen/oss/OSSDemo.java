package com.zhen.oss;

import com.zhen.util.OSSClientUtil;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：
 * Auth：wuhengzhen
 * Date：2018-12-27
 * Time：19:48
 */
public class OSSDemo {

    public static void main(String[] args) {
        // OSS endpoint
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyID = "LTAI7V9HIIoTBcRd";
        // 这里是'apiuser'的权限
        String accessKeySecret = "AyFTNNhAjGlN15vO1cq0r0JIvQNnZh";
        // 存储空间
        String bucketName = "wuhengzhen-oss";
        // 文件目录
        String filedir = "demo/";

        try {
            uploadFileTest(endpoint, accessKeyID, accessKeySecret, bucketName, filedir);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    /**
     * upload test
     *
     * @param endpoint        OSS endpoint
     * @param accessKeyID     账号accessKeyID
     * @param accessKeySecret 账号accessKeySecret
     * @param bucketName      存储空间
     * @param filedir         文件目录
     */
    private static void uploadFileTest(String endpoint, String accessKeyID, String accessKeySecret, String bucketName, String filedir) throws Exception {
        OSSClientUtil client = new OSSClientUtil(endpoint, accessKeyID, accessKeySecret, bucketName, filedir);
        // InputStream inputStream = new FileInputStream("D:\\test\\imag\\timg.jpg");
        // String ret = client.uploadFile2OSS(inputStream, "demo/图片.jpg");
        // System.out.println(ret);
        client.deleteFile("demo/test2.jpg");
        String url = client.getImgUrl("图片.jpeg");
        System.out.println(url);
    }


}
