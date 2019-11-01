package com.zhen.base.service.email;

/**
 * Created with IntelliJ IDEA
 *
 * @Description：Email服务
 * @Author：wuhengzhen
 * @Date：2019-11-01 13:17
 */
public interface IEmailService {

    /**
     * 发送文本邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     */
    void sendSimpleMail(String to, String subject, String content, String... cc);
}
