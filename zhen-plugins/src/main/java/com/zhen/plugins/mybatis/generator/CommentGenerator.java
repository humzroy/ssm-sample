package com.zhen.plugins.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author : wuhengzhen
 * <p>
 * 扩展一个 mybatis generator 插件，用于不仅仅在生成的实体类 还有 *Example 类都序列化
 * @Description :生成model中，字段增加注释
 * @date : 2018/08/08 13:59
 * Copyright 2018 <a href="https://github.com/wuhengzhen" target="_blank">https://github.com/wuhengzhen</a>. All rights reserved.
 */
public class CommentGenerator extends DefaultCommentGenerator {

    private Properties systemPro;
    private boolean suppressDate;
    private String nowTime;

    public CommentGenerator() {
        super();
        systemPro = System.getProperties();
        suppressDate = false;
        nowTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // super.addFieldComment(field, introspectedTable, introspectedColumn);
        if (introspectedColumn.getRemarks() != null && !"".equals(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks() + " " + introspectedColumn.getActualColumnName());
            // addJavadocTag(field, false);
            field.addJavaDocLine(" */");
        }
    }

    /**
     * 设置getter方法注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);

        //加入系统用户
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);

        //是否加入时间戳
        if (suppressDate) {
            sb.append(" * @date " + nowTime);
            method.addJavaDocLine(sb.toString().replace("\n", " "));
            sb.setLength(0);
        }

        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    /**
     * 设置setter方法注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);

        //加入系统用户
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);

        //是否加入时间戳
        if (suppressDate) {
            sb.append(" * @date " + nowTime);
            method.addJavaDocLine(sb.toString().replace("\n", " "));
            sb.setLength(0);
        }

        Parameter parm = method.getParameters().get(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        sb.append(" ");
        sb.append(nowTime);
        innerClass.addJavaDocLine(" */");
    }
}
