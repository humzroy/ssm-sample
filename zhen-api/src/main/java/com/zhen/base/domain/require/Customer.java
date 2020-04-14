package com.zhen.base.domain.require;

import com.zhen.requirerule.MyRequiredRule;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName Customer
 * @Description 客户类
 * @Author wuhengzhen
 * @Date 2020-04-14 17:31
 * @Version 1.0
 */
@ToString
@Data
public class Customer implements Serializable {

    private static final long serialVersionUID = -4967208312143286794L;
    @MyRequiredRule(required = true, exceptionMsg = "客户姓名不能为空")
    private String cusName;
    private String idType;
    private String idNo;
    private String phone;
    private String email;
    private String address;
}
