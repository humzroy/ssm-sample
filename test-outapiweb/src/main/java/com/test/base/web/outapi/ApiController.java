package com.test.base.web.outapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wuhengzhen
 * @Description：对外API接口服务
 * @date：2018-09-17 17:17
 */
@Controller
@RequestMapping("/outapi")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    /**
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "testApi", method = RequestMethod.POST)
    public String outApi(@RequestBody String data) {
        logger.info("接收到数据data:" + data);
        String resp = "Hello World!";
        return resp;
    }
}
