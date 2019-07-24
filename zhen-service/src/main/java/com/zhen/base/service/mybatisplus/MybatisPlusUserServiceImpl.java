package com.zhen.base.service.mybatisplus;

import com.zhen.base.dao.mybatisplus.MybatisPlusUserMapper;
import com.zhen.base.domain.mybatisplus.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：UserServiceImpl
 * @Description：mybatis plus - service impl
 * @Author：wuhengzhen
 * @Date：2019-07-24 16:57
 */
@Component
@Transactional
public class MybatisPlusUserServiceImpl implements MybatisPlusUserService {

    @Autowired
    private MybatisPlusUserMapper mybatisPlusUserMapper;

    /**
     * 1.查询所有的用户
     *
     * @return
     */
    @Override
    public List<User> findUsers() {
        return mybatisPlusUserMapper.findUsers();
    }

    /**
     * 2.新增用户
     *
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
        return mybatisPlusUserMapper.addUser(user);
    }
}
