package com.zhen.base.web.email;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhen.base.service.email.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：EmailController
 * @Description：邮件处理服务
 * @Author：wuhengzhen
 * @Date：2019-11-01 13:16
 */
@RestController
@RequestMapping("/email")
public class EmailController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private IEmailService emailService;

    @RequestMapping(value = "sendSimpleMail", method = RequestMethod.POST)
    public String sendSimpleMail(@RequestBody String jsonStr) {
        JSONObject reqJson = JSON.parseObject(jsonStr);
        String to = reqJson.getString("to");
        String subject = reqJson.getString("subject");
        String content = reqJson.getString("content");
        String cc = reqJson.getString("cc");
        String res = "success";
        emailService.sendSimpleMail(to, subject, content, cc);
        return res;

    }
}
