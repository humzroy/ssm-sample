package com.test.datasource;

import com.github.pagehelper.Dialect;

/**
 * 数据库方言枚举
 */
public enum DataSourceType {
    /**
     * mysql数据库方言
     */
    MYSQL_DATASOURCE("MYSQL", Dialect.mysql),
    /**
     * oracle数据库方言
     */
    ORACLE_DATASOURCE("ORACLE", Dialect.oracle);

    /**
     * 数据库类型
     */
    private String type;

    /**
     * 方言
     */
    private Dialect dialect;


    DataSourceType(String type, Dialect dialect) {
        this.type = type;
        this.dialect = dialect;
    }

    /**
     * 获取数据库对应的方言
     * @return
     */
    public Dialect getDialect() {
        return dialect;
    }

    /**
     * 获取数据库类型
     *
     * @param type
     * @return
     */
    public static DataSourceType getType(String type) {
        for (DataSourceType dataSourceType : DataSourceType.values()) {
            if (dataSourceType.type.equals(type)) {
                return dataSourceType;
            }
        }
        return null;
    }
}
