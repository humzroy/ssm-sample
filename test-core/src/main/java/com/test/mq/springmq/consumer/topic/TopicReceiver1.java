package com.test.mq.springmq.consumer.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author : wuhengzhen
 * @Description : Topic消息监听器
 * @date : 2018/08/06 15:00
 * @system name:
 * @copyright:
 */
public class TopicReceiver1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("TopicReceiver1接收到消息:" + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
