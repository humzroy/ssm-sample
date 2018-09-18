package com.test.base.web.demo;

import com.test.base.service.demo.IDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuhengzhen
 * @Description：
 * @date：2018-09-17 17:24
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    @Qualifier("demoService")
    private IDemoService demoService;

    /**
     * 调用服务端 dubbo 测试 helloWorld 方法
     *
     * @param request
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request) {
        String name = request.getParameter("name");
        logger.info("请求的参数name = " + name);
        String msg = demoService.sayHello(name);
        return msg;
    }

}
