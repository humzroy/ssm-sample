package com.zhen.base.service.demo;

import com.zhen.base.domain.system.RequestLog;
import com.zhen.common.master.BaseRequest;
import com.zhen.datasource.DataSource;
import com.zhen.datasource.DataSourceConstant;
import com.zhen.exception.BusinessException;

/**
 * demo interface
 */
public interface IDemoService {
    /**
     * say hello
     *
     * @param request
     * @return
     */
    String sayHello(BaseRequest request);

    void insertUpmsLogSelective(RequestLog reqlog);

    /**
     * description :测试异常！
     * author : wuhengzhen
     * date : 2019/5/16 15:28
     */
    @DataSource(DataSourceConstant.MYSQL)
    void testException() throws BusinessException;
}
