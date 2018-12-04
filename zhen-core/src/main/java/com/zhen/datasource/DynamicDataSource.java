package com.zhen.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author : wuhengzhen
 * @Description : 动态切换数据源
 * @date : 2018/09/11 16:32
 * @system name:
 * @copyright:
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    /**
     * Determine the current lookup key. This will typically be
     * implemented to check a thread-bound transaction context.
     * <p>Allows for arbitrary keys. The returned key needs
     * to match the stored lookup key type, as resolved by the
     * {@link #resolveSpecifiedLookupKey} method.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DataSourceContextHolder.getDataSourceType();
        logger.info("当前操作使用的数据源：{}", dataSource);
        return dataSource;
    }
}
