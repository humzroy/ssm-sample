package com.zhen.base.web.mq;

import com.zhen.base.service.jms.IJmsSenderService;
import com.zhen.base.service.jms.JmsSenderServiceImpl;
import com.zhen.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName RabbitmqController
 * @Description RabbitMq Controller
 * @Author wuhengzhen
 * @Date 2020-04-26 16:56
 * @Version 1.0
 */
@Controller
@RequestMapping("/rabbitmq")
public class RabbitmqController {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqController.class);

    @Autowired
    @Qualifier("jmsSenderService")
    private IJmsSenderService jmsSenderService;

    /**
     * 发送队列
     *
     * @param message
     * @return
     */
    @ResponseBody
    @RequestMapping("sendQueue")
    public String sendRabbitmqQueue(@RequestParam("message") String message) {
        String code = "0000";

        logger.info("Rabbit MQ 发送消息：{}", message);
        try {
            jmsSenderService.sendRabbitMqMsg("METHOD_EVENT_DIRECT_EXCHANGE", message);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            code = "9999";
        }

        return code;
    }

}
