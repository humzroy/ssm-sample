package com.zhen.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

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
 * @Description：封装http请求（URL编码版本）
 * @Author：wuhengzhen
 * @Date：2019-05-29 9:40
 */
public class HttpUtil {
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
        System.out.println("返回响应码：" + response.getStatusLine().getStatusCode());
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
}
