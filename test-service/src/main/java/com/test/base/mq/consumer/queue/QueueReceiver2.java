package com.test.base.mq.consumer.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author : wuhengzhen
 * @Description : 队列消息监听器
 * @date : 2018/08/06 14:59
 * @system name:
 * @copyright:
 */
public class QueueReceiver2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("QueueReceiver2接收到消息:" + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
