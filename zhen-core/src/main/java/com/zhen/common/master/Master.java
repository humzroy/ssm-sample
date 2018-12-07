package com.zhen.common.master;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：公共参数
 * Auth：wuhengzhen
 * Date：2018-12-05
 * Time：16:16
 */
public class Master implements Serializable {
    private static final long serialVersionUID = 7118157846476173650L;
    /**
     * 接口编码
     */
    private String apiCode;
    /**
     * 序列数
     */
    private String serialNumber;
    /**
     * 应用模块编码
     */
    private String applicationCode;
    /**
     * session
     */
    private String sign;

    /**
     * 远程IP
     */
    private String remoteLocalAddr;

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemoteLocalAddr() {
        return remoteLocalAddr;
    }

    public void setRemoteLocalAddr(String remoteLocalAddr) {
        this.remoteLocalAddr = remoteLocalAddr;
    }

    @Override
    public String toString() {
        return "Master{" +
                "apiCode='" + apiCode + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", applicationCode='" + applicationCode + '\'' +
                ", sign='" + sign + '\'' +
                ", remoteLocalAddr='" + remoteLocalAddr + '\'' +
                '}';
    }
}
