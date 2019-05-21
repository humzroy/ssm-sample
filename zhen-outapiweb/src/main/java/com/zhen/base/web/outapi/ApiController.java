package com.zhen.base.web.outapi;

import com.zhen.base.service.demo.IDemoService;
import com.zhen.exception.BusinessException;
import com.zhen.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuhengzhen
 * @Description：对外API接口服务
 * @date：2018-09-17 17:17
 */
@RestController
@RequestMapping("/outapi")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    @Qualifier("demoService")
    private IDemoService demoService;

    /**
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "testApi", method = RequestMethod.POST)
    public String outApi(@RequestBody String data) {
        logger.info("接收到数据data:" + data);
        return "Hello World!";
    }

    /**
     * helloWorld
     *
     * @return
     */
    @RequestMapping(value = "helloWorld")
    public String testApi() {
        return "Hello World!";
    }

    /**
     * description :测试异常！
     * author : wuhengzhen
     * date : 2019/5/16 15:30
     */
    @RequestMapping(value = "testException", method = RequestMethod.GET)
    public String testException() {
        String exceptionStackTrace = "监测到异常：\n";
        try {
            demoService.testException();
        } catch (Exception e) {
            exceptionStackTrace += ExceptionUtil.getStackTrace(e);
            logger.error("监测到异常！" + ExceptionUtil.getStackTrace(e));
        }
        return exceptionStackTrace;
    }

    /**
     * @description：测试接口方法
     * @author：wuhengzhen
     * @date：2019/5/21 19:00
     * @param：[]
     * @return：java.lang.String
     **/
    @RequestMapping(value = "testInterface", method = RequestMethod.GET)
    public String testInterface() {
        demoService.testInterface();
        return "0000";
    }
}
