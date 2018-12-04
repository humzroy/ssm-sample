package com.zhen.base.web.mq;


import com.zhen.base.service.jms.IJmsSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : wuhengzhen
 * @Description : activemq测试
 * @date : 2018/08/07 15:40
 * @system name:
 * @copyright:
 */
@Controller
@RequestMapping("/activemq")
public class ActivemqController {
    /**
     * 获取 jmsService 对象
     */
    @Autowired
    @Qualifier("jmsSenderService")
    private IJmsSenderService jmsSenderService;

    /**
     * 发送消息到队列
     * Queue队列：仅有一个订阅者会收到消息，消息一旦被处理就不会存在队列中
     *
     * @param message
     * @return String
     */
    @ResponseBody
    @RequestMapping("queueSender")
    public String queueSender(@RequestParam("message") String message) {
        String opt = "";
        try {
            jmsSenderService.sendQueue("zhen.queue", message);
            opt = "suc";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }

    /**
     * 发送消息到主题
     * Topic主题 ：放入一个消息，所有订阅者都会收到
     * 这个是主题目的地是一对多的
     *
     * @param message
     * @return String
     */
    @ResponseBody
    @RequestMapping("topicSender")
    public String topicSender(@RequestParam("message") String message) {
        String opt = "";
        try {
            jmsSenderService.sendTopic("zhen.topic", message);
            opt = "suc";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }
}
