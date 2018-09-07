package com.test.utils;

import com.test.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author : wuhengzhen
 * @Description : HTTP请求工具类
 * @date : 2018/09/07 17:55
 * @system name:
 * @copyright:
 */
public class HttpUtil {
    /**
     * 日志信息
     */
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static RestTemplate restTemplate = new RestTemplate();


    /**
     * 发送POST请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String sendUrlPost(String url, String param) {
        PrintWriter writer = null;
        URL sendUrl;
        BufferedReader in = null;
        String result = "";
        try {
            sendUrl = new URL(url);
            //打开连接
            URLConnection connect = sendUrl.openConnection();
            //设置请求属性
            connect.setRequestProperty("accept", "*/*");
            connect.setRequestProperty("connection", "Keep-Alive");
            //aplication/json  aplication/xml
            connect.setRequestProperty("content-type", "text/html");
            // 发送POST请求必须设置如下两行
            connect.setDoOutput(true);
            connect.setDoInput(true);
            //设置连接超时，读取超时
            connect.setConnectTimeout(1000 * 60);
            connect.setReadTimeout(1000 * 60);
            //创建输出流(UTF-8)
            writer = new PrintWriter(new OutputStreamWriter(connect.getOutputStream(), "UTF-8"));
            writer.print(param);
            writer.flush();
            //定义BufferedReader获取Url响应信息
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("post请求出现错误！", e);
            throw new BusinessException("发送POST请求出现异常！");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("post请求出现错误！", e);
                throw new BusinessException("发送POST请求出现异常！");
            }
        }
        return result;
    }

    /**
     * @description :发送POST请求、数据格式JSON
     * @author : wuhengzhen
     * @date : 2018-7-11 14:21
     */
    public static String sendPostJson(String url, String reqJsonStr) {
        String repStr = "";
        logger.info("请求URL：" + url);
        logger.info("请求报文内容：\n" + reqJsonStr);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
            headers.setConnection("close");
            HttpEntity<String> entity = new HttpEntity<>(reqJsonStr, headers);
            ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
            repStr = result.getBody();
            logger.info("返回报文内容：\n" + repStr);
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error("发送Post请求异常" + e.getMessage());
        }
        return repStr;
    }

    /**
     * @description :Get请求、数据格式为JSON
     * @author : wuhengzhen
     * @date : 2018-5-21 17:46:33
     */
    public static String sendGetJson(String url, String reqParameter) {
        String result = "";
        logger.info("请求URL：\n" + url);
        logger.info("请求报文内容：\n" + reqParameter);
        try {
            url = url + "?" + reqParameter;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
            headers.setConnection("close");
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            result = response.getBody();
            logger.info("返回报文内容：\n" + result);
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error("发送Get请求异常" + e.getMessage());
        }
        return result;
    }

    /**
     * @description : 读取HTTP请求body信息
     * @author : wuhengzhen
     * @date : 2018-5-24 14:28
     */
    public static String getBodyMessage(HttpServletRequest request) {
        BufferedReader reader;
        String line;
        String xmlString = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            StringBuilder inputString = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            reader.close();
            xmlString = inputString.toString();

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("读取body异常！" + e.getMessage());
        }
        return xmlString;
    }
}
