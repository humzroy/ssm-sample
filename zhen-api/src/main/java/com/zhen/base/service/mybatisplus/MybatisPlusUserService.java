package com.zhen.base.service.mybatisplus;

import com.zhen.base.domain.mybatisplus.User;
import com.zhen.datasource.DataSource;
import com.zhen.datasource.DataSourceConstant;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Description：mybatis plus - service interface
 * @Author：wuhengzhen
 * @Date：2019-07-24 16:56
 */
@DataSource(DataSourceConstant.MYSQL)
public interface MybatisPlusUserService {
    /**
     * 1.查询所有的用户
     *
     * @return
     */
    List<User> findUsers();

    /**
     * 2.新增用户
     *
     * @param user
     * @return
     */
    int addUser(User user);
}
