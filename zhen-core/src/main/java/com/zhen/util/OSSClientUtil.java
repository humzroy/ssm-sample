package com.zhen.util;

import com.alibaba.druid.util.StringUtils;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;


/**
 * Description：阿里云 OSS文件类
 * Author：wuhengzhen
 * Date：2018-09-20
 * Time：13:27
 */

public class OSSClientUtil {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(OSSClientUtil.class);

    /**
     * OSS endpoint
     */
    private String endpoint;
    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
    private String accessKeyId;
    /**
     * 这里是'ossuser'的权限
     */
    private String accessKeySecret;

    /**
     * 存储空间
     */
    private String bucketName;
    /**
     * 文件存储目录
     */
    private String filedir;

    private OSSClient ossClient;

    public OSSClientUtil(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String filedir) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.filedir = filedir;

        ClientConfiguration config = new ClientConfiguration();
        config.setProtocol(Protocol.HTTPS);
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, config);
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        if ("bmp".equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(filenameExtension) || "jpg".equalsIgnoreCase(filenameExtension) || "png".equalsIgnoreCase(filenameExtension)) {
            return "image/jpeg";
        }
        if ("html".equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        if ("pptx".equalsIgnoreCase(filenameExtension) || "ppt".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("docx".equalsIgnoreCase(filenameExtension) || "doc".equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        if ("xml".equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        return "image/jpeg";

    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */

    public String getImgUrl(String fileUrl) {

        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;

    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */

    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();

            objectMetadata.setContentLength(instream.available());

            objectMetadata.setCacheControl("no-cache");

            objectMetadata.setHeader("Pragma", "no-cache");

            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));

            objectMetadata.setContentDisposition("inline;filename=" + fileName);

            // 上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, fileName, instream, objectMetadata);
            ret = putResult.getETag();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    public void deleteFile(String fileName) {
        ossClient.deleteObject(bucketName, fileName);
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {

        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
}
