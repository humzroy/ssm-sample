package com.zhen.base.dao.hm;

import com.zhen.base.domain.User;

import java.util.Map;

public interface HmUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * @description : 用户登录校验
     * @author : wuhengzhen
     * @date : 2018-8-2 19:50
     */
    User accountLogin(Map<String, String> param);

}