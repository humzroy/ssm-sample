package com.test.mq;

import org.apache.activemq.ActiveMQConnection;

import javax.jms.JMSException;

/**
 * @author : wuhengzhen
 * @Description : activeMQ测试
 * @date : 2018/08/07 15:28
 * @system name:
 * @copyright:
 */
public class TestMQ {
    /**
     * @param args
     */
    public static void main(String[] args) throws JMSException, Exception {

        JMSListenerConsumer consumer = new JMSListenerConsumer();
        JMSProducer producer = new JMSProducer();
        System.out.println(ActiveMQConnection.DEFAULT_BROKER_URL + "------------");
        // 开始监听
        consumer.consumeMessage();

        // 延时500毫秒之后发送消息
        Thread.sleep(500);
        producer.sendMessage("Hello, world!");
        producer.close();

        // 延时500毫秒之后停止接受消息
        Thread.sleep(500);
        consumer.close();
    }
}
