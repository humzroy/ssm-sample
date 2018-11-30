package com.zhen.mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author : wuhengzhen
 * @Description : 消息消费者
 * @date : 2018/08/07 13:13
 * @system name:
 * @copyright:
 */
public class JMSListenerConsumer {
    /**
     * 默认的连接用户名
     */
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    /**
     * 默认的连接密码
     */
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    /**
     * 默认的连接地址
     */
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    /**
     * 连接
     */
    private Connection connection = null;
    /**
     * 消息的消费者
     */
    private MessageConsumer messageConsumer;
    /**
     * 会话 接受或者发送消息的线程
     */
    private Session session;

    /**
     * 初始化
     *
     * @throws JMSException
     * @throws Exception
     */
    private void initialize() throws JMSException, Exception {
        // 连接工厂
        ConnectionFactory connectionFactory;

        // 消息的目的地
        Destination destination;

        try {
            // 实例化连接工厂,连接工厂是用户创建连接的对象,这里使用的是ActiveMQ的ActiveMQConnectionFactory根据url,username和password创建连接工厂。 .
            connectionFactory = new ActiveMQConnectionFactory(JMSListenerConsumer.USERNAME, JMSListenerConsumer.PASSWORD, JMSListenerConsumer.BROKEURL);
            //连接工厂创建一个jms connection
            connection = connectionFactory.createConnection();
            // 创建Session,是生产和消费的一个单线程上下文.会话用于创建消息的生产者,消费者和消息.会话提供了一个事务性的上下文.
            //不支持事务
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 创建连接的消息队列,目的地是客户用来指定他生产消息的目标还有他消费消息的来源的对象,两种消息传递方式：点对点和发布/订阅
            destination = session.createQueue("hello");
            // 创建消息消费者,会话创建消息的生产者将消息发送到目的地
            messageConsumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费消息
     *
     * @throws JMSException
     * @throws Exception
     */
    public void consumeMessage() throws JMSException, Exception {
        initialize();
        connection.start();

        System.out.println("Consumer:->Begin listening...");
        // 开始监听
        messageConsumer.setMessageListener(new JMSListener());
    }

    /**
     * 关闭连接
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        System.out.println("Consumer:->Closing connection");
        if (messageConsumer != null) {
            messageConsumer.close();
        }
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

}
