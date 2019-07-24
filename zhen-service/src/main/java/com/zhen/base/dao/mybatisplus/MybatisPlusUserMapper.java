package com.zhen.base.dao.mybatisplus;

import com.zhen.base.domain.mybatisplus.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Description：mybatis plus - mapper
 * @Author：wuhengzhen
 * @Date：2019-07-24 16:52
 */
public interface MybatisPlusUserMapper {
    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> findUsers();

    /**
     * 新增用户
     *
     * @param user 用户model
     * @return 影响行数
     */
    int addUser(User user);
}
