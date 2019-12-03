package com.zhen.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：HttpUtil
 * @Description：封装http请求
 * @Author：wuhengzhen
 * @Date：2019-05-29 9:40
 */
public class HttpUtil {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static RestTemplate restTemplate = new RestTemplate();

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
     * get请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doGet(String url, Map<String, Object> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, String.valueOf(param.get(key)));
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
     * post请求
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * post请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, Map<String, Object> param) {
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
                    paramList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
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
     * 封装HTTP GET方法
     * 无参数的Get请求
     *
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String get(String url) throws ClientProtocolException, IOException {
        //首先需要先创建一个DefaultHttpClient的实例
        HttpClient httpClient = new DefaultHttpClient();
        //先创建一个HttpGet对象,传入目标的网络地址,然后调用HttpClient的execute()方法即可:
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(URI.create(url));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP GET方法 UrlEncoded
     * 有参数的Get请求
     *
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String get(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        String httpEntityContent;
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet();
            List<NameValuePair> formParams = setHttpParams(paramMap);
            String param = URLEncodedUtils.format(formParams, "UTF-8");
            httpGet.setURI(URI.create(url + "?" + param));
            HttpResponse response = httpClient.execute(httpGet);
            httpEntityContent = getHttpEntityContent(response);
            httpGet.abort();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return httpEntityContent;
    }

    /**
     * 封装HTTP POST方法 UrlEncoded
     *
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String post(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        String httpEntityContent;
        HttpClient httpClient = new DefaultHttpClient();
        try {
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> formParams = setHttpParams(paramMap);
            UrlEncodedFormEntity param = new UrlEncodedFormEntity(formParams, "UTF-8");
            //通过setEntity()设置参数给post
            httpPost.setEntity(param);
            //利用httpClient的execute()方法发送请求并且获取返回参数
            HttpResponse response = httpClient.execute(httpPost);
            httpEntityContent = getHttpEntityContent(response);
            httpPost.abort();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return httpEntityContent;
    }

    /**
     * 设置请求参数
     *
     * @param
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formParams = new ArrayList<>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formParams;
    }

    /**
     * 获得响应HTTP实体内容
     *
     * @param response
     * @return
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        //通过HttpResponse 的getEntity()方法获取返回信息
        org.apache.http.HttpEntity entity = response.getEntity();
        logger.info("HTTP STATUS CODE：" + response.getStatusLine().getStatusCode());
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
            is.close();
            return sb.toString();
        }
        return "";
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