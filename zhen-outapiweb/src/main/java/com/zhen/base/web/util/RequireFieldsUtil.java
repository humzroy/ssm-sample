package com.zhen.base.web.util;

import com.zhen.base.domain.require.Customer;
import com.zhen.requirerule.MyRequiredRule;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName RequireFieldsUtil
 * @Description 必填项校验
 * @Author wuhengzhen
 * @Date 2020-04-14 17:43
 * @Version 1.0
 */
public class RequireFieldsUtil {

    /**
     *
     * @param customer
     * @throws Exception
     */
    public static void checkCusField(Customer customer) throws Exception {
        if (customer != null) {
            Field[] fields = customer.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MyRequiredRule.class)) {
                    String strV = getFieldVaule(customer, field);
                    if (strV == null || strV.trim().length() <= 0) {
                        MyRequiredRule myRequiredRule = field.getAnnotation(MyRequiredRule.class);
                        throw new Exception("校验失败，字段【" + field.getName() + "】：" + myRequiredRule.exceptionMsg() + "，当前值：" + strV);
                    }
                }
                // if (field.isAnnotationPresent(PxxFixLengthRule.class)) {
                //     String strV = getFieldVaule(o, field);
                //     PxxFixLengthRule pxxFixLengthRule = field.getAnnotation(PxxFixLengthRule.class);
                //     if (strV == null || strV.trim().length() != pxxFixLengthRule.fixLength()) {
                //         throw new Exception("校验失败，字段【" + field.getName() + "】：" + pxxFixLengthRule.exceptionMsg() + "，当前值：" + strV);
                //     }
                // }
                // if (field.isAnnotationPresent(PxxMustNumber.class)) {
                //     String strV = getFieldVaule(o, field);
                //     PxxMustNumber pxxMustNumber = field.getAnnotation(PxxMustNumber.class);
                //     try {
                //         Double d = Double.valueOf(strV);
                //     } catch (Exception e) {
                //         throw new Exception("校验失败，字段【" + field.getName() + "】：" + pxxMustNumber.exceptionMsg() + "，当前值：" + strV);
                //     }
                // }
            }
        }
    }

    /**
     * 获取 字段对应的值
     *
     * @param o
     * @param field
     */
    public static String getFieldVaule(Customer customer, Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        try {
            String fieldMethoeName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Method fieldMethoe = customer.getClass().getMethod(fieldMethoeName, null);
            return (String) fieldMethoe.invoke(customer, null);
        } catch (Exception e) {
            return null;
        }

    }


}
