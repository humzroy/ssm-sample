package com.zhen.base.service.scheduled;

import com.zhen.utils.HttpClientUtil;
import com.zhen.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：ScheduledTest
 * @Description：定时任务测试
 * @Author：wuhengzhen
 * @Date：2019-07-03 18:12
 */
@Component
public class ScheduledTest {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTest.class);
    /**
     * 基础URL
     */
    public static final String BASE_URL = "http://10.192.2.13:8208/fsdpl-api/api";
    /**
     * 服务URL
     */
    public static final String URL = "/rbaTradeQueryCService.tradeRecordDetailQuery";

    /**
     * 测试定时任务 //每隔1min执行一次
     */
    // @Scheduled(cron = "0 0/1 * * * ? ")
    public void testScheduled() {
        String appClientId = "QINGDAOYICAICFFSDPL40";
        String appId = "web1.1";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("comb_request_no", "20190710000042");
        paramMap.put("trade_acco", "ZHLC000000000014");
        String param = JsonUtils.obj2Json(paramMap);

        Map<String, String> urlParam = new HashMap<>();

        urlParam.put("app_client_id", appClientId);
        urlParam.put("app_id", appId);
        urlParam.put("param", param);
        String resJson;
        try {
            long start = System.currentTimeMillis();
            resJson = HttpClientUtil.get(BASE_URL + URL, urlParam);
            long elapsed = System.currentTimeMillis() - start;
            logger.info("调用恒生服务结束...响应报文为：{}，耗时：{}ms", resJson, elapsed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
