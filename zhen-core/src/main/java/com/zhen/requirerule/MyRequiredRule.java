package com.zhen.requirerule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wuhengzhen
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRequiredRule {

    /**
     * 必填项
     * true 为必填项
     *
     * @return
     */
    boolean required() default false;

    /**
     * 具体异常信息
     *
     * @return
     */
    String exceptionMsg() default "异常信息";
}
