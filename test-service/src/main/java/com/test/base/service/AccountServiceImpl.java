package com.test.base.service;

import com.test.utils.DateUtils;
import com.test.base.dao.hm.UserMapper;
import com.test.base.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuhengzhen
 * @Description :
 * @date : 2018/08/02 19:00
 * @copyright:长安新生（深圳）金融投资有限公司
 */
@Component
public class AccountServiceImpl implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
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
    public int accountLogin(String loginId, String password) {
        int record = 0;
        Map<String, String> param = new HashMap<>(2);
        param.put("loginId", loginId);
        param.put("password", password);
        User user = null;
        try {
            user = userMapper.accountLogin(param);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户查询失败！" + e.getMessage());
        }
        if (user != null) {
            record = 1;
        }
        return record;
    }

    /**
     * @param param
     * @description :创建用户
     * @author : wuhengzhen
     * @date : 2018-8-2 20:29
     */
    @Override
    public int createUser(Map<String, String> param) {
        User user = new User();
        user.setUsername(param.get("username"));
        user.setLoginid(param.get("loginid"));
        user.setPassword(param.get("password"));
        user.setMobile(param.get("mobile"));
        user.setEmail(param.get("email"));
        user.setAvatar(param.get("avatar"));
        user.setCreateTime(DateUtils.getCurrentDate());
        user.setLastUpdateTime(null);
        user.setLastLoginTime(null);
        return userMapper.insertSelective(user);
    }

    /**
     * @param param
     * @description : 更新用户
     * @author : wuhengzhen
     * @date : 2018-8-2 21:02
     */
    @Override
    public int updateUser(Map<String, String> param) {
        User user = new User();
        user.setUsername(param.get("username"));
        user.setLoginid(param.get("loginid"));
        user.setPassword(param.get("password"));
        user.setMobile(param.get("mobile"));
        user.setEmail(param.get("email"));
        user.setAvatar(param.get("avatar"));
        user.setLastUpdateTime(DateUtils.getCurrentDate());
        String lastLoginTime = param.get("lastLoginTime");
        if (StringUtils.isNotEmpty(lastLoginTime)) {
            user.setLastLoginTime(DateUtils.getDateByStr(lastLoginTime));
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

}
