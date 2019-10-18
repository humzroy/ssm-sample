package com.zhen.common.master;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：ResultEntity
 * @Description：
 * @Author：wuhengzhen
 * @Date：2019-09-19 14:03
 */
public class ResultEntity implements Serializable {
    private static final long serialVersionUID = -4711162603509922401L;
    /**
     * 状态码，默认200 success
     */
    private Integer status = 200;
    /**
     * 状态消息
     */
    private String statuMsg;
    /**
     * data
     */
    private Object data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatuMsg() {
        return statuMsg;
    }

    public void setStatuMsg(String statuMsg) {
        this.statuMsg = statuMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
