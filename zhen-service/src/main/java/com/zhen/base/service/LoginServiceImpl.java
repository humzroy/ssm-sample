package com.zhen.base.service;

import com.zhen.base.dao.system.UserMapper;
import com.zhen.base.domain.system.User;
import com.zhen.datasource.DataSourceConstant;
import com.zhen.datasource.DataSourceContextHolder;
import com.zhen.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : wuhengzhen
 * @Description :
 * @date : 2018/08/02 19:00
 * @copyright:长安新生（深圳）金融投资有限公司
 */
@Component
public class LoginServiceImpl implements ILoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @param loginId
     * @param password
     * @return
     */
    @Override
    public int login(String loginId, String password) {
        return 0;
    }

    /**
     * description :根据用户名查询用户信息
     * author : wuhengzhen
     * date : 2018-12-4 10:24
     *
     * @param userName 用户名
     */
    @Override
    public User selectUserByUsername(String userName) {
        // 切换数据库
        DataSourceContextHolder.setDataSourceType(DataSourceConstant.MYSQL);
        User user = userMapper.selectUserByUsername(userName);
        if (user != null) {
            logger.info(userName + "用户信息查询成功！");
        } else {
            logger.info(userName + "用户信息查询为空！");
            throw new BusinessException("用户信息查询为空！");
        }
        return user;
    }
}
