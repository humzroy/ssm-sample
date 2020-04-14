package com.zhen.base.web.outapi;

import com.zhen.base.domain.mybatisplus.User;
import com.zhen.base.service.demo.IDemoService;
import com.zhen.base.service.mybatisplus.MybatisPlusUserService;
import com.zhen.common.master.BaseResult;
import com.zhen.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private MybatisPlusUserService mybatisPlusUserService;

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
            demoService.testException2();
        } catch (Exception e) {
            exceptionStackTrace += ExceptionUtil.getStackTrace(e);
            logger.error("监测到异常！" + ExceptionUtil.getStackTrace(e));
        }
        return exceptionStackTrace;
    }

    /**
     * 测试回滚
     * @return
     */
    @RequestMapping(value = "testRollBack", method = RequestMethod.GET)
    public String testRollBack() {
        String exceptionStackTrace = "监测到异常：\n";
        try {
            demoService.testRollBack();
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

    /**
     * @description：测试api列表--用户列表
     * @author：wuhengzhen
     * @date：2019-9-19 13:55:12
     **/
    @RequestMapping(value = "testApiList/queryUserList", method = RequestMethod.GET)
    public BaseResult testApiList() {
        // ResultEntity res = new ResultEntity();
        BaseResult res = BaseResult.createBaseResult();

        // 查询用户列表
        List<User> users = mybatisPlusUserService.findUsers();

        // res.setStatuMsg("查询成功！");
        // res.setData(users);

        logger.info("测试 查询用户列表");
        res.setMessageCode("200");
        res.setMessage("查询成功！");
        res.putValueToData("data", users);

        return res;
    }
}
