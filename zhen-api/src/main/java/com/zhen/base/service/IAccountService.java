package com.zhen.base.service;

import java.util.Map;

public interface IAccountService {
    /**
     * 用户登录
     *
     * @param loginId
     * @param password
     * @return
     */
    int accountLogin(String loginId, String password);

    /**
     * @description :创建用户
     * @author : wuhengzhen
     * @date : 2018-8-2 20:29
     */
    int createUser(Map<String, String> param);

    /**
     * @description : 更新用户
     * @author : wuhengzhen
     * @date : 2018-8-2 21:02
     */
    int updateUser(Map<String, String> param);
}
