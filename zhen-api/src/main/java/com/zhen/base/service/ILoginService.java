package com.zhen.base.service;

import com.zhen.base.domain.system.User;

public interface ILoginService {
    /**
     * 用户登录
     *
     * @param loginId
     * @param password
     * @return
     */
    int login(String loginId, String password);

    /**
     * description :根据用户名查询用户信息
     * author : wuhengzhen
     * date : 2018-12-4 10:24
     */
    User selectUserByUsername(String userName);
}
