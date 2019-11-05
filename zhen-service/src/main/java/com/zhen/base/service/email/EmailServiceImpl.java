package com.zhen.base.service.email;

import cn.hutool.core.util.ArrayUtil;
import com.zhen.utils.DateUtil;
import com.zhen.utils.PropertiesFileUtil;
import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：EmailServiceImpl
 * @Description：Email服务
 * @Author：wuhengzhen
 * @Date：2019-11-01 13:20
 */
@Component
public class EmailServiceImpl implements IEmailService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    /**
     * 发自XX邮箱，
     */
    private String from;
    @Autowired
    private JavaMailSender mailSender;


    /**
     * 发送文本邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        from = PropertiesFileUtil.getInstance().get("from");
        String from1 = PropertiesFileUtil.readPropertiesFromfiles("/email.properties", "from", "CONF_HOME");
        logger.info("from:" + from1);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        // 抄送
        if (ArrayUtil.isNotEmpty(cc)) {
            message.setCc(cc);
        }
        long start = System.currentTimeMillis();
        mailSender.send(message);
        long end = System.currentTimeMillis();
        logger.info("JavaMail 邮件发送完成，耗时：" + (end - start) + " ms");


        OhMyEmail.config(OhMyEmail.SMTP_163(false), "wu_worktest@163.com", "123456a");
        start = System.currentTimeMillis();
        try {
            OhMyEmail.subject("OhMyEmail发送方式测试邮件" + DateUtil.getCurrentDateTime()).from("wu_worktest@163.com").to("15169720@qq.com").text("这是一封来自OhMyEmail发送方式的测试邮件").send();
        } catch (SendMailException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        logger.info("OhMyEmail 邮件发送完成，耗时：" + (end - start) + " ms");
    }
}
