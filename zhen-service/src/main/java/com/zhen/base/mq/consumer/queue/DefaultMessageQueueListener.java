package com.zhen.base.mq.consumer.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：MQ消费者
 * Author：wuhengzhen
 * Date：2018-12-04
 * Time：15:49
 */
public class DefaultMessageQueueListener implements MessageListener {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageQueueListener.class);
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void onMessage(Message message) {
        // 使用线程池多线程处理
        threadPoolTaskExecutor.execute(() -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                logger.info("消费：{}", text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
