package com.test.base.service.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
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
    private JmsTemplate jmsQueueTemplate;

    @Autowired
    private JmsTemplate jmsTopicTemplate;

    /**
     * 一对一消息方法
     *
     * @param destination 消息目的地
     * @param message     消息内容
     */
    @Override
    public void sendQueue(Destination destination, String message) {
        jmsQueueTemplate.send(destination, session -> session.createTextMessage(message));
    }

    /**
     * 发布、订阅消息发送
     *
     * @param destination 消息目的地
     * @param message     消息内容
     */
    @Override
    public void sendTopic(Destination destination, String message) {
        jmsTopicTemplate.send(destination, session -> session.createTextMessage(message));
    }

    /**
     * 一对一消息发送
     *
     * @param name    队列名称
     * @param message 消息内容
     */
    @Override
    public void sendQueue(String name, String message) {
        jmsQueueTemplate.send(name, session -> session.createTextMessage(message));
    }

    /**
     * 发布、订阅消息发送
     *
     * @param name    队列名称
     * @param message 消息内容
     */
    @Override
    public void sendTopic(String name, String message) {
        jmsTopicTemplate.send(name, session -> session.createTextMessage(message));
    }
}
