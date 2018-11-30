package com.zhen.plugins.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * @author : wuhengzhen
 * <p>
 * 扩展一个 mybatis generator 插件，用于不仅仅在生成的实体类 还有 *Example 类都序列化
 * @Description :生成model中，字段增加注释
 * @date : 2018/08/08 13:59
 * @system name:
 * Copyright 2018 <a href="https://github.com/wuhengzhen" target="_blank">https://github.com/wuhengzhen</a>. All rights reserved.
 */
public class CommentGenerator extends DefaultCommentGenerator {
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        super.addFieldComment(field, introspectedTable, introspectedColumn);
        if (introspectedColumn.getRemarks() != null && !"".equals(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
            addJavadocTag(field, false);
            field.addJavaDocLine(" */");
        }
    }
}
