package com.zhen.base.service.scheduled;

import com.zhen.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
     * 测试定时任务 //每隔1min执行一次
     */
    // @Scheduled(cron = "0 0/1 * * * ? ")
    public void testScheduled() {
        logger.info("定时任务开始执行..." + DateUtils.getCurrentDateTimeMillis());
    }

}
