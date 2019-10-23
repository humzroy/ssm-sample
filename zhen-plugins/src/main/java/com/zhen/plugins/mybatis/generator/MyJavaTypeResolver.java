package com.zhen.plugins.mybatis.generator;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;


/**
 * Created with IntelliJ IDEA
 *
 * 把数据库的 OTHER 映射成 String,jdbcTypy改为VARCHAR
 * 原因:
 * 如果把jdbcTypy改为NVARCHAR2,生成的mapper文件的jdbcType=NVARCHAR2
 * mybatis并不能识别,因为mybatis配置中并没有NVARCHAR2这个类型
 *
 * @Author：wuhengzhen
 * @Date：2019-10-23 17:25
 */
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    public MyJavaTypeResolver() {
        super();
        //把数据库的 NVARCHAR2 映射成 String
        super.typeMap.put(Types.OTHER, new JdbcTypeInformation("NVARCHAR2", new FullyQualifiedJavaType(String.class.getName())));
    }
}
