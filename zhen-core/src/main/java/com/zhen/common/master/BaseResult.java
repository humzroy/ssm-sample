package com.zhen.common.master;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：封装返回
 * Auth：wuhengzhen
 * Date：2018-12-05
 * Time：16:50
 */
public class BaseResult implements Serializable {
    /**
     * 私有的构造方法，防止new
     */
    private BaseResult() {
    }

    private static final long serialVersionUID = 8194010942134363408L;
    /**
     * 成功失败标志，默认true
     */
    private boolean success = true;
    /**
     * 消息码
     */
    private String messageCode = "";
    /**
     * 消息描述
     */
    private String message = "";
    /**
     * 用于存放数据的MAP
     */
    private Map<Object, Object> data = new HashMap<>();

    public static BaseResult createBaseResult() {
        return new BaseResult();
    }

    /**
     * 封装data
     *
     * @param key
     * @param value
     */
    public void putValueToData(Object key, Object value) {
        this.getData().put(key, value);
    }

    /**
     * 根据key获取data值
     *
     * @param key
     * @return
     */
    public Object getValueFormData(Object key) {
        return this.getData().get(key);
    }


    public Map<Object, Object> getData() {
        return data;
    }

    public void setData(Map<Object, Object> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
