package com.test.base.service.demo;

import com.alibaba.fastjson.JSONObject;
import com.test.utils.DateUtils;
import com.test.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wuhengzhen
 * @Description：
 * @date：2018-09-17 17:27
 */
@Component
public class DemoServiceImpl implements IDemoService {
    private static Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    /**
     * say hello
     *
     * @param name
     * @return
     */
    @Override
    public String sayHello(String name) {

        JSONObject reqData = new JSONObject();
        JSONObject msg = new JSONObject();
        msg.put("name", name);
        reqData.put("data", msg);
        reqData.put("msgNo", "1001");
        reqData.put("reqTime", DateUtils.formatDateTime(new Date()));
        String url = "http://10.10.10.93:9696/test-outapiweb/outapi/testApi";
        String resp = HttpClientUtil.doPostJson(url, reqData.toJSONString());

        logger.info("service 返回消息为：" + resp);
        return resp;
    }
}
