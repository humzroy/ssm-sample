package com.zhen.base.web.email;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhen.base.service.email.IEmailService;
import com.zhen.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * @description：测试邮件发送 JSON 实例：
     * {
     *   "to": "15169720@qq.com",
     *   "subject": "测试邮件发送",
     *   "content": "你好，这是一封测试邮件。",
     *   "cc": []
     * }
     * @author：wuhengzhen
     * @date：2019/11/4 10:54
     **/
    @RequestMapping(value = "sendSimpleMail", method = RequestMethod.POST)
    public String sendSimpleMail(HttpServletRequest request, @RequestBody String jsonStr) {
        JSONObject reqJson = JSON.parseObject(jsonStr);
        String to = reqJson.getString("to");
        String subject = reqJson.getString("subject");
        String content = reqJson.getString("content");
        JSONArray jsonArray = reqJson.getJSONArray("cc");
        String[] cc = JsonUtil.getJsonToStringArray(jsonArray.toJSONString());
        logger.info("接收到发送email信息：收件人邮箱：{}，邮件主题：{}，邮件内容：{}", to, subject, content);
        String res = "success";
        emailService.sendSimpleMail(to, subject, content, cc);
        return res;
    }
}
