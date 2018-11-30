package com.zhen.base.service.jms;

import javax.jms.Destination;

/**
 * @author wuhengzhen
 * @Description：Jms消息（生产者）发送服务接口
 * @date：2018-09-17 15:11
 */
public interface IJmsSenderService {
    /**
     * 一对一消息方法
     *
     * @param destination 消息目的地
     * @param message     消息内容
     */
    void sendQueue(Destination destination, final String message);

    /**
     * 发布、订阅消息发送
     *
     * @param destination 消息目的地
     * @param message     消息内容
     */
    void sendTopic(Destination destination, final String message);


    /**
     * 一对一消息发送
     *
     * @param name    队列名称
     * @param message 消息内容
     */
    void sendQueue(String name, final String message);

    /**
     * 发布、订阅消息发送
     *
     * @param name    队列名称
     * @param message 消息内容
     */
    void sendTopic(String name, final String message);
}
