package com.zhen.base.service.demo;

import com.zhen.base.dao.system.UserMapper;
import com.zhen.base.domain.system.RequestLog;
import com.zhen.base.domain.system.User;
import com.zhen.common.master.BaseRequest;
import com.zhen.common.master.Master;
import com.zhen.datasource.DataSource;
import com.zhen.datasource.DataSourceConstant;
import com.zhen.exception.BusinessException;
import com.zhen.util.DateUtil;
import com.zhen.util.shiro.ShiroUser;
import com.zhen.util.shiro.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author wuhengzhen
 * @Description：
 * @date：2018-09-17 17:27
 */
@Component

public class DemoServiceImpl implements IDemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IDemoServiceTwo demoServiceTwo;

    /**
     * say hello
     *
     * @param request
     * @return
     */
    @Override
    public String sayHello(BaseRequest request) {
        String str;
        /*JSONObject reqData = new JSONObject();
        JSONObject msg = new JSONObject();
        msg.put("name", name);
        reqData.put("data", msg);
        reqData.put("msgNo", "1001");
        reqData.put("reqTime", DateUtil.formatDateTime(new Date()));
        String url = "http://10.10.10.93:9696/test-outapiweb/outapi/testApi";
        String resp = HttpsUtil.doPostJson(url, reqData.toJSONString());

        logger.info("service 返回消息为：" + resp);
        return resp;*/
        str = " hello !!";
        // 获取登陆信息
        Master master = request.getValueFormData("master");
        ShiroUser user = ShiroUtil.getSessionInfo(master);
        logger.info(user.toString());
        String userCde = user.getUserCde();
        System.out.println(userCde + str);
        return userCde + str;
    }

    @Override
    public void insertUpmsLogSelective(RequestLog reqlog) {
        logger.info("请求日志入库...");
    }

    /**
     * description :测试异常！
     * author : wuhengzhen
     * date : 2019/5/16 15:28
     */
    @Override
    @DataSource(DataSourceConstant.MYSQL)
    @Transactional(rollbackFor = BusinessException.class)
    public void testException() throws BusinessException {
        logger.info("测试异常接口！" + DateUtil.getCurrentDateTimeMillis());

        //User user = new User();
        //user.setLoginName("test");
        //user.setPassword("123456");
        //user.setMobile("18353238798");
        //user.setEmail("wuhengzhen.qd@gmail.com");
        //user.setUserName("测试");
        //user.setCreateTime(DateUtil.getCurrentDateTime());
        //user.setCreateUser("system");
        //userMapper.insertSelective(user);
        //logger.info("insert！！！");
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();

            throw new BusinessException("dddddddddddd");
        }
        System.out.println("sssssssssss");

    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void testException2() {
        testMybatis();
        boolean flag = demoServiceTwo.testReturn();
        if (!flag) {
            throw new BusinessException("demoServiceTwo.testReturn error!!");
        }

    }

    @Override
    public void testInterface() {
        System.out.println("testInterface");
        testInterface1();
    }

    @Override
    public void testInterface1() {

        System.out.println("testInterface1");
    }

    @Override
    @DataSource(DataSourceConstant.MYSQL)
    public void testMybatis() {
        User user = new User();
        user.setLoginName("test");
        user.setPassword("123456");
        user.setMobile("18353238798");
        user.setEmail("wuhengzhen.qd@gmail.com");
        user.setUserName("测试");
        user.setCreateTime(DateUtil.getCurrentDateTime());
        user.setCreateUser("system");
        userMapper.insertSelective(user);
        logger.info("sssssssssssssssss");
    }

    /**
     * 测试回滚
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class,propagation = Propagation.REQUIRED)
    public void testRollBack() {


            demoServiceTwo.testMybatis();

            Map<String,Object> resMap = demoServiceTwo.testRollBack();
            boolean success = (boolean) resMap.get("success");
            if (success) {

            } else {
                logger.info("签订失败！！！");
                throw new BusinessException("异常要回滚！！");
                // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }




    }


}
