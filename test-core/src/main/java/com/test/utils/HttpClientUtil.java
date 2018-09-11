package com.test.utils;

import com.test.exception.BusinessException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : wuhengzhen
 * @Description : HTTP请求工具类
 * @date : 2018/09/07 17:55
 * @system name:
 * @copyright:
 */
public class HttpClientUtil {
    /**
     * 日志信息
     */
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static RestTemplate restTemplate = new RestTemplate();


    /**
     * get请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeResource(httpClient, response);
            } catch (IOException e) {
                logger.error("关闭资源异常!", e);
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 关闭资源
     *
     * @param httpClient
     * @param response
     */
    private static void closeResource(CloseableHttpClient httpClient, CloseableHttpResponse response) throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
        if (response != null) {
            response.close();
        }
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * post请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeResource(httpClient, response);
            } catch (IOException e) {
                logger.error("关闭资源异常!", e);
                e.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     * post请求
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * POST请求，数据格式为JSON
     *
     * @param url
     * @param json
     * @return
     */
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeResource(httpClient, response);
            } catch (IOException e) {
                logger.error("关闭资源异常!", e);
                e.printStackTrace();
            }
        }

        return resultString;
    }


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
