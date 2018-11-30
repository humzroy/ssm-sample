package com.zhen.base.mq.consumer.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author : wuhengzhen
 * @Description : Topic消息监听器
 * @date : 2018/08/06 15:01
 * @system name:
 * @copyright:
 */
public class TopicReceiver2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("TopicReceiver2接收到消息:" + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
