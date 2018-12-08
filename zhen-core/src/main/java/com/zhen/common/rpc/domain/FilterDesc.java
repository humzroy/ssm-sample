package com.zhen.common.rpc.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：请求描述类
 * Auth：wuhengzhen
 * Date：2018-12-08
 * Time：10:02
 */
public class FilterDesc implements Serializable {
    /**
     * 接口名
     */
    private String interfaceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private Object[] args;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
