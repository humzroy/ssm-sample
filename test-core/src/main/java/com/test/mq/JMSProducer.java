package com.test.mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wuhengzhen on 2018/8/2.
 * 消息生产者
 */
public class JMSProducer {
    /**
     * 默认连接用户名
     */
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    /**
     * 默认连接密码
     */
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    /**
     * 默认连接地址
     */
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    /**
     * 连接
     */
    private Connection connection = null;
    /**
     * 会话 接受或者发送消息的线程
     */
    private Session session = null;
    /**
     * 消息生产者
     */
    private MessageProducer messageProducer = null;

    /**
     * 发送的消息数量
     */
    private static final int SENDNUM = 10;

    /**
     * 初始化
     *
     * @throws JMSException
     * @throws Exception
     */
    private void initialize() throws JMSException, Exception {
        // 连接工厂
        ConnectionFactory connectionFactory;
        // 消息目的地
        Destination destination;
        // 实例化连接工厂
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("hello");
            messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param message
     * @throws Exception
     */
    public void sendMessage(String message) throws Exception {
        initialize();
        TextMessage msg = session.createTextMessage(message);
        connection.start();
        System.out.println("Producer:->Sending message: " + message);
        messageProducer.send(msg);
        System.out.println("Producer:->Message sent complete!");
    }

    /**
     * 关闭连接
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        System.out.println("Producer:->Closing connection");
        if (messageProducer != null) {
            messageProducer.close();
        }
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
