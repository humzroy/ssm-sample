package com.zhen.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
public class HttpsUtil {
    /**
     * 日志信息
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpsUtil.class);
    private static final CloseableHttpClient HTTP_CLIENT;
    private static final String CHARSET = "UTF-8";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final int MAX_TIMEOUT = 10000;

    // 采用静态代码块，初始化超时时间配置，再根据配置生成默认httpClient对象
    static {
        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HTTP, PlainConnectionSocketFactory.INSTANCE)
                .register(HTTPS, new SSLConnectionSocketFactory(sslcontext, new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                }))
                .build();
        // 设置连接池
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        RequestConfig requestConfig = configBuilder.build();
        HTTP_CLIENT = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connMgr).build();
    }

    //region Description https请求

    /**
     * @description :https发送get请求，kv格式
     * @author : wuhengzhen
     * @date : 2018-9-12 9:02
     */
    public static String httpsGet(String url, String param) throws IOException {
        if (!StringUtils.isNotBlank(param)) {
            url = url + "?" + param;
        }
        //创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * @description :https发送post请求，json格式
     * @author : wuhengzhen
     * @date : 2018-9-12 9:02
     */
    public static String httpsPostJson(String url, String jsonStr) {
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        //设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(jsonStr, CHARSET);
        stringEntity.setContentEncoding(CHARSET);
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        return execute(httpPost);
    }

    /**
     * @description :https发送post请求，kv格式
     * @author : wuhengzhen
     * @date : 2018-9-12 9:02
     */
    public static String httpsPost(String url, Map<String, String> map) throws IOException {
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        //装填参数
        List<NameValuePair> nvps = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, CHARSET));

        //设置header信息
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        return execute(httpPost);
    }

    /**
     * @description :发送请求
     * @author : wuhengzhen
     * @date : 2018-9-12 9:02
     */
    public static String execute(HttpPost httpPost) {
        String respStr = "";
        CloseableHttpResponse response = null;
        try {
            //执行请求操作，并拿到结果（同步阻塞）
            response = HTTP_CLIENT.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("HTTP请求，响应码：" + statusCode);
            org.apache.http.HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                respStr = EntityUtils.toString(entity, CHARSET);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        } finally {
            // 释放链接
            if (httpPost != null) {
                try {
                    httpPost.releaseConnection();
                } catch (Exception e) {
                    logger.error(ExceptionUtil.getStackTrace(e));
                }
            }
            if (response != null) {
                try {
                    //获取结果实体
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    logger.error(ExceptionUtil.getStackTrace(e));
                }
            }
        }

        return respStr;
    }

    //endregion https请求

    /**
     * 发送POST请求（带token）
     *
     * @param requestUrl
     * @param accessToken
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String requestUrl, String accessToken, String params) throws Exception {
        String contentType = "application/x-www-form-urlencoded";
        return HttpsUtil.post(requestUrl, accessToken, contentType, params);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params) throws Exception {
        String encoding = "UTF-8";
        if (requestUrl.contains("nlp")) {
            encoding = "GBK";
        }
        return HttpsUtil.post(requestUrl, accessToken, contentType, params, encoding);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params, String encoding) throws Exception {
        String url = requestUrl + "?access_token=" + accessToken;
        return HttpsUtil.postGeneralUrl(url, contentType, params, encoding);
    }

    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding) throws Exception {
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(params.getBytes(encoding));
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encoding));
        StringBuilder result = new StringBuilder();
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result.append(getLine);
        }
        in.close();
        System.err.println("result:" + result);
        return result.toString();
    }


    /**
     * @description :绕过验证
     * @author : wuhengzhen
     * @date : 2018-9-12 9:02
     */
    public static SSLContext createIgnoreVerifySSL() {
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext sc = null;
        try {
            // TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            sc = SSLContext.getInstance("TLS");
            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            sc.init(null, new TrustManager[]{trustManager}, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return sc;
    }
}