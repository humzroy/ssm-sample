package com.zhen.base.rabbitmq.consumer.queue;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName RmqMsgConsumer
 * @Description RabbitMq 消息消费者
 * @Author wuhengzhen
 * @Date 2020-04-26 16:48
 * @Version 1.0
 */
public class RmqMsgConsumer implements ChannelAwareMessageListener {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RmqMsgConsumer.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String context = "";
        context = new String(message.getBody(), StandardCharsets.UTF_8);
        logger.info("message:" + message);
        logger.info("接收处理当前监听队列当中的消息： " + context + "\n 当前线程name:" + Thread.currentThread().getName() + "\n 当前线程id:"
                + Thread.currentThread().getId());


        // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        // nack返回false，并重新回到队列，就是重发机制，通常存在于消费失败后处理中；
        //第三个参数与拒绝消息方法的第二个参数同理。即true重新进入队列，false则丢弃；
//		channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        // 拒绝消息,即丢弃消息，消息不会重新回到队列,后面的参数为true则重入队列；为false则丢弃；
//		channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
}
