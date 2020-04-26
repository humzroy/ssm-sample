package com.zhen.base.service.jms;

import com.zhen.base.service.jms.activemq.MQProducer;
import com.zhen.base.service.jms.rabbitmq.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @author wuhengzhen
 * @Description：Jms消息（生产者）发送服务接口实现类
 * @date：2018-09-17 15:11
 */
@Component
public class JmsSenderServiceImpl implements IJmsSenderService {
    @Autowired
    private MQProducer producer;
    @Autowired
    private MsgProducer msgProducer;

    /**
     * 一对一消息方法
     *
     * @param destination 消息目的地
     * @param message     消息内容
     */
    @Override
    public void sendQueue(Destination destination, String message) {
        producer.sendQueue(destination, message);
    }

    /**
     * 发布、订阅消息发送
     *
     * @param destination 消息目的地
     * @param message     消息内容
     */
    @Override
    public void sendTopic(Destination destination, String message) {
        producer.sendTopic(destination, message);
    }

    /**
     * 一对一消息发送
     *
     * @param name    队列名称
     * @param message 消息内容
     */
    @Override
    public void sendQueue(String name, String message) {
        producer.sendQueue(name, message);
    }

    /**
     * 发布、订阅消息发送
     *
     * @param name    队列名称
     * @param message 消息内容
     */
    @Override
    public void sendTopic(String name, String message) {
        producer.sendTopic(name, message);
    }

    /**
     * 发送rabbit mq 消息
     *
     * @param name
     * @param message
     */
    @Override
    public void sendRabbitMqMsg(String name, String message) {
        msgProducer.sendMsg(name, message);
    }
}
