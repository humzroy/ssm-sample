package com.test.pagehelper;

import com.github.pagehelper.Dialect;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 支持多数据源分页
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageHelperUtil implements Interceptor {

    private PageHelper mysqlPageHelper = new PageHelper();

    private PageHelper oraclePageHelper = new PageHelper();

    private PageHelper postgresqlPageHelper = new PageHelper();

    private Map<Object, PageHelper> targetPageHelper = new HashMap<>();

    private PageHelper defaultPageHelper;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return determinePageHelper().intercept(invocation);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        targetPageHelper.put(Dialect.mysql.name(), mysqlPageHelper);
        targetPageHelper.put(Dialect.oracle.name(), oraclePageHelper);
        targetPageHelper.put(Dialect.postgresql.name(), postgresqlPageHelper);
        // 数据库方言
        String dialect = properties.getProperty("dialect");
        if (Dialect.oracle.equals(Dialect.valueOf(dialect.toLowerCase()))) {
            defaultPageHelper = oraclePageHelper;
        } else if (Dialect.postgresql.equals(Dialect.valueOf(dialect.toLowerCase()))) {
            defaultPageHelper = postgresqlPageHelper;
        } else {
            defaultPageHelper = mysqlPageHelper;
        }

        properties.put("dialect", Dialect.mysql.name());
        mysqlPageHelper.setProperties(properties);

        properties.put("dialect", Dialect.oracle.name());
        oraclePageHelper.setProperties(properties);

        properties.put("dialect", Dialect.postgresql.name());
        postgresqlPageHelper.setProperties(properties);

        properties.put("dialect", dialect);
    }

    /**
     * 根据数据源获取对应的分页插件
     *
     * @return
     */
    private PageHelper determinePageHelper() {
        String pageType = PageHelperHolder.getPagerType();
        PageHelper pageHelper = targetPageHelper.get(pageType);
        if (pageHelper != null) {
            return pageHelper;
        } else {
            return defaultPageHelper;
        }
    }

}
