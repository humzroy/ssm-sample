package com.zhen.datasource;

import com.zhen.pagehelper.PageHelperHolder;

/**
 * @author : wuhengzhen
 * @Description : 动态切换数据源支持器
 * @date : 2018/09/11 16:33
 * @system name:
 * @copyright:
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    /**
     * @param dataSourceType 数据库类型
     * @return void
     * @Description: 设置数据源类型
     */
    public static void setDataSourceType(String dataSourceType) {
        // 根据数据源定义数据库分页方言
        DataSourceType dataSourceTypeDialect = DataSourceType.getType(dataSourceType);
        if (dataSourceType != null) {
            PageHelperHolder.setPagerType(dataSourceTypeDialect.getDialect());
        }
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * @return String
     * @Description: 获取数据源类型
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * @return void
     * @Description: 清除数据源类型
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }

}
