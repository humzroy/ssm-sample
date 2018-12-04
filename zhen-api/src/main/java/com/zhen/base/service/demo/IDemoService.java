package com.zhen.base.service.demo;

import com.zhen.base.domain.system.RequestLog;

/**
 * demo interface
 */
public interface IDemoService {
    /**
     * say hello
     * @param name
     * @return
     */
    String sayHello(String name);

    void insertUpmsLogSelective(RequestLog reqlog);
}
