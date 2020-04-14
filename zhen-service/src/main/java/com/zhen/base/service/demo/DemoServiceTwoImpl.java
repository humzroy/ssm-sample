package com.zhen.base.service.demo;

import com.zhen.base.dao.system.SignInfoMapper;
import com.zhen.base.dao.system.UserMapper;
import com.zhen.base.domain.system.SignInfo;
import com.zhen.base.domain.system.User;
import com.zhen.exception.BusinessException;
import com.zhen.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DemoServiceTwoImpl
 * @Description
 * @Author wuhengzhen
 * @Date 2020-02-17 16:36
 * @Version 1.0
 */
@Component
public class DemoServiceTwoImpl implements IDemoServiceTwo {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceTwoImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SignInfoMapper signInfoMapper;


    @Override
    public void testException() throws BusinessException {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new BusinessException("DemoServiceTwoImpl.testException()");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
        logger.info("DemoServiceTwoImpl.testMybatis() success!!");
    }

    @Override
    public boolean testReturn() {
        return false;
    }

    /**
     * 测试回滚
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Object> testRollBack() {
        Map<String, Object> resMap = new HashMap<>(16);

        SignInfo signInfo = new SignInfo();
        signInfo.setStauts("Y");
        signInfo.setCreateBy("admin");
        signInfo.setCreateDt(DateUtil.getCurrentDateTime());


        // ThreadTest apiLogThread = new ThreadTest(signInfo);
        // threadPoolTaskExecutor.submit(apiLogThread);

        signInfoMapper.insertSelective(signInfo);

        logger.info("testRollBack signInfo insert success ！");
        resMap.put("success", false);
        resMap.put("message", "失败！");

        return resMap;
    }
}
