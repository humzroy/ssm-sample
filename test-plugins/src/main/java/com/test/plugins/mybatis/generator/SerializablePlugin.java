package com.test.plugins.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Properties;

/**
 * @author : wuhengzhen
 * @Description :分布式开发的话，Example对象也必须要序列化
 * <p>
 * 扩展一个 mybatis generator 插件，用于不仅仅在生成的实体类 还有 *Example 类都序列化
 * @date : 2018/08/08 14:12
 * @system name:
 * Copyright 2018 <a href="https://github.com/wuhengzhen" target="_blank">https://github.com/wuhengzhen</a>. All rights reserved.
 */
public class SerializablePlugin extends PluginAdapter {
    private FullyQualifiedJavaType serializable;
    private FullyQualifiedJavaType gwtSerializable;
    private boolean addGWTInterface;
    private boolean suppressJavaInterface;

    /**
     * 构造方法
     */
    public SerializablePlugin() {
        super();
        serializable = new FullyQualifiedJavaType("java.io.Serializable");

        gwtSerializable = new FullyQualifiedJavaType("com.google.gwt.user.client.rpc.IsSerializable");

    }

    public boolean validate(List<String> warnings) {
        // this plugin is always valid

        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        addGWTInterface = Boolean.valueOf(properties.getProperty("addGWTInterface"));

        suppressJavaInterface = Boolean.valueOf(properties.getProperty("suppressJavaInterface"));

    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 添加给Example类序列化的方法
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);

        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) {

                innerClass.addSuperInterface(serializable);
            }
            if ("Criteria".equals(innerClass.getType().getShortName())) {

                innerClass.addSuperInterface(serializable);
            }
            if ("Criterion".equals(innerClass.getType().getShortName())) {

                innerClass.addSuperInterface(serializable);
            }
        }

        return true;
    }

    protected void makeSerializable(TopLevelClass topLevelClass,
                                    IntrospectedTable introspectedTable) {
        if (addGWTInterface) {
            topLevelClass.addImportedType(gwtSerializable);
            topLevelClass.addSuperInterface(gwtSerializable);
        }

        if (!suppressJavaInterface) {
            topLevelClass.addImportedType(serializable);
            topLevelClass.addSuperInterface(serializable);

            Field field = new Field();
            field.setFinal(true);
            //$NON-NLS-1$
            field.setInitializationString("1L");
            //$NON-NLS-1$
            field.setName("serialVersionUID");

            field.setStatic(true);
            //$NON-NLS-1$
            field.setType(new FullyQualifiedJavaType("long"));

            field.setVisibility(JavaVisibility.PRIVATE);
            context.getCommentGenerator().addFieldComment(field, introspectedTable);

            topLevelClass.addField(field);
        }
    }

}
