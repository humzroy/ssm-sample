package com.zhen.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author : wuhengzhen
 * @Description : 消息监听
 * @date : 2018/08/07 13:11
 * @system name:
 * @copyright:
 */
public class JMSListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                String msg = txtMsg.getText();
                System.out.println("Consumer:->收到的消息: " + msg);
            } else {
                System.out.println("Consumer:->收到的消息: " + message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
