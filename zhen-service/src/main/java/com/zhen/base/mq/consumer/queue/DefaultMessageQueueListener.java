package com.zhen.base.mq.consumer.queue;

import com.zhen.exception.BusinessException;
import com.zhen.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：MQ消费者
 * Author：wuhengzhen
 * Date：2018-12-04
 * Time：15:49
 */
public class DefaultMessageQueueListener implements SessionAwareMessageListener<Message> {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageQueueListener.class);
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // @Override
    // public void onMessage(Message message) {
    //     // 使用线程池多线程处理
    //     threadPoolTaskExecutor.execute(() -> {
    //         TextMessage textMessage = (TextMessage) message;
    //         try {
    //             String text = textMessage.getText();
    //             logger.info("消费者接受到消息：{}", text);
    //             logger.info("当前处理时间：{}", DateUtil.getCurrentDateTimeMillis());
    //
    //             if ("我是队列消息002".equals(text)) {
    //                 throw new BusinessException("故意抛出的异常！");
    //             }
    //             logger.info("消费：{}", text);
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             throw new RuntimeException(e.getMessage());
    //         }
    //     });
    // }

    @Override
    public void onMessage(Message message, Session session) throws JMSException {

        if (message instanceof TextMessage) {

            String msg = ((TextMessage) message).getText();

            System.out.println("============================================================");
            System.out.println("消费者收到的消息：" + msg);
            System.out.println("============================================================");

            try {
                if ("我是队列消息002".equals(msg)) {
                    throw new RuntimeException("故意抛出的异常");
                }
                // 只要被确认后  就会出队，接受失败没有确认成功，会在原队列里面
                message.acknowledge();
            } catch (Exception e) {
                // 此不可省略 重发信息使用
                session.recover();
            }
        }
    }
}
