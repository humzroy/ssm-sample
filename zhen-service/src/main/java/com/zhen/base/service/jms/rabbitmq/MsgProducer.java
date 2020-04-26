package com.zhen.base.service.jms.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName MsgProducer
 * @Description RabbitMq 消息生产者
 * @Author wuhengzhen
 * @Date 2020-04-26 16:45
 * @Version 1.0
 */
@Component
public class MsgProducer {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MsgProducer.class);

    // 两种获取amqpTemplate方法；一种是注入；另一种是通过加载application-mq.xml文件；
    @Resource
    private AmqpTemplate amqpTemplate;


    // 第二种获取amqpTemplate方法；
    // AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-mq.xml");
    // getBean
    // AmqpTemplate amqpTemplate = ctx.getBean(AmqpTemplate.class);

    /**
     * 发送消息
     *
     * @param queueName 队列名称
     * @param content   消息体
     */
    public void sendMsg(String queueName, String content) {

        logger.info("要发送的消息 ：" + content);
        amqpTemplate.convertAndSend(queueName, "METHOD_EVENT_DIRECT_ROUTING_KEY", content);
        logger.info("消息发送成功");

    }
}
