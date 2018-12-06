package com.zhen.base.service.demo;

import com.zhen.base.domain.system.RequestLog;
import com.zhen.common.master.BaseRequest;

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
}
