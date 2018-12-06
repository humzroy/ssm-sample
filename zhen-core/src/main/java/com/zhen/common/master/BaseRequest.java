package com.zhen.common.master;

import com.zhen.utils.ExceptionUtil;
import com.zhen.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：封装请求
 * Auth：wuhengzhen
 * Date：2018-12-05
 * Time：16:50
 */
public class BaseRequest implements Serializable {
    /**
     * 私有的构造方法，防止new
     */
    private BaseRequest() {
    }

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseRequest.class);

    private static final long serialVersionUID = 179608629396999149L;
    /**
     * 用于存放数据的MAP
     */
    private Map<String, Object> data = new HashMap<>();

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * 封装data
     *
     * @param key   key
     * @param value value
     */
    public void putValueToData(String key, Object value) {
        this.getData().put(key, value);
    }

    /**
     * 根据key获取data的值
     *
     * @param key key
     * @return Object
     */
    public Object getValueFormData(String key) {
        return this.getData().get(key);
    }

    /**
     * description : 返回BaseRequest
     * author : wuhengzhen
     * date : 2018-12-5 16:55
     */
    public static BaseRequest createRequest(HttpServletRequest request, Master master) {

        BaseRequest baseRequest = new BaseRequest();
        String ip = RequestUtil.getIpAddr(request);
        logger.info("远程请求IP：" + ip);
        // String serverAllAddress = request.getRequestURL() + "";
        // String uul = serverAllAddress.substring(7);
        // String serverAddr = uul.substring(0, uul.indexOf("/"));
        master.setRemoteLocalAddr(ip);

        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    String masterSign;
                    try {
                        masterSign = cookie.getValue();
                        master.setSign(masterSign);
                    } catch (Exception e) {
                        logger.error(ExceptionUtil.getStackTrace(e));
                    }
                }
            }
        }
        // 在此方法内进行master的赋值,可省略外部controller的多次赋值.
        baseRequest.putValueToData("master", master);
        return baseRequest;
    }
}
