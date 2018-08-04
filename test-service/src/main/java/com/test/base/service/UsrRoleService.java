package com.test.base.service;

import com.test.base.domain.UsrRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.base.dao.UsrRoleMapper;

/**
 * Created by kevin on 2017/6/9.
 */
@Component
public class UsrRoleService implements  IUsrRoleService{

    @Autowired
   private UsrRoleMapper usrRoleMapper;

    @Override
    public UsrRole getUsrRole(){
  
        return  usrRoleMapper.getUsrRole();		   
    }

}
