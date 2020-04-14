package com.zhen.base.service.demo;

import com.zhen.datasource.DataSource;
import com.zhen.datasource.DataSourceConstant;
import com.zhen.exception.BusinessException;

import java.util.Map;

/**
 * @Description
 * @Author wuhengzhen
 * @Date 2020-02-17 16:36
 */
public interface IDemoServiceTwo {


    @DataSource(DataSourceConstant.MYSQL)
    void testException() throws BusinessException;

    @DataSource(DataSourceConstant.MYSQL)
    void testMybatis();

    boolean testReturn();

    /**
     * 测试回滚
     * @return
     */
    @DataSource(DataSourceConstant.MYSQL)
    Map<String, Object> testRollBack();
}
