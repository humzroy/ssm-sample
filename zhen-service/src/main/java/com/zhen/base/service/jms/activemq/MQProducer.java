package com.zhen.base.service.jms.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：MQ消息生产者
 * Author：wuhengzhen
 * Date：2018-12-05
 * Time：14:00
 */
@Component
public class MQProducer {
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
    public void sendQueue(Destination destination, String message) {
        jmsQueueTemplate.send(destination, session -> session.createTextMessage(message));
    }

    /**
     * 发布、订阅消息发送
     *
     * @param destination 消息目的地
     * @param message     消息内容
     */

    public void sendTopic(Destination destination, String message) {
        jmsTopicTemplate.send(destination, session -> session.createTextMessage(message));
    }

    /**
     * 一对一消息发送
     *
     * @param name    队列名称
     * @param message 消息内容
     */
    public void sendQueue(String name, String message) {
        jmsQueueTemplate.send(name, session -> session.createTextMessage(message));
    }

    /**
     * 发布、订阅消息发送
     *
     * @param name    队列名称
     * @param message 消息内容
     */
    public void sendTopic(String name, String message) {
        jmsTopicTemplate.send(name, session -> session.createTextMessage(message));
    }
}
