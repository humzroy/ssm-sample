package com.test.ocr.baidu;

import com.baidu.aip.util.Base64Util;
import com.test.utils.FileUtil;
import com.test.utils.HttpClientUtil;

import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：百度OCR文字识别Demo
 * Auth：wuhengzhen
 * Date：2018-10-23
 * Time：13:55
 */
public class BaiduOCR {
    /**
     * 百度通用文字识别（含位置信息版）URL
     */
    private static final String OCR_HOST = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
    /**
     * main 方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // 本地图片路径
        String filePath = "E:/img/38.jpg";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /*
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthService.getAuth();
            // 注意：Content-Type = application/x-www-form-urlencoded
            String result = HttpClientUtil.post(OCR_HOST, accessToken, params);
            System.out.println("百度OCR返回结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
