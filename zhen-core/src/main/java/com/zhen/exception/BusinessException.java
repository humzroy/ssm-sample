package com.zhen.exception;


import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -5433056591268198786L;

    /**
     * 错误信息Map
     */
    private Map<String, String> errorMessages = new HashMap<>();

    public BusinessException() {
    }

    /**
     * 构造异常
     *
     * @param message 信息描述
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 构造异常
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public void addErrorMessage(String fieldName, String message) {
        this.errorMessages.put(fieldName, message);
    }

    public void addBindingResultTo(BindingResult result) {
        for (String key : this.errorMessages.keySet()) {
            result.rejectValue(key, "", (String) this.errorMessages.get(key));
        }
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
